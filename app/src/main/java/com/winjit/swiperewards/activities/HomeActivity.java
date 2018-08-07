package com.winjit.swiperewards.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.winjit.swiperewards.R;
import com.winjit.swiperewards.appdata.SingletonAppCache;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.entities.AppConfig;
import com.winjit.swiperewards.entities.UserProfile;
import com.winjit.swiperewards.events.InitSwipeEvent;
import com.winjit.swiperewards.fragments.EventHistoryFragment;
import com.winjit.swiperewards.fragments.HomeFragment;
import com.winjit.swiperewards.fragments.RedeemFragment;
import com.winjit.swiperewards.fragments.SettingsFragment;
import com.winjit.swiperewards.fragments.WalletFragment;
import com.winjit.swiperewards.helpers.UIHelper;
import com.winjit.swiperewards.interfaces.MessageDialogConfirm;
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
    private RelativeLayout bottomSheet;
    private LinearLayout linCamera;
    private LinearLayout linGallery;
    private LinearLayout llUserInfo;
    private LinearLayout llCashback;
    private RelativeLayout rlLevelDetails;
    private AppCompatImageView ivChangeProfilePic;
    private RelativeLayout rlProfilePic;

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
        ivChangeProfilePic = findViewById(R.id.iv_change_profile_pic);
        tvUserName = (AppCompatTextView) findViewById(R.id.tv_user_name);
        tvCashBack = (AppCompatTextView) findViewById(R.id.tv_cashback);
        tvLevel = (AppCompatTextView) findViewById(R.id.tv_level);
        tvLevelDesc = (AppCompatTextView) findViewById(R.id.tv_level_desc);
        skLevel = (AppCompatSeekBar) findViewById(R.id.sk_level);
        bottomSheet = (RelativeLayout) findViewById(R.id.bottom_sheet);
        linCamera = (LinearLayout) findViewById(R.id.lin_camera);
        linGallery = (LinearLayout) findViewById(R.id.lin_gallery);
        llUserInfo = (LinearLayout) findViewById(R.id.ll_user_info);
        llCashback = (LinearLayout) findViewById(R.id.ll_cashback);
        rlLevelDetails = (RelativeLayout) findViewById(R.id.rl_level_details);
        rlProfilePic = (RelativeLayout) findViewById(R.id.rl_profile_pic);


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
        linCamera.setOnClickListener(this);
        linGallery.setOnClickListener(this);
        bottomSheet.setOnClickListener(this);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                setTopLayoutVisibility(item.getItemId());
                setTopLayoutItemsVisibility(item.getItemId());
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

    public void setTopLayoutItemsVisibility(int itemId) {

        switch (itemId) {
            case R.id.navigation_Settings:
                llUserInfo.setVisibility(View.INVISIBLE);
                llCashback.setVisibility(View.INVISIBLE);
                rlLevelDetails.setVisibility(View.GONE);
                ivChangeProfilePic.setVisibility(View.VISIBLE);
                rlProfilePic.setOnClickListener(this);
                tvUserName.setPadding(4, 4, 4, 25);
                break;
            case R.id.navigation_home:
            case R.id.navigation_wallet:
            case R.id.navigation_redeem:
            default:
                llUserInfo.setVisibility(View.VISIBLE);
                llCashback.setVisibility(View.VISIBLE);
                rlLevelDetails.setVisibility(View.VISIBLE);
                ivChangeProfilePic.setVisibility(View.INVISIBLE);
                rlProfilePic.setOnClickListener(null);
                tvUserName.setPadding(4, 4, 4, 4);
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
        if (!checkIfForcedUpdate(initSwipeEvent.getInitSwipe().getAppConfig())) {
            SingletonAppCache.getInstance().setUserProfile(initSwipeEvent.getInitSwipe().getUserProfile());
            SingletonAppCache.getInstance().setAppConfig(initSwipeEvent.getInitSwipe().getAppConfig());
            setUserData(initSwipeEvent.getInitSwipe().getUserProfile());
            setDefaultHomeIndex();
        }

    }

    private boolean checkIfForcedUpdate(AppConfig appConfig) {
        if (appConfig.getForcedUpdate()) {
            String dialogInterfaceMessage = "Please update an app to continue exploring";

            UIHelper.configureShowConfirmDialog(dialogInterfaceMessage, this,
                    R.string.update, R.string.btn_exit, R.string.app_update,
                    new MessageDialogConfirm() {
                        @Override
                        public void onPositiveClick() {
                            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                            }
                        }

                        @Override
                        public void onNegativeClick() {
                            android.os.Process.killProcess(android.os.Process.myPid());
                            return;
                        }
                    });

            return true;
        }
        return false;
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
            tvLevel.setText("Level " + userProfile.getUserLevel());
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
            case R.id.rl_profile_pic:
            case R.id.bottom_sheet:
                showBottomSheetMenu();
                break;
            case R.id.lin_camera:
                showBottomSheetMenu();
                break;
            case R.id.lin_gallery:
                showBottomSheetMenu();
                break;
        }
    }

    private void showBottomSheetMenu() {

        if (bottomSheet.getVisibility() == View.VISIBLE) {
            bottomSheet.setVisibility(View.GONE);
        } else {
            bottomSheet.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}

