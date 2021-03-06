package com.nouvo.rewards.entities;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Deals {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("merchantId")
    @Expose
    private String merchantId;
    @SerializedName("shortDescription")
    @Expose
    private String shortDescription;
    @SerializedName("longDescription")
    @Expose
    private String longDescription;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("endDate")
    @Expose
    private String endDate;

    @SerializedName("entityName")
    @Expose
    private String entityName;

    @SerializedName("cashBonus")
    @Expose
    private Double cashBonus;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("createdBy")
    @Expose
    private Integer createdBy;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("isDeleted")
    @Expose
    private Integer isDeleted;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("modifiedDate")
    @Expose
    private String modifiedDate;

    @SerializedName("latitude")
    @Expose
    private String latitude;

    @SerializedName("longitude")
    @Expose
    private String longitude;

    public String getIncreasedPool() {
        return increasedPool;
    }

    public void setIncreasedPool(String increasedPool) {
        this.increasedPool = increasedPool;
    }

    @SerializedName("increasedPool")
    @Expose
    private String increasedPool;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Double getCashBonus() {
        return cashBonus;
    }

    public void setCashBonus(Double cashBonus) {
        this.cashBonus = cashBonus;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
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

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }
}
