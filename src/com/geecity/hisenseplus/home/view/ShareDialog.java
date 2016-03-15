package com.geecity.hisenseplus.home.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.geecity.hisenseplus.home.R;
import com.lingnet.paylib.weixin.WXShareTools;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;

public class ShareDialog extends Dialog implements android.view.View.OnClickListener {

    private Button mBtnWechat;
    private Button mBtnWxcircle;
    private Context context;
    private String url;
    private String title;
    private String desc;

    /**
     * 分享网页到微信好友和朋友圈
     * 
     * @param url 网址
     * @param title 标题
     * @param desc 描述
     * @param type 微信好友：SendMessageToWX.Req.WXSceneSession 朋友圈SendMessageToWX.Req.WXSceneTimeline
     */
    public ShareDialog(Context context, String url, String title, String desc) {
        super(context, R.style.MyDialog);
        this.context = context;
        this.url = url;
        this.title = title;
        this.desc = desc;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laout_share_dialog);
        setCanceledOnTouchOutside(true);
        initView();
    }

    private void initView() {
        mBtnWechat = (Button) findViewById(R.id.btn_share_wechat);
        mBtnWxcircle = (Button) findViewById(R.id.btn_share_wxcircle);
        mBtnWechat.setOnClickListener(this);
        mBtnWxcircle.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_share_wechat: {
                WXShareTools tools = new WXShareTools(context);
                tools.share(url, title, desc, SendMessageToWX.Req.WXSceneSession);
            }
                break;
            case R.id.btn_share_wxcircle: {
                WXShareTools tools = new WXShareTools(context);
                tools.share(url, title, desc, SendMessageToWX.Req.WXSceneTimeline);
            }
                break;
        }
        dismiss();
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        dismiss();
    }

}
