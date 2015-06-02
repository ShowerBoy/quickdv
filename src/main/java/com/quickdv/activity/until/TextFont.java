package com.quickdv.activity.until;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by lilongfei on 15/4/2.
 *
 * 用于加载文字图标
 */
public class TextFont {

    private static String Tag = "com.quickdv.activity.untilTextFont";
    static TextFont font;
    static Typeface iconfont;
    static {
    }
    private TextFont(){}

    public static TextFont getInstance(Context context){
        if(font == null){
            font = new TextFont();

            try {
                iconfont = Typeface.createFromAsset(context.getAssets(), "iconfont.ttf");
                Log.v(Tag,"字库加载成功");
            } catch (Exception e) {
                Log.v(Tag,"加载字库失败，请检查iconfont.ttf 文件是否存在");
            }

        }
        return font;
    }

    /**
     * 给控件加载文字图标
     * */
    public void Text2Icon(TextView tv){
        tv.setTypeface(iconfont);
    }

    public void setTextBottomSize(TextView tv,int textSize){

       SpannableString msp = new SpannableString(tv.getText());
       msp.setSpan(new AbsoluteSizeSpan(textSize,true), 1, msp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
       tv.setText(msp);
    }

}
