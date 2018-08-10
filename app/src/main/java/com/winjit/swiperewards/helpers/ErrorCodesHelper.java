package com.winjit.swiperewards.helpers;

import android.content.Context;

import com.winjit.swiperewards.R;


public class ErrorCodesHelper {

    public static final int ERROR_GENERIC = 1;
    public static final int ERROR_NETOWRK = 2;
    private static final int USER_ALREADY_EXIST = 1004;
    private static final int INVALID_LOGIN_CREDENTIALS = 1003;
    private static final int INVALID_OLD_PASSWORD = 1006;
    private static final int CARD_ALREADY_EXIST = 1051;
    private static final int PROFILE_PIC_UPLOAD_ERROR_1 = 1008;
    private static final int PROFILE_PIC_UPLOAD_ERROR_2 = 1007;
    private static final int INVALID_RESET_TOKEN = 1010;


    public static String getErrorStringFromCode(Context context, int code) {
        if (context != null) {
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
                case PROFILE_PIC_UPLOAD_ERROR_1:
                case PROFILE_PIC_UPLOAD_ERROR_2:
                    return context.getResources().getString(R.string.err_profile_pic);
                case INVALID_RESET_TOKEN:
                    return context.getResources().getString(R.string.erro_invalid_token);
                case ERROR_GENERIC:
                default:
                    return context.getResources().getString(R.string.err_generic);
            }
        }
        return "Unable to process the request.";
    }

}
