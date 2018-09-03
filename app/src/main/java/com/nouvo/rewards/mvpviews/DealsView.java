/*
 * Copyright (c) 2015 Newshunt. All rights reserved.
 */

package com.nouvo.rewards.mvpviews;

import com.nouvo.rewards.entities.CityDetails;
import com.nouvo.rewards.entities.Deals;


public interface DealsView extends BaseMVPView {
    void onDealCityListReceived(CityDetails[] cityDetails,boolean shouldCallGetDealsAPI);

    void onDealsReceived(Deals[] dealsList);
}
