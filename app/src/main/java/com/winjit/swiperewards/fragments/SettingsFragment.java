package com.winjit.swiperewards.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.winjit.swiperewards.R;
import com.winjit.swiperewards.activities.HomeActivity;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.utils.UIHelper;


public class SettingsFragment extends Fragment implements View.OnClickListener {
    private SwitchCompat swNotification;
    private AppCompatTextView tvChangePassword;
    private AppCompatTextView tvContactUs;
    private AppCompatTextView tvPrivacy;
    private AppCompatTextView tvTermsOfUse;
    private AppCompatTextView tvSignOut;

    public SettingsFragment() {
    }

    public static SettingsFragment newInstance() {
        Bundle args = new Bundle();
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        initViews(view);
        ((HomeActivity)getActivity()).setTopLayoutVisibility(ISwipe.SHOW_TOP_VIEW);
        return view;
    }

    private void initViews(View mRootView) {
        swNotification = (SwitchCompat) mRootView.findViewById(R.id.sw_notification);
        tvChangePassword = (AppCompatTextView) mRootView.findViewById(R.id.tv_change_password);
        tvContactUs = (AppCompatTextView) mRootView.findViewById(R.id.tv_contact_us);
        tvPrivacy = (AppCompatTextView) mRootView.findViewById(R.id.tv_privacy);
        tvTermsOfUse = (AppCompatTextView) mRootView.findViewById(R.id.tv_terms_of_use);
        tvSignOut = (AppCompatTextView) mRootView.findViewById(R.id.tv_sign_out);

        tvChangePassword.setOnClickListener(this);
        tvContactUs.setOnClickListener(this);
        tvPrivacy.setOnClickListener(this);
        tvTermsOfUse.setOnClickListener(this);
        tvSignOut.setOnClickListener(this);

        swNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_change_password:
                ((HomeActivity)getActivity()).setTopLayoutVisibility(ISwipe.HIDE_TOP_VIEW);
                UIHelper.getInstance().replaceFragment(getActivity().getSupportFragmentManager(), R.id.main_container, ChangePasswordFragment.newInstance(), true);
                break;
        }
    }
}
