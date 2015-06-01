package com.logisticsShop.utils;

import java.net.URLEncoder;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.util.Log;

public class HttpJsonRequest {

	private final static String TAG = "HttpJsonObject";

	/**
	 * Get请求方式
	 */
	public static JSONObject getJosnStringGet(String url, JSONObject json) {
		try {
//			HttpParams params = null;
			HttpClient mHttpClient = new DefaultHttpClient();
			StringBuilder sb = new StringBuilder();
			sb.append(url);
			sb.append(URLEncoder.encode(json.toString(), "utf-8"));
//			sb.append(json.toString());
			HttpGet request = new HttpGet(sb.toString());
			request.addHeader("Content-Type",
					"application/json; encoding=utf-8");
			request.addHeader("Accept", "application/json");
//			HttpConnectionParams.setConnectionTimeout(params, 30000);
//			HttpConnectionParams.setSoTimeout(params, 35000);
//			request.setParams(params);
			HttpResponse httpResponse = mHttpClient.execute(request);
			String retSrc = EntityUtils.toString(httpResponse.getEntity(),
					"utf-8");
			return new JSONObject(retSrc);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			return null;
		}
	}

	/**
	 * Post请求方式
	 */
	public static JSONObject getJosnStringPost(String url,
			List<NameValuePair> nameValuePair) {
		try {
			HttpPost request = new HttpPost(url);
			HttpClient mHttpClient = new DefaultHttpClient();
			request.setEntity(new UrlEncodedFormEntity(nameValuePair, "utf-8"));
			HttpParams params = null;
			HttpConnectionParams.setConnectionTimeout(params, 30000);
			HttpConnectionParams.setSoTimeout(params, 35000);
			request.setParams(params);
			HttpResponse httpResponse = mHttpClient.execute(request);
			String retSrc = EntityUtils.toString(httpResponse.getEntity(),
					"utf-8");
			return new JSONObject(retSrc);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			return null;
		}
	}
}