package com.nouvo.rewards.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RedeemModes {

    @SerializedName("modeId")
    @Expose
    private Integer modeId;

    @SerializedName("mode")
    @Expose
    private String mode;

    @SerializedName("modeOptions")
    @Expose
    private RedeemOptions[] modeOptions;

    public Integer getModeId() {
        return modeId;
    }

    public void setModeId(Integer modeId) {
        this.modeId = modeId;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public RedeemOptions[] getModeOptions() {
        return modeOptions;
    }

    public void setModeOptions(RedeemOptions[] modeOptions) {
        this.modeOptions = modeOptions;
    }
}
