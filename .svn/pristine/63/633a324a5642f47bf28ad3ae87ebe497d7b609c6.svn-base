package com.geecity.hisenseplus.home.adapter;

import java.util.LinkedList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.live.MyOrderActivity;
import com.geecity.hisenseplus.home.activity.live.MyOrderActivity.OrderType;
import com.geecity.hisenseplus.home.activity.live.MyOrderDetailActivity;
import com.geecity.hisenseplus.home.bean.MyOrderListParentBean;
import com.geecity.hisenseplus.home.utils.BitmapAsset;
import com.geecity.hisenseplus.home.utils.ViewHolder;
import com.geecity.hisenseplus.home.view.RoundImageView;

/**
 * 我的订单列表适配器
 * */
public class MyOrderAdapter extends BaseAdapter {

  Context context;
  public LinkedList<MyOrderListParentBean> list;
  private OrderClickedListener listener;

  public MyOrderAdapter(Context context) {
    this.context = context;
    list = new LinkedList<MyOrderListParentBean>();
  }
  
  public void setLinkedList(LinkedList<MyOrderListParentBean> list){
    this.list = list;
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
  public MyOrderListParentBean getItem(int position) {
    return list.get(position);
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  @Override
  public View getView(final int position, View convertView, ViewGroup parent) {
    if (null == convertView) {
      convertView = LayoutInflater.from(context).inflate(R.layout.item_order_list, null);
    }
    RelativeLayout rLayout = ViewHolder.get(convertView, R.id.rtly_item_order);
    RoundImageView img = ViewHolder.get(convertView, R.id.rimg_item_order_head);
    TextView storeName = ViewHolder.get(convertView, R.id.tv_item_order_name);
    TextView status = ViewHolder.get(convertView, R.id.tv_item_order_status);
    TextView count = ViewHolder.get(convertView, R.id.tv_item_order_count);
    TextView shipment = ViewHolder.get(convertView, R.id.tv_item_order_shipment);
    TextView price = ViewHolder.get(convertView, R.id.tv_item_order_price);
    LinearLayout layout = ViewHolder.get(convertView, R.id.ly_item_order_goods);
    Button btnRight = ViewHolder.get(convertView, R.id.btn_item_order_right);
    Button btnLeft = ViewHolder.get(convertView, R.id.btn_item_order_left);
    final MyOrderListParentBean bean = list.get(position);
    btnRight.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			if(listener != null){
				listener.OnRight(bean.getStatus(), bean.getOrder_id());
			}
		}
	});
    btnLeft.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			if(listener != null){
				listener.OnLeft(position, bean.getOrder_id());
			}
		}
	});
    btnRight.setText(OrderType.getType(bean.getStatus()).getBtnDesc());
    if(OrderType.UNPAY.getTag()!=bean.getStatus()){
    	btnLeft.setVisibility(View.INVISIBLE);
    }
    BitmapAsset.displayImg(context, img, bean.getStore_logo(), R.drawable.icn_community_selected);
    storeName.setText(bean.getSeller_name());
    status.setText(OrderType.getType(bean.getStatus()).getDesc());
    count.setText("共计0件商品");
    shipment.setText("运费：￥" + bean.getShipping_fee());
    price.setText("￥"+bean.getOrder_amount());
    layout.removeAllViews();
    if(bean.getDetail() == null || bean.getDetail().size() == 0){
    	return convertView;
    }
    int goodsTotalCount = 0;
    for (int i = 0; i < bean.getDetail().size(); i++) {
        //计算商品总数
        goodsTotalCount +=bean.getDetail().get(i).getQuantity();
        
    	View view = LayoutInflater.from(context).inflate(R.layout.item_order_goods_list, layout, false);
    	ImageView goodsImg = (ImageView) view.findViewById(R.id.img_item_bm_logo);
    	TextView goodsName = (TextView) view.findViewById(R.id.tv_item_bm_name);
    	TextView goodsCount = (TextView) view.findViewById(R.id.tv_item_order_list_count);
    	TextView goodsPrice = (TextView) view.findViewById(R.id.tv_item_order_list_price);
    	goodsPrice.setVisibility(View.INVISIBLE);
    	goodsName.setText(bean.getDetail().get(i).getGoods_name());
    	goodsCount.setText("￥"+bean.getDetail().get(i).getPrice() + " x " + bean.getDetail().get(i).getQuantity());
        BitmapAsset.displayImg(context, goodsImg, bean.getDetail().get(i).getGoods_image(), R.drawable.icn_community_selected);
    	//分割线
    	((MarginLayoutParams)view.getLayoutParams()).topMargin = (i==0?0:1);
    	layout.addView(view);
	}
    rLayout.setOnClickListener(new OnClickListener() {
        
        @Override
        public void onClick(View v) {
            launchOrderDetail(bean);
        }
    });
    count.setText("共计"+goodsTotalCount+"件商品");
    return convertView;
  }

    protected void launchOrderDetail(MyOrderListParentBean bean) {
    	Bundle b = new Bundle();
    	b.putString(MyOrderDetailActivity.KEY_ORDER_ID, ""+bean.getOrder_id());
        ((MyOrderActivity)context).startNextActivity(b, MyOrderDetailActivity.class);
    }

    private String getStatus(int status) {
        OrderType type = OrderType.getType(status);
        switch (type) {
            case UNPAY:
                return "待付款";
            case UNCONFIRM:
                return "待收货";
            case UNAPPR:
                return "待评价";
            default:
                break;
        }
        return "";
    }
    
    public interface OrderClickedListener{
    	void OnLeft(int position, int orderId);
    	void OnRight(int status, int orderId);
    }
    
    public void setOrderClickedListener(OrderClickedListener l){
    	this.listener = l;
    }
}
