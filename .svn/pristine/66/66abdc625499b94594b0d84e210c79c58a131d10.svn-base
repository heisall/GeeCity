package com.geecity.hisenseplus.home.adapter;

import java.util.LinkedList;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.estate.EstateActivity.ESTATE;
import com.geecity.hisenseplus.home.bean.EstateAdaptBean;
import com.geecity.hisenseplus.home.utils.BitmapAsset;
import com.geecity.hisenseplus.home.utils.ViewHolder;

/**
 * 房屋列表
 * */
public class EstatesAdapter extends BaseAdapter {

	Context context;
	public LinkedList<EstateAdaptBean> list;

	public EstatesAdapter(Context context) {
		this.context = context;
		list = new LinkedList<EstateAdaptBean>();
	}

	public void setArrayList(LinkedList<EstateAdaptBean> adpteBeans) {
		this.list = adpteBeans;
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
	public EstateAdaptBean getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView==null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_estate_list, null);
		}
		ImageView pic = ViewHolder.get(convertView, R.id.item_img_estate);
		TextView title = ViewHolder.get(convertView, R.id.item_tv_estate_name);
		TextView tv1 = ViewHolder.get(convertView, R.id.item_tv_estate_1);
		TextView tv2 = ViewHolder.get(convertView, R.id.item_tv_estate_2);
		TextView price = ViewHolder.get(convertView, R.id.item_tv_estate_prices);
		TextView pricePre = ViewHolder.get(convertView, R.id.item_tv_estate_prices_pre);
		TextView priceSub = ViewHolder.get(convertView, R.id.item_tv_estate_prices_sub);
		EstateAdaptBean bean = list.get(position);
		if(this.estateType.ordinal() == ESTATE.NEW_HOME.ordinal()){
			pricePre.setVisibility(View.VISIBLE);
			pricePre.setText(bean.getPricePre());
		}else{
			pricePre.setVisibility(View.GONE);
		}
		priceSub.setText(getPiriceSub());
		BitmapAsset.displayImg(context, pic, bean.getPicUrl(), R.drawable.icn_community_selected);
		title.setText(bean.getLpmc());
        if(TextUtils.isEmpty(bean.getTv1())){
            tv1.setText(""); 
        }else{
            tv1.setText(bean.getTv1().replace("null", ""));
        }
		if(TextUtils.isEmpty(bean.getTv2())){
		    tv2.setText(""); 
		}else{
		    tv2.setText(bean.getTv2().replace("null", ""));
		}
		price.setText(bean.getPrice());
		return convertView;
	}

	private String getPiriceSub() {
		String sub = "元";
		switch (estateType) {
		case NEW_HOME:
			sub = "元/平米";
			break;
		case SECOND_HAND:
			sub = "万元";
			break;
		case RENT:
			sub = "元/月";
			break;
		default:
			break;
		}
		return sub;
	}
	
	private ESTATE estateType;

	public void setType(ESTATE type) {
		this.estateType = type;
	}

	public void addList(LinkedList<EstateAdaptBean> adpteBeans) {
		list.addAll(adpteBeans);
		notifyDataSetChanged();
	}

}
