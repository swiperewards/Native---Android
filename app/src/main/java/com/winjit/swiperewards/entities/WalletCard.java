package com.winjit.swiperewards.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletCard {

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("cardNumber")
    @Expose
    private String cardNumber;
    @SerializedName("expiryMonthMM")
    @Expose
    private String expiryMonthMM;
    @SerializedName("expiryYearYYYY")
    @Expose
    private String expiryYearYYYY;
    @SerializedName("cvv")
    @Expose
    private String cvv;
    @SerializedName("nameOnCard")
    @Expose
    private String nameOnCard;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiryMonthMM() {
        return expiryMonthMM;
    }

    public void setExpiryMonthMM(String expiryMonthMM) {
        this.expiryMonthMM = expiryMonthMM;
    }

    public String getExpiryYearYYYY() {
        return expiryYearYYYY;
    }

    public void setExpiryYearYYYY(String expiryYearYYYY) {
        this.expiryYearYYYY = expiryYearYYYY;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

}
