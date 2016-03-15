package com.geecity.hisenseplus.home.adapter;

import java.util.LinkedList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.bean.SupportingBean;
import com.geecity.hisenseplus.home.utils.ViewHolder;

/**
 * 房屋租赁详情配套设施列表
 * */
public class EstateRentSubAdapter extends BaseAdapter {

	Context context;
	public LinkedList<SupportingBean> list;

	public EstateRentSubAdapter(Context context) {
		this.context = context;
		list = new LinkedList<SupportingBean>();
	}

	public void setArrayList(LinkedList<SupportingBean> adpteBeans) {
		this.list = adpteBeans;
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
	public SupportingBean getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (null == convertView) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_estate_rent_sub, null);
		}
		TextView title = ViewHolder.get(convertView, R.id.tv_item_sub);
		SupportingBean bean = list.get(position);
		if(bean != null){
			title.setText(bean.getDicValue());
			if(bean.getIsSup() == 1){
				title.setBackgroundResource(R.drawable.icn_estate_rent_sub);
				title.setTextColor(context.getResources().getColor(R.color.txt_white));
			}else{
				title.setBackgroundResource(R.drawable.icn_estate_rent_unsub);
				title.setTextColor(context.getResources().getColor(R.color.txt_hint_gray));
			}
		}
		return convertView;
	}
}
