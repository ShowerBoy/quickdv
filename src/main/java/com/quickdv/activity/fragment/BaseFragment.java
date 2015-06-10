package com.quickdv.activity.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.quickdv.R;
import com.quickdv.activity.helper.ActivityHelp;
import com.quickdv.activity.imp.BaseImp;
import com.quickdv.activity.network.HttpConnect;
import com.quickdv.activity.network.SucceesInter;
import com.quickdv.activity.until.LogHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BaseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BaseFragment#} factory method to
 * create an instance of this fragment.
 */
public abstract class BaseFragment extends Fragment implements BaseImp,Response.ErrorListener,SucceesInter {

    protected OnFragmentInteractionListener mListener;

    protected Activity activity;

    protected static String Tag;

    protected View root;

    private ActivityHelp help;

    protected String mParam1;

    protected static final String ARG_PARAM1 = "param1";

    protected HttpConnect network;
    protected ProgressDialog dialog;

    protected LogHelper log;

    public boolean firstLoad = true;

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        log = LogHelper.getInstance();
        try {
            this.activity = activity;
            mListener = (OnFragmentInteractionListener) activity;
            help = new ActivityHelp(activity);
            network = new HttpConnect(activity,this,this);
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(getLayoutViewID(),container,false);
        initView();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }

        initData();
        return root;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void showNetWorkDialog() {
        if (dialog == null){
            dialog = new ProgressDialog(activity);
            dialog.setTitle(getResources().getString(R.string.network_title));
            dialog.setMessage(getResources().getString(R.string.network_message));
        }
        dialog.show();
    }

    public void dismissNetWorkDialog(){
        if (dialog.isShowing())
            dialog.dismiss();
    }


    public AlertDialog buildErro(){
        AlertDialog dialog_error = new AlertDialog.Builder(activity).setMessage(getResources().getString(R.string.network_error))
                .setNegativeButton("确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            initData();
                    }
                }).setCancelable(false).create();
        return dialog_error;
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
    protected void addToRequestQueue(int type, Object tag, String methods, Map<String, Object> param) {
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

    protected void startRequest(){
        showNetWorkDialog();
        if(network !=null)
            network.start();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        dismissNetWorkDialog();
        network.stopRequest();
        AlertDialog dialog_error = buildErro();
        dialog_error.show();
    }

    @Override
    public void onResponse(Object tag, JSONObject result) {
        try {
            isOk(activity,result);
        } catch (JSONException e) {
            dismissNetWorkDialog();
            network.stopRequest();
            AlertDialog dialog_error = buildErro();
            dialog_error.show();
        }

    }

    public View getRootView(){
        return root;
    }

    @Override
    public <T extends View> T findView(int id){
        return (T) help.findView(root,id);
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public String getFragTag(){
        return Tag;
    }

    @Override
    public void startActivity(String action){
        help.startActivity(action);
    }

    @Override
    public void startActivity(String action,Bundle bundle){
        help.startActivity(action,bundle);
    }

    public boolean isOk(Context context ,JSONObject obj) throws JSONException {return help.isOk(context,obj);}

    protected abstract void initView();

    protected abstract int getLayoutViewID();

    protected void initData() {

    }
}
