package com.nouvo.rewards.presenters;

import com.android.volley.VolleyError;
import com.nouvo.rewards.constants.ISwipe;
import com.nouvo.rewards.entities.UserDetails;
import com.nouvo.rewards.events.LoginEvent;
import com.nouvo.rewards.events.RegisterUserEvent;
import com.nouvo.rewards.mvpviews.OnBoardingView;
import com.nouvo.rewards.services.ServiceController;
import com.nouvo.rewards.web.WebRequestManager;

public class OnBoardingPresenter extends BasePresenter {
    private OnBoardingView onBoardingView;

    public OnBoardingPresenter(OnBoardingView onBoardingView) {
        this.onBoardingView = onBoardingView;
    }

    public void registerUser(UserDetails userDetails) {
        final boolean isSocialLogin = userDetails.isSocialLogin();
        try {
            new ServiceController().registerUser(onBoardingView.getViewContext(), userDetails, new WebRequestManager.WebProcessListener<RegisterUserEvent>() {
                @Override
                public void onWebProcessSuccess(RegisterUserEvent registerUserEvent) {
                    if (registerUserEvent.getSessionData() != null) {
                        onBoardingView.hideProgress();
                        if (!isSocialLogin) {
                            onBoardingView.onSuccessfulRegistration(registerUserEvent.getSessionData());
                        } else {
                            onBoardingView.onSuccessfulLogin(registerUserEvent.getSessionData());
                        }
                    } else if (registerUserEvent.getStatus() != ISwipe.SUCCESS) {
                        handleReceivedError(onBoardingView, registerUserEvent);
                    }
                }

                @Override
                public void onWebProcessFailed(VolleyError error, Class aClass) {
                    handleWebProcessFailed(onBoardingView, error);
                }
            });
        } catch (Exception e) {
            handleWebProcessFailed(onBoardingView, null);
        }
    }


    public void loginUser(String emailId, String password) {
        try {
            new ServiceController().loginUser(onBoardingView.getViewContext(), emailId, password, new WebRequestManager.WebProcessListener<LoginEvent>() {
                @Override
                public void onWebProcessSuccess(LoginEvent loginEvent) {
                    if (loginEvent.getStatus() == ISwipe.SUCCESS && loginEvent.getSessionData() != null) {
                        onBoardingView.hideProgress();
                        onBoardingView.onSuccessfulLogin(loginEvent.getSessionData());
                    } else {
                        handleReceivedError(onBoardingView, loginEvent);
                    }
                }

                @Override
                public void onWebProcessFailed(VolleyError error, Class aClass) {
                    handleWebProcessFailed(onBoardingView, error);
                }
            });
        } catch (Exception e) {
            handleWebProcessFailed(onBoardingView, null);
        }
    }


}
