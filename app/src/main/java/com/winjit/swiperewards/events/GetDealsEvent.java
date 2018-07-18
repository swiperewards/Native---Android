package com.winjit.swiperewards.events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.winjit.swiperewards.entities.Deals;

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