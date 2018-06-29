package com.winjit.swiperewards.web;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Map;


class WebRequestHelper<T> extends JsonRequest<T> { //com.android.volley.Request<T> {

    private WebResponseListener<T> webResponseListener;
    private Class<T> clazz;
    private Map<String, String> headers;
    private int TaskId;

    interface WebResponseListener<T> extends Response.Listener<T>, Response.ErrorListener {
        void onWebRequestResponse(T response);

        void onWebRequestError(VolleyError error, Class<T> tClass);
    }

    WebRequestHelper(int method, String url, Map<String, String> headers, JSONObject params, WebResponseListener<T> webResponseListener, Class<T> clazz) {
        super(method, url, params.toString(), webResponseListener, webResponseListener);
       /* if (BuildConfig.IS_IN_DEBUG) {
            String requestBody = "";
            try {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonParser jp = new JsonParser();
                JsonElement je = jp.parse(params.toString());
                requestBody = gson.toJson(je);
            } catch (Exception ex) {
                requestBody = "";
            }
            Log.i("Request", requestBody);
        }*/
        this.headers = headers;
        this.webResponseListener = webResponseListener;
        this.clazz = clazz;
        this.TaskId = TaskId;

        final int SOCKET_TIMEOUT_MS = 150000;
//        final int SOCKET_TIMEOUT_MS = (int) MAX_REQUEST_TIMEOUT_MS;

        //set retry policies
        setRetryPolicy(new DefaultRetryPolicy(
                SOCKET_TIMEOUT_MS, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        Response<T> ntResponse;
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
//            Log.e(TaskConstants.TAG,jsonString);
            ntResponse = Response.success(new Gson().fromJson(jsonString, clazz), HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            ntResponse = Response.error(new ParseError(e));
        }
        return ntResponse;
    }

    @Override
    protected void deliverResponse(T response) {
        webResponseListener.onWebRequestResponse(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        webResponseListener.onWebRequestError(error, clazz);
    }

}