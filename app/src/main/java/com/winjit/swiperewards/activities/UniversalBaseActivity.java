package com.winjit.swiperewards.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcDialog;
import com.leo.simplearcloader.SimpleArcLoader;
import com.winjit.swiperewards.R;
import com.winjit.swiperewards.entities.AppConfig;
import com.winjit.swiperewards.helpers.UIHelper;
import com.winjit.swiperewards.interfaces.MessageDialogConfirm;

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

    public SimpleArcDialog getLoader() {
        return pgLoader;
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

    protected boolean checkIfForcedUpdate(AppConfig appConfig) {
        if (appConfig.getForcedUpdate()) {
            String dialogInterfaceMessage = getString(R.string.app_update_msg);

            UIHelper.configureShowConfirmDialog(dialogInterfaceMessage, this,
                    R.string.update, R.string.btn_exit, R.string.app_update,
                    new MessageDialogConfirm() {
                        @Override
                        public void onPositiveClick() {
                            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                            }
                        }

                        @Override
                        public void onNegativeClick() {
                            android.os.Process.killProcess(android.os.Process.myPid());
                            return;
                        }
                    });

            return true;
        }
        return false;
    }



/*
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }*/
}
