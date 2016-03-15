package com.lingnet.paylib.weixin;

import java.util.Locale;

import android.content.Context;

import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.bean.WXPerPayBean;
import com.geecity.hisenseplus.home.bean.WXPerPayResultBean;
import com.geecity.hisenseplus.home.utils.Const;
import com.geecity.hisenseplus.home.utils.NetWorkUtils;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.google.gson.Gson;
import com.ibill.lib.https.HttpUtil;
import com.ibill.lib.https.HttpUtil.RequestHttpCallBack;
import com.ibill.lib.webutils.RequestHandler;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayTools {

    private Context context;

    public WXPayTools(Context context) {
        this.context = context;
    }


    /** 发送支付请求 **/
    public void sendPrePayRequest(final String orderId) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("orderId", orderId);
        String ip = NetWorkUtils.getLocalIpAddress(context);
        params.addBodyParameter("ip", ip);
        HttpUtil httpUtil =
                        new HttpUtil(new RequestHandler(context, "", context.getResources()
                                        .getString(R.string.loading), 0));
        RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> result) {
                super.onSuccess(result);
                try {
                    WXPerPayResultBean resb =
                                    new Gson().fromJson(result.result, WXPerPayResultBean.class);
                    if (Const.SUCCESS.equals(resb.getResult())) {
                        WXPerPayBean bean = resb.getData();
                        if (bean == null) {
                            GCApplication.sApp.showToast(resb.getErrorCode());
                            return;
                        }
                        // 获取微信支付工具

                        GCApplication.sApp.sOrderId = orderId;
                        GCApplication.sApp.sOrderType = 0;
                        startPay(bean);
                    } else {
                        GCApplication.sApp.showToast(resb.getErrorCode());
                    }
                } catch (Exception e) {
                    GCApplication.sApp.showToast(context.getResources()
                                    .getString(R.string.errorMsg));
                }
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                super.onFailure(arg0, arg1);
            }
        };
        httpUtil.post(WebConstants.METHOD_ORDER_PERPAY, params, callback);
    }
    
    public interface OnPayListener{
    	void onStartPay();
    	void onCompletPay(boolean result);
    }
	private OnPayListener onPayListener;
    
    public void setOnPayStartListener(OnPayListener l){
    	this.onPayListener = l;
    }
    
    public void sendProPayRequest(WXPerPayBean bean){
        // 获取微信支付工具
        GCApplication.sApp.sOrderId = bean.getOrderId();
        GCApplication.sApp.sOrderType = 1;
        startPay(bean);
    }

    private void startPay(WXPerPayBean bean) {

        if(onPayListener != null){
            onPayListener.onStartPay();
        }
        
        GCApplication.sApp.showDialog(context,"请稍等，正在启动微信支付");
        IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);
        boolean result = msgApi.registerApp(bean.getAppid());
        if (!result) {
            GCApplication.sApp.showDialog(context,"微信支付功能注册失败");
        }
        PayReq request = new PayReq();
        request.appId = bean.getAppid();
        request.partnerId = bean.getMch_id();
        request.prepayId = bean.getPrepay_id();
        request.packageValue = "Sign=WXPay";
        request.nonceStr = bean.getNonce_str();
        request.timeStamp = genTimeStamp();
        request.sign = getSign(request, bean.getApiKey());
        boolean result1 = msgApi.sendReq(request);
        if (!result1) {
            GCApplication.sApp.showDialog(context,"发送微信支付请求失败");
        }
        if(onPayListener != null){
        	onPayListener.onCompletPay(result1);
        	GCApplication.sApp.dismissDialog();
        }
    }

    /**
     * 生成签名
     * 
     * @param apiKey
     */
    private String getSign(PayReq request, String apiKey) {
        StringBuilder sb = new StringBuilder();
        sb.append("appid=").append(request.appId).append('&').append("noncestr=")
                        .append(request.nonceStr).append('&').append("package=")
                        .append(request.packageValue).append('&').append("partnerid=")
                        .append(request.partnerId).append('&').append("prepayid=")
                        .append(request.prepayId).append('&').append("timestamp=")
                        .append(request.timeStamp).append('&').append("key=").append(apiKey);
        String packageSign =
                        MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase(Locale.CHINA);
        return packageSign;
    }

    private String genTimeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

}
