package com.winjit.swiperewards.helpers;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;


public class PermissionUtils {

    public static boolean requestPermission(Activity activity, String strPermission) {
        if (!checkPermissionGranted(activity, strPermission)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String[] permissions = {strPermission};
                activity.requestPermissions(permissions, 1);
            } else {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }

    public static boolean checkPermissionGranted(Activity activity, String strPermission) {
        int res = activity.checkCallingOrSelfPermission(strPermission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    public static boolean requestMultiplePermissions(Activity activity, String[] strPermission) {
        if (!checkMultiplePermissionsGranted(activity, strPermission)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.requestPermissions(strPermission, 1);
            } else {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }

    public static boolean checkMultiplePermissionsGranted(Activity activity, String[] strPermission) {

        for (int index = 0; index < strPermission.length; index++) {
            int res = activity.checkCallingOrSelfPermission(strPermission[index]);
            if (!(res == PackageManager.PERMISSION_GRANTED)) {
                return false;
            }
        }
        return true;
    }


}
