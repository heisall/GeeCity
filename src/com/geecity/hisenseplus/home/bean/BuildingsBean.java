package com.geecity.hisenseplus.home.bean;

/**
 * 绑定--小区列表类
 * 
 * @author Administrator
 * 
 */
public class BuildingsBean {
	/*
	 * "id":807, "b_id":"DXS", "b_name":"地下室", "SortIndex":null, "JU_BID":null
	 */
	private int id;
	private String b_id;// 楼号，用于获取房间号
	private String b_name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getB_id() {
		return b_id;
	}

	public void setB_id(String b_id) {
		this.b_id = b_id;
	}

	public String getB_name() {
		return b_name;
	}

	public void setB_name(String b_name) {
		this.b_name = b_name;
	}
}
