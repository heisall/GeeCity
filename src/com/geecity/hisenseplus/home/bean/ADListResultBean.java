package com.geecity.hisenseplus.home.bean;

import java.util.LinkedList;


/**
 * 广告获取结果列表类
 * 
 * @author Administrator
 * 
 */
public class ADListResultBean extends BaseBean {
	private LinkedList<ADBean> ad;

	public LinkedList<ADBean> getAd() {
		return ad;
	}

	public void setAd(LinkedList<ADBean> ad) {
		this.ad = ad;
	}


}
