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
import com.geecity.hisenseplus.home.activity.property.model.BillsInfos;
import com.geecity.hisenseplus.home.utils.ViewHolder;

/**
 * 物业账单列表1--全部账单适配器
 * */
public class PropertyP1Adapter extends BaseAdapter {

	Context context;
	public LinkedList<BillsInfos> list;

	public PropertyP1Adapter(Context context) {
		this.context = context;
		list = new LinkedList<BillsInfos>();
	}

	public void setArrayList(LinkedList<BillsInfos> list) {
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
	public BillsInfos getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (null == convertView) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_payment_1_list, null);
		}
		TextView date = ViewHolder.get(convertView, R.id.tv_item_payment_date);
		ImageView icn = ViewHolder.get(convertView, R.id.img_payment_unpay);
		BillsInfos bean = list.get(position);
		date.setText(bean.getT_date());
		icn.setVisibility(bean.getT_sign() == 0 ? View.VISIBLE : View.INVISIBLE);
		TextView tv00 = ViewHolder.get(convertView, R.id.tv_item_payment_00);
		tv00.setText(bean.getT_item());
		TextView tv02 = ViewHolder.get(convertView, R.id.tv_item_payment_02);
		tv02.setText(bean.getPriceForShow());
		return convertView;
	}

}
