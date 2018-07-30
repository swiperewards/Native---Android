/*
 * Copyright (c) 2015 Newshunt. All rights reserved.
 */

package com.winjit.swiperewards.mvpviews;

import com.winjit.swiperewards.entities.EventDetails;


public interface EventHistoryView extends BaseMVPView {
    void onEventHistoryReceived(EventDetails[] eventDetails);
}
