package com.geecity.hisenseplus.home.adapter;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.utils.BitmapAsset;
import com.geecity.hisenseplus.home.utils.ViewHolder;

public class GoodsDetailPicsAdapter extends BaseAdapter {

	Context context;
	public List<String> itemList;

	public GoodsDetailPicsAdapter(Context context) {
		this.context = context;
		itemList = new LinkedList<String>();
	}

	public void setList(List<String> pics) {
		this.itemList = pics;
	}

	@Override
	public int getCount() {
	    int count = 0;
	    if(itemList != null) count = itemList.size();
	    return count;
	}

	@Override
	public String getItem(int position) {
	    if(getCount() == 0) return "";
		return itemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (null == convertView) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_goods_pic_list, null);
		}
		ImageView selectTv = ViewHolder.get(convertView, R.id.pic_item);
		BitmapAsset.displayImg(context, selectTv, itemList.get(position), R.drawable.icn_community_selected);
		return convertView;
	}
}
