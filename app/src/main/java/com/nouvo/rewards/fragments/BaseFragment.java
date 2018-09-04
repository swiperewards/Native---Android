package com.nouvo.rewards.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.nouvo.rewards.activities.LoginActivity;
import com.nouvo.rewards.activities.UniversalBaseActivity;
import com.nouvo.rewards.appdata.SingletonAppCache;
import com.nouvo.rewards.helpers.PreferenceUtils;
import com.nouvo.rewards.helpers.UIHelper;
import com.nouvo.rewards.mvpviews.BaseMVPView;
import com.squareup.picasso.Picasso;


public class BaseFragment extends Fragment implements BaseMVPView {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new UIHelper().hideKeyboard(getActivity());
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
        hideProgress();
    }

    protected void processLogout(Context context) {
        PreferenceUtils.clearPreferences(context);
        Picasso.with(getActivity()).invalidate(SingletonAppCache.getInstance().getUserProfile().getProfilePicUrl());
        Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
        startActivity(loginIntent);
        getActivity().finish();
    }
}