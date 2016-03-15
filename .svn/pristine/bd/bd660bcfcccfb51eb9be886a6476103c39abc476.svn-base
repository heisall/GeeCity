package com.geecity.hisenseplus.home.activity.live;

import java.util.LinkedList;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.WebActivity;
import com.geecity.hisenseplus.home.adapter.ShopmallADAdapter;
import com.geecity.hisenseplus.home.bean.ADBean;
import com.geecity.hisenseplus.home.bean.ADListResultBean;
import com.geecity.hisenseplus.home.utils.Const;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.geecity.hisenseplus.home.view.XListView;
import com.ibill.lib.https.HttpUtil;
import com.ibill.lib.https.HttpUtil.RequestHttpCallBack;
import com.ibill.lib.webutils.RequestHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class ShopMallADActivity extends ActionBarActivity {

    @ViewInject(R.id.btn_home_menu)
    private Button mTopBackBtn;
    @ViewInject(R.id.tv_home_topbar_title)
    private TextView mTopTitle;

    @ViewInject(R.id.xlv_ad)
    private XListView mXLvAD;
    
    private LinkedList<ADBean> mADList;
    private ShopmallADAdapter mADAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopmall_ad);
        ViewUtils.inject(this);
        mADAdapter = new ShopmallADAdapter(this);
        mXLvAD.setAdapter(mADAdapter);
        mXLvAD.setPullRefreshEnable(false);
        mXLvAD.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Bundle b = new Bundle();
				b.putString(WebActivity.KEY_YURL, mADList.get(arg2 - 1).getUrl());
				startNextActivity(b, WebActivity.class);
			}
		});
        sendRequest(0);
    }


    protected void sendRequest(int pageIndex) {
        RequestParams params = new RequestParams();
		params.addBodyParameter("a_id", GCApplication.sApp.getDefaultAreaInfo().getA_id());
        HttpUtil httpUtil = new HttpUtil(new RequestHandler(ShopMallADActivity.this, "", getResources().getString(R.string.loading), 0));
        // 回调函数
        RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> result) {
                super.onSuccess(result);
                try {
                	ADListResultBean resb = mGson.fromJson(result.result, ADListResultBean.class);
                    if (Const.SUCCESS.equals(resb.getResult())) {
                    	mADList = resb.getAd();
                    	mADAdapter.setADBeans(mADList);
                    } else {
                        showToast(resb.getErrorCode());
                    }
                } catch (Exception e) {
                    showToast(R.string.errorMsg);
                }
            }
        };
        httpUtil.post(WebConstants.METHOD_ORDER_FAVOURABLE, params, callback);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void configActionBar() {
        mTopBackBtn.setVisibility(View.VISIBLE);
        mTopTitle.setText(R.string.activity_title_shopmall);
    }

    @OnClick({R.id.btn_home_menu})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_home_menu: // 点击返回按钮
                onBackPressed();
                break;
        }
    }
    
    @Override
    public void requestCallBack(String dataJson, RequestType type) {

    }
}
