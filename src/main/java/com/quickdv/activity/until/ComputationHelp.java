package com.quickdv.activity.until;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by lilongfei on 15/5/5.
 */
public class ComputationHelp {

    /**
     * 设置listView的高度
     * */
    public static void setListHeight(ListView lv){
        //纪录总高度
        int mTotalHeight = 0;
        //遍历child节点

        int count = lv.getAdapter()!=null? lv.getAdapter().getCount():0;
        for(int i = 0;i< count;i++){
            View view = lv.getAdapter().getView(i,null,lv);
            //计算childView的高度与宽度
//            int measureWidth = view.getMeasuredWidth();
//            int measureHeight = view.getMeasuredHeight();
//            view.layout(l,mTotalHeight,measureWidth,mTotalHeight+measureHeight);
            if (view == null) {
                Log.v("this view ","is null");
            }
//            view.setLayoutParams(new ViewGroup.LayoutParams(Layout));
            view.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            );
            mTotalHeight += view.getMeasuredHeight();
        }

        //设置listView的layoutparam 和高度
        ViewGroup.LayoutParams params = lv.getLayoutParams();
        params.height = mTotalHeight + lv.getDividerHeight() * (lv.getCount() - 1);

        lv.setLayoutParams(params);
        lv.requestLayout();
    }

    /**
     * 通过child的高度绘制listView的容器的高度
     * */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
