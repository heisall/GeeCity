package com.lingnet.paylib.weixin;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WeiXinPay {
    Activity avtivity;
    String shopName = "";// 商品名称
    String shopPice = "";// 商品价格
    String spbill_create_ip;
    PayReq req;
    IWXAPI msgApi;
    Map<String, String> resultunifiedorder;
    StringBuffer sb;
    // appid
    public static String APP_ID = "wx706df433748af20c";
    // app_secret
    private String APP_SECRET = "f78e80ec755ab99be5a7b1e3565b5f37";
    // 商户号
    public String MCH_ID = "1309561701";
    // API密钥，在商户平台设置
    public String API_KEY = "f78e80ec755xinwojia2015ab99be5a7";
    // 接收微信支付异步通知回调地址
    public String NOTIFY_URL = "http://115.28.48.166:8100/appPhone/rest/order/payOrder";

    /**
     * 
     * @param activity
     * @param shopName 商品名称
     * @param shopPice 商品价格
     * @param APP_ID app_id
     * @param MCH_ID 商户号
     * @param API_KEY 密钥
     * @param NOTIFY_URL 接收微信支付异步通知回调地址
     * @param SPBIll_CREATE_IP 终端IP // APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
     */
    public WeiXinPay(Activity activity, String shopName, String shopPice, String APP_ID,
                    String MCH_ID, String API_KEY, String NOTIFY_URL, String SPBIll_CREATE_IP) {
        this.avtivity = activity;
        msgApi = WXAPIFactory.createWXAPI(activity, null);
        msgApi.registerApp(APP_ID);
        if (!TextUtils.isEmpty(shopName)) {
            this.shopName = shopName;
        }
        if (!TextUtils.isEmpty(shopPice)) {
            this.shopPice = shopPice;
        }
        if (!TextUtils.isEmpty(APP_ID)) {
            WeiXinPay.APP_ID = APP_ID;
        }
        if (!TextUtils.isEmpty(MCH_ID)) {
            this.MCH_ID = MCH_ID;
        }
        if (!TextUtils.isEmpty(API_KEY)) {
            this.API_KEY = API_KEY;
        }
        if (!TextUtils.isEmpty(NOTIFY_URL)) {
            this.NOTIFY_URL = NOTIFY_URL;
        }
        req = new PayReq();
        sb = new StringBuffer();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
                getPrepayId.execute();
            }
        }, 100);

        // String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
    }

    /**
     * 生成签名
     */

    private String genPackageSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(API_KEY);


        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase(Locale.CHINA);
        return packageSign;
    }

    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(API_KEY);

        this.sb.append("sign str\n" + sb.toString() + "\n\n");
        String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase(Locale.CHINA);
        return appSign;
    }

    private String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<" + params.get(i).getName() + ">");


            sb.append(params.get(i).getValue());
            sb.append("</" + params.get(i).getName() + ">");
        }
        sb.append("</xml>");

        return sb.toString();
    }

    private class GetPrepayIdTask extends AsyncTask<Void, Void, Map<String, String>> {

        private ProgressDialog dialog;


        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(avtivity, "提示", "获取appsks");
        }

        @Override
        protected void onPostExecute(Map<String, String> result) {
            if (dialog != null) {
                dialog.dismiss();
            }
            sb.append("prepay_id\n" + result.get("prepay_id") + "\n\n");

            resultunifiedorder = result;
            genPayReq();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Map<String, String> doInBackground(Void... params) {

            String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
            String entity = genProductArgs();


            byte[] buf = WeixinUtil.httpPost(url, entity);

            String content = new String(buf);
            Map<String, String> xml = decodeXml(content);

            return xml;
        }
    }

    public Map<String, String> decodeXml(String content) {

        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String nodeName = parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:

                        if ("xml".equals(nodeName) == false) {
                            // 实例化student对象
                            xml.put(nodeName, parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }

            return xml;
        } catch (Exception e) {
        }
        return null;

    }

    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    private String genOutTradNo() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private String genProductArgs() {
        StringBuffer xml = new StringBuffer();

        try {
            String nonceStr = genNonceStr();


            xml.append("</xml>");
            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
            packageParams.add(new BasicNameValuePair("appid", APP_ID));
            packageParams.add(new BasicNameValuePair("body", "weixin"));
            packageParams.add(new BasicNameValuePair("mch_id", MCH_ID));
            packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
            packageParams.add(new BasicNameValuePair("notify_url", "http://121.40.35.3/test"));
            packageParams.add(new BasicNameValuePair("out_trade_no", genOutTradNo()));
            packageParams.add(new BasicNameValuePair("spbill_create_ip", "127.0.0.1"));
            packageParams.add(new BasicNameValuePair("total_fee", "1"));
            packageParams.add(new BasicNameValuePair("trade_type", "APP"));


            String sign = genPackageSign(packageParams);
            packageParams.add(new BasicNameValuePair("sign", sign));


            String xmlstring = toXml(packageParams);

            return xmlstring;

        } catch (Exception e) {
            return null;
        }


    }

    private void genPayReq() {

        req.appId = Constants.APP_ID;
        req.partnerId = MCH_ID;
        req.prepayId = resultunifiedorder.get("prepay_id");
        req.packageValue = "Sign=WXPay";
        req.nonceStr = genNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());


        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

        req.sign = genAppSign(signParams);

        sb.append("sign\n" + req.sign + "\n\n");


        sendPayReq();

    }

    private void sendPayReq() {
        msgApi.registerApp(Constants.APP_ID);
        msgApi.sendReq(req);
    }
}
