package com.example.H5PlusPlugin;

import android.os.Bundle;
import android.util.Base64;

import com.HBuilder.integrate.webview.SDK_WebApp;
import com.onlylady.beautyapp.BaseApp;
import com.onlylady.beautyapp.configs.Configs;
import com.onlylady.beautyapp.configs.EventBusC;
import com.onlylady.beautyapp.urls.MD5Util;
import com.onlylady.beautyapp.utils.LogUtils;
import com.onlylady.beautyapp.utils.PhoneInfo;
import com.onlylady.beautyapp.utils.ShareSDKUtils;
import com.onlylady.beautyapp.utils.SharedPrefeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;
import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.DHInterface.StandardFeature;
import io.dcloud.common.util.JSUtil;

/**
 * 插件调用
 */

public class PGPlugintest extends StandardFeature {
    /**
     * 插件调用
     *
     * @param pWebview
     * @param array
     */

    public static IWebview pWebview;
    public static String CallBackID;


    //pgpassport.js
    public void requireShare(IWebview pWebview, JSONArray array) {
        String content = null;
        String url = null;
        String imageUrl = null;
        String tt = null;
        try {
//            String string = array.getString(0);
            String string1 = array.getString(1);
            JSONObject jsonObject = new JSONObject(string1);
            imageUrl = jsonObject.optString("iu");
            url = jsonObject.optString("shu");
            content = jsonObject.optString("desc");
            tt = jsonObject.optString("tt");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogUtils.Log("requireShare");
        ShareSDKUtils.getInstance().ShareToshowPopWindow(SDK_WebApp.context, SDK_WebApp.layout, tt, content, url, imageUrl);
    }

    public String getInfo(IWebview pWebview, JSONArray array) {
        JSONObject retJSONObj = new JSONObject();

        try {
            if (PhoneInfo.getInstance().isApkDebugable(BaseApp.getAppContext())) {//debug
                retJSONObj.putOpt("debug", "true");
            } else {
                retJSONObj.putOpt("debug", "false");
            }
            retJSONObj.putOpt("code", Configs.OLKEY);
            retJSONObj.putOpt("ENV", Base64.encodeToString(PhoneInfo.getInstance().getPhoneInfo(BaseApp.getAppContext()).getBytes(), Base64.NO_WRAP));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogUtils.Log("getInfo" + retJSONObj.toString());
        return JSUtil.wrapJsVar(retJSONObj);
    }

    public String md5(IWebview pWebview, JSONArray array) {
        String md5before = array.optString(0);
        String md5after = MD5Util.stringToMD5(md5before);
        LogUtils.Log("md5" + md5after);
        return JSUtil.wrapJsVar(md5after);
    }

    public String changeStatusBarOrientation(IWebview pWebview, JSONArray array) {
        String orientation = array.optString(0);
        if ("landscape-primary".equals(orientation)) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("fullscreen", false);
            EventBus.getDefault().post(EventBusC.getInstance(EventBusC.FULLSCREEN, bundle));

        } else {
            Bundle bundle = new Bundle();
            bundle.putBoolean("fullscreen", true);
            EventBus.getDefault().post(EventBusC.getInstance(EventBusC.FULLSCREEN, bundle));

        }

        LogUtils.Log("changeStatusBarOrientation");

        return null;
    }

    public String getUserInfo(IWebview pWebview, JSONArray array) {
        JSONObject retJSONObj = new JSONObject();
        getJsonUserInfo(retJSONObj);
        LogUtils.Log("getUserInfo" + retJSONObj.toString());


        return JSUtil.wrapJsVar(retJSONObj);
    }

    public void requireLogin(IWebview pWebview, JSONArray array) {
        JSONObject retJSONObj = new JSONObject();
        String CallBackID = array.optString(0);
        PGPlugintest.pWebview = pWebview;
        PGPlugintest.CallBackID = CallBackID;
        if (SharedPrefeUtils.getSettings(BaseApp.getAppContext(), SharedPrefeUtils.LOGIN, false)) {//登陆
            getJsonUserInfo(retJSONObj);
            LogUtils.Log("requireLogin" + retJSONObj.toString());
            JSUtil.execCallback(pWebview, CallBackID, retJSONObj, JSUtil.OK, false);
        } else {
            LogUtils.Log("requireLogin" + retJSONObj.toString());
            EventBus.getDefault().post(EventBusC.getInstance(EventBusC.JSLOGIN, null));
        }


    }

    public static void getJsonUserInfo(JSONObject retJSONObj) {
        try {
            if (PhoneInfo.uid == 0) {
                PhoneInfo.uid  = SharedPrefeUtils.getSettingsInt(BaseApp.getAppContext(), SharedPrefeUtils.USERID);
            }
            retJSONObj.putOpt("id", PhoneInfo.uid);
            retJSONObj.putOpt("name", SharedPrefeUtils.getSettings(BaseApp.getAppContext(), SharedPrefeUtils.USERNAME));//
            retJSONObj.putOpt("six", SharedPrefeUtils.getSettings(BaseApp.getAppContext(), SharedPrefeUtils.GENDER));//性别
            retJSONObj.putOpt("avatar", SharedPrefeUtils.getSettings(BaseApp.getAppContext(), SharedPrefeUtils.USERICON));//头像
            retJSONObj.putOpt("token", gettoken());//
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static String gettoken() {
        String token = PhoneInfo.uid + "xjdl29wd";
        token = (MD5Util.stringToMD5(token)).substring(0, 10);
        return token;
    }
}