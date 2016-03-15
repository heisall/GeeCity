package com.geecity.hisenseplus.home.activity.live;

import java.util.ArrayList;
import java.util.LinkedList;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.adapter.LifeInfoMenuAdapter;
import com.geecity.hisenseplus.home.bean.ADBean;
import com.geecity.hisenseplus.home.bean.LifeHomePageBean;
import com.geecity.hisenseplus.home.bean.MallMenuBean;
import com.geecity.hisenseplus.home.utils.CommonTools;
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
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 上门信息（首页生活信息的更多跳转到的界面）
 * @author Administrator
 *
 */
public class LifeInfoActivity extends ActionBarActivity {

	@ViewInject(R.id.btn_home_menu)
	private Button mTopBackBtn;
	@ViewInject(R.id.tv_home_topbar_title)
	private TextView mTopTitle;

	@ViewInject(R.id.pv_life_top)
	private PicViewer mPvTopAd;

	@ViewInject(R.id.gvfscv_life_menu)
	private GridViewForScrollView mGvMenu;

	private LifeInfoMenuAdapter mMenuAdapter;
	private LinkedList<MallMenuBean> mMenus;
	protected LinkedList<ADBean> mADBeans;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_life_homepage);
		ViewUtils.inject(this);
		mMenuAdapter = new LifeInfoMenuAdapter(this);
		mGvMenu.setAdapter(mMenuAdapter);
		mPvTopAd.setOnPageViewClick(new OnPageViewClick() {
			
			@Override
			public void onPageViewClick(int postion) {
                CommonTools.adClick(LifeInfoActivity.this, mADBeans, postion);
			}
		});
		sendRequest();
	}
	
	@Override
	protected void sendRequest() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("a_id", GCApplication.sApp.getDefaultAreaInfo().getA_id());
		HttpUtil httpUtil = new HttpUtil(new RequestHandler(LifeInfoActivity.this, "",
				getResources().getString(R.string.loading), 0));
		// 回调函数
		RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> result) {
				super.onSuccess(result);
				try {
					LifeHomePageBean resBean = mGson.fromJson(result.result, LifeHomePageBean.class);
					if ("1".equals(resBean.getResult())) {
						mADBeans = resBean.getAd();
						if(mADBeans != null && mADBeans.size() > 0){
							ArrayList<String> pics = new ArrayList<String>();
							for (ADBean adBean : mADBeans) {
								pics.add(adBean.getPhoto());
							}
							mPvTopAd.setPicUrls(pics);
						}
						
						mMenus = resBean.getThumb();
						if(mMenus != null && mMenus.size() > 0){
							mMenuAdapter.setMenus(mMenus);
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
		httpUtil.post(WebConstants.METHOD_LIFEINFO_HOMEPAGE, params, callback);
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
	public void configActionBar() {
		mTopTitle.setText(R.string.activity_title_live_info);
	}

	@Override
	public void requestCallBack(String dataJson, RequestType type) {
	}
}
