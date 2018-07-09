/*
 * Copyright (c) 2015 Newshunt. All rights reserved.
 */

package com.winjit.swiperewards.mvpviews;

import android.content.Context;

/**
 * Root class for all MVP views
 */
public interface BaseMVPView {
    void showProgress(String message);

    Context getViewContext();

    void hideProgress();

    void showError(String message);


}
