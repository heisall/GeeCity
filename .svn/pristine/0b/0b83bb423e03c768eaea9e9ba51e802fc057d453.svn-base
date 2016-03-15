package com.geecity.hisenseplus.home.activity.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.bean.BaseBean;
import com.geecity.hisenseplus.home.utils.Const;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.ibill.lib.https.HttpUtil;
import com.ibill.lib.https.HttpUtil.RequestHttpCallBack;
import com.ibill.lib.webutils.RequestHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class MyAdviceActivity extends ActionBarActivity {
	@ViewInject(R.id.btn_home_menu)
	private Button mTopBackBtn;
	@ViewInject(R.id.tv_home_topbar_title)
	private TextView mTopTitle;

	@ViewInject(R.id.edit_advice)
	private EditText mEtgetAdvice;
	@ViewInject(R.id.btn_advice_comit)
	private Button mBtnComit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_advice);
		ViewUtils.inject(this);
	}

	protected void sendRequest() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("userid", "" + GCApplication.sApp.getUserInfo().getAccount());
		params.addBodyParameter("content", mEtgetAdvice.getText().toString());

		HttpUtil httpUtil = new HttpUtil(new RequestHandler(
				MyAdviceActivity.this, "", getResources().getString(
						R.string.loading), 0));
		// 回调函数
		RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> result) {
				super.onSuccess(result);
				mEtgetAdvice.setText("");
				try {
					BaseBean bean = mGson.fromJson(result.result, BaseBean.class);
					if(Const.SUCCESS.equals(bean.getResult())){
						showToast("提交成功，感谢您的宝贵意见！");
					}else{
						showToast(bean.getErrorCode());
					}
				} catch (Exception e) {
					showToast(R.string.errorMsg);
				}
			}
		};
		httpUtil.put(WebConstants.METHOD_USER_MY_ADVICE, params, callback);
	}

	@OnClick({ R.id.btn_home_menu, R.id.btn_advice_comit })
	public void OnClick(View v) {
		switch (v.getId()) {
		case R.id.btn_home_menu: // 点击返回按钮
			onBackPressed();
			break;
		case R.id.btn_advice_comit:
			if(TextUtils.isEmpty(mEtgetAdvice.getText().toString())){
				showToast("请输入您的宝贵意见");
			}else{
				sendRequest();
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void configActionBar() {
		mTopTitle.setText(R.string.frg_4_title_2_advance);
	}

	@Override
	public void requestCallBack(String dataJson, RequestType type) {
		// TODO Auto-generated method stub

	}
}
