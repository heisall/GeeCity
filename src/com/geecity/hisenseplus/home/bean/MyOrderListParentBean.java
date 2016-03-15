package com.geecity.hisenseplus.home.bean;

import java.util.LinkedList;

public class MyOrderListParentBean {
    private int order_id;//订单ID
    private String order_sn;//订单编号
    private String add_time;//添加时间
    private String seller_name;//商户名称
    private double goods_amount;//
    private double shipping_fee;// 运费
    private double order_amount;//订单总价
    private String payment_name;//
    private String store_logo;
    private int status;
    private LinkedList<MyOrderListBean> detail;
//    private double price;

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

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public double getGoods_amount() {
        return goods_amount;
    }

    public void setGoods_amount(double goods_amount) {
        this.goods_amount = goods_amount;
    }

    public double getShipping_fee() {
        return shipping_fee;
    }

    public void setShipping_fee(double shipping_fee) {
        this.shipping_fee = shipping_fee;
    }

    public double getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(double order_amount) {
        this.order_amount = order_amount;
    }

    public String getPayment_name() {
        return payment_name;
    }

    public void setPayment_name(String payment_name) {
        this.payment_name = payment_name;
    }

    public String getStore_logo() {
        return store_logo;
    }

    public void setStore_logo(String store_logo) {
        this.store_logo = store_logo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LinkedList<MyOrderListBean> getDetail() {
        return detail;
    }

    public void setDetail(LinkedList<MyOrderListBean> detail) {
        this.detail = detail;
    }

//    public double getPrice() {
//        return price;
//    }
//
//    public void setPrice(double price) {
//        this.price = price;
//    }

}
