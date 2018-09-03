package com.nouvo.rewards.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.login.widget.LoginButton;
import com.nouvo.rewards.R;
import com.nouvo.rewards.activities.LoginActivity;
import com.nouvo.rewards.constants.ISwipe;
import com.nouvo.rewards.entities.UserDetails;
import com.nouvo.rewards.helpers.UIHelper;
import com.nouvo.rewards.helpers.ValidationHelper;


public class RegisterFragment extends OnBoardingBaseFragment implements View.OnClickListener {

    private TextInputEditText etFirstName;
    private TextInputEditText etLastName;
    private TextInputEditText etUserEmail;
    private TextInputEditText etPassword;
    private TextInputEditText etConfirmPassword;
    private Button btSignUp;
    private AppCompatImageView ivFacebook;
    private AppCompatImageView ivGoogle;
    private TextView tvSignIn;


    public static RegisterFragment newInstance() {
        Bundle args = new Bundle();
        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View mRootView) {
        etFirstName = (TextInputEditText) mRootView.findViewById(R.id.et_first_name);
        etLastName = (TextInputEditText) mRootView.findViewById(R.id.et_last_name);
        etUserEmail = (TextInputEditText) mRootView.findViewById(R.id.et_user_email);
        etPassword = (TextInputEditText) mRootView.findViewById(R.id.et_password);
        etConfirmPassword = (TextInputEditText) mRootView.findViewById(R.id.et_confirm_password);
        btSignUp = (Button) mRootView.findViewById(R.id.bt_sign_up);
        ivFacebook = (AppCompatImageView) mRootView.findViewById(R.id.iv_facebook);
        ivGoogle = (AppCompatImageView) mRootView.findViewById(R.id.iv_google);
        tvSignIn = (TextView) mRootView.findViewById(R.id.tv_sign_in);
        fbLoginButton = (LoginButton) mRootView.findViewById(R.id.fb_login_button);

        btSignUp.setOnClickListener(this);
        ivFacebook.setOnClickListener(this);
        ivGoogle.setOnClickListener(this);
        tvSignIn.setOnClickListener(this);
        fbLoginButton.setOnClickListener(this);

        ((LoginActivity) getActivity()).changeHeader(getActivity().getResources().getString(R.string.welcome_register));
        setDummyData();
    }


    @Override
    public void onClick(View v) {
        UIHelper.getInstance().hideKeyboard(getActivity());
        switch (v.getId()) {
            case R.id.bt_sign_up:
                if (isValidInputsEntered()) {
                    showProgress(getActivity().getResources().getString(R.string.please_wait));
                    onBoardingPresenter.registerUser(getUserDetails());
                }
                break;
            case R.id.tv_sign_in:
                new UIHelper().getInstance().popFragment(getActivity().getSupportFragmentManager());
                break;
            case R.id.iv_facebook:
                fbLoginButton.performClick();
                break;
            case R.id.fb_login_button:
                initiateFacebookLogin();
                break;
            case R.id.iv_google:
                initializeGoogleLogin();
                break;
        }
    }

    private UserDetails getUserDetails() {
        UserDetails userDetails = new UserDetails();
        userDetails.setFullName(etFirstName.getText().toString() + " " + etLastName.getText().toString());
        userDetails.setEmailId(etUserEmail.getText().toString());
        userDetails.setLatitude("");
        userDetails.setLongitude("");
        userDetails.setPassword(etPassword.getText().toString());
        userDetails.setSocialLogin(false);
        return userDetails;
    }


    private boolean isValidInputsEntered() {
        ValidationHelper validationHelper = new ValidationHelper();
        return validationHelper.isValidEditTexts(getActivity(), etFirstName, etLastName, etUserEmail, etPassword, etConfirmPassword) &&
                validationHelper.isPasswordMatch(getActivity(), etPassword, etConfirmPassword) &&
                validationHelper.isAcceptablePassword(getActivity(), etPassword) &&
                validationHelper.isValidEmail(getActivity(), etUserEmail);

    }


    private void setDummyData() {
        if (ISwipe.IS_DUMMY_DATA_ENABLED) {
            etFirstName.setText("vishal");
            etLastName.setText("bharati");
            etPassword.setText("winjit@123");
            etConfirmPassword.setText("winjit@123");
            etUserEmail.setText("vishalb@winjit.com");
        }
    }
}
