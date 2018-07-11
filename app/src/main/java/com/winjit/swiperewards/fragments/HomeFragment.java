package com.winjit.swiperewards.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.winjit.swiperewards.R;
import com.winjit.swiperewards.activities.HomeActivity;
import com.winjit.swiperewards.adapters.DealsAdapter;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.interfaces.AdapterResponseInterface;


public class HomeFragment extends Fragment implements View.OnClickListener, AdapterResponseInterface {

    private RecyclerView rvDeals;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View mRootView) {
        rvDeals = mRootView.findViewById(R.id.rv_deals);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvDeals.setLayoutManager(linearLayoutManager);
        rvDeals.setAdapter(new DealsAdapter(getActivity(), this));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public void getAdapterResponse(Bundle bundle) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (((HomeActivity) getActivity()) != null) {
            ((HomeActivity) getActivity()).setTopBarTitle(ISwipe.TITLE_HOME);
        }
    }
}
