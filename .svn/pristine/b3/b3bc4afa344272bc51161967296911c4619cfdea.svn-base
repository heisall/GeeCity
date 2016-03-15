package com.geecity.hisenseplus.home.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.bean.HouseBean;
import com.geecity.hisenseplus.home.utils.ViewHolder;

/**
 * 房源绑定
 * */
public class HouseBindAdapter extends BaseAdapter {

  Context context;
  public ArrayList<HouseBean> list;

  public HouseBindAdapter(Context context) {
    this.context = context;
    list = new ArrayList<HouseBean>();
  }
  
  public void setArrayList(ArrayList<HouseBean> list){
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
  public HouseBean getItem(int position) {
    return list.get(position);
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (null == convertView) {
      convertView = LayoutInflater.from(context).inflate(R.layout.item_house_bind_list, null);
    }
    TextView selectTv = ViewHolder.get(convertView, R.id.item_house_bind_title);
    selectTv.setText(list.get(position).getA_name());
    return convertView;
  }

}
