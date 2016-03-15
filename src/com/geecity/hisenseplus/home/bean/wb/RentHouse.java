package com.geecity.hisenseplus.home.bean.wb;

import java.io.Serializable;

public class RentHouse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5163241202673685361L;
	private String buildingInfo;// 小区
	private String area;// 区域（市区）
	private String house_Indoor;// 室
	private String house_living;// 厅
	private String house_Toilet;// 卫
	private double buildingArea;// 建筑面积
	private double useArea;// 使用面积
	private double rent;// 租金
	private String description;// 描述
	private String addPerson;// 联系人
	private String mobilePhone;// 手机号
	private String supporting;// 配套设施
	private String[] photo;// 照片
	private String orientation;
	private String renovationInfo;
	private String payMethod;
	private String floors; 
	private String floorCount;
	private String niandai;

	public String getBuildingInfo() {
		return buildingInfo;
	}

	public void setBuildingInfo(String buildingInfo) {
		this.buildingInfo = buildingInfo;
	}

	public String getNiandai() {
		return niandai;
	}

	public void setNiandai(String niandai) {
		this.niandai = niandai;
	}

	public String getFloors() {
		return floors;
	}

	public void setFloors(String floors) {
		this.floors = floors;
	}

	public String getFloorCount() {
		return floorCount;
	}

	public void setFloorCount(String floorCount) {
		this.floorCount = floorCount;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getHouse_Indoor() {
		return house_Indoor;
	}

	public void setHouse_Indoor(String house_Indoor) {
		this.house_Indoor = house_Indoor;
	}

	public String getHouse_living() {
		return house_living;
	}

	public void setHouse_living(String house_living) {
		this.house_living = house_living;
	}

	public String getHouse_Toilet() {
		return house_Toilet;
	}

	public void setHouse_Toilet(String house_Toilet) {
		this.house_Toilet = house_Toilet;
	}

	public double getBuildingArea() {
		return buildingArea;
	}

	public void setBuildingArea(double buildingArea) {
		this.buildingArea = buildingArea;
	}

	public double getUseArea() {
		return useArea;
	}

	public void setUseArea(double useArea) {
		this.useArea = useArea;
	}

	public double getRent() {
		return rent;
	}

	public void setRent(double rent) {
		this.rent = rent;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddPerson() {
		return addPerson;
	}

	public void setAddPerson(String addPerson) {
		this.addPerson = addPerson;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getSupporting() {
		return supporting;
	}

	public void setSupporting(String supporting) {
		this.supporting = supporting;
	}

	public String[] getPhoto() {
		return photo;
	}

	public void setPhoto(String[] photo) {
		this.photo = photo;
	}

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	public String getRenovationInfo() {
		return renovationInfo;
	}

	public void setRenovationInfo(String renovationInfo) {
		this.renovationInfo = renovationInfo;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	@Override
	public String toString() {
		return "RentHouse [buildingInfo=" + buildingInfo + ", area=" + area
				+ ", house_Indoor=" + house_Indoor + ", house_living="
				+ house_living + ", house_Toilet=" + house_Toilet
				+ ", buildingArea=" + buildingArea + ", useArea=" + useArea
				+ ", rent=" + rent + ", description=" + description
				+ ", addPerson=" + addPerson + ", mobilePhone=" + mobilePhone
				+ ", supporting=" + supporting + ", orientation=" + orientation
				+ ", renovationInfo=" + renovationInfo + ", payMethod="
				+ payMethod + "]";
	}
}
