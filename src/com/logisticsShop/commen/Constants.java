package com.logisticsShop.commen;

public class Constants {

	 public static String URL = "http://124.128.33.108:8883/LogisticsServer/";
	// public static String URL = "http://172.16.231.41:8080/LogisticsServer/";
//	public static String URL = "http://172.16.231.196:8883/LogisticsServer/";

	/** bug路径 **/
	public static String bugPath = "";

	/** 数据格式格式 **/
	public static String CodeType = "utf-8";
	/** 网络请求 **/

	public static final String NetError = "网络请求出错,请重试";

	/**
	 * 
	 * @author muz
	 * 
	 * @description 接口定义
	 */
	public static class ServiceInterFace {
		public static String UserLoginUrl = "user/register?user=";

		public static String SubmitContextUrl = "context/submitInfo?jsoncontext=";

		public static String QueryContextUrl = "context/queryInfo?jsoncontext=";

		public static String UpdateContextUrl = "context/updateInfo?jsoncontext=";

		public static String ShopRegisterUrl = "shop/insertShop?jsonShop=";

		public static String ShopLoginUrl = "shop/queryShop?jsonShop=";
		public static String AreaUrl = "area/MobileQueryAreaByCityId?JsonArea=";
		public static String AddressUrl = "address/MobileQueryAddress?jsonAddress=";

		public static String QueryHistoryMenuListUrl = "context/queryInfobydata?jsoncontext=";// 历史订单查询

	}

	/**
	 * 
	 * @author muz
	 * 
	 * @description Request的Tag管理
	 */
	public static class RequestTag {

		public static final int UserLoginTag = 1001;

		public static final int SubmitContextTag = 1002;

		public static final int QueryContextTag = 1003;

		public static final int UpdateContextTag = 1004;

		public static final int ShopRegisterTag = 1005;

		public static final int ShopLoginTag = 1006;
		public static final int AreaTag = 1007;
		public static final int AddressTag = 1008;

		public static final int QueryHistoryMenuLisTag = 1009;

	}

	public static class LoginInfo {
		public static String SharedPrefrenceName = "spotprefrence";

		public static String ShopName = "shopname";

		public static String ShopAddress = "shopaddress";

		public static String ShopTel = "shoptel";

		public static String ShopId = "shopid";

		public static String ContextId = "contextid";

		public static String AreaId = "areaid";

		public static String AddressId = "areaid";

		public static String Pwd = "pwd";

		public static String UserName = "username";

		public static String IsLogin = "0";

	}
}
