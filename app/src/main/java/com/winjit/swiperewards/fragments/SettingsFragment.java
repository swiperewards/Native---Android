package com.winjit.swiperewards.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.winjit.swiperewards.R;
import com.winjit.swiperewards.utils.UIHelper;


public class SettingsFragment extends Fragment implements View.OnClickListener {

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
        return view;
    }

    private void initViews(View mRootView) {
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_continue:
                UIHelper.getInstance().popFragment(getActivity().getSupportFragmentManager());
                break;
        }
    }
}
