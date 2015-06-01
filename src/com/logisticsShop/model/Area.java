package com.logisticsShop.model;

public class Area {

	private int area_id;
	private String area_name;
	private int area_cityid;
	
	public int getArea_id() {
		return area_id;
	}
	public void setArea_id(int area_id) {
		this.area_id = area_id;
	}
	public String getArea_name() {
		return area_name;
	}
	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}
	
	public int getArea_cityid() {
		return area_cityid;
	}
	public void setArea_cityid(int area_cityid) {
		this.area_cityid = area_cityid;
	}
	@Override
	public String toString() {
		return "Area [area_id=" + area_id + ", area_name=" + area_name
				+ ", area_cityid=" + area_cityid + "]";
	}


}
