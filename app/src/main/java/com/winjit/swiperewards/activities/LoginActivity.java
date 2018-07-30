package com.winjit.swiperewards.activities;

import android.os.Bundle;
import android.view.View;

import com.winjit.swiperewards.R;
import com.winjit.swiperewards.fragments.LoginFragment;
import com.winjit.swiperewards.helpers.UIHelper;


public class LoginActivity extends UniversalBaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UIHelper.getInstance().addFragment(getSupportFragmentManager(), R.id.login_container, LoginFragment.newInstance(), false, false);
    }


    @Override
    public void onClick(View v) {

    }
}
