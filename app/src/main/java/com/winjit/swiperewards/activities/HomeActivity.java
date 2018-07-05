package com.winjit.swiperewards.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.winjit.swiperewards.R;
import com.winjit.swiperewards.fragments.EventHistoryFragment;
import com.winjit.swiperewards.fragments.HomeFragment;
import com.winjit.swiperewards.fragments.RegisterFragment;
import com.winjit.swiperewards.fragments.SettingsFragment;
import com.winjit.swiperewards.fragments.SuccessFragment;
import com.winjit.swiperewards.utils.UIHelper;

public class HomeActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationViewEx navigation = (BottomNavigationViewEx) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        UIHelper.getInstance().replaceFragment(getSupportFragmentManager(), R.id.main_container, HomeFragment.newInstance(), false);
                        return true;
                    case R.id.navigation_wallet:
                        UIHelper.getInstance().replaceFragment(getSupportFragmentManager(), R.id.main_container, RegisterFragment.newInstance(), false);
                        return true;
                    case R.id.navigation_redeem:
                        UIHelper.getInstance().replaceFragment(getSupportFragmentManager(), R.id.main_container, SuccessFragment.newInstance(), false);
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


        navigation.enableAnimation(false);
        navigation.enableShiftingMode(false);
        navigation.enableItemShiftingMode(false);
    }


}

