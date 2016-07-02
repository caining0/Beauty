package com.onlylady.beautyapp.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.onlylady.beautyapp.R;
import com.onlylady.beautyapp.beans.onlylady.LiveBeans;
import com.onlylady.beautyapp.configs.Configs;
import com.onlylady.beautyapp.engines.BaseEngine;
import com.onlylady.beautyapp.urls.UrlsHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by caining on 16/2/19.
 */
public class ClickUtils {
    public static ClickUtils clickUtils;

    public static ClickUtils getInstance() {
        if (clickUtils == null) {
            clickUtils = new ClickUtils();
        }
        return clickUtils;
    }

    /**
     * 关闭窗口监听
     *
     * @param activity
     * @param viewid
     */

    public void setClosedWindowLisener(final Activity activity, int viewid) {
        activity.findViewById(viewid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

    /**
     * 跳转详情页监听
     *
     * @param context
     * @param view
     */

    public void setSecClickLisener(final Context context,  final Object o,View... views) {
        for (int i = 0; i <views.length ; i++) {
            views[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StartActivityUtils.startH5(context, o);

                }
            });
        }

    }

    /**
     * 分享点击
     * @param view
     */

    public void setShareLisener(final Context context,View view,final String tt, final String content, final String url, final String imageurl) {
        view.findViewById(R.id.shareqq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareSDKUtils.getInstance().showShare(context, QQ.NAME,tt,content,url,imageurl);
            }
        });
        view.findViewById(R.id.shareweibo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareSDKUtils.getInstance().showShare(context, SinaWeibo.NAME,tt,content,url,imageurl);
            }
        });
        view.findViewById(R.id.shareweixin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareSDKUtils.getInstance().showShare(context, Wechat.NAME,tt,content,url,imageurl);
            }
        });
        view.findViewById(R.id.shareqqz).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareSDKUtils.getInstance().showShare(context, QZone.NAME,tt,content,url,imageurl);
            }
        });
        view.findViewById(R.id.sharepengyouquan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareSDKUtils.getInstance().showShare(context, WechatMoments.NAME, tt, content, url, imageurl);
            }
        });

    }

    /**
     * 预约接口
     * @param context
     * @param view
     */


    public void setYuyueLisener(final Context context, final ImageView view, final LiveBeans liveBeans) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (liveBeans.getIrse()==1){//已预约
                    BaseEngine.getInstance().getStringPost(Configs.getDOMAINV100(), UrlsHolder.getInstance().getParams1026(UmengUtils.getInstance().getUmengDeviceToken(context), liveBeans.getLid()), new BaseEngine.CallbackForT<String>() {
                        @Override
                        public void finish(String bean) {
                            try {
                                JSONObject jsonObject = new JSONObject(bean);
                                if (jsonObject.optInt("errcode")==0){
                                    ToastUtils.show(context, context.getString(R.string.seccesscancelyuyue));
                                    liveBeans.setIrse(0);
                                    view.setImageResource(R.mipmap.yuyue);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void finish(List<String> listT) {

                        }
                    });
                }else if (liveBeans.getIrse()==0){//没预约
                    BaseEngine.getInstance().getStringPost(Configs.getDOMAINV100(), UrlsHolder.getInstance().getParams1027(UmengUtils.getInstance().getUmengDeviceToken(context), liveBeans.getLid()), new BaseEngine.CallbackForT<String>() {
                        @Override
                        public void finish(String bean) {
                            try {
                                JSONObject jsonObject = new JSONObject(bean);
                                if (jsonObject.optInt("errcode")==0){
                                    ToastUtils.show(context, context.getString(R.string.seccessyuyue));
                                    liveBeans.setIrse(1);
                                    view.setImageResource(R.mipmap.yiyuyue);
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

            }
        });


    }

}
