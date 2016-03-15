package com.geecity.hisenseplus.home.bean;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.utils.BitmapAsset;
import com.geecity.hisenseplus.home.utils.ViewHolder;

public class MyEstateaAdapter extends BaseAdapter{
     private List<EstateAdaptBean> mListEstate;
     private Context mContext;
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mListEstate.size();
	}

	public MyEstateaAdapter(List<EstateAdaptBean> mListEstate, Context mContext) {
		super();
		this.mListEstate = mListEstate;
		this.mContext = mContext;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mListEstate.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView==null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_estate_list, null);
		}
		ImageView pic = ViewHolder.get(convertView, R.id.item_img_estate);
		TextView title = ViewHolder.get(convertView, R.id.item_tv_estate_name);
		TextView tv1 = ViewHolder.get(convertView, R.id.item_tv_estate_1);
		TextView tv2 = ViewHolder.get(convertView, R.id.item_tv_estate_2);
		TextView price = ViewHolder.get(convertView, R.id.item_tv_estate_prices);
		TextView pricePre = ViewHolder.get(convertView, R.id.item_tv_estate_prices_pre);
		TextView priceSub = ViewHolder.get(convertView, R.id.item_tv_estate_prices_sub);
		EstateAdaptBean bean = mListEstate.get(position);
		priceSub.setText(getPiriceSub());
		BitmapAsset.displayImg(mContext, pic, bean.getPicUrl(), R.drawable.icn_community_selected);
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
	
	
		
		return sub;
	}
}
