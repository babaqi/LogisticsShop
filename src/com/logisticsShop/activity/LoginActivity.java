package com.logisticsShop.activity;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.logisticsShop.R;
import com.logisticsShop.base.BaseActivity;
import com.logisticsShop.commen.Constants;
import com.logisticsShop.http.RequestManager;
import com.logisticsShop.http.RequestManager.ResponseInterface;
import com.logisticsShop.model.ShopModel;
import com.logisticsShop.serviceBean.ShopBean;
import com.logisticsShop.utils.SharePreferenceUtils;
import com.logisticsShop.activity.LoginActivity;

public class LoginActivity extends BaseActivity implements ResponseInterface {

	private EditText ed_username;
	private EditText ed_pwd;
	private Button btn_Login, btn_Register;

	// private String mUserName, mPwd;
	SharePreferenceUtils utils;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		/** 初始化 **/
		FindView();

		btn_Login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// if (ed_username.getText().toString().equals("")) {
				// Toast.makeText(LoginActivity.this, "请输入用户名！", 5000).show();
				// } else if (ed_pwd.getText().toString().equals("")) {
				// Toast.makeText(LoginActivity.this, "请输入密码！", 5000).show();
				// } else {

				Request();
				// }
			}
		});
		btn_Register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(LoginActivity.this,
						RegisterActivity.class);
				startActivity(intent);
				finish();

			}
		});

	}

	@Override
	public <T> void successResponse(T parsedGSON, int tag) {
		// TODO Auto-generated method stub
		utils = new SharePreferenceUtils(LoginActivity.this);
		if (tag == Constants.RequestTag.ShopLoginTag) {
			if (parsedGSON != null) {

				ShopBean bean = new ShopBean();

				bean = (ShopBean) parsedGSON;

				if (bean.getRetcode().equals("0")) {
					// utils.put(Constants.LoginInfo.Pwd, mPwd);
					// utils.put(Constants.LoginInfo.UserName, mUserName);
					utils.put(Constants.LoginInfo.IsLogin, "1");
					utils.put(Constants.LoginInfo.ShopId, bean.getShop()
							.getShop_id());
					utils.put(Constants.LoginInfo.ShopName, bean.getShop()
							.getShop_name());
					utils.put(Constants.LoginInfo.ShopAddress, bean.getShop()
							.getShop_address());
					utils.put(Constants.LoginInfo.ShopTel, bean.getShop()
							.getShop_tel());
					utils.put("shopAreaId", bean.getShop().getShop_area_id());
					utils.put("shopCityId", bean.getShop().getShop_city_id());

					Intent intent = new Intent(LoginActivity.this,
							FragmentNavigation.class);
					startActivity(intent);
					finish();

				} else {

					Toast.makeText(LoginActivity.this, "用户名或者是密码错误！", 5000)
							.show();
				}

			}

		}

	}

	@Override
	public void errorResonse(String retmeg, int tag) {
		// TODO Auto-generated method stub
		if (this != null) {
			Toast.makeText(LoginActivity.this, "登录失败！", 5000).show();
		}

	}

	public void FindView() {
		ed_username = (EditText) findViewById(R.id.LoginUserName);
		ed_pwd = (EditText) findViewById(R.id.LoginPwd);

		// mUserName = ed_username.getText().toString();
		// mPwd = ed_pwd.getText().toString();

		btn_Login = (Button) findViewById(R.id.LoginBtn);
		btn_Register = (Button) findViewById(R.id.RegisterBtn);

	}

	public void Request() {

		Map<String, Object> map = new HashMap<String, Object>();

		ShopModel model = new ShopModel();
		// model.setShop_name("真好吃");
		// model.setShop_pwd("zhenhaochi123");
		model.setShop_name(ed_username.getText().toString());
		model.setShop_pwd(ed_pwd.getText().toString());

		map.put("shop", model);

		RequestManager manager = new RequestManager();
		manager.setResponseListener(LoginActivity.this);
		manager.requestShopLogin(map);

	}

}
