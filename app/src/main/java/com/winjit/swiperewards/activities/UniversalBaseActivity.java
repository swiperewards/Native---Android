package com.winjit.swiperewards.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcDialog;
import com.leo.simplearcloader.SimpleArcLoader;
import com.winjit.swiperewards.R;

public class UniversalBaseActivity extends AppCompatActivity {

    private SimpleArcDialog pgLoader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void showProgress(String message) {
        String strMessage = getResources().getString(R.string.please_wait);
        if (pgLoader == null) {
            int[] colorArray =
                    {
//                            ContextCompat.getColor(this, R.color.colorPrimary), ContextCompat.getColor(this, android.R.color.darker_gray), ContextCompat.getColor(this, R.color.colorPrimaryDark)
                            ContextCompat.getColor(this, R.color.color_spinner), ContextCompat.getColor(this, R.color.colorPrimary), ContextCompat.getColor(this, R.color.colorPrimaryDark)

                    };

            ArcConfiguration configuration = new ArcConfiguration(this);
            configuration.setLoaderStyle(SimpleArcLoader.STYLE.COMPLETE_ARC);
            configuration.setText(message);
            configuration.setTextColor(ContextCompat.getColor(this, android.R.color.black));
            configuration.setColors(colorArray);
            configuration.setAnimationSpeedWithIndex(SimpleArcLoader.SPEED_FAST);

            pgLoader = new SimpleArcDialog(this);
            pgLoader.getLoadingTextView();
            pgLoader.setConfiguration(configuration);
            pgLoader.setCancelable(false);
            pgLoader.show();
        } else if (pgLoader.isShowing() == false) {
            pgLoader.getLoadingTextView().setText(strMessage);
            pgLoader.show();
        }
    }

    public void hideProgress() {
        if (pgLoader != null && pgLoader.isShowing())
            pgLoader.dismiss();
    }

    /**
     * Method used to show toast on screen
     *
     * @param mMessage
     */
    public void showToast(Context context, String mMessage) {
        Toast.makeText(context, mMessage, Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(Context context, String mMessage) {
        Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();
    }
/*
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }*/
}
