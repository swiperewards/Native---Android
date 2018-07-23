package com.winjit.swiperewards.constants;

/**
 * Class which has base url and end point url definitions.
 *
 * @author VishalB
 */
public class WebRequestConstants {
    //Host URL
    private static final String URL = "http://192.168.0.198:5000";

    public static final String WS_REGISTER = URL + "/users/registerUser";
    public static final String WS_LOGIN = URL + "/users/loginUser";
    public static final String WS_GET_DEALS = URL + "/deals/getDeals";
    public static final String WS_CHANGE_PASSWORD = URL + "/users/changepassword";
    public static final String WS_ADD_CARD = URL + "/user/cards/addCard";
    public static final String WS_GET_CARDS = URL + "/user/cards/getCards";
    public static final String WS_GET_TICKET_TYPES = URL + "/tickets/getTicketTypes";
    public static final String WS_GENERATE_TICKET = URL + "/tickets/generateTicket";



}
