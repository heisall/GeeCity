package com.geecity.hisenseplus.home.bean;

import java.util.LinkedList;

/**
 * 二手房列表请求结果封装类
 * 
 * @author Administrator
 * 
 */
public class EstateListSecondHandResultBean extends BaseBean {
	private LinkedList<EstateListSecondHandBean> data;

	public LinkedList<EstateListSecondHandBean> getData() {
		return data;
	}

	public void setData(LinkedList<EstateListSecondHandBean> data) {
		this.data = data;
	}

}
