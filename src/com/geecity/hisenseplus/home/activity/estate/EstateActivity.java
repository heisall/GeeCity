package com.geecity.hisenseplus.home.activity.estate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.adapter.EstateFilterAdapter;
import com.geecity.hisenseplus.home.adapter.EstatesAdapter;
import com.geecity.hisenseplus.home.bean.EstateAdaptBean;
import com.geecity.hisenseplus.home.bean.EstateFilterBean;
import com.geecity.hisenseplus.home.bean.EstateHomeFilterResultBean;
import com.geecity.hisenseplus.home.bean.EstateListNewBean;
import com.geecity.hisenseplus.home.bean.EstateListNewResultBean;
import com.geecity.hisenseplus.home.bean.EstateListRentBean;
import com.geecity.hisenseplus.home.bean.EstateListRentResultBean;
import com.geecity.hisenseplus.home.bean.EstateListSecondHandBean;
import com.geecity.hisenseplus.home.bean.EstateListSecondHandResultBean;
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

public class EstateActivity extends ActionBarActivity implements IXListViewListener{
	private static final int PAGE_COUNT_MAX = 20;
    public static final String ESTATE_DETAIL_ID = "detail_id";
	@ViewInject(R.id.btn_estate_back)
	private Button mBtnBackBtn;
	@ViewInject(R.id.btn_estate_add)
	private Button mBtnAdd;
	@ViewInject(R.id.tv_estate_title)
	private TextView mTvTitle;
	@ViewInject(R.id.ly_estate)
	private LinearLayout mLinerLayout;
	
	@ViewInject(R.id.et_estate_search)
	private EditText mEtSearch;
	
	@ViewInject(R.id.layout_estate_selected)
	private LinearLayout mLYSelected;
	@ViewInject(R.id.xl_estate)
	private XListView mXList;

    @ViewInject(R.id.btn_estate_select_1)
    private Button mBtnFilter01;
    @ViewInject(R.id.btn_estate_select_2)
    private Button mBtnFilter02;
    @ViewInject(R.id.btn_estate_select_3)
    private Button mBtnFilter03;
    @ViewInject(R.id.btn_estate_select_4)
    private Button mBtnFilter04;
	
	public static String KEY_ESTATE = "estate_type";
	
	public enum ESTATE{
		NEW_HOME,SECOND_HAND,RENT
	}
	
	private static final String[] FILTER_TITLE_SECOND = {"区域","总价","户型","面积"};
	private static final String[] FILTER_TITLE_RENT = {"区域","租金","户型","方式"};
	private ArrayList<String> mFilters = new ArrayList<String>();
	
