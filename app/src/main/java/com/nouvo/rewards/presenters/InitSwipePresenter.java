package com.nouvo.rewards.presenters;

import android.graphics.Bitmap;

import com.android.volley.VolleyError;
import com.nouvo.rewards.constants.ISwipe;
import com.nouvo.rewards.events.BaseEvent;
import com.nouvo.rewards.events.InitSwipeEvent;
import com.nouvo.rewards.helpers.UIHelper;
import com.nouvo.rewards.mvpviews.InitSwipeView;
import com.nouvo.rewards.services.ServiceController;
import com.nouvo.rewards.web.WebRequestManager;

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
                        initSwipeView.onSwipeInitialized(initSwipeEvent);
                    } else {
                        handleReceivedError(initSwipeView, initSwipeEvent);
                        initSwipeView.onSwipeInitializationFailed();
                    }
                }

                @Override
                public void onWebProcessFailed(VolleyError error, Class aClass) {
                    handleWebProcessFailed(initSwipeView, error);
                    initSwipeView.onSwipeInitializationFailed();
                }
            });
        } catch (Exception e) {
            handleWebProcessFailed(initSwipeView, null);
            initSwipeView.onSwipeInitializationFailed();
        }
    }


    public void uploadProfilePic(Bitmap profilePic) {
        try {
            String bitmapData =new UIHelper().BitMapToString(profilePic);
            new ServiceController().uploadProfilePic(initSwipeView.getViewContext(), bitmapData, new WebRequestManager.WebProcessListener<BaseEvent>() {
                @Override
                public void onWebProcessSuccess(BaseEvent baseEvent) {
                    if (baseEvent.getStatus() == ISwipe.SUCCESS) {
                        String imageUri="";
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



    public void applyReferralCode(String referredBy) {
        try {
            new ServiceController().applyReferralCode(initSwipeView.getViewContext(), referredBy, new WebRequestManager.WebProcessListener<BaseEvent>() {
                @Override
                public void onWebProcessSuccess(BaseEvent baseEvent) {
                    if (baseEvent.getStatus() == ISwipe.SUCCESS) {
//                        initSwipeView.hideProgress();
                        initSwipeView.referralCodeAppliedSuccessfully();
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
