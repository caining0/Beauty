package com.onlylady.beautyapp.utils;

import android.util.Log;

import com.onlylady.beautyapp.configs.Configs;


/**
 * Created by Administrator on 2015/11/17.
 */
public class LogUtils {


    public static void Log(String ms) {
        if (Configs.logs)
            Log.i("info", "------->" + ms);
    }

    public static void Log(int ms) {
        if (Configs.logs)
            Log.i("info", "------->" + ms);
    }
    public static void Log(float ms) {
        if (Configs.logs)

            Log.i("info", "------->" + ms);
    }

    public static void Log(boolean ms) {
        if (Configs.logs)
            Log.i("info", "------->" + ms);
    }
}
