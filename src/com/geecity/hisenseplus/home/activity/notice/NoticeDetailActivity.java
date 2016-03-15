package com.geecity.hisenseplus.home.activity.notice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.bean.EronllResultBean;
import com.geecity.hisenseplus.home.bean.NoticeDetailBean;
import com.geecity.hisenseplus.home.bean.NoticeDetailResultBean;
import com.geecity.hisenseplus.home.utils.Const;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.ibill.lib.https.HttpUtil;
import com.ibill.lib.https.HttpUtil.RequestHttpCallBack;
import com.ibill.lib.webutils.RequestHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 公告 TODO 评论功能去掉，详情页使用标题+内嵌webview
 * 
 * @author Administrator
 * 
 */
public class NoticeDetailActivity extends ActionBarActivity {

	public static final String KEY_NOTICE_ID = "notice_id";
	@ViewInject(R.id.btn_home_menu)
	private Button mTopBackBtn;
	@ViewInject(R.id.tv_home_topbar_title)
	private TextView mTopTitle;

	@ViewInject(R.id.tv_notice_detail_title)
	private TextView mTvTitle;
	@ViewInject(R.id.tv_notice_detail_date)
	private TextView mTvDate;
	@ViewInject(R.id.btn_notice_detail_signup)
	private Button mBtnSignUp;

	@ViewInject(R.id.tv_notice_detail_count)
	private TextView mTvClickCount;
	@ViewInject(R.id.tv_detail_description)
	private TextView mTvDescription;
	@ViewInject(R.id.wb_detail_link)
	private WebView mWebView;
	
