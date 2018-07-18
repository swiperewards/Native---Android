package com.winjit.swiperewards.presenters;

import com.winjit.swiperewards.mvpviews.OnBoardingView;

public class DealsPresenter {
    private OnBoardingView onBoardingView;

    public DealsPresenter(OnBoardingView onBoardingView) {
        this.onBoardingView = onBoardingView;
    }

//    public void getDeals() {
//        try {
//            new ServiceController().getDeals(onBoardingView.getViewContext(), new WebRequestManager.WebProcessListener<RegisterUserEvent>() {
//                @Override
//                public void onWebProcessSuccess(RegisterUserEvent registerUserEvent) {
//                    if (registerUserEvent.getSessionData() != null) {
//                        onBoardingView.onSuccessfulRegistration(registerUserEvent.getSessionData());
//                    } else if (registerUserEvent.getCode() != ISwipe.SUCCESS) {
//                        onBoardingView.hideProgress();
//                        onBoardingView.showError(ErrorCodesHelper.getErrorStringFromCode(onBoardingView.getViewContext(), registerUserEvent.getCode()));
//                    }
//                }
//
//                @Override
//                public void onWebProcessFailed(VolleyError error, Class aClass) {
//                    onBoardingView.showError(ErrorCodesHelper.getErrorStringFromCode(onBoardingView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//            onBoardingView.showError(ErrorCodesHelper.getErrorStringFromCode(onBoardingView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
//        }
//    }



}
