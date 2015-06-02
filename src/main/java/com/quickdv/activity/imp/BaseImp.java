package com.quickdv.activity.imp;

import android.os.Bundle;
import android.view.View;

/**
 * Created by lilongfei on 15/5/4.
 */
public interface BaseImp {

    void startActivity(String action);
    void startActivity(String action,Bundle bundle);
    <T extends View> T findView(int id);
}
