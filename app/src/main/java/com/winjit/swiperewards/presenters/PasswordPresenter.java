package com.winjit.swiperewards.presenters;

import com.android.volley.VolleyError;
import com.winjit.swiperewards.constants.ISwipe;
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
                    passwordView.showMessage(ErrorCodesHelper.getErrorStringFromCode(passwordView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            passwordView.hideProgress();
            passwordView.showMessage(ErrorCodesHelper.getErrorStringFromCode(passwordView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
        }
    }


}
