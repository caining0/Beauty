package com.onlylady.beautyapp.utils;

import android.content.Context;
import android.content.Intent;

import com.HBuilder.integrate.webview.SDK_WebApp;
import com.onlylady.beautyapp.activitys.AdActivity;
import com.onlylady.beautyapp.activitys.SecondDetailActivity;
import com.onlylady.beautyapp.activitys.SecondLiveActivity;
import com.onlylady.beautyapp.activitys.SplashActivity;
import com.onlylady.beautyapp.beans.MoreLiveBean;
import com.onlylady.beautyapp.beans.onlylady.Focuses;
import com.onlylady.beautyapp.beans.onlylady.HomeBeans;
import com.onlylady.beautyapp.beans.onlylady.LiveBeans;
import com.onlylady.beautyapp.beans.onlylady.MineLiveBeans;
import com.onlylady.beautyapp.beans.onlylady.SecDetailBeans;
import com.onlylady.beautyapp.beans.onlylady.Themes;
import com.onlylady.beautyapp.configs.Configs;
import com.onlylady.beautyapp.engines.BaseEngine;
import com.onlylady.beautyapp.urls.UrlsHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2015/10/29.
 */
public class StartActivityUtils {


    /**
     * 乐视云活动直播
     */
    public static void startLeCloudActionLive(Context context, String id, String type, String rtmp, String text) {
        Intent intent = new Intent(context, SDK_WebApp.class);
        intent.putExtra("id", id);
        intent.putExtra("type", type);
        intent.putExtra("live", true);
        intent.putExtra("text", text);//直播的状态
//        intent.putExtra(PlagerFragment.DATA, LetvParamsUtils.setActionLiveParams(liveid, false));
        intent.putExtra("videoPath",rtmp);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    public static void startH5(Context context, Object o) {
        /**
         * id   aid
         *
         * HomeBeans
         *     ///1（文章，图文，点播等）  2直播  3 直播回顾｜往期直播
         */
        String id = null;
        String type = null;
        String laid = null;
        int irse = 0;
        String lid = null;
//        getLaid(context,"135","1");

        if (o instanceof SecDetailBeans.DataEntity.ArticlesEntity) {
            SecDetailBeans.DataEntity.ArticlesEntity ob = (SecDetailBeans.DataEntity.ArticlesEntity) o;
            id = ob.getAid();
            type = ob.getType();
            if (type.equals("ar") || type.equals("va") || type.equals("ad")) {
                type = "1";
            } else if (type.equals("lv")) {
                type = "2";
                getLaid(context, ob.getAid(), type);
                return;

            }
        } else if (o instanceof HomeBeans) {
            HomeBeans ob = (HomeBeans) o;
            id = ob.getAid();
            type = ob.getType();
            if (type.equals("ar") || type.equals("va") || type.equals("ad")) {
                id = ob.getAid();
                type = "1";
            } else if (type.equals("lv")) {//lv
                id = ob.getLid();//直播id
//                if (ob.getStu() == 1) {
                type = "2";
                laid = ob.getLaid();//本地直播用
                getLaid(context, ob.getLid(), type);
                return;
//                } else {//va
//                    type = "3";
//                }
            }

        } else if (o instanceof LiveBeans) {
            LiveBeans ob = (LiveBeans) o;
            id = ob.getLid();
//            if (ob.getStu() == 1) {
            type = "2";
            laid = ob.getLaid();
            getLaid(context, ob.getLid(), type);
            return;
//                irse = ob.getIrse();

//            } else {
//                type = "3";
//            }

        } else if (o instanceof Focuses.DataEntity.FocusesEntity) {
            Focuses.DataEntity.FocusesEntity ob = (Focuses.DataEntity.FocusesEntity) o;

            id = ob.getAid();
            type = ob.getType();

            if (type.equals("ar") || type.equals("va") || type.equals("ad")) {
                type = "1";
            } else if (type.equals("lv")) {//直播
                //焦点图里的直播
                type = "2";

                getLaid(context, id, type);
                return;
            }


        } else if (o instanceof MoreLiveBean.DataEntity.LiveEntity) {
            MoreLiveBean.DataEntity.LiveEntity ob = (MoreLiveBean.DataEntity.LiveEntity) o;
            id = ob.getLid();
            if (ob.getStu() == 1) {
                type = "2";
//                id = ob.getLaid();
//                laid = ob.getLaid();

                getLaid(context, ob.getLid(), type);
                return;
            } else {
                type = "3";
            }
        }else if (o instanceof MineLiveBeans.DataEntity.LiveEntity){
            MineLiveBeans.DataEntity.LiveEntity ob = ( MineLiveBeans.DataEntity.LiveEntity) o;
            id = ob.getLid();
            if (ob.getStu() == 1) {
                type = "2";
//                id = ob.getLaid();
//                laid = ob.getLaid();

                getLaid(context, ob.getLid(), type);
                return;
            } else {
                type = "3";
            }

        }


        if (type.equals("2")) {//直播 本地
            StartActivityUtils.startLeCloudActionLive(context, id, type, laid, null);
        } else {
            Intent intent = new Intent(context, SDK_WebApp.class);
            intent.putExtra("id", id);
            intent.putExtra("type", type);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }


    }

    /**
     * 焦点图里的直播
     *
     * @param context
     * @param id
     */
    private static void getLaid(final Context context, final String id, final String type) {
        BaseEngine.getInstance().getStringGet(Configs.getDOMAINV100(), UrlsHolder.getInstance().getParams1019(id), new BaseEngine.CallbackForT<String>() {
            @Override
            public void finish(String bean) {
                try {
                    JSONObject json = new JSONObject(bean);
                    String laid = json.optJSONObject("data").optString("laid");
                    int irse = json.optJSONObject("data").optInt("irse");
                    int vl = json.optJSONObject("data").optInt("vl");
                    int stu = json.optJSONObject("data").optInt("stu");
                   JSONObject jsonObject = new JSONObject(json.optJSONObject("data").optString("js"));
                   String rtmp = "rtmp://"+ jsonObject.optJSONObject("hosts").optJSONObject("publish").optString("rtmp")+"/"+jsonObject.optString("hub")+"/"+jsonObject.optString("title");
                    LogUtils.Log(rtmp);

                    if (vl != 0 && stu == 3) {//直播变回放
                        Intent intent = new Intent(context, SDK_WebApp.class);
                        intent.putExtra("id", id);
                        intent.putExtra("type", type);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } else if (vl == 0 && stu == 2) {//直播未开始
//                        StartActivityUtils.startLeCloudActionLive(context, id, type, laid, "直播尚未开始");
                        ToastUtils.show(context, "直播尚未开始");
                    } else if (vl == 0 && stu == 3) {//直播结束但没转回放
                        StartActivityUtils.startLeCloudActionLive(context, id, type, rtmp, "直播已结束");
                    } else {//直播

                        StartActivityUtils.startLeCloudActionLive(context, id, type, rtmp, null);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void finish(List<String> listT) {

            }
        });
    }

    public static void startSecD(Context context, Themes.DataEntity.ThemeEntity themeEntity, String type) {

        Intent intent = new Intent(context, SecondDetailActivity.class);
        if (type != null)
            intent.putExtra("type", type);
        intent.putExtra("data", themeEntity);
        context.startActivity(intent);
    }

    public static void startSecLive(Context context) {

        Intent intent = new Intent(context, SecondLiveActivity.class);
        context.startActivity(intent);
    }
    public static void startADActivity(Context context,String h5url){
        Intent intent = new Intent(context, AdActivity.class);
        intent.putExtra(SplashActivity.VAL,h5url);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


}
