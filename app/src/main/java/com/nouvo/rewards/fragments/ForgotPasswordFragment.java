package com.nouvo.rewards.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nouvo.rewards.R;
import com.nouvo.rewards.activities.LoginActivity;
import com.nouvo.rewards.constants.ISwipe;
import com.nouvo.rewards.helpers.UIHelper;
import com.nouvo.rewards.helpers.ValidationHelper;
import com.nouvo.rewards.mvpviews.PasswordView;
import com.nouvo.rewards.presenters.PasswordPresenter;


public class ForgotPasswordFragment extends BaseFragment implements View.OnClickListener, PasswordView {
    private TextInputEditText etUserEmail;
    private Button btSubmit;
    private PasswordPresenter passwordPresenter;


    public static ForgotPasswordFragment newInstance() {
        Bundle args = new Bundle();
        ForgotPasswordFragment fragment = new ForgotPasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        passwordPresenter = new PasswordPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View mRootView) {
        etUserEmail = (TextInputEditText) mRootView.findViewById(R.id.et_user_email);
        btSubmit = (Button) mRootView.findViewById(R.id.bt_submit);

        btSubmit.setOnClickListener(this);
        ((LoginActivity) getActivity()).changeHeader(getActivity().getResources().getString(R.string.welcome));
    }


    @Override
    public void onClick(View v) {
        UIHelper.getInstance().hideKeyboard(getActivity());
        switch (v.getId()) {
            case R.id.bt_submit:
                if (isValidInputsEntered()) {
                    showProgress(getActivity().getResources().getString(R.string.please_wait));
                    passwordPresenter.forgotPassword(etUserEmail.getText().toString());
                    break;
                }
        }
    }

    private boolean isValidInputsEntered() {
        ValidationHelper validationHelper = new ValidationHelper();
        return validationHelper.isValidEmail(getActivity(), etUserEmail);

    }

    @Override
    public void onPasswordChangedSuccessfully() {

    }

    @Override
    public void onPasswordLinkSentSuccessfully() {
        SetNewPasswordFragment setNewPasswordFragment = SetNewPasswordFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putString(ISwipe.KEY_EMAIL, etUserEmail.getText().toString());
        setNewPasswordFragment.setArguments(bundle);
        UIHelper.getInstance().replaceFragment(getActivity().getSupportFragmentManager(), R.id.login_container,setNewPasswordFragment,false, ISwipe.FragTagSetNewPasswordFragment,null);
    }
}
