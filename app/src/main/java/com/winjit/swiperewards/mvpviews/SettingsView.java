/*
 * Copyright (c) 2015 Newshunt. All rights reserved.
 */

package com.winjit.swiperewards.mvpviews;

public interface SettingsView extends BaseMVPView {
    void onNotificationStatusChanged(boolean updatedNotificationStatus);
    void onErrorNotificationState();
}
