/*
 * Copyright (c) 2015 Newshunt. All rights reserved.
 */

package com.nouvo.rewards.mvpviews;

import com.nouvo.rewards.entities.WalletCard;

public interface WalletCardView extends BaseMVPView {
    void onWalletCardListReceived(WalletCard[] walletCards);

    void onCardAddedSuccessfully();

    void onCardDeletedSuccessfully();
}
