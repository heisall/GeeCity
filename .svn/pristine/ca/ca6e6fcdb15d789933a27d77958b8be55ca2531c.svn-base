package com.geecity.hisenseplus.home.activity.account;

import java.util.ArrayList;
import java.util.LinkedList;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.mine.MyEstateActivity;
import com.geecity.hisenseplus.home.adapter.HouseBindAdapter;
import com.geecity.hisenseplus.home.bean.HouseBean;
import com.geecity.hisenseplus.home.bean.RoomBean;
import com.geecity.hisenseplus.home.bean.RoomListBean;
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

public class HouseBind3Activity extends ActionBarActivity {

	@ViewInject(R.id.btn_home_menu)
	private Button mTopBackBtn;
	@ViewInject(R.id.tv_home_topbar_title)
	private TextView mTopTitle;

	@ViewInject(R.id.lv_house_bind)
	private ListView mListView;

	private ArrayList<HouseBean> mHosueBeans = new ArrayList<HouseBean>();
	private HouseBindAdapter mAdapter;
	private LinkedList<RoomBean> mRBeans;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_house_bind_3_unit);
		ViewUtils.inject(this);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (GCApplication.sApp.getBeans().size() < 4) {
					GCApplication.sApp.getBeans().add(mHosueBeans.get(position));
				} else {
					GCApplication.sApp.getBeans().add(3, mHosueBeans.get(position));
				}
				//
				RoomBean bean = mRBeans.get(position);
				HouseBean hbDY = new HouseBean(""+bean.getR_dy(), ""+bean.getR_dy());
				if (GCApplication.sApp.getBeans().size() < 5) {
					GCApplication.sApp.getBeans().add(hbDY);
				} else {
					GCApplication.sApp.getBeans().add(4, hbDY);
				}

				HouseBean hbRm = new HouseBean(""+bean.getR_dy(), bean.getR_id());
				if (GCApplication.sApp.getBeans().size() < 6) {
					GCApplication.sApp.getBeans().add(hbRm);
				} else {
					GCApplication.sApp.getBeans().add(5, hbRm);
				}
				Bundle b = new Bundle();
                b.putBoolean(MyEstateActivity.KEY_ADD_BIND, mIsAddModel);
				startNextActivity(b, HouseBind4Activity.class, true);
			}
		});
		mAdapter = new HouseBindAdapter(this);
		mListView.setAdapter(mAdapter);
		sendRequest();
        mIsAddModel = getIntent().getBooleanExtra(MyEstateActivity.KEY_ADD_BIND, false);
    }
    private boolean mIsAddModel;
	
	@Override
	protected void sendRequest() {
		super.sendRequest();
		RequestParams params = new RequestParams();
		params.addBodyParameter("a_id", GCApplication.sApp.getBeans().get(1).getA_id());
		params.addBodyParameter("b_id", GCApplication.sApp.getBeans().get(2).getA_id());
		HttpUtil httpUtil = new HttpUtil(new RequestHandler(
				HouseBind3Activity.this, "", getResources().getString(R.string.loading), 0));
		// 回调函数
		RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> result) {
				super.onSuccess(result);
				mHosueBeans.clear();
				try {
					RoomListBean beans = mGson.fromJson(result.result, RoomListBean.class);
					if("1".equals(beans.getResult())){
						mRBeans = beans.getData();
						for (RoomBean bb : mRBeans) {
							mHosueBeans.add(new HouseBean(bb.getR_dy()+"单元"+bb.getR_id(), "" + bb.getJU_RID()));
						}
						mAdapter.setArrayList(mHosueBeans);
					} else {
						showToast(beans.getErrorCode());
						return;
					}

				} catch (Exception e) {
					e.printStackTrace();
					showToast(R.string.errorMsg);
				}

			}
		};
		// 发送数据 post方式（提交保存）
		httpUtil.post(WebConstants.METHOD_GET_ROMES, params, callback);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void configActionBar() {
		mTopBackBtn.setVisibility(View.VISIBLE);
		mTopTitle.setText(R.string.house_bind_title_3);
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
