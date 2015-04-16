package com.example.android.net;
/**
 * URL路径处理类
 * @author Ht
 *
 */
public class URLs {
	public static final String HOST = "http://int.dpool.sina.com.cn/iplookup/iplookup.php";
	public static final String PROJECT_NAME = "";
	public static final String API = "";
	
	public static final String API_TEST = "";
	
	/**
	 * 拼接请求路径
	 * @param uri
	 * @return
	 */
	public static String getURL(String uri){
		return HOST + PROJECT_NAME + API + uri;
	}
	
}
