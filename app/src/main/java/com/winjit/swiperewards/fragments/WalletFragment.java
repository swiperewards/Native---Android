package com.winjit.swiperewards.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.swipe.util.Attributes;
import com.winjit.swiperewards.R;
import com.winjit.swiperewards.activities.HomeActivity;
import com.winjit.swiperewards.adapters.WalletCardsAdapter;
import com.winjit.swiperewards.constants.CardType;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.entities.WalletCard;
import com.winjit.swiperewards.helpers.UIHelper;
import com.winjit.swiperewards.interfaces.AdapterResponseInterface;
import com.winjit.swiperewards.interfaces.MessageDialogConfirm;
import com.winjit.swiperewards.mvpviews.WalletCardView;
import com.winjit.swiperewards.presenters.WalletPresenter;

import java.util.ArrayList;
import java.util.Arrays;


public class WalletFragment extends BaseFragment implements View.OnClickListener, WalletCardView {
    private RecyclerView rvCards;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<WalletCard> walletCardArrayList;
    private WalletPresenter walletPresenter;

    public static WalletFragment newInstance() {
        Bundle args = new Bundle();
        WalletFragment fragment = new WalletFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        walletPresenter = new WalletPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);
        initViews(view);
        ((HomeActivity) getActivity()).setTopLayoutVisibility(ISwipe.SHOW_TOP_VIEW);
        showProgress(getActivity().getResources().getString(R.string.please_wait));
        walletPresenter.getWalletCards();
        return view;
    }

    private void initViews(View mRootView) {
        rvCards = (RecyclerView) mRootView.findViewById(R.id.rv_cards);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvCards.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvCards.getContext(),
                linearLayoutManager.getOrientation());
        rvCards.addItemDecoration(dividerItemDecoration);
    }


    private void showConfirmationDeleteDialog(final long cardID) {
        String dialogInterfaceMessage = "Are you sure you want to delete this card?";

        UIHelper.configureShowConfirmDialog(dialogInterfaceMessage, getActivity(),
                R.string.yes, R.string.btn_cancel, R.string.confirm,
                new MessageDialogConfirm() {
                    @Override
                    public void onPositiveClick() {
                        showProgress(getActivity().getResources().getString(R.string.please_wait));
                        walletPresenter.deleteCard(cardID);
                    }

                    @Override
                    public void onNegativeClick() {
                    }
                });

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

    @Override
    public void onWalletCardListReceived(WalletCard[] walletCards) {
        this.walletCardArrayList = new ArrayList<WalletCard>(Arrays.asList(walletCards));
        this.walletCardArrayList = updateCardType(walletCardArrayList);
        this.walletCardArrayList.add(new WalletCard()); //Adding extra empty card to inject the Add new card at bottom of the card list
        mAdapter = new WalletCardsAdapter(getActivity(), this.walletCardArrayList, new AdapterResponseInterface() {
            @Override
            public void getAdapterResponse(Bundle bundle) {
                if (bundle != null && bundle.containsKey(ISwipe.ACTION_NAME)) {
                    String actionName = bundle.getString(ISwipe.ACTION_NAME);
                    switch (actionName) {
                        case ISwipe.ACTION_ADD_NEW_CARD:
                            ((HomeActivity) getActivity()).setTopLayoutVisibility(ISwipe.HIDE_TOP_VIEW);
                            UIHelper.getInstance().replaceFragment(getActivity().getSupportFragmentManager(), R.id.main_container, AddNewCardFragment.newInstance(), true);
                            break;
                        case ISwipe.ACTION_DELETE_CARD:
                            long cardID = bundle.getLong(ISwipe.CARD_ID);
                            showConfirmationDeleteDialog(cardID);
                            break;
                    }
                }
            }
        });
        ((WalletCardsAdapter) mAdapter).setMode(Attributes.Mode.Single);
        rvCards.setAdapter(mAdapter);

    }


    private ArrayList<WalletCard> updateCardType(ArrayList<WalletCard> walletCardArrayList) {

        for (int i = 0; i < walletCardArrayList.size(); i++) {
            CardType type = CardType.forCardNumber(walletCardArrayList.get(i).getCardNumber());
            walletCardArrayList.get(i).setCardType(type);
        }
        return walletCardArrayList;
    }


    @Override
    public void onCardAddedSuccessfully() {
    }

    @Override
    public void onCardDeletedSuccessfully() {
        showLongToast(getActivity().getResources().getString(R.string.delete_card));
        walletPresenter.getWalletCards();
    }


}
