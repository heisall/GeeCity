package com.geecity.hisenseplus.home.bean;

import java.io.Serializable;
import java.util.LinkedList;

import android.text.TextUtils;

import com.geecity.hisenseplus.home.GCApplication;

/**
 * 发现分类列表
 * 
 * @author Administrator
 * 
 */
public class DiscTypesBean implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 986412624005677456L;
    //"ID":9,"DictKey":"find_type","DictValue":"1","Memo":"社区分享","Fatherid":null
    private String dictValue;
    private String memo;

    public DiscTypesBean(String id, String name) {
        super();
        this.dictValue = id;
        this.memo = name;
    }

    public String getDictValue() {
        return dictValue.replace(".0", "");
    }

    public void setDictValue(String id) {
        this.dictValue = id;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String name) {
        this.memo = name;
    }

	@Override
	public String toString() {
		return "DiscTypesBean [DictValue=" + dictValue + ", Memo=" + memo + "]";
	}
    
    public static String getCatogeryType(String memo) {
        LinkedList<DiscTypesBean> beans = GCApplication.sApp.getDiscTypes();
        if(TextUtils.isEmpty(memo) || beans == null || beans.size() == 0){
        	return null;
        }
        for (DiscTypesBean discTypesBean : beans) {
            if(memo.equals(discTypesBean.getMemo())){
                return discTypesBean.getDictValue();
            }
        }
        return null;
    }
}
