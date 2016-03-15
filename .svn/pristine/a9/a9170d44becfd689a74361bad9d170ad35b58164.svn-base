package com.geecity.hisenseplus.home.activity.live;

import java.util.LinkedList;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.adapter.CommentsAdapter;
import com.geecity.hisenseplus.home.bean.CommentBean;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.geecity.hisenseplus.home.view.XListView;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 公告 TODO 评论功能去掉，详情页使用标题+内嵌webview
 * 
 * @author Administrator
 * 
 */
public class GoodsCommentsActivity extends ActionBarActivity{

	public static final String KEY_TYPE = "comments_list";
	@ViewInject(R.id.btn_home_menu)
	private Button mTopBackBtn;
	@ViewInject(R.id.tv_home_topbar_title)
	private TextView mTopTitle;

	@ViewInject(R.id.lv_notice)
	private XListView mLvNotice;

	private LinkedList<CommentBean> mList = new LinkedList<CommentBean>();
	private CommentsAdapter mAdapte;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notice);
		ViewUtils.inject(this);
		String coms = getIntent().getStringExtra(KEY_TYPE);
		if(TextUtils.isEmpty(coms)){
		    return;
		}
		mLvNotice.setPullRefreshEnable(false);
		mList = mGson.fromJson(coms, new TypeToken<LinkedList<CommentBean>>() {}.getType());
		mAdapte = new CommentsAdapter(this);
		mLvNotice.setAdapter(mAdapte);
		mAdapte.setArrayList(mList);
	}


	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void configActionBar() {
		mTopBackBtn.setVisibility(View.VISIBLE);
		mTopTitle.setText("商品评论");
	}

	@OnClick({ R.id.btn_home_menu })
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
