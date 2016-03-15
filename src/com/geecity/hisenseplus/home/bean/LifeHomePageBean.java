package com.geecity.hisenseplus.home.bean;

import java.util.LinkedList;

public class LifeHomePageBean extends BaseBean {

	private LinkedList<ADBean> ad;
	private LinkedList<MallMenuBean> thumb;

	public LinkedList<ADBean> getAd() {
		return ad;
	}

	public void setAd(LinkedList<ADBean> ad) {
		this.ad = ad;
	}

	public LinkedList<MallMenuBean> getThumb() {
		return thumb;
	}

	public void setThumb(LinkedList<MallMenuBean> thumb) {
		this.thumb = thumb;
	}

}
