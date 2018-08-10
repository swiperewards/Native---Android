package com.winjit.swiperewards.presenters;

import com.android.volley.VolleyError;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.events.BaseEvent;
import com.winjit.swiperewards.events.ChangePasswordEvent;
import com.winjit.swiperewards.helpers.ErrorCodesHelper;
import com.winjit.swiperewards.mvpviews.PasswordView;
import com.winjit.swiperewards.services.ServiceController;
import com.winjit.swiperewards.web.WebRequestManager;

public class PasswordPresenter {
    private PasswordView passwordView;

    public PasswordPresenter(PasswordView passwordView) {
        this.passwordView = passwordView;
    }

    public void changePassword(String oldPassword, String newPassword) {
        try {
            new ServiceController().changePassword(passwordView.getViewContext(), oldPassword, newPassword, new WebRequestManager.WebProcessListener<ChangePasswordEvent>() {
                @Override
                public void onWebProcessSuccess(ChangePasswordEvent changePasswordEvent) {
                    passwordView.hideProgress();
                    if (changePasswordEvent.getStatus() == ISwipe.SUCCESS) {
                        passwordView.onPasswordChangedSuccessfully();
                    } else {
                        passwordView.showMessage(ErrorCodesHelper.getErrorStringFromCode(passwordView.getViewContext(), changePasswordEvent.getStatus()));
                    }
                }

                @Override
                public void onWebProcessFailed(VolleyError error, Class aClass) {
                    passwordView.hideProgress();
                    if (error.getMessage() != null) {
                        passwordView.showMessage(ErrorCodesHelper.getErrorStringFromCode(passwordView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
                    } else {
                        passwordView.showMessage(error.getMessage());
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            passwordView.hideProgress();
            passwordView.showMessage(ErrorCodesHelper.getErrorStringFromCode(passwordView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
        }
    }


    public void setNewPassword(String emailId, String passCode, String newPassword) {
        try {
            new ServiceController().setNewPassword(passwordView.getViewContext(), emailId, passCode, newPassword, new WebRequestManager.WebProcessListener<ChangePasswordEvent>() {
                @Override
                public void onWebProcessSuccess(ChangePasswordEvent changePasswordEvent) {
                    passwordView.hideProgress();
                    if (changePasswordEvent.getStatus() == ISwipe.SUCCESS) {
                        passwordView.onPasswordChangedSuccessfully();
                    } else {
                        passwordView.showMessage(ErrorCodesHelper.getErrorStringFromCode(passwordView.getViewContext(), changePasswordEvent.getStatus()));
                    }
                }

                @Override
                public void onWebProcessFailed(VolleyError error, Class aClass) {
                    passwordView.hideProgress();
                    if (error.getMessage() == null) {
                        passwordView.showMessage(ErrorCodesHelper.getErrorStringFromCode(passwordView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
                    } else {
                        passwordView.showMessage(error.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            passwordView.hideProgress();
            passwordView.showMessage(ErrorCodesHelper.getErrorStringFromCode(passwordView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
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
                        passwordView.showMessage(ErrorCodesHelper.getErrorStringFromCode(passwordView.getViewContext(), baseEvent.getStatus()));
                    }
                }

                @Override
                public void onWebProcessFailed(VolleyError error, Class aClass) {
                    passwordView.hideProgress();
                    if (error.getMessage() == null) {
                        passwordView.showMessage(ErrorCodesHelper.getErrorStringFromCode(passwordView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
                    } else {
                        passwordView.showMessage(error.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            passwordView.hideProgress();
            passwordView.showMessage(ErrorCodesHelper.getErrorStringFromCode(passwordView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
        }
    }

}
