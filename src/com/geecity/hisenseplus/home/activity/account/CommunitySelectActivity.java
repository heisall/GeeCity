package com.geecity.hisenseplus.home.activity.account;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.adapter.HouseBindAdapter;
import com.geecity.hisenseplus.home.bean.HouseBean;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class CommunitySelectActivity extends ActionBarActivity {

	public static final String KEY_TYPE = "bindHouse";
	@ViewInject(R.id.btn_home_menu)
	private Button mTopBackBtn;
	@ViewInject(R.id.tv_home_topbar_title)
	private TextView mTopTitle;

	@ViewInject(R.id.lv_house_bind)
	private ListView mListView;

	private ArrayList<HouseBean> mHosueBeans = new ArrayList<HouseBean>();
	private HouseBindAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_community_select);
		ViewUtils.inject(this);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				nextBindActiv(position);
			}
		});
		mAdapter = new HouseBindAdapter(this);
		mHosueBeans.add(new HouseBean("继续绑定房间", "0"));
		mHosueBeans.add(new HouseBean("游客", "1"));
		mAdapter.setArrayList(mHosueBeans);
		mListView.setAdapter(mAdapter);
	}

	@Override
	public void configActionBar() {
		mTopBackBtn.setVisibility(View.INVISIBLE);
		mTopTitle.setText(R.string.house_community_select_title);
	}

	@OnClick({ R.id.btn_home_menu})
	public void OnClick(View v) {
		switch (v.getId()) {
		case R.id.btn_home_menu: // 点击返回按钮
			onBackPressed();
			break;
		default:
			break;
		}
	}

	private void nextBindActiv(int index) {
//		GCApplication.sApp.getBeans().clear();
		Bundle b = new Bundle();
		//第一个item为进行绑定，第二个选择只选择小区
		b.putBoolean(CommunitySelectActivity.KEY_TYPE, index == 1);
		startNextActivity(b, HouseBind0Activity.class);
	}

	@Override
	public void requestCallBack(String dataJson, RequestType type) {

	}
}
