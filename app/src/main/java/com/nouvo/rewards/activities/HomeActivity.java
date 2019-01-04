package com.nouvo.rewards.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
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

import com.google.gson.Gson;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.myhexaville.smartimagepicker.ImagePicker;
import com.myhexaville.smartimagepicker.OnImagePickedListener;
import com.nouvo.rewards.R;
import com.nouvo.rewards.appdata.SingletonAppCache;
import com.nouvo.rewards.constants.ISwipe;
import com.nouvo.rewards.entities.UserProfile;
import com.nouvo.rewards.events.InitSwipeEvent;
import com.nouvo.rewards.fragments.EventHistoryFragment;
import com.nouvo.rewards.fragments.HomeFragment;
import com.nouvo.rewards.fragments.RedeemFragment;
import com.nouvo.rewards.fragments.SettingsFragment;
import com.nouvo.rewards.fragments.WalletFragment;
import com.nouvo.rewards.helpers.CommonHelper;
import com.nouvo.rewards.helpers.FixScrollingFooterBehavior;
import com.nouvo.rewards.helpers.GPSTracker;
import com.nouvo.rewards.helpers.NetworkUtil;
import com.nouvo.rewards.helpers.PreferenceUtils;
import com.nouvo.rewards.helpers.UIHelper;
import com.nouvo.rewards.interfaces.MessageDialogConfirm;
import com.nouvo.rewards.mvpviews.InitSwipeView;
import com.nouvo.rewards.presenters.InitSwipePresenter;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class HomeActivity extends BaseActivity implements InitSwipeView, View.OnClickListener {

    private AppBarLayout topPanel;
    private AppCompatTextView tvUserLocation;
    private CircleImageView profileImage;
    private AppCompatTextView tvUserName;
    private AppCompatTextView tvCashBack;
    private AppCompatTextView tvLevel;
    private AppCompatTextView tvLevelDesc;
    private LinearLayout llTop;
    private LinearLayout llProfilePic;
    private BottomNavigationViewEx navigation;
    private TextView toolbarTitle;
    private AppCompatSeekBar skLevel;
    private InitSwipePresenter initSwipePresenter;
    private LinearLayout llUserInfo;
    private LinearLayout llCashback;
    private RelativeLayout rlLevelDetails;
    private AppCompatImageView ivChangeProfilePic;
    private RelativeLayout rlProfilePic;
    private ImagePicker imagePicker;
    private View includedContainerMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        llTop = findViewById(R.id.ll_top);
        skLevel = findViewById(R.id.sk_level);

        topPanel = (AppBarLayout) findViewById(R.id.top_panel);
        llTop = (LinearLayout) findViewById(R.id.ll_top);
        tvUserLocation = (AppCompatTextView) findViewById(R.id.tv_user_location);
        profileImage = (CircleImageView) findViewById(R.id.profile_image);
        ivChangeProfilePic = findViewById(R.id.iv_change_profile_pic);
        tvUserName = (AppCompatTextView) findViewById(R.id.tv_user_name);
        tvCashBack = (AppCompatTextView) findViewById(R.id.tv_cashback);
        tvLevel = (AppCompatTextView) findViewById(R.id.tv_level);
        tvLevelDesc = (AppCompatTextView) findViewById(R.id.tv_level_desc);
        skLevel = (AppCompatSeekBar) findViewById(R.id.sk_level);
        llUserInfo = (LinearLayout) findViewById(R.id.ll_user_info);
        llCashback = (LinearLayout) findViewById(R.id.ll_cashback);
        llProfilePic = (LinearLayout) findViewById(R.id.ll_profile_pic);
        rlLevelDetails = (RelativeLayout) findViewById(R.id.rl_level_details);
        rlProfilePic = (RelativeLayout) findViewById(R.id.rl_profile_pic);
        rlProfilePic = (RelativeLayout) findViewById(R.id.rl_profile_pic);
        swPullToRefresh = findViewById(R.id.sw_pull_to_refresh);
        includedContainerMain = findViewById(R.id.included_container_main);
        navigation = (BottomNavigationViewEx) findViewById(R.id.bottom_navigation);


        initToolBar();
        setListeners();

        navigation.enableAnimation(false);
        navigation.enableShiftingMode(false);
        navigation.enableItemShiftingMode(false);


        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(ISwipe.KEY_IS_FIRST_TIME_SOCIAL_LOGIN)) {
            if (getIntent().getExtras().getBoolean(ISwipe.KEY_IS_FIRST_TIME_SOCIAL_LOGIN, false))
                isFirstTimeSocialLoginUser();

        }

        initSwipe();


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initSwipe() {
        initSwipePresenter = new InitSwipePresenter(this);
        // if pull to refresh in progress then don't show progress bar.
        if (!swPullToRefresh.isRefreshing())
            showProgress(getResources().getString(R.string.please_wait));
        initSwipePresenter.initialiseSwipeRewards(new CommonHelper().getVersionCode(this));
    }


    private void setListeners() {
        skLevel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });


        topPanel.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //If topPanel is fully expanded and at default state then only pull to refresh should be enabled.
                swPullToRefresh.setEnabled(shouldPullToRefreshEnabled && verticalOffset == 0);
            }
        });
        swPullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initSwipe();
            }
        });


        rlProfilePic.setOnClickListener(this);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                setTopLayoutVisibility(item.getItemId());
                setTopLayoutItemsVisibility(item.getItemId());
                //To pop the child/sub fragments from created stack
                UIHelper.getInstance().popBackStackByName(getSupportFragmentManager(), ISwipe.APP_STACK);
                //Add to backstack if current fragment is default home fragment
                boolean shouldAddToBackStack = navigation.getSelectedItemId() == R.id.navigation_home;
                String backStackName = shouldAddToBackStack ? ISwipe.HOME_STACK : null;
                shouldAddToBackStack = false;
                backStackName = null;


                //Disable pull to refresh for settings module for other modules it should be enabled.

                setPullToRefreshEnabled(item.getItemId() != R.id.navigation_Settings ? true : false);
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        UIHelper.getInstance().replaceFragment(getSupportFragmentManager(), R.id.main_container, HomeFragment.newInstance(), shouldAddToBackStack, ISwipe.FragTagHomeFragment, backStackName);
                        return true;
                    case R.id.navigation_wallet:
                        UIHelper.getInstance().replaceFragment(getSupportFragmentManager(), R.id.main_container, WalletFragment.newInstance(), shouldAddToBackStack, ISwipe.FragTagWalletFragment, backStackName);
                        return true;
                    case R.id.navigation_redeem:
                        UIHelper.getInstance().replaceFragment(getSupportFragmentManager(), R.id.main_container, RedeemFragment.newInstance(), shouldAddToBackStack, ISwipe.FragTagRedeemFragment, backStackName);
                        return true;
                    case R.id.navigation_history:
                        UIHelper.getInstance().replaceFragment(getSupportFragmentManager(), R.id.main_container, EventHistoryFragment.newInstance(), shouldAddToBackStack, ISwipe.FragTagEventHistoryFragment, backStackName);
                        return true;
                    case R.id.navigation_Settings:
                        UIHelper.getInstance().replaceFragment(getSupportFragmentManager(), R.id.main_container, SettingsFragment.newInstance(), shouldAddToBackStack, ISwipe.FragTagSettingsFragment, backStackName);
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

    public void setNavigationSelectedItem(int navigationItem) {
        View view = navigation.findViewById(navigationItem);
        view.performClick();
//        navigation.setSelectedItemId(navigationItem);
//        //Trick to avoid the reselection of selected item
//        navigation.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
//            @Override
//            public void onNavigationItemReselected(@NonNull MenuItem item) {
//                //To pop the child/sub fragments from created stack
//                UIHelper.getInstance().popBackStackByName(getSupportFragmentManager(), ISwipe.APP_STACK);
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        if (navigation.getCurrentItem() != 0 && getSupportFragmentManager().getBackStackEntryCount() == 0) {
            setNavigationSelectedItem(R.id.navigation_home);
        } else {
            super.onBackPressed();
        }
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
        setScrollBehaviour();
    }

    public void setTopLayoutItemsVisibility(int itemId) {
        getTopView().setExpanded(true, false);
        switch (itemId) {
            case R.id.navigation_Settings:
                int topPadding = rlLevelDetails.getHeight();
                llUserInfo.setVisibility(View.GONE);
                llCashback.setVisibility(View.GONE);
                rlLevelDetails.setVisibility(View.GONE);
                ivChangeProfilePic.setVisibility(View.VISIBLE);
                llProfilePic.setPadding(0, topPadding, 0, 0);
//                tvUserName.setPadding(4, 4, 4, 25);
                break;
            case R.id.navigation_home:
            case R.id.navigation_wallet:
            case R.id.navigation_redeem:
            default:
                llUserInfo.setVisibility(View.VISIBLE);
                llCashback.setVisibility(View.VISIBLE);
                rlLevelDetails.setVisibility(View.VISIBLE);
                ivChangeProfilePic.setVisibility(View.INVISIBLE);
//                tvUserName.setPadding(4, 4, 4, 4);
                llProfilePic.setPadding(0, 0, 0, 0);
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
            try {
                if (initSwipeEvent.getInitSwipe().getUserProfile() != null) {
                    SingletonAppCache.getInstance().setUserProfile(initSwipeEvent.getInitSwipe().getUserProfile());
                    SingletonAppCache.getInstance().setAppConfig(initSwipeEvent.getInitSwipe().getAppConfig());
                    PreferenceUtils.writeString(this, PreferenceUtils.USER_DETAILS, new Gson().toJson(initSwipeEvent.getInitSwipe().getUserProfile()));
                    setUserData(initSwipeEvent.getInitSwipe().getUserProfile());
                    setNavigationSelectedItem(navigation.getSelectedItemId());
                } else {
                    hideProgress();
                    showMessage(getResources().getString(R.string.err_generic));
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }


        }

    }

    @Override
    public void onSwipeInitializationFailed() {
        //Hide loader of pull to refresh if initswipe api call get failed.
        swPullToRefresh.setRefreshing(false);
        String jsonUserObject = PreferenceUtils.readString(this, PreferenceUtils.USER_DETAILS, "");
        if (!TextUtils.isEmpty(jsonUserObject)) {
            UserProfile userProfile = new Gson().fromJson(jsonUserObject, UserProfile.class);
            if (userProfile != null) {
                setUserData(userProfile);
            }
        }
        //Showing current location
        showCurrentCityName();
    }

    @Override
    public void referralCodeAppliedSuccessfully() {
        showMessage(getResources().getString(R.string.referral_applied));
        initSwipe();
    }


    private void showCurrentCityName() {
        GPSTracker gpsTracker = new GPSTracker(this);
        int attempts = 0;
        String cityName;
        do {
            attempts++;

            cityName = gpsTracker.getCityName(this);
        } while (attempts < ISwipe.MAX_GET_CITY_ATTEMPTS && TextUtils.isEmpty(cityName));

        if (!TextUtils.isEmpty(cityName)) {
            updateCityLocation(cityName);
        }
    }

    private void setUserData(UserProfile userProfile) {
        try {
            CommonHelper commonHelper = new CommonHelper();
            if (!TextUtils.isEmpty(userProfile.getCity())) {
                tvUserLocation.setText(commonHelper.capitalize(userProfile.getCity()));
            }

            String balance = String.format("%.2f", userProfile.getWalletBalance());
            tvCashBack.setText("$" + balance);

            if (!TextUtils.isEmpty(userProfile.getFullName())) {
                tvUserName.setText(commonHelper.capitalize(userProfile.getFullName()));
            }

            if (userProfile.getLevelDetails() != null && userProfile.getLevelDetails().getUserLevel() != 0) {
                tvLevel.setText("Level " + userProfile.getLevelDetails().getUserLevel());
                skLevel.setMax(userProfile.getLevelDetails().getLevelMax() - userProfile.getLevelDetails().getLevelMin());
                skLevel.setProgress(userProfile.getLevelDetails().getUserXP() - userProfile.getLevelDetails().getLevelMin());
                tvLevelDesc.setText(userProfile.getLevelDetails().getUserXP() + "/" + userProfile.getLevelDetails().getLevelMax());
            } else {
                int topPadding = rlLevelDetails.getHeight();
                tvLevelDesc.setVisibility(View.GONE);
                tvLevel.setVisibility(View.GONE);
                skLevel.setVisibility(View.GONE);
                rlProfilePic.setPadding(0, topPadding, 0, 0);

            }

            if (!TextUtils.isEmpty(userProfile.getProfilePicUrl())) {
                if (NetworkUtil.getInstance().isConnectedToInternet(this)) {
                    UIHelper.getInstance().loadImageOnline(this,userProfile.getProfilePicUrl().replace(" ","%20"),profileImage,R.mipmap.ic_user_icon,R.mipmap.ic_user_icon);
                } else {
                    UIHelper.getInstance().loadImage(this, userProfile.getProfilePicUrl().replace(" ", "%20"), profileImage, R.mipmap.ic_user_icon, R.mipmap.ic_user_icon);
                }
            }
        } catch (Exception e) {

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
                if (navigation.getCurrentItem() != 4) {
                    navigation.setSelectedItemId(R.id.navigation_Settings);
                } else {
                    launchGallery();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.handleActivityResult(resultCode, requestCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ISwipe.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE_WITH_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED &&
                    grantResults.length > 1 && grantResults[1] == PERMISSION_GRANTED) {
                launchGallery();
            } else {
                showMessage("Permissions must be granted to set the profile picture!");
            }
        }

        if (requestCode == ISwipe.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE_WITHOUT_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED) {
                launchGallery();
            } else {
                showMessage("Permissions must be granted to set the profile picture!");
            }
        } else if (requestCode == ISwipe.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED) {
                launchGallery();
            } else {
                showMessage("Permissions must be granted to set the profile picture!");
            }
        }
    }


    private void launchGallery() {

        if (imagePicker == null) {
            imagePicker = new ImagePicker(this, /* activity non null*/
                    null, /* fragment nullable*/
                    new OnImagePickedListener() {
                        @Override
                        public void onImagePicked(Uri imageUri) {
                            profileImage.setImageURI(imageUri);
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                                Bitmap thumbImage = ThumbnailUtils.extractThumbnail(bitmap, ISwipe.THUMBNAIL_SIZE, ISwipe.THUMBNAIL_SIZE);
                                initSwipePresenter.uploadProfilePic(thumbImage);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
            imagePicker.setWithImageCrop(1, 1);
        }
        imagePicker.choosePicture(true /*show camera intents*/);

    }


    protected void isFirstTimeSocialLoginUser() {
        String dialogInterfaceMessage = getString(R.string.have_referral_code);

        UIHelper.configureReferralDialogWithEditText(dialogInterfaceMessage, this,
                R.string.apply, R.string.btn_cancel, R.string.referral_title,
                new MessageDialogConfirm() {
                    @Override
                    public void onPositiveClick(Bundle bundle) {
                        if (bundle.containsKey(ISwipe.REFERRED_BY)) {
                            String referredBy = bundle.getString(ISwipe.REFERRED_BY);
                            showProgress(getResources().getString(R.string.please_wait));
                            initSwipePresenter.applyReferralCode(referredBy);
                        }
                    }

                    @Override
                    public void onNegativeClick() {
                        return;
                    }
                });

    }


    public void updateCityLocation(String cityName) {
        tvUserLocation.setText(cityName);
    }

    public String getCurrentLocation() {
        return tvUserLocation.getText().toString();
    }

    public AppBarLayout getTopView() {
        return topPanel;
    }

    /**
     * To improve the scrolling behaviour of  parallax effect this function is introduced which sets scrolling behaviour accordingly.
     */
    private void setScrollBehaviour() {
        CoordinatorLayout.LayoutParams params =
                (CoordinatorLayout.LayoutParams) includedContainerMain.getLayoutParams();

        if (llTop.getVisibility() == View.VISIBLE) {
            params.setBehavior(new FixScrollingFooterBehavior());
        } else {
            params.setBehavior(new AppBarLayout.ScrollingViewBehavior());
            includedContainerMain.setPadding(0, 0, 0, 0);
        }

        includedContainerMain.requestLayout();
    }


}