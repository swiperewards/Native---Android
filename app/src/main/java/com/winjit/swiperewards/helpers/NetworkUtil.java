package com.winjit.swiperewards.helpers;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Check network connectivity for making online request to server
 */
public class NetworkUtil {

    private static NetworkUtil networkUtil;

    public static NetworkUtil getInstance() {
        if (networkUtil == null) {
            networkUtil = new NetworkUtil();
        }
        return networkUtil;
    }

    public boolean isConnectedToInternet(Context context) {

        ConnectivityManager conMgr;
        conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}
