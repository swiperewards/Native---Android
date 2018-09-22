package com.nouvo.rewards.entities;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InitSwipe {

    @SerializedName("generalSettings")
    @Expose
    private AppConfig appConfig;


    @SerializedName("userProfile")
    @Expose
    private UserProfile userProfile;


    public AppConfig getAppConfig() {
        return appConfig;
    }

    public void setAppConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
}
