package com.nouvo.rewards.helpers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.nouvo.rewards.R;


public class CommunicationHelper {

    /**
     * Method used to share provided url on available application
     *
     * @param context : application context
     * @param title   : share dialog title
     * @param text    : text to share
     */
    public void shareTextUrl(Context context, String title, String text) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        share.putExtra(Intent.EXTRA_SUBJECT, title);
        share.putExtra(Intent.EXTRA_TEXT, text);

        context.startActivity(Intent.createChooser(share, "Share link!"));
    }

    public void shareOnSocial(Context context, String url, String message) {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, context.getResources().getString(R.string.app_name));
            String sAux = "\n" + message + "\n\n";
            sAux = sAux + url + "\n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            context.startActivity(Intent.createChooser(i, "Choose one"));
        } catch (Exception e) {
            //e.toString();
        }
    }

    /**
     * Method used to make call
     *
     * @param context : application context
     * @param phone   : number to call
     */
    public void makeCall(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        context.startActivity(intent);
    }

    /**
     * Method used to sent email
     *
     * @param context : application context
     * @param emailId : mail send to
     * @param subject : mail subject
     * @param message : mail message body
     */
    public void sendEmail(Context context, String emailId, String subject, String message) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", emailId, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);
        context.startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }
}
