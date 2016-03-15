package com.geecity.hisenseplus.home.bean;

import java.io.Serializable;

public class NoticeListBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String title;
	private String description;
	private String content;
	private int isUrl;
	private int types;
	private String addTime;
	private int ClickCount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getIsUrl() {
		return isUrl;
	}

	public void setIsUrl(int isUrl) {
		this.isUrl = isUrl;
	}

	public int getTypes() {
		return types;
	}

	public void setTypes(int types) {
		this.types = types;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public int getClickCount() {
		return ClickCount;
	}

	public void setClickCount(int clickCount) {
		ClickCount = clickCount;
	}
}
