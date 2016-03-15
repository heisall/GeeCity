package com.geecity.hisenseplus.home.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.bean.ShopCarListBean;
import com.geecity.hisenseplus.home.utils.BitmapAsset;
import com.geecity.hisenseplus.home.utils.ViewHolder;

/**
 * 订单商品列表适配器
 * @author Administrator
 *
 */
public class OrderGoodsListAdapter extends BaseAdapter {

	Context context;
	public ArrayList<ShopCarListBean> itemList;

	public OrderGoodsListAdapter(Context context) {
		this.context = context;
		itemList = new ArrayList<ShopCarListBean>();
	}

	public void setList(ArrayList<ShopCarListBean> spcs) {
		this.itemList = spcs;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
	    int count = 4;
	    if(itemList != null) count = itemList.size();
		return count;
	}

	@Override
	public ShopCarListBean getItem(int position) {
	    if(itemList != null && position < itemList.size()){
	        return itemList.get(position);
	    }
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (null == convertView) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_order_goods_list, null);
		}
		ImageView icn = ViewHolder.get(convertView, R.id.img_item_bm_logo);
		TextView name = ViewHolder.get(convertView, R.id.tv_item_bm_name);
		TextView price = ViewHolder.get(convertView, R.id.tv_item_order_list_price);
		TextView count = ViewHolder.get(convertView, R.id.tv_item_order_list_count);
		
		ShopCarListBean bean = getItem(position);
		BitmapAsset.displayImg(context, icn, bean.getGoods_image(), R.drawable.icn_community_selected);
		name.setText(bean.getGoods_name());
		price.setText(String.valueOf(bean.getPrice()));
		count.setText("x " +bean.getQuantity());
		return convertView;
	}
}
