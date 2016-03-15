package com.geecity.hisenseplus.home.bean;

import java.util.LinkedList;

public class HobbyBean extends BaseBean {
    private LinkedList<HobbyData> data;

    public LinkedList<HobbyData> getData() {
        return data;
    }

    public void setData(LinkedList<HobbyData> data) {
        this.data = data;
    }
}
