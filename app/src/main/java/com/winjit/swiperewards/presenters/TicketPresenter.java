package com.winjit.swiperewards.presenters;

import com.android.volley.VolleyError;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.events.BaseEvent;
import com.winjit.swiperewards.events.GetTicketTypeEvent;
import com.winjit.swiperewards.helpers.ErrorCodesHelper;
import com.winjit.swiperewards.mvpviews.TicketView;
import com.winjit.swiperewards.services.ServiceController;
import com.winjit.swiperewards.web.WebRequestManager;

public class TicketPresenter {
    private TicketView ticketView;

    public TicketPresenter(TicketView ticketView) {
        this.ticketView = ticketView;
    }

    public void getTicketTypes() {
        try {
            new ServiceController().getTicketTypes(ticketView.getViewContext(), new WebRequestManager.WebProcessListener<GetTicketTypeEvent>() {
                @Override
                public void onWebProcessSuccess(GetTicketTypeEvent getTicketTypeEvent) {
                    ticketView.hideProgress();
                    if (getTicketTypeEvent.getStatus() == ISwipe.SUCCESS) {
                        ticketView.onTicketTypesReceived(getTicketTypeEvent.getTicketTypes());
                    } else {
                        ticketView.showMessage(ErrorCodesHelper.getErrorStringFromCode(ticketView.getViewContext(), getTicketTypeEvent.getStatus()));
                    }
                }

                @Override
                public void onWebProcessFailed(VolleyError error, Class aClass) {
                    ticketView.hideProgress();
                    if (error.getMessage() == null) {
                        ticketView.showMessage(ErrorCodesHelper.getErrorStringFromCode(ticketView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
                    } else {
                        ticketView.showMessage(error.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            ticketView.hideProgress();
            ticketView.showMessage(ErrorCodesHelper.getErrorStringFromCode(ticketView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
        }
    }


    public void generateTicketRequest(int ticketTypeId, String userCategory, String feedback) {
        try {
            new ServiceController().generateTicket(ticketView.getViewContext(), ticketTypeId, userCategory, feedback, new WebRequestManager.WebProcessListener<BaseEvent>() {
                @Override
                public void onWebProcessSuccess(BaseEvent baseEvent) {
                    ticketView.hideProgress();
                    if (baseEvent.getStatus() == ISwipe.SUCCESS) {
                        ticketView.onTicketRaisedSuccessfully();
                    } else {
                        ticketView.showMessage(ErrorCodesHelper.getErrorStringFromCode(ticketView.getViewContext(), baseEvent.getStatus()));
                    }
                }

                @Override
                public void onWebProcessFailed(VolleyError error, Class aClass) {
                    ticketView.hideProgress();
                    if (error.getMessage() == null) {
                        ticketView.showMessage(ErrorCodesHelper.getErrorStringFromCode(ticketView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
                    } else {
                        ticketView.showMessage(error.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            ticketView.hideProgress();
            ticketView.showMessage(ErrorCodesHelper.getErrorStringFromCode(ticketView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
        }
    }


}
