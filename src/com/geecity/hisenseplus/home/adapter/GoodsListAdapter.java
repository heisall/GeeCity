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
import com.geecity.hisenseplus.home.bean.GoodsListBean;
import com.geecity.hisenseplus.home.utils.BitmapAsset;
import com.geecity.hisenseplus.home.utils.ViewHolder;

/**
 * 商户下所有商品列表适配器
 * @author Administrator
 *
 */
public class GoodsListAdapter extends BaseAdapter {

	Context context;
	public LinkedList<GoodsListBean> itemList;

	public GoodsListAdapter(Context context) {
		this.context = context;
		itemList = new LinkedList<GoodsListBean>();
	}

	public void setList(LinkedList<GoodsListBean> beans) {
		this.itemList = beans;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
	    int count = 0;
	    if(itemList != null) count = itemList.size();
		return count;
	}

	@Override
	public GoodsListBean getItem(int position) {
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
					R.layout.item_business_merch_list, null);
		}
		ImageView icn = ViewHolder.get(convertView, R.id.img_item_bm_logo);
		TextView name = ViewHolder.get(convertView, R.id.tv_item_bm_name);
		TextView date = ViewHolder.get(convertView, R.id.tv_item_bm_date);
		TextView price = ViewHolder.get(convertView, R.id.tv_item_bm_price);
		TextView views = ViewHolder.get(convertView, R.id.tv_item_bm_views);
		GoodsListBean bean = getItem(position);
		BitmapAsset.displayImg(context, icn, bean.getDefault_image(), R.drawable.icn_community_selected);
		name.setText(bean.getGoods_name());
//		date.setText("市场价：￥" +bean.getOld_price());
		//TODO 没有日期字段，暂不显示
		date.setText("");
		price.setText("￥" +bean.getPrice());
		views.setText("查看人数 ："+bean.getViews());
		return convertView;
	}
}
