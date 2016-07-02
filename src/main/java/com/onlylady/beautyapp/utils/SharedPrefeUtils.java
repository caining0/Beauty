package com.onlylady.beautyapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
/**
 *保存用户设置
 *@author caining
 * */
public class SharedPrefeUtils {
	private static final String PREFERENES_SETTINGS = "appSettings";
	public static final String ACCESSTOKEN = "accessToken";
	public static final String USERID = "uid";
	public static final String USERNAME = "userName";
	public static final String USERICON = "userIcon";
	public static final String LOGINTYPE = "logintype";
	public static final String GENDER = "gender";
	public static final String LOGIN = "login";
	public static final String SJS = "sjs";
	public static final String DEVICETOKEN = "devicetoken";
	public static final String OPENID = "openid";
	public static final String ILV = "ilv";


	/**
	 * 保存用户设置
	 * 
	 * @param context
	 *            , key,flag
	 */
	public static void saveSettings(Context context, String key, int flag) {
		SharedPreferences settings = context.getSharedPreferences(
				PREFERENES_SETTINGS, Context.MODE_APPEND);
		Editor editor = settings.edit();
		editor.putInt(key, flag);
		editor.commit();
	}
	public static void saveSettings(Context context, String key, String flag) {
		SharedPreferences settings = context.getSharedPreferences(
				PREFERENES_SETTINGS, Context.MODE_APPEND);
		Editor editor = settings.edit();
		editor.putString(key, flag);
		editor.commit();
	}
	public static void saveSettings(Context context, String key[], String flag[]) {
		SharedPreferences settings = context.getSharedPreferences(
				PREFERENES_SETTINGS, Context.MODE_APPEND);
		Editor editor = settings.edit();
		for (int i = 0; i < flag.length; i++) {
			editor.putString(key[i], flag[i]);
		}
		editor.commit();
	}
	public static void saveSettings(Context context, String key, boolean flag) {
		SharedPreferences settings = context.getSharedPreferences(
				PREFERENES_SETTINGS, Context.MODE_APPEND);
		Editor editor = settings.edit();
		editor.putBoolean(key, flag);
		editor.commit();
	}

	/**
	 * 读取用户设置
	 * 
	 * @param context
	 *            , key
	 */
	public static String  getSettings(Context context, String key) {
		SharedPreferences settings = context.getSharedPreferences(
				PREFERENES_SETTINGS, Context.MODE_APPEND);
		return settings.getString(key,"");
	}
	public static int  getSettingsInt(Context context, String key) {
		SharedPreferences settings = context.getSharedPreferences(
				PREFERENES_SETTINGS, Context.MODE_APPEND);
		return settings.getInt(key,0);
	}
	public static boolean getSettings(Context context, String key,boolean def) {
		SharedPreferences settings = context.getSharedPreferences(
				PREFERENES_SETTINGS, Context.MODE_APPEND);
		boolean flag = settings.getBoolean(key, def);
		return flag;
	}

}
