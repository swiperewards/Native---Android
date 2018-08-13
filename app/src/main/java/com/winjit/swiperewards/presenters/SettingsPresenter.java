package com.winjit.swiperewards.presenters;

import com.android.volley.VolleyError;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.events.NotificationStatusEvent;
import com.winjit.swiperewards.mvpviews.SettingsView;
import com.winjit.swiperewards.services.ServiceController;
import com.winjit.swiperewards.web.WebRequestManager;

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


}
