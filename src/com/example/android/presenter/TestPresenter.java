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
	 * 测试方法
	 */
	public void test() {
		mUserModel.testModel(new TestListener() {

			@Override
			public void onSuccess(String result) {
				mUserView.testSuccess(result);
			}

			@Override
			public void onFailure() {
				mUserView.testFailure();
			}
		});
	}
}
