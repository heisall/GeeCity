package com.geecity.hisenseplus.home.bean;

import java.util.LinkedList;

public class NoticeListResultBean extends BaseBean {
	private LinkedList<NoticeListBean> data;

	public LinkedList<NoticeListBean> getData() {
		return data;
	}

	public void setData(LinkedList<NoticeListBean> data) {
		this.data = data;
	}

}
