package com.geecity.hisenseplus.home.activity.property.model;

import java.io.Serializable;

import com.geecity.hisenseplus.home.bean.BaseBean;

/**
 * 
 * <pre>
 * 业务名：
 * 功能说明：物业账单
 * 编写日期：2016-1-23 上午8:25:47
 * 作者：魏巍
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class BillsInfos extends BaseBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private int a_id;
	private String b_id;
	private int r_dy;
	private String r_id;
	private int t_sign;
	private String t_item;
	private String t_date;
	private String t_lastdate;
	private String t_thisdate;
	private double t_money;
	private boolean isCheck;

	private String priceForShow;// 显示的多行价格
	private double totalPrice;// 当月统计得出的总价
	private String ids;// 拼接缴费账单的ID，已逗号间隔
	private String datas;//拼接显示的缴费期间

	public String getDatas() {
		return datas;
	}

	public void setDatas(String datas) {
		this.datas = datas;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public int getA_id() {
		return a_id;
	}

	public void setA_id(int a_id) {
		this.a_id = a_id;
	}

	public int getR_dy() {
		return r_dy;
	}

	public void setR_dy(int r_dy) {
		this.r_dy = r_dy;
	}

	public int getT_sign() {
		return t_sign;
	}

	public void setT_sign(int t_sign) {
		this.t_sign = t_sign;
	}

	public String getPriceForShow() {
		return priceForShow;
	}

	public void setPriceForShow(String priceForShow) {
		this.priceForShow = priceForShow;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getB_id() {
		return b_id;
	}

	public void setB_id(String b_id) {
		this.b_id = b_id;
	}

	public String getR_id() {
		return r_id;
	}

	public void setR_id(String r_id) {
		this.r_id = r_id;
	}

	public String getT_item() {
		return t_item;
	}

	public void setT_item(String t_item) {
		this.t_item = t_item;
	}

	public String getT_date() {
		return t_date;
	}

	public void setT_date(String t_date) {
		this.t_date = t_date;
	}

	public String getT_lastdate() {
		return t_lastdate;
	}

	public void setT_lastdate(String t_lastdate) {
		this.t_lastdate = t_lastdate;
	}

	public String getT_thisdate() {
		return t_thisdate;
	}

	public void setT_thisdate(String t_thisdate) {
		this.t_thisdate = t_thisdate;
	}

	public double getT_money() {
		return t_money;
	}

	public void setT_money(double t_money) {
		this.t_money = t_money;
	}

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

	public BillsInfos() {
		super();
		// TODO Auto-generated constructor stub
	}

}
