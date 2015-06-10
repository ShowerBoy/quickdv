package com.quickdv.activity.helper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lilongfei on 15/5/4.
 *
 * activity helper 对象
 */
public class ActivityHelp {

    Context context;
    public ActivityHelp(Context context){
        this.context = context;
    }

    public void startActivity(String action){
        Intent intent = new Intent();
        intent.setAction(action);
        context.startActivity(intent);
    }

    public void startActivity(String action,Bundle bundle){
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setAction(action);
        context.startActivity(intent);
    }

    public View findView(View root,int id){
        if(root == null) return  null;
        return root.findViewById(id);
    }

    public boolean isOk(Context context,JSONObject obj) throws JSONException {
        if(obj == null){
            return false;
        }else if(obj.getString("code") == null){
            return false;
        }else if(obj.getString("code") == "200"){
            return true;
        }
        return false;
    }


}
