package com.nouvo.rewards.presenters;

import com.android.volley.VolleyError;
import com.nouvo.rewards.events.BaseEvent;
import com.nouvo.rewards.mvpviews.FCMView;
import com.nouvo.rewards.services.ServiceController;
import com.nouvo.rewards.web.WebRequestManager;

/**
 * This is used for sending Fcm token to server
 *
 * @author: ${NikitaW}
 * @since:11/10/18
 */
public class FcmTokenPresenter extends BasePresenter {

    private FCMView fcmView;

    public FcmTokenPresenter(FCMView fcmView) {
        this.fcmView = fcmView;
    }

    public void fcmTokenAddUpdate(final String fcmToken) {
        try {
            new ServiceController().addUpdateFcmToken(fcmView.getViewContext(), fcmToken, new WebRequestManager.WebProcessListener<BaseEvent>() {
                @Override
                public void onWebProcessSuccess(BaseEvent baseEvent) {
                    fcmView.hideProgress();
                    baseEvent.getMessage();
                    fcmView.onFcmTokenIdRecieved(fcmToken);
                }

                @Override
                public void onWebProcessFailed(VolleyError error, Class aClass) {
                    handleWebProcessFailed(fcmView, error);

                }
            });
        } catch (Exception e) {
            handleWebProcessFailed(fcmView, null);
        }
    }

}
