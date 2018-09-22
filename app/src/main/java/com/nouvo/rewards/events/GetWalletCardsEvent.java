package com.nouvo.rewards.events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nouvo.rewards.entities.WalletCard;

public class GetWalletCardsEvent extends BaseEvent {

    @SerializedName("responseData")
    @Expose
    private WalletCard[] walletCards;

    public WalletCard[] getWalletCards() {
        return walletCards;
    }

    public void setWalletCards(WalletCard[] walletCards) {
        this.walletCards = walletCards;
    }
}
