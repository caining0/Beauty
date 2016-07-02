package com.onlylady.beautyapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.onlylady.beautyapp.R;
import com.onlylady.beautyapp.configs.Configs;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 * Created by Administrator on 2015/12/3.
 */
public class PhoneInfo {
    public static PhoneInfo phoneInfo;
    public static int uid;
    public static String untoken;
    public static String sjs;
    public static String openId;
    public static int conn;
    public static String username;

    public static PhoneInfo getInstance() {
        if (phoneInfo == null)
            phoneInfo = new PhoneInfo();
        return phoneInfo;
    }


    public String getPhoneInfo(Context context) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("tzone", 8);
            jsonObject.put("res", getwh(context));//"640*1136"
            jsonObject.put("pkg", "");
            jsonObject.put("chan", getChannelName(context));
            jsonObject.put("os", 0);
            jsonObject.put("osvs", Build.VERSION.RELEASE);
            jsonObject.put("model", Build.BRAND + "," + Build.MODEL);
            jsonObject.put("avs", getAppVersionName(context));
//            jsonObject.put("", "zh-Hans");
//            jsonObject.put("device_id", getdeviceid(context));
            jsonObject.put("uid", uid);
            jsonObject.put("aname", context.getString(R.string.app_name));
//            "conn": "wifi", //连网方式
//                    "carrier": "移动", //运营商
//                    "mac": "34-17-EB-BA-B4-29", //硬件终端地址
//                    "oid": "40fd69715e7b663fd3b0e4f221033ae6c49f1426", //iOS 终端设备的OpenUDID
//                    "imei": "354707046861623", //Android操作系统设备号
//                    "aid": "9774d56d682e549c", //Android用户终端的AndroidID
//                    "aaid": "38400000-8cf0-11bd-b23e-10b96e40000d", //Android Advertiser ID
            jsonObject.put("conn", conn);
            jsonObject.put("carr", getOperators(context));
            jsonObject.put("mac", getMac());
            jsonObject.put("imei", getIMEI(context));
            jsonObject.put("aid", Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID));
//            jsonObject.put("aaid", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    private String getwh(Context context) {
        // 方法1 Android获得屏幕的宽和高
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            display.getSize(size);
        }
        int width = size.x;
        int height = size.y;
        return width + "*" + height;
    }

    private String getDeviceInfo(Context context) {
        try {
            JSONObject json = new JSONObject();
            String device_id = getdeviceid(context);

            android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);

            String mac = wifi.getConnectionInfo().getMacAddress();
            json.put("mac", mac);

            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }

            if (TextUtils.isEmpty(device_id)) {
                device_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }

            json.put("device_id", device_id);

            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getdeviceid(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }


    /**
     * 获取渠道名
     *
     * @param ctx 此处习惯性的设置为activity，实际上context就可以
     * @return 如果没有获取成功，那么返回值为空
     */
    private String getChannelName(Context ctx) {
        if (isApkDebugable(ctx)) return "debug";
        if (ctx == null) {
            return null;
        }
        String channelName = "";
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                //注意此处为ApplicationInfo 而不是 ActivityInfo,因为友盟设置的meta-data是在application标签中，而不是某activity标签中，所以用ApplicationInfo
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        //key换成说需要的key
                        channelName = applicationInfo.metaData.getString("UMENG_CHANNEL");
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            return channelName;
        }
        return channelName;
    }

    public boolean isApkDebugable(Context context) {
//        try {
//            ApplicationInfo info = context.getApplicationInfo();
//            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
//        } catch (Exception e) {
//
//        }
        if (Configs.CONNCTTO170.equals("1")) {
            return true;
        } else {
            return false;
        }
    }

    public String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "version error", e);
        }
        return versionName;
    }

    /**
     * 返回运营商 需要加入权限 <uses-permission android:name="android.permission.READ_PHONE_STATE">
     *
     * @return 1, 代表中国移动，2，代表中国联通，3，代表中国电信，0，代表未知
     * @author youzc@yiche.com
     */
    public int getOperators(Context context) {
        // 移动设备网络代码（英语：Mobile Network Code，MNC）是与移动设备国家代码（Mobile Country Code，MCC）
        // （也称为“MCC /
        // MNC”）相结合, 例如46000，前三位是MCC，后两位是MNC 获取手机服务商信息
        int OperatorsName = 0;
        String IMSI = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
        // IMSI号前面3位460是国家，紧接着后面2位00 运营商代码
        if (IMSI == null) return 0;
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002") || IMSI.startsWith("46007")) {
            OperatorsName = 1;
        } else if (IMSI.startsWith("46001") || IMSI.startsWith("46006")) {
            OperatorsName = 2;
        } else if (IMSI.startsWith("46003") || IMSI.startsWith("46005")) {
            OperatorsName = 3;
        }
        return OperatorsName;
    }

    private String getMac() {
        String macSerial = null;
        String str = "";
        try {
            Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            for (; null != str; ) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (IOException ex) {
            // 赋予默认值
            ex.printStackTrace();
        }
        if (macSerial == null) return "";
        return macSerial.replace(":", "-");
    }

    public String getIMEI(Context context) {
        return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }


    /**
     * @param activity
     * @return > 0 success; <= 0 fail
     */
    public int getStatusHeight(Activity activity) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(
                        localObject).toString());
                statusHeight = activity.getResources().getDimensionPixelSize(i5);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }
}
