package com.shuaisun.videolive;

import android.app.Application;

import com.aihuishou.commonlib.BaseAppContext;

/**
 * Created by shuaisun on 2017/9/17.
 */

public class AppAplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        BaseAppContext.init(getApplicationContext());
    }
}
