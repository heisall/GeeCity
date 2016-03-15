package com.geecity.hisenseplus.home.bean;

import java.util.LinkedList;

/**
 * 房屋添加条件列表
 * 
 * @author Administrator
 * 
 */
public class EstateFilterResultBean {

    private LinkedList<EstateFilterBean> lp;
    private LinkedList<EstateFilterBean> cx;
    private LinkedList<SupportingBean> md;
    private LinkedList<EstateFilterBean> qy;
    private LinkedList<EstateFilterBean> zx;
    private LinkedList<EstateFilterBean> fkfs;

    public LinkedList<EstateFilterBean> getFkfs() {
		return fkfs;
	}

	public void setFkfs(LinkedList<EstateFilterBean> fkfs) {
		this.fkfs = fkfs;
	}

	public LinkedList<EstateFilterBean> getLp() {
        return lp;
    }

    public void setLp(LinkedList<EstateFilterBean> lp) {
        this.lp = lp;
    }

    public LinkedList<EstateFilterBean> getCx() {
        return cx;
    }

    public void setCx(LinkedList<EstateFilterBean> cx) {
        this.cx = cx;
    }

    public LinkedList<SupportingBean> getMd() {
        return md;
    }

    public void setMd(LinkedList<SupportingBean> md) {
        this.md = md;
    }

    public LinkedList<EstateFilterBean> getQy() {
        return qy;
    }

    public void setQy(LinkedList<EstateFilterBean> qy) {
        this.qy = qy;
    }

    public LinkedList<EstateFilterBean> getZx() {
        return zx;
    }

    public void setZx(LinkedList<EstateFilterBean> zx) {
        this.zx = zx;
    }

}
