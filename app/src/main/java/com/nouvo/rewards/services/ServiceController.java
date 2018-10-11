package com.nouvo.rewards.services;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.nouvo.rewards.NouvoApp;
import com.nouvo.rewards.constants.ISwipe;
import com.nouvo.rewards.constants.WebRequestConstants;
import com.nouvo.rewards.entities.UserDetails;
import com.nouvo.rewards.entities.WalletCard;
import com.nouvo.rewards.events.BaseEvent;
import com.nouvo.rewards.events.ChangePasswordEvent;
import com.nouvo.rewards.events.GetCitiesEvent;
import com.nouvo.rewards.events.GetDealsEvent;
import com.nouvo.rewards.events.GetEventHistoryEvent;
import com.nouvo.rewards.events.GetRedeemModesEvent;
import com.nouvo.rewards.events.GetTicketTypeEvent;
import com.nouvo.rewards.events.GetWalletCardsEvent;
import com.nouvo.rewards.events.InitSwipeEvent;
import com.nouvo.rewards.events.LoginEvent;
import com.nouvo.rewards.events.NotificationStatusEvent;
import com.nouvo.rewards.events.RegisterUserEvent;
import com.nouvo.rewards.helpers.InputRequestHelper;
import com.nouvo.rewards.helpers.PreferenceUtils;
import com.nouvo.rewards.web.WebRequestManager;

import java.util.HashMap;

/**
 * Class to call api methods.
 */
public class ServiceController {

