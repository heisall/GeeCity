package com.geecity.hisenseplus.home.activity.repair;

import java.util.LinkedList;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.adapter.RepairListAdapter;
import com.geecity.hisenseplus.home.bean.RepairListBean;
import com.geecity.hisenseplus.home.bean.RepairListResultBean;
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
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 报修/投诉 列表界面
 * 
 * @author Administrator
 * 
 */
public class RepairActivity extends ActionBarActivity implements IXListViewListener{

	public static final String KEY_TYPE = "repair_type";
	private static final int PAGE_MAX = 20;
	public static final String KEY_DETAIL = "repair_detail_bean";
    // 类型1 ：物业报修
    public static final String TYPE_REPAIR = "维修";
    // 类型2：物业投诉
    public static final String TYPE_COMPLS = "投诉";
	@ViewInject(R.id.btn_home_menu)
	private Button mTopBackBtn;
	@ViewInject(R.id.tv_home_topbar_title)
	private TextView mTopTitle;
	@ViewInject(R.id.btn_home_message)
	private Button mTopSend;
	@ViewInject(R.id.btn_repair_bottom_add)
	private Button mBtnBottomAdd;

	@ViewInject(R.id.tv_repair_empty)
	private TextView mTvEmpty;

	@ViewInject(R.id.lv_repair)
	private XListView mLvRepair;

	private LinkedList<RepairListBean> mList = new LinkedList<RepairListBean>();
	private RepairListAdapter mAdapte;

	private String mType;// 列表类型：0-物业报修，1-物业投诉。根据此类型区分界面标题和获取列表数据

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_repair);
		ViewUtils.inject(this);
		mType = getIntent().getStringExtra(KEY_TYPE);
		mAdapte = new RepairListAdapter(this);
		mAdapte.setType(mType);
		mLvRepair.setPullRefreshEnable(true);
		//请求数据后根据条数决定是否显示加载更多
		mLvRepair.setPullLoadEnable(false);
		mLvRepair.setXListViewListener(this);
		mLvRepair.setAdapter(mAdapte);
		sendRequest(0);
	}
	
	private int mPageIndex;
	
    protected void sendRequest(final int pageIndex) {
        RequestParams params = new RequestParams();
        //bill/myComplains  userid type：维修 or 投诉
		params.addBodyParameter("type", TYPE_REPAIR.equals(mType)?"维修":"投诉");
        params.addBodyParameter("userid", GCApplication.sApp.getUserInfo().getId() + "");
        LogUtils.d("type: " + (TYPE_REPAIR.equals(mType)?"维修":"投诉") + "  userid : " + GCApplication.sApp.getUserInfo().getId());
        params.addBodyParameter("a_id", GCApplication.sApp.getDefaultAreaInfo().getA_id());
        HttpUtil httpUtil =
                        new HttpUtil(new RequestHandler(RepairActivity.this, "", getResources()
                                        .getString(R.string.loading), 0));
        // 回调函数
        RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> result) {
                super.onSuccess(result);
                mLvRepair.stopRefresh();
                mLvRepair.stopLoadMore();
                try {
                    RepairListResultBean resultBean = mGson.fromJson(result.result, RepairListResultBean.class);
                    if ("1".equals(resultBean.getResult())) {
                    	LinkedList<RepairListBean> resList = resultBean.getData();
                    	if(resList != null && resList.size() > 0){
                    		mPageIndex = pageIndex;
                    		if(mPageIndex > 0){
                    			mList.addAll(resList);
                    		}else{
                    			mList = resList;
                    		}
                    		
                    		if(resList.size() < PAGE_MAX){
                    		    mLvRepair.setPullLoadEnable(false);
                    		}else{
                                mLvRepair.setPullLoadEnable(true);
                    		}
                    	}
                        mAdapte.setArrayList(mList);
                        if(mAdapte.getCount() == 0){
                        	mTvEmpty.setVisibility(View.VISIBLE);
                        }else{
                        	mTvEmpty.setVisibility(View.GONE);
                        }
                    } else {
                        showToast(resultBean.getErrorCode());
                    }
                } catch (Exception e) {
                    showToast(R.string.errorMsg);
                }
            }
        };
        httpUtil.post(WebConstants.METHOD_BILL_MYCOMPLAINS, params, callback);
    }

	@Override
	protected void onResume() {
		super.onResume();
		onRefresh();
	}

	@Override
	public void configActionBar() {
		mTopBackBtn.setVisibility(View.VISIBLE);
		mTopTitle.setText(TYPE_REPAIR.equals(mType) ? R.string.actv_title_repair : R.string.actv_title_repair_appra);
		mTopSend.setText(TYPE_REPAIR.equals(mType) ? R.string.btn_text_repair : R.string.btn_text_repair_appra);
		mBtnBottomAdd.setText(TYPE_REPAIR.equals(mType) ? R.string.btn_text_repair_bottom : R.string.btn_text_repair_appra_bottom);
		mTvEmpty.setText(TYPE_REPAIR.equals(mType) ? R.string.hint_text_repair : R.string.hint_text_repair_appra);
		
		LayoutParams params = mTopSend.getLayoutParams();
		params.width = 120;
		params.height = LayoutParams.MATCH_PARENT;
		mTopSend.setLayoutParams(params);
		mTopSend.setVisibility(View.VISIBLE);
		mTopSend.setTextSize(14);
		mTopSend.setTextColor(getResources().getColor(R.drawable.text_white_gray));
		mTopSend.setBackgroundResource(android.R.color.transparent);
	}

	@OnClick({ R.id.btn_home_menu, R.id.btn_home_message, R.id.btn_repair_bottom_add})
	public void OnClick(View v) {
		switch (v.getId()) {
		case R.id.btn_home_menu: // 点击返回按钮
			onBackPressed();
			break;
		case R.id.btn_repair_bottom_add:
		case R.id.btn_home_message://报修新增
            Bundle b = new Bundle();
            b.putString(RepairActivity.KEY_TYPE, mType);
            startNextActivity(b, RepairAddActivity.class);
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
