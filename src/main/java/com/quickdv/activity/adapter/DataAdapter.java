package com.quickdv.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.quickdv.R;
import com.quickdv.activity.until.BitmapCache;

import java.util.Collection;
import java.util.List;

/**
 * Created by lilongfei on 15/4/8.
 * <p/>
 * 通用数据适配器
 */
public abstract class DataAdapter<T> extends BaseAdapter {

    protected Context c;
    protected List<T> data;
    protected LayoutInflater inflater;
    int[] ids;

    public DataAdapter(Context c, List<T> data) {
        this.c = c;
        this.data = data;
        this.inflater = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return data != null ? data.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return data != null ? data.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = getLayoutView(parent);
        }
        renderData(position, convertView);
        return convertView;
    }

    public final View getResourceView(int id) {
        return inflater.inflate(id, null);
    }

    public void insert(T data) {
        this.data.add(0, data);
        this.notifyDataSetChanged();
    }

    public void append(T data) {
        this.data.add(data);
        this.notifyDataSetChanged();
    }

    public void appendAll(Collection<T> data)
    {
        this.data.addAll(data);
        this.notifyDataSetChanged();
    }

    public void appendAll(int index,Collection<T> data){
        if(index < 0) return;
    }

    public void replace(T data) {
        int index = this.data.indexOf(data);
        if (index != -1)
            this.data.set(index, data);
    }

    public void replace(int index, T data) {
        if (index < 0) return;
        if (index > this.data.size() - 1) return;
        this.data.set(index, data);
        this.notifyDataSetChanged();
    }

    public void removeItem(int index, T data) {
        if (index < 0) return;
        if (index > this.data.size() - 1) return;
        if (this.data.size() <= 0) return;
        this.data.remove(index);
        this.notifyDataSetChanged();

    }

    public void clear() {
        this.data.clear();
        this.notifyDataSetChanged();
    }

    protected void setViewText(TextView v, String txt) {
        v.setText(txt);
    }

    protected void setViewText(TextView v, int txtId) {
        v.setText(c.getResources().getText(txtId));
    }

    protected void setImageSrc(ImageView v, int data) {
        v.setImageResource((Integer) data);
    }

    protected void setImageSrc(ImageView v, String txt) {

        try {
            if (txt != null && txt.length() > 0) {
                v.setImageResource(Integer.parseInt(txt));
            }else
                v.setVisibility(View.GONE);
        } catch (NumberFormatException nfe) {

            RequestQueue mQueue = Volley.newRequestQueue(c.getApplicationContext());

            ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());

            ImageLoader.ImageListener listener = ImageLoader.getImageListener(v, R.drawable.ic_launcher, R.drawable.ic_launcher);
            imageLoader.get(txt, listener);
            //指定图片允许的最大宽度和高度
            //imageLoader.get("http://developer.android.com/images/home/aw_dac.png",listener, 200, 200);
        }
    }

    protected abstract View getLayoutView(ViewGroup parent);

    protected abstract int[] getViewID();

    protected abstract void renderData(int position, View convertView);

}
