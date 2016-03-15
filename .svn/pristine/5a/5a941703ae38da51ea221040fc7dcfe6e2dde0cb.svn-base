package com.geecity.hisenseplus.home.activity.account;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.WebActivity;
import com.geecity.hisenseplus.home.activity.mine.MyEstateActivity;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class HouseBind4Activity extends ActionBarActivity {

	private static final String USER_AGREEMENT_URL = "http://ecmall.hisenseplus.com:88/index.php?app=article&act=content_only&article_id=7";
	@ViewInject(R.id.btn_home_menu)
	private Button mTopBackBtn;
	@ViewInject(R.id.tv_home_topbar_title)
	private TextView mTopTitle;
	
	@ViewInject(R.id.tv_house_bind_4_xieyi)
	private TextView mTvXieYi;

	@ViewInject(R.id.btn_house_bind_4_city)
	private Button mBtnCity;
	@ViewInject(R.id.btn_house_bind_4_area)
	private Button mBtnArea;
	@ViewInject(R.id.btn_house_bind_4_unit)
	private Button mBtnUnit;
	@ViewInject(R.id.cb_house_bind_4)
	private CheckBox mCbAgree;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_house_bind_4_info);
		ViewUtils.inject(this);
		mCbAgree.setChecked(true);
        mIsAddModel = getIntent().getBooleanExtra(MyEstateActivity.KEY_ADD_BIND, false);
    }
    private boolean mIsAddModel;

	@Override
	protected void onResume() {
		super.onResume();
		mBtnCity.setText(GCApplication.sApp.getBeans().get(0).getA_name());
		mBtnArea.setText(GCApplication.sApp.getBeans().get(1).getA_name());
		mBtnUnit.setText(GCApplication.sApp.getBeans().get(2).getA_name()
				+ GCApplication.sApp.getBeans().get(3).getA_name());
	}

	@Override
	public void configActionBar() {
		mTopBackBtn.setVisibility(View.VISIBLE);
		mTopTitle.setText(R.string.house_bind_title_4);
	}

	@OnClick({ R.id.btn_home_menu, R.id.btn_house_bind_4_city,
			R.id.btn_house_bind_4_area, R.id.btn_house_bind_4_unit,
			R.id.btn_house_bind_next, R.id.tv_house_bind_4_xieyi})
	public void OnClick(View v) {
		switch (v.getId()) {
		case R.id.btn_home_menu: // 点击返回按钮
			onBackPressed();
			break;
		case R.id.btn_house_bind_4_city:// 重新选择城市
			startNextActivity(null, HouseBind0Activity.class, true);
			break;
		case R.id.btn_house_bind_4_area:// 重新选择小区
			startNextActivity(null, HouseBind1Activity.class, true);
			break;
		case R.id.btn_house_bind_4_unit:// 重新选择楼房编号
			startNextActivity(null, HouseBind2Activity.class, true);
			break;
		case R.id.btn_house_bind_next:// 下一步
			if (mCbAgree.isChecked()) {
                Bundle b = new Bundle();
                b.putBoolean(MyEstateActivity.KEY_ADD_BIND, mIsAddModel);
				startNextActivity(b, HouseBind5Activity.class, true);
			} else {
				showToast("请阅读用户许可协议，并同意后方可进入下一步");
			}
			break;
		case R.id.tv_house_bind_4_xieyi://用户协议
			Bundle b = new Bundle();
			
			b.putString(WebActivity.KEY_YURL, USER_AGREEMENT_URL);
			startNextActivity(b, WebActivity.class);
			break;
		default:
			break;
		}
	}

	@Override
	public void requestCallBack(String dataJson, RequestType type) {

	}
}
