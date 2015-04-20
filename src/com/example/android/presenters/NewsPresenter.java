package com.example.android.presenters;

import com.example.android.base.BasePresenter;
import com.example.android.ui.views.NewsView;

public interface NewsPresenter extends BasePresenter<NewsView> {
	void showNews();
}
