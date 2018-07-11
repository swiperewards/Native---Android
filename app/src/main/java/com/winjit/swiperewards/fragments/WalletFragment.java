package com.winjit.swiperewards.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.swipe.util.Attributes;
import com.winjit.swiperewards.R;
import com.winjit.swiperewards.activities.HomeActivity;
import com.winjit.swiperewards.adapters.MyCardsAdapter;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.interfaces.AdapterResponseInterface;
import com.winjit.swiperewards.utils.UIHelper;

import java.util.ArrayList;
import java.util.Arrays;


public class WalletFragment extends Fragment implements View.OnClickListener {
    private RecyclerView rvCards;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<String> mDataSet;

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
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);
        initViews(view);
        ((HomeActivity) getActivity()).setTopLayoutVisibility(ISwipe.SHOW_TOP_VIEW);
        return view;
    }

    private void initViews(View mRootView) {
        rvCards = (RecyclerView) mRootView.findViewById(R.id.rv_cards);

        // Item Decorator:
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvCards.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvCards.getContext(),
                linearLayoutManager.getOrientation());
        rvCards.addItemDecoration(dividerItemDecoration);
//        rvCards.setItemAnimator(new FadeInLeftAnimator());

        // Adapter:
        String[] adapterData = new String[]{"Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"};
        mDataSet = new ArrayList<String>(Arrays.asList(adapterData));
        mAdapter = new MyCardsAdapter(getActivity(), mDataSet, new AdapterResponseInterface() {
            @Override
            public void getAdapterResponse(Bundle bundle) {
                if(bundle!=null && bundle.containsKey(ISwipe.IS_ADD_NEW_CARD)){
                    if(bundle.getBoolean(ISwipe.IS_ADD_NEW_CARD)){
                        ((HomeActivity)getActivity()).setTopLayoutVisibility(ISwipe.HIDE_TOP_VIEW);
                        UIHelper.getInstance().replaceFragment(getActivity().getSupportFragmentManager(), R.id.main_container, AddNewCardFragment.newInstance(), true);
                    }
                }
            }
        });
        ((MyCardsAdapter) mAdapter).setMode(Attributes.Mode.Single);
        rvCards.setAdapter(mAdapter);

    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (((HomeActivity) getActivity()) != null) {
            ((HomeActivity) getActivity()).setTopBarTitle(ISwipe.TITLE_WALLET);
        }
    }
}
