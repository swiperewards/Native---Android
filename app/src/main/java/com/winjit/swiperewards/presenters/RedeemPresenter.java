package com.winjit.swiperewards.presenters;

import com.android.volley.VolleyError;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.events.BaseEvent;
import com.winjit.swiperewards.events.GetRedeemModesEvent;
import com.winjit.swiperewards.helpers.ErrorCodesHelper;
import com.winjit.swiperewards.mvpviews.RedeemView;
import com.winjit.swiperewards.services.ServiceController;
import com.winjit.swiperewards.web.WebRequestManager;

import java.util.HashMap;

public class RedeemPresenter {
    private RedeemView redeemView;

    public RedeemPresenter(RedeemView redeemView) {
        this.redeemView = redeemView;
    }

    public void getRedeemModes() {
        try {
            new ServiceController().getRedeemModes(redeemView.getViewContext(), new WebRequestManager.WebProcessListener<GetRedeemModesEvent>() {
                @Override
                public void onWebProcessSuccess(GetRedeemModesEvent getRedeemModesEvent) {
                    redeemView.hideProgress();
                    if (getRedeemModesEvent.getStatus() == ISwipe.SUCCESS) {
                        redeemView.onRedeemModesReceived(getRedeemModesEvent.getRedeemModes());
                    } else {
                        redeemView.showMessage(ErrorCodesHelper.getErrorStringFromCode(redeemView.getViewContext(), getRedeemModesEvent.getStatus()));
                    }
                }

                @Override
                public void onWebProcessFailed(VolleyError error, Class aClass) {
                    redeemView.hideProgress();
                    if (error.getMessage() == null) {
                        redeemView.showMessage(ErrorCodesHelper.getErrorStringFromCode(redeemView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
                    } else {
                        redeemView.showMessage(error.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            redeemView.hideProgress();
            redeemView.showMessage(ErrorCodesHelper.getErrorStringFromCode(redeemView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
        }
    }


    public void raiseRedeemRequest(HashMap<String, Object> map) {
        try {
            new ServiceController().raiseRedeemRequest(redeemView.getViewContext(), map, new WebRequestManager.WebProcessListener<BaseEvent>() {
                @Override
                public void onWebProcessSuccess(BaseEvent baseEvent) {
                    redeemView.hideProgress();
                    if (baseEvent.getStatus() == ISwipe.SUCCESS) {
                        redeemView.onRedeemRequestGenerated();
                    } else {
                        redeemView.showMessage(ErrorCodesHelper.getErrorStringFromCode(redeemView.getViewContext(), baseEvent.getStatus()));
                    }
                }

                @Override
                public void onWebProcessFailed(VolleyError error, Class aClass) {
                    redeemView.hideProgress();
                    if (error.getMessage() == null) {
                        redeemView.showMessage(ErrorCodesHelper.getErrorStringFromCode(redeemView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
                    } else {
                        redeemView.showMessage(error.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            redeemView.hideProgress();
            redeemView.showMessage(ErrorCodesHelper.getErrorStringFromCode(redeemView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
        }
    }


}
