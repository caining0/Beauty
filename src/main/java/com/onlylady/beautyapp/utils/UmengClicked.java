package com.onlylady.beautyapp.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.onlylady.beautyapp.configs.EventBusC;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import de.greenrobot.event.EventBus;

/**
 * Created by caining on 16/3/11.
 */
public class UmengClicked extends UmengNotificationClickHandler {
    @Override
    public void dealWithCustomAction(Context context, UMessage uMessage) {
        // super.dealWithCustomAction(context, uMessage);
        Intent intent = new Intent();
        intent.setClassName(context, "com.onlylady.beautyapp.activitys.SplashActivity");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        HomeActivity.GOTOWHITCH=1;
        context.startActivity(intent);
           new Handler().postDelayed(new Runnable() {
               @Override
               public void run() {
                   EventBus.getDefault().post(EventBusC.getInstance(EventBusC.GOLIVE, null));
               }
           }, 2000);



    }


}

