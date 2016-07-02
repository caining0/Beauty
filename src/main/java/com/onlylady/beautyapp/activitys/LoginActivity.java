package com.onlylady.beautyapp.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.H5PlusPlugin.PGPlugintest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onlylady.beautyapp.BaseApp;
import com.onlylady.beautyapp.R;
import com.onlylady.beautyapp.beans.onlylady.UserInfo;
import com.onlylady.beautyapp.configs.Configs;
import com.onlylady.beautyapp.configs.EventBusC;
import com.onlylady.beautyapp.engines.BaseEngine;
import com.onlylady.beautyapp.urls.UrlsHolder;
import com.onlylady.beautyapp.utils.LogUtils;
import com.onlylady.beautyapp.utils.NetWorkState;
import com.onlylady.beautyapp.utils.PhoneInfo;
import com.onlylady.beautyapp.utils.ShareSDKUtils;
import com.onlylady.beautyapp.utils.SharedPrefeUtils;
import com.onlylady.beautyapp.utils.ToastUtils;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import de.greenrobot.event.EventBus;

public class LoginActivity extends BaseActivity {
    @Bind(R.id.telephonenum)
    EditText telephonenum;
    @Bind(R.id.checktelephone_num)
    EditText checknum;
    @Bind(R.id.sendandcheck)
    ImageView sendandcheck;
    @Bind(R.id.denglu)
    Button denglu;
    @Bind(R.id.sendandcheck_time)
    TextView logintime;
    @Bind(R.id.loginweixin)
    ImageView loginweixin;
    @Bind(R.id.loginsina)
    ImageView loginsina;
    @Bind(R.id.loginqq)
    ImageView loginqq;
    @Bind(R.id.checkbox_agreeservice)
    CheckBox checkboxAgreeservice;
    private Timer timer;
    private String tel;
    private static Activity context;

    private static String logintype;


    @Override
    public boolean useEventbus() {
        return false;
    }

    @Override
    public void createview() {
        setContentView(R.layout.activity_login);
        ShareSDK.initSDK(this);
        context = this;
    }

