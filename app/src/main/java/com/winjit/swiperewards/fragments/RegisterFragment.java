package com.winjit.swiperewards.fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.winjit.swiperewards.R;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.utils.UIHelper;


public class RegisterFragment extends Fragment implements View.OnClickListener {

    private TextInputEditText etFirstName;
    private TextInputEditText etLastName;
    private TextInputEditText etUserEmail;
    private TextInputEditText etPassword;
    private TextInputEditText etConfirmPassword;
    private Button btSignUp;
    private AppCompatImageView ivFacebook;
    private AppCompatImageView ivGoogle;
    private TextView tvSignIn;

    public RegisterFragment() {
    }

    public static RegisterFragment newInstance() {
        Bundle args = new Bundle();
        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
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
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_sign_up:
                SuccessFragment successFragment = SuccessFragment.newInstance();
                Bundle bundle = new Bundle();
                bundle.putBoolean(ISwipe.IS_FROM_SIGN_UP, true);
                successFragment.setArguments(bundle);
                UIHelper.getInstance().replaceFragment(getActivity().getSupportFragmentManager(), R.id.login_container, successFragment, true);
                break;
        }
    }
}
