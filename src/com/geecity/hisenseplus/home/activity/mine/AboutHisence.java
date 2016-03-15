package com.geecity.hisenseplus.home.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
    public class AboutHisence extends ActionBarActivity {
	private WebView mWebViewAboutHisence;
	private Button mBtnAboutBack;
   @Override
protected void onCreate(Bundle savedInstanceState) {
	   requestWindowFeature(Window.FEATURE_NO_TITLE);
	 super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_about_hisence);
	mBtnAboutBack=(Button) findViewById(R.id.btn_about_hisence_back);
	mBtnAboutBack.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			 finish();
		}
	});
	mWebViewAboutHisence=(WebView) findViewById(R.id.webView_about_hisence);
	mWebViewAboutHisence.setWebChromeClient(new WebChromeClient());
	mWebViewAboutHisence.loadUrl("http://ecmall.hisenseplus.com:88/index.php?app=article&act=content_only&article_id=6");
}
@Override
public void configActionBar() {
	// TODO Auto-generated method stub
	
}
@Override
public void requestCallBack(String dataJson, RequestType type) {
	// TODO Auto-generated method stub
	
}
}
