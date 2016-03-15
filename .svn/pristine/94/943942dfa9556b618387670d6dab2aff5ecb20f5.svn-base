package com.geecity.hisenseplus.home.adapter;

import java.util.LinkedList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.bean.MsgListBean;
import com.geecity.hisenseplus.home.utils.ViewHolder;

/**
 * 我的消息列表
 * */
public class MsgAdapter extends BaseAdapter {

  Context context;
  public LinkedList<MsgListBean> list;

  public MsgAdapter(Context context) {
    this.context = context;
    list = new LinkedList<MsgListBean>();
  }
  
  public void setList(LinkedList<MsgListBean> list){
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
      convertView = LayoutInflater.from(context).inflate(R.layout.item_msg_list, null);
    }
    TextView title = ViewHolder.get(convertView, R.id.item_msg_tv_title);
    TextView content = ViewHolder.get(convertView, R.id.item_msg_tv_content);
    TextView date = ViewHolder.get(convertView, R.id.item_msg_tv_date);
    MsgListBean bean = list.get(position);
    title.setText(bean.getMsg());
    content.setText(bean.getTitle());
    date.setText(bean.getSendTime());
    return convertView;
  }

}
