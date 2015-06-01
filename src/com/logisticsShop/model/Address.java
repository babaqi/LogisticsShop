package com.logisticsShop.model;

public class Address {

	private int address_id;
	private String address_name;
	private int address_areaid;
	public int getAddress_id() {
		return address_id;
	}
	public void setAddress_id(int address_id) {
		this.address_id = address_id;
	}
	public String getAddress_name() {
		return address_name;
	}
	public void setAddress_name(String address_name) {
		this.address_name = address_name;
	}
	public int getAddress_areaid() {
		return address_areaid;
	}
	public void setAddress_areaid(int address_areaid) {
		this.address_areaid = address_areaid;
	}
	@Override
	public String toString() {
		return "Address [address_id=" + address_id + ", address_name="
				+ address_name + ", address_areaid=" + address_areaid + "]";
	}

}
