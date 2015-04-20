package com.example.android.listener;

import java.util.List;

import com.example.android.bean.net.response.News;

/**
 * 自定义Presenter回调
 * 
 * @author user
 * 
 */
public interface NewsListener {
	/** 成功回调 */
	void onSuccess(List<News> datas);

	/** 失败回调 */
	void onFailure();
	
	/** 异常回调 */
	void onError();
}
