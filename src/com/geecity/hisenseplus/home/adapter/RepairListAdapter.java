package com.geecity.hisenseplus.home.adapter;

import java.util.LinkedList;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.repair.RepairActivity;
import com.geecity.hisenseplus.home.activity.repair.RepairComplActivity;
import com.geecity.hisenseplus.home.activity.repair.RepairDetailActivity;
import com.geecity.hisenseplus.home.bean.RepairListBean;
import com.geecity.hisenseplus.home.utils.ViewHolder;

/**
 * 报修/投诉列表
 * */
public class RepairListAdapter extends BaseAdapter {

	Context context;
	public LinkedList<RepairListBean> list;
	private String type;

	public RepairListAdapter(Context context) {
		this.context = context;
		list = new LinkedList<RepairListBean>();
	}

	public void setArrayList(LinkedList<RepairListBean> mList) {
		this.list = mList;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		int count = 0;
		if (list != null) {
			count = list.size();
		}
		return count;
	}

	@Override
	public RepairListBean getItem(int position) {
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
					R.layout.item_repair_list, null);
		}
		TextView tvTime = ViewHolder.get(convertView, R.id.tv_item_repair_time);
		TextView tvStatus = ViewHolder.get(convertView,
				R.id.tv_item_repair_status);
		TextView tvTitle = ViewHolder.get(convertView,
				R.id.item_notice_tv_title);
		RatingBar rtBar = ViewHolder.get(convertView,
				R.id.raBar_item_repair_appraise);
		Button btnAppra = ViewHolder.get(convertView,
				R.id.btn_item_repair_appraise);
		final RepairListBean bean = getItem(position);
		btnAppra.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, RepairComplActivity.class);
				intent.putExtra(RepairActivity.KEY_DETAIL, bean);
				context.startActivity(intent);
			}
		});

		RelativeLayout layout = ViewHolder.get(convertView,
				R.id.rly_item_repair);
		layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, RepairDetailActivity.class);
				intent.putExtra(RepairActivity.KEY_DETAIL, bean);
				intent.putExtra(RepairActivity.KEY_TYPE, type);
				context.startActivity(intent);
			}
		});
		tvTime.setText(bean.getCreatetime());
		tvStatus.setText(bean.getZt());
		tvTitle.setText(bean.getMiaoshu());
		rtBar.setVisibility(View.GONE);
		btnAppra.setVisibility(View.GONE);
		// 已回访，未评级的显示评价按钮
		// 已回访，已评级的显示评级条
		// 未回访不显示评级条和评价按钮
		if (!TextUtils.isEmpty(bean.getHfzt())) {// 回访状态
			if (bean.getXing() < 0) {
				
				// TODO 巍修改header
				if ((("已关闭").equals(bean.getZt()))
						|| ("已完成").equals(bean.getZt())) {
					btnAppra.setVisibility(View.VISIBLE);
				}
			} else {
				rtBar.setVisibility(View.VISIBLE);
				rtBar.setRating((float) bean.getXing());
			}
			// TODO 巍修改footer
		} else {

		}
		return convertView;
	}

	public void setType(String type) {
		this.type = type;
	}

}
