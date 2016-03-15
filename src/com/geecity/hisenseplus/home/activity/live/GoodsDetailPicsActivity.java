package com.geecity.hisenseplus.home.activity.live;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.utils.BitmapAsset;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 商品详情页
 * 
 * @author Administrator
 * 
 */
public class GoodsDetailPicsActivity extends ActionBarActivity{

    public static final String KEY_PICS_LIST = "key_pics_list";
	@ViewInject(R.id.btn_home_menu)
	private Button mTopBackBtn;
	@ViewInject(R.id.tv_home_topbar_title)
	private TextView mTopTitle;
	@ViewInject(R.id.tv_empty)
	private TextView mTopEmpty;
	
	@ViewInject(R.id.lvfscr_goods_detail)
	private LinearLayout mLvPics;
	@ViewInject(R.id.scrollView1)
	private ScrollView mScrollView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods_detail_pics);
		ViewUtils.inject(this);
		mInflater = LayoutInflater.from(this);
		ArrayList<String> list = getIntent().getStringArrayListExtra(KEY_PICS_LIST);
		if(list != null && list.size() > 0){
			mTopEmpty.setVisibility(View.GONE);
			initGallgry(list);
		}else{
			mTopEmpty.setVisibility(View.VISIBLE);
		}
	}
	
	private LayoutInflater mInflater;
	
	private void initGallgry(List<String> pics) {
		for (int i = 0; i < pics.size(); i++) {
			final String bean = pics.get(i);
			if(bean == null){
				continue;
			}
			View view = mInflater.inflate(R.layout.item_goods_pic_list, mLvPics, false);
			ImageView head = (ImageView) view.findViewById(R.id.pic_item);
			BitmapAsset.displayImg(GoodsDetailPicsActivity.this, head, bean, R.drawable.icn_community_selected);
			mLvPics.addView(view);
		}
	}

	@Override
	public void configActionBar() {
		mTopBackBtn.setVisibility(View.VISIBLE);
		mTopTitle.setText(R.string.activity_title_goods_detail_info);
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
