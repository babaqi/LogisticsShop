package com.logisticsShop.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.DatePicker.OnDateChangedListener;

import com.example.logisticsShop.R;
import com.gld.widget.refresh.XListView;
import com.gld.widget.refresh.XListView.IXListViewListener;
import com.logisticsShop.adapter.ContextQueryAdapter;
import com.logisticsShop.commen.Constants;
import com.logisticsShop.http.RequestManager;
import com.logisticsShop.http.RequestManager.ResponseInterface;
import com.logisticsShop.model.ContextModel;
import com.logisticsShop.serviceBean.ContextBean;
import com.logisticsShop.utils.SharePreferenceUtils;

public class WaitFragment extends Fragment implements IXListViewListener,
		ResponseInterface {
	private List<ContextModel> QueryList;
	private XListView showListView;
	private ListView myHistoryListView;
	private RelativeLayout RiLi;
	private TextView ChooseDate;
	TextView DemuNum;
	private Button Finsh;
	private ContextQueryAdapter adapter;
	private String time;
	private String currentPage = "0";// 当前页（分页）
	private String status = "0";// 问题状态（0：等待，1：已接；2：配送；3：历史
	private String ShopId;
	private String mMenuNum;
	private View view;
	private Timer timer;
	private SimpleDateFormat format;
	private Map<String, Object> map;
	private RequestManager manager;
	public ProgressDialog mProgress = null;
	SharePreferenceUtils utils;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view = inflater.inflate(R.layout.activity_select, container, false);
		utils = new SharePreferenceUtils(getActivity());
		QueryList = new ArrayList<ContextModel>();

		/** 初始化控件 **/
		FindView();

		/** 网络请求 **/
		// Request();

		/** 逻辑处理 **/
		Logic();

		timer = new Timer();
		timer.schedule(task, 1, 120000); // 1s后执行task,经过1s再次执行
		return view;
	}

	public void FindView() {
		DemuNum = (TextView) view.findViewById(R.id.DemuNum);// 历史订单数
		ChooseDate = (TextView) view.findViewById(R.id.ChooseDate);
		RiLi = (RelativeLayout) view.findViewById(R.id.rili_relay);
		showListView = (XListView) view.findViewById(R.id.ShowListview);
		myHistoryListView = (ListView) view
				.findViewById(R.id.HistoryShowListView);
		showListView.setPullLoadEnable(true);
		showListView.setPullIsEnable(false);
		showListView.setXListViewListener(this);

		status = getArguments().get("status").toString();

	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				Request();
			}
			super.handleMessage(msg);
		};
	};
	TimerTask task = new TimerTask() {

		@Override
		public void run() {
			// 需要做的事:发送消息
			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
		}
	};

	public void Request() {
		showProgress();
		map = new HashMap<String, Object>();
		manager = new RequestManager();
		map.put("page", currentPage);
		map.put("state", status);
		map.put("shopId", utils.getInt(Constants.LoginInfo.ShopId, 0));
		manager.setResponseListener(WaitFragment.this);
		manager.requestSeclect(map);
		stopLoad();
	}

	@Override
	public <T> void successResponse(T parsedGSON, int tag) {
		if (parsedGSON != null) {
			ContextBean contextBean = (ContextBean) parsedGSON;
			if (tag == Constants.RequestTag.QueryContextTag) {
				if (contextBean.getRetcode().equals("0")) {
					QueryList = contextBean.getContext();
					if (QueryList == null) {
						if (getActivity() != null) {
							Toast.makeText(getActivity(), "当前没有相关数据！", 5000)
									.show();
							stopProgress();
						}
					} else {
						adapter = new ContextQueryAdapter(getActivity(),
								QueryList, status);
						showListView.setAdapter(adapter);
						stopProgress();
					}
				} else {
					if (getActivity() != null) {
						Toast.makeText(getActivity(), "没有更多数据", 5000).show();
						showListView.setPullLoadEnable(false);
						stopProgress();
					}
				}
			}

			if (tag == Constants.RequestTag.QueryHistoryMenuLisTag) {
				if (contextBean.getRetcode().equals("0")) {
					QueryList = contextBean.getContext();
					if (QueryList == null) {
						if (getActivity() != null) {
							Toast.makeText(getActivity(), "当前没有相关数据！", 5000)
									.show();
							stopProgress();
						}
					} else {
						mMenuNum = contextBean.getCount();// 得到历史订单的个数
						DemuNum.setText(mMenuNum);// 显示在控件上
						adapter = new ContextQueryAdapter(getActivity(),
								QueryList, status);
						myHistoryListView.setAdapter(adapter);
						stopProgress();
					}
				} else {
					if (getActivity() != null) {
						Toast.makeText(getActivity(), "没有更多数据", 5000).show();
						showListView.setPullLoadEnable(false);
						stopProgress();
					}
				}
			}

		} else {
			if (getActivity() != null) {
				Toast.makeText(getActivity(), "请求失败！！", 5000).show();
				stopProgress();
			}
		}
	}

	@Override
	public void errorResonse(String retmeg, int tag) {
		// TODO Auto-generated method stub
		stopProgress();
		if (getActivity() != null) {
			Toast.makeText(getActivity(), "没有更多消息", 5000).show();
		}
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		currentPage = "0";
		showListView.setPullLoadEnable(true);
		QueryList.clear();
		Request();
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		int Page = Integer.parseInt(currentPage);
		Page++;
		currentPage = Page + "";
		stopProgress();
		Request();
	}

	/**
	 * 停止刷新和加载
	 */
	public void stopLoad() {
		showListView.stopRefresh();
		showListView.stopLoadMore();
		showListView.setRefreshTime();
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

	public void Logic() {

		if (!status.equals("3")) {
			// Request();

		} else {
			showListView.setVisibility(View.GONE);
			myHistoryListView.setVisibility(View.VISIBLE);
			RiLi.setVisibility(View.VISIBLE);// 历史订单页面显示日历布局

			// 选择日期按钮按钮
			ChooseDate.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// 日历dialog
					final AlertDialog dlg = new AlertDialog.Builder(
							getActivity()).create();
					dlg.show();
					Window window = dlg.getWindow();
					// *** 主要就是在这里实现这种效果的.
					// 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
					window.setContentView(R.layout.calender_dialog);
					Finsh = (Button) window.findViewById(R.id.Finsh);
					// timeEt=(EditText)findViewById(R.id.timeEt);
					final DatePicker datePicker = (DatePicker) window
							.findViewById(R.id.datePicker);
					// TimePicker
					// timePicker=(TimePicker)fsindViewById(R.id.timePicker);

					final Calendar calendar = Calendar.getInstance();
					int year = calendar.get(Calendar.YEAR);
					int monthOfYear = calendar.get(Calendar.MONTH);
					int dayOfMonth = calendar.get(Calendar.DATE);

					datePicker.init(year, monthOfYear, dayOfMonth,
							new OnDateChangedListener() {
								public void onDateChanged(DatePicker view,
										final int year, final int monthOfYear,
										final int dayOfMonth) {
								}
							});
					Finsh.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {
							time = datePicker.getYear() + "年"
									+ (datePicker.getMonth() + 1) + "月"
									+ datePicker.getDayOfMonth() + "日";
							ChooseDate.setText(time);
							calendar.set(datePicker.getYear(),
									datePicker.getMonth(),
									datePicker.getDayOfMonth());
							format = new SimpleDateFormat("yyyy-MM-dd");
							String timer = format.format(calendar.getTime());
							HistotyMenuList(timer);
							dlg.dismiss();
						}
					});
				}
			});
		}
	}

	/** 历史订单列表请求 **/
	public void HistotyMenuList(String timer) {
		showProgress();
		map = new HashMap<String, Object>();
		utils = new SharePreferenceUtils(getActivity());
		ShopId = utils.getInt("shopid", 0) + "";
		map.put("time", timer);
		map.put("shopid", ShopId);
		manager.setResponseListener(WaitFragment.this);
		manager.requestHistoryMenuList(map);
	}
}
