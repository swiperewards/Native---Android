package com.winjit.swiperewards.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.winjit.swiperewards.R;
import com.winjit.swiperewards.fragments.LoginFragment;
import com.winjit.swiperewards.helpers.UIHelper;


public class LoginActivity extends UniversalBaseActivity implements View.OnClickListener {
    private TextView tvLoginTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UIHelper.getInstance().addFragment(getSupportFragmentManager(), R.id.login_container, LoginFragment.newInstance(), false, false);
        tvLoginTitle = findViewById(R.id.tv_login_title);
    }


    public void changeHeader(String title){
        if(!TextUtils.isEmpty(title)){
            tvLoginTitle.setText(title);
        }
    }
    @Override
    public void onClick(View v) {

    }
}
