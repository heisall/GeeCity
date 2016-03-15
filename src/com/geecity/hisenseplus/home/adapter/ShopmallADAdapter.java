package com.geecity.hisenseplus.home.adapter;

import java.util.LinkedList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.bean.ADBean;
import com.geecity.hisenseplus.home.utils.BitmapAsset;
import com.ibill.lib.tools.ScreenUtils;

/**
 * 商城优惠列表
 * */
public class ShopmallADAdapter extends BaseAdapter {

	Context context;
	private LinkedList<ADBean> adBeans;
	private int width;

	public ShopmallADAdapter(Context context) {
		this.context = context;
		width = ScreenUtils.getScreenWidth(context);
	}
	
	public void setADBeans(LinkedList<ADBean> adBeans){
		this.adBeans = adBeans;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		int count = 0;
		if (adBeans != null) {
			count = adBeans.size();
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_ad_list, null);
		}
//		ImageView icn = ViewHolder.get(convertView, R.id.img_item_ad);
		ImageView icn = (ImageView) convertView.findViewById(R.id.img_item_ad);
//		AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//		LogUtils.d(params == null?"params == null":params.toString());
//		params.width = width;
//		params.height = AbsListView.LayoutParams.WRAP_CONTENT;
//		icn.setLayoutParams(params);
//		icn.setAdjustViewBounds(true);
//		icn.setScaleType(ScaleType.FIT_XY);
//		icn.setMaxWidth(width);
//		icn.setMaxWidth(width*5);
		BitmapAsset.displayImg(context, icn, 
				adBeans.get(position).getPhoto(),
//				"http://i2.hoopchina.com.cn/user/581/15397581/13460745910.jpg",
				R.drawable.icn_community_selected);
//		Bitmap bitmap = BitmapAsset.getBitmap(context,adBeans.get(position).getPhoto());
//		LogUtils.d(bitmap.getWidth() + "  --  " + bitmap.getHeight());
		return convertView;
	}
}
