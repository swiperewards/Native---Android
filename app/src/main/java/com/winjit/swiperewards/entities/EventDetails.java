package com.winjit.swiperewards.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventDetails {

    @SerializedName("eventId")
    @Expose
    private Integer eventId;
    @SerializedName("eventType")
    @Expose
    private String eventType;
    @SerializedName("notificationDate")
    @Expose
    private String notificationDate;
    @SerializedName("notificationDetails")
    @Expose
    private String notificationDetails;
    @SerializedName("transactionAmount")
    @Expose
    private Float transactionAmount;
    @SerializedName("isCredit")
    @Expose
    private Integer isCredit;

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;
    }

    public String getNotificationDetails() {
        return notificationDetails;
    }

    public void setNotificationDetails(String notificationDetails) {
        this.notificationDetails = notificationDetails;
    }

    public Float getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Float transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Integer getIsCredit() {
        return isCredit;
    }

    public void setIsCredit(Integer isCredit) {
        this.isCredit = isCredit;
    }
}
