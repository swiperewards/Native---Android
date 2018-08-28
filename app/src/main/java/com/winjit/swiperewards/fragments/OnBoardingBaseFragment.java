package com.winjit.swiperewards.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.winjit.swiperewards.R;
import com.winjit.swiperewards.activities.HomeActivity;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.entities.SessionData;
import com.winjit.swiperewards.entities.UserDetails;
import com.winjit.swiperewards.helpers.PreferenceUtils;
import com.winjit.swiperewards.helpers.UIHelper;
import com.winjit.swiperewards.mvpviews.OnBoardingView;
import com.winjit.swiperewards.presenters.OnBoardingPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class OnBoardingBaseFragment extends BaseFragment implements OnBoardingView {
    protected OnBoardingPresenter onBoardingPresenter;
    protected LoginButton fbLoginButton;
    private CallbackManager callbackManager;
    private static final String EMAIL = "email";
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onBoardingPresenter = new OnBoardingPresenter(this);
        callbackManager = CallbackManager.Factory.create();
    }

    protected void initializeGoogleLogin() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        mGoogleSignInClient.signOut();
        mGoogleSignInClient.revokeAccess();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    public void initiateFacebookLogin() {
        LoginManager.getInstance().logOut();
        fbLoginButton.setReadPermissions(Arrays.asList(EMAIL));
        fbLoginButton.setFragment(this);
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                final AccessToken accessToken = loginResult.getAccessToken();
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    String id = object.getString("id");
                                    if (!TextUtils.isEmpty(id)) {
                                        String name = object.getString("name");
                                        String email = object.getString("email");
                                        String profilePic = "https://graph.facebook.com/" + id + "/picture?type=large";
                                        registerUserToSwipeByFacebookAccount(accessToken, name, email, profilePic);
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    hideProgress();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.e("fb", "fb cancel");
                hideProgress();
            }

            @Override
            public void onError(FacebookException exception) {
                hideProgress();
                showMessage("Error while connecting Facebook.");
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                registerUserToSwipeByGoogleAccount(account);
            } catch (ApiException e) {
                Log.w("Social", "Google sign in failed", e);

            }
        } else {
            showProgress(getActivity().getResources().getString(R.string.please_wait));
            callbackManager.onActivityResult(requestCode, resultCode, data);
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void registerUserToSwipeByGoogleAccount(GoogleSignInAccount account) {
        if (account != null && !TextUtils.isEmpty(account.getDisplayName())
                && !TextUtils.isEmpty(account.getEmail()) && !TextUtils.isEmpty(account.getIdToken())) {
            UserDetails userDetails = new UserDetails();
            userDetails.setFullName(account.getDisplayName());
            userDetails.setEmailId(account.getEmail());
            userDetails.setLatitude("");
            userDetails.setLongitude("");
            userDetails.setPassword("");
            userDetails.setSocialToken(account.getIdToken());
            userDetails.setSocialLogin(true);
            userDetails.setProfilePicUrl(account.getPhotoUrl().toString());
            showProgress(getActivity().getResources().getString(R.string.please_wait));
            onBoardingPresenter.registerUser(userDetails);
        } else {
            hideProgress();
        }
    }


    private void registerUserToSwipeByFacebookAccount(AccessToken accessToken, String name, String email, String profilePic) {
        if (!(accessToken == null && TextUtils.isEmpty(accessToken.getToken()) &&
                TextUtils.isEmpty(name) && TextUtils.isEmpty(email) && TextUtils.isEmpty(profilePic))) {
            UserDetails userDetails = new UserDetails();
            userDetails.setFullName(name);
            userDetails.setEmailId(email);
            userDetails.setLatitude("");
            userDetails.setLongitude("");
            userDetails.setPassword("");
            userDetails.setSocialLogin(true);
            userDetails.setProfilePicUrl(profilePic);
            userDetails.setSocialToken(accessToken.getToken());
            onBoardingPresenter.registerUser(userDetails);
        } else {
            hideProgress();
        }
    }


    @Override
    public void onSuccessfulRegistration(SessionData sessionData) {
        SuccessFragment successFragment = SuccessFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putBoolean(ISwipe.IS_FROM_SIGN_UP, true);
        successFragment.setArguments(bundle);
        UIHelper.getInstance().replaceFragment(getActivity().getSupportFragmentManager(), R.id.login_container,successFragment,false, ISwipe.FragTagSuccessFragment,null);


    }

    @Override
    public void onSuccessfulLogin(SessionData sessionData) {
        PreferenceUtils.writeString(getActivity(), PreferenceUtils.SESSION_TOKEN, sessionData.getToken());
        Intent homeIntent = new Intent(getActivity(), HomeActivity.class);
        startActivity(homeIntent);
        getActivity().finish();
    }

}