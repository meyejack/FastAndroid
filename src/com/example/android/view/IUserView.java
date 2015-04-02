package com.example.android.view;

import com.example.android.base.IBaseView;

public interface IUserView extends IBaseView {
	// 测试处理成功  视图更新回调
	void testSuccess(String result);
	// 测试处理失败  提示回调
	void testFailure();
}
