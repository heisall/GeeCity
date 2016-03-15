package com.geecity.hisenseplus.home.activity.live;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 商品规格参数
 * 
 * @author Administrator
 * 
 */
public class GoodsDetailSpecifActivity extends ActionBarActivity{

    public static final String KEY_GG_DESC = "key_gg_desc";
	@ViewInject(R.id.btn_home_menu)
	private Button mTopBackBtn;
	@ViewInject(R.id.tv_home_topbar_title)
	private TextView mTopTitle;
	@ViewInject(R.id.tv_gg_desc)
	private TextView mTopGgDesc;
	
	@ViewInject(R.id.lvfscr_goods_detail)
	private LinearLayout mLvPics;
	@ViewInject(R.id.scrollView1)
	private ScrollView mScrollView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods_detail_ggdesc);
		ViewUtils.inject(this);
		String gg = getIntent().getStringExtra(KEY_GG_DESC);
		if(TextUtils.isEmpty(gg)){
			mTopGgDesc.setText("暂无规格参数");
		}else{
			mTopGgDesc.setText(gg);
		}
	}
	
	@Override
	public void configActionBar() {
		mTopBackBtn.setVisibility(View.VISIBLE);
		mTopTitle.setText(R.string.activity_title_goods_detail_gg);
	}
	
	@SuppressLint("NewApi")
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
	public void requestCallBack(String dataJson, RequestType type) {
		// TODO Auto-generated method stub

	}
}
