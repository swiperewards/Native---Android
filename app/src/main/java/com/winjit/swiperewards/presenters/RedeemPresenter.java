package com.winjit.swiperewards.presenters;

import com.android.volley.VolleyError;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.events.GetRedeemModesEvent;
import com.winjit.swiperewards.helpers.ErrorCodesHelper;
import com.winjit.swiperewards.mvpviews.RedeemView;
import com.winjit.swiperewards.services.ServiceController;
import com.winjit.swiperewards.web.WebRequestManager;

public class RedeemPresenter {
    private RedeemView redeemView;

    public RedeemPresenter(RedeemView redeemView) {
        this.redeemView = redeemView;
    }

    public void getRedeemModes() {
        try {
            new ServiceController().getRedeemModes(redeemView.getViewContext(), new WebRequestManager.WebProcessListener<GetRedeemModesEvent>() {
                @Override
                public void onWebProcessSuccess(GetRedeemModesEvent getRedeemModesEvent) {
                    redeemView.hideProgress();
                    if (getRedeemModesEvent.getStatus() == ISwipe.SUCCESS) {
                        redeemView.onRedeemModesReceived(getRedeemModesEvent.getRedeemModes());
                    } else {
                        redeemView.showMessage(ErrorCodesHelper.getErrorStringFromCode(redeemView.getViewContext(), getRedeemModesEvent.getStatus()));
                    }
                }

                @Override
                public void onWebProcessFailed(VolleyError error, Class aClass) {
                    redeemView.hideProgress();
                    redeemView.showMessage(ErrorCodesHelper.getErrorStringFromCode(redeemView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            redeemView.hideProgress();
            redeemView.showMessage(ErrorCodesHelper.getErrorStringFromCode(redeemView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
        }
    }

//
//    public void generateTicketRequest(int ticketTypeId, String userCategory, String feedback) {
//        try {
//            new ServiceController().generateTicket(redeemView.getViewContext(),ticketTypeId,userCategory,feedback, new WebRequestManager.WebProcessListener<BaseEvent>() {
//                @Override
//                public void onWebProcessSuccess(BaseEvent baseEvent) {
//                    redeemView.hideProgress();
//                    if (baseEvent.getStatus() == ISwipe.SUCCESS) {
//                        redeemView.onTicketRaisedSuccessfully();
//                    } else {
//                        redeemView.showMessage(ErrorCodesHelper.getErrorStringFromCode(redeemView.getViewContext(), baseEvent.getStatus()));
//                    }
//                }
//
//                @Override
//                public void onWebProcessFailed(VolleyError error, Class aClass) {
//                    redeemView.hideProgress();
//                    redeemView.showMessage(ErrorCodesHelper.getErrorStringFromCode(redeemView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//            redeemView.hideProgress();
//            redeemView.showMessage(ErrorCodesHelper.getErrorStringFromCode(redeemView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
//        }
//    }
//

}
