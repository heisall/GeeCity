package com.geecity.hisenseplus.home.activity.live;

import java.util.ArrayList;
import java.util.LinkedList;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.WebActivity;
import com.geecity.hisenseplus.home.adapter.GroupBuyListAdapter;
import com.geecity.hisenseplus.home.adapter.LiveMoreBusinessListAdapter;
import com.geecity.hisenseplus.home.adapter.LiveMoreMenuLeftAdapter;
import com.geecity.hisenseplus.home.bean.ADBean;
import com.geecity.hisenseplus.home.bean.BusinessListBean;
import com.geecity.hisenseplus.home.bean.BusinessListResultBean;
import com.geecity.hisenseplus.home.bean.GroupBuyBean;
import com.geecity.hisenseplus.home.bean.LifeHomePageBean;
import com.geecity.hisenseplus.home.bean.MallMenuBean;
import com.geecity.hisenseplus.home.utils.CommonTools;
import com.geecity.hisenseplus.home.utils.Const;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.geecity.hisenseplus.home.view.ListViewForScrollView;
import com.geecity.hisenseplus.home.view.PicViewer;
import com.geecity.hisenseplus.home.view.PicViewer.OnPageViewClick;
import com.google.gson.JsonSyntaxException;
import com.ibill.lib.https.HttpUtil;
import com.ibill.lib.https.HttpUtil.RequestHttpCallBack;
import com.ibill.lib.tools.ScreenUtils;
import com.ibill.lib.webutils.RequestHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class LiveMoreActivity extends ActionBarActivity{

	private static final int PAGE_MAX_COUNT = 30;

	public static final String CATE_BEAN = "cate_bean";

	@ViewInject(R.id.btn_live_more_back)
	private Button mTopBackBtn;
	
	@ViewInject(R.id.lvfs_estate_new_info)
	private PicViewer mPvTopAd;
	
	@ViewInject(R.id.scrview_right)
	private ScrollView mScView;
	
	@ViewInject(R.id.lv_live_more_all)
	private ListView mLvAll;
	private LiveMoreMenuLeftAdapter mAdapterAll;
	private LinkedList<MallMenuBean> mMenus;
	
	@ViewInject(R.id.xlv_live_more_business)
	private ListViewForScrollView mXlvBusiness;
	private LiveMoreBusinessListAdapter mAdapterBusiness;
	private LinkedList<BusinessListBean> mBusinessBeans;
	
	@ViewInject(R.id.xlv_live_more_groupbuy)
	private ListViewForScrollView mXlvGroupBuy;
	private GroupBuyListAdapter mAdapterGroupBuy;
	protected LinkedList<GroupBuyBean> mGoupBuyBeans;

	private MallMenuBean mCurrentMenuBean;
	protected LinkedList<ADBean> mADBeans;
	private int mCurrentCategory = 0;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_live_more);
		ViewUtils.inject(this);
		mPvTopAd.getLayoutParams().height = Const.HEIGHT * ScreenUtils.getScreenWidth(this) / Const.WIDTH;
		mCurrentMenuBean = (MallMenuBean) getIntent().getSerializableExtra(CATE_BEAN);
		mAdapterAll = new LiveMoreMenuLeftAdapter(this);
		mLvAll.setAdapter(mAdapterAll);
		mLvAll.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if(mCurrentCategory != arg2){
				    //更新商户列表
				    if(mBusinessBeans != null){
				        mBusinessBeans.clear();
				    }
				    mAdapterBusiness.setList(mBusinessBeans);
				    
				    //更新分类菜单点击装填
					mMenus.get(mCurrentCategory).setClicked(false);
					mCurrentCategory = arg2;
					mMenus.get(mCurrentCategory).setClicked(true);
					mAdapterAll.notifyDataSetChanged();
					
					mCurrentMenuBean = mMenus.get(arg2);
					
					requestBusinessList(0);
				}
			}
		});
		
		mAdapterBusiness = new LiveMoreBusinessListAdapter(this);
		mXlvBusiness.setAdapter(mAdapterBusiness);
		mXlvBusiness.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
			    BusinessListBean bean = mBusinessBeans.get(arg2);
			    Bundle b = new Bundle();
			    if(bean.getSgrade() == 4){
			        b.putString(WebActivity.KEY_YURL, bean.getAddress());
			        startNextActivity(b, WebActivity.class);
			    }else{
			        b.putSerializable(BusinessDetailActivity.BUSINESS_BEAN, bean);
			        startNextActivity(b, BusinessDetailActivity.class);
			    }
			}
		});
		
		mAdapterGroupBuy = new GroupBuyListAdapter(this);
		mXlvGroupBuy.setAdapter(mAdapterGroupBuy);
		
		mPvTopAd.setOnPageViewClick(new OnPageViewClick() {
			
			@Override
			public void onPageViewClick(int postion) {
                CommonTools.adClick(LiveMoreActivity.this, mADBeans, postion);
			}
		});
		sendRequest();
	}
	
	@Override
	protected void sendRequest() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("parent_id", mCurrentMenuBean.getParent_id() + "");
		HttpUtil httpUtil = new HttpUtil(new RequestHandler(LiveMoreActivity.this, "",
				getResources().getString(R.string.loading), 0));
		// 回调函数
		RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> result) {
				super.onSuccess(result);
				try {
					LifeHomePageBean resBean = mGson.fromJson(result.result, LifeHomePageBean.class);
					if (Const.SUCCESS.equals(resBean.getResult())) {
						mADBeans = resBean.getAd();
						if(mADBeans != null && mADBeans.size() > 0){
							ArrayList<String> pics = new ArrayList<String>();
							for (ADBean adBean : mADBeans) {
								pics.add(adBean.getPhoto());
							}
							mPvTopAd.setPicUrls(pics);
						}
						
						mMenus = resBean.getThumb();
						mAdapterAll.setList(mMenus);
						if(mMenus != null && mMenus.size() > 0){
							//设置点击状态的item
							mCurrentCategory = -1;
							for (MallMenuBean mm : mMenus) {
								mCurrentCategory ++;
								if(mm.getId() == mCurrentMenuBean.getId()){
									mm.setClicked(true);
									break;
								}
							}
							mAdapterAll.notifyDataSetChanged();
							
							// 请求商户列表
							requestBusinessList(0);
						}
					}else{
						showToast(resBean.getErrorCode());
					}
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		httpUtil.post(WebConstants.METHOD_LIFE_MORE_LIST, params, callback);
    }
	
	protected void requestBusinessList(final int pageIndex) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("a_id", GCApplication.sApp.getDefaultAreaInfo().getA_id());
		params.addBodyParameter("pageindex", pageIndex + "");
		params.addBodyParameter("countperpage", PAGE_MAX_COUNT + "");
		params.addBodyParameter("cateId", mCurrentMenuBean.getId() + "");
		HttpUtil httpUtil = new HttpUtil(new RequestHandler(LiveMoreActivity.this, "",
				getResources().getString(R.string.loading), 0));
		RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> result) {
				super.onSuccess(result);
				try {
					BusinessListResultBean resBean = mGson.fromJson(result.result, BusinessListResultBean.class);
					if (Const.SUCCESS.equals(resBean.getResult())) {
					    LinkedList<BusinessListBean> beans = resBean.getData();
					    if(beans != null && beans.size() > 0){
					        if(pageIndex == 0){
					            mBusinessBeans = beans;
					        }else{
					            mBusinessBeans.addAll(beans);
					        }
                            mGoupBuyBeans = resBean.getGroupBuy();
                            mAdapterGroupBuy.setList(mGoupBuyBeans);
                            new Handler().postDelayed(new Runnable() {
								
								@Override
								public void run() {
									mScView.scrollTo(0, 0);
								}
							}, 150);
					    }else{
					    }
					    mAdapterBusiness.setList(mBusinessBeans);
					}else{
						showToast(resBean.getErrorCode());
					}
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		httpUtil.post(WebConstants.METHOD_LIFE_BUSINESS_LIST, params, callback);
    }

	@OnClick({ R.id.btn_live_more_back })
	public void OnClick(View v) {
		switch (v.getId()) {
		case R.id.btn_live_more_back: // 点击返回按钮
			onBackPressed();
			break;
		default:
			break;
		}
	}

	@Override
	public void configActionBar() {
	}

	@Override
	public void requestCallBack(String dataJson, RequestType type) {
	}
}
