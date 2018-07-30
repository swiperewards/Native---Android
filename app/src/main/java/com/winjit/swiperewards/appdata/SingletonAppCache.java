package com.winjit.swiperewards.appdata;

import com.winjit.swiperewards.entities.AppConfig;
import com.winjit.swiperewards.entities.UserProfile;

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