package com.nouvo.rewards.helpers;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.nouvo.rewards.R;
import com.nouvo.rewards.constants.ISwipe;
import com.nouvo.rewards.entities.Deals;
import com.nouvo.rewards.interfaces.MessageDialogConfirm;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonHelper {

    public ArrayList<Deals> updateStartEndDateFormat(ArrayList<Deals> deals) {

        try {
            for (Deals deal : deals) {
                String startDate = DateUtil.getFormattedDate(deal.getStartDate(), DateUtil.DEAL_API_FORMAT, DateUtil.DEAL_DISPLAY_FORMAT);
                String endDate = DateUtil.getFormattedDate(deal.getEndDate(), DateUtil.DEAL_API_FORMAT, DateUtil.DEAL_DISPLAY_FORMAT);
                deal.setStartDate(startDate);
                deal.setEndDate(endDate);
            }
        } catch (Exception e) {
            return deals;
        }
        return deals;
    }

    public void navigateToDealLocation(final Context context, Bundle bundle) {

        if (bundle != null) {
            final String latitude = bundle.getString(ISwipe.LATITUDE);
            final String longitude = bundle.getString(ISwipe.LONGITUDE);
            if (!(TextUtils.isEmpty(latitude) || TextUtils.isEmpty(longitude))) {

                String dialogInterfaceMessage = "Do you want to navigate to this deal location?";

                UIHelper.configureShowConfirmDialog(dialogInterfaceMessage, context,
                        R.string.yes, R.string.btn_cancel, R.string.confirm,
                        new MessageDialogConfirm() {
                            @Override
                            public void onPositiveClick() {
                                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                        Uri.parse("http://maps.google.com/maps?daddr=" + latitude + "," + longitude));
                                context.startActivity(intent);
                            }

                            @Override
                            public void onNegativeClick() {
                            }
                        });


            }
        }
    }

    public String capitalize(String capString) {
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z-éá])([a-z-éá]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()) {
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }

        return capMatcher.appendTail(capBuffer).toString();
    }

    public int getVersionCode(Context context) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0; // default
        }
    }

    public Deals[] getDealsArrayFromArrayList(ArrayList deals) {
        Deals[] dealsArray = new Deals[deals.size()];
        for (int i = 0; i < deals.size(); i++) {
            dealsArray[i] = (Deals) deals.get(i);
        }
        return dealsArray;
    }
}
