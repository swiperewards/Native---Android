package com.nouvo.rewards.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.nouvo.rewards.R;
import com.nouvo.rewards.activities.HomeActivity;
import com.nouvo.rewards.appdata.SingletonAppCache;
import com.nouvo.rewards.constants.ISwipe;
import com.nouvo.rewards.helpers.CommonHelper;
import com.nouvo.rewards.helpers.UIHelper;
import com.nouvo.rewards.interfaces.MessageDialogConfirm;
import com.nouvo.rewards.mvpviews.SettingsView;
import com.nouvo.rewards.presenters.SettingsPresenter;

public class SettingsFragment extends BaseFragment implements View.OnClickListener, SettingsView, CompoundButton.OnCheckedChangeListener {
    private NestedScrollView svParent;
    private SwitchCompat swNotification;
    private AppCompatTextView tvChangePassword;
    private AppCompatTextView tvContactUs;
    private AppCompatTextView tvPrivacy;
    private AppCompatTextView tvTermsOfUse;
    private AppCompatTextView tvSignOut;
    private AppCompatTextView tvReferEarn;
    private SettingsPresenter settingsPresenter;
    private View vwPasswordSeparator;
    private AppCompatTextView tvVersionNumber;

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
        setVersionNumber();
        ((HomeActivity) getActivity()).setTopLayoutVisibility(ISwipe.SHOW_TOP_VIEW);
        return view;
    }

    private void initViews(View mRootView) {
        svParent = mRootView.findViewById(R.id.sv_parent);
        swNotification = (SwitchCompat) mRootView.findViewById(R.id.sw_notification);
        tvChangePassword = (AppCompatTextView) mRootView.findViewById(R.id.tv_change_password);
        tvContactUs = (AppCompatTextView) mRootView.findViewById(R.id.tv_contact_us);
        tvPrivacy = (AppCompatTextView) mRootView.findViewById(R.id.tv_privacy);
        tvTermsOfUse = (AppCompatTextView) mRootView.findViewById(R.id.tv_terms_of_use);
        tvReferEarn = (AppCompatTextView) mRootView.findViewById(R.id.tv_refer_earn);
        tvSignOut = (AppCompatTextView) mRootView.findViewById(R.id.tv_sign_out);
        vwPasswordSeparator = mRootView.findViewById(R.id.vw_password_separator);
        tvVersionNumber = mRootView.findViewById(R.id.tv_version);
        tvChangePassword.setOnClickListener(this);
        tvContactUs.setOnClickListener(this);
        tvPrivacy.setOnClickListener(this);
        tvTermsOfUse.setOnClickListener(this);
        tvReferEarn.setOnClickListener(this);
        tvSignOut.setOnClickListener(this);


        if (SingletonAppCache.getInstance().getUserProfile() != null) {
            swNotification.setChecked(SingletonAppCache.getInstance().getUserProfile().getNotificationEnabled());
        }
        if (SingletonAppCache.getInstance().isSocialLogin()) {
            tvChangePassword.setVisibility(View.GONE);
            vwPasswordSeparator.setVisibility(View.GONE);
        }
        swNotification.setOnCheckedChangeListener(this);
    }

    private void showConfirmationLogoutDialog() {
        String dialogInterfaceMessage = "Are you sure you want to sign out?";

        UIHelper.configureShowConfirmDialog(dialogInterfaceMessage, getActivity(),
                R.string.yes, R.string.btn_cancel, R.string.confirm,
                new MessageDialogConfirm() {
                    @Override
                    public void onPositiveClick(Bundle bundle) {
                        settingsPresenter.logoutUser();
                        processLogout(getActivity());
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
//                UIHelper.getInstance().addFragment(getActivity().getSupportFragmentManager(), R.id.main_container, ChangePasswordFragment.newInstance(), true, ISwipe.FragTagChangePasswordFragment, ISwipe.APP_STACK);
                UIHelper.getInstance().replaceFragment(getActivity().getSupportFragmentManager(), R.id.main_container, ChangePasswordFragment.newInstance(), true, ISwipe.FragTagChangePasswordFragment, ISwipe.APP_STACK);
                break;
            case R.id.tv_contact_us:
                ((HomeActivity) getActivity()).setTopLayoutVisibility(ISwipe.HIDE_TOP_VIEW);
//                UIHelper.getInstance().addFragment(getActivity().getSupportFragmentManager(), R.id.main_container, ContactUsFragment.newInstance(), true, ISwipe.FragTagContactUsFragment, ISwipe.APP_STACK);
                UIHelper.getInstance().replaceFragment(getActivity().getSupportFragmentManager(), R.id.main_container, ContactUsFragment.newInstance(), true, ISwipe.FragTagContactUsFragment, ISwipe.APP_STACK);
                break;
            case R.id.tv_privacy:
                if (SingletonAppCache.getInstance().getAppConfig() == null || SingletonAppCache.getInstance().getAppConfig().getPrivacySecurityUrl() == null ||
                        TextUtils.isEmpty(SingletonAppCache.getInstance().getAppConfig().getPrivacySecurityUrl())) {
                    showMessage(getActivity().getResources().getString(R.string.err_generic));
                }

                String privacySecurityUrl = SingletonAppCache.getInstance().getAppConfig().getPrivacySecurityUrl();


                if (((HomeActivity) getActivity()) != null) {
                    ((HomeActivity) getActivity()).setTopBarTitle(ISwipe.TITLE_PRIVACY_SECURITY);
                }
                launchWebViewFragment(privacySecurityUrl);
                break;
            case R.id.tv_terms_of_use:

                if (SingletonAppCache.getInstance().getAppConfig() == null ||
                        SingletonAppCache.getInstance().getAppConfig().getPrivacySecurityUrl() == null ||
                        TextUtils.isEmpty(SingletonAppCache.getInstance().getAppConfig().getTermsOfUseUrl())) {
                    showMessage(getActivity().getResources().getString(R.string.err_generic));
                }

                String termsOfUseUrl = SingletonAppCache.getInstance().getAppConfig().getTermsOfUseUrl();
                if (((HomeActivity) getActivity()) != null) {
                    ((HomeActivity) getActivity()).setTopBarTitle(ISwipe.TITLE_TERMS_OF_USE);
                }
                launchWebViewFragment(termsOfUseUrl);
                break;
            case R.id.tv_refer_earn:
                if (SingletonAppCache.getInstance().getUserProfile() != null ||
                        SingletonAppCache.getInstance().getUserProfile().getReferralCode() != null) {
                    ((HomeActivity) getActivity()).setTopLayoutVisibility(ISwipe.HIDE_TOP_VIEW);
                    if (((HomeActivity) getActivity()) != null) {
                        ((HomeActivity) getActivity()).setTopBarTitle(getActivity().getResources().getString(R.string.refer_earn).toUpperCase());
                    }
                    UIHelper.getInstance().replaceFragment(getActivity().getSupportFragmentManager(), R.id.main_container, ReferralInviteFragment.newInstance(), true, ISwipe.FragReferralInviteFragment, ISwipe.APP_STACK);


                } else {
                    showMessage(getActivity().getResources().getString(R.string.err_generic));
                }
                break;
            case R.id.tv_sign_out:
                showConfirmationLogoutDialog();
                break;
        }
    }


    private void launchWebViewFragment(String url) {
        WebViewFragment webViewTermsFragment = WebViewFragment.newInstance();
        Bundle termsBundle = new Bundle();
        termsBundle.putString(ISwipe.WEB_URL, url);
        webViewTermsFragment.setArguments(termsBundle);
//        UIHelper.getInstance().addFragment(getActivity().getSupportFragmentManager(), R.id.main_container, webViewTermsFragment, true, ISwipe.FragTagWebViewFragment, ISwipe.APP_STACK);
        UIHelper.getInstance().replaceFragment(getActivity().getSupportFragmentManager(), R.id.main_container, webViewTermsFragment, true, ISwipe.FragTagWebViewFragment, ISwipe.APP_STACK);

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
        if (swNotification.isChecked()) {
            showMessage(getActivity().getResources().getString(R.string.notification_enabled));
        } else {
            showMessage(getActivity().getResources().getString(R.string.notification_disabled));
        }
        SingletonAppCache.getInstance().getUserProfile().setNotificationEnabled(swNotification.isChecked());
    }

    @Override
    public void onErrorNotificationState() {
        swNotification.setChecked(!swNotification.isChecked());
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isEnabled) {
        if (compoundButton.isPressed()) {
            showProgress(getActivity().getResources().getString(R.string.please_wait));
            settingsPresenter.updateNotificationStatus(isEnabled);
        }
    }

    private void setVersionNumber() {
        String versionName = new CommonHelper().getVersionName(getActivity());
        if (!TextUtils.isEmpty(versionName))
            tvVersionNumber.setText("Version - " + new CommonHelper().getVersionName(getActivity()));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (swNotification != null) {
            swNotification.setOnCheckedChangeListener(null);
        }
    }
}
