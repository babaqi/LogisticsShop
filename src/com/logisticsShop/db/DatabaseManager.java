package com.logisticsShop.db;

import java.util.ArrayList;
import java.util.List;

import com.logisticsShop.model.CommonAddressModel;

import android.R.integer;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {

	private DatabaseHelper helper;
	private SQLiteDatabase database;

	private static final String DATA_NAME = "commonaddress.db";
	private static final int DATA_VERSION = 1;

	public DatabaseManager(Context context) {
		helper = new DatabaseHelper(context, DATA_NAME, null, DATA_VERSION);
		database = helper.getWritableDatabase();
		database = helper.getReadableDatabase();

	}

	// 数据库保存新地址
	public void SaveNewAddress(String newAddress) {

		database.beginTransaction();

		// database.insert("CommonAddress", null, list.get(i).toString());
		database.execSQL("INSERT INTO CommonAddress VALUES(?,?)", new Object[] {
				null, newAddress });
		database.setTransactionSuccessful(); // 设置事务成功完成
		database.endTransaction(); // 结束事务
	}

	// 数据库取地址list
	public List<CommonAddressModel> SelectAddress() {
		List<CommonAddressModel> list = new ArrayList<CommonAddressModel>();
		String[] typeIds = {};
		Cursor cursor = database.rawQuery("Select * from CommonAddress",
				typeIds);
		while (cursor.moveToNext()) {
			CommonAddressModel model = new CommonAddressModel();
			model.setName(cursor.getString(cursor.getColumnIndex("Name")));
			model.setId(cursor.getInt(cursor.getColumnIndex("Id")));
			list.add(model);

		}
		return list;

	}

	public void DeleteAddress(int id) {

		database.execSQL("Delete from CommonAddress where Id = '" + id + "'");

	}
}
