package com.quickdv.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.quickdv.activity.until.BitmapCache;

/**
 * Created by lilongfei on 15/4/29.
 */
public abstract  class BaseViewHolderAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T>{

    protected Context c;
    public BaseViewHolderAdapter(Context c){
        this.c = c;
    }

    protected void setViewText(TextView v ,String  txt){
        v.setText(txt);
    }

    protected void setViewText(TextView v,int txtId){ v.setText(c.getResources().getText(txtId));}

    protected void setImageSrc(ImageView v ,int data){
        v.setImageResource((Integer)data);
    }

    protected void setImageSrc(ImageView v ,String txt){

        try {
            v.setImageResource(Integer.parseInt(txt));
        } catch (NumberFormatException nfe) {

            RequestQueue mQueue = Volley.newRequestQueue(c.getApplicationContext());

            ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());

            ImageLoader.ImageListener listener = ImageLoader.getImageListener(v, com.quickdv.R.drawable.ic_launcher, com.quickdv.R.drawable.ic_launcher);
            imageLoader.get(txt, listener);
            //指定图片允许的最大宽度和高度
            //imageLoader.get("http://developer.android.com/images/home/aw_dac.png",listener, 200, 200);
        }
    }
}
