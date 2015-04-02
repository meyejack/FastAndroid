package com.example.android.presenter;

import com.example.android.listener.TestListener;
import com.example.android.model.ITestModel;
import com.example.android.model.impl.TestModelImpl;
import com.example.android.view.IUserView;

public class TestPresenter {
	private IUserView mUserView;
	private ITestModel mUserModel;

	public TestPresenter(IUserView view) {
		mUserView = view;
		mUserModel = new TestModelImpl();
	}

	/**
	 * 测试方法,调用Model层进行数据逻辑处理,传入回调
	 */
	public void test() {
		mUserModel.testModel(new TestListener() {

			@Override
			public void onSuccess(String result) {
				// 根据不同结果对view进行通知
				mUserView.testSuccess(result);
			}

			@Override
			public void onFailure() {
				// 根据不同结果对view进行通知
				mUserView.testFailure();
			}
		});
	}
}
