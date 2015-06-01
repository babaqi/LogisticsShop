package com.logisticsShop.base;

import java.io.File;

import cn.jpush.android.api.JPushInterface;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.logisticsShop.commen.Constants;
import com.logisticsShop.utils.ScreenManager;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

/**
 * 
 * @author muz
 * 
 */

public class BaseApplication extends Application {

	/** 单例 **/
	private static BaseApplication mBaseApplication;

	/** 管理所有的activity **/
	public ScreenManager mScreenManager = null;

	/** 单例 Context **/
	@SuppressWarnings("unused")
	private Context context;
	/** 全局定义的RequestQueue **/
	private static RequestQueue mRequestQueue;

	/**
	 * Vollery 管理器 单例
	 * 
	 * @return
	 */
	public static RequestQueue getRequestQueuemanager() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(mBaseApplication);
		}
		return mRequestQueue;
	}

	/**
	 * 单例的BaseApplication
	 * 
	 * @return
	 */
	public static BaseApplication getInstance() {
		if (mBaseApplication == null) {
			mBaseApplication = new BaseApplication();
		}
		return mBaseApplication;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mBaseApplication = this;
		context = this;
		mScreenManager = ScreenManager.getScreenManager();
		start();
		JPushInterface.init(this); // 初始化 JPush
		// // 全局异常捕获类
		// CrashHandler crashHandler = CrashHandler.getInstance();
		// crashHandler.init(this);
	}

	/**
	 * 初始化信息
	 */
	public void start() {

		// 文件路径设置
		String parentPath = null;

		// 存在SDCARD的时候，路径设置到SDCARD
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			parentPath = Environment.getExternalStorageDirectory().getPath()
					+ File.separatorChar + getPackageName();
			// 不存在SDCARD的时候
		} else {
			parentPath = Environment.getDataDirectory().getPath()
					+ File.separatorChar + "data" + File.separatorChar
					+ getPackageName();
		}

		Constants.bugPath = parentPath + "/bugfile/";

		// 创建目录
		new File(Constants.bugPath).mkdirs();

	}

}
