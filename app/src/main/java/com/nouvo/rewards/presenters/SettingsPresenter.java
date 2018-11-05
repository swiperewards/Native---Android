package com.nouvo.rewards.presenters;

import com.android.volley.VolleyError;
import com.nouvo.rewards.constants.ISwipe;
import com.nouvo.rewards.events.BaseEvent;
import com.nouvo.rewards.events.NotificationStatusEvent;
import com.nouvo.rewards.mvpviews.SettingsView;
import com.nouvo.rewards.services.ServiceController;
import com.nouvo.rewards.web.WebRequestManager;

public class SettingsPresenter extends BasePresenter {
    private SettingsView settingsView;

    public SettingsPresenter(SettingsView settingsView) {
        this.settingsView = settingsView;
    }

    public void updateNotificationStatus(boolean isChecked) {
        try {
            new ServiceController().updateNotificationStatus(settingsView.getViewContext(), isChecked, new WebRequestManager.WebProcessListener<NotificationStatusEvent>() {
                @Override
                public void onWebProcessSuccess(NotificationStatusEvent notificationStatusEvent) {
                    if (notificationStatusEvent.getStatus() == ISwipe.SUCCESS) {
                        settingsView.hideProgress();
                        settingsView.onNotificationStatusChanged(notificationStatusEvent.getNotificationStatus().isEnableNotification());
                    } else {
                        handleReceivedError(settingsView, notificationStatusEvent);
                        settingsView.onErrorNotificationState();
                    }
                }

                @Override
                public void onWebProcessFailed(VolleyError error, Class aClass) {
                    settingsView.onErrorNotificationState();
                    handleWebProcessFailed(settingsView, error);
                }
            });
        } catch (Exception e) {
            handleWebProcessFailed(settingsView, null);
            settingsView.onErrorNotificationState();
        }
    }


    public void logoutUser() {
        try {
            new ServiceController().logoutUser(settingsView.getViewContext(), new WebRequestManager.WebProcessListener<BaseEvent>() {
                @Override
                public void onWebProcessSuccess(BaseEvent baseEvent) {
                    if (baseEvent.getStatus() == ISwipe.SUCCESS) {
                        settingsView.hideProgress();
                    } else {
                        handleReceivedError(settingsView, baseEvent);
                    }
                }

                @Override
                public void onWebProcessFailed(VolleyError error, Class aClass) {
                    handleWebProcessFailed(settingsView, error);
                }
            });
        } catch (Exception e) {
            handleWebProcessFailed(settingsView, null);
        }
    }

}
