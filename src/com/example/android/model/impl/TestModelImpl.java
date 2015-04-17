package com.example.android.model.impl;

import org.apache.http.Header;

import com.example.android.bean.net.response.User;
import com.example.android.listener.TestListener;
import com.example.android.model.ITestModel;
import com.example.android.net.NetCenter;
import com.example.android.net.URLs;
import com.loopj.android.http.TextHttpResponseHandler;

public class TestModelImpl implements ITestModel {

	/**
	 * 测试处理数据逻辑方法
	 */
	@Override
	public void testModel(final TestListener listener) {
		// 获取请求路径
 		String url = URLs.getURL(URLs.API_TEST);
 		// 构造请求参数
		User user = new User();
		user.setPwd("haha");
		user.setUser("ht");
		
		// 发起请求
		NetCenter.post(url, user, new TextHttpResponseHandler("GBK"){
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO 处理数据逻辑
				listener.onFailure();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				// TODO 处理数据逻辑
				String result = "MVP模式测试成功";
				// 回调可自定义,可传参数等返回给Presenter
				listener.onSuccess(result + "结果是" + responseString);
			}
		});
	}

}
