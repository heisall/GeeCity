package com.geecity.hisenseplus.home.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ShopCarListBean implements Parcelable {

	private int rec_id;// 购物车ID,直接购买的类型没有购物车ID，设置rec_id = -100标识来自直接购买
	private int store_id;// 商户ID
	private String store_name;// 商户名称
	private int goods_id;// 商品ID
	private String goods_name;// 商品名
	// private int spec_id;
	private String goods_image;// 图片
	private int quantity;// 数量
	private double price;
	private int isSelected;
	private int isScore;//自定义字段，用于区别积分商品和普通商品

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public int getRec_id() {
		return rec_id;
	}

	public void setRec_id(int rec_id) {
		this.rec_id = rec_id;
	}

	public int getStore_id() {
		return store_id;
	}

	public void setStore_id(int store_id) {
		this.store_id = store_id;
	}

	public int getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(int goods_id) {
		this.goods_id = goods_id;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	// public int getSpec_id() {
	// return spec_id;
	// }
	//
	// public void setSpec_id(int spec_id) {
	// this.spec_id = spec_id;
	// }

	public boolean isScore() {
		return isScore == 1;
	}

	public void setScore(int isScore) {
		this.isScore = isScore;
	}

	public String getGoods_image() {
		return goods_image;
	}

	public void setGoods_image(String goods_image) {
		this.goods_image = goods_image;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int isSelected() {
		return isSelected;
	}

	public void setSelected(int isOrNo) {
		this.isSelected = isOrNo;
	}

	@SuppressWarnings("unchecked")
	private ShopCarListBean(Parcel pr) {
		rec_id = pr.readInt();
		store_id = pr.readInt();
		goods_id = pr.readInt();
		goods_name = pr.readString();
		goods_image = pr.readString();
		quantity = pr.readInt();
		price = pr.readDouble();
		isSelected = pr.readInt();
		isScore = pr.readInt();
	}

	public ShopCarListBean() {
		// TODO Auto-generated constructor stub
	}

	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(rec_id);
		dest.writeInt(store_id);
		dest.writeInt(goods_id);
		dest.writeString(goods_name);
		dest.writeString(goods_image);
		dest.writeInt(quantity);
		dest.writeDouble(price);
		dest.writeInt(isSelected);
		dest.writeInt(isScore);
	}

	public static final Parcelable.Creator<ShopCarListBean> CREATOR = new Parcelable.Creator<ShopCarListBean>() {
		public ShopCarListBean createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new ShopCarListBean(source);
		}

		public ShopCarListBean[] newArray(int size) {
			// TODO Auto-generated method stub
			return new ShopCarListBean[size];
		}
	};

	@Override
	public String toString() {
		return "ShopCarListBean [rec_id=" + rec_id + ", store_id=" + store_id
				+ ", goods_id=" + goods_id + ", goods_name=" + goods_name
				+ ", goods_image=" + goods_image + ", quantity=" + quantity
				+ ", price=" + price + ", isSelected=" + isSelected
				+ ", isScore=" + isScore + "]";
	}

}
