package com.geecity.hisenseplus.home.bean;

import java.util.LinkedList;

public class GroupBuyListResultBean extends BaseBean {
	private LinkedList<GroupBuyBean> data;

	public LinkedList<GroupBuyBean> getData() {
		return data;
	}

	public void setData(LinkedList<GroupBuyBean> data) {
		this.data = data;
	}

}
