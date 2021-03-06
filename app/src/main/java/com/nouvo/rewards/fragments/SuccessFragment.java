package com.nouvo.rewards.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nouvo.rewards.R;
import com.nouvo.rewards.constants.ISwipe;
import com.nouvo.rewards.helpers.UIHelper;


public class SuccessFragment extends Fragment implements View.OnClickListener {
    private TextView tvMessage;
    private Button btContinue;
    private boolean isFromSignUpScreen;


    public static SuccessFragment newInstance() {
        Bundle args = new Bundle();
        SuccessFragment fragment = new SuccessFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(ISwipe.IS_FROM_SIGN_UP)) {
            isFromSignUpScreen = bundle.getBoolean(ISwipe.IS_FROM_SIGN_UP);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_success, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View mRootView) {
        tvMessage = (TextView) mRootView.findViewById(R.id.tv_message);
        btContinue = (Button) mRootView.findViewById(R.id.bt_continue);

        btContinue.setOnClickListener(this);

        if (isFromSignUpScreen) {
            tvMessage.setText(getActivity().getResources().getString(R.string.verify_email));
        } else {
            tvMessage.setText(getActivity().getResources().getString(R.string.password_sent));
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_continue:
                if (isFromSignUpScreen) {
                    UIHelper.getInstance().popFragment(getActivity().getSupportFragmentManager());
                }else{
                    UIHelper.getInstance().replaceFragment(getActivity().getSupportFragmentManager(), R.id.login_container,SetNewPasswordFragment.newInstance(),false, ISwipe.FragTagSetNewPasswordFragment,null);

                }
                break;
        }
    }
}
