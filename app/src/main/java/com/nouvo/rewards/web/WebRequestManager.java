package com.nouvo.rewards.web;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.nouvo.rewards.R;
import com.nouvo.rewards.helpers.NetworkUtil;

import org.json.JSONObject;

import java.util.Map;

public class WebRequestManager implements WebRequestHelper.WebResponseListener {

    private WebProcessListener mWebProcessListener;
    private Context context;

    public interface WebProcessListener<T> {
        void onWebProcessSuccess(T object);

        void onWebProcessFailed(VolleyError error, Class aClass);
    }

    public WebRequestManager(Context context, WebProcessListener webProcessListener) {
        this.context = context;
        mWebProcessListener = webProcessListener;
    }

    public void makeRequest(RequestQueue requestQueue, int methodType, String url, Map<String, String> headers, JSONObject params, Class clazz) {
        if (context == null || NetworkUtil.getInstance().isConnectedToInternet(context)) {
            WebRequestHelper webRequest;
            webRequest = new WebRequestHelper(methodType, url, headers, (params == null) ? new JSONObject() : params, WebRequestManager.this, clazz);
            requestQueue.add(webRequest);
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