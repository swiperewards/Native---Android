package com.winjit.swiperewards.presenters;

import android.graphics.Bitmap;

import com.android.volley.VolleyError;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.events.BaseEvent;
import com.winjit.swiperewards.events.InitSwipeEvent;
import com.winjit.swiperewards.helpers.UIHelper;
import com.winjit.swiperewards.mvpviews.InitSwipeView;
import com.winjit.swiperewards.services.ServiceController;
import com.winjit.swiperewards.web.WebRequestManager;

public class InitSwipePresenter extends BasePresenter {
    private InitSwipeView initSwipeView;

    public InitSwipePresenter(InitSwipeView initSwipeView) {
        this.initSwipeView = initSwipeView;
    }

    public void initialiseSwipeRewards(int appVersionCode) {
        try {
            new ServiceController().initialiseSwipeRewards(initSwipeView.getViewContext(), appVersionCode, new WebRequestManager.WebProcessListener<InitSwipeEvent>() {
                @Override
                public void onWebProcessSuccess(InitSwipeEvent initSwipeEvent) {
                    if (initSwipeEvent.getStatus() == ISwipe.SUCCESS) {
                        initSwipeView.hideProgress();
                        initSwipeView.onSwipeInitialized(initSwipeEvent);
                    } else {
                        handleReceivedError(initSwipeView, initSwipeEvent);
                    }
                }

                @Override
                public void onWebProcessFailed(VolleyError error, Class aClass) {
                    handleWebProcessFailed(initSwipeView, error);
                }
            });
        } catch (Exception e) {
            handleWebProcessFailed(initSwipeView, null);
        }
    }


    public void uploadProfilePic(Bitmap profilePic) {
        try {
            new ServiceController().uploadProfilePic(initSwipeView.getViewContext(), new UIHelper().BitMapToString(profilePic), new WebRequestManager.WebProcessListener<BaseEvent>() {
                @Override
                public void onWebProcessSuccess(BaseEvent baseEvent) {
                    if (baseEvent.getStatus() == ISwipe.SUCCESS) {
                        initSwipeView.hideProgress();
                        initSwipeView.showMessage("Profile pic uploaded successfully");
                    } else {
                        handleReceivedError(initSwipeView, baseEvent);
                    }
                }

                @Override
                public void onWebProcessFailed(VolleyError error, Class aClass) {
                    handleWebProcessFailed(initSwipeView, error);
                }
            });
        } catch (Exception e) {
            handleWebProcessFailed(initSwipeView, null);
        }
    }


}
