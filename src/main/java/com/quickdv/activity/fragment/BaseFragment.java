package com.quickdv.activity.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.quickdv.activity.bean.ActivityHelp;
import com.quickdv.activity.imp.BaseImp;
import com.quickdv.activity.network.HttpConnect;
import com.quickdv.activity.network.SucceesInter;

import org.json.JSONObject;

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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
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
            dialog.setTitle("获取网络数据中");
        }
        dialog.show();
    }

    public void dismissNetWorkDialog(){
        if (dialog.isShowing())
            dialog.dismiss();
    }

    /**
     * 添加网络请求队列
     * */
    protected void addToRequestQueue(int type,  String tag, JSONObject param){

        addToRequestQueue(type,tag,tag,param);
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
    public void onErrorResponse(VolleyError error) {
        dismissNetWorkDialog();
        network.stopRequest();

    }

    @Override
    public void onResponse(String tag, JSONObject result) {
        dismissNetWorkDialog();
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

    protected abstract void initView();

    protected abstract int getLayoutViewID();
}
