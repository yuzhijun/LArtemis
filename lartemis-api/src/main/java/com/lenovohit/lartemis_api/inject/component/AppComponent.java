package com.lenovohit.lartemis_api.inject.component;

import com.lenovohit.lartemis_api.base.BaseApplication;
import com.lenovohit.lartemis_api.inject.module.ApiServiceModule;
import com.lenovohit.lartemis_api.inject.module.AppModule;
import com.lenovohit.lartemis_api.network.ApiService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by yuzhijun on 2017/6/15.
 */
@Singleton
@Component(modules = {
        AppModule.class,
        ApiServiceModule.class
})
public interface AppComponent {
    BaseApplication getApplication();
    ApiService getService();

    void inject(BaseApplication app);
}
