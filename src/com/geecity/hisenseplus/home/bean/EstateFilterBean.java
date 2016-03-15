package com.geecity.hisenseplus.home.bean;

import java.io.Serializable;

/**
 * 房屋添加条件列表
 * 
 * @author Administrator
 * 
 */
public class EstateFilterBean implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private String dicKey;
	private String dicValue;

	public EstateFilterBean(String dicKey, String dicValue) {
		this.dicKey = dicKey;
		this.dicValue = dicValue;
	}

	public String getDicKey() {
		return dicKey;
	}

	public void setDicKey(String dicKey) {
		this.dicKey = dicKey;
	}

	public String getDicValue() {
		return dicValue;
	}

	public void setDicValue(String dicValue) {
		this.dicValue = dicValue;
	}

	@Override
	public String toString() {
		return "EstateFilterBean [dicKey=" + dicKey + ", dicValue=" + dicValue
				+ "]";
	}

}
