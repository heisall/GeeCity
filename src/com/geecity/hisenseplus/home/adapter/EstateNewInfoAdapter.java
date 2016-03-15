package com.geecity.hisenseplus.home.adapter;

import java.util.LinkedList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.bean.DiscTypesBean;
import com.geecity.hisenseplus.home.utils.ViewHolder;

public class EstateNewInfoAdapter extends BaseAdapter {

	Context context;
	public LinkedList<DiscTypesBean> itemList;

	public EstateNewInfoAdapter(Context context) {
		this.context = context;
		itemList = new LinkedList<DiscTypesBean>();
	}

	public void setList(LinkedList<DiscTypesBean> ilist) {
		this.itemList = ilist;
	}

	@Override
	public int getCount() {
		return itemList.size();
	}

	@Override
	public DiscTypesBean getItem(int position) {
		return itemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (null == convertView) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_estate_new_info, null);
		}
		TextView selectTv = ViewHolder.get(convertView, R.id.tv_item_info);
		selectTv.setText(itemList.get(position).getMemo());
		TextView icnTv = ViewHolder.get(convertView, R.id.tv_item_pre);
		icnTv.setText(itemList.get(position).getDictValue());
		return convertView;
	}
}
