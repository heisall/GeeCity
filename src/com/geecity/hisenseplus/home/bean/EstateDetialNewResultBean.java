package com.geecity.hisenseplus.home.bean;

import java.util.LinkedList;

public class EstateDetialNewResultBean extends BaseBean {
	private LinkedList<EstateDetialNewPhotoBean> photo;
	private EstateDetialNewBean house;

	public LinkedList<EstateDetialNewPhotoBean> getPhoto() {
		return photo;
	}

	public void setPhoto(LinkedList<EstateDetialNewPhotoBean> photo) {
		this.photo = photo;
	}

	public EstateDetialNewBean getHouse() {
		return house;
	}

	public void setHouse(EstateDetialNewBean house) {
		this.house = house;
	}

}
