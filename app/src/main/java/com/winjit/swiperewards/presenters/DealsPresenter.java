package com.winjit.swiperewards.presenters;

import com.android.volley.VolleyError;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.events.GetDealsEvent;
import com.winjit.swiperewards.helpers.ErrorCodesHelper;
import com.winjit.swiperewards.mvpviews.DealsView;
import com.winjit.swiperewards.services.ServiceController;
import com.winjit.swiperewards.web.WebRequestManager;

public class DealsPresenter {
    private DealsView dealsView;

    public DealsPresenter(DealsView dealsView) {
        this.dealsView = dealsView;
    }

    public void getDeals(String location) {
        try {
            new ServiceController().getDeals(dealsView.getViewContext(), location,new WebRequestManager.WebProcessListener<GetDealsEvent>() {
                @Override
                public void onWebProcessSuccess(GetDealsEvent getDealsEvent) {
                    if (getDealsEvent.getDeals() != null) {
                        dealsView.onDealsReceived(getDealsEvent.getDeals());
                    } else if (getDealsEvent.getStatus() != ISwipe.SUCCESS) {
                        dealsView.hideProgress();
                        dealsView.showMessage(ErrorCodesHelper.getErrorStringFromCode(dealsView.getViewContext(), getDealsEvent.getStatus()));
                    }
                }

                @Override
                public void onWebProcessFailed(VolleyError error, Class aClass) {
                    dealsView.showMessage(ErrorCodesHelper.getErrorStringFromCode(dealsView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            dealsView.showMessage(ErrorCodesHelper.getErrorStringFromCode(dealsView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
        }
    }


}
