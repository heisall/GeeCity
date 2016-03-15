package com.geecity.hisenseplus.home.activity.mine;

import java.util.LinkedList;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.adapter.DiscListAdapter;
import com.geecity.hisenseplus.home.bean.DiscListBean;
import com.geecity.hisenseplus.home.bean.MyDisReslutBean;
import com.geecity.hisenseplus.home.utils.Const;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.geecity.hisenseplus.home.view.XListView;
import com.geecity.hisenseplus.home.view.XListView.IXListViewListener;
import com.ibill.lib.https.HttpUtil;
import com.ibill.lib.https.HttpUtil.RequestHttpCallBack;
import com.ibill.lib.webutils.RequestHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class MyDiscActivity extends ActionBarActivity implements
		IXListViewListener {

	public static final String KEY_DISCLIST = "mydiscBean";
	private static final int COUNT_PER_PAGE = 20;
	@ViewInject(R.id.btn_home_menu)
	private Button mTopBackBtn;
	@ViewInject(R.id.tv_home_topbar_title)
	private TextView mTopTitle;
	@ViewInject(R.id.lv_mine_discovery)
	private XListView mList;
	private DiscListAdapter mAdpter;
	private LinkedList<DiscListBean> mDetails = new LinkedList<DiscListBean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_my_dis);
		ViewUtils.inject(this);
		mAdpter = new DiscListAdapter(this);
		mList.setXListViewListener(this);
		mList.setAdapter(mAdpter);
		sendRequest(0);
	}

	private int mPageIndex = 0;

	protected void sendRequest(final int pageindex) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("pageindex", pageindex + "");
		params.addBodyParameter("countperpage", COUNT_PER_PAGE + "");
		params.addBodyParameter("userid", "" + GCApplication.sApp.getUserInfo().getId());
		params.addBodyParameter("a_id", GCApplication.sApp.getDefaultAreaInfo().getA_id());
		HttpUtil httpUtil = new HttpUtil(new RequestHandler(
				MyDiscActivity.this, "", getResources().getString(R.string.loading), 0));
		// 回调函数
		RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> result) {
				super.onSuccess(result);
				mList.stopRefresh();
				mList.stopLoadMore();
				try {
					MyDisReslutBean myDisc = mGson.fromJson(result.result,
							MyDisReslutBean.class);
					if (Const.SUCCESS.equals(myDisc.getResult())) {
						LinkedList<DiscListBean> list = myDisc.getMessage();
						if (list != null && list.size() > 0) {
							if (list.size() == COUNT_PER_PAGE) {
								mList.setPullLoadEnable(true);
							} else {
								mList.setPullLoadEnable(false);
							}
							if (pageindex > 0) {
								mDetails.addAll(list);
							} else {
								mDetails = list;
							}
							mAdpter.setArrayList(mDetails);
							mPageIndex = pageindex;
						} else {
							showToast("暂时没有任何发现");
						}
					} else {
						showToast(myDisc.getErrorCode());
					}

				} catch (Exception e) {
					showToast(R.string.errorMsg);
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				super.onFailure(arg0, arg1);
				showToast(R.string.errorMsg);
				mList.stopRefresh();
				mList.stopLoadMore();
			}
		};
		httpUtil.post(WebConstants.METHOD_MY_DISC_LIST, params, callback);
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
	
	@Override
	public void onRefresh() {
		sendRequest(0);
	}

	@Override
	public void onLoadMore() {
		sendRequest(mPageIndex + 1);
	}

	@Override
	public void configActionBar() {
		mTopTitle.setText(R.string.frg_4_title_disc);
	}

	@Override
	public void requestCallBack(String dataJson, RequestType type) {
	}

}
