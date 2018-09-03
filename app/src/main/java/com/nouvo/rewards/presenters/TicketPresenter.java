package com.nouvo.rewards.presenters;

import com.android.volley.VolleyError;
import com.nouvo.rewards.constants.ISwipe;
import com.nouvo.rewards.events.BaseEvent;
import com.nouvo.rewards.events.GetTicketTypeEvent;
import com.nouvo.rewards.mvpviews.TicketView;
import com.nouvo.rewards.services.ServiceController;
import com.nouvo.rewards.web.WebRequestManager;

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
