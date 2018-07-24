package com.winjit.swiperewards.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.winjit.swiperewards.R;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.fragments.EventHistoryFragment;
import com.winjit.swiperewards.fragments.HomeFragment;
import com.winjit.swiperewards.fragments.RedeemFragment;
import com.winjit.swiperewards.fragments.SettingsFragment;
import com.winjit.swiperewards.fragments.WalletFragment;
import com.winjit.swiperewards.helpers.UIHelper;

public class HomeActivity extends BaseActivity {

    private LinearLayout llTop;
    private BottomNavigationViewEx navigation;
    private TextView toolbarTitle;
    private AppCompatSeekBar skLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        llTop = findViewById(R.id.ll_top);
        skLevel = findViewById(R.id.sk_level);
        navigation = (BottomNavigationViewEx) findViewById(R.id.bottom_navigation);
        initToolBar();
        setListeners();
        navigation.enableAnimation(false);
        navigation.enableShiftingMode(false);
        navigation.enableItemShiftingMode(false);
        setDefaultHomeIndex();

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



}

