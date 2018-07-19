package com.winjit.swiperewards.services;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.winjit.swiperewards.SwipeRewardsApp;
import com.winjit.swiperewards.constants.WebRequestConstants;
import com.winjit.swiperewards.entities.UserDetails;
import com.winjit.swiperewards.entities.WalletCard;
import com.winjit.swiperewards.events.BaseEvent;
import com.winjit.swiperewards.events.ChangePasswordEvent;
import com.winjit.swiperewards.events.GetDealsEvent;
import com.winjit.swiperewards.events.GetWalletCardsEvent;
import com.winjit.swiperewards.events.LoginEvent;
import com.winjit.swiperewards.events.RegisterUserEvent;
import com.winjit.swiperewards.helpers.InputRequestHelper;
import com.winjit.swiperewards.helpers.PreferenceUtils;
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

    private String getSessionToken(Context context) {
        return PreferenceUtils.readString(context, PreferenceUtils.SESSION_TOKEN, "");
    }

    public void registerUser(Context context, UserDetails userDetails, WebRequestManager.WebProcessListener<RegisterUserEvent> webProcessListener) {
        new WebRequestManager(webProcessListener).makeRequest(SwipeRewardsApp.getRequestQueue(context), Request.Method.POST,
                WebRequestConstants.WS_REGISTER,
                generateRequestHeader(null),
                new InputRequestHelper().prepareWrappedInputRequest(context, userDetails),
                RegisterUserEvent.class);
    }


    public void loginUser(Context context, String emailId, String password, WebRequestManager.WebProcessListener<LoginEvent> webProcessListener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("emailId", emailId);
        map.put("password", password);

        new WebRequestManager(webProcessListener).makeRequest(SwipeRewardsApp.getRequestQueue(context), Request.Method.POST,
                WebRequestConstants.WS_LOGIN,
                generateRequestHeader(null),
                new InputRequestHelper().prepareWrappedInputRequest(context, map),
                LoginEvent.class);
    }


    public void getDeals(Context context, String location, WebRequestManager.WebProcessListener<GetDealsEvent> webProcessListener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("location", location);

        new WebRequestManager(webProcessListener).makeRequest(SwipeRewardsApp.getRequestQueue(context), Request.Method.POST,
                WebRequestConstants.WS_GET_DEALS,
                generateRequestHeader(getSessionToken(context)),
                new InputRequestHelper().prepareWrappedInputRequest(context, map),
                GetDealsEvent.class);
    }

    public void changePassword(Context context, String oldPassword, String newPassword, WebRequestManager.WebProcessListener<ChangePasswordEvent> webProcessListener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("oldPassword", oldPassword);
        map.put("newPassword", newPassword);

        new WebRequestManager(webProcessListener).makeRequest(SwipeRewardsApp.getRequestQueue(context), Request.Method.POST,
                WebRequestConstants.WS_CHANGE_PASSWORD,
                generateRequestHeader(getSessionToken(context)),
                new InputRequestHelper().prepareWrappedInputRequest(context, map),
                ChangePasswordEvent.class);
    }


    public void addWalletCard(Context context, WalletCard walletCard, WebRequestManager.WebProcessListener<BaseEvent> webProcessListener) {
        new WebRequestManager(webProcessListener).makeRequest(SwipeRewardsApp.getRequestQueue(context), Request.Method.POST,
                WebRequestConstants.WS_ADD_CARD,
                generateRequestHeader(getSessionToken(context)),
                new InputRequestHelper().prepareWrappedInputRequest(context, walletCard),
                BaseEvent.class);
    }

    public void getWalletCards(Context context, WebRequestManager.WebProcessListener<GetWalletCardsEvent> webProcessListener) {
        new WebRequestManager(webProcessListener).makeRequest(SwipeRewardsApp.getRequestQueue(context), Request.Method.POST,
                WebRequestConstants.WS_GET_CARDS,
                generateRequestHeader(getSessionToken(context)),
                new InputRequestHelper().prepareWrappedInputRequest(context, null),
                GetWalletCardsEvent.class);
    }


}