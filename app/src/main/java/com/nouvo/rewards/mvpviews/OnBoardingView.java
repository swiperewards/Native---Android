/*
 * Copyright (c) 2015 Newshunt. All rights reserved.
 */

package com.nouvo.rewards.mvpviews;

import com.nouvo.rewards.entities.SessionData;

/**
 * Root class for all MVP views
 */
public interface OnBoardingView extends BaseMVPView{
    void onSuccessfulRegistration(SessionData sessionData);

    void onSuccessfulLogin(SessionData sessionData);


}
