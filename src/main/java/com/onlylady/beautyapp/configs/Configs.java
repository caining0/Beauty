package com.onlylady.beautyapp.configs;

/**
 * Created by Administrator on 2015/12/21.
 */
public class Configs {
    public static final String UUID = "cfdce463f4";
    public static final String UID = "104359";
    public static final String KEY = "beaaae59eb2e930ad53d0b3ac118a688";
    /**
     * 以上为乐视用户信息
     */
    public static final String OLKEY = "4eA59fEF705f449e-";
    public static final int LOGIN = 20000;
    public static final int JSLOGIN = 20002;
    public static final int LOGINOUT = 20001;
    public static final String LOGTYPE = "100";
    public static final String[] TITLES = {"首页", "直播", "发现", "个人"};
//    public static final String DOMAIN = "http://10.19.136.136/beauty/";
    private static final String DOMAIN = "http://mapi.onlylady.com/beauty/";
    public static final String CONNCTTO170 = "1";
    public static boolean logs = true;

    public static String getDOMAINV100() {
        return DOMAIN + "v100/";
    }

    public static String getDOMAINV110() {
        return DOMAIN + "v110/";
    }
}
