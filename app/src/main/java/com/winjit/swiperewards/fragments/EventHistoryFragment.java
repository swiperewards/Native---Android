package com.winjit.swiperewards.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.winjit.swiperewards.R;
import com.winjit.swiperewards.activities.HomeActivity;
import com.winjit.swiperewards.adapters.EventHistoryAdapter;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.entities.EventDetails;
import com.winjit.swiperewards.interfaces.AdapterResponseInterface;
import com.winjit.swiperewards.mvpviews.EventHistoryView;
import com.winjit.swiperewards.presenters.EventHistoryPresenter;


public class EventHistoryFragment extends BaseFragment implements AdapterResponseInterface,EventHistoryView {

    private RecyclerView rvEventHistory;
    private EventHistoryPresenter eventHistoryPresenter;


    public static EventHistoryFragment newInstance() {
        Bundle args = new Bundle();
        EventHistoryFragment fragment = new EventHistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventHistoryPresenter = new EventHistoryPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_history, container, false);
        initViews(view);
        eventHistoryPresenter.getEventHistory();
        return view;
    }

    private void initViews(View mRootView) {
        rvEventHistory = mRootView.findViewById(R.id.rv_event_history);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvEventHistory.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvEventHistory.getContext(),
                linearLayoutManager.getOrientation());
        rvEventHistory.addItemDecoration(dividerItemDecoration);

    }



    @Override
    public void getAdapterResponse(Bundle bundle) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (((HomeActivity) getActivity()) != null) {
            ((HomeActivity) getActivity()).setTopBarTitle(ISwipe.TITLE_HISTORY);
        }
    }

    @Override
    public void onEventHistoryReceived(EventDetails[] eventDetails) {
        rvEventHistory.setAdapter(new EventHistoryAdapter(getActivity(), this,eventDetails));
    }
}
