package com.logisticsShop.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.logisticsShop.R;
import com.logisticsShop.commen.Constants;
import com.logisticsShop.http.RequestManager;
import com.logisticsShop.http.RequestManager.ResponseInterface;
import com.logisticsShop.model.ContextModel;
import com.logisticsShop.serviceBean.ContextBean;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ContextQueryAdapter extends BaseAdapter implements
		ResponseInterface {

	// private LayoutInflater mInflater;
	private List<ContextModel> queryList;
	private Context mContext;

	private String mStatus;// 状态

	// private String mCurrentPage;

	private AlertDialog mDlg;

	private RequestManager requestManager;

	private Map<String, Object> map;// 请求map

	private String mContextId;
	private int removeNum;

	private ProgressDialog mProgress = null;

	public ContextQueryAdapter(Context context, List<ContextModel> list,
			String status) {

		// this.mInflater = LayoutInflater.from(mContext);
		this.queryList = list;
		this.mContext = context;
		this.mStatus = status;
		// this.mCurrentPage = currentpage;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return queryList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_waitlistview, null);

			holder.tv_shop = (TextView) convertView.findViewById(R.id.shopName);
			holder.tv_address = (TextView) convertView
					.findViewById(R.id.userAddress);
			holder.tv_phone = (TextView) convertView.findViewById(R.id.userTel);
			holder.tv_price = (TextView) convertView.findViewById(R.id.price);
			holder.tv_shopTel = (TextView) convertView
					.findViewById(R.id.item_shopTel);
			holder.tv_amountofmoney = (TextView) convertView
					.findViewById(R.id.amountMoney);
			holder.tv_infomation = (TextView) convertView
					.findViewById(R.id.context_infomation);
			holder.tv_type = (TextView) convertView.findViewById(R.id.type);
			holder.tv_Diliveryman = (TextView) convertView
					.findViewById(R.id.Diliveryman);
			holder.btn_cancel = (LinearLayout) convertView
					.findViewById(R.id.cancel_btn);
			holder.serialNumber = (TextView) convertView
					.findViewById(R.id.serialNumber);
			holder.time = (TextView) convertView.findViewById(R.id.time);
			convertView.setTag(holder);

		} else {

			// 取出ViewHolder对象
			holder = (ViewHolder) convertView.getTag();
		}
		if (mStatus != null) {
			if (mStatus.equals("0")) {

				holder.btn_cancel.setVisibility(View.VISIBLE);
				// 撤销订单按钮监听
				holder.btn_cancel.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						mContextId = String.valueOf(queryList.get(position)
								.getContext_id());
						removeNum = position;
						// 弹出是否确认撤销的提示
						createDialog();
					}
				});

			}

		}
		holder.tv_Diliveryman.setText(queryList.get(position)
				.getContext_user_name());
		holder.serialNumber.setText(position + 1 + "号");
		holder.tv_address.setText(queryList.get(position).getContext_address());
		holder.tv_amountofmoney.setText(queryList.get(position)
				.getContext_amountofmoney());
		holder.tv_infomation.setText("内容："
				+ queryList.get(position).getContext_infomation());
		holder.tv_phone.setText(queryList.get(position).getContext_phone());
		holder.tv_phone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String tell = queryList.get(position).getContext_phone()
						.toString();
				// Intent intent = new Intent(Intent.ACTION_CALL,
				// Uri.parse("tel:" + tell));

				Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
						+ tell));
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

				mContext.startActivity(intent);
			}
		});
		holder.tv_price.setText(queryList.get(position).getContext_price());
		holder.tv_shopTel.setText(queryList.get(position).getContext_shop_tel()
				.toString());
		holder.tv_shopTel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String tell = queryList.get(position).getContext_shop_tel()
						.toString();
				// Intent intent = new Intent(Intent.ACTION_CALL,
				// Uri.parse("tel:" + tell));

				Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
						+ tell));
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

				mContext.startActivity(intent);
			}
		});
		holder.tv_shop.setText(queryList.get(position).getContext_shop_name());
		holder.time.setText(queryList.get(position).getContext_timer());
		if (queryList.get(position).getContext_type() == 0) {
			holder.tv_type.setText("等待中");
		}
		if (queryList.get(position).getContext_type() == 1) {
			holder.tv_type.setText("已接受");
		}
		if (queryList.get(position).getContext_type() == 2) {
			holder.tv_type.setText("配送中");
		}
		if (queryList.get(position).getContext_type() == 3) {
			holder.tv_type.setText("历史");
		}

		return convertView;
	}

	class ViewHolder {

		private TextView tv_shop;
		private TextView tv_address;
		private TextView tv_phone;
		private TextView tv_price;
		private TextView tv_amountofmoney;
		private TextView tv_infomation;
		private TextView tv_type;
		private TextView tv_Diliveryman;
		private TextView tv_shopTel, serialNumber, time;
		private LinearLayout btn_cancel;

	}

	private void createDialog() {
		if (mDlg == null) {
			mDlg = new AlertDialog.Builder(mContext).create();
		}
		mDlg.show();
		// 点击对话框外部取消对话框显示
		mDlg.setCanceledOnTouchOutside(true);
		Window window = mDlg.getWindow();
		// 设置窗口的内容页面,dialog_uploading.xml文件中定义view内容
		window.setContentView(R.layout.dialog_unupload);
		// 确定按钮
		Button ok = (Button) window.findViewById(R.id.dlg_btn_ok);
		Button no = (Button) window.findViewById(R.id.dlg_btn_no);

		// 确认撤销监听
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				showProgress();
				Map<String, Object> map = new HashMap<String, Object>();
				RequestManager manager = new RequestManager();

				map.put("context_id", mContextId);
				map.put("state", "5");

				manager.setResponseListener(ContextQueryAdapter.this);

				manager.requestUpdate(map);

				mDlg.dismiss();

			}
		});
		// 取消撤销监听
		no.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mDlg.dismiss();
			}
		});
	}

	@Override
	public <T> void successResponse(T parsedGSON, int tag) {
		// TODO Auto-generated method stub

		stopProgress();

		if (tag == Constants.RequestTag.UpdateContextTag) {
			if (parsedGSON != null) {

				ContextBean bean = new ContextBean();

				bean = (ContextBean) parsedGSON;

				if (bean.getRetcode().equals("0")) {

					Toast.makeText(mContext, "撤销请求成功！", 5000).show();

					queryList.remove(removeNum);
					this.notifyDataSetChanged();

				} else {
					Toast.makeText(mContext, "撤销请求失败！", 5000).show();

				}
			}

		}

	}

	@Override
	public void errorResonse(String retmeg, int tag) {
		// TODO Auto-generated method stub
		stopProgress();
		Toast.makeText(mContext, "撤销失败！", 5000).show();
	}

	/**
	 * progressDialog的展示
	 */
	public void showProgress() {
		if (mContext != null && !((Activity) mContext).isFinishing()) {
			mProgress = new ProgressDialog(mContext);
			mProgress.setMessage("正在努力加载中...");
			mProgress.setCancelable(true);
			mProgress.show();
		}
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
