package com.winjit.swiperewards.presenters;

import com.android.volley.VolleyError;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.events.GetEventHistoryEvent;
import com.winjit.swiperewards.mvpviews.EventHistoryView;
import com.winjit.swiperewards.services.ServiceController;
import com.winjit.swiperewards.web.WebRequestManager;

public class EventHistoryPresenter extends BasePresenter {
    private EventHistoryView eventHistoryView;

    public EventHistoryPresenter(EventHistoryView eventHistoryView) {
        this.eventHistoryView = eventHistoryView;
    }

    public void getEventHistory() {
        try {
            new ServiceController().getEventHistory(eventHistoryView.getViewContext(), new WebRequestManager.WebProcessListener<GetEventHistoryEvent>() {
                @Override
                public void onWebProcessSuccess(GetEventHistoryEvent getEventHistoryEvent) {
                    if (getEventHistoryEvent.getEventDetails() != null) {
                        eventHistoryView.hideProgress();
                        eventHistoryView.onEventHistoryReceived(getEventHistoryEvent.getEventDetails());
                    } else if (getEventHistoryEvent.getStatus() != ISwipe.SUCCESS) {
                        handleReceivedError(eventHistoryView, getEventHistoryEvent);
                    }
                }

                @Override
                public void onWebProcessFailed(VolleyError error, Class aClass) {
                    handleWebProcessFailed(eventHistoryView, error);
                }
            });
        } catch (Exception e) {
            handleWebProcessFailed(eventHistoryView, null);
        }
    }


}