    @Override
    public void initlisener() {

    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.sendandcheck)
    public void sendandcheck(View view) {
        tel = telephonenum.getText().toString();
        if (!isMobileNO(tel)) {
            Toast.makeText(getApplicationContext(), "请输入正确的手机号!", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean connect = NetWorkState.getInstance().isConnect(BaseApp.getAppContext());
        if (!connect){
            ToastUtils.show(BaseApp.getAppContext(), "请检查您的网络设置！");
            return;
        }

        BaseEngine.getInstance().getStringPost(Configs.getDOMAINV100(), UrlsHolder.getInstance().getParams1012(tel), new BaseEngine.CallbackForT<String>() {
            @Override
            public void finish(String bean) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(bean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (jsonObject.optInt("errcode")==0) {
                    sendandcheck.setVisibility(View.GONE);
                    logintime.setVisibility(View.VISIBLE);
                    logintime.setText("60s");
                    sendandcheck.setClickable(false);
                    timer = new Timer();
                    timerbegin();
                }
            }

            @Override
            public void finish(List<String> listT) {

            }
        });
    }
    //本次验证码
    String cd;
    @OnClick(R.id.agreeservice_text)
    public void agreeserviceText() {
        Intent intent = new Intent(LoginActivity.this, TextReaderActivity.class);
        intent.putExtra("fwxy", true);
        startActivity(intent);
    }
    @OnClick(R.id.denglu)
    public void login() {
        if (!checkboxAgreeservice.isChecked()){
//            ToastUtils.show(getApplicationContext(),"请同意服务协议");
            return;
        }
        tel = telephonenum.getText().toString();
        if (!isMobileNO(tel)) {
            Toast.makeText(getApplicationContext(), "请输入正确的手机号!", Toast.LENGTH_SHORT).show();
            return;
        }
        cd = checknum.getText().toString();
        if (!cheackYZM(cd)) return;
        BaseEngine.getInstance().getStringPost(Configs.getDOMAINV100(), UrlsHolder.getInstance().getParams1013(tel, cd), new BaseEngine.CallbackForT<String>() {
            @Override
            public void finish(String bean) {
                telLogin(bean);
            }

            @Override
            public void finish(List<String> listT) {

            }
        });
    }



    private void timerbegin() {
//        EventBus.getDefault().post(EventBusC.getInstance(EventBusC.FirstEvent));
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {      // UI thread
                            @Override
                            public void run() {
                                handler.sendEmptyMessage(0);
                            }
                        });
                    }
                }, 1000, 1000);
    }

    public static boolean isMobileNO(String mobiles) {
      /*
      移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
      联通：130、131、132、152、155、156、185、186
      电信：133、153、180、189、（1349卫通）
      170
      总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
              */
        String telRegex = "[1][3578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

    private boolean cheackYZM(String num) {
        if (num.length() == 0) {
            Toast.makeText(getApplicationContext(), "请输入验证码", Toast.LENGTH_SHORT).show();
            return false;
        }
        //保存本次使用的验证码

        return true;
    }

    private int time = 59;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    logintime.setText("" + (time--) + "s");
                    if (time < 0) {
                        timer.cancel();
                        timer.purge();
                        timer = null;
                        sendandcheck.setVisibility(View.VISIBLE);
                        sendandcheck.setClickable(true);
                        logintime.setVisibility(View.GONE);
                        time = 59;
                    }
                    break;
            }
        }
    };

    private void telLogin(String result) {
        JSONObject json = null;
        try {
            json = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int errcode = json.optInt("errcode");
        if (errcode != 0) {
            if (errcode == 4003) {//签名验证失败
                Toast.makeText(getApplicationContext(), "4003", Toast.LENGTH_SHORT).show();

            } else if (errcode == 4030) {//验证码无效
                String lastTimeYzm=SharedPrefeUtils.getSettings(LoginActivity.this, "lasttimeyzm");

                if(lastTimeYzm!=null&&lastTimeYzm.length()>0&&lastTimeYzm.equals(cd)){
                    Toast.makeText(getApplicationContext(), "验证码失效！", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "验证码错误！", Toast.LENGTH_SHORT).show();}
            } else {
                Toast.makeText(getApplicationContext(), json.optString("errmsg"), Toast.LENGTH_SHORT).show();
            }
            //                    LogUtils.Log(json.optString("errmsg") + json.optInt("errcode"), getApplicationContext());

        } else {
            JSONObject data = json.optJSONObject("data");
            if (data.optInt("ibne") == 0) {
                Intent intent = new Intent(LoginActivity.this, UserRegisterActivity.class);
                intent.putExtra("tel", tel);
                intent.putExtra("telornot", true);
                intent.putExtra("username", tel);
                intent.putExtra("imageturl", data.optString("ul"));
                startActivityForResult(intent, Configs.LOGIN);
                //startActivity(intent);
            } else {
                //保存本次登录成功的验证码
                SharedPrefeUtils.saveSettings(LoginActivity.this, "lasttimeyzm", cd);
                ShareSDKUtils.getInstance().saveUserInfo(getApplicationContext(), data);
                Bundle bundle = new Bundle();
                bundle.putString("ue", data.optString("ue"));
                bundle.putString("ul", data.optString("ul"));
                EventBus.getDefault().post(EventBusC.getInstance(EventBusC.LoginEvent, bundle));
//                SharedPrefeUtils.saveSettings(context, SharedPrefeUtils.LOGIN, true);
                Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();

                getUserInfo(data.toString(),LoginActivity.this);
                LoginActivity.this.finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Configs.LOGIN) {
            getuserinfo(context);
            Toast.makeText(context.getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
            context.finish();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK(this);
    }

    @OnClick(R.id.loginqq)
    public void loginqq(View view) {
        ShareSDKUtils.getInstance().login(this, QQ.NAME);
    }

    @OnClick(R.id.loginsina)
    public void loginsina(View view) {
        ShareSDKUtils.getInstance().login(this, SinaWeibo.NAME);
    }

    @OnClick(R.id.loginweixin)
    public void setLoginweixin(View view) {
        ShareSDKUtils.getInstance().login(this, Wechat.NAME);

    }

    @OnClick(R.id.login_goback)
    public void goback() {
        this.finish();
    }


    public static void getuserinfo(final Activity activity) {


        logintype = ShareSDKUtils.type;
        if (TextUtils.isEmpty(logintype)) {
            logintype = SharedPrefeUtils.getSettings(activity, SharedPrefeUtils.LOGINTYPE);
        }
        if (Configs.LOGTYPE.equals(logintype)){
            getUserInfo("",activity);//add cnn 手机登陆
        }else {
            if (TextUtils.isEmpty(PhoneInfo.openId)) {
                PhoneInfo.openId = SharedPrefeUtils.getSettings(BaseApp.getAppContext(), SharedPrefeUtils.OPENID);
                PhoneInfo.username = SharedPrefeUtils.getSettings(BaseApp.getAppContext(), SharedPrefeUtils.USERNAME);
            }
            BaseEngine.getInstance().getStringPost(Configs.getDOMAINV100(), UrlsHolder.getInstance().getParams1007(logintype, PhoneInfo.openId, SharedPrefeUtils.getSettings(activity, SharedPrefeUtils.USERICON)), new BaseEngine.CallbackForT<String>() {
                        @Override
                        public void finish(String bean) {
                            try {
                                JSONObject jsonObject = new JSONObject(bean).optJSONObject("data");
                                if (jsonObject.optInt("ibne") == 0) {//需要注册
                                    activity.startActivityForResult(new Intent(activity, UserRegisterActivity.class), Configs.LOGIN);
                                } else {
                                    getUserInfo(jsonObject.toString(), activity);
                                    // context.startActivity(new Intent(context, UserRegisterActivity.class));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void finish(List<String> listT) {

                        }
                    }

            );
        }
    }

    private static void getUserInfo(String result, Activity activity) {
        UserInfo userinfo = null;
        if (Configs.LOGTYPE.equals(SharedPrefeUtils.getSettings(activity, SharedPrefeUtils.LOGINTYPE))) {//手机登陆
            LogUtils.Log("11111");
            userinfo = new UserInfo();
            userinfo.setSjs(SharedPrefeUtils.getSettings(activity, SharedPrefeUtils.SJS));
            userinfo.setUe(SharedPrefeUtils.getSettings(activity, SharedPrefeUtils.USERNAME));
            userinfo.setUl(SharedPrefeUtils.getSettings(activity, SharedPrefeUtils.USERICON));
            userinfo.setUn(SharedPrefeUtils.getSettings(activity, SharedPrefeUtils.ACCESSTOKEN));
            userinfo.setUx(SharedPrefeUtils.getSettings(activity, SharedPrefeUtils.GENDER));
            int ilv = SharedPrefeUtils.getSettingsInt(activity, SharedPrefeUtils.ILV);
            userinfo.setIlv(ilv);
            int settings = SharedPrefeUtils.getSettingsInt(activity, SharedPrefeUtils.USERID);
            userinfo.setUd(settings);
        } else {
            Gson gson = new Gson();
            userinfo = gson.fromJson(result, new TypeToken<UserInfo>() {
            }.getType());
            SharedPrefeUtils.saveSettings(activity, SharedPrefeUtils.USERNAME, userinfo.getUe());
            SharedPrefeUtils.saveSettings(activity,SharedPrefeUtils.USERID,userinfo.getUd());
            SharedPrefeUtils.saveSettings(activity, SharedPrefeUtils.ACCESSTOKEN, userinfo.getUn());
            SharedPrefeUtils.saveSettings(activity, SharedPrefeUtils.GENDER, userinfo.getUx());
            SharedPrefeUtils.saveSettings(activity, SharedPrefeUtils.ILV, userinfo.getIlv());
            SharedPrefeUtils.saveSettings(activity, SharedPrefeUtils.USERICON, userinfo.getUl());
            SharedPrefeUtils.saveSettings(activity, SharedPrefeUtils.SJS, userinfo.getSjs());
            SharedPrefeUtils.saveSettings(activity, SharedPrefeUtils.LOGIN, true);

        }
        PhoneInfo.uid = userinfo.getUd();
        MobclickAgent.onProfileSignIn("" + PhoneInfo.uid + "--" + userinfo.getUe());
        PhoneInfo.untoken = PGPlugintest.gettoken();
        //userinfo.getUn();

        LogUtils.Log("uid"+PhoneInfo.uid+PhoneInfo.untoken);
        PhoneInfo.sjs = userinfo.getSjs();
        PhoneInfo.username =userinfo.getUe();
        Bundle bundle = new Bundle();
        bundle.putString("ue", userinfo.getUe());
        bundle.putString("ul", userinfo.getUl());
        bundle.putInt("ilv",userinfo.getIlv());
        LogUtils.Log(userinfo.getUe() + userinfo.getUl());

        EventBus.getDefault().post(EventBusC.getInstance(EventBusC.LoginEvent, bundle));
        if (context == null)
            return;
        if (context.getIntent().getAction().equals("main")) {
            context.setResult(Activity.RESULT_OK);
            context.finish();
        }
    }

}
