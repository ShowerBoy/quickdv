package com.quickdv.activity.network;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.ResponseDelivery;

/**
 * Created by Administrator on 2015/5/29.
 */
public class NetRequest extends RequestQueue {

    public NetRequest(Cache cache, Network network, int threadPoolSize, ResponseDelivery delivery) {
        super(cache, network, threadPoolSize, delivery);
    }
}
