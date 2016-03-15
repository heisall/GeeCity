package com.geecity.hisenseplus.home;

import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.geecity.hisenseplus.home.view.XListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public abstract class BaseListActivity extends ActionBarActivity {

	@ViewInject(R.id.btn_home_menu)
	private Button mTopBackBtn;
	@ViewInject(R.id.tv_home_topbar_title)
	private TextView mTopTitle;

	@ViewInject(R.id.xl_base)
	private XListView mXList;

	protected abstract int getActivityTitle();
	protected abstract BaseAdapter getAdapter();
	protected abstract void onItemClicked(int position);
	/**
	 * Activity启动后执行此接口获取数据，获取成功后调用refresh接口刷新界面
	 */
	protected abstract void requestDate();
	
	@Override
	public void configActionBar() {
		mTopBackBtn.setVisibility(View.VISIBLE);
		mTopTitle.setText(getActivityTitle());
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
	
	protected void refresh(BaseAdapter adapter) {
		mXList.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

}
