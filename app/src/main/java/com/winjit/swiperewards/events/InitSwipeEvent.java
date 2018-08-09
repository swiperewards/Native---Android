package com.winjit.swiperewards.events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.winjit.swiperewards.entities.InitSwipe;

public class InitSwipeEvent extends BaseEvent {

    @SerializedName("responseData")
    @Expose
    private InitSwipe initSwipe;

    public InitSwipe getInitSwipe() {
        return initSwipe;
    }

    public void setInitSwipe(InitSwipe initSwipe) {
        this.initSwipe = initSwipe;
    }
}