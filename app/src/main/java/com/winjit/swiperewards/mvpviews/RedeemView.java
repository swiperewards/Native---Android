/*
 * Copyright (c) 2015 Newshunt. All rights reserved.
 */

package com.winjit.swiperewards.mvpviews;

import com.winjit.swiperewards.entities.RedeemModes;

public interface RedeemView extends BaseMVPView {
    void onRedeemModesReceived(RedeemModes[] redeemModes);

    void onRedeemRequestGenerated();
}
