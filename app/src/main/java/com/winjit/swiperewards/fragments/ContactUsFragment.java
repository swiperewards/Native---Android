package com.winjit.swiperewards.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.winjit.swiperewards.R;


public class ContactUsFragment extends Fragment implements View.OnClickListener {

    public ContactUsFragment() {
    }

    public static ContactUsFragment newInstance() {
        Bundle args = new Bundle();
        ContactUsFragment fragment = new ContactUsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
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
