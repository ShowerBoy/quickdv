package com.quickdv.activity.network;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Created by Administrator on 2015/6/3.
 */
public class HttpClientCon {


    String uri = "http://192.168.2.101:8080/xiaolingtong/mobile/http/api.do";

    public void post(Map<String,Object> params) {
        BufferedReader in = null;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost request = new HttpPost(uri);
//            //使用NameValuePair来保存要传递的Post参数

            //convert parameters into JSON object
            JSONObject holder = getJsonObjectFromMap(params);

            //passes the results to a string builder/entity
            StringEntity se = new StringEntity(holder.toString());

            //使用HttpPost对象来设置UrlEncodedFormEntity的Entity
            request.setEntity(se);
            HttpResponse response = client.execute(request);
            in = new BufferedReader(
                    new InputStreamReader(
                            response.getEntity().getContent()));

            StringBuffer string = new StringBuffer("");
            String lineStr = "";
            while ((lineStr = in.readLine()) != null) {
                string.append(lineStr + "\n");
            }
            in.close();

            String resultStr = string.toString();
            Log.v("",resultStr);

        } catch(Exception e) {
            // Do something about exceptions
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

   public JSONObject  getJsonObjectFromMap(Map<String,Object> params){
       return new JSONObject(params);
   }
}
