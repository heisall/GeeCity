package com.geecity.hisenseplus.home.bean.wb;

import java.io.Serializable;
import java.util.Date;

/**
 * 二手房对象
 * @author duanjj
 *
 */
public class OldHouse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1492492317635442564L;
	
	private String buildingInfo;
	private String area;
	private String house_Indoor;
	private String house_living;
	private String house_Toilet;
	private Double buildingArea;
	private Double useArea;
	private Double rent;
	private String floors;
	private String floorCount;
	private String niandai;
	private String maidian;
	private String fangyuanmiaoshu;
	private String orientation;
	private String renovationInfo;
	private String mobilePhone;
	private String[] photo;
	private String addPerson;
	private Integer clickCount;
	private Date addTime;
	private String city;
	
	public OldHouse(){}
	
	
	public OldHouse(String buildingInfo, String area, String house_Indoor, String house_living, String house_Toilet,
			Double buildingArea, Double useArea, Double rent, String floors, String floorCount, String niandai,
			String maidian, String fangyuanmiaoshu, String orientation, String renovationInfo, String mobilePhone,
			String[] photo, String addPerson, Integer clickCount, Date addTime, String city) {
		super();
		this.buildingInfo = buildingInfo;
		this.area = area;
		this.house_Indoor = house_Indoor;
		this.house_living = house_living;
		this.house_Toilet = house_Toilet;
		this.buildingArea = buildingArea;
		this.useArea = useArea;
		this.rent = rent;
		this.floors = floors;
		this.floorCount = floorCount;
		this.niandai = niandai;
		this.maidian = maidian;
		this.fangyuanmiaoshu = fangyuanmiaoshu;
		this.orientation = orientation;
		this.renovationInfo = renovationInfo;
		this.mobilePhone = mobilePhone;
		this.photo = photo;
		this.addPerson = addPerson;
		this.clickCount = clickCount;
		this.addTime = addTime;
		this.city = city;
	}
	public String getBuildingInfo() {
		return buildingInfo;
	}
	public void setBuildingInfo(String buildingInfo) {
		this.buildingInfo = buildingInfo;
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
	public Double getBuildingArea() {
		return buildingArea;
	}
	public void setBuildingArea(Double buildingArea) {
		this.buildingArea = buildingArea;
	}
	public Double getUseArea() {
		return useArea;
	}
	public void setUseArea(Double useArea) {
		this.useArea = useArea;
	}
	public Double getRent() {
		return rent;
	}
	public void setRent(Double rent) {
		this.rent = rent;
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
	public String getNiandai() {
		return niandai;
	}
	public void setNiandai(String niandai) {
		this.niandai = niandai;
	}
	public String getMaidian() {
		return maidian;
	}
	public void setMaidian(String maidian) {
		this.maidian = maidian;
	}
	public String getFangyuanmiaoshu() {
		return fangyuanmiaoshu;
	}
	public void setFangyuanmiaoshu(String fangyuanmiaoshu) {
		this.fangyuanmiaoshu = fangyuanmiaoshu;
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
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String[] getPhoto() {
		return photo;
	}
	public void setPhoto(String[] photo) {
		this.photo = photo;
	}
	public String getAddPerson() {
		return addPerson;
	}
	public void setAddPerson(String addPerson) {
		this.addPerson = addPerson;
	}
	public Integer getClickCount() {
		return clickCount;
	}
	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "OldHouse [buildingInfo=" + buildingInfo + ", area=" + area
				+ ", house_Indoor=" + house_Indoor + ", house_living="
				+ house_living + ", house_Toilet=" + house_Toilet
				+ ", buildingArea=" + buildingArea + ", useArea=" + useArea
				+ ", rent=" + rent + ", floors=" + floors + ", floorCount="
				+ floorCount + ", niandai=" + niandai + ", maidian=" + maidian
				+ ", fangyuanmiaoshu=" + fangyuanmiaoshu + ", orientation="
				+ orientation + ", renovationInfo=" + renovationInfo
				+ ", mobilePhone=" + mobilePhone + ", addPerson=" + addPerson
				+ ", clickCount=" + clickCount + ", addTime=" + addTime
				+ ", city=" + city + "]";
	}
}
