package com.geecity.hisenseplus.home.activity.property;

import java.text.DecimalFormat;
import java.util.LinkedList;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.property.model.BillsInfos;
import com.geecity.hisenseplus.home.activity.property.model.PropertyP01Adapter;
import com.geecity.hisenseplus.home.bean.ManagerHomeBean;
import com.geecity.hisenseplus.home.bean.ManagerRoomBean;
import com.geecity.hisenseplus.home.bean.WXPerPayBean;
import com.geecity.hisenseplus.home.bean.WXPerPayResultBean;
import com.geecity.hisenseplus.home.utils.BitmapAsset;
import com.geecity.hisenseplus.home.utils.Const;
import com.geecity.hisenseplus.home.utils.NetWorkUtils;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.geecity.hisenseplus.home.view.ListViewForScrollView;
import com.geecity.hisenseplus.home.view.RoundImageView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.ibill.lib.https.HttpUtil;
import com.ibill.lib.https.HttpUtil.RequestHttpCallBack;
import com.ibill.lib.webutils.RequestHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lingnet.paylib.weixin.WXPayTools;
import com.lingnet.paylib.weixin.WXPayTools.OnPayListener;

/**
 * 物业账单
 * 
 * @author Administrator
 * 
 */
public class ProPayOrderActivity extends ActionBarActivity {

	public static final String KEY_PAY_LIST = "key_pay_list";
	@ViewInject(R.id.btn_home_menu)
	private Button mTopBackBtn;
	@ViewInject(R.id.tv_home_topbar_title)
	private TextView mTopTitle;

	@ViewInject(R.id.img_pp_head)
	private RoundImageView mImgHead;
	// 小区
	@ViewInject(R.id.tv_pp_name)
	private TextView mTvName;
	// 单元
	@ViewInject(R.id.tv_pp_no)
	private TextView mTvNO;
	@ViewInject(R.id.xl_payment_left)
	private ListViewForScrollView mLvFs;// 账单列表
	@ViewInject(R.id.tv_payment_price)
	private TextView mTvTotalPrice;// 总计价格
	@ViewInject(R.id.btn_payment_pay)
	private Button mBtnPay;// 支付

	@ViewInject(R.id.rtly_pay_by_weichat)
	private RelativeLayout mRtlyPayWeiChat;
	@ViewInject(R.id.img_payment_pay_by_selected_weichat)
	private ImageView mImgWeiChat;
	@ViewInject(R.id.rtly_pay_by_alipay)
	private RelativeLayout mRtlyPayAlipay;
	@ViewInject(R.id.img_payment_pay_by_selected_alipay)
	private ImageView mImgAlipay;

	private PropertyP01Adapter mAdapter;
	private LinkedList<BillsInfos> mBeans = new LinkedList<BillsInfos>();
	// 总价格
	private double mTotalPrice = 0;
	// 缴费订单的ID串
	private String mOrderIds = "";

