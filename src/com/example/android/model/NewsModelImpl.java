package com.example.android.model;

import java.util.List;

import org.apache.http.Header;

import com.example.android.bean.net.response.News;
import com.example.android.bean.net.response.Response;
import com.example.android.listener.NewsListener;
import com.example.android.net.NetCenter;
import com.example.android.net.URLs;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;

public class NewsModelImpl implements NewsModel {

	/**
	 * 获取新闻列表,处理数据逻辑方法
	 */
	@Override
	public void getNewsList(final NewsListener listener) {
		// TODO 从内存缓存中获取数据(根据是否有该需求,自行实现)

		// TODO 如果未获取到,从数据库中获取持久化缓存数据(根据是否有该需求,自行实现)

		// 如果未获取到,从网络获取数据,获取完成后进行缓存
		// 获取请求路径
		String url = URLs.getURL(URLs.API_NEWS_LIST);

		// 发起请求
		NetCenter.get(url, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// 请求失败处理
				listener.onFailure();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Response response = null;
				List<News> datas = null;

				// 测试标准json接口时请删除以下两行代码,此处两行代码仅对该接口生效,该接口json数据非标准json,故需要剔除ccad(....)该字符
				responseString = responseString.replace("ccad(", "");
				responseString = responseString.replace(")", "");

				// 处理数据逻辑
				try {
					response = Response.getOnlyDataResponse(responseString);
					datas = response.getBeanList(new TypeToken<List<News>>() {
					}.getType());
				} catch (JsonSyntaxException e) {
					// Json解析异常回调
					listener.onError();
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// Response中data为空
					listener.onError();
					e.printStackTrace();
				}

				// 回调可自定义,可传参数等返回给Presenter
				if (datas != null && datas.size() > 0) {
					listener.onSuccess(datas);
					// TODO 缓存数据
				}
			}
		});
	}

}
