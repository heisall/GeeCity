package com.geecity.hisenseplus.home.bean;

import java.util.LinkedList;

/**
 * 收货地址请求结果列表类
 * 
 * @author Administrator
 * 
 */
public class AddressListResultBean extends BaseBean {
	private LinkedList<AddressListBean> data;

	public LinkedList<AddressListBean> getData() {
		return data;
	}

	public void setData(LinkedList<AddressListBean> data) {
		this.data = data;
	}
}
