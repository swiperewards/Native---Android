/*
 * Copyright (c) 2015 Newshunt. All rights reserved.
 */

package com.winjit.swiperewards.mvpviews;

import com.winjit.swiperewards.entities.TicketType;

public interface TicketView extends BaseMVPView {
    void onTicketTypesReceived(TicketType[] ticketTypes);

    void onTicketRaisedSuccessfully();
}
