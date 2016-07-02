package com.onlylady.beautyapp;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.onlylady.beautyapp.utils.UmengUtils;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import java.util.List;


public class BaseApp extends Application{
	private static Context context;
	public static Context getAppContext() {
		return context;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
//		String processName = getProcessName(this, android.os.Process.myPid());
//		if (getApplicationInfo().packageName.equals(processName)) {
//			//TODO CrashHandler是一个抓取崩溃log的工具类（可选）
//
//			LeCloudPlayerConfig.getInstance().setDeveloperMode(true).setIsApp().setUseLiveToVod(true);
//			LeCloudProxy.init(getApplicationContext());
//		}

		MobclickAgent.updateOnlineConfig(context);
		AnalyticsConfig.enableEncrypt(true);//日志加密
		MobclickAgent.setDebugMode(true);
		MobclickAgent.openActivityDurationTrack(false);
		UmengUtils.getInstance().umengSDKInit(PushAgent.getInstance(this),this);//友盟初始化
	}



	public static String getProcessName(Context cxt, int pid) {
		ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
		if (runningApps != null) {
			for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
				if (procInfo.pid == pid) {
					return procInfo.processName;
				}
			}
		}

		return null;
	}



}
