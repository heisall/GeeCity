package com.geecity.hisenseplus.home.activity.estate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.PhotoBrowseActivity;
import com.geecity.hisenseplus.home.adapter.EstateNewInfoAdapter;
import com.geecity.hisenseplus.home.bean.BaseBean;
import com.geecity.hisenseplus.home.bean.DiscTypesBean;
import com.geecity.hisenseplus.home.bean.EstateDetialNewBean;
import com.geecity.hisenseplus.home.bean.EstateDetialNewPhotoBean;
import com.geecity.hisenseplus.home.bean.EstateDetialNewResultBean;
import com.geecity.hisenseplus.home.utils.BitmapAsset;
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
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class EstateDetailNewActivity extends ActionBarActivity {
	@ViewInject(R.id.btn_estate_new_back)
	private Button mBtnBackBtn;
	@ViewInject(R.id.btn_estate_new_call)
	private Button mBtnCall;
	@ViewInject(R.id.tv_estate_new_name)
	private TextView mTvName;
	@ViewInject(R.id.tv_estate_new_price)
	private TextView mTvPrice;
	@ViewInject(R.id.btn_estate_new_collect)
	private Button mBtnCollection;
	@ViewInject(R.id.textView1)
	private TextView mTvCollected;
	@ViewInject(R.id.btn_share)
	private Button mBtnShare;
	@ViewInject(R.id.textViewShare)
	private TextView mTvShare;
	
	@ViewInject(R.id.ly_estate_new_pics)
	private LinearLayout mLyPics;
	@ViewInject(R.id.lvfs_estate_new_info)
	private ListViewForScrollView mLvInfos;
	@ViewInject(R.id.scv_estate_detail_new)
	private ScrollView mSView;
    @ViewInject(R.id.pv_estate_second_pics)
    private PicViewer mPicViewer;
	
	private EstateNewInfoAdapter mAdpterInfo;

	private LinkedList<EstateDetialNewPhotoBean> mPhotos;
	private EstateDetialNewBean mDetailBean;
	private String mDetailID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_estate_detail_new);
		ViewUtils.inject(this);
		mDetailID = getIntent().getStringExtra(EstateActivity.ESTATE_DETAIL_ID);
		mAdpterInfo = new EstateNewInfoAdapter(this); 
		mLvInfos.setAdapter(mAdpterInfo);
		mInflater = LayoutInflater.from(this);
		mPicViewer.setOnPageViewClick(new OnPageViewClick() {
			
			@Override
			public void onPageViewClick(int postion) {
				ArrayList<String> photos = new ArrayList<>();
				for (String eb : mPhotoList) {
					photos.add(eb);
				}
				Bundle b = new Bundle();
				b.putStringArrayList(PhotoBrowseActivity.KEY_PIC_INFO, photos);
				startNextActivity(b, PhotoBrowseActivity.class);
			}
		});
		sendRequest();
	}

	@Override
	protected void sendRequest() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("areaId", mDetailID);
		params.addBodyParameter("userid", GCApplication.sApp.getUserInfo().getId()+"");
		HttpUtil httpUtil = new HttpUtil(new RequestHandler(EstateDetailNewActivity.this, "",
				getResources().getString(R.string.loading), 0));
		// 回调函数
		RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> result) {
				super.onSuccess(result);
				try {
                    EstateDetialNewResultBean resultBean = mGson.fromJson(result.result, EstateDetialNewResultBean.class);
                    if (Const.SUCCESS.equals(resultBean.getResult())) {
                    	mPhotos = resultBean.getPhoto();
                    	mDetailBean = resultBean.getHouse();
                        updateUI();
                    } else {
                        showToast(resultBean.getErrorCode());
                    }
                } catch (JsonSyntaxException e) {
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				super.onFailure(arg0, arg1);
			}

		};
		httpUtil.post(WebConstants.METHOD_ESTATE_NEW_DETAIL, params, callback);
	}
	
	private LayoutInflater mInflater;
	private List<String> mPhotoList;
	protected void updateUI() {
		if(mPhotos != null && mPhotos.size() > 0){
			mLyPics.removeAllViews();
			for (int i = 0; i < mPhotos.size(); i++) {
				final EstateDetialNewPhotoBean bean = mPhotos.get(i);
				if(bean == null){
					continue;
				}
				View view = mInflater.inflate(R.layout.item_estate_new_gallry, mLyPics, false);
				RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.item_estate_gallry_layout);
				LayoutParams params = layout.getLayoutParams();
				params.width = ScreenUtils.getScreenWidth(EstateDetailNewActivity.this) / 3;
				params.height = ScreenUtils.getScreenWidth(EstateDetailNewActivity.this) / 3 + 30;
				layout.setLayoutParams(params);
				layout.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						ArrayList<String> photos = new ArrayList<>();
						for (EstateDetialNewPhotoBean eb : mPhotos) {
							photos.add(eb.getHxt());
						}
						Bundle b = new Bundle();
						b.putStringArrayList(PhotoBrowseActivity.KEY_PIC_INFO, photos);
						startNextActivity(b, PhotoBrowseActivity.class);
					}
				});
				ImageView head = (ImageView) view.findViewById(R.id.item_estate_gallry_img);
				BitmapAsset.displayImg(EstateDetailNewActivity.this, head, bean.getHxt(), R.drawable.icn_community_selected);
				mLyPics.addView(view);
			}
		
		}
		if(mDetailBean != null){
	        String photos = mDetailBean.getZst();
	        if(!TextUtils.isEmpty(photos)){
	        	mPhotoList = Arrays.asList(photos.split(","));
	            if(mPhotoList != null && mPhotoList.size() > 0){
	                ArrayList<String> urls = new ArrayList<String>();
	                for (String string : mPhotoList) {
	                    urls.add(string);
	                }
	                mPicViewer.setPicUrls(urls);
	            }
	        }
			mTvName.setText(mDetailBean.getLpmc());
			mTvPrice.setText(mDetailBean.getJiage() + "");
			boolean isColed = mDetailBean.getIsCollected() == 1;
			mBtnCollection.setBackgroundResource(isColed?R.drawable.icn_collected : R.drawable.icn_un_collected);
			mTvCollected.setText(isColed?"已收藏":"收藏");
			LinkedList<DiscTypesBean> ilist = new LinkedList<DiscTypesBean>();
			// 开盘、地址、状态、优惠、特点、最新动态、周边配套、详细信息
			ilist.add(new DiscTypesBean("开         盘    ", mDetailBean.getKpsj()));
			ilist.add(new DiscTypesBean("地         址    ", mDetailBean.getCityName() + mDetailBean.getQuyu() + mDetailBean.getWeiZhi()));
			ilist.add(new DiscTypesBean("状         态    ", mDetailBean.getZt()));
			ilist.add(new DiscTypesBean("优         惠    ", mDetailBean.getYhxx()));
			ilist.add(new DiscTypesBean("特         点    ", mDetailBean.getTd()));
			ilist.add(new DiscTypesBean("最新动态    ", mDetailBean.getZxdt()));
			ilist.add(new DiscTypesBean("周边配套    ", mDetailBean.getZbpt()));
			ilist.add(new DiscTypesBean("详细信息    ", mDetailBean.getXxxx()));
			mAdpterInfo.setList(ilist);
			mAdpterInfo.notifyDataSetChanged();
		}
		new Handler().postDelayed(new Runnable() {
            
            @Override
            public void run() {
                mSView.scrollTo(0, 0);
            }
        }, 100);
	}

	@OnClick({ R.id.btn_estate_new_back, R.id.btn_estate_new_call, R.id.btn_estate_new_collect,R.id.textView1
		,R.id.btn_share,R.id.textViewShare})
	public void OnClick(View v) {
		switch (v.getId()) {
		case R.id.btn_estate_new_back: // 点击返回按钮
			onBackPressed();
			break;
		case R.id.btn_estate_new_call: // 预约看房
			if(mDetailBean != null && !TextUtils.isEmpty(mDetailBean.getLxdh())){
				CommonTools.launchCall(EstateDetailNewActivity.this, mDetailBean.getLxdh());
			}else{
				showToast("暂未提供联系电话");
			}
			break;
		case R.id.textView1:
		case R.id.btn_estate_new_collect:
			sendCollectRequest();
			break;
		case R.id.textViewShare:
		case R.id.btn_share:
			share();
			break;
		default:
			break;
		}
	}
	
	private void share() {
//      final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
//      mController.getConfig().removePlatform(SHARE_MEDIA.SINA, SHARE_MEDIA.TENCENT ); 
//      mController.setShareContent(mDetailBean.getLpmc());
//      mController.setShareImage(new UMImage(EstateDetailNewActivity.this, mPhotoList.get(0)));
//      // 添加微信平台
//      UMWXHandler wxHandler = new UMWXHandler(EstateDetailNewActivity.this,WebConstants.WXAPP_APPID, WebConstants.WXAPP_APPSECRET);
//      wxHandler.setTitle("海信地产，值得信赖");
//      wxHandler.setTargetUrl(WebConstants.SHARE_URL_ESTATE_NEW + mDetailBean.getId());
//      wxHandler.addToSocialSDK();
//      // 添加微信朋友圈
//      UMWXHandler wxCircleHandler = new UMWXHandler(EstateDetailNewActivity.this,WebConstants.WXAPP_APPID, WebConstants.WXAPP_APPSECRET);
//      wxCircleHandler.setTitle(mDetailBean.getLpmc());
//      wxCircleHandler.setTargetUrl(WebConstants.SHARE_URL_ESTATE_NEW + mDetailBean.getId());
//      wxCircleHandler.setToCircle(true);
//      wxCircleHandler.addToSocialSDK();
//      // 是否只有已登录用户才能打开分享选择页
//      mController.openShare(EstateDetailNewActivity.this, false);
  
	}
	
	private void sendCollectRequest() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("isCollect", "" + (mDetailBean.getIsCollected() == 1 ? 0:1));
		params.addBodyParameter("lpId", mDetailID);
		params.addBodyParameter("userid", GCApplication.sApp.getUserInfo().getId()+"");
		params.addBodyParameter("type", "1");
		HttpUtil httpUtil = new HttpUtil(new RequestHandler(EstateDetailNewActivity.this, "",
				getResources().getString(R.string.loading), 0));
		// 回调函数
		RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> result) {
				super.onSuccess(result);
				try {
					BaseBean resultBean = mGson.fromJson(result.result, BaseBean.class);
                    if (Const.SUCCESS.equals(resultBean.getResult())) {
                    	boolean isColed = mDetailBean.getIsCollected() == 1;
            			mBtnCollection.setBackgroundResource(!isColed?R.drawable.icn_collected : R.drawable.icn_un_collected);
                        mDetailBean.setIsCollected(!isColed ? 1 : 0);
            			mTvCollected.setText(!isColed?"已收藏":"收藏");
                    } else {
                        showToast(resultBean.getErrorCode());
                    }
                } catch (JsonSyntaxException e) {
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				super.onFailure(arg0, arg1);
			}

		};
		httpUtil.post(WebConstants.METHOD_ESTATE_COLLECT, params, callback);
	}

	@Override
	public void requestCallBack(String dataJson, RequestType type) {

	}
	
	@Override
	public void configActionBar() {
	}

}
