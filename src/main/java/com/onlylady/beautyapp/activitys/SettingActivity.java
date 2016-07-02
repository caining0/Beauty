package com.onlylady.beautyapp.activitys;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import com.onlylady.beautyapp.R;
import com.onlylady.beautyapp.utils.DataCleanManager;
import com.onlylady.beautyapp.utils.DialogUtils;
import com.onlylady.beautyapp.utils.SharedPrefeUtils;
import com.onlylady.beautyapp.utils.ToastUtils;

import butterknife.Bind;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity{

    @Bind(R.id.cache_size)
    TextView cacheSize;
    @Bind(R.id.logout_btn)
    TextView logout_btn;
    @Bind(R.id.version)
    TextView version;
    @Override
    public boolean useEventbus() {
        return false;
    }

    @Override
    public void createview() {
        setContentView(R.layout.activity_setting);

    }

    @Override
    public void initlisener() {

    }

    @Override
    public void initData() {
        if(!SharedPrefeUtils.getSettings(this, SharedPrefeUtils.LOGIN, false)){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                logout_btn.setAlpha(0f);
            }
            logout_btn.setClickable(false);
        }
        try {
            cacheSize.setText(DataCleanManager.getTotalCacheSize(SettingActivity.this));
        }catch (Exception e){
            e.printStackTrace();
        }
        version.setText("版本V_"+getAppVersionName(getApplicationContext()));
    }

    @OnClick(R.id.rl_clean)
    public void cleanCache(View view){
        DataCleanManager.clearAllCache(SettingActivity.this);
        cacheSize.setText("0KB");
        ToastUtils.show(getApplicationContext(),"清理完毕");
    }
    @OnClick(R.id.setting_goback)
    public void settingGoback(View view){
        finish();
    }
    @OnClick(R.id.logout_btn)
    public void logout(View view){
        DialogUtils.getInstance().dialogout(this, logout_btn);
    }
    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
//            versioncode = pi.versionCode;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
//            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }
}
