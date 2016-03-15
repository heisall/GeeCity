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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.live.LiveMoreActivity;
import com.geecity.hisenseplus.home.bean.MallMenuBean;
import com.geecity.hisenseplus.home.utils.BitmapAsset;
import com.geecity.hisenseplus.home.utils.ViewHolder;

/**
 * 房屋列表
 * */
public class LiveHomeMenuAdapter extends BaseAdapter {

	Context context;
	private LinkedList<MallMenuBean> menus;

	public LiveHomeMenuAdapter(Context context) {
		this.context = context;
	}
	
	public void setMenus(LinkedList<MallMenuBean> menus){
		this.menus = menus;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		int count = 0;
		if (menus != null) {
			count = menus.size();
		}
		return count;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView,
			ViewGroup parent) {
		if (null == convertView) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_live_home_gridview, null);
		}
		RelativeLayout rly = ViewHolder.get(convertView, R.id.rly_item_life);
		ImageView icn = ViewHolder.get(convertView, R.id.img_item_life_icn);
		TextView title = ViewHolder.get(convertView, R.id.tv_item_life_title);

		BitmapAsset.displayImg(context, icn, menus.get(position).getThumb(), R.drawable.icn_community_selected);
		title.setText(menus.get(position).getCateName());

		rly.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				launchBusinessActivity(position);
			}
		});

		icn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				launchBusinessActivity(position);
			}
		});

		title.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				launchBusinessActivity(position);
			}
		});
		return convertView;
	}

	protected void launchBusinessActivity(int position) {
		if(menus.get(position).getId() == 0){//更多 ID指定为0，所以点击更多时，跳转的商户列表默认显示第一个分类的数据
			position = 0;
		}
		Intent intent = new Intent(context,LiveMoreActivity.class);
		intent.putExtra(LiveMoreActivity.CATE_BEAN, menus.get(position));
		context.startActivity(intent);
	}

}
