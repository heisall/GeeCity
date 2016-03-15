package com.geecity.hisenseplus.home.adapter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.bean.BusinessListBean;
import com.geecity.hisenseplus.home.utils.BitmapAsset;
import com.geecity.hisenseplus.home.utils.ViewHolder;

public class LiveMoreBusinessListAdapter extends BaseAdapter {

	Context context;
	public LinkedList<BusinessListBean> itemList;

	public LiveMoreBusinessListAdapter(Context context) {
		this.context = context;
		itemList = new LinkedList<BusinessListBean>();
	}

	public void setList(LinkedList<BusinessListBean> businessBeans) {
		this.itemList = businessBeans;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
	    int count = 0;
	    if(itemList != null) count = itemList.size();
		return count;
	}

	@Override
	public BusinessListBean getItem(int position) {
		return itemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (null == convertView) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_business_list, null);
		}
		ImageView icn = ViewHolder.get(convertView, R.id.img_item_live_business_logo);
		TextView name = ViewHolder.get(convertView, R.id.tv_item_live_business_name);
		TextView views = ViewHolder.get(convertView, R.id.tv_item_live_business_views);
		TextView tv01 = ViewHolder.get(convertView, R.id.tv_item_live_business_01);
		TextView tv02 = ViewHolder.get(convertView, R.id.tv_item_live_business_02);
		TextView tv03 = ViewHolder.get(convertView, R.id.tv_item_live_business_03);
		TextView tv04 = ViewHolder.get(convertView, R.id.tv_item_live_business_04);
		TextView tv05 = ViewHolder.get(convertView, R.id.tv_item_live_business_05);
		TextView tv06 = ViewHolder.get(convertView, R.id.tv_item_live_business_06);
		RatingBar rb =  ViewHolder.get(convertView, R.id.raBar_item_appraise);
		
		tv01.setVisibility(View.INVISIBLE);
		tv02.setVisibility(View.INVISIBLE);
		tv03.setVisibility(View.INVISIBLE);
		tv04.setVisibility(View.INVISIBLE);
		tv05.setVisibility(View.INVISIBLE);
		tv06.setVisibility(View.INVISIBLE);
		BusinessListBean bean = getItem(position);
		BitmapAsset.displayImg(context, icn, bean.getLogo(), R.drawable.icn_community_selected);
		name.setText(bean.getSname());
		rb.setRight(bean.getStar());
		rb.setRating((float)bean.getStar());
		String prop = bean.getProp();
		if(!TextUtils.isEmpty(prop)){
		    List<String> props = Arrays.asList(prop.split(","));
		    if(props != null && props.size() > 0){
		        switch (props.size()) {
                    case 6:
                        tv06.setVisibility(View.VISIBLE);
                        tv06.setText(props.get(5));
                    case 5:
                        tv05.setVisibility(View.VISIBLE);
                        tv05.setText(props.get(4));
                    case 4:
                        tv04.setVisibility(View.VISIBLE);
                        tv04.setText(props.get(3));
                    case 3:
                        tv03.setVisibility(View.VISIBLE);
                        tv03.setText(props.get(2));
                    case 2:
                        tv02.setVisibility(View.VISIBLE);
                        tv02.setText(props.get(1));
                    case 1:
                        tv01.setVisibility(View.VISIBLE);
                        tv01.setText(props.get(0));
                        break;

                    default:
                        break;
                }
		    }
		}
		views.setText("查看人数 ："+bean.getVisits()+"");
		views.setVisibility(View.GONE);
		return convertView;
	}
}
