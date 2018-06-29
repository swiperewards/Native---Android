package com.winjit.swiperewards.utils;

import android.content.Context;
import android.graphics.Typeface;


public class FontManager {
    private static final String ROOT = "fonts/",
            FONT_ICON = ROOT + "swipe.ttf";

    public static Typeface getTypeface(Context context) {
        return Typeface.createFromAsset(context.getAssets(), FONT_ICON);
    }
}
