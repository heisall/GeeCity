package com.geecity.hisenseplus.home.bean;

import java.util.LinkedList;

import com.geecity.hisenseplus.home.activity.property.model.BillsInfos;

public class BillUnPayListBean extends BaseBean{
	private LinkedList<BillsInfos> data;

	public LinkedList<BillsInfos> getData() {
		return data;
	}

	public void setData(LinkedList<BillsInfos> data) {
		this.data = data;
	}
	
}
