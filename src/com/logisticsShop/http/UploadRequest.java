package com.logisticsShop.http;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.logisticsShop.utils.StringTools;

public class UploadRequest extends Request<String> {

	/**
	 * params
	 */
	private Map<String, String> params = new HashMap<String, String>();

	/**
	 * timeOut
	 */
	private int timeOut = 35000;

	/**
	 * 监听器
	 */
	private final Listener<String> listener;

	/**
	 * 初始化的类型带Param
	 */
	public UploadRequest(int method, String url, Listener<String> listener,
			ErrorListener errorListener, Map<String, String> params) {
		super(method, url, errorListener);
		this.listener = listener;
		this.params = params;
	}

	/**
	 * RetryPolicy
	 */
	@Override
	public RetryPolicy getRetryPolicy() {
		RetryPolicy retryPolicy = new DefaultRetryPolicy(timeOut,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		return retryPolicy;
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Charset", "UTF-8");
		// headers.put("Content-Type", "application/x-javascript");
		return headers;
	}

	/**
	 * 
	 * @param timeOut
	 */
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;

	}

	/**
	 * 简单的参数
	 */
	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return params;
	}

	@Override
	protected void deliverResponse(String arg0) {

		listener.onResponse(arg0);

	}

	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response) {
		try {
			String strObject = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
			if (StringTools.isEmpty(strObject)) {
				return null;
			} else {
				return Response.success(strObject,
						HttpHeaderParser.parseCacheHeaders(response));
			}

		} catch (Exception e) {
			Log.e("error", e.getMessage().toString());
			return Response.error(new ParseError(e));
		}
	}

}
