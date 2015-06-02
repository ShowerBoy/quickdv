package com.quickdv.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.quickdv.R;
import com.quickdv.activity.fragment.BaseFragment;
import com.quickdv.activity.until.Density;
import com.quickdv.activity.until.TextFont;

/**
 * Created by lilongfei on 15/4/2.
 *
 * tabFragment 快速开发库
 *
 * 快速开发页签切换式的fragment activity
 */
public abstract  class DevTabFragmentActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener{

    int checkedColor = 0;
    int uncheckedColor = 0;
    int[] txt;  //文字图标的对应值
    protected BaseFragment[] frags;  //fragment数量
    RadioGroup rdg_group;
    FragmentManager fm;

    {
        setFragments();
        setTxt();
        setCheckedColor();
        setUncheckedColor();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabfragment_act);
    }

    @Override
    public void initView(){
        FragmentTransaction transaction ;

        fm = getSupportFragmentManager();
        if(frags != null){
            rdg_group = buildGroup();
            transaction = fm.beginTransaction();
            for (BaseFragment frag : frags){
                transaction.add(R.id.tabFrame, frag,frag.getFragTag());
            }
            transaction.commit();
        }

        rdg_group.setOnCheckedChangeListener(this);
        rdg_group.check(0);
    }

    //初始化radiobutton group
    protected RadioGroup buildGroup() {

        RadioGroup rdg_group = (RadioGroup) findViewById(R.id.radioGroup);
        rdg_group.setBackgroundResource(getRadioGroupBg());
        //初始化字符图标库
        TextFont font = TextFont.getInstance(this);
        int padding = Density.dip2px(this, 4);
        for(int i = 0 ;i < frags.length;i++){
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.MATCH_PARENT);
            params.weight = 1;
            params.gravity = Gravity.CENTER;

            RadioButton button = new RadioButton(this);
            button.setLayoutParams(params);
            button.setText(txt[i]);
            button.setButtonDrawable(getResources().getDrawable(android.R.color.transparent));
            button.setPadding(padding, padding, padding, padding);
            button.setGravity(Gravity.CENTER);
            button.setId(i);
            button.setTextSize(getIconSize());
            button.setLineSpacing(0,1.2f);
            font.Text2Icon(button);
            font.setTextBottomSize(button,getTextSize());
            rdg_group.addView(button,i);
        }

        return rdg_group;

    }

    public int getIconSize(){
        return Density.px2sp(this,60);
    }

    public int getTextSize(){
        return Density.px2sp(this,25);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        hideFragment();
        resetButtonColor();
        showFragment(checkedId);
        ((RadioButton)rdg_group.getChildAt(checkedId)).setTextColor(getResources().getColor(checkedColor));
        onTabChanged(checkedId);
    }

    private void hideFragment(){
        FragmentTransaction trans = fm.beginTransaction();
        if(frags != null){
            for(Fragment frag :frags){
                trans.hide(frag);
            }
        }
        trans.commit();
    }

    private void resetButtonColor(){
        for(int i =0 ; i< rdg_group.getChildCount();i++) {
            ((RadioButton)rdg_group.getChildAt(i)).setTextColor(getResources().getColor(uncheckedColor));
        }
    }

    private void showFragment(int index){
        FragmentTransaction trans = fm.beginTransaction();
        trans.show(frags[index]);
        trans.commit();
    }

    protected void setFragments(){
        this.frags = getFragments();
    }
    protected void setTxt(){ this.txt = getTxt();}

    protected void setCheckedColor(){
        this.checkedColor =  getCheckedColor();
    }
    protected void setUncheckedColor(){
        this.uncheckedColor = getUncheckedColor();
    }

    protected abstract BaseFragment[] getFragments();

    protected abstract int[] getTxt();

    protected abstract int getRadioGroupBg();

    protected abstract void onTabChanged(int checkedId);

    protected abstract int getCheckedColor();

    protected abstract int getUncheckedColor();
}
