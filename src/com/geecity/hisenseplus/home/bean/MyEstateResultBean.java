package com.geecity.hisenseplus.home.bean;

import java.util.LinkedList;

public class MyEstateResultBean extends BaseBean{
    private LinkedList<MyEstateBean> data;

    public LinkedList<MyEstateBean> getData() {
        return data;
    }

    public void setData(LinkedList<MyEstateBean> data) {
        this.data = data;
    }
}
