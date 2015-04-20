package com.example.android.bean.net.response;

import java.lang.reflect.Type;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.example.android.base.BaseResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class Response extends BaseResponse {

	private String dateFormat = "yyyy-MM-dd";

	/**
	 * 解析json,获取响应对象
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 * @return
	 * @throws JSONException
	 */
	public static Response getResponse(String json) throws JSONException {
		Response mResponse = new Response();

		JSONObject jsonObject = new JSONObject(json);
		int code = jsonObject.getInt("code");
		String msg = jsonObject.getString("msg");
		String data = jsonObject.getString("data");

		mResponse.setCode(code);
		mResponse.setMsg(msg);
		mResponse.setData(data);

		return mResponse;
	}

	/**
	 * 不解析json,直接作为响应的data封装后返回
	 * 
	 * @param json
	 * @return
	 */
	public static Response getOnlyDataResponse(String json) {
		Response mResponse = new Response();

		mResponse.setData(json);

		return mResponse;
	}

	/**
	 * 设置Gson解析Date的解析格式
	 * 
	 * @param format
	 */
	public void setGsonDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	private Gson getGson() {
		return new GsonBuilder().setDateFormat(dateFormat).create();
	}

	public <T> T getBean(Class<T> clazz) throws IllegalArgumentException,
			JsonSyntaxException {
		if (TextUtils.isEmpty(getData()))
			throw new IllegalArgumentException(
					"In the Response, data can't be empty");

		T object = null;

		try {
			Gson gson = getGson();
			object = gson.fromJson(getData(), clazz);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw e;
		}

		return object;
	}

	public <T> T getBeanList(Type typeOfT) throws IllegalArgumentException,
			JsonSyntaxException {
		if (TextUtils.isEmpty(getData()))
			throw new IllegalArgumentException(
					"In the Response, data can't be empty");

		T object = null;

		try {
			Gson gson = getGson();
			object = gson.fromJson(getData(), typeOfT);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw e;
		}

		return object;
	}
}
