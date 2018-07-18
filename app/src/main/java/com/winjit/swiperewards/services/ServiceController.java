package com.winjit.swiperewards.services;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.winjit.swiperewards.SwipeRewardsApp;
import com.winjit.swiperewards.constants.WebRequestConstants;
import com.winjit.swiperewards.entities.UserDetails;
import com.winjit.swiperewards.events.LoginEvent;
import com.winjit.swiperewards.events.RegisterUserEvent;
import com.winjit.swiperewards.helpers.InputRequestHelper;
import com.winjit.swiperewards.web.WebRequestManager;

import java.util.HashMap;

/**
 * Class to call api methods.
 */
public class ServiceController {

    private HashMap<String, String> generateRequestHeader(String accessToken) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json; charset=utf-8");
        if (TextUtils.isEmpty(accessToken)) {
            return headers;
        }
        headers.put("auth", accessToken);
        return headers;
    }

    public void registerUser(Context context, UserDetails userDetails, WebRequestManager.WebProcessListener<RegisterUserEvent> webProcessListener) {
        new WebRequestManager(webProcessListener).makeRequest(SwipeRewardsApp.getRequestQueue(context), Request.Method.POST,
                WebRequestConstants.WS_REGISTER,
                generateRequestHeader(null),
                new InputRequestHelper().prepareWrappedInputRequest(context,userDetails),
                RegisterUserEvent.class);
    }


    public void loginUser(Context context, String emailId,String password, WebRequestManager.WebProcessListener<LoginEvent> webProcessListener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("emailId", emailId);
        map.put("password", password);

        new WebRequestManager(webProcessListener).makeRequest(SwipeRewardsApp.getRequestQueue(context), Request.Method.POST,
                WebRequestConstants.WS_LOGIN,
                generateRequestHeader(null),
                new InputRequestHelper().prepareWrappedInputRequest(context,map),
                LoginEvent.class);
    }


}