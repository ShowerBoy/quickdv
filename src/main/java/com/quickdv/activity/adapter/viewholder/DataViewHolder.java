package com.quickdv.activity.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by lilongfei on 15/4/27.
 */
public class DataViewHolder extends RecyclerView.ViewHolder {
    SparseArray<View> holder;
    View itemView;

    public DataViewHolder(View itemView, int[] ids) {
        super(itemView);
        this.itemView = itemView;
        holder = new SparseArray<>();
        for (int id : ids) {
            View view = itemView.findViewById(id);
            holder.put(id, view);
        }
    }

    public View getView(int id) {
        if (holder != null)
            return holder.get(id);
        return null;
    }

    public View getRootView(){
        return this.itemView;
    }

    public View findView(int id) {
        return itemView.findViewById(id);
    }
}
