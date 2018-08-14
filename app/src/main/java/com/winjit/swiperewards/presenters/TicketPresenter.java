package com.winjit.swiperewards.presenters;

import com.android.volley.VolleyError;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.events.BaseEvent;
import com.winjit.swiperewards.events.GetTicketTypeEvent;
import com.winjit.swiperewards.mvpviews.TicketView;
import com.winjit.swiperewards.services.ServiceController;
import com.winjit.swiperewards.web.WebRequestManager;

public class TicketPresenter extends BasePresenter {
    private TicketView ticketView;

    public TicketPresenter(TicketView ticketView) {
        this.ticketView = ticketView;
    }

    public void getTicketTypes() {
        try {
            new ServiceController().getTicketTypes(ticketView.getViewContext(), new WebRequestManager.WebProcessListener<GetTicketTypeEvent>() {
                @Override
                public void onWebProcessSuccess(GetTicketTypeEvent getTicketTypeEvent) {
                    if (getTicketTypeEvent.getStatus() == ISwipe.SUCCESS) {
                        ticketView.hideProgress();
                        ticketView.onTicketTypesReceived(getTicketTypeEvent.getTicketTypes());
                    } else {
                        handleReceivedError(ticketView, getTicketTypeEvent);
                    }
                }

                @Override
                public void onWebProcessFailed(VolleyError error, Class aClass) {
                    handleWebProcessFailed(ticketView, error);
                }
            });
        } catch (Exception e) {
            handleWebProcessFailed(ticketView, null);
        }
    }


    public void generateTicketRequest(int ticketTypeId, String userCategory, String feedback) {
        try {
            new ServiceController().generateTicket(ticketView.getViewContext(), ticketTypeId, userCategory, feedback, new WebRequestManager.WebProcessListener<BaseEvent>() {
                @Override
                public void onWebProcessSuccess(BaseEvent baseEvent) {
                    if (baseEvent.getStatus() == ISwipe.SUCCESS) {
                        ticketView.hideProgress();
                        ticketView.onTicketRaisedSuccessfully();
                    } else {
                        handleReceivedError(ticketView, baseEvent);
                    }
                }

                @Override
                public void onWebProcessFailed(VolleyError error, Class aClass) {
                    handleWebProcessFailed(ticketView, error);
                }
            });
        } catch (Exception e) {
            handleWebProcessFailed(ticketView, null);
        }
    }

}
