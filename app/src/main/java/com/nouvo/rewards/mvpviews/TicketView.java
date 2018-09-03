/*
 * Copyright (c) 2015 Newshunt. All rights reserved.
 */

package com.nouvo.rewards.mvpviews;

import com.nouvo.rewards.entities.TicketType;

public interface TicketView extends BaseMVPView {
    void onTicketTypesReceived(TicketType[] ticketTypes);

    void onTicketRaisedSuccessfully();
}
