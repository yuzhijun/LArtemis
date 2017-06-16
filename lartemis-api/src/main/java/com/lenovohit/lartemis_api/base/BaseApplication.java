package com.lenovohit.lartemis_api.base;

import android.app.Application;

import com.lenovohit.lartemis_api.inject.component.AppComponent;
import com.lenovohit.lartemis_api.inject.component.DaggerAppComponent;
import com.lenovohit.lartemis_api.inject.module.AppModule;
import com.lenovohit.lartemis_api.ui.controller.MainController;

import javax.inject.Inject;

/**
 * application基类
 * Created by yuzhijun on 2017/6/16.
 */
public class BaseApplication extends Application{
    public static BaseApplication mBaseApplication;
    private AppComponent appComponent;
    @Inject
    MainController mMainController;

    public MainController getMainController() {
        return mMainController;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBaseApplication = this;

        setupGraph();
    }

    private void setupGraph() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        appComponent.inject(this);
    }

    public static BaseApplication getBaseApplication(){
        return mBaseApplication;
    }
}
