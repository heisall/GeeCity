package com.geecity.hisenseplus.home.bean;

/**
 * 房间列表
 * 
 * @author Administrator
 * 
 */
public class RoomBean {

	private int id;
	private int R_dy;//单元ID
	private int R_lc;//楼层
	private String R_id;//房间号
	private int U_id;
	private String JU_RID;// 房间ID

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getR_dy() {
		return R_dy;
	}

	public void setR_dy(int r_dy) {
		R_dy = r_dy;
	}

	public int getR_lc() {
		return R_lc;
	}

	public void setR_lc(int r_lc) {
		R_lc = r_lc;
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

	public String getJU_RID() {
		return JU_RID;
	}

	public void setJU_RID(String jU_RID) {
		JU_RID = jU_RID;
	}
}
