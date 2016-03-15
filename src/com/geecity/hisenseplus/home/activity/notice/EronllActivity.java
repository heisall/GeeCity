package com.geecity.hisenseplus.home.activity.notice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.utils.CommonTools;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 报名：手机号+姓名
 * 
 * @author Administrator
 * 
 */
public class EronllActivity extends ActionBarActivity{

	public static final String ERONLL_NAME = "ERONLL_NAME";
	public static final String ERONLL_PHONE = "ERONLL_PHONE";
	@ViewInject(R.id.btn_home_menu)
	private Button mTopBackBtn;
	@ViewInject(R.id.tv_home_topbar_title)
	private TextView mTopTitle;

	@ViewInject(R.id.et_ernoll_phone)
	private EditText mEtPhone;
	@ViewInject(R.id.et_ernoll_name)
	private EditText mEtName;
	
	private int mTitleId;
	public static final String KEY_TITLE = "key_title";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notice_ernoll);
		ViewUtils.inject(this);
		mTitleId = getIntent().getIntExtra(KEY_TITLE, R.string.actv_title_enroll_defalut);
		mEtPhone.setText(GCApplication.sApp.getUserInfo().getAccount());
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void configActionBar() {
		mTopBackBtn.setVisibility(View.VISIBLE);
		mTopTitle.setText(mTitleId);
	}

	@OnClick({ R.id.btn_home_menu, R.id.btn_enroll_save})
	public void OnClick(View v) {
		switch (v.getId()) {
		case R.id.btn_home_menu: // 点击返回按钮
//			onBackPressed();
			setResult(Activity.RESULT_CANCELED);
			finish();
			break;
		case R.id.btn_enroll_save:// 完成
			String phone = mEtPhone.getText().toString().trim();
			if(!CommonTools.isIllegalPhone(phone)){
				showToast("手机号码格式错误");
				return;
			}
			String name = mEtName.getText().toString().trim();
			if(TextUtils.isEmpty(name)){
				showToast("请输入姓名");
				return;
			}
			Intent intent = new Intent();
			intent.putExtra(ERONLL_NAME, name);
			intent.putExtra(ERONLL_PHONE, phone);
			setResult(Activity.RESULT_OK, intent);
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	public void requestCallBack(String dataJson, RequestType type) {
	}
}
