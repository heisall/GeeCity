package com.geecity.hisenseplus.home.adapter;

import java.util.LinkedList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.bean.ShopCarListBean;
import com.geecity.hisenseplus.home.utils.BitmapAsset;
import com.geecity.hisenseplus.home.utils.ViewHolder;

/**
 * 购物车列表适配器
 * @author Administrator
 *
 */
public class ShopCarListAdapter extends BaseAdapter {

	Context context;
	public LinkedList<ShopCarListBean> itemList;

	public ShopCarListAdapter(Context context) {
		this.context = context;
		itemList = new LinkedList<ShopCarListBean>();
	}

	public void setList(LinkedList<ShopCarListBean> mShopCars) {
		this.itemList = mShopCars;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
	    int count = 0;
	    if(itemList != null) count = itemList.size();
		return count;
	}

	@Override
	public ShopCarListBean getItem(int position) {
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
					R.layout.item_shopcar_list, null);
		}
		TextView storeName = ViewHolder.get(convertView, R.id.tv_item_shopcar_store_name);
		CheckBox cb = ViewHolder.get(convertView, R.id.cb_item_shopcar_select);
		ImageView icn = ViewHolder.get(convertView, R.id.img_item_shopcar);
		TextView name = ViewHolder.get(convertView, R.id.tv_item_shopcar_title);
		TextView price = ViewHolder.get(convertView, R.id.tv_item_shopcar_price);
		final TextView count = ViewHolder.get(convertView, R.id.tv_item_shopcar_count);
		TextView line = ViewHolder.get(convertView, R.id.tv_item_shopcar_list_bottom_line);
		Button btnAdd = ViewHolder.get(convertView, R.id.btn_item_shopcar_add);
		Button btnLess = ViewHolder.get(convertView, R.id.btn_item_shopcar_less);
		btnAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				int oldCount = Integer.parseInt(count.getText().toString());
				oldCount ++;
				count.setText(String.valueOf(oldCount));
				listener.onCountChanged(position, oldCount, 1);
			}
		});
		btnLess.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				int oldCount = Integer.parseInt(count.getText().toString());
				if(oldCount <= 1){
				  return;
				}
				oldCount --;
				count.setText(String.valueOf(oldCount));
				listener.onCountChanged(position, oldCount, 0);
			}
		});
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(listener != null){
					listener.onSelected(position, arg1);
				}
			}
		});
		ShopCarListBean bean = getItem(position);
		storeName.setVisibility(View.VISIBLE);
		line.setVisibility(View.VISIBLE);
		if (position > 0) {
			if (bean.getStore_id() == getItem(position - 1).getStore_id()) {
				storeName.setVisibility(View.GONE);
			} else if (position < getCount() - 1
					&& bean.getStore_id() == getItem(position + 1).getStore_id()) {
				line.setVisibility(View.GONE);
			}

		}
		storeName.setText(bean.getStore_name());
		
		cb.setChecked(bean.isSelected() == 1);
		BitmapAsset.displayImg(context, icn, bean.getGoods_image(), R.drawable.icn_community_selected);
		name.setText(bean.getGoods_name());
		price.setText(String.valueOf(bean.getPrice()));
		count.setText(String.valueOf(bean.getQuantity()));
		return convertView;
	}
	
	private ShopCarChangedListener listener;
	
	public void setOnShopCarSelectedListener(ShopCarChangedListener sl){
		this.listener = sl;
	}
	
	public interface ShopCarChangedListener{
		void onSelected(int position, boolean isSelected);
		/**
		 * 购物车数量改变
		 * @param position 列表索引
		 * @param newValue 变更后的数量
		 * @param addFlag 加为1，减为0
		 */
		void onCountChanged(int position, int newValue, int addFlag);
	}
}
