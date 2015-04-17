package com.example.android.net;

import java.util.Map;

import android.content.Context;

import com.example.android.app.AppManager;
import com.example.android.base.BaseRequest;
import com.example.android.utils.NetUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 网络访问控制中心 用于统一管理网络访问接口及相关配置
 * 
 * @author Ht
 * 
 */
public class NetCenter {

	private static final int GET = 1;
	private static final int POST = 2;
	private static final int PUT = 3;

	// 连接超时时间
	private static final int CONNECTTIMEOUT = 15 * 1000;
	// 最大连接数
	private static final int MaxConnections = 15;
	// 失败重连次数
	private static final int MaxRetries = 3;
	// 失败重连间隔时间
	private static final int RetriesTimeout = 5 * 1000;
	// 响应超时时间
	private static final int ResponseTimeout = 15 * 1000;

	/** 如需扩展更多功能,请浏览官方API自行封装：http://loopj.com/android-async-http/ */
	private static AsyncHttpClient client = new AsyncHttpClient();

	static {
		// 设置连接超时时间
		client.setConnectTimeout(CONNECTTIMEOUT);
		// 设置最大连接数
		client.setMaxConnections(MaxConnections);
		// 设置重连次数以及间隔时间
		client.setMaxRetriesAndTimeout(MaxRetries, RetriesTimeout);
		// 设置响应超时时间
		client.setResponseTimeout(ResponseTimeout);
	}

	/**
	 * 发起带参数的get请求
	 * 
	 * @param url
	 *            请求路径
	 * @param t
	 *            继承自BaseRequest的请求参数实体类
	 * @param responseHandler
	 *            响应回调
	 */
	public static <T extends BaseRequest> void get(String url, T t,
			AsyncHttpResponseHandler responseHandler) {

		sendRequest(GET, url, t, responseHandler);
	}

	/**
	 * 发起不带参数get请求
	 * 
	 * @param url
	 *            请求路径
	 * @param responseHandler
	 *            响应回调
	 */
	public static void get(String url, AsyncHttpResponseHandler responseHandler) {

		get(url, null, responseHandler);
	}

	/**
	 * 发起带参数post请求
	 * 
	 * @param url
	 *            请求路径
	 * @param t
	 *            继承自BaseRequest的请求参数实体类
	 * @param responseHandler
	 *            响应回调
	 */
	public static <T extends BaseRequest> void post(String url, T t,
			AsyncHttpResponseHandler responseHandler) {

		sendRequest(POST, url, t, responseHandler);
	}

	/**
	 * 发起不带参数post请求
	 * 
	 * @param url
	 *            请求路径
	 * @param responseHandler
	 *            响应回调
	 */
	public static void post(String url, AsyncHttpResponseHandler responseHandler) {

		post(url, null, responseHandler);
	}

	/**
	 * 发起网络请求
	 * 
	 * @param type
	 *            请求类型
	 * @param url
	 *            请求路径
	 * @param params
	 *            请求参数
	 * @param responseHandler
	 *            响应回调
	 */
	private static <T extends BaseRequest> void sendRequest(int type,
			String url, T t, AsyncHttpResponseHandler responseHandler) {
		// 将传入的请求实体类映射成Map
		Map<String, String> params = NetUtils.getParams(t);
		// 将Map转换成请求参数
		RequestParams requestParams = new RequestParams(params);

		// 获取当前页面的Context
		Context context = AppManager.getAppManager().currentActivity();

		// 判断网络是否可用
		if (!NetUtils.isNetworkConnected(context)) {
			return;
		}

		// 根据传入类型调用不同请求方法,可自行扩展
		// 传入Context以便与生命周期联动
		switch (type) {
		case GET:
			// 发起get请求,获取请求处理器
			client.get(context, url, requestParams, responseHandler);
			break;
		case POST:
			// 发起post请求,获取请求处理器
			client.post(context, url, requestParams, responseHandler);
			break;
		case PUT:
			// 发起post请求,获取请求处理器
			// .....
			client.put(context, url, requestParams, responseHandler);
		default:
			// 默认发起get请求
			client.get(context, url, requestParams, responseHandler);
			break;
		}
	}

	/** 取消当前Context的请求队列 */
	public static void clearRequestQueue() {
		Context context = AppManager.getAppManager().currentActivity();
		// 销毁指定Context的请求, 第二个参数true代表强制结束
		client.cancelRequests(context, true);
	}
}
