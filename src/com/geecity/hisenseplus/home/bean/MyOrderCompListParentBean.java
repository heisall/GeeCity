package com.geecity.hisenseplus.home.bean;

import java.io.Serializable;


public class MyOrderCompListParentBean implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int order_id;// 订单ID
    private String order_sn;// 订单编号
    private String add_time;// 添加时间
    private int seller_id;// 商家ID
    private String seller_name;// 商户名称
    private int goods_id;// 商品ID
    private String goods_name;// 商品名
    private int quantity;// 数量
    private String goods_image;
    private double price;
    private boolean e_status;// 商品是否评价

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public int getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(int seller_id) {
        this.seller_id = seller_id;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getGoods_image() {
        return goods_image;
    }

    public void setGoods_image(String goods_image) {
        this.goods_image = goods_image;
    }


    public boolean isE_status() {
        return e_status;
    }

    public void setE_status(boolean e_status) {
        this.e_status = e_status;
    }

    @Override
    public String toString() {
        return "MyOrderCompListParentBean [order_id=" + order_id + ", order_sn=" + order_sn
                        + ", add_time=" + add_time + ", seller_id=" + seller_id + ", seller_name="
                        + seller_name + ", goods_id="
                        + goods_id + ", goods_name=" + goods_name + ", quantity=" + quantity
                        + ", goods_image=" + goods_image + ", price=" + price + ", e_status="
                        + e_status + "]";
    }
}
