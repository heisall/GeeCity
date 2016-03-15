package com.geecity.hisenseplus.home.activity.notice;

import java.util.LinkedList;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.adapter.NoticeListAdapter;
import com.geecity.hisenseplus.home.bean.NoticeListBean;
import com.geecity.hisenseplus.home.bean.NoticeListResultBean;
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
 * 公告 TODO 评论功能去掉，详情页使用标题+内嵌webview
 * 
 * @author Administrator
 * 
 */
public class NoticeActivity extends ActionBarActivity implements IXListViewListener{

	public static final String KEY_TYPE = "activ_type";
	// 公告类型：通知
	public static final int TYPE_NOTICE = 0;
	// 公告类型：活动
	public static final int TYPE_LIVE = 1;
	@ViewInject(R.id.btn_home_menu)
	private Button mTopBackBtn;
	@ViewInject(R.id.tv_home_topbar_title)
	private TextView mTopTitle;

	@ViewInject(R.id.lv_notice)
	private XListView mLvNotice;

	private LinkedList<NoticeListBean> mList = new LinkedList<NoticeListBean>();
	private NoticeListAdapter mAdapte;

	private int mListType;// 列表类型：0-通知，1-活动。根据此类型区分界面标题和获取列表数据

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notice);
		ViewUtils.inject(this);
		mListType = getIntent().getIntExtra(KEY_TYPE, 0);
		mAdapte = new NoticeListAdapter(this);
		mLvNotice.setPullRefreshEnable(true);
		//请求数据后根据条数决定是否显示加载更多
		mLvNotice.setPullLoadEnable(false);
		mLvNotice.setXListViewListener(this);
		mLvNotice.setAdapter(mAdapte);
		mLvNotice.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if(mList != null && arg2 - 1 <mList.size()){
					NoticeListBean bean = mList.get(arg2 - 1);
					Bundle bundle = new Bundle();
					bundle.putString(NoticeDetailActivity.KEY_NOTICE_ID, bean.getId()+"");
					startNextActivity(bundle, NoticeDetailActivity.class);
				}
			}
		});
		sendRequest();
	}

	private int mPageIndex;
	private static final int PAGE_MAX = 20;
	
    @Override
    protected void sendRequest() {
        RequestParams params = new RequestParams();
		params.addBodyParameter("types", mListType + "");
        params.addBodyParameter("pageindex", mPageIndex + "");
        params.addBodyParameter("countperpage", PAGE_MAX + "");
        params.addBodyParameter("a_id", GCApplication.sApp.getDefaultAreaInfo().getA_id());
        HttpUtil httpUtil =
                        new HttpUtil(new RequestHandler(NoticeActivity.this, "", getResources()
                                        .getString(R.string.loading), 0));
        RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> result) {
                super.onSuccess(result);
                mLvNotice.stopRefresh();
                mLvNotice.stopLoadMore();
                try {
                    NoticeListResultBean resultBean = mGson.fromJson(result.result, NoticeListResultBean.class);
                    if (Const.SUCCESS.equals(resultBean.getResult())) {
                    	LinkedList<NoticeListBean> resList = resultBean.getData();
                    	if(resList != null && resList.size() > 0){
                    		if(mPageIndex > 0){
                    			mList.addAll(resList);
                    		}else{
                    			mList = resList;
                    		}
                    	}else{
                    		mPageIndex --;
                    	}
                        mAdapte.setArrayList(mList);
                    } else {
                    	if(mPageIndex > 0) mPageIndex --;
                        showToast(resultBean.getErrorCode());
                    }
                } catch (Exception e) {
                	if(mPageIndex > 0) mPageIndex --;
                    showToast(R.string.errorMsg);
                }
            }
        };
        httpUtil.post(WebConstants.METHOD_GET_NOTICE, params, callback);
    }

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void configActionBar() {
		mTopBackBtn.setVisibility(View.VISIBLE);
		mTopTitle.setText(mListType == TYPE_NOTICE ? R.string.actv_title_notice1 : R.string.actv_title_live);
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

	@Override
	public void onRefresh() {
		mPageIndex = 0;
		sendRequest();
	}

	@Override
	public void onLoadMore() {
		mPageIndex ++;
		sendRequest();
	}
}
