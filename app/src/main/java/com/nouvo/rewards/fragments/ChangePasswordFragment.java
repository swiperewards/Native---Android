package com.nouvo.rewards.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nouvo.rewards.R;
import com.nouvo.rewards.activities.HomeActivity;
import com.nouvo.rewards.constants.ISwipe;
import com.nouvo.rewards.helpers.UIHelper;
import com.nouvo.rewards.helpers.ValidationHelper;
import com.nouvo.rewards.mvpviews.PasswordView;
import com.nouvo.rewards.presenters.PasswordPresenter;


public class ChangePasswordFragment extends BaseFragment implements View.OnClickListener, PasswordView {

    private TextInputEditText etOldPassword;
    private TextInputEditText etNewPassword;
    private TextInputEditText etConfirmNewPassword;
    private Button btSubmit;
    private PasswordPresenter passwordPresenter;


    public static ChangePasswordFragment newInstance() {
        Bundle args = new Bundle();
        ChangePasswordFragment fragment = new ChangePasswordFragment();
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

        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View mRootView) {
        etOldPassword = (TextInputEditText) mRootView.findViewById(R.id.et_old_password);
        etNewPassword = (TextInputEditText) mRootView.findViewById(R.id.et_new_password);
        etConfirmNewPassword = (TextInputEditText) mRootView.findViewById(R.id.et_confirm_new_password);
        btSubmit = (Button) mRootView.findViewById(R.id.bt_submit);

        btSubmit.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_submit:
                UIHelper.getInstance().hideKeyboard(getActivity());
                if (isValidInputsEntered()) {
                    showProgress(getActivity().getResources().getString(R.string.please_wait));
                    passwordPresenter.changePassword(etOldPassword.getText().toString(), etNewPassword.getText().toString());
                }
                break;
        }
    }

    private boolean isValidInputsEntered() {
        ValidationHelper validationHelper = new ValidationHelper();
        return validationHelper.isValidEditTexts(getActivity(), etOldPassword, etNewPassword, etConfirmNewPassword) &&
                validationHelper.isPasswordMatch(getActivity(), etNewPassword, etConfirmNewPassword) &&
                validationHelper.isAcceptablePassword(getActivity(), etNewPassword);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (((HomeActivity) getActivity()) != null) {
            ((HomeActivity) getActivity()).setTopBarTitle(ISwipe.TITLE_CHANGE_PASSWORD);
        }
    }

    @Override
    public void onPasswordChangedSuccessfully() {
        showMessage(getActivity().getResources().getString(R.string.password_changed));
        clearInputFields();
//        UIHelper.getInstance().popFragment(getActivity().getSupportFragmentManager());
    }

    private void clearInputFields() {
        etOldPassword.setText("");
        etNewPassword.setText("");
        etConfirmNewPassword.setText("");
    }

    @Override
    public void onPasswordLinkSentSuccessfully() {

    }
}
