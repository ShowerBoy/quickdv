package com.quickdv.activity.until;

import android.util.Log;

/**
 * Created by Administrator on 2015/6/4.
 */
public class LogHelper {

    private static LogHelper log;
    private boolean isLog = true;
    private LogHelper(){};

    public static LogHelper getInstance(){
        if(log == null)
            log =new LogHelper();
        return log;
    }

    public void v(String tag, String v){
        if(isLog)
            Log.v(tag,v);
    }

    public void e(String tag,String e){
        if (isLog)
            Log.e(tag,e);
    }

    public void wtf(String tag,String wtf){
        if (isLog)
            log.wtf(tag,wtf);
    }

    public void isLog(Boolean isLog){
        this.isLog = isLog;
    }
}
