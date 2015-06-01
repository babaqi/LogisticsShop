package com.logisticsShop.serviceBean;

import java.util.List;

import com.logisticsShop.model.ContextModel;

public class ContextBean extends BaseBean {
	private String count;
	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public List<ContextModel> getContext() {
		return context;
	}

	public void setContext(List<ContextModel> context) {
		this.context = context;
	}

	private List<ContextModel> context;


}
