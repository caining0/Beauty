package com.onlylady.beautyapp.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.onlylady.beautyapp.R;
import com.onlylady.beautyapp.activitys.LoginActivity;
import com.onlylady.beautyapp.configs.Configs;

import org.json.JSONObject;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by Administrator on 2015/11/2.
 */
public class ShareSDKUtils {
    public static ShareSDKUtils shareSDKUtils;
    public static String type = "";

    public static ShareSDKUtils getInstance() {
        if (null == shareSDKUtils)
            shareSDKUtils = new ShareSDKUtils();
        return shareSDKUtils;
    }

    public void showShare(Context context, String platform, String tt, String content, String url, String imageurl) {
//        MobclickAgent.onEvent(context, UmengKey.UMENTKEY26);
        OnekeyShare oks = new OnekeyShare();
        //  oks.disableSSOWhenAuthorize();
        oks.setImageUrl(imageurl);
        oks.setTitle(tt);
        oks.setText(content);
        if (platform != null) {
            oks.setPlatform(platform);
        }
        if (platform.equals(QZone.NAME)) {
            oks.setTitleUrl(url);// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
            oks.setText(" ");
            oks.setSite(content);
            // siteUrl是分享此内容的网站地址，仅在QQ空间使用
            oks.setSiteUrl(url);
        } else if (platform.equals(SinaWeibo.NAME)) {
            oks.setText(tt + ".戳:" + url + "");
        } else if (platform.equals(Wechat.NAME)) {
            oks.setUrl(url); // url仅在微信（包括好友和朋友圈）中使用
        } else if (platform.equals(QQ.NAME)) {
            oks.setUrl(url);
            oks.setTitleUrl(url);
        } else if (platform.equals(WechatMoments.NAME)) {
            oks.setUrl(url);
        }
        oks.show(context);

    }

    public void login(final LoginActivity context, String platform) {
        Platform weibo = ShareSDK.getPlatform(platform);
        weibo.SSOSetting(false);  //设置false表示使用SSO授权方式
        if(platform.equals(Wechat.NAME)&&!weibo.isClientValid()){
            ToastUtils.show(context,"微信客户端不存在");
        }
        weibo.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int action, HashMap<String, Object> hashMap) {
                PlatformDb platDB = platform.getDb();
                String accessToken = platDB.getToken(); // 获取授权token
                String openId = platDB.getUserId(); // 获取用户在此平台的ID
                String userName = platDB.getUserName();
                String userIcon = platDB.getUserIcon();
//                String nickname = platDB.get("nickname"); // 获取用户昵称
                String type = "";
                if (platform.getName().equals(QQ.NAME)) {
                    type = "2";//

                } else if (platform.getName().equals(SinaWeibo.NAME)) {
                    type = "1";
                } else if (platform.getName().equals(Wechat.NAME)) {
                    type = "3";

                }
                LogUtils.Log(type);
                ShareSDKUtils.type = type;
                PhoneInfo.openId = openId;
                PhoneInfo.username=userName;
                String[] key = {"platform", SharedPrefeUtils.ACCESSTOKEN, SharedPrefeUtils.USERNAME, SharedPrefeUtils.USERICON, SharedPrefeUtils.LOGINTYPE, SharedPrefeUtils.GENDER, SharedPrefeUtils.OPENID};
                String[] values = {platform.getName(), accessToken, userName, userIcon, type, platDB.getUserGender(), openId};
                SharedPrefeUtils.saveSettings(context, key, values);
//                SharedPrefeUtils.saveSettings(context, "platform", platform.getName());
//                SharedPrefeUtils.saveSettings(context, "accessToken", accessToken);
//                SharedPrefeUtils.saveSettings(context, "openId", openId);
//                SharedPrefeUtils.saveSettings(context, "userName", userName);
//                SharedPrefeUtils.saveSettings(context, "userIcon", userIcon);
//                SharedPrefeUtils.saveSettings(context, "logintype", type);
//                SharedPrefeUtils.saveSettings(context, "gender", platDB.getUserGender());
                context.getuserinfo(context);
//                Toast.makeText(context, context.getString(R.string.loginsusses), Toast.LENGTH_SHORT).show();
//                MainActivity.getuserinfo();

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        }); // 设置分享事件回调
        weibo.authorize();


