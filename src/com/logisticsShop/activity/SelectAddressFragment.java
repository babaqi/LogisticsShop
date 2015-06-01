package com.logisticsShop.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.logisticsShop.R;
import com.logisticsShop.commen.Constants;
import com.logisticsShop.http.RequestManager;
import com.logisticsShop.http.RequestManager.ResponseInterface;
import com.logisticsShop.model.Address;
import com.logisticsShop.model.Area;
import com.logisticsShop.serviceBean.AddressBean;
import com.logisticsShop.serviceBean.AreaBean;
import com.logisticsShop.utils.SharePreferenceUtils;

public class SelectAddressFragment extends Fragment implements
		ResponseInterface {

	private View view;
	private ListView selectAddressListView;
	private SelectAddressAdapter selectAddressAdapter;
	SharePreferenceUtils utils;
	private List<Area> areaList;
	private List<Address> addressList;
	private Stack<List<Area>> areastack;
	private Stack<List<Address>> addressstack;
	private static Boolean isExit = false;
	private Button backBtn;
	public ProgressDialog mProgress = null;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		view = inflater.inflate(R.layout.select_address, container, false);
		backBtn = (Button) view.findViewById(R.id.backBtn);
		utils = new SharePreferenceUtils(getActivity());
		areastack = new Stack<List<Area>>();
		addressstack = new Stack<List<Address>>();
		areaList = new ArrayList<Area>();
		addressList = new ArrayList<Address>();
		// 初始化
		FindView();
		Map<String, Object> map = new HashMap<String, Object>();
		Area area = new Area();
		area.setArea_cityid(utils.getInt("shopCityId", 0));
		map.put("area", area);
		RequestManager manager = new RequestManager();
		manager.setResponseListener(SelectAddressFragment.this);
		manager.requestArea(map);
		showProgress();
		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (areastack.empty() != true) {
					areaList = areastack.pop();
					areastack.clear();
					selectAddressAdapter = new SelectAddressAdapter(areaList,
							null);
					selectAddressListView.setAdapter(selectAddressAdapter);
					selectAddressAdapter.notifyDataSetChanged();
					stopProgress();
					if (addressList != null && addressList.size() != 0) {
						addressList.clear();
					}

				} else {
					// getActivity().finish();
					Toast.makeText(getActivity(), "没有上一层数据", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
		return view;
	}

	public void FindView() {
		selectAddressListView = (ListView) view
				.findViewById(R.id.selectAddress);
		selectAddressListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
			
				if (areaList.size() != 0 && addressList.size() == 0) {
					showProgress();
					Map<String, Object> map = new HashMap<String, Object>();
					Address address = new Address();
					address.setAddress_areaid(areaList.get(arg2).getArea_id());
					map.put("address", address);
					utils.put("selectaddressid",
							String.valueOf(areaList.get(arg2).getArea_id()));
					utils.put("selectaddressname", areaList.get(arg2)
							.getArea_name());
					RequestManager manager = new RequestManager();
					manager.setResponseListener(SelectAddressFragment.this);
					manager.requestAddress(map);
					areastack.push(areaList);

				} else {
					Intent intent = new Intent(getActivity(),
							PublishActivity.class);
					intent.putExtra("address", addressList.get(arg2)
							.getAddress_name());
					intent.putExtra("areaid", String.valueOf(addressList.get(
							arg2).getAddress_areaid()));
					startActivity(intent);
				}
			}
		});
	}

	@Override
	public <T> void successResponse(T parsedGSON, int tag) {
		// TODO Auto-generated method stub
		stopProgress();
		if (tag == Constants.RequestTag.AreaTag) {

			if (parsedGSON != null) {
				AreaBean areaBean = (AreaBean) parsedGSON;

				areaList = areaBean.getArea();

				selectAddressAdapter = new SelectAddressAdapter(areaList, null);
				selectAddressListView.setAdapter(selectAddressAdapter);
				selectAddressAdapter.notifyDataSetChanged();
			}
		}
		if (tag == Constants.RequestTag.AddressTag) {
			if (parsedGSON != null) {
				AddressBean addressBean = (AddressBean) parsedGSON;
				if (addressBean.getAddress() != null) {
					addressList = addressBean.getAddress();
				}
				selectAddressAdapter = new SelectAddressAdapter(null,
						addressList);
				selectAddressListView.setAdapter(selectAddressAdapter);
				selectAddressAdapter.notifyDataSetChanged();
			}
		}
	}

	@Override
	public void errorResonse(String retmeg, int tag) {
		// TODO Auto-generated method stub
		stopProgress();
		Toast.makeText(getActivity(), "请求地址失败", Toast.LENGTH_SHORT).show();
	}

	public class SelectAddressAdapter extends BaseAdapter {

		private List<Area> mAreaList;
		private List<Address> mAddressList;

		public SelectAddressAdapter(List<Area> list, List<Address> addressList) {
			this.mAreaList = list;
			this.mAddressList = addressList;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if (mAreaList != null) {
				return mAreaList.size();
			} else if (mAddressList != null) {
				return mAddressList.size();
			} else {
				return 0;
			}
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub

			if (mAreaList != null) {
				return mAreaList.get(arg0);
			} else if (mAddressList != null) {
				return mAddressList.get(arg0);
			} else {
				return 0;
			}
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		public View getView(final int position, View convertView,
				ViewGroup parent) {
			final HolderView mHolder;
			if (convertView == null) {
				mHolder = new HolderView();
				convertView = LayoutInflater.from(getActivity()).inflate(
						R.layout.city_adapter, null);
				mHolder.city_text = (TextView) convertView
						.findViewById(R.id.city_text);

				convertView.setTag(mHolder);
			} else {
				mHolder = (HolderView) convertView.getTag();
			}

			if (mAreaList != null) {
				mHolder.city_text.setText(mAreaList.get(position)
						.getArea_name());
			} else {
				mHolder.city_text.setText(mAddressList.get(position)
						.getAddress_name());
			}

			return convertView;
		}

		class HolderView {
			TextView city_text;

		}

	}

	/**
	 * progressDialog的展示
	 */
	public void showProgress() {
		if (getActivity() != null && !getActivity().isFinishing()) {
			mProgress = new ProgressDialog(getActivity());
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