	private ESTATE mEstateType;
    private EstatesAdapter mAdapte;
    protected EstateHomeFilterResultBean mFilterBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_estate_home);
		ViewUtils.inject(this);
		mEstateType = (ESTATE) getIntent().getSerializableExtra(KEY_ESTATE);
		initFilter();
		mEtSearch.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                    KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_UP){
                    LogUtils.d("search : " + mEtSearch.getText().toString().trim());
                    InputMethodManager m =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
                    m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS); 
                    onRefresh();
                }
                return true;
            }

        });
        mAdapte = new EstatesAdapter(this);
        mXList.setAdapter(mAdapte);
        mXList.setPullRefreshEnable(true);
        mXList.setPullLoadEnable(false);
        mXList.setXListViewListener(this);
        mXList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
			    String id = mList.get(arg2 - 1).getId() + "";
			    LogUtils.d(id);
			    Bundle b = new Bundle();
			    b.putString(ESTATE_DETAIL_ID, id);
			    switch (mEstateType) {
                    case NEW_HOME:{
                        startNextActivity(b, EstateDetailNewActivity.class);
                    }
                        break;
                    case SECOND_HAND:{
                        startNextActivity(b, EstateDetailSecondActivity.class);
                    }
                        break;
                    case RENT:{
                        startNextActivity(b, EstateDetailRentActivity.class);
                    }
                        break;

                    default:
                        break;
                }
			}
		});
        initFilterMap();
        sendFilterRequest();
        sendRequest(0);
	}

	private EstateFilterAdapter mFilterAdpter;
	// 所有筛选条件集合
	LinkedHashMap<Integer, LinkedList<EstateFilterBean>> mLmpFilters = new LinkedHashMap<>();
	// 默认全部
	private static final EstateFilterBean DEFAULT_BEAN = new EstateFilterBean("","全部");
	// 筛选视图
    private PopupWindow mPopFilter;

    //记录当前点击的筛选框，选择不同的筛选条件进行显示
    private int mCurrentFilterId;
    //记录当前选择的筛选条件.
    private LinkedHashMap<Integer, EstateFilterBean> mFilterMap = new LinkedHashMap<Integer, EstateFilterBean>();
    
    // 初始化选择器原始数据，便于首次获取列表
	private void initFilterMap() {
		for (int i = 0; i < 4; i++) {
			mFilterMap.put(i, DEFAULT_BEAN);
		}
	}
    
    private void sendFilterRequest() {
        HttpUtil httpUtil = new HttpUtil(new RequestHandler(EstateActivity.this, "", getResources().getString(R.string.loading), 0));
        // 回调函数
        RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> result) {
                super.onSuccess(result);
                try {
                    mFilterBean = mGson.fromJson(result.result, EstateHomeFilterResultBean.class);
                    if(mFilterBean != null){
                    	mLmpFilters.clear();
                    	switch (mEstateType) {
                        case NEW_HOME:
							return;
                        case SECOND_HAND:
                        	mLmpFilters.put(0, mFilterBean.getDistrict());
                        	mLmpFilters.put(1, mFilterBean.getPrice());
                        	mLmpFilters.put(2, mFilterBean.getStyle());
                        	mLmpFilters.put(3, mFilterBean.getSquare());
                            break;
                        case RENT:
                        	mLmpFilters.put(0, mFilterBean.getArea());
                        	mLmpFilters.put(1, mFilterBean.getZujin());
                        	mLmpFilters.put(2, mFilterBean.getHx());
                        	mLmpFilters.put(3, mFilterBean.getFkfs());
                            break;
                        default:
                            break;
                        }
                    	
                    	//增加“全部"选择项
                    	for (int i = 0; i < mLmpFilters.size(); i++) {
							mLmpFilters.get(i).add(0, DEFAULT_BEAN);
							for (EstateFilterBean bean : mLmpFilters.get(i)) {
								LogUtils.d(i + "--" + bean.toString());
							}
						}
                    	initPop();
                    }else{
                        showToast("获取条件失败");
                    }

                } catch (Exception e) {
                    showToast(R.string.errorMsg);
                }
            }
        };

        switch (mEstateType) {
        case NEW_HOME:
            break;
        case SECOND_HAND:
            httpUtil.post(WebConstants.METHOD_ESTATE_HOUSEFILTER, null, callback);
            break;
        case RENT:
            httpUtil.post(WebConstants.METHOD_ESTATE_RENTFILTER, null, callback);
            break;
        default:
            break;
        }
    }
    
    private void initPop() {
    	mFilterAdpter = new EstateFilterAdapter(EstateActivity.this, false);
    	mFilterAdpter.setList(mLmpFilters.get(mCurrentFilterId));

		LinearLayout layout = (LinearLayout) LayoutInflater.from(EstateActivity.this).inflate(R.layout.spinner_down, null);
		ListView listView = (ListView) layout.findViewById(R.id.list_spinner);
		listView.setSelector(R.color.txt_white);
		listView.setAdapter(mFilterAdpter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mFilterMap.put(mCurrentFilterId, mLmpFilters.get(mCurrentFilterId).get(position));
				String defaultStr = FILTER_TITLE_RENT[mCurrentFilterId];
				if(mEstateType  == ESTATE.SECOND_HAND){
					defaultStr = FILTER_TITLE_SECOND[mCurrentFilterId];
				}
				mFilters.set(mCurrentFilterId, mLmpFilters.get(mCurrentFilterId).get(position).getDicValue().replace("全部", defaultStr));
				mPopFilter.dismiss();
				initFilter();
				onRefresh();
			}
		});
		mPopFilter = new PopupWindow(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT) {
			@Override
			public void showAsDropDown(View anchor) {
				super.showAsDropDown(anchor);
			}

			@Override
			public void dismiss() {
				super.dismiss();
			}
		};
		mPopFilter.setOutsideTouchable(true);
		mPopFilter.setFocusable(true);
		mPopFilter.setContentView(layout);
		mPopFilter.setBackgroundDrawable(new ColorDrawable());
		mPopFilter.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
			}
		});
		mFilterAdpter.notifyDataSetChanged();
	}

    private int mPageIndex;
	private LinkedList<EstateAdaptBean> mList;
	
    protected void sendRequest(final int pageIndex) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("pageindex", pageIndex + "");
        params.addBodyParameter("countperpage", PAGE_COUNT_MAX + "");
        switch (mEstateType) {
		case NEW_HOME:
	        params.addBodyParameter("area", mEtSearch.getText().toString().trim());
			break;
		case SECOND_HAND:
	        params.addBodyParameter("buildingInfo", mEtSearch.getText().toString().trim());
	        params.addBodyParameter("district",mFilterMap.get(0).getDicValue().replace("全部", ""));//区域
	        params.addBodyParameter("price",mFilterMap.get(1).getDicKey());//价格
	        params.addBodyParameter("style",mFilterMap.get(2).getDicKey());//户型
	        params.addBodyParameter("square",mFilterMap.get(3).getDicKey());//面积
			LogUtils.d("district : " + mFilterMap.get(0).getDicValue().replace("全部", "")
					+ ", price : " + mFilterMap.get(1).getDicKey()
					+ ", style : " + mFilterMap.get(2).getDicKey()
					+ ", square : " + mFilterMap.get(3).getDicKey());
			break;
		case RENT:
	        params.addBodyParameter("buildingInfo", mEtSearch.getText().toString().trim());
	        params.addBodyParameter("area",mFilterMap.get(0).getDicKey());//区域
	        params.addBodyParameter("zujin",mFilterMap.get(1).getDicKey());//租金
	        params.addBodyParameter("hx",mFilterMap.get(2).getDicKey());//户型
	        params.addBodyParameter("fkfs",mFilterMap.get(3).getDicKey());//付款方式
			LogUtils.d("area : " + mFilterMap.get(0).getDicKey() + ", zujin : "
					+ mFilterMap.get(1).getDicKey() + ", hx : "
					+ mFilterMap.get(2).getDicKey() + ", fkfs : "
					+ mFilterMap.get(3).getDicKey());
			break;
		default:
			break;
		}
        HttpUtil httpUtil = new HttpUtil(new RequestHandler(EstateActivity.this, "", getResources().getString(R.string.loading), 0));
        // 回调函数
        RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> result) {
                super.onSuccess(result);
                mXList.stopRefresh();
                mXList.stopLoadMore();
                try {
                	String errorInfo = "";
                	switch (mEstateType) {
					case NEW_HOME:{
						EstateListNewResultBean resBean = mGson.fromJson(result.result, EstateListNewResultBean.class);
						if("1".equals(resBean.getResult())){
							mPageIndex = pageIndex;
							mAdapte.setType(ESTATE.NEW_HOME);
							LinkedList<EstateListNewBean> elBeans = resBean.getData();
							if(elBeans != null && elBeans.size() > 0){
								// 转换为适配器所需数据类型
								LinkedList<EstateAdaptBean> adpteBeans = new LinkedList<EstateAdaptBean>();
								for (EstateListNewBean eBean : elBeans) {
									EstateAdaptBean bean = new EstateAdaptBean();
									bean.setId(eBean.getId());
									bean.setLpmc(eBean.getLpmc());
									bean.setTv1(eBean.getDescription());
									bean.setTv2(eBean.getZt());
									bean.setPricePre(eBean.getJiageLeixing());
									bean.setPrice(String.valueOf(eBean.getJiage()));
									bean.setPicUrl(eBean.getXzst());
									adpteBeans.add(bean);
								}
								
								if(mPageIndex == 0){
									mList = adpteBeans;
									mAdapte.setArrayList(adpteBeans);
								}else{
								    mList.addAll(adpteBeans);
									mAdapte.addList(adpteBeans);
								}
								if(elBeans.size() >= PAGE_COUNT_MAX){
									mXList.setPullLoadEnable(true);
								}else{
									mXList.setPullLoadEnable(false);
								}
							}else{
                                mList.clear();
                                mAdapte.setArrayList(mList);
							}
	                    	return;
						}else{
							errorInfo = resBean.getErrorCode();
						}
					}
						break;
					case SECOND_HAND:{
						EstateListSecondHandResultBean resBean = mGson.fromJson(result.result, EstateListSecondHandResultBean.class);
						if("1".equals(resBean.getResult())){
							mPageIndex = pageIndex;
							mAdapte.setType(ESTATE.SECOND_HAND);
						    LinkedList<EstateListSecondHandBean> elBeans = resBean.getData();
							if(elBeans != null && elBeans.size() > 0){
								// 转换为适配器所需数据类型
								LinkedList<EstateAdaptBean> adpteBeans = new LinkedList<EstateAdaptBean>();
								for (EstateListSecondHandBean eBean : elBeans) {
									EstateAdaptBean bean = new EstateAdaptBean();
                                    bean.setId(eBean.getId());
									bean.setLpmc(eBean.getBuildingInfo());
									bean.setTv1(eBean.getHouse_Indoor() + "室" + eBean.getHouse_living() +"厅"+eBean.getHouse_Toilet() + "卫"+ "  " + eBean.getBuildingArea() + "平米");
									bean.setTv2(eBean.getCity() + "  " + eBean.getArea());
									bean.setPrice(String.valueOf(eBean.getRent()));
									bean.setPicUrl(eBean.getPhoto());
									adpteBeans.add(bean);
								}
                                
                                if(mPageIndex == 0){
                                    mList = adpteBeans;
                                    mAdapte.setArrayList(adpteBeans);
                                }else{
                                    mList.addAll(adpteBeans);
                                    mAdapte.addList(adpteBeans);
                                }
                                if(elBeans.size() >= PAGE_COUNT_MAX){
                                    mXList.setPullLoadEnable(true);
                                }else{
                                    mXList.setPullLoadEnable(false);
                                }
							}else{
                                mList.clear();
                                mAdapte.setArrayList(mList);
							}
	                    	return;
						}else{
							errorInfo = resBean.getErrorCode();
						}
					}
						break;
					case RENT:{
						EstateListRentResultBean resBean = mGson.fromJson(result.result, EstateListRentResultBean.class);
						if("1".equals(resBean.getResult())){
							mPageIndex = pageIndex;
							mAdapte.setType(ESTATE.RENT);
							LinkedList<EstateListRentBean> elBeans = resBean.getData();
							if(elBeans != null && elBeans.size() > 0){
								// 转换为适配器所需数据类型
								LinkedList<EstateAdaptBean> adpteBeans = new LinkedList<EstateAdaptBean>();
								for (EstateListRentBean eBean : elBeans) {
									EstateAdaptBean bean = new EstateAdaptBean();
                                    bean.setId(eBean.getId());
									bean.setLpmc(eBean.getBuildingInfo());
									//室、厅、卫+面积+装修情况
									bean.setTv1(eBean.getHouse_Indoor() + "室" + eBean.getHouse_living() +"厅"+eBean.getHouse_Toilet() + "卫"+ "  " + eBean.getBuildingArea() + "㎡");
									//青岛市+区域
									bean.setTv2(eBean.getArea());
									bean.setPricePre("");
									bean.setPrice(String.valueOf(eBean.getRent()));
									bean.setPicUrl(eBean.getPhoto());
									LogUtils.d(bean.toString());
									adpteBeans.add(bean);
								}
                                
                                if(mPageIndex == 0){
                                    mList = adpteBeans;
                                    mAdapte.setArrayList(adpteBeans);
                                }else{
                                    mList.addAll(adpteBeans);
                                    mAdapte.addList(adpteBeans);
                                }
                                if(elBeans.size() >= PAGE_COUNT_MAX){
                                    mXList.setPullLoadEnable(true);
                                }else{
                                    mXList.setPullLoadEnable(false);
                                }
							}else{
                                mList.clear();
                                mAdapte.setArrayList(mList);
							}
	                    	return;
						}else{
							errorInfo = resBean.getErrorCode();
						}
					}
						break;

					default:
						break;
					}
                    showToast(errorInfo);
                } catch (Exception e) {
                	e.printStackTrace();
                    showToast(R.string.errorMsg);
                }
            }
        };
        switch (mEstateType) {
		case NEW_HOME:
	        httpUtil.post(WebConstants.METHOD_ESTATE_NEW_LIST, params, callback);
			break;
		case SECOND_HAND:
	        httpUtil.post(WebConstants.METHOD_ESTATE_SENDHAND_LIST, params, callback);
			break;
		case RENT:
	        httpUtil.post(WebConstants.METHOD_ESTATE_RENT_LIST, params, callback);
			break;
		default:
			break;
		}
    }
	
	@Override
	public void configActionBar() {
		switch (mEstateType) {
		case NEW_HOME:
			mTvTitle.setText(getResources().getString(R.string.text_estate_new));
			mLinerLayout.setVisibility(View.GONE);
			mBtnAdd.setVisibility(View.GONE);
			mLYSelected.setVisibility(View.GONE);
			break;
		case SECOND_HAND:
			mTvTitle.setText(getResources().getString(R.string.text_estate_second_hand));
			mLinerLayout.setVisibility(View.GONE);
			mBtnAdd.setVisibility(View.VISIBLE);
			mLYSelected.setVisibility(View.VISIBLE);
			break;
		case RENT:
			mTvTitle.setText(getResources().getString(R.string.text_estate_rent));
			mLinerLayout.setVisibility(View.GONE);
			mBtnAdd.setVisibility(View.VISIBLE);
			mLYSelected.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
	}

	@OnClick({ R.id.btn_estate_back, R.id.btn_estate_add,R.id.btn_estate_select_1,R.id.btn_estate_select_2,R.id.btn_estate_select_3, R.id.btn_estate_select_4})
	public void OnClick(View v) {
		switch (v.getId()) {
		case R.id.btn_estate_back: // 点击返回按钮
			onBackPressed();
			break;
		case R.id.btn_estate_add: // 新增
			if(mEstateType.ordinal() == ESTATE.RENT.ordinal()){
				startNextActivity(null, EstateAddRentActivity.class);
			}else if(mEstateType.ordinal() == ESTATE.SECOND_HAND.ordinal()){
				startNextActivity(null, EstateAddSecondActivity.class);
			}else{
				
			}
			break;
		case R.id.btn_estate_select_1:
		    mCurrentFilterId = 0;
		    showFilterPop();
		    break;
        case R.id.btn_estate_select_2:
		    mCurrentFilterId = 1;
		    showFilterPop();
            break;
        case R.id.btn_estate_select_3:
		    mCurrentFilterId = 2;
		    showFilterPop();
            break;
        case R.id.btn_estate_select_4:
		    mCurrentFilterId = 3;
		    showFilterPop();
            break;
		default:
			break;
		}
	}
    
    private void showFilterPop() {
	    if(mPopFilter == null){
	    	initPop();
	    }else{
	    	mFilterAdpter.setList(mLmpFilters.get(mCurrentFilterId));
	    	mFilterAdpter.notifyDataSetChanged();
	    }
	    mPopFilter.showAsDropDown(mLYSelected);
	}

	private void initFilter() {
        switch (mEstateType) {
            case NEW_HOME:
            	return;
            case SECOND_HAND:
                for (String filter : FILTER_TITLE_SECOND) {
                    mFilters.add(filter);
                };
                break;
            case RENT:
                for (String filter : FILTER_TITLE_RENT) {
                    mFilters.add(filter);
                };
                break;
            default:
                break;
            }
        initFilterBtn();
    }

    private void initFilterBtn() {
        mBtnFilter01.setText(mFilters.get(0));
        mBtnFilter02.setText(mFilters.get(1));
        mBtnFilter03.setText(mFilters.get(2));
        mBtnFilter04.setText(mFilters.get(3));
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
	public void requestCallBack(String dataJson, RequestType type) {
	}
}
