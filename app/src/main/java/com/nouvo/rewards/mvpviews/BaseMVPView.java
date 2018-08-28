/*
 * Copyright (c) 2015 Newshunt. All rights reserved.
 */

package com.nouvo.rewards.mvpviews;

import android.content.Context;

/**
 * Root class for all MVP views
 */
public interface BaseMVPView {
    void showProgress(String message);

    Context getViewContext();

    void hideProgress();

    void showMessage(String message);

    void onSessionExpired();

}
