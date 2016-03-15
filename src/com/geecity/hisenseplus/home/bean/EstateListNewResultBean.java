package com.geecity.hisenseplus.home.bean;

import java.util.LinkedList;

/**
 * 新房列表请求结果封装类
 * 
 * @author Administrator
 * 
 */
public class EstateListNewResultBean extends BaseBean {
	private LinkedList<EstateListNewBean> data;

	public LinkedList<EstateListNewBean> getData() {
		return data;
	}

	public void setData(LinkedList<EstateListNewBean> data) {
		this.data = data;
	}

}
