package com.example.android.net;
/**
 * URL路径处理类
 * @author Ht
 *
 */
public class URLs {
	public static final String HOST = "http://www.ccad.gov.cn/";
	public static final String PROJECT_NAME = "";
	public static final String API = "api/";
	
	// 新闻列表
	public static final String API_NEWS_LIST = "27.json";
	
	/**
	 * 拼接请求路径
	 * @param uri
	 * @return
	 */
	public static String getURL(String uri){
		return HOST + PROJECT_NAME + API + uri;
	}
	
}
