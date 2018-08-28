/*
 * Copyright (c) 2015 Newshunt. All rights reserved.
 */

package com.winjit.swiperewards.mvpviews;

import com.winjit.swiperewards.entities.CityDetails;
import com.winjit.swiperewards.entities.Deals;


public interface DealsView extends BaseMVPView {
    void onDealCityListReceived(CityDetails[] cityDetails,boolean shouldCallGetDealsAPI);

    void onDealsReceived(Deals[] dealsList);
}
