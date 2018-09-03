/*
 * Copyright (c) 2015 Newshunt. All rights reserved.
 */

package com.nouvo.rewards.mvpviews;

import com.nouvo.rewards.events.InitSwipeEvent;

public interface InitSwipeView extends BaseMVPView {
    void onSwipeInitialized(InitSwipeEvent initSwipeEvent);
    void onSwipeInitializationFailed();
}
