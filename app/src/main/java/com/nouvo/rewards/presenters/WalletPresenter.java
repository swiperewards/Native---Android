package com.nouvo.rewards.presenters;

import com.android.volley.VolleyError;
import com.nouvo.rewards.constants.ISwipe;
import com.nouvo.rewards.entities.WalletCard;
import com.nouvo.rewards.events.BaseEvent;
import com.nouvo.rewards.events.GetWalletCardsEvent;
import com.nouvo.rewards.mvpviews.WalletCardView;
import com.nouvo.rewards.services.ServiceController;
import com.nouvo.rewards.web.WebRequestManager;

public class WalletPresenter extends BasePresenter {
    private WalletCardView walletCardView;

    public WalletPresenter(WalletCardView walletCardView) {
        this.walletCardView = walletCardView;
    }

    public void addWalletCard(WalletCard walletCard) {
        try {
            new ServiceController().addWalletCard(walletCardView.getViewContext(), walletCard, new WebRequestManager.WebProcessListener<BaseEvent>() {
                @Override
                public void onWebProcessSuccess(BaseEvent baseEvent) {
                    if (baseEvent.getStatus() == ISwipe.SUCCESS) {
                        walletCardView.hideProgress();
                        walletCardView.onCardAddedSuccessfully();
                    } else {
                        handleReceivedError(walletCardView, baseEvent);
                    }
                }

                @Override
                public void onWebProcessFailed(VolleyError error, Class aClass) {
                    handleWebProcessFailed(walletCardView, error);
                }
            });
        } catch (Exception e) {
            handleWebProcessFailed(walletCardView, null);
        }
    }


    public void getWalletCards() {
        try {
            new ServiceController().getWalletCards(walletCardView.getViewContext(), new WebRequestManager.WebProcessListener<GetWalletCardsEvent>() {
                @Override
                public void onWebProcessSuccess(GetWalletCardsEvent getWalletCardsEvent) {
                    walletCardView.hideProgress();
                    if (getWalletCardsEvent.getStatus() == ISwipe.SUCCESS && getWalletCardsEvent.getWalletCards() != null) {
                        walletCardView.onWalletCardListReceived(getWalletCardsEvent.getWalletCards());
                    } else {
                        handleReceivedError(walletCardView, getWalletCardsEvent);
                    }
                }

                @Override
                public void onWebProcessFailed(VolleyError error, Class aClass) {
                    handleWebProcessFailed(walletCardView, error);
                }
            });
        } catch (Exception e) {
            handleWebProcessFailed(walletCardView, null);
        }
    }


    public void deleteCard(long cardID) {
        try {
            new ServiceController().deleteCard(walletCardView.getViewContext(), cardID, new WebRequestManager.WebProcessListener<BaseEvent>() {
                @Override
                public void onWebProcessSuccess(BaseEvent baseEvent) {
                    walletCardView.hideProgress();
                    if (baseEvent.getStatus() == ISwipe.SUCCESS) {
                        walletCardView.onCardDeletedSuccessfully();
                    } else {
                        handleReceivedError(walletCardView,baseEvent);
                    }
                }

                @Override
                public void onWebProcessFailed(VolleyError error, Class aClass) {
                    handleWebProcessFailed(walletCardView, error);

                }
            });
        } catch (Exception e) {
            handleWebProcessFailed(walletCardView, null);

        }
    }
}
