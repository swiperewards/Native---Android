package com.winjit.swiperewards.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.winjit.swiperewards.R;
import com.winjit.swiperewards.activities.HomeActivity;
import com.winjit.swiperewards.activities.LoginActivity;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.helpers.UIHelper;
import com.winjit.swiperewards.interfaces.MessageDialogConfirm;
import com.winjit.swiperewards.mvpviews.SettingsView;
import com.winjit.swiperewards.presenters.SettingsPresenter;

public class SettingsFragment extends BaseFragment implements View.OnClickListener, SettingsView, CompoundButton.OnCheckedChangeListener {
    private SwitchCompat swNotification;
    private AppCompatTextView tvChangePassword;
    private AppCompatTextView tvContactUs;
    private AppCompatTextView tvPrivacy;
    private AppCompatTextView tvTermsOfUse;
    private AppCompatTextView tvSignOut;
    private SettingsPresenter settingsPresenter;


    public static SettingsFragment newInstance() {
        Bundle args = new Bundle();
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsPresenter = new SettingsPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        initViews(view);
        ((HomeActivity) getActivity()).setTopLayoutVisibility(ISwipe.SHOW_TOP_VIEW);
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

        swNotification.setOnCheckedChangeListener(this);
    }

    private void showConfirmationLogoutDialog() {
        String dialogInterfaceMessage = "Are you sure you want to sign out?";

        UIHelper.configureShowConfirmDialog(dialogInterfaceMessage, getActivity(),
                R.string.yes, R.string.btn_cancel,
                new MessageDialogConfirm() {
                    @Override
                    public void onPositiveClick() {
                        Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(loginIntent);
                        getActivity().finish();
                    }

                    @Override
                    public void onNegativeClick() {
                    }
                });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_change_password:
                ((HomeActivity) getActivity()).setTopLayoutVisibility(ISwipe.HIDE_TOP_VIEW);
                UIHelper.getInstance().replaceFragment(getActivity().getSupportFragmentManager(), R.id.main_container, ChangePasswordFragment.newInstance(), true);
                break;
            case R.id.tv_contact_us:
                ((HomeActivity) getActivity()).setTopLayoutVisibility(ISwipe.HIDE_TOP_VIEW);
                UIHelper.getInstance().replaceFragment(getActivity().getSupportFragmentManager(), R.id.main_container, ContactUsFragment.newInstance(), true);
                break;
            case R.id.tv_privacy:
                if (((HomeActivity) getActivity()) != null) {
                    ((HomeActivity) getActivity()).setTopBarTitle(ISwipe.TITLE_PRIVACY_SECURITY);
                }
                WebViewFragment webViewFragment = WebViewFragment.newInstance();
                Bundle bundle = new Bundle();
                bundle.putString(ISwipe.WEB_URL, ISwipe.PRIVACY_SECURITY_URL);
                webViewFragment.setArguments(bundle);
                UIHelper.getInstance().replaceFragment(getActivity().getSupportFragmentManager(), R.id.main_container, webViewFragment, true);
                break;
            case R.id.tv_terms_of_use:
                if (((HomeActivity) getActivity()) != null) {
                    ((HomeActivity) getActivity()).setTopBarTitle(ISwipe.TITLE_TERMS_OF_USE);
                }
                WebViewFragment webViewTermsFragment = WebViewFragment.newInstance();
                Bundle termsBundle = new Bundle();
                termsBundle.putString(ISwipe.WEB_URL, ISwipe.TERMS_OF_USE_URL);
                webViewTermsFragment.setArguments(termsBundle);
                UIHelper.getInstance().replaceFragment(getActivity().getSupportFragmentManager(), R.id.main_container, webViewTermsFragment, true);
                break;
            case R.id.tv_sign_out:
                showConfirmationLogoutDialog();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (((HomeActivity) getActivity()) != null) {
            ((HomeActivity) getActivity()).setTopBarTitle(ISwipe.TITLE_SETTINGS);
        }
    }

    @Override
    public void onNotificationStatusChanged(boolean updatedNotificationStatus) {
        if (swNotification.isEnabled()) {
            showMessage(getActivity().getResources().getString(R.string.notification_enabled));
        }
    }

    @Override
    public void onErrorNotificationState() {
        swNotification.setOnCheckedChangeListener(null);
        swNotification.setChecked(!swNotification.isChecked());
        swNotification.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isEnabled) {
        settingsPresenter.updateNotificationStatus(isEnabled);
    }
}
