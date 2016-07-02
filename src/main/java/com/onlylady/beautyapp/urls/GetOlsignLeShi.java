package com.onlylady.beautyapp.urls;

import com.onlylady.beautyapp.configs.Configs;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by caining on 15/10/19.
 */
public class GetOlsignLeShi {

    public static String getOlsignString(Map<String,String> maps){
//        Arrays.sort(strs);//1.排序
        String key[] = new String[maps.size()];
        int index=0;
        for (String k : maps.keySet()) {
           key[index++] = k;
        }
        Arrays.sort(key);//1.排序
        String olsign="";
        for (int i = 0; i <key.length ; i++) {
            olsign+=key[i];
            olsign+= maps.get(key[i]);
        }
//        for (String str:strs)//2.相加+key
//            olsign += str;
        olsign+= Configs.KEY;
        olsign = MD5Util.stringToMD5(olsign);
        return  olsign;
    }
}
