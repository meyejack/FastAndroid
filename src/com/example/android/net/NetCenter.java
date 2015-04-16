package com.example.android.net;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.Volley;
import com.example.android.app.AppManager;
import com.example.android.base.BaseRequest;
import com.example.android.utils.NetUtils;

/**
 * 网络访问控制中心 用于统一管理网络访问接口及相关配置
 * 
 * @author Ht
 * 
 */
public class NetCenter {
	public static final int GET = 1;
	public static final int POST = 2;
	private static NetCenter mNetCenter;
	/** 请求队列管理池 */
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

	private void accessNetwork(int method, Map<String, String> params,
			String url, Listener<String> listener, ErrorListener errorListener) {
		Context context = AppManager.getAppManager().currentActivity();
		// 判断网络是否可用
		NetUtils netUtils = new NetUtils(context);
		if (!netUtils.isNetworkConnected()) {
			return;
		}

		CustomStringRequest mRequest = new CustomStringRequest(method, url,
				listener, errorListener);

		if (params != null && params.size() > 0) {
			mRequest.setParams(params);
		}

		RequestQueue mRequestQueue = mRequestMap.get(context);
		if (mRequestQueue != null) {
			mRequestQueue.add(mRequest);
		}
	}

	/**
	 * 发起带参数的请求
	 * 
	 * @param method
	 *            请求方式 POST/GET
	 * @param t
	 *            请求参数实体类
	 * @param url
	 *            请求路径
	 * @param listener
	 *            成功回调
	 * @param errorListener
	 *            失败回调
	 */
	public <T extends BaseRequest> void send(int method, T t, String url,
			Listener<String> listener, ErrorListener errorListener) {
		Map<String, String> postParams = new HashMap<String, String>();

		switch (method) {
		case Method.GET:
			String getParams = NetUtils.getGetParams(t);
			url += getParams;
			break;
		case Method.POST:
			postParams = NetUtils.getPostParams(t);
			break;
		default:
			break;
		}

		accessNetwork(method, postParams, url, listener, errorListener);
	}

	/**
	 * 发起不带参数的请求
	 * 
	 * @param method
	 *            请求方式 POST/GET
	 * @param url
	 *            请求路径
	 * @param listener
	 *            成功回调
	 * @param errorListener
	 *            失败回调
	 */
	public <T extends BaseRequest> void send(int method, String url,
			Listener<String> listener, ErrorListener errorListener) {

		accessNetwork(method, null, url, listener, errorListener);
	}
}
