package com.nouvo.rewards.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SessionData {

    @SerializedName("userId")
    @Expose
    private String userID;
    @SerializedName("emailId")
    @Expose
    private String emailId;
    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("isNewRecord")
    @Expose
    private int isFirstTimeSocialLogin;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int isFirstTimeSocialLogin() {
        return isFirstTimeSocialLogin;
    }

    public void setFirstTimeSocialLogin(int firstTimeSocialLogin) {
        isFirstTimeSocialLogin = firstTimeSocialLogin;
    }
}
