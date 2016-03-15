package com.geecity.hisenseplus.home.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.bean.EstateDetialNewPhotoBean;
import com.geecity.hisenseplus.home.utils.ViewHolder;
import com.lidroid.xutils.BitmapUtils;

public class PhotoGvAdapter extends BaseAdapter {

	protected static final String TAG = "PhotoGvAdapter";
	private Context context;
	private List<EstateDetialNewPhotoBean> list;
	private BitmapUtils mUtils;
	private Bitmap mDefaultBitmap;

	public PhotoGvAdapter(Context context) {
		super();
		this.mUtils = new BitmapUtils(context);
		if (mDefaultBitmap == null) {
			mDefaultBitmap = BitmapFactory.decodeResource(
					context.getResources(), R.drawable.icn_community_selected);
		}
		mUtils.configDefaultLoadingImage(mDefaultBitmap);
		this.context = context;
	}

	public void setArrayData(List<EstateDetialNewPhotoBean> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		int count = 0;
		if (list != null && list.size() > 0) {
			count = list.size();
		}
		return count;
	}

	@Override
	public Object getItem(int position) {
		if (list == null || list.size() == 0 || position > list.size()) {
			return null;
		}
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_photo_browse, null);
		}

		ImageView img = ViewHolder.get(convertView, R.id.imageView);
		mUtils.display(img, list.get(position).getHxt());
		return convertView;
	}
}
