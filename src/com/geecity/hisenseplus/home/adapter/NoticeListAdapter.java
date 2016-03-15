package com.geecity.hisenseplus.home.adapter;

import java.util.LinkedList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.bean.NoticeListBean;
import com.geecity.hisenseplus.home.utils.ViewHolder;

/**
 *  公告列表
 * */
public class NoticeListAdapter extends BaseAdapter {

  Context context;
  public LinkedList<NoticeListBean> list;

  public NoticeListAdapter(Context context) {
    this.context = context;
    list = new LinkedList<NoticeListBean>();
  }
  
  public void setArrayList(LinkedList<NoticeListBean> mList){
    this.list = mList;
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
  public NoticeListBean getItem(int position) {
    return list.get(position);
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (null == convertView) {
      convertView = LayoutInflater.from(context).inflate(R.layout.item_notice_list, null);
    }
    TextView tvTitle = ViewHolder.get(convertView, R.id.item_notice_tv_title);
    TextView tvDate = ViewHolder.get(convertView, R.id.item_notice_tv_date);
    TextView tvContent = ViewHolder.get(convertView, R.id.item_notice_tv_content);
    TextView tvCount = ViewHolder.get(convertView, R.id.item_notice_tv_count);
    tvTitle.setText(list.get(position).getTitle());
    tvDate.setText(list.get(position).getAddTime());
    tvContent.setText(list.get(position).getDescription());
    tvCount.setText(list.get(position).getClickCount()+"");
    return convertView;
  }

}
