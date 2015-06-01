package com.logisticsShop.model;

public class ShopModel {

	private int shop_id;
	private String shop_name;
	private String shop_address;
	private String shop_tel;
	private String shop_pwd;
	private int shop_city_id;
	private int shop_area_id;
	
	
	public int getShop_area_id() {
		return shop_area_id;
	}

	public void setShop_area_id(int shop_area_id) {
		this.shop_area_id = shop_area_id;
	}

	public String getShop_pwd() {
		return shop_pwd;
	}

	public void setShop_pwd(String shop_pwd) {
		this.shop_pwd = shop_pwd;
	}

	public int getShop_id() {
		return shop_id;
	}

	public void setShop_id(int shop_id) {
		this.shop_id = shop_id;
	}

	public String getShop_name() {
		return shop_name;
	}

	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}

	public String getShop_address() {
		return shop_address;
	}

	public void setShop_address(String shop_address) {
		this.shop_address = shop_address;
	}

	public String getShop_tel() {
		return shop_tel;
	}

	public void setShop_tel(String shop_tel) {
		this.shop_tel = shop_tel;
	}

	public int getShop_city_id() {
		return shop_city_id;
	}

	public void setShop_city_id(int shop_city_id) {
		this.shop_city_id = shop_city_id;
	}

	@Override
	public String toString() {
		return "Shop [shop_id=" + shop_id + ", shop_name=" + shop_name
				+ ", shop_address=" + shop_address + ", shop_tel=" + shop_tel
				+ ", shop_pwd=" + shop_pwd + ", shop_city_id=" + shop_city_id
				+ ", shop_area_id=" + shop_area_id + "]";
	}


	


	
}
