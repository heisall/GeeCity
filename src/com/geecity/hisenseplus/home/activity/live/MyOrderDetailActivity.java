package com.geecity.hisenseplus.home.activity.live;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.live.MyOrderActivity.OrderType;
import com.geecity.hisenseplus.home.bean.AddressListBean;
import com.geecity.hisenseplus.home.bean.BaseBean;
import com.geecity.hisenseplus.home.bean.MyOrderDetailBean;
import com.geecity.hisenseplus.home.bean.MyOrderDetailResultBean;
import com.geecity.hisenseplus.home.utils.BitmapAsset;
import com.geecity.hisenseplus.home.utils.Const;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.geecity.hisenseplus.home.view.RoundImageView;
import com.ibill.lib.https.HttpUtil;
import com.ibill.lib.https.HttpUtil.RequestHttpCallBack;
import com.ibill.lib.webutils.RequestHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lingnet.paylib.weixin.WXPayTools;
import com.lingnet.paylib.weixin.WXPayTools.OnPayListener;

public class MyOrderDetailActivity extends ActionBarActivity {

	public static final String KEY_ORDER_ID = "key_order_id";

	@ViewInject(R.id.btn_home_menu)
	private Button mTopBackBtn;
	@ViewInject(R.id.tv_home_topbar_title)
	private TextView mTopTitle;

	@ViewInject(R.id.tv_order_detail_type)
	private TextView mTvOrderType;
	@ViewInject(R.id.tv_order_detail_01)
	private TextView mTvOrder01;
	@ViewInject(R.id.tv_order_detail_02)
	private TextView mTvOrder02;
	@ViewInject(R.id.tv_order_detail_03)
	private TextView mTvOrder03;
	@ViewInject(R.id.tv_order_detail_04)
	private TextView mTvOrder04;
	@ViewInject(R.id.tv_order_detail_05)
	private TextView mTvOrder05;

	@ViewInject(R.id.tv_order_detail_conact_name)
	private TextView mTvOrderContactName;
	@ViewInject(R.id.tv_order_detail_tel)
	private TextView mTvOrderTel;
	@ViewInject(R.id.tv_order_detail_address)
	private TextView mTvOrderAddress;

	@ViewInject(R.id.rimg_item_order_head)
	private RoundImageView mImgHead;
	@ViewInject(R.id.tv_item_order_name)
	private TextView mTvOrderBottomName;
	@ViewInject(R.id.tv_item_order_count)
	private TextView mTvOrderBottomCount;
	@ViewInject(R.id.tv_item_order_shipment)
	private TextView mTvOrderBottomFee;
	@ViewInject(R.id.tv_item_order_price)
	private TextView mTvOrderBottomPrices;
	@ViewInject(R.id.ly_item_order_goods)
	private LinearLayout mLyOrderGoods;
	@ViewInject(R.id.btn_item_order_left)
	private Button mBtnLeft;
	@ViewInject(R.id.btn_item_order_right)
	private Button mBtnRight;

