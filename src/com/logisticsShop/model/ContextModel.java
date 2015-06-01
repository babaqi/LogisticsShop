package com.logisticsShop.model;

public class ContextModel {
	private int context_id;
	private String context_shop_id;
	private String context_address;
	private String context_phone;
	private String context_price;
	private String context_amountofmoney;
	private String context_infomation;
	private int context_type;
	private int context_userid;
	private int context_cityid;
	private int context_areaid;
	private String context_shop_name;
	private String context_shop_address;
	private String context_user_name;
	private String context_timer;
	private String context_shop_tel;
	
	
	public String getContext_shop_tel() {
		return context_shop_tel;
	}

	public void setContext_shop_tel(String context_shop_tel) {
		this.context_shop_tel = context_shop_tel;
	}

	public int getContext_areaid() {
		return context_areaid;
	}

	public void setContext_areaid(int context_areaid) {
		this.context_areaid = context_areaid;
	}

	public String getContext_timer() {
		return context_timer;
	}

	public void setContext_timer(String context_timer) {
		this.context_timer = context_timer;
	}

	public String getContext_shop_id() {
		return context_shop_id;
	}

	public void setContext_shop_id(String context_shop_id) {
		this.context_shop_id = context_shop_id;
	}

	public String getContext_shop_name() {
		return context_shop_name;
	}

	public void setContext_shop_name(String context_shop_name) {
		this.context_shop_name = context_shop_name;
	}

	public String getContext_shop_address() {
		return context_shop_address;
	}

	public void setContext_shop_address(String context_shop_address) {
		this.context_shop_address = context_shop_address;
	}

	public String getContext_user_name() {
		return context_user_name;
	}

	public void setContext_user_name(String context_user_name) {
		this.context_user_name = context_user_name;
	}

	public int getContext_id() {
		return context_id;
	}

	public void setContext_id(int context_id) {
		this.context_id = context_id;
	}


	public String getContext_address() {
		return context_address;
	}

	public void setContext_address(String context_address) {
		this.context_address = context_address;
	}

	public String getContext_phone() {
		return context_phone;
	}

	public void setContext_phone(String context_phone) {
		this.context_phone = context_phone;
	}

	public String getContext_price() {
		return context_price;
	}

	public void setContext_price(String context_price) {
		this.context_price = context_price;
	}

	public String getContext_amountofmoney() {
		return context_amountofmoney;
	}

	public void setContext_amountofmoney(String context_amountofmoney) {
		this.context_amountofmoney = context_amountofmoney;
	}

	public String getContext_infomation() {
		return context_infomation;
	}

	public void setContext_infomation(String context_infomation) {
		this.context_infomation = context_infomation;
	}

	public int getContext_type() {
		return context_type;
	}

	public void setContext_type(int context_type) {
		this.context_type = context_type;
	}

	public int getContext_userid() {
		return context_userid;
	}

	public void setContext_userid(int context_userid) {
		this.context_userid = context_userid;
	}

	public int getContext_cityid() {
		return context_cityid;
	}

	public void setContext_cityid(int context_cityid) {
		this.context_cityid = context_cityid;
	}

	@Override
	public String toString() {
		return "Context [context_id=" + context_id + ", context_shop_id="
				+ context_shop_id + ", context_address=" + context_address
				+ ", context_phone=" + context_phone + ", context_price="
				+ context_price + ", context_amountofmoney="
				+ context_amountofmoney + ", context_infomation="
				+ context_infomation + ", context_type=" + context_type
				+ ", context_userid=" + context_userid + ", context_cityid="
				+ context_cityid + ", context_areaid=" + context_areaid
				+ ", context_shop_name=" + context_shop_name
				+ ", context_shop_address=" + context_shop_address
				+ ", context_user_name=" + context_user_name
				+ ", context_timer=" + context_timer + ", context_shop_tel="
				+ context_shop_tel + "]";
	}







}
