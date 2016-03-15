package com.geecity.hisenseplus.home.bean;

import java.io.Serializable;


public class MessageDetail implements Serializable{
	 private static final long serialVersionUID = 986412624005677456L;
   private String id;
   private String a_id;
   private String Memo;
   private String content;
   private String Photo;
   private String AppID;
   private String LeaveWordCount;
   private String ClickPraiseCount;
   private String ShareQQCount;
   private String ShareWXCount; 
    public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getA_id() {
	return a_id;
}
public void setA_id(String a_id) {
	this.a_id = a_id;
}
public String getMemo() {
	return Memo;
}
public void setMemo(String memo) {
	Memo = memo;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public String getPhoto() {
	return Photo;
}
public void setPhoto(String photo) {
	Photo = photo;
}
public String getAppID() {
	return AppID;
}
public void setAppID(String appID) {
	AppID = appID;
}
public String getLeaveWordCount() {
	return LeaveWordCount;
}
public void setLeaveWordCount(String leaveWordCount) {
	LeaveWordCount = leaveWordCount;
}
public String getClickPraiseCount() {
	return ClickPraiseCount;
}
public void setClickPraiseCount(String clickPraiseCount) {
	ClickPraiseCount = clickPraiseCount;
}
public String getShareQQCount() {
	return ShareQQCount;
}
public void setShareQQCount(String shareQQCount) {
	ShareQQCount = shareQQCount;
}
public String getShareWXCount() {
	return ShareWXCount;
}
public void setShareWXCount(String shareWXCount) {
	ShareWXCount = shareWXCount;
}
public String getReleaseTime() {
	return ReleaseTime;
}
public void setReleaseTime(String releaseTime) {
	ReleaseTime = releaseTime;
}
	private String ReleaseTime;
	@Override
	public String toString() {
	    return "DiscListBean [id=" + id + ", a_id=" + a_id + ", Memo=" + Memo + ", content="
	                    + content + ", Photo=" + Photo + ", AppID=" + AppID + ", LeaveWordCount="
	                    + LeaveWordCount + ", ClickPraiseCount=" + ClickPraiseCount
	                    + ", ShareQQCount=" + ShareQQCount + ", ShareWXCount=" + ShareWXCount
	                    + ", ReleaseTime=" + ReleaseTime + "]";
	}
}
