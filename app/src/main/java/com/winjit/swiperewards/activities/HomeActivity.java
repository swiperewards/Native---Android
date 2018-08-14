package com.winjit.swiperewards.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
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
import com.myhexaville.smartimagepicker.ImagePicker;
import com.myhexaville.smartimagepicker.OnImagePickedListener;
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
import com.winjit.swiperewards.helpers.CommonHelper;
import com.winjit.swiperewards.helpers.UIHelper;
import com.winjit.swiperewards.mvpviews.InitSwipeView;
import com.winjit.swiperewards.presenters.InitSwipePresenter;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class HomeActivity extends BaseActivity implements InitSwipeView, View.OnClickListener {


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        llTop = findViewById(R.id.ll_top);
        skLevel = findViewById(R.id.sk_level);

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
        initSwipePresenter.initialiseSwipeRewards(new CommonHelper().getVersionCode(this));
    }


    private void setListeners() {
        skLevel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });


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

    @Override
    public void onBackPressed() {
        if (navigation.getCurrentItem() != 0 && getSupportFragmentManager().getBackStackEntryCount() == 0) {
            setDefaultHomeIndex();
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
    }

    public void setTopLayoutItemsVisibility(int itemId) {

        switch (itemId) {
            case R.id.navigation_Settings:
                int topPadding = rlLevelDetails.getHeight();
                llUserInfo.setVisibility(View.GONE);
                llCashback.setVisibility(View.GONE);
                rlLevelDetails.setVisibility(View.GONE);
                ivChangeProfilePic.setVisibility(View.VISIBLE);
                rlProfilePic.setOnClickListener(this);
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
                rlProfilePic.setOnClickListener(null);
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
            if (initSwipeEvent.getInitSwipe().getUserProfile() != null) {
                SingletonAppCache.getInstance().setUserProfile(initSwipeEvent.getInitSwipe().getUserProfile());
                SingletonAppCache.getInstance().setAppConfig(initSwipeEvent.getInitSwipe().getAppConfig());
                setUserData(initSwipeEvent.getInitSwipe().getUserProfile());
                setDefaultHomeIndex();
            } else {
                showMessage(getResources().getString(R.string.err_generic));
            }
        }

    }

    private void setUserData(UserProfile userProfile) {
        try {
            if (!TextUtils.isEmpty(userProfile.getCity())) {
                tvUserLocation.setText(userProfile.getCity());
            }

            String balance = String.format("%02d", userProfile.getWalletBalance());
            tvCashBack.setText("$" + balance);

            if (!TextUtils.isEmpty(userProfile.getFullName())) {
                tvUserName.setText(userProfile.getFullName());
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
                UIHelper.getInstance().loadImageOnline(this, userProfile.getProfilePicUrl().replace(" ", "%20"), profileImage, R.mipmap.ic_user_icon, R.mipmap.ic_user_icon);
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
                launchGallery();
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

}