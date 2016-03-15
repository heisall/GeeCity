package com.geecity.hisenseplus.home.bean;

import java.io.Serializable;

/**
 * 报修/投诉列表类
 * 
 * @author Administrator
 * 
 */
public class RepairDetailBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * "id":25, "code":"1-20151214025", "createtime":"12-14 21:37",
	 * "miaoshu":"我家的下水道堵了", "zt":"未受理", "hfzt":null, "xing":-1,
	 * "":"12-14 21:37", "":"12-14 全天", "":null, "":null, "":null, "":null,
	 * "leixing":"维修"
	 */
	private int id;
	private String code;// 接待单号
	private String createtime;// 提交时间
	private String miaoshu;// 内容
	private String zt;// 状态
	private String hfzt;// 回访状态
	private int xing;// 一个星级数字+1,返回整数
	private String leixing;// 类型("维修"or"投诉")
	private String fj;

	private String yytime;// 预约时间
	private String yytime1;// 预约时间1
	private String slname;// 受理人名称
	private String gbtime;//关闭事件
	private String lwpgsj;//派工时间
	private String lwclr;//处理人名称
	private String sj;//处理人手机号码

	public String getSj() {
		return sj;
	}

	public void setSj(String sj) {
		this.sj = sj;
	}

	public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

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

	public String getYytime() {
		return yytime;
	}

	public void setYytime(String yytime) {
		this.yytime = yytime;
	}

	public String getYytime1() {
		return yytime1;
	}

	public void setYytime1(String yytime1) {
		this.yytime1 = yytime1;
	}

	public String getSlname() {
		return slname;
	}

	public void setSlname(String slname) {
		this.slname = slname;
	}

	public String getGbtime() {
		return gbtime;
	}

	public void setGbtime(String gbtime) {
		this.gbtime = gbtime;
	}

	public String getLwpgsj() {
		return lwpgsj;
	}

	public void setLwpgsj(String lwpgsj) {
		this.lwpgsj = lwpgsj;
	}

	public String getLwclr() {
		return lwclr;
	}

	public void setLwclr(String lwclr) {
		this.lwclr = lwclr;
	}

}
