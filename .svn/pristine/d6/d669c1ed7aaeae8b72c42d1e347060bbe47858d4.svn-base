package com.geecity.hisenseplus.home.activity.live;

import java.util.LinkedList;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.adapter.GroupBuyListAdapter;
import com.geecity.hisenseplus.home.bean.GroupBuyBean;
import com.geecity.hisenseplus.home.bean.GroupBuyListResultBean;
import com.geecity.hisenseplus.home.utils.Const;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.geecity.hisenseplus.home.view.XListView;
import com.geecity.hisenseplus.home.view.XListView.IXListViewListener;
import com.ibill.lib.https.HttpUtil;
import com.ibill.lib.https.HttpUtil.RequestHttpCallBack;
import com.ibill.lib.webutils.RequestHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 团购列表
 * 
 * @author Administrator
 * 
 */
public class GroupBuyActivity extends ActionBarActivity implements IXListViewListener{

	@ViewInject(R.id.btn_home_menu)
	private Button mTopBackBtn;
	@ViewInject(R.id.tv_home_topbar_title)
	private TextView mTopTitle;

	@ViewInject(R.id.lv_notice)
	private XListView mLvNotice;

	private GroupBuyListAdapter mAdapte;
	protected LinkedList<GroupBuyBean> mGroupBuyList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notice);
		ViewUtils.inject(this);
		mAdapte = new GroupBuyListAdapter(this);
		mLvNotice.setPullRefreshEnable(true);
		//请求数据后根据条数决定是否显示加载更多
		mLvNotice.setPullLoadEnable(false);
		mLvNotice.setXListViewListener(this);
		mLvNotice.setAdapter(mAdapte);
		sendRequest();
	}
	
    @Override
    protected void sendRequest() {
        RequestParams params = new RequestParams();
        HttpUtil httpUtil = new HttpUtil(new RequestHandler(GroupBuyActivity.this, "", getResources().getString(R.string.loading), 0));
        RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> result) {
                super.onSuccess(result);
                mLvNotice.stopRefresh();
                mLvNotice.stopLoadMore();
                try {
                    GroupBuyListResultBean resultBean = mGson.fromJson(result.result, GroupBuyListResultBean.class);
                    if (Const.SUCCESS.equals(resultBean.getResult())) {
                    	mGroupBuyList = resultBean.getData();
                        mAdapte.setList(mGroupBuyList);
                    } else {
                        showToast(resultBean.getErrorCode());
                    }
                } catch (Exception e) {
                    showToast(R.string.errorMsg);
                }
            }
        };
        httpUtil.post(WebConstants.METHOD_GROUPBUY_LIST, params, callback);
    }

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void configActionBar() {
		mTopBackBtn.setVisibility(View.VISIBLE);
		mTopTitle.setText(R.string.actv_title_groupbuy);
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

	}

	@Override
	public void onRefresh() {
		sendRequest();
	}

	@Override
	public void onLoadMore() {
		sendRequest();
	}
}
