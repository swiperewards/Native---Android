package com.winjit.swiperewards.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.winjit.swiperewards.R;
import com.winjit.swiperewards.activities.HomeActivity;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.entities.SessionData;
import com.winjit.swiperewards.helpers.PreferenceUtils;
import com.winjit.swiperewards.helpers.UIHelper;
import com.winjit.swiperewards.helpers.ValidationHelper;
import com.winjit.swiperewards.mvpviews.OnBoardingView;
import com.winjit.swiperewards.presenters.OnBoardingPresenter;


public class LoginFragment extends BaseFragment implements View.OnClickListener, OnBoardingView {

    private TextInputEditText etUserEmail;
    private TextInputEditText etPassword;
    private TextView tvForgotPassword;
    private Button btLogin;
    private AppCompatImageView ivFacebook;
    private AppCompatImageView ivGoogle;
    private Button btRegister;
    private OnBoardingPresenter onBoardingPresenter;

    public LoginFragment() {
    }

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onBoardingPresenter = new OnBoardingPresenter(this);
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
        ivFacebook = (AppCompatImageView) mRootView.findViewById(R.id.iv_facebook);
        ivGoogle = (AppCompatImageView) mRootView.findViewById(R.id.iv_google);
        btRegister = (Button) mRootView.findViewById(R.id.bt_register);

        btLogin.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
        ivFacebook.setOnClickListener(this);
        ivGoogle.setOnClickListener(this);
        btRegister.setOnClickListener(this);

        setDummyData();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                if (isValidInputsEntered()) {
                    onBoardingPresenter.loginUser(etUserEmail.getText().toString(), etPassword.getText().toString());
                }
                break;
            case R.id.forgot_password:
                UIHelper.getInstance().replaceFragment(getActivity().getSupportFragmentManager(), R.id.login_container, ForgotPasswordFragment.newInstance(), true);
                break;
            case R.id.bt_register:
                UIHelper.getInstance().replaceFragment(getActivity().getSupportFragmentManager(), R.id.login_container, RegisterFragment.newInstance(), true);
                break;
        }
    }

    private boolean isValidInputsEntered() {
        ValidationHelper validationHelper = new ValidationHelper();
        return validationHelper.isValidEditTexts(getActivity(), etUserEmail, etPassword) &&
                validationHelper.isValidEmail(getActivity(), etUserEmail);

    }

    @Override
    public void onSuccessfulRegistration(SessionData sessionData) {

    }

    @Override
    public void onSuccessfulLogin(SessionData sessionData) {
        PreferenceUtils.writeString(getActivity(), PreferenceUtils.SESSION_TOKEN, sessionData.getToken());
        Intent homeIntent = new Intent(getActivity(), HomeActivity.class);
        startActivity(homeIntent);
        getActivity().finish();
    }

    private void setDummyData() {
        if (ISwipe.IS_DUMMY_DATA_ENABLED) {
            etUserEmail.setText("vishalb@winjit.com");
            etPassword.setText("winjit@123");
        }
    }
}
