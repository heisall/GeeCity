package com.geecity.hisenseplus.home.bean;

import java.io.Serializable;

/**
 * 报修/投诉列表类
 * 
 * @author Administrator
 * 
 */
public class RepairListBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * "":"1-20151214025", "":"12-14 21:37", "":"我家的下水道堵了", "":"未受理", "":null,
	 * "":null, "":"维修"
	 */

	/*
	 * 1.处理中 lwpgsj 不是空 2.已完成 gbtime 不是空 3.评价 根据hfzt不是已评价 和gbtime不是空
	 */
	private int id;
	private String code;// 接待单号
	private String createtime;// 提交时间
	private String miaoshu;// 内容
	private String zt;// 状态
	private String hfzt;// 回访状态
	private int xing;// 一个星级数字+1,返回整数
	private String leixing;
	private String photos;// 图片，逗号分隔

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getMiaoshu() {
		return miaoshu;
	}

	public void setMiaoshu(String miaoshu) {
		this.miaoshu = miaoshu;
	}

	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	public String getHfzt() {
		return hfzt;
	}

	public void setHfzt(String hfzt) {
		this.hfzt = hfzt;
	}

	public int getXing() {
		return xing;
	}

	public void setXing(int xing) {
		this.xing = xing;
	}

	public String getLeixing() {
		return leixing;
	}

	public void setLeixing(String leixing) {
		this.leixing = leixing;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	@Override
	public String toString() {
		return "RepairListBean [id=" + id + ", code=" + code + ", createtime="
				+ createtime + ", miaoshu=" + miaoshu + ", zt=" + zt
				+ ", hfzt=" + hfzt + ", xing=" + xing + ", leixing=" + leixing
				+ ", photos=" + photos + "]";
	}
}
