package com.nouvo.rewards.presenters;

import com.android.volley.VolleyError;
import com.nouvo.rewards.constants.ISwipe;
import com.nouvo.rewards.events.BaseEvent;
import com.nouvo.rewards.events.GetRedeemModesEvent;
import com.nouvo.rewards.mvpviews.RedeemView;
import com.nouvo.rewards.services.ServiceController;
import com.nouvo.rewards.web.WebRequestManager;

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