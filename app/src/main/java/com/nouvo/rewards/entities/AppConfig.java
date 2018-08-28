package com.nouvo.rewards.entities;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppConfig {

    @SerializedName("isForcedUpdate")
    @Expose
    private Boolean isForcedUpdate;
    @SerializedName("playStoreURL")
    @Expose
    private String playStoreURL;
    @SerializedName("privacySecurityUrl")
    @Expose
    private String privacySecurityUrl;
    @SerializedName("termsOfUseUrl")
    @Expose
    private String termsOfUseUrl;
    @SerializedName("currentTimeStamp")
    @Expose
    private String currentTimeStamp;
    @SerializedName("maxRedeemCashBack")
    @Expose
    private Float maxRedeemCashBack;

    public Boolean getForcedUpdate() {
        return isForcedUpdate;
    }

    public void setForcedUpdate(Boolean forcedUpdate) {
        isForcedUpdate = forcedUpdate;
    }

    public String getPlayStoreURL() {
        return playStoreURL;
    }

    public void setPlayStoreURL(String playStoreURL) {
        this.playStoreURL = playStoreURL;
    }

    public String getPrivacySecurityUrl() {
        return privacySecurityUrl;
    }

    public void setPrivacySecurityUrl(String privacySecurityUrl) {
        this.privacySecurityUrl = privacySecurityUrl;
    }

    public String getTermsOfUseUrl() {
        return termsOfUseUrl;
    }

    public void setTermsOfUseUrl(String termsOfUseUrl) {
        this.termsOfUseUrl = termsOfUseUrl;
    }

    public String getCurrentTimeStamp() {
        return currentTimeStamp;
    }

    public void setCurrentTimeStamp(String currentTimeStamp) {
        this.currentTimeStamp = currentTimeStamp;
    }

    public Float getMaxRedeemCashBack() {
        return maxRedeemCashBack;
    }

    public void setMaxRedeemCashBack(Float maxRedeemCashBack) {
        this.maxRedeemCashBack = maxRedeemCashBack;
    }
}
