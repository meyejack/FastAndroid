package com.example.android.ui;

import java.util.List;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import butterknife.InjectView;

import com.example.android.R;
import com.example.android.adapter.NewsAdapter;
import com.example.android.base.BaseActivity;
import com.example.android.bean.net.response.News;
import com.example.android.presenters.NewsPresenterImpl;
import com.example.android.ui.custom.HeaderLayout;
import com.example.android.ui.views.NewsView;

public class NewsActivity extends BaseActivity implements NewsView {

	private HeaderLayout mTitleBar;

	@InjectView(R.id.lv_news)
	ListView lvNews;

	private NewsPresenterImpl mNewsPresenter;

	private NewsAdapter newsAdapter;

	@Override
	public void initContentView() {
		setContentView(R.layout.activity_main);
	}

	@Override
	public void initView() {
		initTitleBar();

		initListView();

		// MVP模式开始
		mNewsPresenter.showNews();
	}

	/**
	 * 初始化用于展示News的ListView
	 */
	private void initListView() {
		newsAdapter = new NewsAdapter(this);
		lvNews.setAdapter(newsAdapter);
	}

	/**
	 * 初始化自定义的TitleBar
	 */
	private void initTitleBar() {
		mTitleBar = getTitleBar();
		mTitleBar.setTitleBar("新闻列表-MVP测试页面",
				R.drawable.abc_ab_bottom_solid_light_holo,
				R.drawable.abc_ab_bottom_solid_dark_holo);
		mTitleBar.setLeftListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showToast("点击了左边");
			}
		});
		mTitleBar.setRightListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showToast("点击了右边");
			}
		});
	}

	@Override
	public void initPresenter() {
		mNewsPresenter = new NewsPresenterImpl();
		mNewsPresenter.init(this);
	}

	@Override
	public void showNewsS(List<News> datas) {
		newsAdapter.setData(datas);
		newsAdapter.notifyDataSetChanged();
	}

}