	// 在未支付订单选择后更新，进入订单确认界面时使用
	public LinkedList<BillsInfos> mWaitPayBills;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pro_pay_order);
		ViewUtils.inject(this);
		BitmapAsset.displayImg(this, mImgHead, GCApplication.sApp.getUserInfo()
				.getPhoto(), R.drawable.icon_default_head);
		// 获取房源信息
		getRoomInfo();
		mAdapter = new PropertyP01Adapter(this);
		mAdapter.setOnItemSelected(false);
		mLvFs.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			}
		});
		mBeans = GCApplication.sApp.mWaitPayBills;
		mAdapter.setList(mBeans);
		mLvFs.setAdapter(mAdapter);
		for (BillsInfos bean : mBeans) {
			mTotalPrice += bean.getTotalPrice();
			mOrderIds += bean.getIds() + ",";
		}
		while (mOrderIds.endsWith(",")) {
			mOrderIds = mOrderIds.substring(0, mOrderIds.length() - 1);
		}
		mTvTotalPrice.setText(String.valueOf(reserved2DecimalPlaces(mTotalPrice)));
	}

	@Override
	public void configActionBar() {
		mTopBackBtn.setVisibility(View.VISIBLE);
		mTopTitle.setText("缴费详情");
	}


	@OnClick({ R.id.btn_home_menu, R.id.rtly_pay_by_weichat, R.id.rtly_pay_by_alipay,
			R.id.btn_payment_pay })
	private void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_home_menu:
			onBackPressed();
			break;
		case R.id.rtly_pay_by_weichat:// 微信支付
			mImgWeiChat.setVisibility(View.VISIBLE);
			mImgAlipay.setVisibility(View.INVISIBLE);
			break;
		case R.id.rtly_pay_by_alipay:// 支付宝
			// mImgWeiChat.setVisibility(View.INVISIBLE);
			// mImgAlipay.setVisibility(View.VISIBLE);
			showToast("支付宝支付功能即将开放，敬请期待");
			break;
		case R.id.btn_payment_pay:// 支付
			// 生成预订单后进入微信支付界面
			requestPrepayBills();
			break;
		default:
			break;
		}
	}

	/**
	 * 获取服务器数据
	 * 
	 * @Title: getData void
	 */
	// 获取账单列表
	private void requestPrepayBills() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("orderId", mOrderIds);
		String ip = NetWorkUtils.getLocalIpAddress(ProPayOrderActivity.this);
		params.addBodyParameter("ip", ip);
		params.addBodyParameter("money", mTvTotalPrice.getText().toString());
		LogUtils.i("orderId: " + mOrderIds + "\nip:" + ip + ", money: " + mTvTotalPrice.getText().toString());
		HttpUtil httpUtil = new HttpUtil(new RequestHandler(ProPayOrderActivity.this, "",
				getResources().getString(R.string.loading), 0));
		// 回调函数
		RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> result) {
				super.onSuccess(result);
				WXPerPayResultBean bean = new Gson().fromJson(result.result, WXPerPayResultBean.class);
				if(!Const.SUCCESS.equals(bean.getResult())){
					showToast(bean.getErrorCode());
				}
				
				WXPerPayBean pBean = bean.getData();
				// 预定订单生成后，调起微信支付
				if(pBean != null && !TextUtils.isEmpty(pBean.getOrderId())){
					WXPayTools tools = new WXPayTools(ProPayOrderActivity.this);
					tools.setOnPayStartListener(new OnPayListener() {
						
						@Override
						public void onStartPay() {
//							showToast("onStartPay");
						}
						
						@Override
						public void onCompletPay(boolean result) {
							// TODO Auto-generated method stub
//						    showToast("onCompletPay : " + result);
							finish();
						}
					});
					tools.sendProPayRequest(pBean);
				}
			}
		};
		httpUtil.post(WebConstants.METHOD_BILL_PERPAY, params, callback);
	}

	@Override
	public void requestCallBack(String dataJson, RequestType type) {

	}

	/** 获取房源信息 */
	private void getRoomInfo() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("a_id", GCApplication.sApp.getDefaultAreaInfo()
				.getA_id());
		params.addBodyParameter("userid", GCApplication.sApp.getUserInfo()
				.getId() + "");
		HttpUtil httpUtil = new HttpUtil(new RequestHandler(
				ProPayOrderActivity.this, "", getResources().getString(
						R.string.loading), 0));
		// 回调函数
		RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> result) {
				super.onSuccess(result);
				try {
					Gson gson = new Gson();
					ManagerHomeBean resBean = gson.fromJson(result.result,
							ManagerHomeBean.class);
					if ("1".equals(resBean.getResult())) {
						ManagerRoomBean room = resBean.getRoom();
						if (room != null) {
							if (TextUtils.isEmpty(room.getA_name())
									|| TextUtils.isEmpty(room.getB_id())
									|| TextUtils.isEmpty(room.getR_id())) {
								return;
							}
							mTvName.setText(room.getA_name() + "小区");
							mTvNO.setText((room.getB_id() + "号楼"
									+ room.getR_dy() + "单元" + room.getR_id())
									.replace("null", ""));

							b_id = room.getB_id();
							r_dy = room.getR_dy();
							r_id = room.getR_id();
						}
					}
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		httpUtil.post(WebConstants.METHOD_GET_MANAGER_HOMEPAGE, params,
				callback);
	}

	/** 提供fragment使用的相关参数 */
	public static String b_id = null;
	public static int r_dy = 0;
	public static String r_id = null;

	/** 保留一位小数 */
	private double reserved2DecimalPlaces(double oldPrice) {
		DecimalFormat df = new DecimalFormat("#.00");
		String v = df.format(oldPrice);
		double newValue = 0;
		if (isNumber(v)) {
			newValue = Double.parseDouble(v);
		}

		return newValue;
	}
	
	/** 判断是否可以转化为数字 */
	private boolean isNumber(String string) {
		try {
			Double.parseDouble(string);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
