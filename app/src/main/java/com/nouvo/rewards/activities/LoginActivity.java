package com.nouvo.rewards.activities;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.nouvo.rewards.R;
import com.nouvo.rewards.constants.ISwipe;
import com.nouvo.rewards.events.InitSwipeEvent;
import com.nouvo.rewards.fragments.LoginFragment;
import com.nouvo.rewards.helpers.CommonHelper;
import com.nouvo.rewards.helpers.UIHelper;
import com.nouvo.rewards.mvpviews.InitSwipeView;
import com.nouvo.rewards.presenters.InitSwipePresenter;


public class LoginActivity extends UniversalBaseActivity implements InitSwipeView, View.OnClickListener {
    private TextView tvLoginTitle;
    private InitSwipePresenter initSwipePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UIHelper.getInstance().addFragment(getSupportFragmentManager(), R.id.login_container, LoginFragment.newInstance(), false, ISwipe.FragTagLoginFragment, null);
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
    public void onBackPressed() {
        super.onBackPressed();
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

    @Override
    public void onSwipeInitializationFailed() {

    }

    @Override
    public void referralCodeAppliedSuccessfully() {

    }

}
