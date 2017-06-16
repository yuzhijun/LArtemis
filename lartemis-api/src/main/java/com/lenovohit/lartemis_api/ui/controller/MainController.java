package com.lenovohit.lartemis_api.ui.controller;

import com.google.common.base.Preconditions;
import com.lenovohit.lartemis_api.base.BaseController;
import com.lenovohit.lartemis_api.model.HttpResult;
import com.lenovohit.lartemis_api.model.Weather;
import com.lenovohit.lartemis_api.network.ApiService;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 主页p层控制器
 * Created by yuzhijun on 2017/6/16.
 */
public class MainController extends BaseController<MainController.MainUi,MainController.MainUiCallbacks>{
    private static final String IP = "63.223.108.42";
    private ApiService mApiService;

    @Inject
    public MainController(ApiService apiService){
        super();
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
    protected MainUiCallbacks createUiCallbacks(final MainUi ui) {
        return new MainUiCallbacks() {
            @Override
            public void getWeatherInfo() {
                mApiService.getWeatherResult(IP)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<HttpResult<Weather>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HttpResult<Weather> weatherHttpResult) {
                        ui.showToast();
                    }
                });
            }
        };
    }

    public interface MainUiCallbacks{//给UI界面调用
        void getWeatherInfo();
    }

    public interface MainUi extends BaseController.Ui<MainUiCallbacks> {
        //这里面的方式是给UI界面调用的，要activity等UI层实现
        void showToast();
    }
}
