package com.onlylady.beautyapp.urls;

import android.content.Context;
import android.text.TextUtils;

import com.onlylady.beautyapp.BaseApp;
import com.onlylady.beautyapp.configs.Configs;
import com.onlylady.beautyapp.utils.LogUtils;
import com.onlylady.beautyapp.utils.PhoneInfo;
import com.onlylady.beautyapp.utils.SharedPrefeUtils;
import com.onlylady.beautyapp.utils.ToastUtils;
import com.onlylady.beautyapp.utils.UmengUtils;

import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/21.
 */
public class UrlsHolder {
    public static UrlsHolder urlHolder;

    public static UrlsHolder getInstance() {
        if (null == urlHolder) {
            urlHolder = new UrlsHolder();
        }
        return urlHolder;
    }

//    public static final String MAIN = "http://api.letvcloud.com/open.php";
//    public static final String LIVEMAIN = "http://api.open.letvcloud.com/live/execute";
//    private String videoList = "video.list";
//    private String liveVideoList = "letv.cloudlive.activity.search";
//    private String videoUploadInit = "video.upload.init";
//    private String imageGet = "image.get";
//    private String activityCreate = "letv.cloudlive.activity.create";
//    private String getPushUrl = "letv.cloudlive.activity.getPushUrl";
//    private String videoDown = "video.download";

//    public String getPushUrl(Map<String, String> map,String activityId) {//点播列表
//        addLivebefore(map);
//        map.put("method", getPushUrl);
//        map.put("activityId", activityId);
//        map.put("sign", GetOlsignLeShi.getOlsignString(map));
//        return LIVEMAIN;
//    }
//
//    public String getActivityCreate(Map<String, String> map) {//点播列表
//        addLivebefore(map);
//        map.put("method", activityCreate);
//        map.put("activityName","测试");
//        SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
//        Date date = new Date();
//        map.put("startTime",f.format(date));
//        map.put("endTime",f.format(date.getTime() + 1 * 24 * 60 * 60 * 1000));
////        map.put("coverImgUrl","封面");
////        map.put("description","描述");
//        map.put("liveNum","1");//机位数量
//        map.put("codeRateTypes","13");//流的码率类型，逗号分隔。由大到小排列。取值范围：10 流畅；13 标清；16 高清；19 超清；22 720P；25 1080P；99 原画
//        map.put("needRecord","0");//是否支持全程录制 0：否 1：是。默认为0
//        map.put("needTimeShift","0");//是否支持时移 0：否 1：是。默认为0
//        map.put("needFullView","0");//是否全景观看 0：否 1：是。默认为0
//        map.put("activityCategory","999");//活动分类（001：发布会、002婚礼、003年会、004体育、005游戏、006旅游&户外、007财经、008演唱会、009烹饪、010宠物&动物、011访谈、012教育，013：竞技、014：剧场、015：晚会、016：电视节目、017：秀场、999其他）
//        map.put("playMode","0");//播放模式，0：实时直播。1：延时直播
//        map.put("sign", GetOlsignLeShi.getOlsignString(map));
//        return LIVEMAIN;
//    }


//    public Map<String, String> getVideoDown() {//下载
//        Map<String, String> map = new Hashtable<>();
//        addbefore(map);
//        map.put("video_id", "23668181");
//        map.put("api", videoDown);
//        map.put("sign", GetOlsignLeShi.getOlsignString(map));
//        return map;
//    }

//    public String getImageGet(Map<String, String> map) {
////        截图尺寸，每种尺寸各有8张图。 有以下尺寸供选择：100_100、200_200、300_300、120_90、128_96、132_99、160_120、200_150、400_300、160_90、320_180、640_360、90_120、120_160、150_200、300_400
//        addbefore(map);
//        map.put("api", imageGet);
//        map.put("size", "1280_1920");
//        map.put("sign", GetOlsignLeShi.getOlsignString(map));
//        return MAIN;
//    }
//
//    public String getvideoUploadInit(Map<String, String> map) {//点播列表
//        addbefore(map);
//        map.put("api", videoUploadInit);
//        map.put("sign", GetOlsignLeShi.getOlsignString(map));
//        return MAIN;
//    }
//
//    public String getVideoList(Map<String, String> map,int page) {//点播列表
//        addbefore(map);
//        map.put("index", ""+page);
//        map.put("size", "10");
//        map.put("status", "10");
//        map.put("api", videoList);
//        map.put("sign", GetOlsignLeShi.getOlsignString(map));
//        return MAIN;
//    }
//
//    public String getLiveVideoList(Map<String, String> map) {//直播列表
//        addLivebefore(map);
//        map.put("method", liveVideoList);
////        map.put("activityId","1");
////        map.put("activityStatus","10");
////        map.put("activityName","10");
//        map.put("sign", GetOlsignLeShi.getOlsignString(map));
//        return LIVEMAIN;
//    }

//    private void addbefore(Map<String, String> map) {
//        map.put("user_unique", Configs.UUID);
//        String time = "" + System.currentTimeMillis();
//        map.put("timestamp", time);
//        map.put("format", "json");
//        map.put("ver", "2.0");
//    }

//    private void addLivebefore(Map<String, String> map) {
//        map.put("userid", Configs.UID);
//        String time = "" + System.currentTimeMillis();
//        map.put("timestamp", time);
//        map.put("ver", "3.0");
//    }


