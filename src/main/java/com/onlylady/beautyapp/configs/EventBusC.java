package com.onlylady.beautyapp.configs;

import android.os.Bundle;

/**
 * Created by Administrator on 2016/1/21.
 * eventbus 工具类 不能重复
 */
public class EventBusC {
    public static final int FirstEvent = 1;
    public static final int LoginEvent = 2;
    public static final int logoutEvent = 3;
    public static final int FULLSCREEN = 4;//插件通知直播
    public static final int ZHIBOFULLSCREEN = 7;//直播时隐藏按钮
    public static final int JSLOGIN = 5;
    public static final int GOLIVE = 6;
    public static final int NETCONNET = 8;//网路连接状态
//    public static final int GOTOWHITCHPAGE = 9;//去哪页

    public static final int MINEREFRESS = 10;//添加直播页，刷新
    public static final int STOPZHIBO = 11;//停止直播

    private int from;
    private Bundle bundle;

    public static EventBusC getInstance(int from, Bundle bundle) {
        return new EventBusC(from,bundle);
    }
    public EventBusC(int from,Bundle bundle)
    {
        this.from = from;
        this.bundle = bundle;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public int getFrom() {
        return from;
    }
}
