package com.quickdv.activity.network;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

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

    public void addToRequestQueue(int type, final String method, JSONObject param) {
        JsonObjectRequest request = new JsonObjectRequest(type, url + method, param, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                responseListener.onResponse(method, response);
            }
        }, errorListener) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=UTF-8");

                return headers;
            }
        };
        request.setTag(method);

        mRequestQueue.add(request);

    }

    public void regest() {

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("mobile", "15639932746");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url + "testmobile", new JSONObject(headers), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                responseListener.onResponse("testmobile", response);
            }
        }, errorListener) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=UTF-8");

                return headers;
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString =
                            new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                    return Response.success(new JSONObject(jsonString),
                            HttpHeaderParser.parseCacheHeaders(response, ignoreNoCache()));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException je) {
                    return Response.error(new ParseError(je));
                }
            }
        };
        request.setTag("testmobile");

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
