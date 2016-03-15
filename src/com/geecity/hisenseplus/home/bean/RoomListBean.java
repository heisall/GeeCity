package com.geecity.hisenseplus.home.bean;

import java.util.LinkedList;

public class RoomListBean extends BaseBean{
	private LinkedList<RoomBean> data;

	public LinkedList<RoomBean> getData() {
		return data;
	}

	public void setData(LinkedList<RoomBean> data) {
		this.data = data;
	}
	
}
