package com.winjit.swiperewards.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.winjit.swiperewards.R;
import com.winjit.swiperewards.activities.HomeActivity;
import com.winjit.swiperewards.adapters.DealsAdapter;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.entities.Deals;
import com.winjit.swiperewards.helpers.DateUtil;
import com.winjit.swiperewards.interfaces.AdapterResponseInterface;
import com.winjit.swiperewards.mvpviews.DealsView;
import com.winjit.swiperewards.presenters.DealsPresenter;

import java.util.ArrayList;
import java.util.Arrays;


public class HomeFragment extends BaseFragment implements View.OnClickListener, AdapterResponseInterface, DealsView {

    private RecyclerView rvDeals;
    private DealsPresenter dealsPresenter;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dealsPresenter = new DealsPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initViews(view);
        dealsPresenter.getDeals(ISwipe.DUMMY_CITY);
        return view;
    }

    private void initViews(View mRootView) {
        rvDeals = mRootView.findViewById(R.id.rv_deals);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvDeals.setLayoutManager(linearLayoutManager);
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

    @Override
    public void onDealsReceived(Deals[] dealsList) {
        rvDeals.setAdapter(new DealsAdapter(getActivity(), updateStartEndDateFormat(new ArrayList<Deals>(Arrays.asList(dealsList))), this));
    }

    private ArrayList<Deals> updateStartEndDateFormat(ArrayList<Deals> deals) {

        try {
            for (Deals deal : deals) {
                String startDate = DateUtil.getFormattedDate(deal.getStartDate(), DateUtil.DEAL_API_FORMAT, DateUtil.DEAL_DISPLAY_FORMAT);
                String endDate = DateUtil.getFormattedDate(deal.getEndDate(), DateUtil.DEAL_API_FORMAT, DateUtil.DEAL_DISPLAY_FORMAT);
                deal.setStartDate(startDate);
                deal.setEndDate(endDate);
            }
        } catch (Exception e) {
            return deals;
        }
        return deals;
    }
}
