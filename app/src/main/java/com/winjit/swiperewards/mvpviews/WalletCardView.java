/*
 * Copyright (c) 2015 Newshunt. All rights reserved.
 */

package com.winjit.swiperewards.mvpviews;

import com.winjit.swiperewards.entities.WalletCard;

public interface WalletCardView extends BaseMVPView {
    void onWalletCardListReceived(WalletCard[] walletCards);

    void onCardAddedSuccessfully();

    void onCardDeletedSuccessfully();
}
