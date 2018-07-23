package com.winjit.swiperewards.presenters;

import com.android.volley.VolleyError;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.events.NotificationStatusEvent;
import com.winjit.swiperewards.helpers.ErrorCodesHelper;
import com.winjit.swiperewards.mvpviews.SettingsView;
import com.winjit.swiperewards.services.ServiceController;
import com.winjit.swiperewards.web.WebRequestManager;

public class SettingsPresenter {
    private SettingsView settingsView;

    public SettingsPresenter(SettingsView settingsView) {
        this.settingsView = settingsView;
    }

    public void updateNotificationStatus(boolean notificationStatus) {
        try {
            new ServiceController().updateNotificationStatus(settingsView.getViewContext(), notificationStatus, new WebRequestManager.WebProcessListener<NotificationStatusEvent>() {
                @Override
                public void onWebProcessSuccess(NotificationStatusEvent notificationStatusEvent) {
                    if (notificationStatusEvent.getStatus() == ISwipe.SUCCESS) {
                        settingsView.onNotificationStatusChanged(notificationStatusEvent.getNotificationStatus().isEnableNotification());
                    } else {
                        settingsView.hideProgress();
                        settingsView.showMessage(ErrorCodesHelper.getErrorStringFromCode(settingsView.getViewContext(), notificationStatusEvent.getStatus()));
                        settingsView.onErrorNotificationState();
                    }
                }

                @Override
                public void onWebProcessFailed(VolleyError error, Class aClass) {
                    settingsView.showMessage(ErrorCodesHelper.getErrorStringFromCode(settingsView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
                    settingsView.onErrorNotificationState();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            settingsView.showMessage(ErrorCodesHelper.getErrorStringFromCode(settingsView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
            settingsView.onErrorNotificationState();
        }
    }


}
