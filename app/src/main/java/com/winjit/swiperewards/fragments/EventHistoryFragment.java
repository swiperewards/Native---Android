package com.winjit.swiperewards.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.winjit.swiperewards.R;
import com.winjit.swiperewards.adapters.EventHistoryAdapter;
import com.winjit.swiperewards.interfaces.AdapterResponseInterface;


public class EventHistoryFragment extends Fragment implements View.OnClickListener, AdapterResponseInterface {

    private RecyclerView rvEventHistory;
    private EventHistoryAdapter eventHistoryAdapter;

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
        View view = inflater.inflate(R.layout.fragment_event_history, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View mRootView) {
        rvEventHistory = mRootView.findViewById(R.id.rv_event_history);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvEventHistory.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvEventHistory.getContext(),
                linearLayoutManager.getOrientation());
        rvEventHistory.addItemDecoration(dividerItemDecoration);
        rvEventHistory.setAdapter(new EventHistoryAdapter(getActivity(),this));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public void getAdapterResponse(Bundle bundle) {

    }
}
