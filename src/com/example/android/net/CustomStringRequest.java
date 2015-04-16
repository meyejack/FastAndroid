package com.example.android.net;

import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
/**
 * Volley自定义POST请求
 * @author Ht
 *
 */
public class CustomStringRequest extends StringRequest {
	private Map<String, String> params;
	private final String ENCODEING = "UTF-8";

	public CustomStringRequest(int method, String url, Listener<String> listener,
			ErrorListener errorListener) {
		super(method, url, listener, errorListener);
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return params == null ? super.getParams() : params;
	}

	@Override
	protected String getParamsEncoding() {
		return ENCODEING;
	}
	
	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		// TODO Auto-generated method stub
		return super.getHeaders();
	}

}
