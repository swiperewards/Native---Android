package com.winjit.swiperewards.fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.winjit.swiperewards.R;
import com.winjit.swiperewards.utils.UIHelper;
import com.winjit.swiperewards.utils.ValidationHelper;


public class LoginFragment extends BaseFragment implements View.OnClickListener {

    private TextInputEditText etUserEmail;
    private TextInputEditText etPassword;
    private TextView tvForgotPassword;
    private Button btLogin;
    private AppCompatImageView ivFacebook;
    private AppCompatImageView ivGoogle;
    private Button btRegister;


    public LoginFragment() {
    }

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
        ivFacebook = (AppCompatImageView) mRootView.findViewById(R.id.iv_facebook);
        ivGoogle = (AppCompatImageView) mRootView.findViewById(R.id.iv_google);
        btRegister = (Button) mRootView.findViewById(R.id.bt_register);

        btLogin.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
        ivFacebook.setOnClickListener(this);
        ivGoogle.setOnClickListener(this);
        btRegister.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                if(isValidInputsEntered()){

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
}
