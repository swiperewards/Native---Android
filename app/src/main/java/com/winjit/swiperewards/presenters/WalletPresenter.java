package com.winjit.swiperewards.presenters;

import com.android.volley.VolleyError;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.entities.WalletCard;
import com.winjit.swiperewards.events.BaseEvent;
import com.winjit.swiperewards.events.GetWalletCardsEvent;
import com.winjit.swiperewards.helpers.ErrorCodesHelper;
import com.winjit.swiperewards.mvpviews.WalletCardView;
import com.winjit.swiperewards.services.ServiceController;
import com.winjit.swiperewards.web.WebRequestManager;

public class WalletPresenter {
    private WalletCardView walletCardView;

    public WalletPresenter(WalletCardView walletCardView) {
        this.walletCardView = walletCardView;
    }

    public void addWalletCard(WalletCard walletCard) {
        try {
            new ServiceController().addWalletCard(walletCardView.getViewContext(), walletCard, new WebRequestManager.WebProcessListener<BaseEvent>() {
                @Override
                public void onWebProcessSuccess(BaseEvent baseEvent) {
                    walletCardView.hideProgress();
                    if (baseEvent.getStatus() == ISwipe.SUCCESS) {
                        walletCardView.onCardAddedSuccessfully();
                    } else {
                        walletCardView.showMessage(ErrorCodesHelper.getErrorStringFromCode(walletCardView.getViewContext(), baseEvent.getStatus()));
                    }
                }

                @Override
                public void onWebProcessFailed(VolleyError error, Class aClass) {
                    walletCardView.hideProgress();
                    if (error.getMessage() == null) {
                        walletCardView.showMessage(ErrorCodesHelper.getErrorStringFromCode(walletCardView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
                    } else {
                        walletCardView.showMessage(error.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            walletCardView.hideProgress();
            walletCardView.showMessage(ErrorCodesHelper.getErrorStringFromCode(walletCardView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
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
                        walletCardView.showMessage(ErrorCodesHelper.getErrorStringFromCode(walletCardView.getViewContext(), getWalletCardsEvent.getStatus()));
                    }
                }

                @Override
                public void onWebProcessFailed(VolleyError error, Class aClass) {
                    walletCardView.hideProgress();
                    if (error.getMessage() == null) {
                        walletCardView.showMessage(ErrorCodesHelper.getErrorStringFromCode(walletCardView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
                    } else {
                        walletCardView.showMessage(error.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            walletCardView.hideProgress();
            walletCardView.showMessage(ErrorCodesHelper.getErrorStringFromCode(walletCardView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
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
                        walletCardView.showMessage(ErrorCodesHelper.getErrorStringFromCode(walletCardView.getViewContext(), baseEvent.getStatus()));
                    }
                }

                @Override
                public void onWebProcessFailed(VolleyError error, Class aClass) {
                    walletCardView.hideProgress();
                    if (error.getMessage() == null) {
                        walletCardView.showMessage(ErrorCodesHelper.getErrorStringFromCode(walletCardView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
                    } else {
                        walletCardView.showMessage(error.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            walletCardView.hideProgress();
            walletCardView.showMessage(ErrorCodesHelper.getErrorStringFromCode(walletCardView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
        }
    }
}
