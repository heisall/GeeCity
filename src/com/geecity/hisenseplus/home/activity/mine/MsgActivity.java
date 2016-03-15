package com.geecity.hisenseplus.home.activity.mine;

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
import com.geecity.hisenseplus.home.activity.disc.DiscDetailActivity;
import com.geecity.hisenseplus.home.adapter.MsgAdapter;
import com.geecity.hisenseplus.home.bean.DiscListBean;
import com.geecity.hisenseplus.home.bean.MsgListBean;
import com.geecity.hisenseplus.home.bean.MsgListResultBean;
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

public class MsgActivity extends ActionBarActivity implements IXListViewListener{

	private static final int PAGE_MAX = 20;
	@ViewInject(R.id.btn_home_menu)
	private Button mTopBackBtn;
	@ViewInject(R.id.tv_home_topbar_title)
	private TextView mTopTitle;

	@ViewInject(R.id.xl_base)
	private XListView mXList;

	private MsgAdapter mAdapter;
	private LinkedList<MsgListBean> mBeans;
	protected int mPageIndex;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base_list);
		ViewUtils.inject(this);
		mAdapter = new MsgAdapter(this);
		mXList.setAdapter(mAdapter);
		mXList.setXListViewListener(this);
		mXList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				onItemClicked(arg2);
			}
		});
		requestDate(0);
	}
	
	@Override
	public void configActionBar() {
		mTopBackBtn.setVisibility(View.VISIBLE);
		mTopTitle.setText(getActivityTitle());
	}
	
	private int getActivityTitle() {
		return R.string.frg_4_title_msg;
	}

	private void onItemClicked(int position) {
		MsgListBean bean = mBeans.get(position - 1);
		Bundle b = new Bundle();
		Class<?> newClass = null;
		if("find".equals(bean.getType())){
			DiscListBean db = new DiscListBean();
			db.setId(bean.getFromid());
			b.putSerializable(DiscDetailActivity.KEY_DISCLIST, db);
			newClass = DiscDetailActivity.class;
		}else{
			
		}
		
		if(newClass != null){
			startNextActivity(b, newClass);
		}
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

	private void requestDate(final int pageIndex) {
        RequestParams params = new RequestParams();
		params.addBodyParameter("userid", GCApplication.sApp.getUserInfo().getId() + "");
        params.addBodyParameter("pageindex", pageIndex + "");
        params.addBodyParameter("countperpage", PAGE_MAX + "");
        params.addBodyParameter("a_id", GCApplication.sApp.getDefaultAreaInfo().getA_id());
        HttpUtil httpUtil = new HttpUtil(new RequestHandler(MsgActivity.this, "", getResources().getString(R.string.loading), 0));
        RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> result) {
                super.onSuccess(result);
                mXList.stopRefresh();
                mXList.stopLoadMore();
                try {
                    MsgListResultBean resultBean = mGson.fromJson(result.result, MsgListResultBean.class);
                    if (Const.SUCCESS.equals(resultBean.getResult())) {
                    	LinkedList<MsgListBean> resList = resultBean.getData();
                    	if(resList != null && resList.size() > 0){
                    		mPageIndex = pageIndex;
                    		if(pageIndex > 0){
                    			mBeans.addAll(resList);
                    		}else{
                    			mBeans = resList;
                    		}
                    		if(resList.size() == PAGE_MAX){
                    			mXList.setPullLoadEnable(true);
                    		}else{
                    			mXList.setPullLoadEnable(false);
                    		}
                    	}else{
                    	}
                    	mAdapter.setList(mBeans);
                    } else {
                        showToast(resultBean.getErrorCode());
                    }
                } catch (Exception e) {
                	e.printStackTrace();
                    showToast(R.string.errorMsg);
                }
            }
        };
        httpUtil.post(WebConstants.METHOD_USER_MESAAGE, params, callback);
	}

	@Override
	public void requestCallBack(String dataJson, RequestType type) {
		
	}

	@Override
	public void onRefresh() {
		requestDate(0);
	}

	@Override
	public void onLoadMore() {
		requestDate(mPageIndex + 1);
	}
}
