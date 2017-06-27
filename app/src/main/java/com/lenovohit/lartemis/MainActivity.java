package com.lenovohit.lartemis;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lenovohit.lartemis_api.annotation.ContentView;
import com.lenovohit.lartemis_api.base.BaseController;
import com.lenovohit.lartemis_api.base.CoreActivity;
import com.lenovohit.lartemis_api.core.LArtemis;
import com.lenovohit.lartemis_api.ui.controller.MainController;

@ContentView(R.layout.activity_main)
public class MainActivity extends CoreActivity<MainController.MainUiCallbacks> implements MainController.MainUi {
    private Button button1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        button1 = (Button) findViewById(R.id.btnNetWork);

        initEvent();
    }

    private void initEvent(){
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCallbacks().getWeatherInfo();
            }
        });
    }

    @Override
    protected BaseController getController() {
        return LArtemis.getInstance().getMainController();
    }

    @Override
    public void showToast() {
        Toast.makeText(this, "返回网络成功", Toast.LENGTH_SHORT).show();
    }
}
