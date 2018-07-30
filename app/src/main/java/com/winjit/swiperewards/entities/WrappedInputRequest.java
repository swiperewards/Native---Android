package com.winjit.swiperewards.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WrappedInputRequest<T> {

    @SerializedName("platform")
    @Expose
    private String platform;

    @SerializedName("deviceId")
    @Expose
    private String deviceId;

    @SerializedName("lat")
    @Expose
    private String latitude;

    @SerializedName("long")
    @Expose
    private String longitude;

    @SerializedName("requestData")
    @Expose
    private T requestData;


    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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

    public T getRequestData() {
        return requestData;
    }

    public void setRequestData(T requestData) {
        this.requestData = requestData;
    }


}
