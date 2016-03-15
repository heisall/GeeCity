package com.geecity.hisenseplus.home.adapter;

import java.util.LinkedList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.bean.EstateFilterBean;
import com.geecity.hisenseplus.home.utils.ViewHolder;

public class EstateFilterAdapter extends BaseAdapter {

	Context context;
	private boolean isShowIcn = false; // 是否显示icn
	public LinkedList<EstateFilterBean> itemList;

	public EstateFilterAdapter(Context context) {
		this.context = context;
		itemList = new LinkedList<EstateFilterBean>();
	}

	public EstateFilterAdapter(Context context, boolean showIcn) {
		this(context);
		this.isShowIcn = showIcn;
	}

	public void setList(LinkedList<EstateFilterBean> ilist) {
		this.itemList = ilist;
	}

	@Override
	public int getCount() {
		return itemList.size();
	}

	@Override
	public EstateFilterBean getItem(int position) {
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
					R.layout.item_select_comm, null);
		}
		TextView selectTv = ViewHolder.get(convertView, R.id.tv_comm_item);
		selectTv.setText(itemList.get(position).getDicValue());
		TextView icnTv = ViewHolder.get(convertView, R.id.tv_item_select_pre);
		icnTv.setVisibility(isShowIcn ? View.VISIBLE : View.INVISIBLE);


		// //改变文字颜色
		// if (selectConditon[clickFlag - 1].replace(",", "").equals(
		// selectTv.getText().toString())) {
		// selectTv.setTextColor(getResources().getColor(R.color.orange2));
		// } else {
		// selectTv.setTextColor(getResources()
		// .getColor(R.color.textcolor));
		// }
		return convertView;
	}
}
