package com.quickdv.activity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import com.quickdv.activity.until.ViewHolder;

import java.util.List;
import java.util.Map;

/**
 * Created by lilongfei on 15/4/9.
 */
public abstract  class DevSimpleAdapter extends DataAdapter<Map<String,Object>> {

    protected int[] ids;
    protected String[] key;
    protected int layout;

    public DevSimpleAdapter(Context c,List<Map<String,Object>> data,int layout,String[] from,int[] to){
        super(c,data);
        this.ids = to;
        this.key = from;
        this.layout = layout;
    }

    @Override
    protected View getLayoutView(ViewGroup parent) {
        return inflater.inflate(layout,parent,false);
    }

    @Override
    protected void renderData(int position, View convertView) {
        for (int i = 0; i < ids.length; i++) {
            View view = ViewHolder.get(convertView, ids[i]);
            final Object data = getData(position,key[i]);
            String text = data == null ? "" : data.toString();
            if (view instanceof ImageView) {
                if (data instanceof Integer)
                    setImageSrc((ImageView)view, (Integer)data);
                else
                    setImageSrc((ImageView)view, text);
            } else if (view instanceof TextView) {
                if(data instanceof Integer){
                    setViewText((TextView)view,(Integer)data);
                }else
                    setViewText((TextView)view, text);
            }
            if (view instanceof Checkable) {
                //如果数据类型是boolean，那么就是CheckBox
                if (data instanceof Boolean) {
                    ((Checkable) view).setChecked((Boolean) data);
                    //如果不是CheckBox，那么判断是不是继承TextView的CheckedTextView，是的话赋值，不是就抛出异常
                } else if (view instanceof TextView) {
                    setViewText((TextView) view, text);
                } else {
                    throw new IllegalStateException(view.getClass()
                            .getName()
                            + " should be bound to a Boolean, not a "
                            + (data == null ? "<unknown type>"
                            : data.getClass()));
                }
            }
        }
    }

    protected Object getData(int position,String key){
        if(position > data.size()) return null;
        return this.data.get(position).get(key);
    }
}
