package com.onlylady.beautyapp.utils;

import android.app.Notification;
import android.content.Context;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;

import com.onlylady.beautyapp.R;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengRegistrar;
import com.umeng.message.entity.UMessage;

/**
 * Created by caining on 16/3/8.
 * 友盟工具类
 */
public class UmengUtils {
    public static UmengUtils timeUtils;

    public static UmengUtils getInstance() {
        if (timeUtils == null) {
            timeUtils = new UmengUtils();
        }
        return timeUtils;
    }

    /**
     * 获取device_token
     * @param context
     * @return
     */

    public String getUmengDeviceToken(Context context){
        String device_token = null;
        device_token = SharedPrefeUtils.getSettings(context,SharedPrefeUtils.DEVICETOKEN);
        if (!TextUtils.isEmpty(device_token)){
            return device_token;
        }else {
            device_token= UmengRegistrar.getRegistrationId(context);
            SharedPrefeUtils.saveSettings(context,SharedPrefeUtils.DEVICETOKEN,device_token);
            return device_token;
        }




    }
    public void umengSDKInit(PushAgent mPushAgent,Context context) {
        // 友盟设置

//        PushAgent mPushAgent = PushAgent.getInstance(context);

//        mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);

        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            @Override
            public Notification getNotification(Context context, UMessage uMessage) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                builder.setSmallIcon(R.mipmap.ic_launcher)
                        .setContentText(uMessage.text)
                        .setAutoCancel(true)
                        .setContentTitle(uMessage.title);
                Notification notification = builder.build();
                return notification;
            }
        };
        mPushAgent.setMessageHandler(messageHandler);
        mPushAgent.setNotificationClickHandler(new UmengClicked());
//        mPushAgent.setMergeNotificaiton(false);
        mPushAgent.enable();
        String device_token = UmengRegistrar.getRegistrationId(context);
        LogUtils.Log(device_token);
        if (!TextUtils.isEmpty(device_token)) {
            SharedPrefeUtils.saveSettings(context, SharedPrefeUtils.DEVICETOKEN, device_token);
        }
    }
}
