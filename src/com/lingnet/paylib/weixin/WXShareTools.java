package com.lingnet.paylib.weixin;

import android.content.Context;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXShareTools {
    
    private IWXAPI api;
    private Context context;
    
    public WXShareTools(Context context){
        this.context = context;
        regToWx();
    }
    
    private void regToWx(){
        api = WXAPIFactory.createWXAPI(context, Constants.APP_ID, true);
        api.registerApp(Constants.APP_ID);
    }

    /**
     * 分享网页到微信好友和朋友圈
     * @param url 网址
     * @param title 标题
     * @param desc 描述
     * @param type 微信好友：SendMessageToWX.Req.WXSceneSession  朋友圈SendMessageToWX.Req.WXSceneTimeline
     */
    public void share(String url, String title, String desc, int type){
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = desc;
//        Bitmap thumb = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
//        msg.thumbData = WeixinUtil.bmpToByteArray(thumb, true);
        
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = type;
        
        api.sendReq(req);
    }
}
