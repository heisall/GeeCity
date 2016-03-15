package com.geecity.hisenseplus.home.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.live.MyOrderActivity.OrderType;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.ibill.lib.https.HttpUtil;
import com.ibill.lib.https.HttpUtil.RequestHttpCallBack;
import com.ibill.lib.webutils.RequestHandler;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.util.LogUtils;
import com.lingnet.paylib.weixin.Constants;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

	private IWXAPI api;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
		api.handleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		GCApplication.sApp.dismissDialog();
		String result = "";
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			result = "支付成功";
			GCApplication.sApp.showToast(result);
			// 修改订单状态为已支付（待发货）
			payByWeiChat(OrderType.UNPAY.getTag(), GCApplication.sApp.sOrderId);
			return;
		case BaseResp.ErrCode.ERR_UNSUPPORT:
		case BaseResp.ErrCode.ERR_SENT_FAILED:
		case BaseResp.ErrCode.ERR_COMM:
			result = "支付失败";
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			result = "认证失败";
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			result = "取消支付";
			break;
		default:
			break;
		}
		if (TextUtils.isEmpty(result)) {
			return;
		} else {
			GCApplication.sApp.showToast(result);
		}
		finish();
	}

	protected void payByWeiChat(int oldStatus, String orderId) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("orderId", orderId);
		if (GCApplication.sApp.sOrderType == 0) {
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
			LogUtils.d("changeOrderStatus : orderId = " + orderId
					+ ",  oldStatus = " + oldStatus + ",  status = " + status);
		} else {
			LogUtils.d("bill/changeOrder : orderId = " + orderId);
		}
		HttpUtil httpUtil = new HttpUtil(new RequestHandler(
				WXPayEntryActivity.this, "", getResources().getString(
						R.string.loading), 0, true));
		// 回调函数
		RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> result) {
				LogUtils.d(result.result);
				finish();
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				LogUtils.d(arg1 + "--" + arg0.getMessage());
				finish();
			}
		};
		if (GCApplication.sApp.sOrderType == 0) {
			httpUtil.put(WebConstants.METHOD_ORDER_CHANGE_STATUS, params,
					callback);
		} else {
			httpUtil.post(WebConstants.METHOD_BILL_CHANGEORDER, params,
					callback);
		}
	}
}
