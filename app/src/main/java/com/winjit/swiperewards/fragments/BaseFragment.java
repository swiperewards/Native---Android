package com.winjit.swiperewards.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

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
        ((UniversalBaseActivity) getActivity()).showProgress(message);
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
        ((UniversalBaseActivity) getActivity()).showToast(getActivity(), error);
    }

    public void showLongToast(String message) {
        ((UniversalBaseActivity) getActivity()).showLongToast(getActivity(), message);
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgress();
    }
}