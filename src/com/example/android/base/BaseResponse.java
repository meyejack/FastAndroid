package com.example.android.base;

import java.lang.reflect.Type;

import com.google.gson.JsonSyntaxException;

/**
 * 公共响应参数
 * 
 * @author Ht
 * 
 */
public abstract class BaseResponse {
	private int code;
	private String msg;
	private String data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	/**
	 * 返回与错误码相应的string.xml的错误提示
	 * 
	 * @return
	 */
	public int getErrorRes() {
		int errorRes = -1;
		switch (code) {
		case 1000:
			// 成功
			// errorRes = R.id.xxx;
			errorRes = 1000;
			break;
		case 1001:
			// 失败
			errorRes = 1001;
			break;
		default:
			break;
		}

		return errorRes;
	}

	/**
	 * 根据code获取当前状态 code == 1000 ? true : false 该code请根据需求项目文档自行修改
	 * 
	 * @return
	 */
	public boolean getStatus() {
		return code == 1000 ? true : false;
	}

	/**
	 * 解析单条数据
	 * 
	 * @param clazz
	 * @return
	 * @throws IllegalArgumentException
	 *             参数异常(Response中data为空)
	 * @throws JsonSyntaxException
	 *             Json解析异常
	 */
	public abstract <T> T getBean(Class<T> clazz)
			throws IllegalArgumentException, JsonSyntaxException;

	/**
	 * 解析数据列表
	 * 
	 * @param typeOfT
	 * @return
	 * @throws IllegalArgumentException
	 *             参数异常(Response中data为空)
	 * @throws JsonSyntaxException
	 *             Json解析异常
	 */
	public abstract <T> T getBeanList(Type typeOfT)
			throws IllegalArgumentException, JsonSyntaxException;

}
