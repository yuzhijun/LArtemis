package com.lenovohit.lartemis_api.ui.controller;

import com.google.common.base.Preconditions;
import com.lenovohit.lartemis_api.base.BaseController;
import com.lenovohit.lartemis_api.network.ApiService;

import javax.inject.Inject;

/**
 * 主页p层控制器
 * Created by yuzhijun on 2017/6/16.
 */
public class MainController extends BaseController<MainController.MainUi,MainController.MainUiCallbacks>{
    private ApiService mApiService;

    @Inject
    public MainController(ApiService apiService){
        mApiService = Preconditions.checkNotNull(apiService, "ApiService cannot be null");
    }

    @Override
    protected void onInited() {
        super.onInited();
        //其他controller则在这里init
    }

    @Override
    protected void onSuspended() {
        super.onSuspended();
        //其他controller则在这里suspend
    }

    @Override
    protected  void populateUi(MainUi ui) {
        super.populateUi(ui);
    }

    @Override
    protected MainUiCallbacks createUiCallbacks(MainUi ui) {
        return null;
    }

    public interface MainUiCallbacks{

    }

    public interface MainUi extends BaseController.Ui<MainUiCallbacks> {

    }
}
