package com.quickdv.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.quickdv.activity.bean.ActivityHelp;
import com.quickdv.activity.imp.ActivityImp;
import com.quickdv.activity.imp.BaseImp;
import com.quickdv.activity.network.HttpConnect;
import com.quickdv.activity.network.SucceesInter;

import org.json.JSONObject;

public abstract class BaseActivity extends ActionBarActivity implements ActivityImp,BaseImp,Response.ErrorListener,SucceesInter {

    //为activity提供一些公用的事件和函数
    private ActivityHelp help;
    protected HttpConnect network;
    protected ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        help = new ActivityHelp(this);

        super.onCreate(savedInstanceState);
        network = new HttpConnect(this,this,this);
        dialog = new ProgressDialog(this);
        dialog.setTitle("获取网络数据中");
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initView();
    }

    @Override
    public View customActionBar() {
        return null;
    }

    /**是否是自定义的actionbar抬头*/
    public void isBuildActionBar(){
        View actionBarCustomView = customActionBar();
        if(actionBarCustomView == null) return;
        getSupportActionBar().hide();

    }

    public void showNetWorkDialog() {
        if (dialog == null){
            dialog = new ProgressDialog(this);
            dialog.setTitle("获取网络数据中");
        }
        dialog.show();
    }

    public void dismissNetWorkDialog(){
        if (dialog.isShowing())
            dialog.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initActionbar();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        dismissNetWorkDialog();
    }

    @Override
    public void onResponse(String tag, JSONObject result) {
        dismissNetWorkDialog();
    }

    /**
     * 添加网络请求队列
     * */
    protected void addToRequestQueue(int type,  String tag, JSONObject param){

        network.addToRequestQueue(type,tag,param);
    }

    /**
     * 添加网络请求队列
     * */
    protected void addToRequestQueue(int type,  String tag, String methods,JSONObject param){
        network.addToRequestQueue(type,tag,methods,param);

    }

    protected void startRequest(){
        showNetWorkDialog();
        if(network !=null)
            network.start();
    }


    @Override
    public void startActivity(String action) {
        help.startActivity(action);
    }

    @Override
    public void startActivity(String action, Bundle bundle) {
        help.startActivity(action,bundle);
    }

    @Override
    public <T extends View> T findView(int id) {
        return (T)help.findView(getWindow().getDecorView(),id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(network !=null)
            network.stopRequest();
        network = null;
    }
}