    public ServiceController() {

    }

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
        new WebRequestManager(context, webProcessListener).makeRequest(NouvoApp.getRequestQueue(context), Request.Method.POST,
                WebRequestConstants.WS_REGISTER,
                generateRequestHeader(null),
                new InputRequestHelper().prepareWrappedInputRequest(context, userDetails),
                RegisterUserEvent.class);
    }


    public void loginUser(Context context, String emailId, String password, WebRequestManager.WebProcessListener<LoginEvent> webProcessListener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("emailId", emailId);
        map.put("password", password);

        new WebRequestManager(context, webProcessListener).makeRequest(NouvoApp.getRequestQueue(context), Request.Method.POST,
                WebRequestConstants.WS_LOGIN,
                generateRequestHeader(null),
                new InputRequestHelper().prepareWrappedInputRequest(context, map),
                LoginEvent.class);
    }


    public void getDeals(Context context, String location, WebRequestManager.WebProcessListener<GetDealsEvent> webProcessListener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("location", location);

        new WebRequestManager(context, webProcessListener).makeRequest(NouvoApp.getRequestQueue(context), Request.Method.POST,
                WebRequestConstants.WS_GET_DEALS,
                generateRequestHeader(getSessionToken(context)),
                new InputRequestHelper().prepareWrappedInputRequest(context, map),
                GetDealsEvent.class);
    }

    public void changePassword(Context context, String oldPassword, String newPassword, WebRequestManager.WebProcessListener<ChangePasswordEvent> webProcessListener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("oldPassword", oldPassword);
        map.put("password", newPassword);

        new WebRequestManager(context, webProcessListener).makeRequest(NouvoApp.getRequestQueue(context), Request.Method.POST,
                WebRequestConstants.WS_CHANGE_PASSWORD,
                generateRequestHeader(getSessionToken(context)),
                new InputRequestHelper().prepareWrappedInputRequest(context, map),
                ChangePasswordEvent.class);
    }


    public void addWalletCard(Context context, WalletCard walletCard, WebRequestManager.WebProcessListener<BaseEvent> webProcessListener) {
        new WebRequestManager(context, webProcessListener).makeRequest(NouvoApp.getRequestQueue(context), Request.Method.POST,
                WebRequestConstants.WS_ADD_CARD,
                generateRequestHeader(getSessionToken(context)),
                new InputRequestHelper().prepareWrappedInputRequest(context, walletCard),
                BaseEvent.class);
    }

    public void getWalletCards(Context context, WebRequestManager.WebProcessListener<GetWalletCardsEvent> webProcessListener) {
        new WebRequestManager(context, webProcessListener).makeRequest(NouvoApp.getRequestQueue(context), Request.Method.POST,
                WebRequestConstants.WS_GET_CARDS,
                generateRequestHeader(getSessionToken(context)),
                new InputRequestHelper().prepareWrappedInputRequest(context, null),
                GetWalletCardsEvent.class);
    }


    public void deleteCard(Context context, long cardId, WebRequestManager.WebProcessListener<BaseEvent> webProcessListener) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("cardId", cardId);

        new WebRequestManager(context, webProcessListener).makeRequest(NouvoApp.getRequestQueue(context), Request.Method.POST,
                WebRequestConstants.WS_DELETE_CARD,
                generateRequestHeader(getSessionToken(context)),
                new InputRequestHelper().prepareWrappedInputRequest(context, map),
                BaseEvent.class);
    }


    public void getTicketTypes(Context context, WebRequestManager.WebProcessListener<GetTicketTypeEvent> webProcessListener) {

        new WebRequestManager(context, webProcessListener).makeRequest(NouvoApp.getRequestQueue(context), Request.Method.POST,
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

        new WebRequestManager(context, webProcessListener).makeRequest(NouvoApp.getRequestQueue(context), Request.Method.POST,
                WebRequestConstants.WS_GENERATE_TICKET,
                generateRequestHeader(getSessionToken(context)),
                new InputRequestHelper().prepareWrappedInputRequest(context, map),
                BaseEvent.class);
    }


    public void updateNotificationStatus(Context context, boolean isEnabled, WebRequestManager.WebProcessListener<NotificationStatusEvent> webProcessListener) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("enableNotification", isEnabled);

        new WebRequestManager(context, webProcessListener).makeRequest(NouvoApp.getRequestQueue(context), Request.Method.POST,
                WebRequestConstants.WS_SET_NOTIFICATION_STATUS,
                generateRequestHeader(getSessionToken(context)),
                new InputRequestHelper().prepareWrappedInputRequest(context, map),
                NotificationStatusEvent.class);
    }


    public void initialiseSwipeRewards(Context context, int appVersionCode, WebRequestManager.WebProcessListener<InitSwipeEvent> webProcessListener) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("appVersionCode", appVersionCode);
        map.put("platform", ISwipe.PLATFORM);

        new WebRequestManager(context, webProcessListener).makeRequest(NouvoApp.getRequestQueue(context), Request.Method.POST,
                WebRequestConstants.WS_INIT_SWIPE,
                generateRequestHeader(getSessionToken(context)),
                new InputRequestHelper().prepareWrappedInputRequest(context, map),
                InitSwipeEvent.class);
    }


    public void getRedeemModes(Context context, WebRequestManager.WebProcessListener<GetRedeemModesEvent> webProcessListener) {
        new WebRequestManager(context, webProcessListener).makeRequest(NouvoApp.getRequestQueue(context), Request.Method.POST,
                WebRequestConstants.WS_GET_REDEEM_OPTIONS,
                generateRequestHeader(getSessionToken(context)),
                new InputRequestHelper().prepareWrappedInputRequest(context, null),
                GetRedeemModesEvent.class);

    }

    public void getEventHistory(Context context, WebRequestManager.WebProcessListener<GetEventHistoryEvent> webProcessListener) {
        new WebRequestManager(context, webProcessListener).makeRequest(NouvoApp.getRequestQueue(context), Request.Method.POST,
                WebRequestConstants.WS_GET_EVENT_HISTORY,
                generateRequestHeader(getSessionToken(context)),
                new InputRequestHelper().prepareWrappedInputRequest(context, null),
                GetEventHistoryEvent.class);

    }

    public void raiseRedeemRequest(Context context, HashMap<String, Object> map, WebRequestManager.WebProcessListener<BaseEvent> webProcessListener) {
        new WebRequestManager(context, webProcessListener).makeRequest(NouvoApp.getRequestQueue(context), Request.Method.POST,
                WebRequestConstants.WS_RAISE_REDEEM_REQUEST,
                generateRequestHeader(getSessionToken(context)),
                new InputRequestHelper().prepareWrappedInputRequest(context, map),
                BaseEvent.class);
    }

    public void uploadProfilePic(Context context, String bitMap, WebRequestManager.WebProcessListener<BaseEvent> webProcessListener) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("image", bitMap);
        new WebRequestManager(context, webProcessListener).makeRequest(NouvoApp.getRequestQueue(context), Request.Method.POST,
                WebRequestConstants.WS_UPLOAD_PROFILE_PIC,
                generateRequestHeader(getSessionToken(context)),
                new InputRequestHelper().prepareWrappedInputRequest(context, map),
                BaseEvent.class);
    }


    public void forgotPassword(Context context, String emailID, WebRequestManager.WebProcessListener<BaseEvent> webProcessListener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("emailId", emailID);

        new WebRequestManager(context, webProcessListener).makeRequest(NouvoApp.getRequestQueue(context), Request.Method.POST,
                WebRequestConstants.WS_FORGOT_PASSWORD,
                generateRequestHeader(getSessionToken(context)),
                new InputRequestHelper().prepareWrappedInputRequest(context, map),
                BaseEvent.class);
    }


    public void setNewPassword(Context context, String emailID, String passCode, String newPassword, WebRequestManager.WebProcessListener<ChangePasswordEvent> webProcessListener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("resetToken", passCode);
        map.put("password", newPassword);
        map.put("emailId", emailID);

        new WebRequestManager(context, webProcessListener).makeRequest(NouvoApp.getRequestQueue(context), Request.Method.POST,
                WebRequestConstants.WS_SET_NEW_PASSWORD,
                generateRequestHeader(getSessionToken(context)),
                new InputRequestHelper().prepareWrappedInputRequest(context, map),
                ChangePasswordEvent.class);
    }


    public void getCityList(Context context, WebRequestManager.WebProcessListener<GetCitiesEvent> webProcessListener) {

        new WebRequestManager(context, webProcessListener).makeRequest(NouvoApp.getRequestQueue(context), Request.Method.POST,
                WebRequestConstants.WS_GET_CITIES,
                generateRequestHeader(getSessionToken(context)),
                new InputRequestHelper().prepareWrappedInputRequest(context, null),
                GetCitiesEvent.class);
    }

    public void getDealsWithPagination(Context context, String location, int pageNumber, int pageSize, WebRequestManager.WebProcessListener<GetDealsEvent> webProcessListener) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("location", location);
        map.put("pageNumber", pageNumber);
        map.put("pageSize", pageSize);

        new WebRequestManager(context, webProcessListener).makeRequest(NouvoApp.getRequestQueue(context), Request.Method.POST,
                WebRequestConstants.WS_GET_DEALS_PAGINATION,
                generateRequestHeader(getSessionToken(context)),
                new InputRequestHelper().prepareWrappedInputRequest(context, map),
                GetDealsEvent.class);
    }


    public void applyReferralCode(Context context, String referredBy, WebRequestManager.WebProcessListener<BaseEvent> webProcessListener) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("referredBy", referredBy);

        new WebRequestManager(context, webProcessListener).makeRequest(NouvoApp.getRequestQueue(context), Request.Method.POST,
                WebRequestConstants.WS_APPLY_REFERRAL_CODE,
                generateRequestHeader(getSessionToken(context)),
                new InputRequestHelper().prepareWrappedInputRequest(context, map),
                BaseEvent.class);
    }


    public void addUpdateFcmToken(Context context, String fcmToken, WebRequestManager.WebProcessListener<BaseEvent> webProcessListener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("token", fcmToken);

        new WebRequestManager(context, webProcessListener).makeRequest(NouvoApp.getRequestQueue(context), Request.Method.POST,
                WebRequestConstants.WS_FCM_TOKEN,
                generateRequestHeader(getSessionToken(context)),
                new InputRequestHelper().prepareWrappedInputRequest(context, map),
                BaseEvent.class);
    }



}