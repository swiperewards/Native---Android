package com.winjit.swiperewards.helpers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.winjit.swiperewards.R;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.entities.Deals;
import com.winjit.swiperewards.interfaces.MessageDialogConfirm;

import java.util.ArrayList;

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
                        R.string.yes, R.string.btn_cancel,R.string.confirm,
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


}
