package com.winjit.swiperewards.fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.winjit.swiperewards.R;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.utils.UIHelper;
import com.winjit.swiperewards.utils.ValidationHelper;


public class ForgotPasswordFragment extends Fragment implements View.OnClickListener {
    private TextInputEditText etUserEmail;
    private Button btSubmit;

    public ForgotPasswordFragment() {
    }

    public static ForgotPasswordFragment newInstance() {
        Bundle args = new Bundle();
        ForgotPasswordFragment fragment = new ForgotPasswordFragment();
        fragment.setArguments(args);
        return fragment;
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
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_submit:
                if (isValidInputsEntered()) {
                    SuccessFragment successFragment = SuccessFragment.newInstance();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(ISwipe.IS_FROM_SIGN_UP, false);
                    successFragment.setArguments(bundle);
                    UIHelper.getInstance().replaceFragment(getActivity().getSupportFragmentManager(), R.id.login_container, successFragment, false);
                    break;
                }
        }
    }

    private boolean isValidInputsEntered() {
        ValidationHelper validationHelper = new ValidationHelper();
        return validationHelper.isValidEmail(getActivity(), etUserEmail);

    }
}
