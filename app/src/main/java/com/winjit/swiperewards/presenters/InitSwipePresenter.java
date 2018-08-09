package com.winjit.swiperewards.presenters;

import com.android.volley.VolleyError;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.events.InitSwipeEvent;
import com.winjit.swiperewards.helpers.ErrorCodesHelper;
import com.winjit.swiperewards.mvpviews.InitSwipeView;
import com.winjit.swiperewards.services.ServiceController;
import com.winjit.swiperewards.web.WebRequestManager;

public class InitSwipePresenter {
    private InitSwipeView initSwipeView;

    public InitSwipePresenter(InitSwipeView initSwipeView) {
        this.initSwipeView = initSwipeView;
    }

    public void initialiseSwipeRewards(int appVersionCode) {
        try {
            new ServiceController().initialiseSwipeRewards(initSwipeView.getViewContext(), appVersionCode, new WebRequestManager.WebProcessListener<InitSwipeEvent>() {
                @Override
                public void onWebProcessSuccess(InitSwipeEvent initSwipeEvent) {
                    initSwipeView.hideProgress();
                    if (initSwipeEvent.getStatus() == ISwipe.SUCCESS) {
                        initSwipeView.onSwipeInitialized(initSwipeEvent);
                    } else {
                        initSwipeView.showMessage(ErrorCodesHelper.getErrorStringFromCode(initSwipeView.getViewContext(), initSwipeEvent.getStatus()));
                    }
                }

                @Override
                public void onWebProcessFailed(VolleyError error, Class aClass) {
                    initSwipeView.hideProgress();
                    initSwipeView.showMessage(ErrorCodesHelper.getErrorStringFromCode(initSwipeView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
                }
            });
        } catch (Exception e) {
            initSwipeView.hideProgress();
            e.printStackTrace();
            initSwipeView.showMessage(ErrorCodesHelper.getErrorStringFromCode(initSwipeView.getViewContext(), ErrorCodesHelper.ERROR_GENERIC));
        }
    }


}
