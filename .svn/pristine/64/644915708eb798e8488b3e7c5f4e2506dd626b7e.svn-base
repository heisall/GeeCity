package com.geecity.hisenseplus.home.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class WebActivity extends ActionBarActivity {

    public static final String KEY_YURL = "KEY_YURL";
    public static final boolean KEY_IS_SIGN = false;
    @ViewInject(R.id.btn_home_menu)
    private Button mTopBackBtn;
    @ViewInject(R.id.tv_home_topbar_title)
    private TextView mTopTitle;

    @ViewInject(R.id.wb_link)
    private WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ViewUtils.inject(this);
        initWebView();
        mWebView.loadUrl(getIntent().getStringExtra(KEY_YURL));
    }

    private void initWebView() {
        WebSettings settings = mWebView.getSettings();
        settings.setSupportZoom(true); // 支持缩放
        settings.setBuiltInZoomControls(true); // 启用内置缩放装置
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true); // 这个似乎更有效果，自动缩到最小，能够全部显示
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);
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
                    if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
                        mWebView.goBack(); // 后退
                        return true; // 已处理
                    }
                }
                return false;
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture,
                            Message resultMsg) {
                return true;
            }
        });
    }

    @OnClick({R.id.btn_home_menu})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_home_menu: // 点击返回按钮
                onBackPressed();
                break;
            default:
                break;
        }
    }

    @Override
    public void configActionBar() {
        mTopBackBtn.setVisibility(View.VISIBLE);
        mTopTitle.setText(R.string.activity_title_detail);
    }

    @Override
    public void requestCallBack(String dataJson, RequestType type) {

    }
}
