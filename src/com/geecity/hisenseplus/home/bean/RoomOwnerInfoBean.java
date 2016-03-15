package com.geecity.hisenseplus.home.bean;

/**
 * 帮定房源--身份信息验证类
 * @author Administrator
 *
 */
public class RoomOwnerInfoBean {
	private int id;
	private int A_id;
	private String B_id;
	private int R_dy;
	private String R_id;
	private int U_id;// 城市编号，用于获取小区
	private String U_name;
	private String mobilePhone;
	private int OutState;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getA_id() {
		return A_id;
	}

	public void setA_id(int a_id) {
		A_id = a_id;
	}

	public String getB_id() {
		return B_id;
	}

	public void setB_id(String b_id) {
		B_id = b_id;
	}

	public int getR_dy() {
		return R_dy;
	}

	public void setR_dy(int r_dy) {
		R_dy = r_dy;
	}

	public String getR_id() {
		return R_id;
	}

	public void setR_id(String r_id) {
		R_id = r_id;
	}

	public int getU_id() {
		return U_id;
	}

	public void setU_id(int u_id) {
		U_id = u_id;
	}

	public String getU_name() {
		return U_name;
	}

	public void setU_name(String u_name) {
		U_name = u_name;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public int getOutState() {
		return OutState;
	}

	public void setOutState(int outState) {
		OutState = outState;
	}

	@Override
	public String toString() {
		return "RoomOwnerInfoBean [id=" + id + ", A_id=" + A_id + ", B_id="
				+ B_id + ", R_dy=" + R_dy + ", R_id=" + R_id + ", U_id=" + U_id
				+ ", U_name=" + U_name + ", mobilePhone=" + mobilePhone
				+ ", OutState=" + OutState + "]";
	}
}
