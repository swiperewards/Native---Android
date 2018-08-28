/*
 * Copyright (c) 2015 Newshunt. All rights reserved.
 */

package com.nouvo.rewards.mvpviews;

import com.nouvo.rewards.entities.RedeemModes;

public interface RedeemView extends BaseMVPView {
    void onRedeemModesReceived(RedeemModes[] redeemModes);

    void onRedeemRequestGenerated();
}
