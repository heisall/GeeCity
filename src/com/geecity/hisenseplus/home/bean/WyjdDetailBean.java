package com.geecity.hisenseplus.home.bean;

import java.util.LinkedList;

public class WyjdDetailBean extends BaseBean {
	private WyjdWorkListBean work;
	private LinkedList<CommentBean> comments;

	public WyjdWorkListBean getWork() {
		return work;
	}

	public void setWork(WyjdWorkListBean work) {
		this.work = work;
	}

	public LinkedList<CommentBean> getSupervise() {
		return comments;
	}

	public void setSupervise(LinkedList<CommentBean> comments) {
		this.comments = comments;
	}

}
