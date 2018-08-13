package com.winjit.swiperewards.presenters;

import com.android.volley.VolleyError;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.events.BaseEvent;
import com.winjit.swiperewards.events.GetRedeemModesEvent;
import com.winjit.swiperewards.mvpviews.RedeemView;
import com.winjit.swiperewards.services.ServiceController;
import com.winjit.swiperewards.web.WebRequestManager;

import java.util.HashMap;

public class RedeemPresenter extends BasePresenter {
    private RedeemView redeemView;

    public RedeemPresenter(RedeemView redeemView) {
        this.redeemView = redeemView;
    }

    public void getRedeemModes() {
        try {
            new ServiceController().getRedeemModes(redeemView.getViewContext(), new WebRequestManager.WebProcessListener<GetRedeemModesEvent>() {
                @Override
                public void onWebProcessSuccess(GetRedeemModesEvent getRedeemModesEvent) {
                    if (getRedeemModesEvent.getStatus() == ISwipe.SUCCESS) {
                        redeemView.hideProgress();
                        redeemView.onRedeemModesReceived(getRedeemModesEvent.getRedeemModes());
                    } else {
                        handleReceivedError(redeemView, getRedeemModesEvent);
                    }
                }

                @Override
                public void onWebProcessFailed(VolleyError error, Class aClass) {
                    handleWebProcessFailed(redeemView, error);
                }
            });
        } catch (Exception e) {
            handleWebProcessFailed(redeemView, null);
        }
    }


    public void raiseRedeemRequest(HashMap<String, Object> map) {
        try {
            new ServiceController().raiseRedeemRequest(redeemView.getViewContext(), map, new WebRequestManager.WebProcessListener<BaseEvent>() {
                @Override
                public void onWebProcessSuccess(BaseEvent baseEvent) {
                    if (baseEvent.getStatus() == ISwipe.SUCCESS) {
                        redeemView.hideProgress();
                        redeemView.onRedeemRequestGenerated();
                    } else {
                        handleReceivedError(redeemView, baseEvent);
                    }
                }

                @Override
                public void onWebProcessFailed(VolleyError error, Class aClass) {
                    handleWebProcessFailed(redeemView, error);
                }
            });
        } catch (Exception e) {
            handleWebProcessFailed(redeemView, null);
        }
    }
}