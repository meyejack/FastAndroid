package com.example.android.ui.views;

import java.util.List;

import com.example.android.base.IBaseView;
import com.example.android.bean.net.response.News;

public interface NewsView extends IBaseView {
	/** 显示新闻处理成功的回调,S为Success的简写 */
	void showNewsS(List<News> datas);
}
