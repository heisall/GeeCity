package com.geecity.hisenseplus.home.activity.live;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.live.MyOrderActivity.OrderType;
import com.geecity.hisenseplus.home.adapter.OrderGoodsListAdapter;
import com.geecity.hisenseplus.home.bean.AddressListBean;
import com.geecity.hisenseplus.home.bean.BaseBean;
import com.geecity.hisenseplus.home.bean.OrderCommitResultBean;
import com.geecity.hisenseplus.home.bean.OrderInfoResultBean;
import com.geecity.hisenseplus.home.bean.ShopCarListBean;
import com.geecity.hisenseplus.home.utils.Const;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.geecity.hisenseplus.home.view.ListViewForScrollView;
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

public class OrderCommitActivity extends ActionBarActivity {

    public static final String SHOPCAR_LIST = "shopcar_list";
    private static final int REQUEST_CODE = 0x02;
	@ViewInject(R.id.btn_home_menu)
    private Button mTopBackBtn;
    @ViewInject(R.id.tv_home_topbar_title)
    private TextView mTopTitle;

    @ViewInject(R.id.rtly_order_pay_address)
    private RelativeLayout mRtlyAddress;
    @ViewInject(R.id.tv_order_pay_person_info)
    private TextView mTvPerson;
    @ViewInject(R.id.tv_order_pay_address)
    private TextView mTvAddress;
    @ViewInject(R.id.btn_order_pay_changed_address)
    private Button mBtnAddress;
    
    @ViewInject(R.id.rtly_order_pay_way_hdfk)
    private RelativeLayout mRtlyHdfk;
    @ViewInject(R.id.img_order_pay_by_hdfk)
    private ImageView mImgPayByHdfk;
    @ViewInject(R.id.rtly_order_pay_way_weichat)
    private RelativeLayout mRtlyWechat;
    @ViewInject(R.id.img_order_pay_by_wechat)
    private ImageView mImgPayByWechat;
    @ViewInject(R.id.textView022)
    private TextView mTvPayMethod;
    @ViewInject(R.id.line03)
    private TextView mTvSplitLine;
    
    @ViewInject(R.id.lvfs_order_pay_goods)
    private ListViewForScrollView mLvfsGoods;
    
    @ViewInject(R.id.tv_order_pay_kdfy)
    private TextView mTvCourier;
    @ViewInject(R.id.et_order_pay_mark)
    private EditText mEtMark;
    
    @ViewInject(R.id.tv_order_pay_total_price)
    private TextView mTvTotalPrice;
    @ViewInject(R.id.btn_order_pay)
    private Button mBtnPay;
    
