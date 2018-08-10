package com.winjit.swiperewards.fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.login.widget.LoginButton;
import com.winjit.swiperewards.R;
import com.winjit.swiperewards.activities.LoginActivity;
import com.winjit.swiperewards.helpers.UIHelper;
import com.winjit.swiperewards.helpers.ValidationHelper;


public class LoginFragment extends SocialBaseFragment implements View.OnClickListener {

    private TextInputEditText etUserEmail;
    private TextInputEditText etPassword;
    private TextView tvForgotPassword;
    private Button btLogin;
    private AppCompatImageView ivGoogle;
    private AppCompatImageView ivFacebook;
    private Button btRegister;


    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View mRootView) {
        etUserEmail = (TextInputEditText) mRootView.findViewById(R.id.et_user_email);
        etPassword = (TextInputEditText) mRootView.findViewById(R.id.et_password);
        tvForgotPassword = (TextView) mRootView.findViewById(R.id.forgot_password);
        btLogin = (Button) mRootView.findViewById(R.id.bt_login);
        fbLoginButton = (LoginButton) mRootView.findViewById(R.id.fb_login_button);
        ivGoogle = (AppCompatImageView) mRootView.findViewById(R.id.iv_google);
        btRegister = (Button) mRootView.findViewById(R.id.bt_register);
        ivFacebook = (AppCompatImageView) mRootView.findViewById(R.id.iv_facebook);


        btLogin.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
        fbLoginButton.setOnClickListener(this);
        ivGoogle.setOnClickListener(this);
        ivFacebook.setOnClickListener(this);
        btRegister.setOnClickListener(this);

        ((LoginActivity) getActivity()).changeHeader(getActivity().getResources().getString(R.string.welcome));
        setDummyData();
    }


    @Override
    public void onClick(View v) {
        UIHelper.getInstance().hideKeyboard(getActivity());
        switch (v.getId()) {
            case R.id.bt_login:
                if (isValidInputsEntered()) {
                    showProgress(getActivity().getResources().getString(R.string.please_wait));
                    onBoardingPresenter.loginUser(etUserEmail.getText().toString(), etPassword.getText().toString());
                }
                break;
            case R.id.forgot_password:
                UIHelper.getInstance().replaceFragment(getActivity().getSupportFragmentManager(), R.id.login_container, ForgotPasswordFragment.newInstance(), true);
                break;
            case R.id.bt_register:
                UIHelper.getInstance().replaceFragment(getActivity().getSupportFragmentManager(), R.id.login_container, RegisterFragment.newInstance(), true);
                break;
            case R.id.iv_google:
                initializeGoogleLogin();
                break;
            case R.id.iv_facebook:
                fbLoginButton.performClick();
                break;
            case R.id.fb_login_button:
                initiateFacebookLogin();
                break;
        }
    }

    private boolean isValidInputsEntered() {
        ValidationHelper validationHelper = new ValidationHelper();
        return validationHelper.isValidEditTexts(getActivity(), etUserEmail, etPassword) &&
                validationHelper.isValidEmail(getActivity(), etUserEmail);

    }


    private void setDummyData() {
//        if (ISwipe.IS_DUMMY_DATA_ENABLED)
        {
            etUserEmail.setText("vishalb@winjit.com");
            etPassword.setText("Qwerty@123");
        }
    }
}