	private MyOrderDetailBean mOrderDetailBean;
	private String mOrderId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_detail);
		ViewUtils.inject(this);
		mOrderId = getIntent().getStringExtra(KEY_ORDER_ID);
		sendRequest();
	}

	protected void sendRequest() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("orderId", mOrderId);
		LogUtils.d("orderId = " + mOrderId);
		HttpUtil httpUtil = new HttpUtil(new RequestHandler(
				MyOrderDetailActivity.this, "", getResources().getString(
						R.string.loading), 0));
		// 回调函数
		RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> result) {
				super.onSuccess(result);
				try {
					MyOrderDetailResultBean resb = mGson.fromJson(
							result.result, MyOrderDetailResultBean.class);
					if (Const.SUCCESS.equals(resb.getResult())) {
						mOrderDetailBean = resb.getData();
						if (mOrderDetailBean != null) {
							updateUI();
						}
					} else {
						showToast(resb.getErrorCode());
					}
				} catch (Exception e) {
					showToast(R.string.errorMsg);
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				super.onFailure(arg0, arg1);
			}
		};
		httpUtil.post(WebConstants.METHOD_ORDER_DETAILS, params, callback);
	}

	protected void updateUI() {
		mTvOrderType.setText(OrderType.getType(mOrderDetailBean.getStatus()).getDesc());
		mTvOrder01.setText("订单编号：" + mOrderDetailBean.getOrder_sn());
		mTvOrder02.setText("提交时间：" + mOrderDetailBean.getAdd_time());
		AddressListBean bean = mOrderDetailBean.getExtm();
		if (bean != null) {
			mTvOrderContactName.setText("收货人：" + bean.getConsignee());
			mTvOrderTel.setText(bean.getPhone_tel());
			mTvOrderAddress.setText("收货地址：" + bean.getRegion_name()
					+ bean.getAddress());
		}
		BitmapAsset.displayImg(MyOrderDetailActivity.this, mImgHead,
				mOrderDetailBean.getStore_logo(),
				R.drawable.icn_community_selected);
		mTvOrderBottomName.setText(mOrderDetailBean.getSeller_name());
		mTvOrderBottomCount.setText("共计0件商品");
		mTvOrderBottomFee.setText("运费：￥" + mOrderDetailBean.getShipping_fee());
		mTvOrderBottomPrices.setText("￥" + mOrderDetailBean.getOrder_amount());
		mLyOrderGoods.removeAllViews();
		if (mOrderDetailBean.getDetail() == null
				|| mOrderDetailBean.getDetail().size() == 0) {
			return;
		}
		int goodsTotalCount = 0;
		for (int i = 0; i < mOrderDetailBean.getDetail().size(); i++) {
			// 计算商品总数
			goodsTotalCount += mOrderDetailBean.getDetail().get(i)
					.getQuantity();

			View view = LayoutInflater.from(MyOrderDetailActivity.this)
					.inflate(R.layout.item_order_goods_list, mLyOrderGoods,
							false);
			ImageView goodsImg = (ImageView) view
					.findViewById(R.id.img_item_bm_logo);
			TextView goodsName = (TextView) view
					.findViewById(R.id.tv_item_bm_name);
			TextView goodsCount = (TextView) view
					.findViewById(R.id.tv_item_order_list_count);
			TextView goodsPrice = (TextView) view
					.findViewById(R.id.tv_item_order_list_price);
			goodsPrice.setVisibility(View.INVISIBLE);
			goodsName.setText(mOrderDetailBean.getDetail().get(i)
					.getGoods_name());
			goodsCount.setText("￥"
					+ mOrderDetailBean.getDetail().get(i).getPrice() + " x "
					+ mOrderDetailBean.getDetail().get(i).getQuantity());
			BitmapAsset.displayImg(MyOrderDetailActivity.this, goodsImg,
					mOrderDetailBean.getDetail().get(i).getGoods_image(),
					R.drawable.icn_community_selected);
			// 分割线
			((MarginLayoutParams) view.getLayoutParams()).topMargin = (i == 0 ? 0
					: 1);
			mLyOrderGoods.addView(view);
		}
		mTvOrderBottomCount.setText("共计" + goodsTotalCount + "件商品");

		mBtnRight.setText(OrderType.getType(mOrderDetailBean.getStatus())
				.getBtnDesc());
		if (OrderType.UNPAY.getTag() !=  mOrderDetailBean.getStatus()) {
			mBtnLeft.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		sendRequest();
	}

	@Override
	public void configActionBar() {
		mTopBackBtn.setVisibility(View.VISIBLE);
		mTopTitle.setText(R.string.frg_4_title_order);
	}

	@OnClick({ R.id.btn_home_menu, R.id.btn_item_order_left,
			R.id.btn_item_order_right })
	public void OnClick(View v) {
		switch (v.getId()) {
		case R.id.btn_home_menu: // 点击返回按钮
			onBackPressed();
			break;
		case R.id.btn_item_order_left:
			sendOrderDelRequest(mOrderDetailBean.getOrder_id());
			break;
		case R.id.btn_item_order_right:
			WXPayTools tools = new WXPayTools(MyOrderDetailActivity.this);
			tools.sendPrePayRequest(mOrderId);
			tools.setOnPayStartListener(new OnPayListener() {
                
                @Override
                public void onStartPay() {
                }
                
                @Override
                public void onCompletPay(boolean result) {
                }
            });
			break;
		default:
			break;
		}
	}

	private void changStatus() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("orderId", mOrderId + "");
		OrderType type = OrderType.getType(mOrderDetailBean.getStatus());
		int status = 0;
		switch (type) {
		case UNPAY:
			status = OrderType.UNCONFIRM.getTag();
			break;
		case UNCONFIRM:
			status = OrderType.UNAPPR.getTag();
			break;

		default:
			break;
		}

		params.addBodyParameter("status", ""+status);
		LogUtils.d("changeOrderStatus : orderId = " + mOrderId
				+ ",  oldStatus = " + mOrderDetailBean.getStatus()
				+ ",  status = " + status);
		HttpUtil httpUtil = new HttpUtil(new RequestHandler(
				MyOrderDetailActivity.this, "", getResources().getString(
						R.string.loading), 0));
		// 回调函数
		RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> result) {
				super.onSuccess(result);
				try {
					BaseBean resb = mGson.fromJson(result.result, BaseBean.class);
					if (Const.SUCCESS.equals(resb.getResult())) {
						onBackPressed();
					} else {
						showToast(resb.getErrorCode());
					}
				} catch (Exception e) {
					showToast(R.string.errorMsg);
				}
			}
		};
		httpUtil.put(WebConstants.METHOD_ORDER_CHANGE_STATUS, params, callback);

	}

	protected void sendOrderDelRequest(int orderId) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("orderId", orderId + "");
		LogUtils.d("delOrder : orderId = " + orderId);
		HttpUtil httpUtil = new HttpUtil(new RequestHandler(
				MyOrderDetailActivity.this, "", getResources().getString(
						R.string.loading), 0));
		// 回调函数
		RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> result) {
				super.onSuccess(result);
				try {
					BaseBean resb = mGson.fromJson(result.result,
							BaseBean.class);
					if (Const.SUCCESS.equals(resb.getResult())) {
						showToast("删除成功");
						onBackPressed();
					} else {
						showToast(resb.getErrorCode());
					}
				} catch (Exception e) {
					showToast(R.string.errorMsg);
				}
			}
		};
		httpUtil.put(WebConstants.METHOD_ORDER_DEL_DETAILS, params, callback);
	}

	@Override
	public void requestCallBack(String dataJson, RequestType type) {

	}
}
