package com.nouvo.rewards.presenters;

import android.support.annotation.Nullable;

import com.android.volley.VolleyError;
import com.nouvo.rewards.events.BaseEvent;
import com.nouvo.rewards.helpers.ErrorCodesHelper;
import com.nouvo.rewards.mvpviews.BaseMVPView;

public class BasePresenter {

    protected void handleWebProcessFailed(BaseMVPView baseMVPView, @Nullable VolleyError error) {
        baseMVPView.hideProgress();
        if (error == null || error.getMessage() == null) {
            baseMVPView.showMessage(ErrorCodesHelper.getErrorStringFromCode(baseMVPView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
        } else {
            baseMVPView.showMessage(error.getMessage());
        }
    }

    protected void handleReceivedError(BaseMVPView baseMVPView, BaseEvent baseEvent) {
        baseMVPView.hideProgress();
        //check if session expired
        baseMVPView.showMessage(ErrorCodesHelper.getErrorStringFromCode(baseMVPView.getViewContext(), baseEvent.getStatus()));
        if (baseEvent.getStatus() == ErrorCodesHelper.INVALID_TOKEN) {
            baseMVPView.onSessionExpired();
        }

    }
}