//        Platform qzone = ShareSDK.getPlatform(context, platform);


    }

    public void logout(Context context) {
        String p = SharedPrefeUtils.getSettings(context, "platform");
        if (!TextUtils.isEmpty(p)) {
            Platform qzone = ShareSDK.getPlatform(context, p);
            if (qzone.isValid()) {
                qzone.removeAccount();
            }
        }
        SharedPrefeUtils.saveSettings(context, SharedPrefeUtils.ACCESSTOKEN, "");
        SharedPrefeUtils.saveSettings(context, SharedPrefeUtils.USERID, 0);
        SharedPrefeUtils.saveSettings(context, SharedPrefeUtils.USERNAME, "");
        SharedPrefeUtils.saveSettings(context, SharedPrefeUtils.USERICON, "");
        SharedPrefeUtils.saveSettings(context, SharedPrefeUtils.LOGINTYPE, "");
        SharedPrefeUtils.saveSettings(context, SharedPrefeUtils.GENDER, "");
        SharedPrefeUtils.saveSettings(context, SharedPrefeUtils.SJS, "");
        SharedPrefeUtils.saveSettings(context, SharedPrefeUtils.LOGIN, false);
        SharedPrefeUtils.saveSettings(context, SharedPrefeUtils.ILV, 0);
        PhoneInfo.uid = 0;
        PhoneInfo.untoken = "";
        PhoneInfo.sjs = "";
//        context.setResult(Configs.LOGINOUT);
    }

    public void saveUserInfo(Context context, JSONObject data) {
        String[] key = {SharedPrefeUtils.USERNAME, SharedPrefeUtils.USERICON, SharedPrefeUtils.LOGINTYPE, SharedPrefeUtils.ACCESSTOKEN, SharedPrefeUtils.GENDER, SharedPrefeUtils.SJS};
        String[] values = {data.optString("ue"), data.optString("ul"), Configs.LOGTYPE, data.optString("un"), data.optString("ux"), data.optString("sjs")};
        SharedPrefeUtils.saveSettings(context,SharedPrefeUtils.USERID, data.optInt("ud"));//int
        SharedPrefeUtils.saveSettings(context,SharedPrefeUtils.ILV, data.optInt("ilv"));//int
        SharedPrefeUtils.saveSettings(context, key, values);
        SharedPrefeUtils.saveSettings(context, SharedPrefeUtils.LOGIN, true);
    }


    public void ShareToshowPopWindow(Activity context, View anchor, String tt, String content, String url, String imageurl) {
        View popView = LayoutInflater.from(context).inflate(R.layout.share_platform, null);
//        myCallback.sendOnclickFromView(popView.findViewById(R.id.share_weixin), popView.findViewById(R.id.share_weixinq), popView.findViewById(R.id.share_sinaweibo), popView.findViewById(R.id.shareqq), popView.findViewById(R.id.share_qqzone));
        ClickUtils.getInstance().setShareLisener(context, popView, tt, content, url, imageurl);

        PopupWindow popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置popwindow出现和消失动画

        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ///  layout.setBackgroundColor(Color.parseColor("#00000000"));
            }
        });
//        popupWindow.setBackgroundDrawable(new ColorDrawable(0xb0000000));
        final Window window = context.getWindow();
        final WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.4f;
        window.setAttributes(lp);

        popupWindow.showAtLocation(anchor, Gravity.BOTTOM, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lp.alpha=1f;
                window.setAttributes(lp);
            }
        });


//        MaterialDialog alert = new MaterialDialog(context).setContentView(popView);
//        alert.show();
    }
}
