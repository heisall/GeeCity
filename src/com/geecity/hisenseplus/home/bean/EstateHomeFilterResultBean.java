package com.geecity.hisenseplus.home.bean;

import java.util.LinkedList;

/**
 * 房屋首页筛选条件
 * 
 * @author Administrator
 * 
 */
public class EstateHomeFilterResultBean {

    // 二手房
    private LinkedList<EstateFilterBean> square;
    private LinkedList<EstateFilterBean> price;
    private LinkedList<EstateFilterBean> district;
    private LinkedList<EstateFilterBean> style;

    // 租房
    private LinkedList<EstateFilterBean> area;
    private LinkedList<EstateFilterBean> hx;
    private LinkedList<EstateFilterBean> zujin;
    private LinkedList<EstateFilterBean> fkfs;

    public LinkedList<EstateFilterBean> getSquare() {
        return square;
    }

    public void setSquare(LinkedList<EstateFilterBean> square) {
        this.square = square;
    }

    public LinkedList<EstateFilterBean> getPrice() {
        return price;
    }

    public void setPrice(LinkedList<EstateFilterBean> price) {
        this.price = price;
    }

    public LinkedList<EstateFilterBean> getDistrict() {
        return district;
    }

    public void setDistrict(LinkedList<EstateFilterBean> district) {
        this.district = district;
    }

    public LinkedList<EstateFilterBean> getStyle() {
        return style;
    }

    public void setStyle(LinkedList<EstateFilterBean> style) {
        this.style = style;
    }

    public LinkedList<EstateFilterBean> getArea() {
        return area;
    }

    public void setArea(LinkedList<EstateFilterBean> area) {
        this.area = area;
    }

    public LinkedList<EstateFilterBean> getHx() {
        return hx;
    }

    public void setHx(LinkedList<EstateFilterBean> hx) {
        this.hx = hx;
    }

    public LinkedList<EstateFilterBean> getZujin() {
        return zujin;
    }

    public void setZujin(LinkedList<EstateFilterBean> zujin) {
        this.zujin = zujin;
    }

    public LinkedList<EstateFilterBean> getFkfs() {
        return fkfs;
    }

    public void setFkfs(LinkedList<EstateFilterBean> fkfs) {
        this.fkfs = fkfs;
    }


}
