package com.logisticsShop.activity;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.logisticsShop.R;
import com.logisticsShop.base.BaseActivity;
import com.logisticsShop.commen.Constants;
import com.logisticsShop.http.RequestManager;
import com.logisticsShop.http.RequestManager.ResponseInterface;
import com.logisticsShop.model.ContextModel;
import com.logisticsShop.utils.SharePreferenceUtils;
import com.logisticsShop.utils.Utils;

public class PublishActivity extends BaseActivity implements ResponseInterface {
	/** 提交页面的输入框 **/
	private EditText ed_address;
	private EditText ed_phone;
	private EditText ed_price;
	private EditText ed_amountofmoney;
	private EditText ed_infomation;
	private TextView selectAddressText;
	// private ListView selectAddressListView;

	// private Button select_address;
	private LinearLayout mBtnSubmit;
	SharePreferenceUtils utils;
	Intent intent;
	/** 请求参数 **/

	private String mShop, mAddress, mPhone, mPrice, mAmountofmoney,
			mInfomation;
	private int mType;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submit);
		utils = new SharePreferenceUtils(this);
		intent = getIntent();
		// 初始化
		FindView();

		// String addressname = intent.putExtra("address", "").toString();
		// int areaid = Integer.parseInt(intent.putExtra("areaid",
		// "").toString());

		mBtnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// 提交请求
				Request();

			}
		});
		// select_address.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// // TODO Auto-generated method stub
		// ShowPopupWindow show = new ShowPopupWindow(this,
		// PublishFragment.this.getWindow());
		// show.setmAddressGetHandler(this.);
		// show.createPop();
		//
		// }
		// });
	}

	public void FindView() {

		// selectAddressListView= (ListView)
		// view.findViewById(R.id.selectAddress);
		// ed_shop = (EditText) findViewById(R.id.ed_shop);

		ed_address = (EditText) findViewById(R.id.ed_address);

		ed_phone = (EditText) findViewById(R.id.ed_phone);

		ed_price = (EditText) findViewById(R.id.ed_price);
		ed_price.addTextChangedListener(mTextWatcher); 
		
		
		
		ed_amountofmoney = (EditText) findViewById(R.id.ed_amountofmoney);

		ed_infomation = (EditText) findViewById(R.id.ed_infomation);

		// ed_type = (EditText) view.findViewById(R.id.ed_type);

		mBtnSubmit = (LinearLayout) findViewById(R.id.btn_Submit);

		// select_address = (Button) findViewById(R.id.select_address);
		selectAddressText = (TextView) findViewById(R.id.selectAddressText);
		selectAddressText.setText(intent.getStringExtra("address"));
	}

	TextWatcher mTextWatcher = new TextWatcher() {

		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1,
				int arg2, int arg3) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			ed_amountofmoney.setText(ed_price.getText());
			
		}  
	};
	
	public void Request() {

		mAddress = selectAddressText.getText().toString()
				+ ed_address.getText().toString();
		mPhone = ed_phone.getText().toString();
		mAmountofmoney = ed_amountofmoney.getText().toString();
		mInfomation = ed_infomation.getText().toString();
		// mType = Integer.parseInt(ed_type.getText().toString());
		mPrice = ed_price.getText().toString();

		if (mAddress.equals("") || mPhone.equals("")
				|| mAmountofmoney.equals("") || mInfomation.equals("")
				|| mPrice.equals("")) {
			Toast.makeText(PublishActivity.this, "请检查信息是否完整", 3000).show();
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			ContextModel contextModel = new ContextModel();
			contextModel.setContext_address(mAddress);
			contextModel.setContext_amountofmoney(mAmountofmoney);
			contextModel.setContext_areaid(Integer.parseInt(intent
					.getStringExtra("areaid")));
			contextModel.setContext_cityid(utils.getInt("shopCityId", 0));
			contextModel.setContext_infomation(mInfomation);
			contextModel.setContext_phone(mPhone);
			contextModel.setContext_price(mPrice);
			contextModel.setContext_shop_address(utils.getString(
					Constants.LoginInfo.ShopAddress, ""));
			contextModel.setContext_shop_id(String.valueOf(utils.getInt(
					Constants.LoginInfo.ShopId, 0)));
			contextModel.setContext_shop_name(utils.getString(
					Constants.LoginInfo.ShopName, ""));
			contextModel.setContext_shop_tel(utils.getString(
					Constants.LoginInfo.ShopTel, ""));
			contextModel.setContext_timer(Utils.nowTime());
			contextModel.setContext_type(0);
			// Gson gson = new Gson();
			// gson.toJson(contextModel);
			map.put("context", contextModel);

			RequestManager manager = new RequestManager();
			manager.setResponseListener(PublishActivity.this);

			manager.requestSubmit(map);
		}
	}

	@Override
	public <T> void successResponse(T parsedGSON, int tag) {
		// TODO Auto-generated method stub
		PublishActivity.this.finish();
		Toast.makeText(this, "发布需求成功", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void errorResonse(String retmeg, int tag) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "发布需求失败！", Toast.LENGTH_SHORT).show();
	}

//	@Override
//	public void onPopAddressGet(String address) {
//		// TODO Auto-generated method stub
//		ed_address.setText(address);
//	}

}
