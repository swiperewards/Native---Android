package com.winjit.swiperewards.constants;

/**
 * Class which has base url and end point url definitions.
 *
 * @author VishalB
 */
public class WebRequestConstants {
    //Host URL
//    private static final String URL = "http://winjitstaging.cloudapp.net:5000";
    private static final String URL = "http://192.168.0.198:5000";

    public static final String WS_REGISTER = URL + "/users/registerUser";
    public static final String WS_LOGIN = URL + "/users/loginUser";
    public static final String WS_GET_DEALS = URL + "/deals/getDeals";
    public static final String WS_CHANGE_PASSWORD = URL + "/users/changepassword";
    public static final String WS_FORGOT_PASSWORD = URL + "/users/forgotPassword";
    public static final String WS_SET_NEW_PASSWORD = URL + "/users/setPassword";
    public static final String WS_ADD_CARD = URL + "/user/cards/addCard";
    public static final String WS_GET_CARDS = URL + "/user/cards/getCards";
    public static final String WS_DELETE_CARD = URL + "/user/cards/deleteCard";
    public static final String WS_GET_TICKET_TYPES = URL + "/tickets/getTicketTypes";
    public static final String WS_GENERATE_TICKET = URL + "/tickets/generateTicket";
    public static final String WS_SET_NOTIFICATION_STATUS = URL + "/users/toggleNotification";
    public static final String WS_INIT_SWIPE = URL + "/config/initSwipe";
    public static final String WS_GET_REDEEM_OPTIONS = URL + "/redeem/getRedeemOptions";
    public static final String WS_RAISE_REDEEM_REQUEST = URL + "/redeem/raiseRedeemRequest";
    public static final String WS_GET_EVENT_HISTORY = URL + "/event/getEventNotifications";
    public static final String WS_UPLOAD_PROFILE_PIC = URL + "/users/updateProfilePicV1";
    public static final String WS_GET_CITIES = URL + "/config/getCities";




}
