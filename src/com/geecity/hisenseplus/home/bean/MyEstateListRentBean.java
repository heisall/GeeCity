package com.geecity.hisenseplus.home.bean;

public class MyEstateListRentBean {
	private int id;
	private String buildingInfo;
	private String area;
	private String house_Indoor;
	private String house_living;
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
	
	
	public float getRent() {
		return rent;
	}
	public void setRent(float rent) {
		this.rent = rent;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	private String house_Toilet;
	private double buildingArea;
	private float rent;
	private String photo;
}
