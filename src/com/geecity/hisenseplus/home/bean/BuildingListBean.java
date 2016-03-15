package com.geecity.hisenseplus.home.bean;

import java.util.LinkedList;

public class BuildingListBean extends BaseBean{
	private LinkedList<BuildingsBean> data;

	public LinkedList<BuildingsBean> getData() {
		return data;
	}

	public void setData(LinkedList<BuildingsBean> data) {
		this.data = data;
	}
	
}
