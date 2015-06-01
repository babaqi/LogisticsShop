package com.logisticsShop.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.PaintDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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

import com.example.logisticsShop.R;
import com.logisticsShop.db.DatabaseManager;
import com.logisticsShop.model.CommonAddressModel;

public class ShowPopupWindow {

	private Context mContext;
	private Window mWindow;
	private AlertDialog mDlg;
	private PopupWindow popupWindow;
	ListView CommonAddresslistView;
	/** popupwindow常用地址list显示控件 **/
	private Button btn_addAddress, btn_ok;
	private EditText ed_addAddress;// 地址输入框
	private String mNewAddress;// 地址输入框内容
	private List<CommonAddressModel> addresslist;
	List<String> PopGetNameFromSQLiteList;
	List<Integer> PopGetIDFromSQLiteList;
	ArrayAdapter<String> adapter;
	String address;
	private DatabaseManager manager;

	private PopAddressGetListener mAddressGetHandler;

	public void setmAddressGetHandler(PopAddressGetListener mAddressGetHandler) {
		this.mAddressGetHandler = mAddressGetHandler;
	}

	// 反射
	public static interface PopAddressGetListener {
		void onPopAddressGet(String address);
	}

	public ShowPopupWindow(Context context, Window window) {
		// TODO Auto-generated constructor stub
		mContext = context;
		mWindow = window;
	}

	public void createPop() {
		final View view = LayoutInflater.from(mContext).inflate(
				R.layout.dialog_commonaddresslist, null);

		btn_addAddress = (Button) view.findViewById(R.id.AddAddresds);
		ed_addAddress = (EditText) view.findViewById(R.id.EditAddress);

		CommonAddresslistView = (ListView) view
				.findViewById(R.id.CommonAddListView);
		btn_ok = (Button) view.findViewById(R.id.OKButton);

		view.setBackgroundColor(Color.YELLOW);
		popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);

		popupWindow.setContentView(view);
		popupWindow.setFocusable(true);
		popupWindow.setBackgroundDrawable(new PaintDrawable());

		/** 取出数据库中地址 **/
		QuerySQLite();

		// 取到父布局
		View parent = mWindow.getDecorView();

		popupWindow.setFocusable(true);
		popupWindow.showAtLocation(parent, Gravity.CENTER_VERTICAL, 0, 0);

		btn_addAddress.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				ed_addAddress.setVisibility(view.VISIBLE);
				btn_ok.setVisibility(view.VISIBLE);
			}
		});
		// 确认添加地址按钮监听
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				/** 将新添加的地址插入到数据库中 **/
				InsertSQLite();
				/** 取出数据库中的内容 **/
				QuerySQLite();

				Toast.makeText(mContext, "添加地址成功！", 5000).show();

			}
		});
		CommonAddresslistView
				.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> arg0,
							View arg1, int arg2, long arg3) {

						createDialog(arg2, PopGetIDFromSQLiteList);

						return false;
					}
				});
		CommonAddresslistView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				address = PopGetNameFromSQLiteList.get(arg2).toString();
				mAddressGetHandler.onPopAddressGet(address);
			}
		});
	}

	private void createDialog(final int position, final List<Integer> listid) {
		if (mDlg == null) {
			mDlg = new AlertDialog.Builder(mContext).create();
		}
		mDlg.show();
		// 点击对话框外部取消对话框显示
		mDlg.setCanceledOnTouchOutside(true);
		Window window = mDlg.getWindow();
		// 设置窗口的内容页面,dialog_uploading.xml文件中定义view内容
		window.setContentView(R.layout.dialog_unupload);
		TextView dialog_title = (TextView) window
				.findViewById(R.id.dialog_title);
		dialog_title.setText("确定要删除该地址吗？");
		Button ok = (Button) window.findViewById(R.id.dlg_btn_ok);
		Button no = (Button) window.findViewById(R.id.dlg_btn_no);
		// 确定按钮
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int id = listid.get(position);
				manager.DeleteAddress(id);

				PopGetIDFromSQLiteList.remove(position);
				PopGetNameFromSQLiteList.remove(position);
				adapter.notifyDataSetChanged();
				mDlg.dismiss();
			}
		});
		no.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mDlg.dismiss();
			}
		});
	}

	public void InsertSQLite() {

		manager = new DatabaseManager(mContext);
		mNewAddress = ed_addAddress.getText().toString();
		if (mNewAddress.equals("")) {
			Toast.makeText(mContext, "请输入内容", 5000).show();
		} else {

			ed_addAddress.setText("");
			ed_addAddress.setVisibility(View.GONE);
			btn_ok.setVisibility(View.GONE);
			manager.SaveNewAddress(mNewAddress);
		}

	}

	public void QuerySQLite() {
		/** 取出数据库中地址 **/
		manager = new DatabaseManager(mContext);
		addresslist = manager.SelectAddress();
		PopGetNameFromSQLiteList = new ArrayList<String>();
		PopGetIDFromSQLiteList = new ArrayList<Integer>();
		for (int i = 0; i < addresslist.size(); i++) {
			PopGetNameFromSQLiteList.add(addresslist.get(i).getName());
			PopGetIDFromSQLiteList.add(addresslist.get(i).getId());
		}
		adapter = new ArrayAdapter<String>(mContext,
				android.R.layout.simple_list_item_1, PopGetNameFromSQLiteList);
		adapter.notifyDataSetChanged();
		CommonAddresslistView.setAdapter(adapter);
	}

}
