package com.geecity.hisenseplus.home.bean;

import java.util.LinkedList;

/**
 * 商户详情的商户信息类
 * 
 * @author Administrator
 * 
 */
public class BusinessDetailResultBean extends BaseBean {

	private LinkedList<GoodsListBean> goods;
	private BusinessDetailBean store;
	private LinkedList<GoodsCategrayBean> cates;

	public LinkedList<GoodsListBean> getGoods() {
		return goods;
	}

	public void setGoods(LinkedList<GoodsListBean> goods) {
		this.goods = goods;
	}

	public BusinessDetailBean getStore() {
		return store;
	}

	public void setStore(BusinessDetailBean store) {
		this.store = store;
	}

	public LinkedList<GoodsCategrayBean> getCates() {
		return cates;
	}

	public void setCates(LinkedList<GoodsCategrayBean> cates) {
		this.cates = cates;
	}
}
