package com.nouvo.rewards.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.nouvo.rewards.activities.LoginActivity;
import com.nouvo.rewards.activities.UniversalBaseActivity;
import com.nouvo.rewards.helpers.PreferenceUtils;
import com.nouvo.rewards.helpers.UIHelper;
import com.nouvo.rewards.mvpviews.BaseMVPView;
import com.nouvo.rewards.mvpviews.FCMView;
import com.nouvo.rewards.presenters.FcmTokenPresenter;


public class BaseFragment extends Fragment implements BaseMVPView, FCMView {


    protected FcmTokenPresenter fcmTokenPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new UIHelper().hideKeyboard(getActivity());
        fcmTokenPresenter = new FcmTokenPresenter(this);

        if (isValidSession() && PreferenceUtils.readBoolean(getContext(), PreferenceUtils.TO_REGISTER_FCM_TOKEN, true) == true) {
            addUpdateFcmToken();
        }
    }

    @Override
    public Context getViewContext() {
        return getActivity();
    }

    @Override
    public void showProgress(String message) {
        if (((UniversalBaseActivity) getActivity()) != null) {
            ((UniversalBaseActivity) getActivity()).showProgress(message);
        }
    }

    @Override
    public void hideProgress() {
        if (((UniversalBaseActivity) getActivity()) != null) {
            ((UniversalBaseActivity) getActivity()).hideProgress();
        }
    }

    @Override
    public void showMessage(String error) {
        hideProgress();
        if (((UniversalBaseActivity) getActivity()) != null) {
            ((UniversalBaseActivity) getActivity()).showToast(getActivity(), error);
        }
    }

    @Override
    public void onSessionExpired() {
        processLogout(getActivity());
    }

    public void showLongToast(String message) {
        if (((UniversalBaseActivity) getActivity()) != null) {
            ((UniversalBaseActivity) getActivity()).showLongToast(getActivity(), message);
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (((UniversalBaseActivity) getActivity()) != null) {
            ((UniversalBaseActivity) getActivity()).hideLoaderOnly();
        }


    }

    protected void processLogout(Context context) {

        //todo dont clear shared prefernce as to save fcm token
        //PreferenceUtils.clearPreferences(context);
        PreferenceUtils.clearValue(context, PreferenceUtils.USER_DETAILS);
        PreferenceUtils.clearValue(context, PreferenceUtils.SESSION_TOKEN);
        PreferenceUtils.clearValue(context, PreferenceUtils.TO_REGISTER_FCM_TOKEN);
//      Picasso.with(getActivity()).invalidate(SingletonAppCache.getInstance().getUserProfile().getProfilePicUrl());
        Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
        startActivity(loginIntent);
        getActivity().finish();
    }

    /**
     * api call to add or update fcm token to server
     */
    private void addUpdateFcmToken() {
        try {
            String fcmToken = PreferenceUtils.readString(getContext(), PreferenceUtils.FCM_TOKEN, "");
            if (fcmToken != null && !fcmToken.isEmpty()) {
                fcmTokenPresenter.fcmTokenAddUpdate(fcmToken);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFcmTokenIdRecieved(String fcmTokenId) {
        if (fcmTokenId.trim().equalsIgnoreCase("")) {
        } else {
            PreferenceUtils.writeBoolean(getContext(), PreferenceUtils.TO_REGISTER_FCM_TOKEN, false);
        }
        // PreferenceUtils.clearValue(getContext(), "Fcmtoken");
    }

    private boolean isValidSession() {
        return !TextUtils.isEmpty(PreferenceUtils.readString(getContext(), PreferenceUtils.SESSION_TOKEN, ""));
    }
}