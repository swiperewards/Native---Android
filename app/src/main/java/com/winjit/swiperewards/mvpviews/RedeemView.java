/*
 * Copyright (c) 2015 Newshunt. All rights reserved.
 */

package com.winjit.swiperewards.mvpviews;

import com.winjit.swiperewards.entities.TicketType;

public interface RedeemView extends BaseMVPView {
    void onRedeemModesReceived(TicketType[] ticketTypes);

    void onRedeemRequestGenerated();
}
