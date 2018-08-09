package com.winjit.swiperewards.events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.winjit.swiperewards.entities.EventDetails;

public class GetEventHistoryEvent extends BaseEvent {

    @SerializedName("responseData")
    @Expose
    private EventDetails[] eventDetails;

    public EventDetails[] getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(EventDetails[] eventDetails) {
        this.eventDetails = eventDetails;
    }
}