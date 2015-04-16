package com.example.android.bean.net.response;

import com.example.android.base.BaseRequest;


public class User extends BaseRequest {
	private String user;
	private String pwd;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
}
