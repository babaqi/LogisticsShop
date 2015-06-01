package com.logisticsShop.activity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.example.logisticsShop.R;
import com.example.logisticsShop.R.color;
import com.logisticsShop.base.BaseActivity;
import com.logisticsShop.base.BaseApplication;
import com.logisticsShop.commen.Constants;
import com.logisticsShop.db.DatabaseManager;
import com.logisticsShop.jpushdemo.ExampleUtil;
import com.logisticsShop.model.CommonAddressModel;
import com.logisticsShop.utils.SharePreferenceUtils;
import com.logisticsShop.utils.Utils;

public class FragmentNavigation extends BaseActivity {
	protected static final String TAG = FragmentNavigation.class
			.getSimpleName();
	private static FragmentManager fMgr;

	private static Boolean isExit = false;
	public static boolean isForeground = false;
	SharePreferenceUtils utils;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		registerMessageReceiver();
		/** 解决actionbar中菜单栏中flowover不显示问题 **/
		try {
			ViewConfiguration mconfig = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(mconfig, false);
			}
		} catch (Exception ex) {
		}

		// 获取FragmentManager实例
		fMgr = getSupportFragmentManager();
		utils = new SharePreferenceUtils(this);
		setAlias(utils.getInt(Constants.LoginInfo.ShopId, 0));
		initFragment();
		dealBottomButtonsClickEvent();

	}

	/**
	 * 初始化首个Fragment
	 */
	private void initFragment() {
		FragmentTransaction ft = fMgr.beginTransaction();
		SelectAddressFragment selectAddressFragment = new SelectAddressFragment();
		ft.add(R.id.fragmentRoot, selectAddressFragment, "weiXinFragment");
		ft.addToBackStack("weiXinFragment");
		ft.commit();
		// findViewById(R.id.rbWeiXin).setBackgroundColor(R.color.deeporange);
		findViewById(R.id.rbWeiXin).setBackgroundResource(R.color.orange);
		findViewById(R.id.rbAddress).setBackgroundResource(R.color.orange);
		findViewById(R.id.rbAccept).setBackgroundResource(R.color.orange);
		findViewById(R.id.rbFind).setBackgroundResource(R.color.orange);
		findViewById(R.id.rbMe).setBackgroundResource(R.color.orange);
	}

	/**
	 * 处理底部点击事件
	 */
	private void dealBottomButtonsClickEvent() {
		findViewById(R.id.rbWeiXin).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popAllFragmentsExceptTheBottomOne();
				FragmentTransaction ft = fMgr.beginTransaction();
				ft.hide(fMgr.findFragmentByTag("weiXinFragment"));
				SelectAddressFragment selectAddressFragment = new SelectAddressFragment();
				ft.add(R.id.fragmentRoot, selectAddressFragment, "MeFragment");
				ft.addToBackStack("MeFragment");
				ft.commit();

				findViewById(R.id.rbWeiXin).setBackgroundResource(
						R.color.deeporange);
				findViewById(R.id.rbAddress).setBackgroundResource(
						R.color.orange);
				findViewById(R.id.rbAccept).setBackgroundResource(
						R.color.orange);
				findViewById(R.id.rbFind).setBackgroundResource(R.color.orange);
				findViewById(R.id.rbMe).setBackgroundResource(R.color.orange);

			}
		});
		findViewById(R.id.rbAddress).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popAllFragmentsExceptTheBottomOne();
				FragmentTransaction ft = fMgr.beginTransaction();
				ft.hide(fMgr.findFragmentByTag("weiXinFragment"));
				WaitFragment showListView = new WaitFragment();
				Bundle bundle = new Bundle();
				bundle.putString("status", "0");
				showListView.setArguments(bundle);
				ft.add(R.id.fragmentRoot, showListView, "MeFragment");
				ft.addToBackStack("MeFragment");
				ft.commit();
				findViewById(R.id.rbWeiXin).setBackgroundResource(
						R.color.orange);
				findViewById(R.id.rbAddress).setBackgroundResource(
						R.color.deeporange);
				findViewById(R.id.rbAccept).setBackgroundResource(
						R.color.orange);
				findViewById(R.id.rbFind).setBackgroundResource(R.color.orange);
				findViewById(R.id.rbMe).setBackgroundResource(R.color.orange);
			}
		});
		findViewById(R.id.rbAccept).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popAllFragmentsExceptTheBottomOne();
				FragmentTransaction ft = fMgr.beginTransaction();
				ft.hide(fMgr.findFragmentByTag("weiXinFragment"));
				WaitFragment showListView = new WaitFragment();
				Bundle bundle = new Bundle();
				bundle.putString("status", "1");
				showListView.setArguments(bundle);
				ft.add(R.id.fragmentRoot, showListView, "AddressFragment");
				ft.addToBackStack("AddressFragment");
				ft.commit();
				findViewById(R.id.rbWeiXin).setBackgroundResource(
						R.color.orange);
				findViewById(R.id.rbAddress).setBackgroundResource(
						R.color.orange);
				findViewById(R.id.rbAccept).setBackgroundResource(
						R.color.deeporange);
				findViewById(R.id.rbFind).setBackgroundResource(R.color.orange);
				findViewById(R.id.rbMe).setBackgroundResource(R.color.orange);
			}
		});
		findViewById(R.id.rbFind).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popAllFragmentsExceptTheBottomOne();
				FragmentTransaction ft = fMgr.beginTransaction();
				ft.hide(fMgr.findFragmentByTag("weiXinFragment"));
				WaitFragment showListView = new WaitFragment();
				Bundle bundle = new Bundle();
				bundle.putString("status", "2");
				showListView.setArguments(bundle);
				ft.add(R.id.fragmentRoot, showListView, "AddressFragment");
				ft.addToBackStack("FindFragment");
				ft.commit();
				findViewById(R.id.rbWeiXin).setBackgroundResource(
						R.color.orange);
				findViewById(R.id.rbAddress).setBackgroundResource(
						R.color.orange);
				findViewById(R.id.rbAccept).setBackgroundResource(
						R.color.orange);
				findViewById(R.id.rbFind).setBackgroundResource(
						R.color.deeporange);
				findViewById(R.id.rbMe).setBackgroundResource(R.color.orange);
			}
		});
		findViewById(R.id.rbMe).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popAllFragmentsExceptTheBottomOne();
				FragmentTransaction ft = fMgr.beginTransaction();
				ft.hide(fMgr.findFragmentByTag("weiXinFragment"));
				WaitFragment setFragment = new WaitFragment();
				Bundle bundle = new Bundle();
				bundle.putString("status", "3");
				setFragment.setArguments(bundle);
				ft.add(R.id.fragmentRoot, setFragment, "MeFragment");
				ft.addToBackStack("MeFragment");
				ft.commit();
				findViewById(R.id.rbWeiXin).setBackgroundResource(
						R.color.orange);
				findViewById(R.id.rbAddress).setBackgroundResource(
						R.color.orange);
				findViewById(R.id.rbAccept).setBackgroundResource(
						R.color.orange);
				findViewById(R.id.rbFind).setBackgroundResource(R.color.orange);
				findViewById(R.id.rbMe).setBackgroundResource(
						R.color.deeporange);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.image, menu);
		// 动态设置标题栏中用户名
		menu.findItem(R.id.TitleUserName).setTitle(Utils.getShopName());
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.TitleUserName:// 标题栏显示用户名

			break;
		case R.id.TitleChangeUser:// 切换用户
			SharePreferenceUtils utils = new SharePreferenceUtils(this);
			utils.clear();
			utils.commit();
			Intent intent = new Intent(FragmentNavigation.this,
					LoginActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.TitleExit:// 退出程序

			BaseApplication.getInstance().mScreenManager.finishAllActivity();
			finish();
			break;
		// case R.id.CommonAddress:// 添加常用地址
		//
		// ShowPopupWindow show = new ShowPopupWindow(FragmentNavigation.this,
		// FragmentNavigation.this.getWindow());
		// show.createPop();
		//
		// break;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * 从back stack弹出所有的fragment，保留首页的那个
	 */
	public static void popAllFragmentsExceptTheBottomOne() {
		for (int i = 0, count = fMgr.getBackStackEntryCount() - 1; i < count; i++) {
			fMgr.popBackStack();
		}
	}

	// 点击返回按钮
	@Override
	public void onBackPressed() {
		if (fMgr.findFragmentByTag("weiXinFragment") != null
				&& fMgr.findFragmentByTag("weiXinFragment").isVisible()) {
			FragmentNavigation.this.finish();
		} else {
			super.onBackPressed();
		}
	}

	// 双击退出
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			exitByDoubleClick();
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

	private void exitByDoubleClick() {
		Timer tExit;
		if (isExit == false) {
			isExit = true; // 准备退出
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit = false; // 取消退出
				}
			}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

		} else {
			BaseApplication.getInstance().mScreenManager.finishAllActivity();
		}
	}

	private void setAlias(Integer alias) {

		String aliasa = alias.toString().trim();
		// 调用JPush API设置Alias
		mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, aliasa));
	}

	/**
	 * 设置通知提示方式 - 基础属性
	 */
	private void setStyleBasic() {
		BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(
				FragmentNavigation.this);
		builder.statusBarDrawable = R.drawable.ic_launcher;
		builder.notificationFlags = Notification.FLAG_AUTO_CANCEL; // 设置为点击后自动消失
		builder.notificationDefaults = Notification.DEFAULT_SOUND; // 设置为铃声（
																	// Notification.DEFAULT_SOUND）或者震动（
																	// Notification.DEFAULT_VIBRATE）
		JPushInterface.setPushNotificationBuilder(1, builder);
	}

	private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

		@Override
		public void gotResult(int code, String alias, Set<String> tags) {
			String logs;
			switch (code) {
			case 0:
				logs = "Set tag and alias success";
				Log.i(TAG, logs);
				break;

			case 6002:
				logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
				Log.i(TAG, logs);
				if (ExampleUtil.isConnected(getApplicationContext())) {
					mHandler.sendMessageDelayed(
							mHandler.obtainMessage(MSG_SET_ALIAS, alias),
							1000 * 60);
				} else {
					Log.i(TAG, "No network");
				}
				break;

			default:
				logs = "Failed with errorCode = " + code;
				Log.e(TAG, logs);
			}

			ExampleUtil.showToast(logs, getApplicationContext());
		}

	};
	private final TagAliasCallback mTagsCallback = new TagAliasCallback() {

		@Override
		public void gotResult(int code, String alias, Set<String> tags) {
			String logs;
			switch (code) {
			case 0:
				logs = "Set tag and alias success";
				Log.i(TAG, logs);
				break;

			case 6002:
				logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
				Log.i(TAG, logs);
				if (ExampleUtil.isConnected(getApplicationContext())) {
					mHandler.sendMessageDelayed(
							mHandler.obtainMessage(MSG_SET_TAGS, tags),
							1000 * 60);
				} else {
					Log.i(TAG, "No network");
				}
				break;

			default:
				logs = "Failed with errorCode = " + code;
				Log.e(TAG, logs);
			}

			ExampleUtil.showToast(logs, getApplicationContext());
		}

	};
	private static final int MSG_SET_ALIAS = 1001;
	private static final int MSG_SET_TAGS = 1002;

	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case MSG_SET_ALIAS:
				Log.d(TAG, "Set alias in handler.");
				JPushInterface.setAliasAndTags(getApplicationContext(),
						(String) msg.obj, null, mAliasCallback);
				break;

			case MSG_SET_TAGS:
				Log.d(TAG, "Set tags in handler.");
				JPushInterface.setAliasAndTags(getApplicationContext(), null,
						(Set<String>) msg.obj, mTagsCallback);
				break;

			default:
				Log.i(TAG, "Unhandled msg - " + msg.what);
			}
		}
	};

	// for receive customer msg from jpush server
	private MessageReceiver mMessageReceiver;
	public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
	public static final String KEY_TITLE = "title";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_EXTRAS = "extras";

	public void registerMessageReceiver() {
		mMessageReceiver = new MessageReceiver();
		IntentFilter filter = new IntentFilter();
		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		filter.addAction(MESSAGE_RECEIVED_ACTION);
		registerReceiver(mMessageReceiver, filter);
	}

	public class MessageReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
				String messge = intent.getStringExtra(KEY_MESSAGE);
				String extras = intent.getStringExtra(KEY_EXTRAS);
				StringBuilder showMsg = new StringBuilder();
				showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
				if (!ExampleUtil.isEmpty(extras)) {
					showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
				}
				setCostomMsg(showMsg.toString());
			}
		}
	}

	private void setCostomMsg(String msg) {
	}
}
