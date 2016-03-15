package com.geecity.hisenseplus.home.bean;

public class ADBean {
	private int ID;
	private int A_id;
	private String Description;
	private String Photo;
	private String Types;
	private String Content;
	private String url;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getA_id() {
		return A_id;
	}

	public void setA_id(int a_id) {
		A_id = a_id;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getPhoto() {
		return Photo;
	}

	public void setPhoto(String photo) {
		Photo = photo;
	}

	public String getTypes() {
		return Types;
	}

	public void setTypes(String types) {
		Types = types;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "ADBean [ID=" + ID + ", A_id=" + A_id + ", Description="
				+ Description + ", Photo=" + Photo + ", Types=" + Types
				+ ", Content=" + Content + ", url=" + url + "]";
	}
}
