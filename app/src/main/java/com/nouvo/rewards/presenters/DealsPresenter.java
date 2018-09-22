package com.nouvo.rewards.presenters;

import com.android.volley.VolleyError;
import com.nouvo.rewards.constants.ISwipe;
import com.nouvo.rewards.events.GetCitiesEvent;
import com.nouvo.rewards.events.GetDealsEvent;
import com.nouvo.rewards.mvpviews.DealsView;
import com.nouvo.rewards.services.ServiceController;
import com.nouvo.rewards.web.WebRequestManager;

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
