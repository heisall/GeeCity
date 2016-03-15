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
import com.geecity.hisenseplus.home.activity.disc.DiscDetailActivity;
import com.geecity.hisenseplus.home.activity.mine.MyDiscActivity;
import com.geecity.hisenseplus.home.bean.DiscTypesBean;
import com.geecity.hisenseplus.home.bean.MessageDetail;
import com.geecity.hisenseplus.home.utils.BitmapAsset;
import com.geecity.hisenseplus.home.utils.ViewHolder;
import com.lidroid.xutils.util.LogUtils;

/**
 * 发现列表
 * */
public class MyDisAdapter extends BaseAdapter {

	Context context;
	public LinkedList<MessageDetail> list;

	public MyDisAdapter(Context context) {
		this.context = context;
		list = new LinkedList<MessageDetail>();
	}

	public void setArrayList(LinkedList<MessageDetail> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		int count = 0;
		if (list != null) {
			count = list.size();
		}

		if (count % 2 > 0) {
			count = count / 2 + 1;
		} else {
			count /= 2;
		}
		return count += 1;
	}

	@Override
	public MessageDetail getItem(int position) {
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
					R.layout.item_discovery_list, null);
		}

		RelativeLayout layoutLeft = ViewHolder.get(convertView,
				R.id.item_disc_layout_left);
		RelativeLayout layoutRight = ViewHolder.get(convertView,
				R.id.item_disc_layout_right);

		if (position * 2 > list.size() - 1) {
			layoutLeft.setVisibility(View.INVISIBLE);
			layoutRight.setVisibility(View.INVISIBLE);
			return convertView;
		} else if (list.size() % 2 == 0 && list.size() - 2 < position * 2) {
			layoutLeft.setVisibility(View.INVISIBLE);
			layoutRight.setVisibility(View.INVISIBLE);
			return convertView;
		} else if (list.size() % 2 == 1 && position * 2 + 1 > list.size() - 2) {
			layoutLeft.setVisibility(View.VISIBLE);
			layoutRight.setVisibility(View.INVISIBLE);
		} else {
			layoutLeft.setVisibility(View.VISIBLE);
		}

		final MessageDetail beanLeft = list.get(position * 2);
		ImageView imagLeft = ViewHolder.get(convertView, R.id.item_disc_img_left);
		BitmapAsset.displayRec(context, imagLeft, beanLeft.getPhoto());

		TextView titleLeft = ViewHolder.get(convertView, R.id.item_disc_tv_title_left);
		titleLeft.setText(beanLeft.getContent());

		Button viewCountLeft = ViewHolder.get(convertView, R.id.item_disc_btn_msg_left);
		viewCountLeft.setText("" + beanLeft.getLeaveWordCount());

		TextView timeLeft = ViewHolder.get(convertView, R.id.item_disc_tv_time_left);
		timeLeft.setText(beanLeft.getReleaseTime());
		TextView catogeryLeft = ViewHolder.get(convertView, R.id.item_disc_tv_catogery_left);
		catogeryLeft.setText(beanLeft.getMemo());
//		String typeLeft = DiscTypesBean.getCatogeryType(beanLeft.getMemo());
//		catogeryLeft.setBackgroundResource(BitmapAsset.convertBg(typeLeft));

		layoutLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, DiscDetailActivity.class);
//				intent.putExtra(MyDisc.KEY_DISCLIST, beanLeft);
				LogUtils.d(beanLeft.getId() + "  --LEFT");
				context.startActivity(intent);
			}
		});
		viewCountLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}
		});

		if (list.size() % 2 == 1 && position * 2 + 1 > list.size() - 2) {
			layoutRight.setVisibility(View.INVISIBLE);
			return convertView;
		}
		if (position * 2 + 1 > list.size() - 1) {
			layoutRight.setVisibility(View.INVISIBLE);
			return convertView;
		}

		layoutRight.setVisibility(View.VISIBLE);

		final MessageDetail beanRight = list.get(position * 2 + 1);
		ImageView imagRight = ViewHolder.get(convertView, R.id.item_disc_img_right);
		TextView titleRight = ViewHolder.get(convertView, R.id.item_disc_tv_title_right);
		Button viewCountRight = ViewHolder.get(convertView, R.id.item_disc_btn_msg_right);
		TextView timeRight = ViewHolder.get(convertView, R.id.item_disc_tv_time_right);
		BitmapAsset.displayRec(context, imagRight, beanRight.getPhoto());
		titleRight.setText(beanRight.getContent());
		viewCountRight.setText("" + beanRight.getLeaveWordCount());
		timeRight.setText(beanRight.getReleaseTime());

		TextView catogeryRight = ViewHolder.get(convertView, R.id.item_disc_tv_catogery_right);
		catogeryRight.setText(beanRight.getMemo());
		String typeRight = DiscTypesBean.getCatogeryType(beanRight.getMemo());
		catogeryRight.setBackgroundResource(BitmapAsset.convertBg(typeRight));

		layoutRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, DiscDetailActivity.class);
				intent.putExtra(MyDiscActivity.KEY_DISCLIST, beanRight);
				LogUtils.d(beanRight.getId() + "  --RIGHT");
				context.startActivity(intent);
			}
		});
		viewCountRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
			}
		});
		return convertView;
	}

}

