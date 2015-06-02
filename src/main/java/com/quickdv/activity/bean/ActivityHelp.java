package com.quickdv.activity.bean;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
}
