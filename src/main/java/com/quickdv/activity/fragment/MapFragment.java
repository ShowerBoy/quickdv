package com.quickdv.activity.fragment;

import android.os.Bundle;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;

/**
 * Created by Administrator on 2015/5/27.
 */
public abstract class MapFragment extends BaseFragment {



    @Override
    protected int getLayoutViewID() {
        return 0;
    }

    protected MapView mapView;
    protected AMap aMap;

    public MapView getMapView(){
        return mapView;
    }

    public AMap getaMap(){
        return aMap;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapView.onCreate(savedInstanceState);// 必须要写
        init();
    }

    /**
     * 初始化AMap对象
     */
    protected void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
    }
    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

}
