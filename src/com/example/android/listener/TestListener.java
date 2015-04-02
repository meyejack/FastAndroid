package com.example.android.listener;
/**
 * 自定义Presenter回调
 * @author user
 *
 */
public interface TestListener {
	/** 成功回调 */
	void onSuccess(String result);
	/** 失败回调 */
	void onFailure();
}
