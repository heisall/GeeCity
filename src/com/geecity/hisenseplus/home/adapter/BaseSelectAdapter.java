package com.geecity.hisenseplus.home.adapter;

import java.util.LinkedList;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.bean.DiscTypesBean;
import com.geecity.hisenseplus.home.utils.BitmapAsset;
import com.geecity.hisenseplus.home.utils.ViewHolder;

public class BaseSelectAdapter extends BaseAdapter {

	Context context;
	private boolean isShowIcn = false; // 是否显示icn
	public LinkedList<DiscTypesBean> itemList;

	public BaseSelectAdapter(Context context) {
		this.context = context;
		itemList = new LinkedList<DiscTypesBean>();
	}

	public BaseSelectAdapter(Context context, boolean showIcn) {
		this(context);
		this.isShowIcn = showIcn;
	}

	public void setList(LinkedList<DiscTypesBean> ilist) {
		this.itemList = ilist;
	}

	@Override
	public int getCount() {
		int count = 0;
		if(itemList != null) count = itemList.size();
		return count;
	}

	@Override
	public DiscTypesBean getItem(int position) {
		return itemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		String value = getItem(position).getDictValue().replace(".0", "");
		if(TextUtils.isEmpty(value)){
			return 0L;
		}
		return Long.parseLong(value);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (null == convertView) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_select_comm, null);
		}
		TextView selectTv = ViewHolder.get(convertView, R.id.tv_comm_item);
		selectTv.setText(itemList.get(position).getMemo());
		TextView icnTv = ViewHolder.get(convertView, R.id.tv_item_select_pre);
		icnTv.setVisibility(isShowIcn ? View.VISIBLE : View.INVISIBLE);

		String type = itemList.get(position).getDictValue().replace(".0", "");
		if (isShowIcn) {
		    icnTv.setBackgroundResource(BitmapAsset.convertBg(type));
		}

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
