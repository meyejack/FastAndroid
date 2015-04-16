package com.example.android.utils;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.android.app.AppManager;
import com.example.android.base.BaseActivity;
import com.example.android.base.BaseRequest;

/**
 * 网络相关工具类
 * 
 * @author Ht
 * 
 */
public class NetUtils {
	private Context context;

	public NetUtils(Context context) {
		this.context = context;
	}

	/**
	 * 判断当前网络是否可用
	 * 
	 * @return
	 */
	public boolean isNetworkConnected() {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (mConnectivityManager == null) {
				showNetErrorDialog();
				return false;
			}
			NetworkInfo[] infos = mConnectivityManager.getAllNetworkInfo();
			if (infos != null) {
				for (NetworkInfo info : infos) {
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}

		showNetErrorDialog();
		return false;
	}

	private void showNetErrorDialog() {
		try {
			BaseActivity activity = (BaseActivity) AppManager.getAppManager()
					.currentActivity();
			activity.hideProgress();
		} catch (Exception e) {
			e.printStackTrace();
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(context,
				AlertDialog.THEME_HOLO_DARK);
		builder.setTitle("提示");
		builder.setMessage("当前网络不可用,请检查网络设置。");
		builder.setNegativeButton("去设置", new AlertDialog.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {

			}
		});

		builder.setPositiveButton("取消", new AlertDialog.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {

			}
		});
		builder.show();

	}

	/**
	 * 判断当前wifi是否可用
	 * 
	 * @return
	 */
	public boolean isWifiConnected() {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mWiFiNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (mWiFiNetworkInfo != null) {
				return mWiFiNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 判断MOBILE网络是否可用
	 * 
	 * @return
	 */
	public boolean isMobileConnected() {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mMobileNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (mMobileNetworkInfo != null) {
				return mMobileNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 获取当前网络连接的类型信息 1 wifi 2 移动网络 -1 无网络
	 * 
	 * @return
	 */
	public int getConnectedType() {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
				return mNetworkInfo.getType() == mConnectivityManager.TYPE_WIFI ? 1
						: 2;
			}
		}
		return -1;
	}

	/**
	 * 获取post请求参数
	 * 
	 * @param t
	 * @return
	 */
	public static <T extends BaseRequest> Map<String, String> getPostParams(T t) {
		Class<? extends BaseRequest> clazz = t.getClass();
		Class<? extends Object> superclass = clazz.getSuperclass();

		Field[] fields = clazz.getDeclaredFields();
		Field[] superFields = superclass.getDeclaredFields();

		if (fields == null || fields.length == 0) {
			return Collections.emptyMap();
		}

		Map<String, String> params = new HashMap<String, String>();
		try {
			for (Field field : fields) {
				field.setAccessible(true);
				params.put(field.getName(), String.valueOf(field.get(t)));
			}

			for (Field field : superFields) {
				field.setAccessible(true);
				params.put(field.getName(), String.valueOf(field.get(t)));
			}

		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return params;
	}

	/**
	 * 获取get请求参数
	 * 
	 * @param t
	 * @return
	 */
	public static <T extends BaseRequest> String getGetParams(T t) {
		Class<? extends BaseRequest> clazz = t.getClass();
		Class<? extends Object> superclass = clazz.getSuperclass();

		Field[] fields = clazz.getDeclaredFields();
		Field[] superFields = superclass.getDeclaredFields();

		if (fields == null || fields.length == 0) {
			return "";
		}

		StringBuilder builder = new StringBuilder();
		builder.append("?");
		try {
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				field.setAccessible(true);
				builder.append(field.getName() + "="
						+ String.valueOf(field.get(t)));

				if (i != fields.length - 1) {
					builder.append("&");
				}
			}

			for (int i = 0; i < superFields.length; i++) {
				Field field = superFields[i];
				field.setAccessible(true);
				builder.append("&");
				builder.append(field.getName() + "="
						+ String.valueOf(field.get(t)));
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return builder.toString();
	}
}
