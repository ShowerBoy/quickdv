package com.quickdv.activity.helper;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.quickdv.activity.network.HttpConnect;
import com.quickdv.activity.network.SucceesInter;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by lilongfei on 15/6/7.
 */
public class RequestHelper {

    protected HttpConnect network;

    public RequestHelper(Context context, SucceesInter responseListener, Response.ErrorListener errorListener){
    network = new HttpConnect(context,responseListener,errorListener);
    }

    /**
     * 添加网络请求队列
     */
    protected void addToRequestQueue(int type, String methods, Map<String, Object> param) {

        addToRequestQueue(type, methods, methods, param);
    }

    /**
     * 添加网络请求队列
     */
    protected void addToRequestQueue(int type, String tag, String methods, Map<String, Object> param) {
        if (type == Request.Method.GET && param != null) {
            methods = methods + "/?";
            Iterator<Map.Entry<String, Object>> iterator = param.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entity = iterator.next();
                methods += entity.getKey() + "=" + entity.getValue() + "&";
            }
            if (methods.endsWith("&"))
                methods = methods.substring(0, methods.length() - 1);
            network.addToRequestQueue(type, tag, methods, null);

        } else if (param != null)
            network.addToRequestQueue(type, tag, methods, new JSONObject(param));
        else
            network.addToRequestQueue(type, tag, methods, null);

    }

    protected void startRequest(){
        if(network !=null)
            network.start();
    }

}
