package com.geecity.hisenseplus.home.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.bean.MsgListBean;
import com.geecity.hisenseplus.home.utils.ViewHolder;

/**
 * 未缴账单列表
 * */
public class PaymentUnpayAdapter extends BaseAdapter {

  Context context;
  public ArrayList<MsgListBean> list;

  public PaymentUnpayAdapter(Context context) {
    this.context = context;
    list = new ArrayList<MsgListBean>();
  }
  
  public void setArrayList(ArrayList<MsgListBean> list){
    this.list = list;
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
  public MsgListBean getItem(int position) {
    return list.get(position);
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (null == convertView) {
      convertView = LayoutInflater.from(context).inflate(R.layout.item_payment_01_list, null);
    }
    CheckBox cb = ViewHolder.get(convertView, R.id.cb_item_payment_left);
    TextView date = ViewHolder.get(convertView, R.id.tv_item_payment_date);
    
    RelativeLayout rly0 = ViewHolder.get(convertView, R.id.rly_item_payment_0);
    TextView type0 = ViewHolder.get(convertView, R.id.tv_item_payment_00);
    TextView price0 = ViewHolder.get(convertView, R.id.tv_item_payment_01);
    rly0.setVisibility(View.GONE);
    
    RelativeLayout rly1 = ViewHolder.get(convertView, R.id.rly_item_payment_1);
    TextView type1 = ViewHolder.get(convertView, R.id.tv_item_payment_10);
    TextView price1 = ViewHolder.get(convertView, R.id.tv_item_payment_11);
    rly1.setVisibility(View.GONE);
    
    RelativeLayout rly2 = ViewHolder.get(convertView, R.id.rly_item_payment_2);
    TextView type2 = ViewHolder.get(convertView, R.id.tv_item_payment_20);
    TextView price2 = ViewHolder.get(convertView, R.id.tv_item_payment_21);
    rly2.setVisibility(View.GONE);
    
    RelativeLayout rly3 = ViewHolder.get(convertView, R.id.rly_item_payment_3);
    TextView type3 = ViewHolder.get(convertView, R.id.tv_item_payment_30);
    TextView price3 = ViewHolder.get(convertView, R.id.tv_item_payment_31);
    rly3.setVisibility(View.GONE);
    
    RelativeLayout rly4 = ViewHolder.get(convertView, R.id.rly_item_payment_4);
    TextView type4 = ViewHolder.get(convertView, R.id.tv_item_payment_40);
    TextView price4 = ViewHolder.get(convertView, R.id.tv_item_payment_41);
    rly4.setVisibility(View.GONE);
    
    RelativeLayout rly5 = ViewHolder.get(convertView, R.id.rly_item_payment_5);
    TextView type5 = ViewHolder.get(convertView, R.id.tv_item_payment_50);
    TextView price5 = ViewHolder.get(convertView, R.id.tv_item_payment_51);
    rly5.setVisibility(View.GONE);
    
    return convertView;
  }

}
