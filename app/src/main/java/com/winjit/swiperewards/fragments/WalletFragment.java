package com.winjit.swiperewards.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.winjit.swiperewards.R;
import com.winjit.swiperewards.activities.HomeActivity;
import com.winjit.swiperewards.constants.ISwipe;


public class WalletFragment extends Fragment implements View.OnClickListener {

    public WalletFragment() {
    }

    public static WalletFragment newInstance() {
        Bundle args = new Bundle();
        WalletFragment fragment = new WalletFragment();
        fragment.setArguments(args);
        return fragment;
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

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_change_password:
                break;
        }
    }
}
