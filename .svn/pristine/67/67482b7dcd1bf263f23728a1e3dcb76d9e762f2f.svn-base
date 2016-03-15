package com.geecity.hisenseplus.home.activity.property.model;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.utils.ViewHolder;

/**
 * 物业账单列表1--未缴账单适配器
 * */
public class PropertyP01Adapter extends BaseAdapter {

	Context context;
	public List<BillsInfos> list;
	private boolean isSelectedModel = true;

	public PropertyP01Adapter(Context context) {
		this.context = context;
		list = new ArrayList<BillsInfos>();
	}

	public void setList(List<BillsInfos> list) {
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
	public BillsInfos getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final BillsInfos bills = list.get(position);

		if (null == convertView) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_payment_01_list, null);
		}
		final CheckBox cb = ViewHolder.get(convertView, R.id.cb_item_payment_left);
		TextView date = ViewHolder.get(convertView, R.id.tv_item_payment_date);
		TextView item = ViewHolder.get(convertView, R.id.tv_item_payment_00);
		TextView money = ViewHolder.get(convertView, R.id.tv_item_payment_01);

		date.setText(bills.getT_date());
		item.setText(bills.getT_item());
		money.setText(bills.getPriceForShow());
		if(isSelectedModel){
			cb.setVisibility(View.VISIBLE);
			cb.setChecked(bills.isCheck());
		}else{
			cb.setVisibility(View.INVISIBLE);
		}
		return convertView;
	}

	public void setOnItemSelected(boolean b) {
		this.isSelectedModel  = b;
	}
}