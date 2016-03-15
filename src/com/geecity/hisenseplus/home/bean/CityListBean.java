package com.geecity.hisenseplus.home.bean;

import java.util.LinkedList;

public class CityListBean extends BaseBean{
	private LinkedList<CityBean> data;

	public LinkedList<CityBean> getData() {
		return data;
	}

	public void setData(LinkedList<CityBean> data) {
		this.data = data;
	}
	
}
