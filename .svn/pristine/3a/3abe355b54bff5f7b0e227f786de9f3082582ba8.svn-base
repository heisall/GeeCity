package com.geecity.hisenseplus.home.adapter;

import java.util.LinkedList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.bean.RepairProgressBean;
import com.geecity.hisenseplus.home.utils.ViewHolder;

/**
 * 报修/投诉进程列表
 * */
public class RepairDetailProgressAdapter extends BaseAdapter {

	Context context;
	public LinkedList<RepairProgressBean> list;

	public RepairDetailProgressAdapter(Context context) {
		this.context = context;
		list = new LinkedList<RepairProgressBean>();
	}

	public void setArrayList(LinkedList<RepairProgressBean> mList) {
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
	public RepairProgressBean getItem(int position) {
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
					R.layout.item_repair_progress_list, null);
		}
		TextView tvProgress = ViewHolder.get(convertView,
				R.id.tv_item_repair_progress);
		TextView tvStatus = ViewHolder.get(convertView,
				R.id.tv_item_repair_progress_status);
		TextView tv1 = ViewHolder.get(convertView,
				R.id.tv_item_repair_progress_content);
		TextView tv2 = ViewHolder.get(convertView,
				R.id.tv_item_repair_progress_tel);
		TextView tvDate = ViewHolder.get(convertView,
				R.id.tv_item_repair_progress_date);
		tv1.setText("");
		tv2.setText("");

		RepairProgressBean bean = list.get(position);
		tvProgress.setBackgroundResource(getBgId(bean.getStatus()));
		tvStatus.setText(bean.getTitle());
		if (bean.getStatus() == 0) {

		} else if (bean.getStatus() == 1) {// 处理中
			tv1.setText(bean.getContent());
		} else if (bean.getStatus() == 2) {// 已完成
			tv1.setText("处理人：" + bean.getContact());
			tv2.setText("手机号：" + bean.getContactPhone());
		}

		tvDate.setText(bean.getDate());

		return convertView;
	}

	/* TODO 巍修改 - head */
	private int getBgId(int status) {
		switch (status) {
		case 0:
			// 已受理
			return R.drawable.icn_progress_start;
		case 10:
            // 已受理
            return R.drawable.icn_progress_start_normal;
		case 1:
			// 处理中
			return R.drawable.icn_progress_center;
		case 11:
			// 处理中 - normal
			return R.drawable.icn_progress_center_normal;
		case 2:
			// 已完成
			return R.drawable.icn_progress_end;
		case 21:
			// 已完成 - normal
			return R.drawable.icn_progress_end_normal;
		default:
			// 已受理 - normal
			return R.drawable.icn_progress_start;
		}
	}
	/* TODO 巍修改 - end */
}
