package com.winjit.swiperewards.web;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.Map;

public class WebRequestManager implements WebRequestHelper.WebResponseListener {

    private WebProcessListener mWebProcessListener;

    public interface WebProcessListener<T> {
        void onWebProcessSuccess(T object);

        void onWebProcessFailed(VolleyError error, Class aClass);
    }

    public WebRequestManager(WebProcessListener webProcessListener) {
        mWebProcessListener = webProcessListener;
    }

    public void makeRequest(RequestQueue requestQueue, int methodType,  String url, Map<String, String> headers, JSONObject params, Class clazz) {
        WebRequestHelper webRequest;
        webRequest = new WebRequestHelper(methodType, url, headers, (params == null) ? new JSONObject() : params, WebRequestManager.this, clazz);
        requestQueue.add(webRequest);
    }

    @Override
    public void onWebRequestResponse(Object response) {
        if (response != null) {
            mWebProcessListener.onWebProcessSuccess(response);
        }
    }

    @Override
    public void onWebRequestError(VolleyError error, Class aClass) {
        mWebProcessListener.onWebProcessFailed(error,  aClass);
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