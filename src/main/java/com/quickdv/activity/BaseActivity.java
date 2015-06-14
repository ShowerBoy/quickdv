package com.quickdv.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.quickdv.R;
import com.quickdv.activity.helper.ActivityHelp;
import com.quickdv.activity.imp.ActivityImp;
import com.quickdv.activity.imp.BaseImp;
import com.quickdv.activity.network.HttpConnect;
import com.quickdv.activity.network.SucceesInter;
import com.quickdv.activity.until.LogHelper;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

public abstract class BaseActivity extends ActionBarActivity implements ActivityImp, BaseImp, Response.ErrorListener, SucceesInter {

    //为activity提供一些公用的事件和函数
    private ActivityHelp help;
    protected HttpConnect network;
    protected ProgressDialog dialog;
    protected AlertDialog dialog_error;
    protected LogHelper log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        help = new ActivityHelp(this);
        log = LogHelper.getInstance();
        super.onCreate(savedInstanceState);
        network = new HttpConnect(this, this, this);
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

    /**
     * 是否是自定义的actionbar抬头
     */
    public void isBuildActionBar() {
        View actionBarCustomView = customActionBar();
        if (actionBarCustomView == null) return;
        getSupportActionBar().hide();

    }

    public void showNetWorkDialog() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.setTitle(getResources().getString(R.string.network_title));
            dialog.setMessage(getResources().getString(R.string.network_message));
        }
        dialog.show();
    }


    public AlertDialog  buildErro(){
        AlertDialog dialog_error = new AlertDialog.Builder(this).setMessage(getResources().getString(R.string.network_error))
                .setNegativeButton("确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
        return dialog_error;
    }

    public void dismissNetWorkDialog() {
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
        network.stopRequest();
        dialog_error = buildErro();
        dialog_error.show();
    }

    @Override
    public void onResponse(Object tag, JSONObject result) {
        dismissNetWorkDialog();
    }

    /**
     * 设置url
     */

    protected void setUrl(String url) {
        network.setUrl(url);
    }

    /**
     * 添加网络请求队列
     */
    protected void addToRequestQueue(int type, String methods, Map<String, Object> param) {

        addToRequestQueue(type, methods, methods, param);
    }

    /**
     * 添加网络请求队列
     */
    protected void addToRequestQueue(int type, String tag, String methods, Map<String, Object> param) {
        if (type == Request.Method.GET && param != null) {
            methods = methods + "/?";
            Iterator<Map.Entry<String, Object>> iterator = param.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entity = iterator.next();
                methods += entity.getKey() + "=" + entity.getValue() + "&";
            }
            if (methods.endsWith("&"))
                methods = methods.substring(0, methods.length() - 1);
            network.addToRequestQueue(type, tag, methods, null);

        } else if (param != null)
            network.addToRequestQueue(type, tag, methods, new JSONObject(param));
        else
            network.addToRequestQueue(type, tag, methods, null);

    }

    protected void startRequest() {
        showNetWorkDialog();
        if (network != null)
            network.start();
    }

    protected  void startRequestNotDialog(){
        if (network != null)
            network.start();
    }


    @Override
    public void startActivity(String action) {
        help.startActivity(action);
    }

    @Override
    public void startActivity(String action, Bundle bundle) {
        help.startActivity(action, bundle);
    }

    @Override
    public <T extends View> T findView(int id) {
        return (T) help.findView(getWindow().getDecorView(), id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (network != null)
            network.stopRequest();
        network = null;
    }

    @Override
    public void initData() {

    }
}
