package com.logisticsShop.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.logisticsShop.R;
import com.logisticsShop.base.BaseActivity;
import com.logisticsShop.http.RequestManager;
import com.logisticsShop.http.RequestManager.ResponseInterface;
import com.logisticsShop.model.ShopModel;

public class RegisterActivity extends BaseActivity implements ResponseInterface {

	/** 注册页面变量 **/
	private String mShopname, mShopAddress, mShopTel, mPwd;

	private EditText ed_ShopName;
	private EditText ed_ShopAddress;
	private EditText ed_ShopTel;
	private EditText ed_Pwd;

	private Button btn_RegisterOK, btn_RegisterCancel;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		/** 初始化 **/
		FindView();

		// 确定按钮

		btn_RegisterOK.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Request();
				Intent intent = new Intent(RegisterActivity.this,
						LoginActivity.class);
				startActivity(intent);
				finish();

			}
		});
		btn_RegisterCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(RegisterActivity.this,
						LoginActivity.class);
				startActivity(intent);
				finish();

			}
		});
	}

	@Override
	public <T> void successResponse(T parsedGSON, int tag) {
		// TODO Auto-generated method stub
		if (this != null) {
			Toast.makeText(RegisterActivity.this, "注册成功！", 5000).show();
		}

	}

	@Override
	public void errorResonse(String retmeg, int tag) {
		// TODO Auto-generated method stub
		if (this != null) {
			Toast.makeText(RegisterActivity.this, "注册出现问题，请重新注册", 5000).show();
		}

	}

	public void FindView() {

		ed_ShopName = (EditText) findViewById(R.id.ShopNames);
		ed_ShopAddress = (EditText) findViewById(R.id.ShopAddress);
		ed_ShopTel = (EditText) findViewById(R.id.ShopTel);
		ed_Pwd = (EditText) findViewById(R.id.Pwd);
		btn_RegisterOK = (Button) findViewById(R.id.Register);
		btn_RegisterCancel = (Button) findViewById(R.id.CancelRegister);

	}

	public void Request() {

		mShopname = ed_ShopName.getText().toString();
		mShopAddress = ed_ShopAddress.getText().toString();
		mShopTel = ed_ShopTel.getText().toString();
		mPwd = ed_Pwd.getText().toString();

		Map<String, Object> map = new HashMap<String, Object>();

		ShopModel model = new ShopModel();
		model.setShop_address(mShopAddress);
		model.setShop_name(mShopname);
		model.setShop_tel(mShopTel);
		model.setShop_pwd(mPwd);

		// model.setShop_address("银河大厦金得利");
		// model.setShop_name("金得利");
		// model.setShop_tel("18585858585");
		// model.setShop_pwd("1");

		map.put("shop", model);
		RequestManager manager = new RequestManager();
		manager.setResponseListener(RegisterActivity.this);
		manager.requestShopRegister(map);

	}
}