    private ArrayList<ShopCarListBean> mShopCarList;
    private String mRecIds = "";//存放购物车商品的id序列
    private OrderGoodsListAdapter mAdapter;
    private double mTotalPrices;
    private AddressListBean mAddress;
    private int mSendMethodId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_pay);
        ViewUtils.inject(this);
        mShopCarList = getIntent().getParcelableArrayListExtra(SHOPCAR_LIST);
        mAdapter = new OrderGoodsListAdapter(this);
        mLvfsGoods.setAdapter(mAdapter);
        if(mShopCarList == null){
        	LogUtils.d("selectBeans == null");
        	return;
        }else{
            for (int i = 0; i < mShopCarList.size(); i++) {
                ShopCarListBean spcb = mShopCarList.get(i);
                mTotalPrices += (spcb.getQuantity() * spcb.getPrice());
				mRecIds += spcb.getRec_id();
                LogUtils.d(spcb.getGoods_name() + "  --  "+i);
                if(i < mShopCarList.size() - 1){
                    mRecIds += ",";
                }
            }
        }
        
        if(isScore()){
        	mRtlyWechat.setVisibility(View.GONE);
        	mTvSplitLine.setVisibility(View.GONE);
        	mTvPayMethod.setText("积分兑换");
        	mBtnPay.setText("兑换");
        	mTvTotalPrice.setText(mTotalPrices+" 积分");
        }else{
        	mTvPayMethod.setText("货到付款");
        	mRtlyWechat.setVisibility(View.VISIBLE);
        	mTvSplitLine.setVisibility(View.VISIBLE);
        	mBtnPay.setText("提交");
        	mTvTotalPrice.setText("￥"+ mTotalPrices);
        }
        mAdapter.setList(mShopCarList);
        mAdapter.notifyDataSetChanged();
        
        sendRequest();
    }

    private boolean isScore() {
		return (mShopCarList!=null && mShopCarList.size() > 0 && mShopCarList.get(0).isScore());
	}

	@Override
    protected void sendRequest() {
    	super.sendRequest();
        RequestParams params = new RequestParams();
		params.addBodyParameter("account", GCApplication.sApp.getUserInfo().getAccount());
		params.addBodyParameter("storeId", mShopCarList.get(0).getStore_id() + "");
        HttpUtil httpUtil = new HttpUtil(new RequestHandler(OrderCommitActivity.this, "", getResources().getString(R.string.loading), 0));
        // 回调函数
        RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> result) {
                super.onSuccess(result);
                try {
                    OrderInfoResultBean resb = mGson.fromJson(result.result, OrderInfoResultBean.class);
                    if (Const.SUCCESS.equals(resb.getResult())) {
                    	mAddress = resb.getAddress();
                    	if(mAddress != null){
                    		mTvPerson.setText(mAddress.getConsignee() + "  " + mAddress.getPhone_tel());
                    		mTvAddress.setText(mAddress.getRegion_name() + "  " + mAddress.getAddress());
                    	}
                    	mSendMethodId = 0;
                    } else {
                        showToast(resb.getErrorCode());
                    }
                } catch (Exception e) {
                    showToast(R.string.errorMsg);
                }
            }
        };
        httpUtil.post(WebConstants.METHOD_ORDER_GET_ORDER_INFO, params, callback);
    
    }
    
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void configActionBar() {
        mTopBackBtn.setVisibility(View.VISIBLE);
        mTopTitle.setText(R.string.activity_title_order_pay);
    }

    @OnClick({R.id.btn_home_menu, 
    	R.id.rtly_order_pay_address, R.id.tv_order_pay_person_info,R.id.tv_order_pay_address,R.id.btn_order_pay_changed_address,
    	R.id.rtly_order_pay_way_hdfk, R.id.rtly_order_pay_way_weichat,R.id.btn_order_pay})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_home_menu: // 点击返回按钮
                onBackPressed();
                break;
            case R.id.rtly_order_pay_address:// 修改地址
            case R.id.tv_order_pay_person_info:
            case R.id.tv_order_pay_address:
            case R.id.btn_order_pay_changed_address:
            	startNextActivityForResult(null, AddressActivity.class, REQUEST_CODE);
                break;
            case R.id.rtly_order_pay_way_hdfk:// 货到付款
            	mImgPayByHdfk.setVisibility(View.VISIBLE);
            	mImgPayByWechat.setVisibility(View.INVISIBLE);
            	mBtnPay.setText("提交");
                break;
            case R.id.rtly_order_pay_way_weichat:// 微信支付
            	mImgPayByWechat.setVisibility(View.VISIBLE);
            	mImgPayByHdfk.setVisibility(View.INVISIBLE);
            	mBtnPay.setText("去付款");
                break;
            case R.id.btn_order_pay:// 支付
            	if(mShopCarList.get(0).getRec_id() == -100){
            		sendDirectPayRequest();
            	}else{
            		sendShopCarPayRequest();
            	}
                break;
            default:
                break;
        }
    }

    /*
     *直接购买的付款 ，区别于购物车购买方式，没有购物车ID，适用于积分兑换
     */
    private void sendDirectPayRequest() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("goodsId",mShopCarList.get(0).getGoods_id()+"");
        params.addBodyParameter("quantity",mShopCarList.get(0).getQuantity()+"");
        params.addBodyParameter("account",GCApplication.sApp.getUserInfo().getAccount());
		params.addBodyParameter("postscript", mEtMark.getText().toString().trim());
		params.addBodyParameter("addrId", mAddress.getAddr_id() + "");
		params.addBodyParameter("shippingId", mSendMethodId + "");
		params.addBodyParameter("shippingFee", "0");
		LogUtils.d(WebConstants.METHOD_ORDER_DIRECTBUY + " ： goodsId=" + mShopCarList.get(0).getGoods_id()
				+"  quantity=" + mShopCarList.get(0).getQuantity()
				+"  account=" + GCApplication.sApp.getUserInfo().getAccount()
				+"  postscript="+mEtMark.getText().toString().trim()
				+"  addrId="+mAddress.getAddr_id()
				+"  shippingId=" + mSendMethodId + "  shippingFee=0");
        HttpUtil httpUtil = new HttpUtil(new RequestHandler(OrderCommitActivity.this, "", getResources().getString(R.string.loading), 1));
        // 回调函数
        RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> result) {
                super.onSuccess(result);
                try {
                    BaseBean resb = mGson.fromJson(result.result, BaseBean.class);
                    if (Const.SUCCESS.equals(resb.getResult())) {
                        OrderCommitResultBean bean = mGson.fromJson(result.result, OrderCommitResultBean.class);
                    	if(isScore()){//积分兑换
                            chagerOrderStatus(OrderType.UNPAY.getTag(), bean.getData(),"兑换");
                    	}else if("提交".equals(mBtnPay.getText().toString())){//货到付款
                    		chagerOrderStatus(OrderType.UNPAY.getTag(), bean.getData(), "提交");
                    	}else{//在线支付购买
                    		showToast("提交成功，即将进行支付");
                    		String orderId = bean.getData();
                    		startPay(orderId);
                        }
                    } else {
                        showToast(resb.getErrorCode());
                    }
                } catch (Exception e) {
                    showToast(R.string.errorMsg);
                }
            }
        };
        httpUtil.put(WebConstants.METHOD_ORDER_DIRECTBUY, params, callback);
	}
    
    /**
     * 修改订单状态
     * @param oldStatus 初始状态
     * @param orderId 订单ID
     * @param resultDesc 修改成功后的提示信息（主要区别于积分商品和普通商品）
     */
    protected void chagerOrderStatus(int oldStatus, String orderId, final String resultDesc) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("orderId", orderId);
        OrderType type = OrderType.getType(oldStatus);
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
        params.addBodyParameter("status", "" + status);
        LogUtils.d("changeOrderStatus : orderId = " + orderId + ",  oldStatus = " + oldStatus + ",  status = " + status);
        HttpUtil httpUtil =
                        new HttpUtil(new RequestHandler(OrderCommitActivity.this, "", getResources()
                                        .getString(R.string.loading), 0));
        // 回调函数
        RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> result) {
                LogUtils.d(result.result);
                try {
                    BaseBean resb = mGson.fromJson(result.result, BaseBean.class);
                    if (Const.SUCCESS.equals(resb.getResult())) {
                        showToast(resultDesc + "成功");
                    } else {
                        showToast(resb.getErrorCode());
                    }
                } catch (Exception e) {
                    showToast(R.string.errorMsg);
                }
                onBackPressed();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                LogUtils.d(arg1 + "--" + arg0.getMessage());
                showToast(resultDesc + "失败");
                finish();
            }
        };
        httpUtil.put(WebConstants.METHOD_ORDER_CHANGE_STATUS, params, callback);
    }
    
    /*
     * 从购物车发起购买，区别于直接购买，有购物车ID
     */
	private void sendShopCarPayRequest() {
        RequestParams params = new RequestParams();
		params.addBodyParameter("storeId", mShopCarList.get(0).getStore_id() + "");
		params.addBodyParameter("recIds", mRecIds);
		params.addBodyParameter("postscript", mEtMark.getText().toString().trim());
		params.addBodyParameter("addrId", mAddress.getAddr_id() + "");
		params.addBodyParameter("shippingId", mSendMethodId + "");
		params.addBodyParameter("shippingFee", "0");
		LogUtils.d("storeId=" + mShopCarList.get(0).getStore_id()
				+"  recIds=" + mRecIds
				+"  postscript="+mEtMark.getText().toString().trim()
				+"  addrId="+mAddress.getAddr_id()
				+"  shippingId=" + mSendMethodId + "  shippingFee=0");
        HttpUtil httpUtil = new HttpUtil(new RequestHandler(OrderCommitActivity.this, "", getResources().getString(R.string.loading), 0));
        RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> result) {
                super.onSuccess(result);
                try {
                    BaseBean resb = mGson.fromJson(result.result, BaseBean.class);
                    if (Const.SUCCESS.equals(resb.getResult())) {
                        OrderCommitResultBean bean = mGson.fromJson(result.result, OrderCommitResultBean.class);
                        if("提交".equals(mBtnPay.getText().toString())){//货到付款
                            chagerOrderStatus(OrderType.UNPAY.getTag(), bean.getData(), "提交");
                        }else{//在线支付购买
                            showToast("提交成功，即将进行支付");
                            startPay(bean.getData());
                        }
                    } else {
                        showToast(resb.getErrorCode());
                    }
                } catch (Exception e) {
                    showToast(R.string.errorMsg);
                }
            }
        };
        httpUtil.put(WebConstants.METHOD_ORDER_SAVE, params, callback);
	}
    
    protected void startPay(String orderId) {
        if(TextUtils.isEmpty(orderId)){
            return;
        }
        // 获取微信支付工具
        WXPayTools tools = new WXPayTools(OrderCommitActivity.this);
        tools.sendPrePayRequest(orderId);
        tools.setOnPayStartListener(new OnPayListener() {
            
            @Override
            public void onStartPay() {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void onCompletPay(boolean result) {
                if(result){
                    finish();
                }
            }
        });
	}

	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode != REQUEST_CODE){
            return;
        }
        switch (resultCode) {
        case Activity.RESULT_CANCELED:
            
            break;
        case Activity.RESULT_OK:
            mAddress = (AddressListBean) data.getSerializableExtra(AddressActivity.SELECTEED_ADDRESS);
            mTvPerson.setText(mAddress.getConsignee() + "  " + mAddress.getPhone_tel());
            mTvAddress.setText(mAddress.getRegion_name() + "  " + mAddress.getAddress());
            break;
        default:
            break;
        }
    }

	@Override
    public void requestCallBack(String dataJson, RequestType type) {

    }
}
