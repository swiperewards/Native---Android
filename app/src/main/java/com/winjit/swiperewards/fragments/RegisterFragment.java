package com.winjit.swiperewards.fragments;

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
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.entities.SessionData;
import com.winjit.swiperewards.entities.UserDetails;
import com.winjit.swiperewards.mvpviews.OnBoardingView;
import com.winjit.swiperewards.presenters.OnBoardingPresenter;
import com.winjit.swiperewards.helpers.UIHelper;
import com.winjit.swiperewards.helpers.ValidationHelper;


public class RegisterFragment extends BaseFragment implements View.OnClickListener, OnBoardingView {

    private TextInputEditText etFirstName;
    private TextInputEditText etLastName;
    private TextInputEditText etUserEmail;
    private TextInputEditText etPassword;
    private TextInputEditText etConfirmPassword;
    private Button btSignUp;
    private AppCompatImageView ivFacebook;
    private AppCompatImageView ivGoogle;
    private TextView tvSignIn;
    private OnBoardingPresenter onBoardingPresenter;


    public static RegisterFragment newInstance() {
        Bundle args = new Bundle();
        RegisterFragment fragment = new RegisterFragment();
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

        btSignUp.setOnClickListener(this);
        ivFacebook.setOnClickListener(this);
        ivGoogle.setOnClickListener(this);
        tvSignIn.setOnClickListener(this);

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
                    break;
                }
        }
    }

    private UserDetails getUserDetails() {
        UserDetails userDetails = new UserDetails();
        userDetails.setFullName(etFirstName.getText().toString() + " "+etLastName.getText().toString());
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
                validationHelper.isValidEmail(getActivity(), etUserEmail);

    }

    @Override
    public void onSuccessfulRegistration(SessionData sessionData) {
        SuccessFragment successFragment = SuccessFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putBoolean(ISwipe.IS_FROM_SIGN_UP, true);
        successFragment.setArguments(bundle);
        UIHelper.getInstance().replaceFragment(getActivity().getSupportFragmentManager(), R.id.login_container, successFragment, true);
    }

    @Override
    public void onSuccessfulLogin(SessionData sessionData) {

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
