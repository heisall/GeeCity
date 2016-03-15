package com.geecity.hisenseplus.home.bean;

public class CityBean{
	//{"result":"1","data":[{"id":5,"CityName":"济南市","CityNo":"3701","SortIndex":1,"GPS":"1"},{"id":6,"CityName":"青岛市","CityNo":"3702","SortIndex":2,"GPS":"2"}],"errorCode":null}
	private int id;
	private String CityName;
	private String CityNo;//城市编号，用于获取小区
	private String GPS;
	private int SortIndex;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCityName() {
		return CityName;
	}
	public void setCityName(String cityName) {
		CityName = cityName;
	}
	public String getCityNo() {
		return CityNo;
	}
	public void setCityNo(String cityNo) {
		CityNo = cityNo;
	}
	public String getGPS() {
		return GPS;
	}
	public void setGPS(String gPS) {
		GPS = gPS;
	}
	public int getSortIndex() {
		return SortIndex;
	}
	public void setSortIndex(int sortIndex) {
		SortIndex = sortIndex;
	}
	
}
