package com.winjit.swiperewards;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.winjit.swiperewards.constants.ISwipe;


/**
 * An application class which holds and perform the common activities like crashlytics, volley request queue, event bus.
 *
 * @author VishalB
 */
public class SwipeRewardsApp extends Application {


    private static boolean activityVisible;
    private static RequestQueue sQueue;

    @Override
    public void onCreate() {
        super.onCreate();
//        setCustomFont();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

/*
        long PICASSO_DISK_CACHE_SIZE = 1024 * 1024 * 10;
        Picasso picasso = new Picasso.Builder(this)
                .memoryCache(new LruCache((int) PICASSO_DISK_CACHE_SIZE))
                .build();
        Picasso.setSingletonInstance(picasso);*/
    }

    /**
     * Volley requestQueue instance
     *
     * @return : Global volley request queue instance
     */
    /**
     * Volley requestQueue instance
     *
     * @return : Global volley request queue instance
     */
    public static RequestQueue getRequestQueue(Context context) {
        if (context != null) {
            if (sQueue == null) {
                sQueue = Volley.newRequestQueue(context);
                return sQueue;
            } else {
                return sQueue;
            }
        } else {
            Log.e(ISwipe.TAG, "Swipe not initialised");
            return null;
        }
    }



    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

}