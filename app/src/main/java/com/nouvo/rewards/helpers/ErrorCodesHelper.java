package com.nouvo.rewards.helpers;

import android.content.Context;

import com.nouvo.rewards.R;


public class ErrorCodesHelper {

    public static final int ERROR_GENERIC = 1;
    public static final int ERROR_NETOWRK = 2;
    private static final int FAILED_VERIFICATION_LINK = 1001;
    private static final int ERR_VERIFICATION_PENDING = 1002;
    private static final int INVALID_LOGIN_CREDENTIALS = 1003;
    private static final int USER_ALREADY_EXIST = 1004;
    private static final int INVALID_OLD_PASSWORD = 1006;
    private static final int INVALID_IMAGE = 1007;
    private static final int ERR_UPLOADING_IMAGE = 1008;
    private static final int INVALID_EMAIL_ID = 1009;
    private static final int ERR_UNAUTHORIZED = 1010;
    private static final int ERR_TOKEN_EXPIRED = 1011;
    private static final int EMAIL_NOT_FOUND = 1012;
    private static final int FAILED_PASSWORD_RESET_LINK = 1013;
    private static final int INVALID_EMAIL = 1014;
    public static final int INVALID_TOKEN = 1050;
    private static final int CARD_ALREADY_EXIST = 1051;


    public static String getErrorStringFromCode(Context context, int code) {
        if (context != null) {
            switch (code) {
                case FAILED_VERIFICATION_LINK:
                    return context.getResources().getString(R.string.err_failed_verification_link);
                case ERR_VERIFICATION_PENDING:
                    return context.getResources().getString(R.string.err_verification_pending);

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
                case INVALID_EMAIL:
                case EMAIL_NOT_FOUND:
                case INVALID_EMAIL_ID:
                    return context.getResources().getString(R.string.invalid_email);
                case ERR_UPLOADING_IMAGE:
                case INVALID_IMAGE:
                    return context.getResources().getString(R.string.err_profile_pic);
                case FAILED_PASSWORD_RESET_LINK:
                    return context.getResources().getString(R.string.err_falled_password_reset_link);
                case ERR_UNAUTHORIZED:
                    return context.getResources().getString(R.string.err_invalid_token);
                case ERR_TOKEN_EXPIRED:
                    return context.getResources().getString(R.string.err_token_expired);
                case INVALID_TOKEN:
                    return context.getResources().getString(R.string.err_session_expired);
                case ERROR_GENERIC:
                default:
                    return context.getResources().getString(R.string.err_generic);
            }
        }
        return "Something wen’t wrong, We are facing some challenges with connecting to the server. Kindly ensure you have a proper internet connection.";
    }

}
