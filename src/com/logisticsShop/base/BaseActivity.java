package com.logisticsShop.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;


/**
 * 基类Activity
 * 
 * @author mz
 * 
 */
public abstract class BaseActivity extends FragmentActivity {

    /**
     * Application
     */
    private BaseApplication mAppManager;

    public ProgressDialog mProgress = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        /** 记录Activity **/
        mAppManager = BaseApplication.getInstance();
        mAppManager.mScreenManager.pushActivity(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * 
     * @Title: getViewById
     * @Description: 根据控件id查找控件
     * @param @param id 控件id
     * @param @return 设定文件
     * @return T 返回类型
     * @throws
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getViewById(int id) {
        return (T) this.findViewById(id);
    }

    /**
     * 
     * @Title: findView
     * @Description: 查找控件
     * @param 设定文件
     * @return void 返回类型
     * @throws
     */
    public void initView(){};

    /**
     * 
     * @Title: setListener
     * @Description: 设置事件监听
     * @param 设定文件
     * @return void 返回类型
     * @throws
     */
    public void initListener() {
	}

    /**
     * 
     * @Title: processLogic
     * @Description: 处理业务逻辑
     * @param 设定文件
     * @return void 返回类型
     * @throws
     */
    public void logic() {};

    public void onDestory() {
        super.onDestroy();
    }

    /**
     * progressDialog的展示
     */
    public void showProgress() {
        mProgress = new ProgressDialog(this);
        mProgress.setMessage("正在努力加载中...");
        mProgress.setCancelable(true);
        mProgress.show();
    }

    /**
     * progressDialog的消失
     */
    public void stopProgress() {
        if (mProgress != null) {
            mProgress.dismiss();
            mProgress = null;
        }
    }
}
