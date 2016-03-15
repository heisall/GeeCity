package com.geecity.hisenseplus.home.adapter;

import java.util.LinkedList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.live.GoodsDetailActivity;
import com.geecity.hisenseplus.home.bean.GoodsListBean;
import com.geecity.hisenseplus.home.bean.GroupBuyBean;
import com.geecity.hisenseplus.home.utils.BitmapAsset;
import com.geecity.hisenseplus.home.utils.ViewHolder;

/**
 * 团购商品列表适配器
 * @author Administrator
 *
 */
public class GroupBuyListAdapter extends BaseAdapter {

	Context context;
	public LinkedList<GroupBuyBean> itemList;

	public GroupBuyListAdapter(Context context) {
		this.context = context;
		itemList = new LinkedList<GroupBuyBean>();
	}

	public void setList(LinkedList<GroupBuyBean> beans) {
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
	public GroupBuyBean getItem(int position) {
	    if(itemList != null && position < itemList.size()){
	        return itemList.get(position);
	    }
		return null;
	}

	@Override
	public long getItemId(int position) {
	    if(itemList != null && position < itemList.size()){
	        return itemList.get(position).getGoods_id();
	    }
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (null == convertView) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_groupbuy_list, null);
		}
		final GroupBuyBean bean = getItem(position);
		RelativeLayout lyout = ViewHolder.get(convertView, R.id.ly_item_groupbuy_list);
		lyout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				launchGoodDetailActivity(bean);
			}
		});
		ImageView icn = ViewHolder.get(convertView, R.id.img_item_groupbuy_logo);
		icn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				launchGoodDetailActivity(bean);
			}
		});
		TextView name = ViewHolder.get(convertView, R.id.tv_item_groupbuy_name);
		name.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				launchGoodDetailActivity(bean);
			}
		});
		TextView price = ViewHolder.get(convertView, R.id.tv_item_groupbuy_prices);
		TextView oldPrice = ViewHolder.get(convertView, R.id.tv_item_groupbuy_old_prices);
		TextView date = ViewHolder.get(convertView, R.id.tv_item_groupbuy_date);
		Button btn =  ViewHolder.get(convertView, R.id.btn_item_groupbuy_buy);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				launchGoodDetailActivity(bean);
			}
		});
		BitmapAsset.displayImg(context, icn, bean.getDefault_image(), R.drawable.icn_community_selected);
		name.setText(bean.getGoods_name());
		price.setText("￥" +bean.getPrice());
		oldPrice.setText("￥" +bean.getOld_price());
		date.setText(bean.getEnd_time());
		return convertView;
	}

	protected void launchGoodDetailActivity(GroupBuyBean bean) {
		if(bean != null){
			GoodsListBean gbean = new GoodsListBean();
			gbean.setGoods_id(bean.getGoods_id());
			gbean.setGoods_name(bean.getGoods_name());
		    Intent intent = new Intent(context, GoodsDetailActivity.class);
		    intent.putExtra(GoodsDetailActivity.KEY_DETAIL_BEAN, gbean);
		    context.startActivity(intent);
		}
	}
}
