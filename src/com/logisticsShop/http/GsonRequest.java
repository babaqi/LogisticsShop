package com.logisticsShop.http;

import java.io.UnsupportedEncodingException;
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
import com.android.volley.VolleyLog;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * 泛型类型的Request，T的类型是bean 这个地方解析数据的时候使用Gson，注意返回的Json字段名字一定要和Bean的字段对应起来 Created
 * by zhang_q
 */
public class GsonRequest<T> extends Request<T> {

	/** Charset for request. */
	private static final String PROTOCOL_CHARSET = "utf-8";

	/** Content type for request. */
	private static final String PROTOCOL_CONTENT_TYPE = String.format(
			"application/json; charset=%s", PROTOCOL_CHARSET);
	/**
	 * 监听器
	 */
	private final Listener<T> listener;

	/**
	 * 解析方式
	 */
	private Gson mGson;

	/**
	 * 解析的Bean类型
	 */
	private Class<T> clazz;

	/**
	 * 上传的参数
	 */
	private String mRequestBody;

	/**
	 * params
	 */
	private Map<String, String> params = new HashMap<String, String>();

	/**
	 * headers
	 */
	private Map<String, String> headers = new HashMap<String, String>();

	/**
	 * timeOut
	 */
	private int timeOut = 20000;

	/**
	 * 初始化的类型(Post请求)（mRequestBody是转换成JsonString的字符串）
	 */
	public GsonRequest(int method, String url, String mRequestBody,
			Class<T> clazz, Listener<T> listener, ErrorListener errorListener) {
		super(method, url, errorListener);
		mGson = new Gson();
		this.clazz = clazz;
		this.listener = listener;
		this.mRequestBody = mRequestBody;

	}

	/**
	 * 初始化的类型(Get请求)
	 */
	public GsonRequest(int method, String url, Class<T> clazz,
			Listener<T> listener, ErrorListener errorListener) {
		super(method, url, errorListener);
		mGson = new Gson();
		this.clazz = clazz;
		this.listener = listener;
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

	/**
	 * 
	 * @param timeOut
	 */
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;

	}

	/**
	 * 设置几个必要的参数
	 */
	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		/** 置字符集为UTF-8,并采用gzip压缩传输 **/
		headers.put("Charset", "UTF-8");
		headers.put("Content-Type", "application/x-javascript");
		// headers.put("Accept-Encoding", "gzip,deflate");
		return headers;
	}

	/**
	 * 添加Header
	 * 
	 * @param params
	 */
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	/**
	 * 简单的参数
	 */
	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return params;
	}

	/**
	 * setParams()
	 * 
	 * @param params
	 */
	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	@Override
	protected void deliverResponse(T t) {
		listener.onResponse(t);
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String strObject = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
			T parsedGSON = mGson.fromJson(strObject, clazz);
			return Response.success(parsedGSON,
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (Exception e) {
			Log.e("error", e.getMessage().toString());
			return Response.error(new ParseError(e));
		}
	}

	@Override
	public String getBodyContentType() {
		return PROTOCOL_CONTENT_TYPE;
	}

	@Override
	public byte[] getBody() {
		try {
			return mRequestBody == null ? null : mRequestBody
					.getBytes(PROTOCOL_CHARSET);
		} catch (UnsupportedEncodingException uee) {
			VolleyLog
					.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
							mRequestBody, PROTOCOL_CHARSET);
			return null;
		}
	}

}
