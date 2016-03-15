package com.geecity.hisenseplus.home.activity.live;

import java.util.LinkedList;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.adapter.ScoreExchangeListAdapter;
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
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 积分兑换列表
 * 
 * @author Administrator
 * 
 */
public class ScoreExchangeActivity extends ActionBarActivity implements IXListViewListener{

	private static final int PAGE_MAX = 20;
    @ViewInject(R.id.btn_home_menu)
	private Button mTopBackBtn;
	@ViewInject(R.id.tv_home_topbar_title)
	private TextView mTopTitle;

	@ViewInject(R.id.textview01)
	private TextView mTvAll;
	@ViewInject(R.id.tvscore_line_01)
	private TextView mTvAllLine;
	
	@ViewInject(R.id.textview02)
	private TextView mTvMine;
	@ViewInject(R.id.tvscore_line_02)
	private TextView mTvMineLine;

	@ViewInject(R.id.xlv_score)
	private XListView mLvNotice;

	private ScoreExchangeListAdapter mAdapte;
	protected LinkedList<GroupBuyBean> mGroupBuyList;
	private int mPageIndex;
	//积分列表类型：0-全部，1-我的
	private int mType = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score);
		ViewUtils.inject(this);
		mAdapte = new ScoreExchangeListAdapter(this);
		mLvNotice.setPullRefreshEnable(true);
		//请求数据后根据条数决定是否显示加载更多
		mLvNotice.setPullLoadEnable(false);
		mLvNotice.setXListViewListener(this);
		mLvNotice.setAdapter(mAdapte);
		sendRequest(0);
	}
	
    protected void sendRequest(final int index) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("pageindex", index+"");
        params.addBodyParameter("countperpage", PAGE_MAX + "");
		params.addBodyParameter("type", mType + "");
		params.addBodyParameter("account", GCApplication.sApp.getUserInfo().getAccount());
        HttpUtil httpUtil = new HttpUtil(new RequestHandler(ScoreExchangeActivity.this, "", getResources().getString(R.string.loading), 0));
        RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> result) {
                super.onSuccess(result);
                mLvNotice.stopRefresh();
                mLvNotice.stopLoadMore();
                try {
                    GroupBuyListResultBean resultBean = mGson.fromJson(result.result, GroupBuyListResultBean.class);
                    if (Const.SUCCESS.equals(resultBean.getResult())) {
                        if(index == 0){
                            mGroupBuyList = resultBean.getData();
                        }else{
                            mGroupBuyList.addAll(resultBean.getData());
                        }
                        mPageIndex = index;
                        
                        if (index < PAGE_MAX) {
                            mLvNotice.setPullLoadEnable(false);
                        } else {
                            mLvNotice.setPullLoadEnable(true);
                        }
                        mAdapte.setList(mGroupBuyList);
                    } else {
                        showToast(resultBean.getErrorCode());
                    }
                } catch (Exception e) {
                    showToast(R.string.errorMsg);
                }
            }
            
            @Override
            public void onFailure(HttpException arg0, String arg1) {
            	super.onFailure(arg0, arg1);
                mLvNotice.stopRefresh();
                mLvNotice.stopLoadMore();
            }
        };
        httpUtil.post(WebConstants.METHOD_SCORE_GOODS_LIST, params, callback);
    }

	@Override
	public void configActionBar() {
		mTopBackBtn.setVisibility(View.VISIBLE);
		mTopTitle.setText(R.string.actv_title_score);
	}

	@OnClick({ R.id.btn_home_menu ,R.id.textview01,R.id.textview02})
	public void OnClick(View v) {
		switch (v.getId()) {
		case R.id.btn_home_menu: // 点击返回按钮
			onBackPressed();
			break;
		case R.id.textview01:
			if(mType  == 0){
				return;
			}
			mType = 0;
			mTvAll.setTextColor(getResources().getColor(R.color.txt_hisense_green));
			mTvAllLine.setVisibility(View.VISIBLE);
			mTvMine.setTextColor(getResources().getColor(R.color.dark_gray));
			mTvMineLine.setVisibility(View.INVISIBLE);
			sendRequest(0);
			break;
		case R.id.textview02:
			if(mType  == 1){
				return;
			}
			mType = 1;
			mTvAll.setTextColor(getResources().getColor(R.color.dark_gray));
			mTvAllLine.setVisibility(View.INVISIBLE);
			mTvMine.setTextColor(getResources().getColor(R.color.txt_hisense_green));
			mTvMineLine.setVisibility(View.VISIBLE);
			sendRequest(0);
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
		sendRequest(0);
	}

	@Override
	public void onLoadMore() {
		sendRequest(mPageIndex + 1);
	}
}
