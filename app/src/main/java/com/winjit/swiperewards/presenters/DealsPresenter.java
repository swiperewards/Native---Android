package com.winjit.swiperewards.presenters;

import com.android.volley.VolleyError;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.events.GetCitiesEvent;
import com.winjit.swiperewards.events.GetDealsEvent;
import com.winjit.swiperewards.mvpviews.DealsView;
import com.winjit.swiperewards.services.ServiceController;
import com.winjit.swiperewards.web.WebRequestManager;

public class DealsPresenter extends BasePresenter {
    private DealsView dealsView;

    public DealsPresenter(DealsView dealsView) {
        this.dealsView = dealsView;
    }

    public void getDeals(String location) {
        try {
            new ServiceController().getDeals(dealsView.getViewContext(), location, new WebRequestManager.WebProcessListener<GetDealsEvent>() {
                @Override
                public void onWebProcessSuccess(GetDealsEvent getDealsEvent) {
                    if (getDealsEvent.getStatus() == ISwipe.SUCCESS && getDealsEvent.getDeals() != null) {
                        dealsView.hideProgress();
                        dealsView.onDealsReceived(getDealsEvent.getDeals());
                    } else  {
                        handleReceivedError(dealsView, getDealsEvent);
                    }
                }

                @Override
                public void onWebProcessFailed(VolleyError error, Class aClass) {
                    handleWebProcessFailed(dealsView, error);
                }
            });
        } catch (Exception e) {
            handleWebProcessFailed(dealsView, null);
        }
    }



    public void getCities() {
        try {
            new ServiceController().getCityList(dealsView.getViewContext(), new WebRequestManager.WebProcessListener<GetCitiesEvent>() {
                @Override
                public void onWebProcessSuccess(GetCitiesEvent getCitiesEvent) {
                    if (getCitiesEvent.getStatus() == ISwipe.SUCCESS) {
                        dealsView.onDealCityListReceived(getCitiesEvent.getCityDetails(),true);
                    } else {
                        handleReceivedError(dealsView, getCitiesEvent);
                        dealsView.onDealCityListReceived(null,false);
                    }
                }

                @Override
                public void onWebProcessFailed(VolleyError error, Class aClass) {
                    handleWebProcessFailed(dealsView, error);
                    dealsView.onDealCityListReceived(null,false);
                }
            });
        } catch (Exception e) {
            handleWebProcessFailed(dealsView, null);
            dealsView.onDealCityListReceived(null,false);
        }
    }


    public void getDealsWithPagination(String location,int pageNumber, int pageSize) {
        try {
            new ServiceController().getDealsWithPagination(dealsView.getViewContext(), location,pageNumber,pageSize, new WebRequestManager.WebProcessListener<GetDealsEvent>() {
                @Override
                public void onWebProcessSuccess(GetDealsEvent getDealsEvent) {
                    if (getDealsEvent.getStatus() == ISwipe.SUCCESS && getDealsEvent.getDeals() != null) {
                        dealsView.hideProgress();
                        dealsView.onDealsReceived(getDealsEvent.getDeals());
                    } else  {
                        handleReceivedError(dealsView, getDealsEvent);
                    }
                }

                @Override
                public void onWebProcessFailed(VolleyError error, Class aClass) {
                    handleWebProcessFailed(dealsView, error);
                }
            });
        } catch (Exception e) {
            handleWebProcessFailed(dealsView, null);
        }
    }

}