    //----------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
//                                       以上为乐视  以下为  Onlylady
//----------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    public String getMAPISIGNKEY() {
        return Configs.OLKEY;
    }


//    private String BEAUTY1001 = DOMAIN + "/beauty/v100/";//首页焦点图接口

    public Map<String, String> getParams1001() {
        Map<String, String> map = new Hashtable<>();
        map.put("rd", "1001");
        map.put("olsign", GetOlsign.getOlsignString(getMapToStrs(map)));
        return map;
    }

    public Map<String, String> getParams1002(String type) {//主题推荐专区接口
        Map<String, String> map = new Hashtable<>();
        map.put("rd", "1002");
        map.put("type", type);//type	是	type值 index：首页 found：发现
        map.put("olsign", GetOlsign.getOlsignString(getMapToStrs(map)));
        return map;
    }

    public Map<String, String> getParams1003(String tid, int page) {//主题 详情
        Map<String, String> map = new Hashtable<>();
        map.put("rd", "1003");
        map.put("tid", tid);
        map.put("ie", "" + page);
        map.put("olsign", GetOlsign.getOlsignString(getMapToStrs(map)));
        return map;
    }

    public Map<String, String> getParams1004(int page) {//首页
        Map<String, String> map = new Hashtable<>();
        map.put("rd", "1004");
        map.put("ie", "" + page);
        map.put("olsign", GetOlsign.getOlsignString(getMapToStrs(map)));
        return map;
    }

    public Map<String, String> getParams1005(int page, String chid) {//首页
        Map<String, String> map = new Hashtable<>();
        map.put("rd", "1005");
        map.put("ie", "" + page);
        map.put("chid", chid);
        map.put("olsign", GetOlsign.getOlsignString(getMapToStrs(map)));
        return map;
    }

    //    public Map<String, String>  getParams1006(int page) {//首页
