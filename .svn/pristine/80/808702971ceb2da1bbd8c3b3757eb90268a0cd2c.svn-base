package com.geecity.hisenseplus.home.adapter;

import java.util.LinkedList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.bean.MyEstateBean;
import com.geecity.hisenseplus.home.utils.ViewHolder;

/**
 * 我的房产列表
 * */
public class MyEstateListAdpter extends BaseAdapter {

    Context context;
    public LinkedList<MyEstateBean> list;

    public MyEstateListAdpter(Context context) {
        this.context = context;
        list = new LinkedList<MyEstateBean>();
    }

    public void setArrayList(LinkedList<MyEstateBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        int count = 0;
        if (list != null) {
            count = list.size();
        }
        return count;
    }

    @Override
    public MyEstateBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_my_estate_list, null);
        }
        TextView building = ViewHolder.get(convertView, R.id.item_my_estate_building);
        TextView detail = ViewHolder.get(convertView, R.id.item_my_estate_detail);
        ImageView icn = ViewHolder.get(convertView, R.id.imageView1);
        building.setText(list.get(position).getA_name());
        detail.setText(list.get(position).getB_name() + list.get(position).getR_dy() +"单元"+ list.get(position).getR_id());
        icn.setVisibility(list.get(position).getIsDefault() == 1?View.VISIBLE:View.INVISIBLE);
        return convertView;
    }

}
