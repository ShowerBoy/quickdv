package com.quickdv.activity.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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
    RequestQueue mRequestQueue;
    Context context;


    public HttpConnect(Context context, SucceesInter responseListener, Response.ErrorListener errorListener) {
        this.mRequestQueue = Volley.newRequestQueue(context);
        this.context = context;
        this.responseListener = responseListener;
        this.errorListener = errorListener;
    }

    public void addToRequestQueue(int type, final String tag, JSONObject param) {
            JsonObjectRequest request = new JsonObjectRequest(type, url, param, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                        responseListener.onResponse(tag,response);
                }
            }, errorListener);
            request.setTag(tag);
            mRequestQueue.add(request);

    }

    public void addToRequestQueue(int type, final String tag, String methods, JSONObject params) {
        final JsonObjectRequest requested = new JsonObjectRequest(type, url + methods, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                responseListener.onResponse(tag, response);
            }
        }, errorListener);
        mRequestQueue.add(requested);
    }

    public void start() {
        mRequestQueue.start();
    }


    public void get() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }


    public void stopRequest() {
        if (mRequestQueue != null)
            mRequestQueue.cancelAll(context);
    }
}
