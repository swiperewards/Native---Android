package com.winjit.swiperewards.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.leo.simplearcloader.SimpleArcDialog;
import com.winjit.swiperewards.activities.UniversalBaseActivity;
import com.winjit.swiperewards.helpers.UIHelper;
import com.winjit.swiperewards.mvpviews.BaseMVPView;


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

    public void showLongToast(String message) {
        if (((UniversalBaseActivity) getActivity()) != null) {
            ((UniversalBaseActivity) getActivity()).showLongToast(getActivity(), message);
        }
    }


    public SimpleArcDialog getLoader() {
        if (((UniversalBaseActivity) getActivity()) != null) {
            return ((UniversalBaseActivity) getActivity()).getLoader();
        }
        return null;
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgress();
    }
}