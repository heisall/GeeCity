package com.geecity.hisenseplus.home.bean;

import java.util.LinkedList;

import android.text.TextUtils;

public class EstateDetialRentBean {
	
    private int id;
    private String buildingInfo;
    private String area;
    private String house_Indoor;
    private String house_living;
    private String house_Toilet;
    private double buildingArea;
    private LinkedList<SupportingBean> supporting;
    private String fkfs;
    private String floors;
    private String floorCount;
    private String orientation;
    private String renovationInfo;
    private String description;
    private String contactPerson;
    private String contactPhone;
    private String ReleaseTime;
    private int rent;
    private int clickCount;
    private String photo;
    private int isCollected;
    private String niandai;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getNiandai() {
		if(TextUtils.isEmpty(niandai)){
			return "";
		}
		return niandai;
	}
	public void setNiandai(String niandai) {
		this.niandai = niandai;
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
	public LinkedList<SupportingBean> getSupporting() {
		return supporting;
	}
	public void setSupporting(LinkedList<SupportingBean> supporting) {
		this.supporting = supporting;
	}
	public String getFkfs() {
		return fkfs;
	}
	public void setFkfs(String fkfs) {
		this.fkfs = fkfs;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getReleaseTime() {
		return ReleaseTime;
	}
	public void setReleaseTime(String releaseTime) {
		ReleaseTime = releaseTime;
	}
	public int getRent() {
		return rent;
	}
	public void setRent(int rent) {
		this.rent = rent;
	}
	public int getClickCount() {
		return clickCount;
	}
	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public int getIsCollected() {
		return isCollected;
	}
	public void setIsCollected(int isCollected) {
		this.isCollected = isCollected;
	}
   
}
