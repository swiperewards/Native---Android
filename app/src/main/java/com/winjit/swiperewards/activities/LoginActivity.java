package com.winjit.swiperewards.activities;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.winjit.swiperewards.R;
import com.winjit.swiperewards.events.InitSwipeEvent;
import com.winjit.swiperewards.fragments.LoginFragment;
import com.winjit.swiperewards.helpers.CommonHelper;
import com.winjit.swiperewards.helpers.UIHelper;
import com.winjit.swiperewards.mvpviews.InitSwipeView;
import com.winjit.swiperewards.presenters.InitSwipePresenter;


public class LoginActivity extends UniversalBaseActivity implements InitSwipeView, View.OnClickListener {
    private TextView tvLoginTitle;
    private InitSwipePresenter initSwipePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UIHelper.getInstance().addFragment(getSupportFragmentManager(), R.id.login_container, LoginFragment.newInstance(), false, false);
        tvLoginTitle = findViewById(R.id.tv_login_title);
        initSwipe();
    }

    private void initSwipe() {
        initSwipePresenter = new InitSwipePresenter(this);
        showProgress(getResources().getString(R.string.please_wait));
        initSwipePresenter.initialiseSwipeRewards(new CommonHelper().getVersionCode(this));
    }


    public void changeHeader(String title) {
        if (!TextUtils.isEmpty(title)) {
            tvLoginTitle.setText(title);
        }
    }

    @Override
    public void onClick(View v) {

    }


    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void showMessage(String message) {

    }


    @Override
    public void onSwipeInitialized(InitSwipeEvent initSwipeEvent) {
        checkIfForcedUpdate(initSwipeEvent.getInitSwipe().getAppConfig());
    }
}
