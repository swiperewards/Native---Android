package com.nouvo.rewards.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;

import com.nouvo.rewards.R;
import com.nouvo.rewards.helpers.CommonHelper;
import com.nouvo.rewards.helpers.PreferenceUtils;


/**
 * Class to display splash screen.
 */
public class SplashActivity extends Activity {

    private int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        setVersionNumber();

        if (!isTaskRoot()) {
            finish();
            return;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (isValidSession()) {
                    Intent mainIntent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(mainIntent);
                    finish();
                } else {
                    Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);

    }

    private void setVersionNumber() {
        AppCompatTextView tvVersionNumber = findViewById(R.id.tv_version);
        String versionName = new CommonHelper().getVersionName(this);
        if (!TextUtils.isEmpty(versionName))
            tvVersionNumber.setText("Version - " + new CommonHelper().getVersionName(this));
    }

    private boolean isValidSession() {
        return !TextUtils.isEmpty(PreferenceUtils.readString(this, PreferenceUtils.SESSION_TOKEN, ""));
    }
}