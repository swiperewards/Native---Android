package com.winjit.swiperewards.services;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.winjit.swiperewards.SwipeRewardsApp;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.constants.WebRequestConstants;
import com.winjit.swiperewards.entities.UserDetails;
import com.winjit.swiperewards.entities.WalletCard;
import com.winjit.swiperewards.events.BaseEvent;
import com.winjit.swiperewards.events.ChangePasswordEvent;
import com.winjit.swiperewards.events.GetDealsEvent;
import com.winjit.swiperewards.events.GetEventHistoryEvent;
import com.winjit.swiperewards.events.GetRedeemModesEvent;
import com.winjit.swiperewards.events.GetTicketTypeEvent;
import com.winjit.swiperewards.events.GetWalletCardsEvent;
import com.winjit.swiperewards.events.InitSwipeEvent;
import com.winjit.swiperewards.events.LoginEvent;
import com.winjit.swiperewards.events.NotificationStatusEvent;
import com.winjit.swiperewards.events.RegisterUserEvent;
import com.winjit.swiperewards.helpers.InputRequestHelper;
import com.winjit.swiperewards.helpers.PreferenceUtils;
import com.winjit.swiperewards.web.WebRequestManager;

import org.json.JSONObject;

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


    public void deleteCard(Context context, long cardId, WebRequestManager.WebProcessListener<BaseEvent> webProcessListener) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("cardId", cardId);

        new WebRequestManager(webProcessListener).makeRequest(SwipeRewardsApp.getRequestQueue(context), Request.Method.POST,
                WebRequestConstants.WS_DELETE_CARD,
                generateRequestHeader(getSessionToken(context)),
                new InputRequestHelper().prepareWrappedInputRequest(context, map),
                BaseEvent.class);
    }


    public void getTicketTypes(Context context, WebRequestManager.WebProcessListener<GetTicketTypeEvent> webProcessListener) {

        new WebRequestManager(webProcessListener).makeRequest(SwipeRewardsApp.getRequestQueue(context), Request.Method.POST,
                WebRequestConstants.WS_GET_TICKET_TYPES,
                generateRequestHeader(getSessionToken(context)),
                new InputRequestHelper().prepareWrappedInputRequest(context, null),
                GetTicketTypeEvent.class);
    }

    public void generateTicket(Context context, int ticketTypeId, String userCategory, String feedback, WebRequestManager.WebProcessListener<BaseEvent> webProcessListener) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("ticketTypeId", Integer.valueOf(ticketTypeId));
        map.put("userCategory", userCategory);
        map.put("feedback", feedback);

        new WebRequestManager(webProcessListener).makeRequest(SwipeRewardsApp.getRequestQueue(context), Request.Method.POST,
                WebRequestConstants.WS_GENERATE_TICKET,
                generateRequestHeader(getSessionToken(context)),
                new InputRequestHelper().prepareWrappedInputRequest(context, map),
                BaseEvent.class);
    }


    public void updateNotificationStatus(Context context, boolean isEnabled, WebRequestManager.WebProcessListener<NotificationStatusEvent> webProcessListener) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("enableNotification", isEnabled);

        new WebRequestManager(webProcessListener).makeRequest(SwipeRewardsApp.getRequestQueue(context), Request.Method.POST,
                WebRequestConstants.WS_SET_NOTIFICATION_STATUS,
                generateRequestHeader(getSessionToken(context)),
                new InputRequestHelper().prepareWrappedInputRequest(context, map),
                NotificationStatusEvent.class);
    }


    public void initialiseSwipeRewards(Context context, int appVersionCode, WebRequestManager.WebProcessListener<InitSwipeEvent> webProcessListener) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("appVersionCode", appVersionCode);
        map.put("platform", ISwipe.PLATFORM);

        new WebRequestManager(webProcessListener).makeRequest(SwipeRewardsApp.getRequestQueue(context), Request.Method.POST,
                WebRequestConstants.WS_INIT_SWIPE,
                generateRequestHeader(getSessionToken(context)),
                new InputRequestHelper().prepareWrappedInputRequest(context, map),
                InitSwipeEvent.class);
    }

    public void getRedeemModes(Context context, WebRequestManager.WebProcessListener<GetRedeemModesEvent> webProcessListener) {
        new WebRequestManager(webProcessListener).makeRequest(SwipeRewardsApp.getRequestQueue(context), Request.Method.POST,
                WebRequestConstants.WS_GET_REDEEM_OPTIONS,
                generateRequestHeader(getSessionToken(context)),
                new InputRequestHelper().prepareWrappedInputRequest(context, null),
                GetRedeemModesEvent.class);

    }

    public void getEventHistory(Context context, WebRequestManager.WebProcessListener<GetEventHistoryEvent> webProcessListener) {
        new WebRequestManager(webProcessListener).makeRequest(SwipeRewardsApp.getRequestQueue(context), Request.Method.POST,
                WebRequestConstants.WS_GET_EVENT_HISTORY,
                generateRequestHeader(getSessionToken(context)),
                new InputRequestHelper().prepareWrappedInputRequest(context, null),
                GetEventHistoryEvent.class);

    }

    public void raiseRedeemRequest(Context context, HashMap<String, Object> map, WebRequestManager.WebProcessListener<BaseEvent> webProcessListener) {
        new WebRequestManager(webProcessListener).makeRequest(SwipeRewardsApp.getRequestQueue(context), Request.Method.POST,
                WebRequestConstants.WS_RAISE_REDEEM_REQUEST,
                generateRequestHeader(getSessionToken(context)),
                new InputRequestHelper().prepareWrappedInputRequest(context, map),
                BaseEvent.class);
    }

    public void uploadProfilePic(Context context, String bitMap, WebRequestManager.WebProcessListener<BaseEvent> webProcessListener) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("file", bitMap);
        new WebRequestManager(webProcessListener).makeRequest(SwipeRewardsApp.getRequestQueue(context), Request.Method.POST,
                WebRequestConstants.WS_UPLOAD_PROFILE_PIC,
                generateRequestHeader(getSessionToken(context)),
                new JSONObject(map),
                BaseEvent.class);
    }

}