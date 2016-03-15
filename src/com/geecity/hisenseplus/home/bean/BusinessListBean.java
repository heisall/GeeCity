package com.geecity.hisenseplus.home.bean;

import java.io.Serializable;

/**
 * 商户列表
 * 
 * @author Administrator
 * 
 */
public class BusinessListBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 986412624005677456L;
	
    private String address;
    private int sgrade;
	private int id;
	private String sname;
	private String logo;
	private String prop;// 特点，逗号分隔
	private int visits;
	private int star;
	private int sorder;

	public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSgrade() {
        return sgrade;
    }

    public void setSgrade(int sgrade) {
        this.sgrade = sgrade;
    }

    public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getProp() {
		return prop;
	}

	public void setProp(String prop) {
		this.prop = prop;
	}

	public int getVisits() {
		return visits;
	}

	public void setVisits(int visits) {
		this.visits = visits;
	}

	public int getSorder() {
		return sorder;
	}

	public void setSorder(int sorder) {
		this.sorder = sorder;
	}

	@Override
	public String toString() {
		return "BusinessListBean [id=" + id + ", sname=" + sname + ", logo="
				+ logo + ", prop=" + prop + ", visits=" + visits + ", sorder="
				+ sorder + "]";
	}
}
