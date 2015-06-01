package com.logisticsShop.http;

/**
 * Request 请求管理器
 */
import java.net.URLEncoder;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.logisticsShop.base.BaseApplication;
import com.logisticsShop.commen.Constants;
import com.logisticsShop.serviceBean.AddressBean;
import com.logisticsShop.serviceBean.AreaBean;
import com.logisticsShop.serviceBean.ContextBean;
import com.logisticsShop.serviceBean.ShopBean;
import com.logisticsShop.utils.StringTools;

/**
 * Created by zhang_q
 */
public class RequestManager {

	/** 接口 **/
	private ResponseInterface mResponseInterface;

	/** Gson解析 **/
	private Gson gson;

	/**
	 * 初始化
	 */
	public RequestManager() {
		gson = new Gson();
	}

	// -------------------------请求服务器的方法写在此下面---------------------------------------------
	/**
	 * 用户登录
	 * 
	 * @param url
	 * @param params
	 */

	public void requestShopLogin(Map<String, Object> params) {
		GsonRequest<ShopBean> request = new GsonRequest<ShopBean>(
				Request.Method.GET, returnGetUrl(Constants.URL
						+ Constants.ServiceInterFace.ShopLoginUrl, params),
				ShopBean.class, new Listener<ShopBean>() {

					@Override
					public void onResponse(ShopBean arg0) {

						mResponseInterface.successResponse(arg0,
								Constants.RequestTag.ShopLoginTag);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						mResponseInterface.errorResonse(Constants.NetError,
								Constants.RequestTag.ShopLoginTag);
					}
				});

		/** 添加标签 **/
		request.setTag(Constants.RequestTag.ShopLoginTag);

		/** 添加执行 **/
		BaseApplication.getRequestQueuemanager().add(request);

	}

	/**
	 * (用户注册)
	 * 
	 * @param url
	 * @param params
	 */

	public void requestShopRegister(Map<String, Object> params) {
		GsonRequest<ShopBean> request = new GsonRequest<ShopBean>(
				Request.Method.GET, returnGetUrl(Constants.URL
						+ Constants.ServiceInterFace.ShopRegisterUrl, params),
				ShopBean.class, new Listener<ShopBean>() {

					@Override
					public void onResponse(ShopBean arg0) {

						mResponseInterface.successResponse(arg0,
								Constants.RequestTag.ShopRegisterTag);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						mResponseInterface.errorResonse(Constants.NetError,
								Constants.RequestTag.ShopRegisterTag);
					}
				});

		/** 添加标签 **/
		request.setTag(Constants.RequestTag.ShopRegisterTag);

		/** 添加执行 **/
		BaseApplication.getRequestQueuemanager().add(request);

	}

	/**
	 * (提交菜单)
	 * 
	 * @param url
	 * @param params
	 */

	public void requestSubmit(Map<String, Object> params) {
		GsonRequest<ContextBean> request = new GsonRequest<ContextBean>(
				Request.Method.GET, returnGetUrl(Constants.URL
						+ Constants.ServiceInterFace.SubmitContextUrl, params),
				ContextBean.class, new Listener<ContextBean>() {

					@Override
					public void onResponse(ContextBean arg0) {

						mResponseInterface.successResponse(arg0,
								Constants.RequestTag.SubmitContextTag);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						mResponseInterface.errorResonse(Constants.NetError,
								Constants.RequestTag.SubmitContextTag);
					}
				});

		/** 添加标签 **/
		request.setTag(Constants.RequestTag.SubmitContextTag);

		/** 添加执行 **/
		BaseApplication.getRequestQueuemanager().add(request);

	}

	/**
	 * (根据城市返回地区)
	 * 
	 * @param url
	 * @param params
	 */

	public void requestArea(Map<String, Object> params) {
		GsonRequest<AreaBean> request = new GsonRequest<AreaBean>(
				Request.Method.GET, returnGetUrl(Constants.URL
						+ Constants.ServiceInterFace.AreaUrl, params),
				AreaBean.class, new Listener<AreaBean>() {

					@Override
					public void onResponse(AreaBean arg0) {

						mResponseInterface.successResponse(arg0,
								Constants.RequestTag.AreaTag);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						mResponseInterface.errorResonse(Constants.NetError,
								Constants.RequestTag.AreaTag);
					}
				});

		/** 添加标签 **/
		request.setTag(Constants.RequestTag.AreaTag);

		/** 添加执行 **/
		BaseApplication.getRequestQueuemanager().add(request);

	}

	/**
	 * (根据地区返回地址)
	 * 
	 * @param url
	 * @param params
	 */

