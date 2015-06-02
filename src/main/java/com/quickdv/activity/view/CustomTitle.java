package com.quickdv.activity.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nineoldandroids.animation.ObjectAnimator;
import com.quickdv.R;
import com.quickdv.activity.until.Density;

/**
 * Created by lilongfei on 15/4/26.
 */
public class CustomTitle extends RelativeLayout implements View.OnClickListener {

    private String[] title;
    private Context context;
    TextView line;
    int selected_color, title_color;
    OnClickListener onclick;
    LinearLayout ll;
    boolean isClick;

    public CustomTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        buildLayout();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.Title);
        selected_color = array.getColor(R.styleable.Title_selectColor, R.color.block);
        title_color = array.getColor(R.styleable.Title_titleColor, R.color.white);
    }

    private void buildLayout() {
        ll = new LinearLayout(context);
        LayoutParams ll_param = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        addView(ll, ll_param);
    }

    private void buildView() {
        if (title == null) return;
        int i = 0;
        for (String t : title) {
            TextView textView = new TextView(context);
            textView.setText(t);
            textView.setId(i);
            textView.setTextSize(Density.sp2px(context, 8));
            textView.setTextColor(title_color);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.weight = 1;
            params.gravity = Gravity.CENTER;
            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER);
            textView.setOnClickListener(this);
            ll.addView(textView);
            i++;
        }

    }

    private void buildLine() {
        if (title == null) return;
        line = new TextView(context);
        line.setBackgroundColor(selected_color);
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = manager.getDefaultDisplay().getWidth();
        LayoutParams params = new LayoutParams(width / title.length, Density.dip2px(context, 3));
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        addView(line, params);
    }

    public boolean getIsClick(){return isClick;}

    public void moveLine(int position) {
        for (int i = 0; i < ll.getChildCount(); i++) {
            TextView textView = (TextView) ll.getChildAt(i);
            textView.setTextColor(title_color);
        }
        ((TextView) ll.getChildAt(position)).setTextColor(selected_color);
        ObjectAnimator animator = ObjectAnimator.ofFloat(line, "translationX", line.getX(), line.getWidth());
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(500);
        animator.start();

    }

    public void setTitle(String[] title) {
        this.title = title;
        buildView();
        buildLine();
    }

    public void dataChanged() {
        ll.removeAllViews();
        buildView();
    }

    public void setOnItemClickListener(OnClickListener onclick) {
        this.onclick = onclick;
    }

    @Override
    public void onClick(View v) {
        if (onclick != null) {
            isClick =true;
            onclick.onClick(v);
        }
    }
}
