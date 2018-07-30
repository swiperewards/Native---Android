package com.winjit.swiperewards.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.winjit.swiperewards.R;


public class BaseActivity extends UniversalBaseActivity {
    boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBackStackListener();
    }

    private void initToolBar() {
        if (getSupportActionBar() != null) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_top);
            TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

            setSupportActionBar(toolbar);
            mTitle.setText(toolbar.getTitle());
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }


    @Override
    public void onBackPressed() {
        doubleBackPressToExit();
    }

    /**
     * Function to provide double back key press event to exit the app.
     */
    private void doubleBackPressToExit() {
        //Checking for fragment count on backstack
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStackImmediate();

        } else if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            showToast(this, getString(R.string.msg_exit));

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
            android.os.Process.killProcess(android.os.Process.myPid());
            return;
        }
    }


    private void setBackStackListener() {
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    configureBackButton(true);
                } else {
                    configureBackButton(false);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void configureBackButton(boolean shouldDisplayBackButton) {
        if (getSupportActionBar() != null) {
            if (shouldDisplayBackButton) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            } else {
                getSupportActionBar().setHomeButtonEnabled(false);
                getSupportActionBar().setDisplayShowHomeEnabled(false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
        }
    }

}
