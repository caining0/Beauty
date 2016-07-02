package com.onlylady.beautyapp.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.onlylady.beautyapp.configs.EventBusC;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2015/10/22.
 */
public class NetWorkState {
    private static int connectcount = 0;
    private static NetWorkState netWorkState;

    public static NetWorkState getInstance() {
        if (null == netWorkState)
            netWorkState = new NetWorkState();

        return netWorkState;
    }

    public void registReceiver(Context context) {
        IntentFilter filter = new IntentFilter(
                ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(myNetReceiver, filter);
    }

    public void unregistReceiver(Context context) {
        if (myNetReceiver != null) {
            context.unregisterReceiver(myNetReceiver);
        }
    }


    private ConnectivityManager mConnectivityManager;

    private NetworkInfo netInfo;

/////////////监听网络状态变化的广播接收器

    private BroadcastReceiver myNetReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            connectcount++;

            boolean connect = false;
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {

                mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                netInfo = mConnectivityManager.getActiveNetworkInfo();
                if (netInfo != null && netInfo.isAvailable()) {
                    connect = true;
                    /////////////网络连接
                    String name = netInfo.getTypeName();
                    LogUtils.Log(name);
//                    PhoneInfo.conn=name;
                    if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                        /////WiFi网络
//                        Toast.makeText(context,"wifi"+name,Toast.LENGTH_SHORT).show();
                        PhoneInfo.conn = 1;

                    } else if (netInfo.getType() == ConnectivityManager.TYPE_ETHERNET) {
//                        Toast.makeText(context,"有线网络",Toast.LENGTH_SHORT).show();
                        /////有线网络

                    } else if (netInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                        /////////3g网络
//                        Toast.makeText(context,"3g网络",Toast.LENGTH_SHORT).show();
                        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                        int type = telephonyManager.getNetworkType();
                        PhoneInfo.conn = getNetworkClass(type);
                    }


                    refresh();
                } else {
                    connect = false;
                    ////////网络断开
                    LogUtils.Log("网络断开");
                    connectfalse();
                    Toast.makeText(context, "当前网络不可用，请检查你的网络设置", Toast.LENGTH_SHORT).show();
                }
                final Bundle bundle = new Bundle();
                bundle.putBoolean("connect", connect);
                if (connectcount > 1 || !connect)//
                    EventBus.getDefault().post(EventBusC.getInstance(EventBusC.NETCONNET, bundle));


            }

        }
    };

    private void connectfalse() {
        EventBus.getDefault().post(EventBusC.getInstance(EventBusC.STOPZHIBO,null));// TODO: 16/4/20 停止直播
    }

    private void refresh() {
        EventBus.getDefault().post(EventBusC.getInstance(EventBusC.MINEREFRESS,null));// TODO: 16/4/20 通知我的直播页
    }


    private int getNetworkClass(int networkType) {
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return 2;//2g
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return 3;//3g
            case TelephonyManager.NETWORK_TYPE_LTE:
                return 4;//4g
        }
        return 0;
    }

    public int getNetworkType(Context context) {
        int netType = 0;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = networkInfo.getExtraInfo();
            if (!TextUtils.isEmpty(extraInfo)) {
                netType = 2;
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = 1;
        }
        return netType;
    }

    public boolean isConnect(Context context) {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                // 获取网络连接管理的对象
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    // 判断当前网络是否已经连接
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            Log.v("error", e.toString());
        }
        return false;
    }


}
