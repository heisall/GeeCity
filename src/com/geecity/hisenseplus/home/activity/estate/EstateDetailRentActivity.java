package com.geecity.hisenseplus.home.activity.estate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.PhotoBrowseActivity;
import com.geecity.hisenseplus.home.adapter.EstateRentSubAdapter;
import com.geecity.hisenseplus.home.bean.BaseBean;
import com.geecity.hisenseplus.home.bean.EstateDetialRentBean;
import com.geecity.hisenseplus.home.bean.EstateDetialRentResultBean;
import com.geecity.hisenseplus.home.bean.SupportingBean;
import com.geecity.hisenseplus.home.utils.CommonTools;
import com.geecity.hisenseplus.home.utils.Const;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.geecity.hisenseplus.home.view.GridViewForScrollView;
import com.geecity.hisenseplus.home.view.PicViewer;
import com.geecity.hisenseplus.home.view.PicViewer.OnPageViewClick;
import com.google.gson.JsonSyntaxException;
import com.ibill.lib.https.HttpUtil;
import com.ibill.lib.https.HttpUtil.RequestHttpCallBack;
import com.ibill.lib.webutils.RequestHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class EstateDetailRentActivity extends ActionBarActivity {
	public static final String ESTATE_SECOND_DETAIL_ID = "mDetailID";
	@ViewInject(R.id.btn_estate_rent_back)
	private Button mBtnBackBtn;
	@ViewInject(R.id.btn_estate_rent_call)
	private Button mBtnCall;
    @ViewInject(R.id.btn_estate_rent_collect)
    private Button mBtnCollect;

    @ViewInject(R.id.scv_estate_detail_rent)
    private ScrollView mScrView;
    
    @ViewInject(R.id.pv_estate_rent_pics)
    private PicViewer mPicViewer;
    @ViewInject(R.id.tv_estate_rent_name)
    private TextView mTvName;
    @ViewInject(R.id.tv_estate_rent_time)
    private TextView mTvTime;
    @ViewInject(R.id.tv_estate_rent_prices)
    private TextView mTvPrices;
    @ViewInject(R.id.tv_estate_rent_type)
    private TextView mTvType;
    @ViewInject(R.id.tv_estate_rent_area)
    private TextView mTvAreas;
    @ViewInject(R.id.tv_estate_rent_floors)
    private TextView mTvFloors;
    @ViewInject(R.id.tv_estate_rent_years)
    private TextView mTvYears;
    @ViewInject(R.id.tv_estate_rent_paytype)
    private TextView mTvPayType;
    @ViewInject(R.id.tv_estate_rent_orient)
    private TextView mTvChaoX;
    @ViewInject(R.id.tv_estate_rent_zhuangx)
    private TextView mTvZhuangX;

    @ViewInject(R.id.gvfs_estate_rent_ptss)
    private GridViewForScrollView mGvPeiT;
    
    @ViewInject(R.id.tv_estate_rent_miaoshu)
    private TextView mTvMiaos;
    @ViewInject(R.id.textView08)
    private TextView mTvIsCollected;
	
    private String mDetailID;
    
    private EstateDetialRentBean mDetailBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_estate_detail_rent);
		mDetailID = getIntent().getStringExtra(EstateActivity.ESTATE_DETAIL_ID);
		ViewUtils.inject(this);
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
		params.addBodyParameter("id", mDetailID);
        params.addBodyParameter("userid", GCApplication.sApp.getUserInfo().getId()+"");
		HttpUtil httpUtil = new HttpUtil(new RequestHandler(EstateDetailRentActivity.this, "",
				getResources().getString(R.string.loading), 0));
		// 回调函数
		RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> result) {
				super.onSuccess(result);
				try {
                    EstateDetialRentResultBean resultBean = mGson.fromJson(result.result, EstateDetialRentResultBean.class);
                    if (Const.SUCCESS.equals(resultBean.getResult())) {
                        mDetailBean = resultBean.getData();
                        updateUI();
                    } else {
                        showToast(resultBean.getErrorCode());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				super.onFailure(arg0, arg1);
			}

		};
		httpUtil.post(WebConstants.METHOD_ESTATE_RENT_DETAIL, params, callback);
	}

	private List<String> mPhotoList;
	protected void updateUI() {
        if(mDetailBean == null){
            return;
        }
        String photos = mDetailBean.getPhoto();
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
        
        LinkedList<SupportingBean> beans = mDetailBean.getSupporting();
        if(beans != null && beans.size() > 0){
        	EstateRentSubAdapter adapter = new EstateRentSubAdapter(EstateDetailRentActivity.this);
        	mGvPeiT.setAdapter(adapter);
        	adapter.setArrayList(beans);
        }
        
        mTvName.setText(mDetailBean.getBuildingInfo());
        mTvTime.setText("发布时间 "+mDetailBean.getReleaseTime());
        mTvPrices.setText(mDetailBean.getRent() + "元/月");
        mTvType.setText(mDetailBean.getHouse_Indoor()+"室"+mDetailBean.getHouse_living()+"厅"+mDetailBean.getHouse_Toilet()+"卫");
        mTvAreas.setText(mDetailBean.getBuildingArea()+"㎡");
        mTvFloors.setText((mDetailBean.getFloors()+"/"+mDetailBean.getFloorCount()).replace("null", ""));
        mTvYears.setText(mDetailBean.getNiandai());
        mTvPayType.setText(mDetailBean.getFkfs());
        mTvChaoX.setText(mDetailBean.getOrientation());
        mTvZhuangX.setText(mDetailBean.getRenovationInfo());
        mTvMiaos.setText(mDetailBean.getDescription());
        mBtnCall.setText(mDetailBean.getContactPerson() + "  " + mDetailBean.getContactPhone());
        mTvIsCollected.setText(mDetailBean.getIsCollected()==0?"收藏":"已收藏");
        mBtnCollect.setBackgroundResource(mDetailBean.getIsCollected()==1?R.drawable.icn_collected : R.drawable.icn_un_collected);
   
        new Handler().postDelayed(new Runnable() {
			public void run() {
				mScrView.scrollTo(0, 0);
			}
		}, 100);
	}

    @OnClick({ R.id.btn_estate_rent_back, R.id.btn_estate_rent_call, R.id.textView08, R.id.btn_estate_rent_collect})
	public void OnClick(View v) {
		switch (v.getId()) {
		case R.id.btn_estate_rent_back: // 点击返回按钮
			onBackPressed();
			break;
		case R.id.btn_estate_rent_call: // 拨打联系电话
			if(mDetailBean != null && !TextUtils.isEmpty(mDetailBean.getContactPhone())){
				CommonTools.launchCall(EstateDetailRentActivity.this, mDetailBean.getContactPhone());
			}else{
				showToast("暂未提供联系电话");
			}
			break;
		case R.id.textView08:
		case R.id.btn_estate_rent_collect:
			sendCollectRequest();
			break;
		default:
			break;
		}
	}

	private void sendCollectRequest() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("isCollect", "" + (mDetailBean.getIsCollected() == 1 ? 0:1));
		params.addBodyParameter("lpId", mDetailID);
		params.addBodyParameter("userid", GCApplication.sApp.getUserInfo().getId()+"");
		params.addBodyParameter("type", "3");
		HttpUtil httpUtil = new HttpUtil(new RequestHandler(EstateDetailRentActivity.this, "",
				getResources().getString(R.string.loading), 1));
		// 回调函数
		RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> result) {
				super.onSuccess(result);
				try {
					BaseBean resultBean = mGson.fromJson(result.result, BaseBean.class);
                    if (Const.SUCCESS.equals(resultBean.getResult())) {
                    	boolean isColed = mDetailBean.getIsCollected() == 1;
            			mBtnCollect.setBackgroundResource(!isColed?R.drawable.icn_collected : R.drawable.icn_un_collected);
            			mTvIsCollected.setText(!isColed?"已收藏":"收藏");
            			mDetailBean.setIsCollected(!isColed?1:0);
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
