package com.winjit.swiperewards.events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.winjit.swiperewards.entities.CityDetails;

public class GetCitiesEvent extends BaseEvent {

    @SerializedName("responseData")
    @Expose
    private CityDetails[] cityDetails;

    public CityDetails[] getCityDetails() {
        return cityDetails;
    }

    public void setCityDetails(CityDetails[] cityDetails) {
        this.cityDetails = cityDetails;
    }
}