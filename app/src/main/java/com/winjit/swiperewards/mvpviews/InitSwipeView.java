/*
 * Copyright (c) 2015 Newshunt. All rights reserved.
 */

package com.winjit.swiperewards.mvpviews;

import com.winjit.swiperewards.events.InitSwipeEvent;

public interface InitSwipeView extends BaseMVPView {
    void onSwipeInitialized(InitSwipeEvent initSwipeEvent);
    void onSwipeInitializationFailed();
}
