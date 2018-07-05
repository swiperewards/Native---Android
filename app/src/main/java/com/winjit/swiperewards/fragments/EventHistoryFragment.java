package com.winjit.swiperewards.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.winjit.swiperewards.R;


public class EventHistoryFragment extends Fragment implements View.OnClickListener {

    public EventHistoryFragment() {
    }

    public static EventHistoryFragment newInstance() {
        Bundle args = new Bundle();
        EventHistoryFragment fragment = new EventHistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_success, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View mRootView) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
