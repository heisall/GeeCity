package com.geecity.hisenseplus.home.utils;

public class CYTJHelper {
	private int index; // 索引(唯一性),方便图片压缩列表与 gridview.list列表中值对应
	private int type; // //类别 0为 id/1为bit/2为path(本地手机相册地址) /4表示网络图片地址
	private Object imageInfo; // 如果type为0,此值为数字,如果type为bit,则此类型为裁剪后的Bitmap,如果type为path,则此值为地址,用于显示提交创意gridView里面的图片
	private String imageStream; // 图片所对应的64进制

	public String getImageStream() {
		return imageStream;
	}

	public void setImageStream(String imageStream) {
		this.imageStream = imageStream;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Object getImageInfo() {
		return imageInfo;
	}

	public void setImageInfo(Object imageInfo) {
		this.imageInfo = imageInfo;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
