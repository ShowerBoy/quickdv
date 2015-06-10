package com.quickdv.activity.network;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by lilongfei on 15/5/1.
 */
public class HttpConnect {

    SucceesInter responseListener;
    Response.ErrorListener errorListener;
    String url = "http://xdtu.wicp.net:100/carproject/web/app/index.php/Ipublic/";
//    String url = "http://192.168.2.101:8080/xiaolingtong/mobile/http/";
    RequestQueue mRequestQueue;
    Context context;
    private final int MY_SOCKET_TIMEOUT_MS = 10000;


    public HttpConnect(Context context, SucceesInter responseListener, Response.ErrorListener errorListener) {
        this.mRequestQueue = Volley.newRequestQueue(context);
        this.context = context;
        this.responseListener = responseListener;
        this.errorListener = errorListener;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public void addToRequestQueue(int type, final String method, JSONObject param) {
        JsonObjectRequest request = new JsonObjectRequest(type, method !=null ? url + method: url, param, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                responseListener.onResponse(method, response);
            }
        }, errorListener);
        if(method != null)
            request.setTag(method);

        mRequestQueue.add(request);

    }

    public void addToRequestQueue(int type, final Object tag, String methods, JSONObject params) {
        final JsonObjectRequest requested = new JsonObjectRequest(type,  methods !=null ? url + methods: url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                responseListener.onResponse(tag, response);
            }
        }, errorListener);
        requested.setTag(tag);
        requested.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(requested);
    }

    public void start() {
        mRequestQueue.start();
    }

    public void stopRequest() {
        if (mRequestQueue != null)
            mRequestQueue.cancelAll(context);
    }
}
