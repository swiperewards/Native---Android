package com.winjit.swiperewards.helpers;

import android.content.Context;
import android.provider.Settings;

import com.google.gson.Gson;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.entities.WrappedInputRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class InputRequestHelper {


    public JSONObject prepareWrappedInputRequest(Context context, Object inputRequest) {

        String deviceID = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        WrappedInputRequest wrappedInputRequest = new WrappedInputRequest();
        wrappedInputRequest.setDeviceId(deviceID);
        wrappedInputRequest.setLatitude("");
        wrappedInputRequest.setLongitude("");
        wrappedInputRequest.setPlatform(ISwipe.PLATFORM);
        wrappedInputRequest.setRequestData(inputRequest);

        JSONObject inputJsonObject = null;
        try {
            inputJsonObject = new JSONObject(new Gson().toJson(wrappedInputRequest));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return inputJsonObject;
    }
}
