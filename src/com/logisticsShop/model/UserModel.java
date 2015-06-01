package com.logisticsShop.model;

/**
 * 用户信息
 * 
 * @author mz
 * 
 * 
 */
public class UserModel {

	private int user_id;
	private String user_name;
	private String user_pwd;
	private String user_tel;
	private int user_city_id;
	private int user_area_id;
	
	public String getUser_tel() {
		return user_tel;
	}
	public void setUser_tel(String user_tel) {
		this.user_tel = user_tel;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_pwd() {
		return user_pwd;
	}
	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}
	
	
	public int getUser_city_id() {
		return user_city_id;
	}
	public void setUser_city_id(int user_city_id) {
		this.user_city_id = user_city_id;
	}
	
	
	
	public int getUser_area_id() {
		return user_area_id;
	}
	public void setUser_area_id(int user_area_id) {
		this.user_area_id = user_area_id;
	}
	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", user_name=" + user_name
				+ ", user_pwd=" + user_pwd + ", user_tel=" + user_tel
				+ ", user_city_id=" + user_city_id + ", user_adderss_id="
				+ user_area_id + "]";
	}

	
}
