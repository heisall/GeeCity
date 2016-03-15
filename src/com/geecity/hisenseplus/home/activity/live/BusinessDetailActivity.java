package com.geecity.hisenseplus.home.activity.live;

import java.util.LinkedList;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.adapter.BaseSelectAdapter;
import com.geecity.hisenseplus.home.adapter.GoodsListAdapter;
import com.geecity.hisenseplus.home.bean.BusinessDetailBean;
import com.geecity.hisenseplus.home.bean.BusinessDetailResultBean;
import com.geecity.hisenseplus.home.bean.BusinessListBean;
import com.geecity.hisenseplus.home.bean.DiscTypesBean;
import com.geecity.hisenseplus.home.bean.GoodsCategrayBean;
import com.geecity.hisenseplus.home.bean.GoodsListBean;
import com.geecity.hisenseplus.home.utils.BitmapAsset;
import com.geecity.hisenseplus.home.utils.Const;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.geecity.hisenseplus.home.view.XListView;
import com.geecity.hisenseplus.home.view.XListView.IXListViewListener;
import com.google.gson.JsonSyntaxException;
import com.ibill.lib.https.HttpUtil;
import com.ibill.lib.https.HttpUtil.RequestHttpCallBack;
import com.ibill.lib.tools.ScreenUtils;
import com.ibill.lib.webutils.RequestHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class BusinessDetailActivity extends ActionBarActivity implements IXListViewListener{

	private static final int PAGE_MAX_COUNT = 10;

	public static final String BUSINESS_BEAN = "business_bean";

	@ViewInject(R.id.btn_business_detail_back)
	private Button mTopBackBtn;

	@ViewInject(R.id.tv_business_detail_title)
	private TextView mTvTitle;
	
	@ViewInject(R.id.img_business_detail)
	private ImageView mImgTopAd;
	@ViewInject(R.id.btn_business_detail_newest)
	private Button mBtnNewEst;
	@ViewInject(R.id.btn_business_detail_count)
	private Button mBtnCount;
	@ViewInject(R.id.btn_business_detail_price)
	private Button mBtnPrice;
	@ViewInject(R.id.xlv_business_detail_merch)
	private XListView mXlvBusiness;
	private int mCurrentPage = 0;
	private int mRequestPage = 0;
	
	@ViewInject(R.id.img_business_detail_center)
	private ImageView mImgCenter;
	@ViewInject(R.id.tv_business_detail_catogery)
	private TextView mTvCatogery;
	@ViewInject(R.id.line_22)
	private TextView mTvLine;
	
//	@ViewInject(R.id.scr_business_detail)
//	private ScrollView mScrollView;

	private BusinessListBean mCurrentSourceBean;
	private BusinessDetailBean mDetailBean;
	private GoodsListAdapter mAdapterGoodsList;
	private LinkedList<GoodsListBean> mGoodsList = new LinkedList<>();
	
	private LinkedList<GoodsCategrayBean> mGoodsCateBeans;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_business_detail);
		ViewUtils.inject(this);
		mImgTopAd.getLayoutParams().height = Const.HEIGHT * ScreenUtils.getScreenWidth(this) / Const.WIDTH;
		mCurrentSourceBean = (BusinessListBean) getIntent().getSerializableExtra(BUSINESS_BEAN);
		
		mAdapterGoodsList = new GoodsListAdapter(this);
		mXlvBusiness.setAdapter(mAdapterGoodsList);
		mXlvBusiness.setXListViewListener(this);
		mXlvBusiness.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				GoodsListBean bean = mAdapterGoodsList.getItem(arg2-1);
				if(bean != null){
				    Bundle b = new Bundle();
				    b.putSerializable(GoodsDetailActivity.KEY_DETAIL_BEAN, bean);
//				    b.putString(GoodsDetailActivity.KEY_STORE_ID, mCurrentSourceBean.getId() + "");
				    startNextActivity(b, GoodsDetailActivity.class);
				}
			}
		});
		initPop();
		// 默认按最新倒序排序
