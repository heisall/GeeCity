package com.geecity.hisenseplus.home.adapter;

import java.util.LinkedList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.live.GoodsDetailActivity;
import com.geecity.hisenseplus.home.bean.GoodsListBean;
import com.geecity.hisenseplus.home.bean.GroupBuyBean;
import com.geecity.hisenseplus.home.utils.BitmapAsset;
import com.geecity.hisenseplus.home.utils.ViewHolder;

/**
 * 积分列表适配器
 * @author Administrator
 *
 */
public class ScoreExchangeListAdapter extends BaseAdapter {

	Context context;
	public LinkedList<GroupBuyBean> itemList;

	public ScoreExchangeListAdapter(Context context) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_score_change_list, null);
		}

		RelativeLayout layoutLeft = ViewHolder.get(convertView, R.id.item_score_layout_left);
		RelativeLayout layoutRight = ViewHolder.get(convertView, R.id.item_score_layout_right);
		LinearLayout layoutRightWide = ViewHolder.get(convertView, R.id.item_score_layout_wide_right);
		final GroupBuyBean beanLeft = itemList.get(position * 2);
		ImageView imagLeft = ViewHolder.get(convertView, R.id.item_score_img_left);
		BitmapAsset.displayRec(context, imagLeft, beanLeft.getDefault_image());

		TextView titleLeft = ViewHolder.get(convertView, R.id.item_score_tv_title_left);
		titleLeft.setText(beanLeft.getGoods_name());

		TextView scjLeft = ViewHolder.get(convertView, R.id.item_score_tv_scj);
		scjLeft.setText("市场价：￥"+beanLeft.getOld_price());
		TextView scoreLeft = ViewHolder.get(convertView, R.id.item_score_tv_score_left);
		scoreLeft.setText("所需积分："+beanLeft.getPrice());

		layoutLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				launchGoodDetailActivity(beanLeft);
			}
		});

		if (position * 2 + 1 > itemList.size() - 1) {
			layoutRightWide.setVisibility(View.INVISIBLE);
			return convertView;
		}

		layoutRightWide.setVisibility(View.VISIBLE);
		final GroupBuyBean beanRight = itemList.get(position * 2 + 1);
		ImageView imagRight = ViewHolder.get(convertView, R.id.item_score_img_right);
		BitmapAsset.displayRec(context, imagRight, beanRight.getDefault_image());

		TextView titleRight = ViewHolder.get(convertView, R.id.item_score_tv_title_right);
		titleRight.setText(beanRight.getGoods_name());

		TextView scjRight = ViewHolder.get(convertView, R.id.item_score_tv_scj_right);
		scjRight.setText("市场价：￥"+beanRight.getOld_price());
		TextView scoreRight = ViewHolder.get(convertView, R.id.item_score_tv_score_right);
		scoreRight.setText("所需积分："+beanRight.getPrice());

		layoutRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				launchGoodDetailActivity(beanRight);
			}
		});
		return convertView;
	}

	protected void launchGoodDetailActivity(GroupBuyBean bean) {
		if(bean != null){
			GoodsListBean gbean = new GoodsListBean();
			gbean.setGoods_id(bean.getGoods_id());
			gbean.setGoods_name(bean.getGoods_name());
		    Intent intent = new Intent(context, GoodsDetailActivity.class);
		    intent.putExtra(GoodsDetailActivity.KEY_DETAIL_BEAN, gbean);
		    intent.putExtra(GoodsDetailActivity.KEY_IS_SCORE, true);
		    context.startActivity(intent);
		}
	}
}
