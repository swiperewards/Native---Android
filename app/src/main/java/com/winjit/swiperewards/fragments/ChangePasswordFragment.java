package com.winjit.swiperewards.fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.winjit.swiperewards.R;
import com.winjit.swiperewards.utils.UIHelper;
import com.winjit.swiperewards.utils.ValidationHelper;


public class ChangePasswordFragment extends BaseFragment implements View.OnClickListener {

    private TextInputEditText etOldPassword;
    private TextInputEditText etNewPassword;
    private TextInputEditText etConfirmNewPassword;
    private Button btSubmit;


    public ChangePasswordFragment() {
    }

    public static ChangePasswordFragment newInstance() {
        Bundle args = new Bundle();
        ChangePasswordFragment fragment = new ChangePasswordFragment();
        fragment.setArguments(args);
        return fragment;
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
                if (isValidInputsEntered()) {
                    UIHelper.getInstance().popFragment(getActivity().getSupportFragmentManager());
                }
                break;
        }
    }

    private boolean isValidInputsEntered() {
        ValidationHelper validationHelper = new ValidationHelper();
        return validationHelper.isValidEditTexts(getActivity(), etOldPassword, etNewPassword, etConfirmNewPassword) &&
                validationHelper.isPasswordMatch(getActivity(), etNewPassword, etOldPassword);
    }
}
