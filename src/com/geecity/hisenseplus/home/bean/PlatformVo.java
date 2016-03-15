package com.geecity.hisenseplus.home.bean;

/**
 * 分享平台列表
 * @ClassName: PlatformVo 
 * @Description: TODO 
 * @author shimy
 * @date 2015-12-16 上午10:05:49 
 *
 */
public class PlatformVo {

	private int id;//平台的id，自定义
	private String platform;
	private String shareParams;//分享到平台的参数
	private String name;//分享平台的名字
	private int resId;//分享平台的logo
	
	public int getResId() {
		return resId;
	}
	public void setResId(int resId) {
		this.resId = resId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getShareParams() {
		return shareParams;
	}
	public void setShareParams(String shareParams) {
		this.shareParams = shareParams;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
