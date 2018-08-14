package com.winjit.swiperewards.presenters;

import com.android.volley.VolleyError;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.events.BaseEvent;
import com.winjit.swiperewards.events.ChangePasswordEvent;
import com.winjit.swiperewards.mvpviews.PasswordView;
import com.winjit.swiperewards.services.ServiceController;
import com.winjit.swiperewards.web.WebRequestManager;

public class PasswordPresenter extends BasePresenter {
    private PasswordView passwordView;

    public PasswordPresenter(PasswordView passwordView) {
        this.passwordView = passwordView;
    }

    public void changePassword(String oldPassword, String newPassword) {
        try {
            new ServiceController().changePassword(passwordView.getViewContext(), oldPassword, newPassword, new WebRequestManager.WebProcessListener<ChangePasswordEvent>() {
                @Override
                public void onWebProcessSuccess(ChangePasswordEvent changePasswordEvent) {
                    if (changePasswordEvent.getStatus() == ISwipe.SUCCESS) {
                        passwordView.hideProgress();
                        passwordView.onPasswordChangedSuccessfully();
                    } else {
                        handleReceivedError(passwordView, changePasswordEvent);
                    }
                }

                @Override
                public void onWebProcessFailed(VolleyError error, Class aClass) {
                    handleWebProcessFailed(passwordView, error);

                }
            });
        } catch (Exception e) {
            handleWebProcessFailed(passwordView, null);
        }
    }


    public void setNewPassword(String emailId, String passCode, String newPassword) {
        try {
            new ServiceController().setNewPassword(passwordView.getViewContext(), emailId, passCode, newPassword, new WebRequestManager.WebProcessListener<ChangePasswordEvent>() {
                @Override
                public void onWebProcessSuccess(ChangePasswordEvent changePasswordEvent) {
                    if (changePasswordEvent.getStatus() == ISwipe.SUCCESS) {
                        passwordView.hideProgress();
                        passwordView.onPasswordChangedSuccessfully();
                    } else {
                        handleReceivedError(passwordView, changePasswordEvent);
                    }
                }

                @Override
                public void onWebProcessFailed(VolleyError error, Class aClass) {
                    handleWebProcessFailed(passwordView, error);
                }
            });
        } catch (Exception e) {
            handleWebProcessFailed(passwordView, null);
        }
    }


    public void forgotPassword(String emailId) {
        try {
            new ServiceController().forgotPassword(passwordView.getViewContext(), emailId, new WebRequestManager.WebProcessListener<BaseEvent>() {
                @Override
                public void onWebProcessSuccess(BaseEvent baseEvent) {
                    passwordView.hideProgress();
                    if (baseEvent.getStatus() == ISwipe.SUCCESS) {
                        passwordView.onPasswordLinkSentSuccessfully();
                    } else {
                        handleReceivedError(passwordView, baseEvent);
                    }
                }

                @Override
                public void onWebProcessFailed(VolleyError error, Class aClass) {
                    handleWebProcessFailed(passwordView, error);
                }
            });
        } catch (Exception e) {
            handleWebProcessFailed(passwordView, null);
        }
    }

}
