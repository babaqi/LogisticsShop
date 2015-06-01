package com.logisticsShop.activity;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.logisticsShop.R;
import com.logisticsShop.base.BaseActivity;
import com.logisticsShop.http.RequestManager;
import com.logisticsShop.http.RequestManager.ResponseInterface;

public class ContextStateUpdateActivity extends BaseActivity implements
		ResponseInterface {

	private Button btn_update;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update);

		/** 初始化控件 **/
		FindView();

		/** 网络请求 **/

		btn_update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				/** 查询请求 **/

				Request();

			}
		});
	}

	public void FindView() {

		btn_update = (Button) findViewById(R.id.btn_Update);

	}

	public void Request() {

		Map<String, Object> map = new HashMap<String, Object>();
		RequestManager manager = new RequestManager();

		map.put("context_id", "111");
		map.put("state", "1");

		manager.setResponseListener(ContextStateUpdateActivity.this);

		manager.requestUpdate(map);

	}

	@Override
	public <T> void successResponse(T parsedGSON, int tag) {
		// TODO Auto-generated method stub
		Toast.makeText(ContextStateUpdateActivity.this, "哈嘿，成功了", 5000).show();
	}

	@Override
	public void errorResonse(String retmeg, int tag) {
		// TODO Auto-generated method stub
		Toast.makeText(ContextStateUpdateActivity.this, "艾玛，失败了", 5000).show();
	}

}
