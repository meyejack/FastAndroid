package com.example.android.presenters;

import java.util.List;

import com.example.android.bean.net.response.News;
import com.example.android.listener.NewsListener;
import com.example.android.model.NewsModelImpl;
import com.example.android.model.NewsModel;
import com.example.android.ui.views.NewsView;

public class NewsPresenterImpl implements NewsPresenter {
	private NewsView mNewsView;
	private NewsModel mUserModel;

	public NewsPresenterImpl() {
		mUserModel = new NewsModelImpl();
	}

	/**
	 * 测试方法,调用Model层进行数据逻辑处理,传入自定义回调回调
	 */
	public void showNews() {
		mUserModel.getNewsList(new NewsListener() {

			@Override
			public void onSuccess(List<News> datas) {
				// 根据不同结果对view进行通知
				mNewsView.showNewsS(datas);
			}

			@Override
			public void onFailure() {
				// 根据不同结果对view进行通知
				mNewsView.showNetError();
			}

			@Override
			public void onError() {
				// 解析异常
				mNewsView.showParseError();
			}
		});
	}

	@Override
	public void init(NewsView view) {
		mNewsView = view;
	}
}
