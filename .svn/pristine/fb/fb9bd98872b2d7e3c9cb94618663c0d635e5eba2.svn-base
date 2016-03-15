package com.geecity.hisenseplus.home.activity.account;

import java.util.ArrayList;
import java.util.LinkedList;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.HomeActivity;
import com.geecity.hisenseplus.home.activity.mine.MyEstateActivity;
import com.geecity.hisenseplus.home.adapter.HouseBindAdapter;
import com.geecity.hisenseplus.home.bean.AreaInfoBean;
import com.geecity.hisenseplus.home.bean.CommunityBean;
import com.geecity.hisenseplus.home.bean.CommunityListBean;
import com.geecity.hisenseplus.home.bean.HouseBean;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.ibill.lib.https.HttpUtil;
import com.ibill.lib.https.HttpUtil.RequestHttpCallBack;
import com.ibill.lib.webutils.RequestHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class HouseBind1Activity extends ActionBarActivity {


  @ViewInject(R.id.btn_home_menu)
  private Button mTopBackBtn;
  @ViewInject(R.id.tv_home_topbar_title)
  private TextView mTopTitle;

  @ViewInject(R.id.btn_reset_showpwd)
  private Button mBtnShowPwd;
  @ViewInject(R.id.lv_house_bind)
  private ListView mListView;
  
  private ArrayList<HouseBean> mHosueBeans = new ArrayList<HouseBean>();
  private HouseBindAdapter mAdapter;
  private boolean isSelectModel;
  private boolean isRentSelect;
  
  private LinkedList<CommunityBean> mCBeans;
  private String mCityNo;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_house_bind_1_area);
    ViewUtils.inject(this);
	isSelectModel = getIntent().getBooleanExtra(CommunitySelectActivity.KEY_TYPE, false);
	mIsAddModel = getIntent().getBooleanExtra(MyEstateActivity.KEY_ADD_BIND, false);
	mCityNo = getIntent().getStringExtra(HouseBean.KEY_ID);
    mListView.setOnItemClickListener(new OnItemClickListener() {

      @Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (GCApplication.sApp.getBeans().size() < 2) {
					GCApplication.sApp.getBeans().add(mHosueBeans.get(position));
				} else {
					GCApplication.sApp.getBeans().add(1, mHosueBeans.get(position));
				}
				if(isSelectModel){
					AreaInfoBean infoBean = new AreaInfoBean();
					infoBean.setA_id(mHosueBeans.get(position).getA_id());
					infoBean.setA_name(mHosueBeans.get(position).getA_name());
					GCApplication.sApp.setDefaultAreaInfo(infoBean);
					startNextActivity(null, HomeActivity.class);
				}else if(isRentSelect){
					
				}else{
					Bundle b = new Bundle();
					b.putString(HouseBean.KEY_ID, mHosueBeans.get(position).getA_id());
			        b.putBoolean(MyEstateActivity.KEY_ADD_BIND, mIsAddModel);
					startNextActivity(b, HouseBind2Activity.class);
				}
			}});
		mAdapter = new HouseBindAdapter(this);
		mListView.setAdapter(mAdapter);
		sendRequest();
    }
    private boolean mIsAddModel;

	@Override
	protected void sendRequest() {
		super.sendRequest();
		RequestParams params = new RequestParams();
		params.addBodyParameter("cityNo", TextUtils.isEmpty(mCityNo)?GCApplication.sApp.getBeans().get(0).getA_id():mCityNo);
		HttpUtil httpUtil = new HttpUtil(new RequestHandler(
				HouseBind1Activity.this, "", getResources().getString(R.string.loading), 0));
		// 回调函数
		RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> result) {
				super.onSuccess(result);
				mHosueBeans.clear();
				try {
					CommunityListBean beans = mGson.fromJson(result.result, CommunityListBean.class);
					if("1".equals(beans.getResult())){
						mCBeans = beans.getData();
						for (CommunityBean cb : mCBeans) {
							mHosueBeans.add(new HouseBean(cb.getA_name(), cb.getA_id()+""));
						}
						mAdapter.setArrayList(mHosueBeans);
					} else {
						showToast(beans.getErrorCode());
						return;
					}

				} catch (Exception e) {
					showToast(R.string.errorMsg);
				}

			}
		};
		// 发送数据 post方式（提交保存）
		httpUtil.post(WebConstants.METHOD_GET_AREA, params, callback);
	}

  @Override
  protected void onResume() {
    super.onResume();
  }

  @Override
  public void configActionBar() {
    mTopBackBtn.setVisibility(View.VISIBLE);
    mTopTitle.setText(R.string.house_bind_title_1);
  }

  @OnClick({R.id.btn_home_menu})
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
