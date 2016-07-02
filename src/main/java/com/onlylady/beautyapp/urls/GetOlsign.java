package com.onlylady.beautyapp.urls;


import java.util.Arrays;

/**
 * Created by caining on 15/10/19.
 */
public class GetOlsign {

    public static String getOlsignString(String...strs){
        Arrays.sort(strs);//1.排序
        String olsign="";
        for (String str:strs)//2.相加+key
            olsign += str;
        olsign+= UrlsHolder.getInstance().getMAPISIGNKEY();
        olsign = MD5Util.stringToMD5(olsign);
        //string2MD5(olsign);
        return  olsign;
    }
}
