package com.lingnet.paylib.weixin;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.lidroid.xutils.util.LogUtils;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WeiPay {
    Activity activity;
    String shopName = "";// 商品名称
    String shopPice = "";// 商品价格
    PayReq req;
    IWXAPI msgApi;
    Map<String, String> resultunifiedorder;
    StringBuffer sb;
    //开放平台appid和appsecret
    public String APP_ID = "wx706df433748af20c";
    private String APP_SECRET = "f78e80ec755ab99be5a7b1e3565b5f37";
    // 商户号
    public String PARTNER_ID = "1309561701";
    // API密钥，在商户平台设置
    public String API_KEY = "f78e80ec755xinwojia2015ab99be5a7";
    // 接收微信支付异步通知回调地址
    public String NOTIFY_URL = "http://115.28.48.166:8100/appPhone/rest/order/payOrder";

    
    /**
     * 预付单ID（由后台调用微信统一下单API获取到返回给APP）
     * 
     */
    private String prepay_id = "1101000000140429eb40476f8896f4c9";
    public String SPBIll_CREATE_IP = "127.0.0.1";// 端口

    /**
     * 
     * @param activity
     * @param prepay_id  预订单id
     * @param shopName 商品名称
     * @param shopPice 商品价格
     */
    public WeiPay(Activity activity,String prepay_id, String shopName, String shopPice) {
        this.prepay_id = prepay_id;
        this.activity = activity;
        this.shopName = shopName;
        this.shopPice = shopPice;
        msgApi = WXAPIFactory.createWXAPI(activity, null);
        msgApi.registerApp(APP_ID);
    }
    
    /**
     * 
     * @param activity
     * @param app_id  appid
     * @param app_secret 微信开放平台和商户约定的密钥
     * @param partner_id 商户Id
     * @param api_key API密钥，在商户平台设置
     * @param prepay_id 预订单Id
     * @param shopName 商品名称
     * @param shopPice 商品价格
     */
    public WeiPay(Activity activity, String app_id, String app_secret, String partner_id,
                    String api_key, String prepay_id, String shopName, String shopPice) {
        if (!TextUtils.isEmpty(app_id)) {
            this.APP_ID = app_id;
        }
        if (!TextUtils.isEmpty(app_secret)) {
            this.APP_SECRET = app_secret;
        }
        if (!TextUtils.isEmpty(partner_id)) {
            this.PARTNER_ID = API_KEY;
        }
        if (!TextUtils.isEmpty(api_key)) {
            this.API_KEY = api_key;
        }

        this.prepay_id = prepay_id;
        this.activity = activity;
        this.shopName = shopName;
        this.shopPice = shopPice;
        msgApi = WXAPIFactory.createWXAPI(activity, null);
        msgApi.registerApp(APP_ID);
    }

    public String startPay() {
        String resultMsg = "";
        AsyncTask<Void, Void, GetAccessTokenResult> result = new GetAccessTokenTask().execute();
        try {
            resultMsg = result.get().expiresIn + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMsg;
    }                                                                                                                                                  // 对应的支付密�?

    // 1:获取access_token
    private class GetAccessTokenTask extends AsyncTask<Void, Void, GetAccessTokenResult> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(activity, "提示", "正在获取access_token....");
        }

        protected void onPostExecute(GetAccessTokenResult result) {
            if (dialog != null) {
                dialog.dismiss();
            }
            if (result.localRetCode == LocalRetCode.ERR_OK) {
                Toast.makeText(activity, "获取access_token成功", Toast.LENGTH_LONG).show();
                GetPrepayIdTask getPrepayId = new GetPrepayIdTask(result.accessToken);
                getPrepayId.execute();
            } else {
                Toast.makeText(activity, "获取access_token失败原因" + result.localRetCode.name(),
                                Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected GetAccessTokenResult doInBackground(Void... params) {
            GetAccessTokenResult result = new GetAccessTokenResult();
            // 发网络请求获取access token
            // App_id,审核成功 生成 密钥 请求获取
            String url =
                            String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s",
                                            APP_ID, APP_SECRET);
            byte[] buf = WeixinUtil.httpGet(url);
            if (buf == null || buf.length == 0) {
                result.localRetCode = LocalRetCode.ERR_HTTP;
                return result;
            }

            String content = new String(buf);
            result.parseFrom(content);
            return result;
        }
    }

    // 2.生成预支付订单ID
    private class GetPrepayIdTask extends AsyncTask<Void, Void, GetPrepayIdResult> {

        private ProgressDialog dialog;
        private String accessToken;

        public GetPrepayIdTask(String accessToken) {
            this.accessToken = accessToken;
        }

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(activity, "提示", "获取prepayid");
        }

        @Override
        protected void onPostExecute(GetPrepayIdResult result) {
            if (dialog != null) {
                dialog.dismiss();
            }

            if (result.localRetCode == LocalRetCode.ERR_OK) {
                Toast.makeText(activity, "获取_prepayid成功", Toast.LENGTH_LONG).show();
                sendPayReq(result);
            } else {
                Toast.makeText(activity, "获取_prepayid失败" + result.localRetCode.name(),
                                Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected GetPrepayIdResult doInBackground(Void... params) {
            // 第一步获取的 access_token
            String url =
                            String.format("https://api.weixin.qq.com/pay/genprepay?access_token=%s",
                                            accessToken);
            String entity = genProductArgs();

             LogUtils.d("doInBackground, url = " + url);
             LogUtils.d("doInBackground, entity = " + entity);

            GetPrepayIdResult result = new GetPrepayIdResult();

            byte[] buf = WeixinUtil.httpPost(url, entity);
            if (buf == null || buf.length == 0) {
                result.localRetCode = LocalRetCode.ERR_HTTP;
                return result;
            }

            String content = new String(buf);
             LogUtils.d("doInBackground, content = " + content);
            result.parseFrom(content);
            return result;
        }
    }

    private String genPackage(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
//        sb.append("key=");
//        sb.append(PARTNER_KEY); // 注意：不能hardcode在客户端，建议genPackage这个过程都由服务器端完成

        // 进行md5摘要前，params内容为原始内容，未经过url encode处理
        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();

        return URLEncodedUtils.format(params, "utf-8") + "&sign=" + packageSign;
    }  

    private static enum LocalRetCode {
        ERR_OK, ERR_HTTP, ERR_JSON, ERR_OTHER
    }

    private static class GetAccessTokenResult {

        private static final String TAG = "MicroMsg.SDKSample.PayActivity.GetAccessTokenResult";

        public LocalRetCode localRetCode = LocalRetCode.ERR_OTHER;
        public String accessToken;
        public int expiresIn;
        public int errCode;
        public String errMsg;

        public void parseFrom(String content) {

            if (content == null || content.length() <= 0) {
                localRetCode = LocalRetCode.ERR_JSON;
                return;
            }

            try {
                JSONObject json = new JSONObject(content);
                if (json.has("access_token")) { // success case
                    accessToken = json.getString("access_token");
                    expiresIn = json.getInt("expires_in");
                    localRetCode = LocalRetCode.ERR_OK;
                } else {
                    errCode = json.getInt("errcode");
                    errMsg = json.getString("errmsg");
                    localRetCode = LocalRetCode.ERR_JSON;
                }

            } catch (Exception e) {
                localRetCode = LocalRetCode.ERR_JSON;
            }
        }

        public void execute() {
            // TODO Auto-generated method stub

        }
    }

    private static class GetPrepayIdResult {

        private static final String TAG = "MicroMsg.SDKSample.PayActivity.GetPrepayIdResult";

        public LocalRetCode localRetCode = LocalRetCode.ERR_OTHER;
        public String prepayId;
        public int errCode;
        public String errMsg;

        public void parseFrom(String content) {

            if (content == null || content.length() <= 0) {
                localRetCode = LocalRetCode.ERR_JSON;
                return;
            }

            try {
                JSONObject json = new JSONObject(content);
                if (json.has("prepayid")) { // success case
                    prepayId = json.getString("prepayid");
                    localRetCode = LocalRetCode.ERR_OK;
                } else {
                    localRetCode = LocalRetCode.ERR_JSON;
                }

                errCode = json.getInt("errcode");
                errMsg = json.getString("errmsg");

            } catch (Exception e) {
                localRetCode = LocalRetCode.ERR_JSON;
            }
        }
    }

    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 建议 traceid 字段包含用户信息及订单信息，方便后续对订单状态的查询和跟
     */
    private String getTraceId() {
        return "crestxu_" + genTimeStamp();
    }

    /**
     * 注意：商户系统内部的订单Id,32个字符内、可包含字母,确保在商户系统唯
     */
    private String genOutTradNo() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private long timeStamp;
    private String nonceStr, packageValue;

    private String genSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        int i = 0;
        for (; i < params.size() - 1; i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append(params.get(i).getName());
        sb.append('=');
        sb.append(params.get(i).getValue());

        String sha1 = WeixinUtil.sha1(sb.toString());
        LogUtils.d("genSign, sha1 = " + sha1);
        return sha1;
    }

    private String genProductArgs() {
        // /json
        JSONObject json = new JSONObject();

        // 利用access_token，以及partnerid和APP_KEY生成预支付订�?
        // 获取PrepayId的操�?
        // 这里面涉及到[package 生成方法]和[app_signature 生成方法]等复杂操作参见demo代码及文�?
        try {
            json.put("appid", APP_ID);
            String traceId = getTraceId(); // traceId
                                           // 由开发�?�自定义，可用于订单的查询与跟踪，建议根据支付用户信息生成此id
            json.put("traceid", traceId);
            nonceStr = genNonceStr();
            json.put("noncestr", nonceStr);

            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
            packageParams.add(new BasicNameValuePair("bank_type", "WX"));// /支付.类型
            packageParams.add(new BasicNameValuePair("body", shopName));// /商品名称
            packageParams.add(new BasicNameValuePair("fee_type", "1"));// 货币类型
            packageParams.add(new BasicNameValuePair("input_charset", "UTF-8"));
            packageParams.add(new BasicNameValuePair("notify_url", NOTIFY_URL));
            packageParams.add(new BasicNameValuePair("out_trade_no", genOutTradNo()));
            packageParams.add(new BasicNameValuePair("partner", PARTNER_ID));// /*商户支付的号*/
            packageParams.add(new BasicNameValuePair("spbill_create_ip", SPBIll_CREATE_IP));
            packageParams.add(new BasicNameValuePair("total_fee", shopPice));// /支付金额
            packageValue = genPackage(packageParams);
            json.put("package", packageValue);
            timeStamp = genTimeStamp();
            json.put("timestamp", timeStamp);
            // /生成签名
            List<NameValuePair> signParams = new LinkedList<NameValuePair>();
            signParams.add(new BasicNameValuePair("appid", APP_ID));
//            signParams.add(new BasicNameValuePair("appkey", APP_KEY));
            signParams.add(new BasicNameValuePair("noncestr", nonceStr));
            signParams.add(new BasicNameValuePair("package", packageValue));
            signParams.add(new BasicNameValuePair("timestamp", String.valueOf(timeStamp)));
            signParams.add(new BasicNameValuePair("traceid", traceId));
            json.put("app_signature", genSign(signParams));

            json.put("sign_method", "sha1");
        } catch (Exception e) {
            return null;
        }

        return json.toString();
    }

    // 获取二次签名sign
    private void sendPayReq(GetPrepayIdResult result) {

        PayReq req = new PayReq();
        req.appId = APP_ID;// /申请的APP_Id
        req.partnerId = PARTNER_ID;
        /** 商家向财付�?�申请的商家id */
        req.prepayId = result.prepayId;// 获取�?
        req.nonceStr = nonceStr;// 随机生成�?
        req.timeStamp = String.valueOf(timeStamp);// /时间
        req.packageValue = "Sign=WXPay";

        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
//        signParams.add(new BasicNameValuePair("appkey", APP_KEY));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        /** 商家向财付�?�申请的商家id */
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));// 获取的prepayId
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
        req.sign = genSign(signParams);

        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        msgApi.sendReq(req);
    }

}
