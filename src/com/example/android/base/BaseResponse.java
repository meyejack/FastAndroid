package com.example.android.base;

import java.lang.reflect.Type;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.google.gson.Gson;

/**
 * 公共响应参数
 * 
 * @author Ht
 * 
 */
public class BaseResponse {
	private int code;
	private String msg;
	private String data;

	private BaseResponse() {
	}

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
	 * 公共解析方法
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 * @throws JSONException
	 */
	public static BaseResponse baseParse(String json) throws JSONException {
		BaseResponse mResponse = new BaseResponse();

		JSONObject jsonObject = new JSONObject(json);
		mResponse.code = jsonObject.getInt("code");
		mResponse.msg = jsonObject.getString("msg");
		mResponse.data = jsonObject.getString("data");

		return mResponse;
	}

	/**
	 * 解析单条数据
	 * 
	 * @param clazz
	 * @return
	 */
	public <T> T getBean(Class<T> clazz) {
		if (TextUtils.isEmpty(getData()))
			return null;

		T object = null;

		try {
			Gson gson = new Gson();
			object = gson.fromJson(getData(), clazz);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return object;
	}

	/**
	 * 解析列表数据
	 * 
	 * @param typeOfT
	 * @return
	 */
	public <T> T getBeanList(Type typeOfT) {
		if (TextUtils.isEmpty(getData()))
			return null;

		T object = null;

		try {
			Gson gson = new Gson();
			object = gson.fromJson(getData(), typeOfT);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return object;
	}

}
