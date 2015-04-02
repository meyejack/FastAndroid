package com.example.android.utils;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;

import com.example.android.Cookie;
import com.example.android.R;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

/**
 * 通用工具类
 * 
 * @author Ht
 * 
 */
public class CommonUtils {

	/**
	 * 隐藏软键盘
	 * 
	 * @param activity
	 */
	public static void hideSoftKeybord(Activity activity) {

		if (null == activity) {
			return;
		}
		try {
			final View v = activity.getWindow().peekDecorView();
			if (v != null && v.getWindowToken() != null) {
				InputMethodManager imm = (InputMethodManager) activity
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
			}
		} catch (Exception e) {

		}
	}

	/**
	 * 验证json合法性
	 * 
	 * @param jsonContent
	 * @return
	 */
	public static boolean isJsonFormat(String jsonContent) {
		try {
			new JsonParser().parse(jsonContent);
			return true;
		} catch (JsonParseException e) {
			LogUtils.i("bad json: " + jsonContent);
			return false;
		}
	}

	/**
	 * 抖动动画
	 * 
	 * @param context
	 * @param view
	 */
	public static void startShakeAnim(Context context, View view) {
		Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake);
		view.startAnimation(shake);
	}

	/**
	 * 获取应用版本号
	 * 
	 * @param context
	 * @return
	 */
	public static String softVersion(Context context) {
		PackageInfo info = null;
		try {
			info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return info.versionName;
	}

	/**
	 * 验证cookie的有效性
	 * 
	 * @param url
	 * @param domain
	 * @return
	 */
	public static boolean checkCookie(String url, Cookie cookie) {
		String rex = "\\*" + cookie.getDomain() + "\\*";
		// 验证domain的有效性
		if (!TextUtils.isEmpty(cookie.getDomain())) {
			if (!url.matches(rex)) {
				return false;
			}
		}

		// 验证生命周期的有效性
		Date expires = cookie.getExpires();
		if (expires == null) {
			return true;
		}

		return expires.after(new Date());
	}
}