	private String mNLBeanId;
	private NoticeDetailBean mDetailBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置窗口风格为进度条
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.activity_notice_detail);
		ViewUtils.inject(this);
		initWebView();
		mBtnSignUp.setVisibility(View.GONE);
		mNLBeanId = getIntent().getStringExtra(KEY_NOTICE_ID);
		sendRequest();
	}
	
    private void setContent(String content) {
		if(!TextUtils.isEmpty(content)){
			if(content.startsWith("http://")){
				mTvDescription.setVisibility(View.GONE);
				mWebView.setVisibility(View.VISIBLE);
				mWebView.loadUrl(content);
			}else{
				mWebView.setVisibility(View.GONE);
				mTvDescription.setVisibility(View.VISIBLE);
				mTvDescription.setText(content);
			}
		}
	}

	@Override
    protected void sendRequest() {
        RequestParams params = new RequestParams();
		params.addBodyParameter("id", mNLBeanId);
        params.addBodyParameter("account", GCApplication.sApp.getUserInfo().getAccount());
        LogUtils.d("id: " + mNLBeanId + "  account : " + GCApplication.sApp.getUserInfo().getAccount());
        HttpUtil httpUtil = new HttpUtil(new RequestHandler(NoticeDetailActivity.this, "", getResources().getString(R.string.loading), 0));
    	// 回调函数
        RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> result) {
                super.onSuccess(result);
                try {
                    NoticeDetailResultBean resultBean = mGson.fromJson(result.result, NoticeDetailResultBean.class);
                    if ("1".equals(resultBean.getResult())) {
                    	mDetailBean = resultBean.getData();
                    	if(mDetailBean != null){
                    		updateUI();
                    	}
                    } else {
                        showToast(resultBean.getErrorCode());
                    }
                } catch (Exception e) {
                	e.printStackTrace();
                    showToast(R.string.errorMsg);
                }
            }
        };

        httpUtil.put(WebConstants.METHOD_GET_NOTICE_DETAIL, params, callback);
    }
        
	protected void updateUI() {
		mTvTitle.setText(mDetailBean.getTitle());
		mTvDate.setText(mDetailBean.getAddTime());
		mTvClickCount.setText(mDetailBean.getClickCount());
		setContent(mDetailBean.getContent());
		mBtnSignUp.setVisibility(Integer.parseInt(mDetailBean.getTypes())== NoticeActivity.TYPE_NOTICE ? View.GONE : View.VISIBLE);
		//报名未截止、允许报名并且该用户能够报名（未报名的），显示可点击的报名按钮
		if(mDetailBean.getIsEnrollEnd() == 0 
				&& mDetailBean.getIsAllowEnroll() == 1
				&& mDetailBean.getCanEnroll() == 1){
			mBtnSignUp.setEnabled(true);
			mBtnSignUp.setBackgroundResource(R.drawable.btn_send);
		}else{
			mBtnSignUp.setEnabled(false);
			mBtnSignUp.setBackgroundColor(getResources().getColor(R.color.txt_hint_gray));
			if(mDetailBean.getIsEnrollEnd() == 1){
				mBtnSignUp.setText("报名截止");
			}else if(mDetailBean.getIsAllowEnroll() == 0){
				mBtnSignUp.setText("暂未开始报名");
			}else if(mDetailBean.getCanEnroll() == 0){
				mBtnSignUp.setText("我已报名");
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void configActionBar() {
		mTopBackBtn.setVisibility(View.VISIBLE);
		mTopTitle.setText(R.string.activity_title_detail);
	}

	@OnClick({ R.id.btn_home_menu, R.id.btn_notice_detail_signup})
	public void OnClick(View v) {
		switch (v.getId()) {
		case R.id.btn_home_menu: // 点击返回按钮
			onBackPressed();
			break;
		case R.id.btn_notice_detail_signup:// 报名
            Bundle b = new Bundle();
            b.putInt(EronllActivity.KEY_TITLE, R.string.actv_title_live_enroll);
			startNextActivityForResult(b, EronllActivity.class, REQUEST_CODE);
		default:
			break;
		}
	}
	
	private static final int REQUEST_CODE = 1;
	
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
			String phone = data.getStringExtra(EronllActivity.ERONLL_PHONE);
			String name = data.getStringExtra(EronllActivity.ERONLL_NAME);
			sendEronllActv(phone, name);
			break;
		default:
			break;
		}
	}

	private void sendEronllActv(String phone, String name) {
        RequestParams params = new RequestParams();
        //id:通知/活动id ，account:用户账号，phone：手机号码， name:姓名
		params.addBodyParameter("id", mNLBeanId);
        params.addBodyParameter("account", GCApplication.sApp.getUserInfo().getAccount());
        params.addBodyParameter("phone", phone);
        params.addBodyParameter("name", name);
        LogUtils.d("id: " + mNLBeanId + "  account : " + GCApplication.sApp.getUserInfo().getAccount()
        		+"  phone : " + phone + " name : " + name);
        HttpUtil httpUtil = new HttpUtil(new RequestHandler(NoticeDetailActivity.this, "", getResources().getString(R.string.loading), 0));
    	// 回调函数
        RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> result) {
                super.onSuccess(result);
                try {
                    EronllResultBean resultBean = mGson.fromJson(result.result, EronllResultBean.class);
                    if (Const.SUCCESS.equals(resultBean.getResult())) {
                    	showToast("报名成功");
                        sendRequest();
                    } else {
                        showToast(resultBean.getErrorCode());
                    }
                } catch (Exception e) {
                	e.printStackTrace();
                    showToast(R.string.errorMsg);
                }
            }
        };

        httpUtil.post(WebConstants.METHOD_ENROLL_ACT, params, callback);
	}

	private void initWebView() {
		WebSettings settings = mWebView.getSettings();
		settings.setSupportZoom(true); // 支持缩放
		settings.setBuiltInZoomControls(true); // 启用内置缩放装置
		settings.setUseWideViewPort(true);
		settings.setLoadWithOverviewMode(true); // 这个似乎更有效果，自动缩到最小，能够全部显示
		// settings.setJavaScriptEnabled(true); // 启用JS脚本
		mWebView.setWebViewClient(new WebViewClient() {
			// 当点击链接时,希望覆盖而不是打开新窗口
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url); // 加载新的url
				return true; // 返回true,代表事件已处理,事件流到此终止
			}
		});

		// 点击后退按钮,让WebView后退一页(也可以覆写Activity的onKeyDown方法)
		mWebView.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					if (keyCode == KeyEvent.KEYCODE_BACK
							&& mWebView.canGoBack()) {
						mWebView.goBack(); // 后退
						return true; // 已处理
					}
				}
				return false;
			}
		});
	}

	@Override
	public void requestCallBack(String dataJson, RequestType type) {
		// TODO Auto-generated method stub

	}
}
