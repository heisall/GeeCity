package com.geecity.hisenseplus.home.bean;

import java.util.LinkedList;

public class WyjdHomeBean extends BaseBean {
	private LinkedList<WyjdWorkListBean> work;
	private LinkedList<WyjdEmployerBean> supervise;

	public LinkedList<WyjdWorkListBean> getWork() {
		return work;
	}

	public void setWork(LinkedList<WyjdWorkListBean> work) {
		this.work = work;
	}

	public LinkedList<WyjdEmployerBean> getSupervise() {
		return supervise;
	}

	public void setSupervise(LinkedList<WyjdEmployerBean> supervise) {
		this.supervise = supervise;
	}

}
