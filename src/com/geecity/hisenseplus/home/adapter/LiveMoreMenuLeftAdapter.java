package com.geecity.hisenseplus.home.adapter;

import java.util.LinkedList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.bean.MallMenuBean;
import com.geecity.hisenseplus.home.utils.ViewHolder;

public class LiveMoreMenuLeftAdapter extends BaseAdapter {

	Context context;
	public LinkedList<MallMenuBean> itemList;

	public LiveMoreMenuLeftAdapter(Context context) {
		this.context = context;
		itemList = new LinkedList<MallMenuBean>();
	}

	public void setList(LinkedList<MallMenuBean> ilist) {
		this.itemList = ilist;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		int count = 0;
		if(itemList != null) count = itemList.size();
		return count;
	}

	@Override
	public MallMenuBean getItem(int position) {
		return itemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (null == convertView) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_live_more_left, null);
		}
		TextView title = ViewHolder.get(convertView, R.id.tv_item_more_left);
		title.setText(itemList.get(position).getCateName());
		if(itemList.get(position).isClicked()){
			title.setBackgroundResource(R.color.txt_white);
			title.setTextColor(context.getResources().getColor(R.color.txt_hisense_green));
		}else{
			title.setBackgroundResource(R.color.bg_black_dark);
			title.setTextColor(context.getResources().getColor(R.color.txt_menu_left));
		}
		return convertView;
	}
}
