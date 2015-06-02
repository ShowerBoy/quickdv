package com.quickdv.activity.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by lilongfei on 15/5/3.
 */
public class CustomListView extends ListView {

    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
//        //纪录总高度
//        int mTotalHeight = 0;
//        //遍历child节点
//
//        int count = getAdapter()!=null? getAdapter().getCount():0;
//        for(int i = 0;i< count;i++){
//            View view = getAdapter().getView(i,getChildAt(i),this);
//            //计算childView的高度与宽度
////            int measureWidth = view.getMeasuredWidth();
////            int measureHeight = view.getMeasuredHeight();
////            view.layout(l,mTotalHeight,measureWidth,mTotalHeight+measureHeight);
//            view.measure(
//                    View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
//                    View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
//            );
//            mTotalHeight += view.getMeasuredHeight();
//        }
//
//        //设置listView的layoutparam 和高度
//        ViewGroup.LayoutParams params = this.getLayoutParams();
//        params.height = mTotalHeight + getDividerHeight() * (getCount() - 1);
//        setLayoutParams(params);
//        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int measureWidth = measureWidth(widthMeasureSpec);
//        int measureHeight = measureHeight(heightMeasureSpec);
//        measureChildren(widthMeasureSpec,heightMeasureSpec);
//        setMeasuredDimension(measureWidth,measureHeight);
    }


    private int measureWidth(int pWidthMeasureSpec) {
        int result = 0;
        int widthMode = MeasureSpec.getMode(pWidthMeasureSpec);// 得到模式
        int widthSize = MeasureSpec.getSize(pWidthMeasureSpec);// 得到尺寸

        switch (widthMode) {
            /**
             * mode共有三种情况，取值分别为MeasureSpec.UNSPECIFIED, MeasureSpec.EXACTLY,
             * MeasureSpec.AT_MOST。
             *
             *
             * MeasureSpec.EXACTLY是精确尺寸，
             * 当我们将控件的layout_width或layout_height指定为具体数值时如andorid
             * :layout_width="50dip"，或者为FILL_PARENT是，都是控件大小已经确定的情况，都是精确尺寸。
             *
             *
             * MeasureSpec.AT_MOST是最大尺寸，
             * 当控件的layout_width或layout_height指定为WRAP_CONTENT时
             * ，控件大小一般随着控件的子空间或内容进行变化，此时控件尺寸只要不超过父控件允许的最大尺寸即可
             * 。因此，此时的mode是AT_MOST，size给出了父控件允许的最大尺寸。
             *
             *
             * MeasureSpec.UNSPECIFIED是未指定尺寸，这种情况不多，一般都是父控件是AdapterView，
             * 通过measure方法传入的模式。
             */
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = widthSize;
                break;
        }
        return result;
    }

    private int measureHeight(int pHeightMeasureSpec) {
        int result = 0;

        int heightMode = MeasureSpec.getMode(pHeightMeasureSpec);
        int heightSize = MeasureSpec.getSize(pHeightMeasureSpec);

        switch (heightMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = heightSize;
                break;
        }
        return result;
    }
}
