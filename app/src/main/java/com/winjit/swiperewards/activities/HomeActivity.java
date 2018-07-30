package com.winjit.swiperewards.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.winjit.swiperewards.R;
import com.winjit.swiperewards.appdata.SingletonAppCache;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.entities.UserProfile;
import com.winjit.swiperewards.events.InitSwipeEvent;
import com.winjit.swiperewards.fragments.EventHistoryFragment;
import com.winjit.swiperewards.fragments.HomeFragment;
import com.winjit.swiperewards.fragments.RedeemFragment;
import com.winjit.swiperewards.fragments.SettingsFragment;
import com.winjit.swiperewards.fragments.WalletFragment;
import com.winjit.swiperewards.helpers.UIHelper;
import com.winjit.swiperewards.mvpviews.InitSwipeView;
import com.winjit.swiperewards.presenters.InitSwipePresenter;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends BaseActivity implements InitSwipeView, View.OnClickListener {


    private NestedScrollView svParent;
    private AppCompatTextView tvUserLocation;
    private CircleImageView profileImage;
    private AppCompatTextView tvUserName;
    private AppCompatTextView tvCashBack;
    private AppCompatTextView tvLevel;
    private AppCompatTextView tvLevelDesc;
    private LinearLayout llTop;
    private BottomNavigationViewEx navigation;
    private TextView toolbarTitle;
    private AppCompatSeekBar skLevel;
    private InitSwipePresenter initSwipePresenter;
    private LinearLayout bottomSheet;
    private BottomSheetBehavior sheetBehavior;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        llTop = findViewById(R.id.ll_top);
        skLevel = findViewById(R.id.sk_level);

        svParent = (NestedScrollView) findViewById(R.id.sv_parent);
        llTop = (LinearLayout) findViewById(R.id.ll_top);
        tvUserLocation = (AppCompatTextView) findViewById(R.id.tv_user_location);
        profileImage = (CircleImageView) findViewById(R.id.profile_image);
        tvUserName = (AppCompatTextView) findViewById(R.id.tv_user_name);
        tvCashBack = (AppCompatTextView) findViewById(R.id.tv_cashback);
        tvLevel = (AppCompatTextView) findViewById(R.id.tv_level);
        tvLevelDesc = (AppCompatTextView) findViewById(R.id.tv_level_desc);
        skLevel = (AppCompatSeekBar) findViewById(R.id.sk_level);
        bottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);

//        sheetBehavior = BottomSheetBehavior.from(bottomSheet);

        navigation = (BottomNavigationViewEx) findViewById(R.id.bottom_navigation);
        initToolBar();
        setListeners();
        navigation.enableAnimation(false);
        navigation.enableShiftingMode(false);
        navigation.enableItemShiftingMode(false);
        initSwipe();

    }

    private void initSwipe() {
        initSwipePresenter = new InitSwipePresenter(this);
        showProgress(getResources().getString(R.string.please_wait));
        initSwipePresenter.initialiseSwipeRewards(1);
    }


    private void setListeners() {
        skLevel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        profileImage.setOnClickListener(this);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                setTopLayoutVisibility(item.getItemId());
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        UIHelper.getInstance().replaceFragment(getSupportFragmentManager(), R.id.main_container, HomeFragment.newInstance(), false);
                        return true;
                    case R.id.navigation_wallet:
                        UIHelper.getInstance().replaceFragment(getSupportFragmentManager(), R.id.main_container, WalletFragment.newInstance(), false);
                        return true;
                    case R.id.navigation_redeem:
                        UIHelper.getInstance().replaceFragment(getSupportFragmentManager(), R.id.main_container, RedeemFragment.newInstance(), false);
                        return true;
                    case R.id.navigation_history:
                        UIHelper.getInstance().replaceFragment(getSupportFragmentManager(), R.id.main_container, EventHistoryFragment.newInstance(), false);
                        return true;
                    case R.id.navigation_Settings:
                        UIHelper.getInstance().replaceFragment(getSupportFragmentManager(), R.id.main_container, SettingsFragment.newInstance(), false);
                        return true;

                }
                return false;
            }


        });
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        toolbarTitle.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void setDefaultHomeIndex() {
        View view = navigation.findViewById(R.id.navigation_home);
        view.performClick();
    }


    public void setTopLayoutVisibility(int itemId) {

        switch (itemId) {
            case R.id.navigation_home:
            case R.id.navigation_wallet:
            case R.id.navigation_redeem:
            case R.id.navigation_Settings:
            case ISwipe.SHOW_TOP_VIEW:
                llTop.setVisibility(View.VISIBLE);
                break;
            case R.id.navigation_history:
            case ISwipe.HIDE_TOP_VIEW:
                llTop.setVisibility(View.GONE);
                break;
        }
    }

    public void setTopBarTitle(String title) {
        if (toolbarTitle != null) {
            toolbarTitle.setText(title);
        }
    }


    @Override
    public void onSwipeInitialized(InitSwipeEvent initSwipeEvent) {
        SingletonAppCache.getInstance().setUserProfile(initSwipeEvent.getInitSwipe().getUserProfile());
        SingletonAppCache.getInstance().setAppConfig(initSwipeEvent.getInitSwipe().getAppConfig());
        setUserData(initSwipeEvent.getInitSwipe().getUserProfile());
        setDefaultHomeIndex();

    }

    private void setUserData(UserProfile userProfile) {
        if (!TextUtils.isEmpty(userProfile.getCity())) {
            tvUserLocation.setText(userProfile.getCity());
        }

//        if(!TextUtils.isEmpty(userProfile.getCashbak())){
//            tvCashBack.setText(userProfile.getCashbak());
//        }

        if (!TextUtils.isEmpty(userProfile.getFullName())) {
            tvUserName.setText(userProfile.getFullName());
            }

        if (userProfile.getUserLevel() != null) {
            tvLevel.setText("Level "+userProfile.getUserLevel());
            skLevel.setProgress(userProfile.getUserLevel());
        }

        if (userProfile.getLevelDescription() != null) {
            tvLevelDesc.setText(userProfile.getLevelDescription());
        }

        if (!TextUtils.isEmpty(userProfile.getProfilePicUrl())) {
            UIHelper.getInstance().loadImageOnline(this, userProfile.getProfilePicUrl().replace(" ", "%20"), profileImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        }
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void showMessage(String message) {
        showToast(this, message);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_image:
                showBottomSheetMenu();
                break;
        }
    }

    private void showBottomSheetMenu() {
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}

