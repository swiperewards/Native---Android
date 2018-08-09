package com.winjit.swiperewards.presenters;

import com.android.volley.VolleyError;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.events.GetEventHistoryEvent;
import com.winjit.swiperewards.helpers.ErrorCodesHelper;
import com.winjit.swiperewards.mvpviews.EventHistoryView;
import com.winjit.swiperewards.services.ServiceController;
import com.winjit.swiperewards.web.WebRequestManager;

public class EventHistoryPresenter {
    private EventHistoryView eventHistoryView;

    public EventHistoryPresenter(EventHistoryView eventHistoryView) {
        this.eventHistoryView = eventHistoryView;
    }

    public void getEventHistory() {
        try {
            new ServiceController().getEventHistory(eventHistoryView.getViewContext(),new WebRequestManager.WebProcessListener<GetEventHistoryEvent>() {
                @Override
                public void onWebProcessSuccess(GetEventHistoryEvent getDealsEvent) {
                    if (getDealsEvent.getEventDetails() != null) {
                        eventHistoryView.hideProgress();
                        eventHistoryView.onEventHistoryReceived(getDealsEvent.getEventDetails());
                    } else if (getDealsEvent.getStatus() != ISwipe.SUCCESS) {
                        eventHistoryView.hideProgress();
                        eventHistoryView.showMessage(ErrorCodesHelper.getErrorStringFromCode(eventHistoryView.getViewContext(), getDealsEvent.getStatus()));
                    }
                }

                @Override
                public void onWebProcessFailed(VolleyError error, Class aClass) {
                    eventHistoryView.hideProgress();
                    eventHistoryView.showMessage(ErrorCodesHelper.getErrorStringFromCode(eventHistoryView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
                }
            });
        } catch (Exception e) {
            eventHistoryView.hideProgress();
            e.printStackTrace();
            eventHistoryView.showMessage(ErrorCodesHelper.getErrorStringFromCode(eventHistoryView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
        }
    }


}
