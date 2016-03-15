package com.geecity.hisenseplus.home.adapter;

import java.util.LinkedList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.live.MyOrderActivity;
import com.geecity.hisenseplus.home.activity.live.OrderAppaActivity;
import com.geecity.hisenseplus.home.bean.MyOrderCompListParentBean;
import com.geecity.hisenseplus.home.utils.BitmapAsset;
import com.geecity.hisenseplus.home.utils.ViewHolder;
import com.lidroid.xutils.util.LogUtils;

/**
 * 已完成订单列表适配器
 * @author Administrator
 *
 */
public class MyOrderCompleteListAdapter extends BaseAdapter {

	Context context;
	public LinkedList<MyOrderCompListParentBean> itemList;

	public MyOrderCompleteListAdapter(Context context) {
		this.context = context;
		itemList = new LinkedList<MyOrderCompListParentBean>();
	}

	public void setList(LinkedList<MyOrderCompListParentBean> beans) {
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
	public MyOrderCompListParentBean getItem(int position) {
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (null == convertView) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_order_complete_list, null);
		}
		ImageView icn = ViewHolder.get(convertView, R.id.img_item_order_comp_logo);
		TextView name = ViewHolder.get(convertView, R.id.tv_item_order_comp_name);
		TextView date = ViewHolder.get(convertView, R.id.tv_item_order_comp_miaos);
		Button btnCopm = ViewHolder.get(convertView, R.id.btn_item_order_comp);
		final MyOrderCompListParentBean bean = getItem(position);
		btnCopm.setOnClickListener(new OnClickListener() {
		    
		    @Override
		    public void onClick(View v) {
		        LogUtils.d(""+position);
		        if(bean.isE_status()){
		            return;
		        }else{
		            Bundle b = new Bundle();
		            b.putSerializable(OrderAppaActivity.KEY_DETAIL, bean);
		            ((MyOrderActivity)context).startNextActivity(b, OrderAppaActivity.class);
		        }
		    }
		});
		BitmapAsset.displayImg(context, icn, bean.getGoods_image(), R.drawable.icn_community_selected);
		name.setText(bean.getGoods_name());
		date.setText("￥" +bean.getPrice() + "  " + " 收货时间："+bean.getAdd_time());
		btnCopm.setVisibility(View.VISIBLE);
		if(bean.isE_status()){
		    btnCopm.setBackgroundColor(context.getResources().getColor(R.color.txt_white));
		    btnCopm.setText("已评价");
		    btnCopm.setTextColor(context.getResources().getColor(R.color.dark_gray));
		}else{
		    btnCopm.setBackgroundResource(R.drawable.btn_send);
            btnCopm.setText("待评价");
            btnCopm.setTextColor(context.getResources().getColor(R.color.txt_white));
		}
		return convertView;
	}
}
