package com.onlylady.beautyapp.activitys;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;


public abstract class BaseActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        PushAgent.getInstance(this).onAppStart();
        createview();
        ButterKnife.bind(this);

        initlisener();
        initData();
        if (useEventbus()) {
            EventBus.getDefault().register(this);//并不能所有界面注册
        }


    }

    public abstract boolean useEventbus();
    public abstract void createview();
    public abstract void initlisener();
    protected abstract void  initData();
    @Override
    protected void onResume() {

        super.onResume();
        MobclickAgent.onResume(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (useEventbus()) {
            EventBus.getDefault().unregister(this);
        }


    }


}
