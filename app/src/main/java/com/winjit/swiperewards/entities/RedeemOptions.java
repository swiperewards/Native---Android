package com.winjit.swiperewards.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RedeemOptions {

    @SerializedName("modeSubId")
    @Expose
    private Integer modeSubId;

    @SerializedName("name")
    @Expose
    private String name;

    public Integer getModeSubId() {
        return modeSubId;
    }

    public void setModeSubId(Integer modeSubId) {
        this.modeSubId = modeSubId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
