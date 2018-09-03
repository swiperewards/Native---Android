package com.nouvo.rewards.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.nouvo.rewards.helpers.security.CryptoHelper;


/**
 * This class manages the storage and retrieval of the data from Shared Preferences
 */
public class PreferenceUtils {

    private static final int MODE = Context.MODE_PRIVATE;
    // Keys
    public static final String SESSION_TOKEN = "session_token";
    public static final String USER_DETAILS = "user_details";
    private static final String PREF_NAME = "swipe_rewards";


    public static void writeBoolean(Context context, String key, boolean value) {
        if (context != null) {
            Editor editor = getEditor(context);
            if (editor != null) {
                editor.putBoolean(key, value).commit();
            }
        }
    }

    public static boolean readBoolean(Context context, String key,
                                      boolean defValue) {
        if (context != null) {
            SharedPreferences sharedPreferences = getPreferences(context);
            if (sharedPreferences != null) {
                return sharedPreferences.getBoolean(key, defValue);
            }
            return defValue;
        } else {
            return defValue;
        }
    }

    public static void writeInteger(Context context, String key, int value) {
        getEditor(context).putInt(key, value).commit();
    }

    public static int readInteger(Context context, String key, int defValue) {
        return getPreferences(context).getInt(key, defValue);
    }

    public static void writeString(Context context, String key, String value) {
        if (!TextUtils.isEmpty(value)) {
            value = new CryptoHelper().encryptXOR(value);
        }

        if (context != null) {
            Editor editor = getEditor(context);
            if (editor != null) {
                editor.putString(key, value).commit();
            }
        }
    }

    public static String readString(Context context, String key, String defValue) {
        SharedPreferences sharedPreferences = getPreferences(context);

        if (sharedPreferences != null) {
            String readValue = sharedPreferences.getString(key, defValue);
            if (!TextUtils.isEmpty(readValue)) {
                readValue = new CryptoHelper().decryptXOR(readValue);
            }
            return readValue;
        } else {
            return defValue;
        }
    }

    public static void writeFloat(Context context, String key, float value) {
        if (context != null) {
            Editor editor = getEditor(context);
            if (editor != null) {
                editor.putFloat(key, value).commit();
            }
        }
//        getEditor(context).putFloat(key, value).commit();
    }

    public static float readFloat(Context context, String key, float defValue) {
        SharedPreferences sharedPreferences = getPreferences(context);
        if (sharedPreferences != null) {
            return sharedPreferences.getFloat(key, defValue);
        }
        return defValue;
    }

    public static void writeLong(Context context, String key, long value) {
        getEditor(context).putLong(key, value).commit();
    }

    public static long readLong(Context context, String key, long defValue) {
        return getPreferences(context).getLong(key, defValue);
    }

    public static String readList(Context context, String key, String defValue) {
        if (context != null) {
            return getPreferences(context).getString(key, defValue);
        } else {
            return defValue;
        }
    }

    public static void clearPreferences(Context context) {
        if (context != null) {
            getEditor(context).clear().commit();
        }
    }

    private static SharedPreferences getPreferences(Context context) {
        if (context != null) {
            return context.getSharedPreferences(PREF_NAME, MODE);
        }
        return null;
    }

    private static Editor getEditor(Context context) {
        if (context != null) {
            return getPreferences(context).edit();
        }
        return null;
    }
}
