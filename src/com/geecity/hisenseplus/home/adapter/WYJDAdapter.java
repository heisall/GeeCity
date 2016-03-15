package com.geecity.hisenseplus.home.adapter;

import java.util.LinkedList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.bean.WyjdEmployerBean;
import com.geecity.hisenseplus.home.utils.BitmapAsset;
import com.geecity.hisenseplus.home.utils.CommonTools;
import com.geecity.hisenseplus.home.utils.ViewHolder;
import com.geecity.hisenseplus.home.view.RoundImageView;

/**
 * 物业监督--物业员工列表
 * */
public class WYJDAdapter extends BaseAdapter {

  Context context;
  public LinkedList<WyjdEmployerBean> list;

  public WYJDAdapter(Context context) {
    this.context = context;
    list = new LinkedList<WyjdEmployerBean>();
  }
  
  public void setArrayList(LinkedList<WyjdEmployerBean> mSupervises){
    this.list = mSupervises;
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
  public WyjdEmployerBean getItem(int position) {
    return list.get(position);
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (null == convertView) {
      convertView = LayoutInflater.from(context).inflate(R.layout.item_wyjd_list, null);
    }
    RoundImageView head = ViewHolder.get(convertView, R.id.item_wuye_jiandu_head);
    TextView name = ViewHolder.get(convertView, R.id.item_wuye_jiandu_name);
    TextView post = ViewHolder.get(convertView, R.id.item_wuye_jiandu_zhiwei);
    TextView line = ViewHolder.get(convertView, R.id.line);
    final Button phone = ViewHolder.get(convertView, R.id.item_wuye_jiandu_phone);
    final WyjdEmployerBean bean = list.get(position);
    phone.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			CommonTools.launchCall(context, bean.getPhone());
		}
	});
    BitmapAsset.displayImg(context, head, bean.getPhoto(), R.drawable.icon_default_head);
    name.setText(bean.getName());
    post.setText("职位："+bean.getPosition());
    phone.setText(bean.getPhone());
    
    if(position == list.size() - 1){
        line.setVisibility(View.INVISIBLE);
    }else{
        line.setVisibility(View.VISIBLE);
    }
    
    return convertView;
  }

}
