package com.logisticsShop.activity;

import com.example.logisticsShop.R;
import com.logisticsShop.commen.Constants;
import com.logisticsShop.utils.SharePreferenceUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

/**
 * 应用程序启动类：显示欢迎界面并跳转到主界面
 */
public class StartActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final View view = View.inflate(this, R.layout.activity_start, null);
		setContentView(view);

		// 渐变展示启动屏
		AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
		aa.setDuration(2000);
		view.startAnimation(aa);
		aa.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationEnd(Animation arg0) {
				redirectTo();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationStart(Animation animation) {
			}

		});

	}

	/**
	 * 跳转到...
	 */
	private void redirectTo() {
		Intent intent = new Intent();
		SharePreferenceUtils utils = new SharePreferenceUtils(
				StartActivity.this);
		String isLogin = utils.getString(Constants.LoginInfo.IsLogin, "0");
		if ("0".equals(isLogin)) {
			intent.setClass(this, LoginActivity.class);
			startActivity(intent);
			finish();
		}
		if ("1".equals(isLogin)) {

			intent.setClass(this, FragmentNavigation.class);

			startActivity(intent);
			finish();
		}
	}
}