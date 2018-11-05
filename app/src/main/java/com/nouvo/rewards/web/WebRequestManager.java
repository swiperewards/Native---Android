package com.nouvo.rewards.web;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.evgenii.jsevaluator.interfaces.JsCallback;
import com.nouvo.rewards.R;
import com.nouvo.rewards.constants.ISwipe;
import com.nouvo.rewards.cryptography.JsEncryptor;
import com.nouvo.rewards.helpers.NetworkUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class WebRequestManager implements WebRequestHelper.WebResponseListener {

    private WebProcessListener mWebProcessListener;
    private Context context;
    private JsEncryptor mJsEncryptor;

    public interface WebProcessListener<T> {
        void onWebProcessSuccess(T object);

        void onWebProcessFailed(VolleyError error, Class aClass);
    }

    public WebRequestManager(Context context, WebProcessListener webProcessListener) {
        this.context = context;
        mWebProcessListener = webProcessListener;
    }

    public void makeRequest(final RequestQueue requestQueue, final int methodType, final String url, final Map<String, String> headers, final JSONObject params, final Class clazz) {
        if (context != null && NetworkUtil.getInstance().isConnectedToInternet(context)) {
            try {
                mJsEncryptor = JsEncryptor.evaluateAllScripts(context);
                if (ISwipe.IS_ENCRYPTIOON_ON) {
                    if (params.has("requestData")) {
                        JSONObject requestData = params.getJSONObject("requestData");

                        mJsEncryptor.encrypt(requestData.toString().replace("\\", ""), ISwipe.ENCRYPTION_PASSWORD,
                                new JsCallback() {
                                    @Override
                                    public void onResult(final String encryptedMessage) {
                                        try {
                                            params.put("requestData", encryptedMessage);
                                            WebRequestHelper webRequest;
                                            webRequest = new WebRequestHelper(methodType, url, headers, (params == null) ? new JSONObject() : params, WebRequestManager.this, clazz);
                                            requestQueue.add(webRequest);
                                            System.out.print("webRequest:" + webRequest);
                                        } catch (JSONException e) {
                                            onWebRequestError(new VolleyError(context.getResources().getString(R.string.err_generic)), clazz);
                                        }
                                    }

                                    @Override
                                    public void onError(String s) {
                                        onWebRequestError(new VolleyError(context.getResources().getString(R.string.err_generic)), clazz);
                                    }
                                });
                    } else {
                        WebRequestHelper webRequest;
                        webRequest = new WebRequestHelper(methodType, url, headers, (params == null) ? new JSONObject() : params, WebRequestManager.this, clazz);
                        requestQueue.add(webRequest);
                    }
                } else {
                    WebRequestHelper webRequest;
                    webRequest = new WebRequestHelper(methodType, url, headers, (params == null) ? new JSONObject() : params, WebRequestManager.this, clazz);
                    requestQueue.add(webRequest);
                }


            } catch (JSONException e) {
                onWebRequestError(new VolleyError(context.getResources().getString(R.string.err_generic)), clazz);
            }
        } else {
            onWebRequestError(new VolleyError(context.getResources().getString(R.string.err_network)), clazz);
        }

    }

    @Override
    public void onWebRequestResponse(Object response) {
        if (response != null) {
            mWebProcessListener.onWebProcessSuccess(response);
        }
    }

    @Override
    public void onWebRequestError(VolleyError error, Class aClass) {
        mWebProcessListener.onWebProcessFailed(error, aClass);
    }
//
//    @Override
//    public void onWebRequestError(VolleyError error, int TaskId) {
//        mWebProcessListener.onWebProcessFailed(error.getMessage(), TaskId);
//    }

    @Override
    public void onResponse(Object response) {
    }

    @Override
    public void onErrorResponse(VolleyError error) {
    }
}