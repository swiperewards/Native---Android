package com.nouvo.rewards.events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nouvo.rewards.entities.Deals;

public class GetDealsEvent extends BaseEvent {

    @SerializedName("responseData")
    @Expose
    private Deals[] deals;

    public Deals[] getDeals() {
        return deals;
    }

    public void setDeals(Deals[] deals) {
        this.deals = deals;
    }
}