package com.onlylady.beautyapp.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2015/12/25.
 * 吐司工具类
 */
public class ToastUtils {
    public static void show(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}
