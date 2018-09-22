package com.nouvo.rewards.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfile {

    @SerializedName("userId")
    @Expose
    private Long userId;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("level")
    @Expose
    private LevelDetails levelDetails;

    @SerializedName("isNotificationEnabled")
    @Expose
    private Boolean isNotificationEnabled;
    @SerializedName("profilePicUrl")
    @Expose
    private String profilePicUrl;


    @SerializedName("walletBalance")
    @Expose
    private float walletBalance;


    @SerializedName("userReferralCode")
    @Expose
    private String referralCode;



    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Boolean getNotificationEnabled() {
        return isNotificationEnabled;
    }

    public void setNotificationEnabled(Boolean notificationEnabled) {
        isNotificationEnabled = notificationEnabled;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public float getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(float walletBalance) {
        this.walletBalance = walletBalance;
    }

    public LevelDetails getLevelDetails() {
        return levelDetails;
    }

    public void setLevelDetails(LevelDetails levelDetails) {
        this.levelDetails = levelDetails;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }
}