//        sendRequest(0);
		onRefresh();
	}
	
	protected void sendRequest(final int index) {
        RequestParams params = new RequestParams();
        switch (mIndexBtn) {
            case 0:
                params.addBodyParameter("zx", "1");// 最新排序字段 String,0正序1,倒序
                break;
            case 1:
                params.addBodyParameter("xl", "1");// 销量排序字段 String,0正序1,倒序
                break;
            case 2:
                params.addBodyParameter("jg", "0");// 价格排序字段 String,0正序1,倒序
                break;
            default:
                break;
        }
		params.addBodyParameter("cateId", mDiscType.getDictValue());//商品分类id
		params.addBodyParameter("store_id", mCurrentSourceBean.getId() + "");//商户id
        params.addBodyParameter("pageindex",  index + "");//第几页 String,从0开始
        params.addBodyParameter("countperpage", PAGE_MAX_COUNT + "");
		HttpUtil httpUtil = new HttpUtil(new RequestHandler(BusinessDetailActivity.this, "",
				getResources().getString(R.string.loading), 0));
		// 回调函数
		RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> result) {
				super.onSuccess(result);
				mXlvBusiness.stopLoadMore();
				mXlvBusiness.stopRefresh();
				try {
					BusinessDetailResultBean resBean = mGson.fromJson(result.result, BusinessDetailResultBean.class);
					if (Const.SUCCESS.equals(resBean.getResult())) {
					    mPageIndex = index;
					    
					    mDetailBean = resBean.getStore();
					    if(mDetailBean != null){
					        mTvTitle.setText(mDetailBean.getStore_name());
					        BitmapAsset.displayImg(BusinessDetailActivity.this, mImgTopAd, mDetailBean.getStore_banner(), R.drawable.icn_community_selected);
					        BitmapAsset.displayImg(BusinessDetailActivity.this, mImgCenter, mDetailBean.getStore_ad(), R.drawable.icn_community_selected);
					    }
					    LinkedList<GoodsListBean> goods = resBean.getGoods();
					    if(goods != null){
					        mCurrentPage = mRequestPage;
					        if(mPageIndex == 0){
					            mGoodsList.clear();
					            mGoodsList = goods;
					        }else{
					            mGoodsList.addAll(goods);
					        }
					    }
					    
					    if (goods.size() >= PAGE_MAX_COUNT) {
                            mXlvBusiness.setPullLoadEnable(true);
                        }else {
                            mXlvBusiness.setPullLoadEnable(false);
                        }
					    mAdapterGoodsList.setList(mGoodsList);
					    
					    //分类
					    mGoodsCateBeans = resBean.getCates();
					    mDiscTypesBeans = new LinkedList<DiscTypesBean>();
					    if(mGoodsCateBeans!=null && mGoodsCateBeans.size() > 0){
					    	for (GoodsCategrayBean cb : mGoodsCateBeans) {
								DiscTypesBean b = new DiscTypesBean(cb.getCate_id()+"", cb.getCate_name());
								mDiscTypesBeans.add(b);
							}
					    }
				    	mDiscTypesBeans.addFirst(new DiscTypesBean("", "全部"));
	                    if (mDiscTypesBeans != null && mDiscTypesBeans.size() > 0) {
	                        mSelectAdapter.setList(mDiscTypesBeans);
	                    }
	                    
//	                    new Handler().postDelayed(new Runnable() {
//                            
//                            @Override
//                            public void run() {
//                                mScrollView.scrollTo(0, 0);
//                            }
//                        }, 100);
					}else{
						showToast(resBean.getErrorCode());
					}
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			@Override
			public void onFailure(HttpException arg0, String arg1) {
			    super.onFailure(arg0, arg1);
			    mXlvBusiness.stopLoadMore();
                mXlvBusiness.stopRefresh();
			}
		};
		httpUtil.post(WebConstants.METHOD_LIFE_STORED_ETAIL, params, callback);
    }
	

	private PopupWindow mPopType;
	private LinkedList<DiscTypesBean> mDiscTypesBeans;
	private BaseSelectAdapter mSelectAdapter;
	private DiscTypesBean mDiscType = new DiscTypesBean("", "不限");

	private void initPop() {

		mSelectAdapter = new BaseSelectAdapter(BusinessDetailActivity.this, false);
		mSelectAdapter.setList(mDiscTypesBeans);

		LinearLayout layout = (LinearLayout) LayoutInflater.from(BusinessDetailActivity.this).inflate(
				R.layout.spinner_down, null);
		ListView listView = (ListView) layout.findViewById(R.id.list_spinner);
		listView.setSelector(R.color.txt_white);
		listView.setAdapter(mSelectAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mDiscType = mDiscTypesBeans.get(position);
				mBtnNewEst.setText(mDiscType.getMemo());
				mPopType.dismiss();
				onRefresh();
			}
		});
		mPopType = new PopupWindow(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT) {
			@Override
			public void showAsDropDown(View anchor) {
				super.showAsDropDown(anchor);
			}

			@Override
			public void dismiss() {
				super.dismiss();
			}
		};
		mPopType.setOutsideTouchable(true);
		mPopType.setFocusable(true);
		mPopType.setContentView(layout);
		mPopType.setBackgroundDrawable(new ColorDrawable());
		mPopType.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
			}
		});
		mSelectAdapter.notifyDataSetChanged();
	}
	
	private int mIndexBtn;

    private int mPageIndex;
	

    @OnClick({R.id.btn_business_detail_back, R.id.btn_business_detail_newest,
                    R.id.btn_business_detail_count, R.id.btn_business_detail_price,R.id.tv_business_detail_catogery})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_business_detail_back: // 点击返回按钮
                onBackPressed();
                break;
            case R.id.btn_business_detail_newest: {// 最新
    			mPopType.showAsDropDown(mBtnNewEst);
//                if(mIndexBtn == 0){
//                    return;
//                }
                mIndexBtn = 0;
                mBtnNewEst.setTextColor(getResources().getColor(R.color.txt_hisense_green));
                mBtnCount.setTextColor(getResources().getColor(R.color.txt_black_light2));
                mBtnPrice.setTextColor(getResources().getColor(R.color.txt_black_light2));
                
//                sendRequest(0);
            }
                break;
            case R.id.btn_business_detail_count: {// 销量
                if(mIndexBtn == 1){
                    return;
                }
                mIndexBtn = 1;
                mBtnNewEst.setTextColor(getResources().getColor(R.color.txt_black_light2));
                mBtnCount.setTextColor(getResources().getColor(R.color.txt_hisense_green));
                mBtnPrice.setTextColor(getResources().getColor(R.color.txt_black_light2));
                
                sendRequest(0);
            }
                break;
            case R.id.btn_business_detail_price: {// 价格
                if(mIndexBtn == 2){
                    return;
                }
                mIndexBtn = 2;
                mBtnNewEst.setTextColor(getResources().getColor(R.color.txt_black_light2));
                mBtnCount.setTextColor(getResources().getColor(R.color.txt_black_light2));
                mBtnPrice.setTextColor(getResources().getColor(R.color.txt_hisense_green));
                
                sendRequest(0);
            }
                break;
                
            case R.id.tv_business_detail_catogery:
//    			mPopType.showAsDropDown(mTvLine);
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

	@Override
    public void onRefresh() {
	    mRequestPage = 0;
        sendRequest(mRequestPage);
    }

    @Override
    public void onLoadMore() {
        mRequestPage = mCurrentPage + 1;
        sendRequest(mRequestPage);
    }
}
