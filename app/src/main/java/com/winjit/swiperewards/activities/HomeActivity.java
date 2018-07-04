package com.winjit.swiperewards.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.winjit.swiperewards.R;
import com.winjit.swiperewards.fragments.LoginFragment;
import com.winjit.swiperewards.utils.UIHelper;

public class HomeActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    UIHelper.getInstance().addFragment(getSupportFragmentManager(), R.id.main_container, LoginFragment.newInstance(), false, false);
                    return true;
                case R.id.navigation_wallet:
                    return true;
                case R.id.navigation_redeem:
                    return true;
                case R.id.navigation_history:
                    return true;
                case R.id.navigation_Settings:
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationViewEx navigation = (BottomNavigationViewEx) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        navigation.enableAnimation(false);
        navigation.enableShiftingMode(false);
        navigation.enableItemShiftingMode(false);
        navigation.setSelectedItemId(0);
    }

}
