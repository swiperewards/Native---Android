/*
 * Copyright (c) 2015 Newshunt. All rights reserved.
 */

package com.nouvo.rewards.mvpviews;

import com.nouvo.rewards.entities.EventDetails;


public interface EventHistoryView extends BaseMVPView {
    void onEventHistoryReceived(EventDetails[] eventDetails);
}
