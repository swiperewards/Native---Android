package com.winjit.swiperewards.activities;

import android.os.Handler;

import com.winjit.swiperewards.R;


public class BaseActivity extends UniversalBaseActivity {
    boolean doubleBackToExitPressedOnce = false;


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

}
