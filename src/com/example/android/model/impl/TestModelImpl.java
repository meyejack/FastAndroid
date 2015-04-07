package com.example.android.model.impl;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.example.android.listener.TestListener;
import com.example.android.model.ITestModel;
import com.example.android.net.NetCenter;

public class TestModelImpl implements ITestModel {

	/**
	 * 测试处理数据逻辑方法
	 */
	@Override
	public void testModel(final TestListener listener) {
		NetCenter.getInstance().test(new Listener<String>() {

			@Override
			public void onResponse(String response) {
				// TODO 处理数据逻辑
				String result = "MVP模式测试成功";
				// 回调可自定义,可传参数等返回给Presenter
				listener.onSuccess(result);
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO 处理数据逻辑
				listener.onFailure();
			}
		});
	}

}
