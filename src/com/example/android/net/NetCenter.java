package com.example.android.net;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.app.AppManager;
import com.example.android.utils.NetUtils;

/**
 * 网络访问控制中心 用于统一管理网络访问接口及相关配置
 * 
 * @author Ht
 * 
 */
public class NetCenter {
	private static NetCenter mNetCenter;
	private Map<Context, RequestQueue> mRequestMap;

	private NetCenter() {
		mRequestMap = new HashMap<Context, RequestQueue>();
	}

	public static NetCenter getInstance() {
		if (mNetCenter == null) {
			mNetCenter = new NetCenter();
		}

		return mNetCenter;
	}

	/**
	 * 根据Context创建一个请求队列,用于与Activity生命周期联动
	 * 
	 * @param context
	 */
	public RequestQueue init(Context context) {
		RequestQueue mRequestQueue = Volley.newRequestQueue(context);
		mRequestMap.put(context, mRequestQueue);
		return mRequestQueue;
	}

	/** 清除当前Activity的请求队列 */
	public void clearRequestQueue() {
		Context context = AppManager.getAppManager().currentActivity();
		RequestQueue mRequestQueue = mRequestMap.get(context);
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(context);
			mRequestQueue = null;
		}
	}

	/**
	 * 发起一个带tag的post请求
	 * 
	 * @param params
	 *            请求参数
	 * @param url
	 *            请求url地址
	 * @param listener
	 *            请求成功回调
	 * @param errorListener
	 *            请求失败回调
	 * @param tag
	 *            请求标记,用于取消请求
	 */
	private void post(Map<String, String> params, String url,
			Listener<String> listener, ErrorListener errorListener) {
		Context context = AppManager.getAppManager().currentActivity();
		// 判断网络是否可用
		NetUtils netUtils = new NetUtils(context);
		if (!netUtils.isNetworkConnected()) {
			return;
		}

		StringPostRequest mRequest = new StringPostRequest(url, listener,
				errorListener);
		mRequest.setParams(params);
		RequestQueue mRequestQueue = mRequestMap.get(context);
		if (mRequestQueue != null) {
			mRequestQueue.add(mRequest);
		}
	}

	/**
	 * 发起一个带tag的get请求
	 * 
	 * @param url
	 *            请求url地址
	 * @param listener
	 *            请求成功回调
	 * @param errorListener
	 *            请求失败回调
	 * @param tag
	 *            请求标记,用于取消请求
	 */
	private void get(String url, Listener<String> listener,
			ErrorListener errorListener) {
		Context context = AppManager.getAppManager().currentActivity();
		// 判断网络是否可用
		NetUtils netUtils = new NetUtils(context);
		if (!netUtils.isNetworkConnected()) {
			return;
		}

		StringRequest mRequest = new StringRequest(url, listener, errorListener);
		RequestQueue mRequestQueue = mRequestMap.get(context);
		if (mRequestQueue != null) {
			mRequestQueue.add(mRequest);
		}
	}

	/**
	 * 返回带有公共参数的请求参数
	 * 
	 * @return 公共参数Map
	 */
	private Map<String, String> getBaseParams() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("base", "val");

		return params;
	}

	/**
	 * 测试接口
	 * 
	 * @param user
	 * @param listener
	 * @param errorListener
	 */
	public void test(Listener<String> listener,
			ErrorListener errorListener) {
		String url = URLs.getURL(URLs.API_TEST);

		// 发起post请求
		// Map<String, String> params = getBaseParams();
		// params.put("user", user);
		// params.put("pwd", pwd);
		// post(params, url, listener, errorListener);
		
		// 发起get请求
		get(url, listener, errorListener);
	}
}
