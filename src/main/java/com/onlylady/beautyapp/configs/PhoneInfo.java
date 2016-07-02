//package com.onlylady.beautyapp.configs;
//
//import android.content.Context;
//import android.net.wifi.WifiInfo;
//import android.net.wifi.WifiManager;
//
///**
// * Created by Administrator on 2015/12/22.
// */
//public class PhoneInfo {
//    public static String getIp(Context context) {
//        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//        if (!wifiManager.isWifiEnabled()) {
//            wifiManager.setWifiEnabled(true);
//        }
//        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//        int i = wifiInfo.getIpAddress();
//        return (i & 0xFF) + "." +
//                ((i >> 8) & 0xFF) + "." +
//                ((i >> 16) & 0xFF) + "." +
//                (i >> 24 & 0xFF);
//    }
//}
