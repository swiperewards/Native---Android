package com.nouvo.rewards.appdata;

import com.nouvo.rewards.entities.AppConfig;
import com.nouvo.rewards.entities.UserProfile;

public class SingletonAppCache {

    private AppConfig appConfig;
    private UserProfile userProfile;

    private static SingletonAppCache singletonAppCache;


    public static SingletonAppCache getInstance() {
        if (singletonAppCache == null) {
            singletonAppCache = new SingletonAppCache();
        }
        return singletonAppCache;
    }

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