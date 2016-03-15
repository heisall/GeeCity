package com.geecity.hisenseplus.home.bean;

import java.util.LinkedList;

public class ManagerHomeBean extends BaseBean {
	private LinkedList<ADBean> ads;
	private ManagerRoomBean room;

	public ManagerRoomBean getRoom() {
		return room;
	}

	public void setRoom(ManagerRoomBean room) {
		this.room = room;
	}

	public LinkedList<ADBean> getAds() {
		return ads;
	}

	public void setAds(LinkedList<ADBean> ads) {
		this.ads = ads;
	}
}
