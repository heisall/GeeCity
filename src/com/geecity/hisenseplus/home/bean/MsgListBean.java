package com.geecity.hisenseplus.home.bean;

import java.io.Serializable;

public class MsgListBean implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private int id;// ID
	private int fromid;// ID
	private String msg;// 标题（标题中涵盖消息类型，由后台配置）
	private String type;// 区分帖子来源(find：发现的评论、公告发布消息、通知)
	private String title;// 内容
	private String sendTime;// 发布时间

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFromid() {
		return fromid;
	}

	public void setFromid(int fromid) {
		this.fromid = fromid;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

}
