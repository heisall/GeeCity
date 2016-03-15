package com.geecity.hisenseplus.home.bean;

import java.io.Serializable;

/**
 * 收货地址列表类
 * 
 * @author Administrator
 * 
 */
public class AddressListBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int addr_id;
	private String consignee;// 姓名
	private int region_id;
	private String region_name;// 区域
	private String address;// 地址
	private String phone_tel;// 电话
	private boolean is_default;// 是否默认
	private boolean isSelected;// 是否被选

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public int getAddr_id() {
		return addr_id;
	}

	public void setAddr_id(int addr_id) {
		this.addr_id = addr_id;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public int getRegion_id() {
		return region_id;
	}

	public void setRegion_id(int region_id) {
		this.region_id = region_id;
	}

	public String getRegion_name() {
		return region_name;
	}

	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone_tel() {
		return phone_tel;
	}

	public void setPhone_tel(String phone_tel) {
		this.phone_tel = phone_tel;
	}

	public boolean isIs_default() {
		return is_default;
	}

	public void setIs_default(boolean is_default) {
		this.is_default = is_default;
	}

}
