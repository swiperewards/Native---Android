package com.winjit.swiperewards.presenters;

import com.android.volley.VolleyError;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.entities.UserDetails;
import com.winjit.swiperewards.events.LoginEvent;
import com.winjit.swiperewards.events.RegisterUserEvent;
import com.winjit.swiperewards.mvpviews.OnBoardingView;
import com.winjit.swiperewards.services.ServiceController;
import com.winjit.swiperewards.helpers.ErrorCodesHelper;
import com.winjit.swiperewards.web.WebRequestManager;

public class OnBoardingPresenter {
    private OnBoardingView onBoardingView;

    public OnBoardingPresenter(OnBoardingView onBoardingView) {
        this.onBoardingView = onBoardingView;
    }

    public void registerUser(UserDetails userDetails) {
        try {
            new ServiceController().registerUser(onBoardingView.getViewContext(), userDetails, new WebRequestManager.WebProcessListener<RegisterUserEvent>() {
                @Override
                public void onWebProcessSuccess(RegisterUserEvent registerUserEvent) {
                    if (registerUserEvent.getSessionData() != null) {
                        onBoardingView.hideProgress();
                        onBoardingView.onSuccessfulRegistration(registerUserEvent.getSessionData());
                    } else if (registerUserEvent.getStatus() != ISwipe.SUCCESS) {
                        onBoardingView.hideProgress();
                        onBoardingView.showMessage(ErrorCodesHelper.getErrorStringFromCode(onBoardingView.getViewContext(), registerUserEvent.getStatus()));
                    }
                }

                @Override
                public void onWebProcessFailed(VolleyError error, Class aClass) {
                    onBoardingView.hideProgress();
                    onBoardingView.showMessage(ErrorCodesHelper.getErrorStringFromCode(onBoardingView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            onBoardingView.hideProgress();
            onBoardingView.showMessage(ErrorCodesHelper.getErrorStringFromCode(onBoardingView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
        }
    }


    public void loginUser(String emailId, String password) {
        try {
            new ServiceController().loginUser(onBoardingView.getViewContext(), emailId, password, new WebRequestManager.WebProcessListener<LoginEvent>() {
                @Override
                public void onWebProcessSuccess(LoginEvent loginEvent) {
                    if (loginEvent.getSessionData() != null) {
                        onBoardingView.hideProgress();
                        onBoardingView.onSuccessfulLogin(loginEvent.getSessionData());
                    } else if (loginEvent.getStatus() != ISwipe.SUCCESS) {
                        onBoardingView.showMessage(ErrorCodesHelper.getErrorStringFromCode(onBoardingView.getViewContext(), loginEvent.getStatus()));
                        onBoardingView.hideProgress();
                    }
                }

                @Override
                public void onWebProcessFailed(VolleyError error, Class aClass) {
                    onBoardingView.hideProgress();
                    onBoardingView.showMessage(ErrorCodesHelper.getErrorStringFromCode(onBoardingView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            onBoardingView.hideProgress();
            onBoardingView.showMessage(ErrorCodesHelper.getErrorStringFromCode(onBoardingView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
        }
    }
}