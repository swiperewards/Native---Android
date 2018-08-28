package com.nouvo.rewards.customviews;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;


public class FontIconTextView extends AppCompatTextView {

    public FontIconTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        this.setTypeface(FontManager.getTypeface(context));
    }

    public FontIconTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
//        this.setTypeface(FontManager.getTypeface(context));
    }

    public FontIconTextView(Context context) {
        super(context);
//        this.setTypeface(FontManager.getTypeface(context));
    }

}