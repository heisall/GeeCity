package com.geecity.hisenseplus.home.bean;

import java.util.LinkedList;

public class MyOrderCompResultBean extends BaseBean {
    private MyOrderCountBean orderCount;
    private LinkedList<MyOrderCompListParentBean> orders;

    public LinkedList<MyOrderCompListParentBean> getOrders() {
        return orders;
    }

    public void setOrders(LinkedList<MyOrderCompListParentBean> orders) {
        this.orders = orders;
    }

    public MyOrderCountBean getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(MyOrderCountBean orderCount) {
        this.orderCount = orderCount;
    }

}
