package com.winjit.swiperewards.events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.winjit.swiperewards.entities.TicketType;

public class GetTicketTypeEvent extends BaseEvent {

    @SerializedName("responseData")
    @Expose
    private TicketType[] ticketTypes;

    public TicketType[] getTicketTypes() {
        return ticketTypes;
    }

    public void setTicketTypes(TicketType[] ticketTypes) {
        this.ticketTypes = ticketTypes;
    }
}