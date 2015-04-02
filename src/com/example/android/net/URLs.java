package com.example.android.net;
/**
 * URL路径处理类
 * @author Ht
 *
 */
public class URLs {
	public static final String HOST = "http://192.168.192.209/";
	public static final String PROJECT_NAME = "project/";
	public static final String API = "api/";
	
	public static final String API_USER_LOGIN = "user/signin";
	
	public static final String API_NEWS_LIST = "news/getlist";
	
	public static String getURL(String uri){
		return HOST + PROJECT_NAME + API + uri;
	}
	
}