	public void requestAddress(Map<String, Object> params) {
		GsonRequest<AddressBean> request = new GsonRequest<AddressBean>(
				Request.Method.GET, returnGetUrl(Constants.URL
						+ Constants.ServiceInterFace.AddressUrl, params),
				AddressBean.class, new Listener<AddressBean>() {

					@Override
					public void onResponse(AddressBean arg0) {

						mResponseInterface.successResponse(arg0,
								Constants.RequestTag.AddressTag);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						mResponseInterface.errorResonse(Constants.NetError,
								Constants.RequestTag.AddressTag);
					}
				});

		/** 添加标签 **/
		request.setTag(Constants.RequestTag.AddressTag);

		/** 添加执行 **/
		BaseApplication.getRequestQueuemanager().add(request);

	}

	/**
	 * (查询菜单)
	 * 
	 * @param url
	 * @param params
	 */

	public void requestSeclect(Map<String, Object> params) {
		GsonRequest<ContextBean> request = new GsonRequest<ContextBean>(
				Request.Method.GET, returnGetUrl(Constants.URL
						+ Constants.ServiceInterFace.QueryContextUrl, params),
				ContextBean.class, new Listener<ContextBean>() {

					@Override
					public void onResponse(ContextBean arg0) {

						mResponseInterface.successResponse(arg0,
								Constants.RequestTag.QueryContextTag);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						mResponseInterface.errorResonse(Constants.NetError,
								Constants.RequestTag.QueryContextTag);
					}
				});

		/** 添加标签 **/
		request.setTag(Constants.RequestTag.QueryContextTag);

		/** 添加执行 **/
		BaseApplication.getRequestQueuemanager().add(request);

	}

	/**
	 * (更新菜单)
	 * 
	 * @param url
	 * @param params
	 */

	public void requestUpdate(Map<String, Object> params) {
		GsonRequest<ContextBean> request = new GsonRequest<ContextBean>(
				Request.Method.GET, returnGetUrl(Constants.URL
						+ Constants.ServiceInterFace.UpdateContextUrl, params),
				ContextBean.class, new Listener<ContextBean>() {

					@Override
					public void onResponse(ContextBean arg0) {

						mResponseInterface.successResponse(arg0,
								Constants.RequestTag.UpdateContextTag);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						mResponseInterface.errorResonse(Constants.NetError,
								Constants.RequestTag.UpdateContextTag);
					}
				});

		/** 添加标签 **/
		request.setTag(Constants.RequestTag.UpdateContextTag);

		/** 添加执行 **/
		BaseApplication.getRequestQueuemanager().add(request);

	}

	/**
	 * (查询历史菜单)
	 * 
	 * @param url
	 * @param params
	 */

	public void requestHistoryMenuList(Map<String, Object> params) {
		GsonRequest<ContextBean> request = new GsonRequest<ContextBean>(
				Request.Method.GET, returnGetUrl(Constants.URL
						+ Constants.ServiceInterFace.QueryHistoryMenuListUrl,
						params), ContextBean.class,
				new Listener<ContextBean>() {
					@Override
					public void onResponse(ContextBean arg0) {
						mResponseInterface.successResponse(arg0,
								Constants.RequestTag.QueryHistoryMenuLisTag);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						mResponseInterface.errorResonse(Constants.NetError,
								Constants.RequestTag.QueryHistoryMenuLisTag);
					}
				});
		/** 添加标签 **/
		request.setTag(Constants.RequestTag.QueryHistoryMenuLisTag);

		/** 添加执行 **/
		BaseApplication.getRequestQueuemanager().add(request);

	}

	// --------------------以上是请求---------------------------------------

	/**
	 * 拼接请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	private String returnGetUrl(String url, Map<String, Object> params) {

		// 设置Get请求方式
		StringBuilder sb = new StringBuilder();
		sb.append(url);
		// sb.append("?JsonString=");
		sb.append(StringTools.correctEncode(returnGetPara(params)));
		return sb.toString();
	}

	/**
	 * 拼接请求参数
	 * 
	 * @param params
	 * @return
	 */
	private String returnGetPara(Map<String, Object> params) {
		String utfStr = "";
		try {
			utfStr = URLEncoder.encode(gson.toJson(params), Constants.CodeType);
		} catch (Exception e) {
			utfStr = "";
		}
		return utfStr;
	}

	public void setResponseListener(ResponseInterface mResponseInterface) {
		this.mResponseInterface = mResponseInterface;
	}

	/**
	 * 处理接口
	 * 
	 * @author zhang_q
	 * 
	 */
	public interface ResponseInterface {

		public <T> void successResponse(T parsedGSON, int tag);

		public void errorResonse(String retmeg, int tag);
	}
}
