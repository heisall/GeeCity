package com.geecity.hisenseplus.home.activity.property;

import java.util.LinkedList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.adapter.WYJDAdapter;
import com.geecity.hisenseplus.home.bean.WyjdEmployerBean;
import com.geecity.hisenseplus.home.bean.WyjdHomeBean;
import com.geecity.hisenseplus.home.bean.WyjdWorkListBean;
import com.geecity.hisenseplus.home.utils.BitmapAsset;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.geecity.hisenseplus.home.view.ListViewForScrollView;
import com.ibill.lib.https.HttpUtil;
import com.ibill.lib.https.HttpUtil.RequestHttpCallBack;
import com.ibill.lib.tools.ScreenUtils;
import com.ibill.lib.webutils.RequestHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class WYJDActivity extends ActionBarActivity {

	public static final String KEY_WORKLISTBEAN = "KEY_WORKLISTBEAN";
	@ViewInject(R.id.btn_home_menu)
	private Button mTopBackBtn;
	@ViewInject(R.id.tv_home_topbar_title)
	private TextView mTopTitle;

	@ViewInject(R.id.lv_wyjd)
	private ListViewForScrollView mListView;
	@ViewInject(R.id.scv_wyjd)
	private ScrollView mScrollView;
	@ViewInject(R.id.glry_wygj)
	private LinearLayout mGallryLayout;

	private WYJDAdapter mAdapter;

	private LayoutInflater mInflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wyjd_homepage);
		ViewUtils.inject(this);
		mInflater = LayoutInflater.from(this);
		mAdapter = new WYJDAdapter(this);
		mListView.setAdapter(mAdapter);
		mScrollView.smoothScrollTo(0, 0);
		sendRequest();
	}

	private LinkedList<WyjdWorkListBean> mWorks;
	private LinkedList<WyjdEmployerBean> mSupervises;
	
    @Override
    protected void sendRequest() {
        // TODO Auto-generated method stub
        super.sendRequest();
        RequestParams params = new RequestParams();
        params.addBodyParameter("a_id", GCApplication.sApp.getDefaultAreaInfo().getA_id());
        HttpUtil httpUtil =
                        new HttpUtil(new RequestHandler(WYJDActivity.this, "", getResources()
                                        .getString(R.string.loading), 0));
        // 回调函数
        RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> result) {
                super.onSuccess(result);
                try {
                	WyjdHomeBean resultBean = mGson.fromJson(result.result, WyjdHomeBean.class);
                    if ("1".equals(resultBean.getResult())) {
                    	mWorks = resultBean.getWork();
                    	if(mWorks != null && mWorks.size() > 0){
                    		initGallgry();
                    	}
                    	mSupervises = resultBean.getSupervise();
                    	if(mSupervises != null && mSupervises.size()>0){
                    		mAdapter.setArrayList(mSupervises);
                    	}
                    } else {
                        showToast(resultBean.getErrorCode());
                    }
                } catch (Exception e) {
                	e.printStackTrace();
                    showToast(R.string.errorMsg);
                }
            }
        };
        httpUtil.post(WebConstants.METHOD_SUP_SUPINDEX, params, callback);
    }

	private void initGallgry() {
		for (int i = 0; i < mWorks.size(); i++) {
			final WyjdWorkListBean bean = mWorks.get(i);
			if(bean == null){
				continue;
			}
			View view = mInflater.inflate(R.layout.item_wyjd_gallry, mGallryLayout, false);
			RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.item_wyjd_gallry_layout);
			layout.getLayoutParams().height = ScreenUtils.getScreenWidth(WYJDActivity.this)/2;
			layout.getLayoutParams().width = layout.getLayoutParams().height *4/3;
			layout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Bundle b = new Bundle();
					b.putSerializable(WYJDActivity.KEY_WORKLISTBEAN, bean);
					startNextActivity(b, WYJDDetailActivity.class);
				}
			});
			ImageView head = (ImageView) view.findViewById(R.id.item_wyjd_gallry_img);
			TextView tvCatogory = (TextView) view.findViewById(R.id.item_wyjd_gallry_tv_catogery);
			TextView tvTitle = (TextView) view.findViewById(R.id.item_wyjd_gallry_tv_title);
			TextView tvDate = (TextView) view.findViewById(R.id.item_wyjd_gallry_btn_date);
			TextView tvTimes = (TextView) view.findViewById(R.id.item_wyjd_gallry_tv_clicktimes);
			BitmapAsset.displayImg(WYJDActivity.this, head, bean.getPhoto(), R.drawable.icn_community_selected);
			tvCatogory.setText(bean.getTypes());
			tvCatogory.setBackgroundResource(BitmapAsset.convertBg(bean.getTypes()));
			tvDate.setText(bean.getReleaseTime());
			tvTimes.setText("点击:"+bean.getClicks());
			tvTitle.setText(bean.getContent());
			mGallryLayout.addView(view);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void configActionBar() {
		mTopBackBtn.setVisibility(View.VISIBLE);
		mTopTitle.setText(R.string.activity_title_property);
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
}
