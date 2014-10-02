package com.diandian.coolco.poll.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.TypedValue;
import android.widget.Toast;

public class Util {

	public static void showShortToast(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	public static int getResIdByName(Context context, String defType, String name) {
		ApplicationInfo appInfo = context.getApplicationInfo();
		int resID = context.getResources().getIdentifier(name, defType, appInfo.packageName);
		return resID;
	}

	public static String getPrefString(Context context, String key) {
		return getPrefString(context, key, "");
	}

	public static String getPrefString(Context context, String key, String defaultValue) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		return pref.getString(key, defaultValue);
	}

	public static Boolean getPrefBoolean(Context context, String key) {
		return getPrefBoolean(context, key, false);
	}

	public static Boolean getPrefBoolean(Context context, String key, Boolean defaultValue) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		return pref.getBoolean(key, defaultValue);
	}

	public static int getPrefInt(Context context, String key) {
		return getPrefInt(context, key, 0);
	}

	public static int getPrefInt(Context context, String key, int defaultValue) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		return pref.getInt(key, defaultValue);
	}
	
	public static long getPrefLong(Context context, String key) {
		return getPrefLong(context, key, 0);
	}
	
	public static long getPrefLong(Context context, String key, long defaultValue) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		return pref.getLong(key, defaultValue);
	}

	public static void setPrefString(Context context, String key, String value) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = pref.edit();
		editor.putString(key, value).commit();
	}

	public static void setPrefBoolean(Context context, String key, boolean b) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = pref.edit();
		editor.putBoolean(key, b).commit();
	}

	public static void setPrefInt(Context context, String key, int n) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = pref.edit();
		editor.putInt(key, n).commit();
	}
	
	public static void setPrefLong(Context context, String key, long n) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = pref.edit();
		editor.putLong(key, n).commit();
	}

	public static String getSerialNum(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceId = telephonyManager.getDeviceId();
		if (deviceId == null || deviceId.trim().length() == 0 || deviceId.matches("0+")) {
			deviceId = (new StringBuilder("EMU")).append((new Random(System.currentTimeMillis())).nextLong()).toString();
		}
		return deviceId;
	}

	public static int dp2px(final Context context, float dp) {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
	
	public static String getCurrentDateTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return format.format(new Date());
	}
}
