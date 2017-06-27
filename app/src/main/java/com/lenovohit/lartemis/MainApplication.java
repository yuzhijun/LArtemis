package com.lenovohit.lartemis;

import android.app.Application;

import com.lenovohit.lartemis_api.core.LArtemis;

/**
 * Created by yuzhijun on 2017/6/16.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LArtemis.getInstance().init(this);
    }
}