//        Map<String, String> map = new Hashtable<>();
//        map.put("rd","1006");
//        map.put("ie",""+page);//0,yugao,1,今日，2，昨日
//        map.put("olsign",GetOlsign.getOlsignString(getMapToStrs(map)));
//        return  map;
//    }
    public Map<String, String> getParams1006(String deviceToken) {//直播首页

        deviceToken = UmengUtils.getInstance().getUmengDeviceToken(BaseApp.getAppContext());
//            LogUtils.Log(deviceToken);

        Map<String, String> map = new Hashtable<>();
        map.put("rd", "1006");
        map.put("tk", deviceToken);
        map.put("olsign", GetOlsign.getOlsignString(getMapToStrs(map)));
        return map;
    }

    public Map<String, String> getParams1018(int page) {//视频回顾
        Map<String, String> map = new Hashtable<>();
        map.put("rd", "1018");
        map.put("ie", "" + page);
        map.put("olsign", GetOlsign.getOlsignString(getMapToStrs(map)));
        return map;
    }

    public Map<String, String> getParams1007(String type, String openid, String ul) {
        Map<String, String> map = new Hashtable<>();
        map.put("rd", "1007");
        map.put("le", type);
        map.put("ul", ul);
        map.put("olsign", GetOlsign.getOlsignString(getMapToStrs(map)));
        return map;
    }

    public Map<String, String> getParams1008(String type, String at) {
        Map<String, String> map = new Hashtable<>();
        map.put("rd", type);
        map.put("at", at);
        map.put("olts", "" + System.currentTimeMillis());
        map.put("olsign", GetOlsign.getOlsignString(getMapToStrs(map)));
        return map;
    }

    public Map<String, String> getParams1012(String mob) {
        Map<String, String> map = new Hashtable<>();
        String time = "" + System.currentTimeMillis();
        map.put("rd", "1012");
        map.put("olts", "" + time);
        map.put("mob", mob);
        map.put("olsign", GetOlsign.getOlsignString(getMapToStrs(map)));
        return map;
    }

    public Map<String, String> getParams1013(String mob, String cd) {
        Map<String, String> map = new Hashtable<>();
        String time = "" + System.currentTimeMillis();
        map.put("rd", "1013");
        map.put("olts", "" + time);
        map.put("mob", mob);
        map.put("cd", cd);
        map.put("olsign", GetOlsign.getOlsignString(getMapToStrs(map)));
        return map;
    }

    public Map<String, String> getParams1014(String mob, String un) {
        Map<String, String> map = new Hashtable<>();
        String time = "" + System.currentTimeMillis();
        map.put("rd", "1014");
        map.put("olts", "" + time);
        map.put("mob", mob);
        map.put("un", un);
        map.put("olsign", GetOlsign.getOlsignString(getMapToStrs(map)));
        return map;
    }

    public Map<String, String> getParams1019(String lid) {
        Map<String, String> map = new Hashtable<>();//加一字段 js  是七牛的
        map.put("rd", "1019");
        map.put("lid", lid);
        map.put("olsign", GetOlsign.getOlsignString(getMapToStrs(map)));
        return map;
    }

    public Map<String, String> getParams1025(String cnt) {
        Map<String, String> map = new Hashtable<>();
        String time = "" + System.currentTimeMillis();
        map.put("rd", "1025");
        map.put("olts", "" + time);
        map.put("cnt", cnt);
        map.put("olsign", GetOlsign.getOlsignString(getMapToStrs(map)));
        return map;
    }

    public Map<String, String> getParams1026(String token, String lid) {
        Map<String, String> map = new Hashtable<>();
        String time = "" + System.currentTimeMillis();
        map.put("rd", "1026");
        map.put("olts", "" + time);
        map.put("tk", token);
        map.put("lid", lid);
        map.put("olsign", GetOlsign.getOlsignString(getMapToStrs(map)));
        return map;
    }

    public Map<String, String> getParams1027(String token, String lid) {
        LogUtils.Log(token);
        Map<String, String> map = new Hashtable<>();
        String time = "" + System.currentTimeMillis();
        map.put("rd", "1027");
        map.put("olts", "" + time);
        map.put("tk", token);
        map.put("lid", lid);
        map.put("olsign", GetOlsign.getOlsignString(getMapToStrs(map)));
        return map;
    }


    public String[] getMapToStrs(Map<String, String> map) {
        String[] strs = new String[map.size()];
        int i = 0;
        for (String key : map.keySet()) {
            strs[i++] = map.get(key);
        }
        return strs;
    }

    // TODO: 16/3/28 1.10接口

    /**
     * 创建直播 post
     * <p/>
     * rd	是	1101
     * olsign	是	请求签名验证请求有效性，所有接口请求都要带olsign参数
     * olts	是	发起请求的时间戳
     * ud	是	用户uid
     * un	是否	验证用户的token
     * lne	是	直播名称
     * lst	是	直播开始时间 int时间戳
     * des	是	直播描述
     * file	是	图片上传的控件名
     */
    public Map<String, String> getParams1101(Context context, String lst, String lne, String des) {
        Map<String, String> map = new Hashtable<>();
        map.put("rd", "1101");
        map.put("olts", "" + System.currentTimeMillis());
        if (PhoneInfo.uid == 0) {
            map.put("ud", "" + SharedPrefeUtils.getSettingsInt(context, SharedPrefeUtils.USERID));
        } else {
            map.put("ud", "" + PhoneInfo.uid);
        }
        if (TextUtils.isEmpty(PhoneInfo.untoken)) {
            map.put("un", SharedPrefeUtils.getSettings(context, SharedPrefeUtils.ACCESSTOKEN));
        } else {
            map.put("un", "" + PhoneInfo.untoken);
        }

        map.put("lst", lst);
        map.put("lne", lne);
        map.put("des", des);
        map.put("olsign", GetOlsign.getOlsignString(getMapToStrs(map)));
        return map;
    }

    /**
     * 跟新直播 post
     * <p/>
     * 参数	是否必填	描述
     * rd	是	1102
     * olsign	是	请求签名验证请求有效性，所有接口请求都要带olsign参数
     * olts	是	发起请求的时间戳
     * lid	是	直播id
     * ud	是	用户uid
     * un	是否	验证用户的token
     * lne	否	直播名称
     * lst	否	直播开始时间 int时间戳
     * des	否	直播描述
     * file	否	图片上传的控件名
     */
    public Map<String, String> getParams1102(String lst, String lne, String des, String lid) {
        Map<String, String> map = new Hashtable<>();
        map.put("rd", "1102");
        map.put("olts", "" + System.currentTimeMillis());
        if (PhoneInfo.uid == 0 || TextUtils.isEmpty(PhoneInfo.untoken)) {
            ToastUtils.show(BaseApp.getAppContext(), "无权限");
            return null;
        } else {
            map.put("ud", "" + PhoneInfo.uid);
            map.put("un", PhoneInfo.untoken);
        }
        map.put("lid", lid);
        map.put("lst", lst);
        map.put("lne", lne);
        map.put("des", des);
        map.put("olsign", GetOlsign.getOlsignString(getMapToStrs(map)));
        return map;
    }

    /**
     * 开始直播 post
     * 参数	是否必填	描述
     * rd	是	1103
     * olsign	是	请求签名验证请求有效性，所有接口请求都要带olsign参数
     * olts	是	发起请求的时间戳
     * lid	是	直播id
     * ud	是	用户uid
     * un	是否	验证用户的token
     */
    public Map<String, String> getParams1103(String lid) {
        Map<String, String> map = new Hashtable<>();
        map.put("rd", "1103");
        map.put("olts", "" + System.currentTimeMillis());
        if (PhoneInfo.uid == 0 || TextUtils.isEmpty(PhoneInfo.untoken)) {
            ToastUtils.show(BaseApp.getAppContext(), "无权限");
            return map;
        } else {
            map.put("ud", "" + PhoneInfo.uid);
            map.put("un", PhoneInfo.untoken);
        }
        map.put("lid", lid);
        map.put("olsign", GetOlsign.getOlsignString(getMapToStrs(map)));
        return map;
    }

    /**
     * 结束直播 post
     * 参数	是否必填	描述
     * rd	是	1104
     * olsign	是	请求签名验证请求有效性，所有接口请求都要带olsign参数
     * olts	是	发起请求的时间戳
     * lid	是	直播id
     * ud	是	用户uid
     * un	是否	验证用户的token
     */
    public Map<String, String> getParams1104(String lid) {
        Map<String, String> map = new Hashtable<>();
        map.put("rd", "1104");
        map.put("olts", "" + System.currentTimeMillis());
        if (PhoneInfo.uid == 0 || TextUtils.isEmpty(PhoneInfo.untoken)) {
            ToastUtils.show(BaseApp.getAppContext(), "无权限");
            return map;
        } else {
            map.put("ud", "" + PhoneInfo.uid);
            if (!TextUtils.isEmpty(PhoneInfo.untoken))
                map.put("un", PhoneInfo.untoken);
        }
        map.put("lid", lid);
        map.put("olsign", GetOlsign.getOlsignString(getMapToStrs(map)));
        return map;
    }

    /**
     * 直播列表 get
     * 参数	是否必填	描述
     * rd	是	1105
     * olsign	是	请求签名验证请求有效性，所有接口请求都要带olsign参数
     * ud	是	用户uid
     * un	是否	验证用户的token
     */
    public Map<String, String> getParams1105() {
        Map<String, String> map = new Hashtable<>();
        map.put("rd", "1105");
        map.put("ud", "" + PhoneInfo.uid);
        if (!TextUtils.isEmpty(PhoneInfo.untoken))
            map.put("un", "" + PhoneInfo.untoken);
        map.put("olsign", GetOlsign.getOlsignString(getMapToStrs(map)));
        return map;
    }

    /**
     * 广告 get
     * rd	是	1106
     * type	是	类型 1：开屏广告iphone3，2:开屏广告iphone5
     * olsign	是	请求签名验证请求有效性，所有接口请求都要带olsign参数
     */
    public Map<String, String> getParams1106(String type) {
        Map<String, String> map = new Hashtable<>();
        map.put("rd", "1106");
        map.put("type", type);
        map.put("olsign", GetOlsign.getOlsignString(getMapToStrs(map)));
        return map;
    }

    /**
     * rd	是	1107
     * olsign	是	请求签名验证请求有效性，所有接口请求都要带olsign参数
     * olts	是	发起请求的时间戳
     * lid	是	直播id
     * ud	是	用户uid
     * un	是否	验证用户的token
     */
    public Map<String, String> getParams1107(String lid) {
        Map<String, String> map = new Hashtable<>();
        map.put("rd", "1107");
        map.put("lid", lid);
        map.put("olts", "" + System.currentTimeMillis());
        if (PhoneInfo.uid == 0 || TextUtils.isEmpty(PhoneInfo.untoken)) {
            ToastUtils.show(BaseApp.getAppContext(), "无权限");
            return null;
        } else {
            map.put("ud", "" + PhoneInfo.uid);
            if (!TextUtils.isEmpty(PhoneInfo.untoken))
                map.put("un", PhoneInfo.untoken);
        }
        map.put("olsign", GetOlsign.getOlsignString(getMapToStrs(map)));
        return map;
    }


}
