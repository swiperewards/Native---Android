package com.nouvo.rewards.events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nouvo.rewards.entities.RedeemModes;

public class GetRedeemModesEvent extends BaseEvent {

    @SerializedName("responseData")
    @Expose
    private RedeemModes[] redeemModes;

    public RedeemModes[] getRedeemModes() {
        return redeemModes;
    }

    public void setRedeemModes(RedeemModes[] redeemModes) {
        this.redeemModes = redeemModes;
    }
}