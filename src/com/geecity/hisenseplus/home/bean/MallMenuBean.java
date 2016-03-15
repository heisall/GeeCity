package com.geecity.hisenseplus.home.bean;

import java.io.Serializable;

public class MallMenuBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3714049482322518399L;
	private int id;
	private int parent_id;
	private String thumb;
	private String cateName;
	private int sort_order;
	private boolean isClicked;

	public int getParent_id() {
		return parent_id;
	}

	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}

	public boolean isClicked() {
		return isClicked;
	}

	public void setClicked(boolean isClicked) {
		this.isClicked = isClicked;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public int getSort_order() {
		return sort_order;
	}

	public void setSort_order(int sort_order) {
		this.sort_order = sort_order;
	}

	@Override
	public String toString() {
		return "MallMenuBean [id=" + id + ", parent_id=" + parent_id
				+ ", thumb=" + thumb + ", cateName=" + cateName
				+ ", sort_order=" + sort_order + ", isClicked=" + isClicked
				+ "]";
	}
}
