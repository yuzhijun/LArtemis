# LArtemis
![Logo](https://github.com/yuzhijun/LArtemis/blob/master/app/src/main/logo/main_logo.png)
```
p层利用率更高的mvp架构
```
---

## 1.开始使用

### 1.1.在项目中集成
```
1.首先在要使用此架构的工程模块中添加项目依赖 compile project(':lartemis-api')
```

### 1.2.创建自定义applicaiton

在你的项目模块中创建自定义application然后在oncreate中初始化框架

```
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LArtemis.getInstance().init(this);//此处
    初始化框架}
}
```
此处继承application如果有需要可以换成自己的application

然后在AndroidManifest.xml中加入

```
<application
      ...
      android:name=".CustomApplication"
      ...
      >
      ...
</application>
```
### 1.3.创建一个新的Controller

#### 1.3.1一个Controller对应于一个大的模块，可以有对应于多个Activity或者Fragment，创建给UI界面调用的接口和回调UI界面的接口
```
 public interface SecondUiCallbacks{//给UI界面调用
        void getWeatherInfo();
    }
    
    public interface SecondUi extends BaseController.Ui<SecondController.SecondUiCallbacks> {
        //这里面的方式是给UI界面调用的，要activity等UI层实现
        void showToast();
    }
```
如果同个模块但是不同的调用接口则可以在SecondUiCallbacks进行添加方法，例如：
``` public interface SecondUiCallbacks{//给UI界面调用
        void getWeatherInfo();
        void getWeatherInfo1();
    }
  //然后在此Controller中实现
    @Override
    protected SecondUiCallbacks createUiCallbacks(SecondUi ui) {
        return new SecondUiCallbacks() {
            @Override
            public void getWeatherInfo() {

            }

            @Override
            public void getWeatherInfo1() {

            }
        };
    }
    //如果回调的界面不同但是又属于当前模块则可以在此Controller中添加
     public interface thirdUi extends SecondUi{
        void showToast1();
    }
```
#### 1.3.2.新建的Controller需要构成函数加上@Inject注解
```
  @Inject
    public void SecondController(ApiService apiService){
        mApiService = Preconditions.checkNotNull(apiService, "ApiService cannot be null");
    }
```
同时如果有必要则重写生命周期OnInited()，populateUi()方法相当于OnResume()但是OnInited在populateUi之前执行，onSuspended相当于onResume
，例如 EventUtil.register(this);在OnInited()中执行，EventUtil.unregister(this);放在onSuspended()中执行，进来就加载网络数据放在populateUi()
```
  @Override
    protected void onInited() {
        super.onInited();
    }

    @Override
    protected void onSuspended() {
        super.onSuspended();
    }

    @Override
    protected synchronized void populateUi(SecondUi ui) {
        super.populateUi(ui);
    }
```
#### 1.3.3.新建的Controller需要在MainController的构造函数传入且调用一些初始化方法
```
  @Inject
    public MainController(ApiService apiService,SecondController secondController){
        super();
        mApiService = Preconditions.checkNotNull(apiService, "ApiService cannot be null");
        mSecondController = Preconditions.checkNotNull(secondController, "secondController cannot be null");
    }
    
     @Override
    protected void onInited() {
        super.onInited();
        //其他controller则在这里init
        mSecondController.init();
    }
    
     @Override
    protected void onSuspended() {
        super.onSuspended();
        //其他controller则在这里suspend
        mSecondController.suspend();
    }
    
    //然后添加getter方法
     public SecondController getSecondController() {
        return mSecondController;
    }
```
### 1.4.新建一个Acitivity
新建一个activity需要在类上面注解写入layout布局，且实现一个UI接口
```
@ContentView(R.layout.activity_main)
public class MainActivity extends CoreActivity<MainController.MainUiCallbacks> implements MainController.MainUi {
}
```

### 1.5.新建一个Fragment
新建一个fragment跟activity类似
```
@ContentView(R.layout.activity_main)
public class MainFragment extends BaseFragment<MainController.MainUiCallbacks> implements MainController.MainUi{
}
```
如果有参数则在如果方法中获取
```
  @Override
    protected void handleArguments(Bundle arguments) {
    }
```
获取UI中控件则
```
 @Override
    protected void initViews(Bundle savedInstanceState) {
        
    }
```

## 2.使用

### 2.1.在View层进行调用
```
 button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCallbacks().getWeatherInfo();
            }
        });
```
fragment和activity中调用是类似的

### 2.2.Controller层回调View层

如果Controller是对应多个UI界面则需要判断
```
  @Override
    protected MainUiCallbacks createUiCallbacks(final MainUi ui) {
        return new MainUiCallbacks() {
            @Override
            public void getWeatherInfo() {
                if (ui instanceof MainUi){

                }
                mApiService.getWeatherResult(IP)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new RequestCallBack<HttpResult<Weather>>() {
                            @Override
                            public void onResponse(HttpResult<Weather> response) {
                               ui.showToast();
                            }

                            @Override
                            public void onFailure(ResponseError error) {
                                ui.onResponseError(error);
                            }
                        });
            }
        };
    }
```

