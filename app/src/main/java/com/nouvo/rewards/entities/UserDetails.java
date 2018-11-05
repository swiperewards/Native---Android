package com.nouvo.rewards.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class UserDetails {

    @SerializedName("fullName")
    @Expose
    private String fullName;


    @SerializedName("contactNumber")
    @Expose
    private String contactNumber;


    @SerializedName("emailId")
    @Expose
    private String emailId;


    @SerializedName("password")
    @Expose
    private String password;


    @SerializedName("lat")
    @Expose
    private String latitude;


    @SerializedName("long")
    @Expose
    private String longitude;


    @SerializedName("city")
    @Expose
    private String city;


    @SerializedName("pincode")
    @Expose
    private String pinCode;


    @SerializedName("isSocialLogin")
    @Expose
    private boolean isSocialLogin;


    @SerializedName("socialToken")
    @Expose
    private String socialToken;


    @SerializedName("profilePicUrl")
    @Expose
    private String profilePicUrl;


    @SerializedName("referredBy")
    @Expose
    private String referredBy;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public boolean isSocialLogin() {
        return isSocialLogin;
    }

    public void setSocialLogin(boolean socialLogin) {
        isSocialLogin = socialLogin;
    }

    public String getReferredBy() {
        return referredBy;
    }

    public void setReferredBy(String referredBy) {
        this.referredBy = referredBy;
    }

    public String getSocialToken() {
        return socialToken;
    }

    public void setSocialToken(String socialToken) {
        this.socialToken = socialToken;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }
}
