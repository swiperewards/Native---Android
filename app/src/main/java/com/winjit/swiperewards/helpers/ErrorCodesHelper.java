package com.winjit.swiperewards.helpers;

import android.content.Context;

import com.winjit.swiperewards.R;


public class ErrorCodesHelper {

    public static final int ERROR_GENERIC = 1;
    public static final int ERROR_NETOWRK = 2;
    private static final int USER_ALREADY_EXIST = 801;
    private static final int INVALID_LOGIN_CREDENTIALS = 1003;
    private static final int INVALID_OLD_PASSWORD = 1006;
    private static final int CARD_ALREADY_EXIST = 1051;


    public static String getErrorStringFromCode(Context context, int code) {
        switch (code) {
            case ERROR_NETOWRK:
                return context.getResources().getString(R.string.err_network);
            case USER_ALREADY_EXIST:
                return context.getResources().getString(R.string.err_email_exist);
            case INVALID_LOGIN_CREDENTIALS:
                return context.getResources().getString(R.string.err_invalid_creds);
            case INVALID_OLD_PASSWORD:
                return context.getResources().getString(R.string.err_invalid_old_password);
            case CARD_ALREADY_EXIST:
                return context.getResources().getString(R.string.err_card_aready_exists);

            case ERROR_GENERIC:
            default:
                return context.getResources().getString(R.string.err_generic);
        }
    }

}
