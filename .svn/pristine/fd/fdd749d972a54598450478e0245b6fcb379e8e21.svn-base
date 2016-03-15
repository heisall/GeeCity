package com.geecity.hisenseplus.home.activity.mine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.estate.EstateActivity.ESTATE;
import com.geecity.hisenseplus.home.fragment.MyRentSaleFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class MyRentSaleActivity extends FragmentActivity {
	@ViewInject(R.id.btn_home_menu)
	private Button mTopBackBtn;
	@ViewInject(R.id.tv_home_topbar_title)
	private TextView mTopTitle;
	
	@ViewInject(R.id.framlayout)
	private FrameLayout mFrameLayout;
	
	@ViewInject(R.id.radiobtn_rent)
	private Button mBtnRent;
	@ViewInject(R.id.radiobtn_sale)
	private Button mBtnSale;
	@ViewInject(R.id.textview_sale)
	private TextView mImageViewSale;
	@ViewInject(R.id.textview_rent)
	private TextView mImageViewRent;

	FragmentTransaction mTrans;
	private MyRentSaleFragment mFragmentMyRent;
	private MyRentSaleFragment mFragmentMySale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mine_estate);
		ViewUtils.inject(this);
		mTopTitle.setText(R.string.frg_4_title_estate);
		showRentFragment(ESTATE.SECOND_HAND);
	}

	@OnClick({ R.id.btn_home_menu, R.id.radiobtn_rent, R.id.radiobtn_sale})
	public void OnClick(View v) {
		switch (v.getId()) {
		case R.id.btn_home_menu: // 点击返回按钮
			onBackPressed();
			break;
		case R.id.radiobtn_rent:
			mImageViewRent.setVisibility(View.VISIBLE);
			mImageViewSale.setVisibility(View.INVISIBLE);
			mBtnRent.setTextColor(getResources().getColor(R.color.txt_hisense_green));
			mBtnSale.setTextColor(getResources().getColor(R.color.dark_gray));
			showRentFragment(ESTATE.RENT);
			break;
		case R.id.radiobtn_sale:
			mImageViewRent.setVisibility(View.INVISIBLE);
			mImageViewSale.setVisibility(View.VISIBLE);
			mBtnRent.setTextColor(getResources().getColor(R.color.dark_gray));
			mBtnSale.setTextColor(getResources().getColor(R.color.txt_hisense_green));
			showRentFragment(ESTATE.SECOND_HAND);
			break;
		default:
			break;
		}
	}

	private void showRentFragment(ESTATE type) {
		switch (type) {
		case RENT:
			if(mFragmentMyRent == null){
				mFragmentMyRent = new MyRentSaleFragment(type);
			}
			showFragment(mFragmentMyRent);
			break;
		case SECOND_HAND:
			if(mFragmentMySale == null){
				mFragmentMySale = new MyRentSaleFragment(type);
			}
			showFragment(mFragmentMySale);
			break;

		default:
			break;
		}
	}

	private static Fragment sCurrentFrgt;

	private void showFragment(Fragment newFrgt) {
		mTrans = getSupportFragmentManager().beginTransaction();
		// 切换时不刷新 fragment
		if (!newFrgt.isAdded()) {
			if (sCurrentFrgt == null) {
				mTrans.add(R.id.framlayout, newFrgt).commit();
			} else {
				mTrans.add(R.id.framlayout, newFrgt).hide(sCurrentFrgt).show(newFrgt).commit();
			}
		} else {
			mTrans.hide(sCurrentFrgt).show(newFrgt).commit();
		}
		sCurrentFrgt = newFrgt;
	}
}
