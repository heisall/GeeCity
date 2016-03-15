package com.geecity.hisenseplus.home.activity.account;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.mine.MyEstateActivity;
import com.geecity.hisenseplus.home.adapter.HouseBindAdapter;
import com.geecity.hisenseplus.home.bean.CityBean;
import com.geecity.hisenseplus.home.bean.CityListBean;
import com.geecity.hisenseplus.home.bean.HouseBean;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.ibill.lib.https.HttpUtil;
import com.ibill.lib.https.HttpUtil.RequestHttpCallBack;
import com.ibill.lib.webutils.RequestHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class HouseBind0Activity extends ActionBarActivity {

	@ViewInject(R.id.btn_home_menu)
	private Button mTopBackBtn;
	@ViewInject(R.id.tv_home_topbar_title)
	private TextView mTopTitle;

	@ViewInject(R.id.lv_house_bind)
	private ListView mListView;

	@ViewInject(R.id.tv_house_bind_location)
	private TextView mTVLovcation;
	@ViewInject(R.id.layout_house_bind)
	private LinearLayout mLayoutLocation;

	private LinkedList<CityBean> mCityBeans;
	private ArrayList<HouseBean> mHosueBeans = new ArrayList<HouseBean>();
	private HouseBindAdapter mAdapter;

	LocationManager locationManager;
	boolean isSelectModel;
	private boolean mIsAddModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_house_bind_0_city);
		ViewUtils.inject(this);
		isSelectModel = getIntent().getBooleanExtra(CommunitySelectActivity.KEY_TYPE, false);
		mIsAddModel = getIntent().getBooleanExtra(MyEstateActivity.KEY_ADD_BIND, false);
		ss();
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				nextBindActiv(position);
			}
		});
		mAdapter = new HouseBindAdapter(this);
		sendRequest();
		mListView.setAdapter(mAdapter);
	}

	@Override
	protected void sendRequest() {
		// TODO Auto-generated method stub
		super.sendRequest();
		HttpUtil httpUtil = new HttpUtil(new RequestHandler(
				HouseBind0Activity.this, "", getResources().getString(
						R.string.loading), 0));
		// 回调函数
		RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> result) {
				super.onSuccess(result);
				mHosueBeans.clear();
				try {
					CityListBean beans = mGson.fromJson(result.result, CityListBean.class);
					if("1".equals(beans.getResult())){
						mCityBeans = beans.getData();
						for (CityBean be : mCityBeans) {
							mHosueBeans.add(new HouseBean(be.getCityName(), be.getCityNo()));
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
		httpUtil.post(WebConstants.METHOD_GET_CITY, null, callback);
	}

	@Override
	public void configActionBar() {
		mTopBackBtn.setVisibility(View.VISIBLE);
		mTopTitle.setText(R.string.house_bind_title_0);
	}

	@OnClick({ R.id.btn_home_menu, R.id.layout_house_bind })
	public void OnClick(View v) {
		switch (v.getId()) {
		case R.id.btn_home_menu: // 点击返回按钮
			onBackPressed();
			break;
		case R.id.layout_house_bind:// 选择定位城市
			String locaCity = mTVLovcation.getText().toString();
			int index = 0;
			for (CityBean bean : mCityBeans) {
				if (bean.getCityName().equals(locaCity)) {
					nextBindActiv(index);
					return;
				}
				index ++;
			}
			showToast("很抱歉，该城市暂未开通，敬请等待");
		default:
			break;
		}
	}

	private void nextBindActiv(int index) {
		GCApplication.sApp.getBeans().clear();
		HouseBean  bean = new HouseBean(mCityBeans.get(index).getCityName(), mCityBeans.get(index).getCityNo());
		GCApplication.sApp.getBeans().add(bean);
		Bundle b = new Bundle();
		b.putBoolean(MyEstateActivity.KEY_ADD_BIND, mIsAddModel);
		b.putBoolean(CommunitySelectActivity.KEY_TYPE, isSelectModel);
		b.putString(HouseBean.KEY_ID, mCityBeans.get(index).getCityNo());
		startNextActivity(b, HouseBind1Activity.class);
	}

	@Override
	public void requestCallBack(String dataJson, RequestType type) {

	}

	public void ss() {
		// 获取到LocationManager对象
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		if (locationManager.getProvider(LocationManager.NETWORK_PROVIDER) != null) {
			/*
			 * 进行定位 provider:用于定位的locationProvider字符串:LocationManager.
			 * NETWORK_PROVIDER/LocationManager.GPS_PROVIDER
			 * minTime:时间更新间隔，单位：ms minDistance:位置刷新距离，单位：m
			 * listener:用于定位更新的监听者locationListener
			 */
			locationManager
					.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
							100, 10, locationListener);
		} else {
			// 无法定位：1、提示用户打开定位服务；2、跳转到设置界面
			Toast.makeText(this, "无法定位，请打开网络服务", Toast.LENGTH_SHORT).show();
			Intent i = new Intent();
			i.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(i);
		}

	}

	// 创建位置监听器
	private LocationListener locationListener = new LocationListener() {
		// 位置发生改变时调用
		@Override
		public void onLocationChanged(Location location) {
			// 解析地址并显示
			Geocoder geoCoder = new Geocoder(HouseBind0Activity.this);
			try {
				Double latitude = location.getLatitude();
				Double longitude = location.getLongitude();
				List<Address> list = geoCoder.getFromLocation(latitude,
						longitude, 2);
				if (list != null && !list.isEmpty()) {
					// 取第一个地址就可以
					Address address = list.get(0);
					// getCountryName 国家
					// getAdminArea 省份
					// getLocality 城市
					// getSubLocality 区
					// getFeatureName 街道
					mTVLovcation.setText(address.getLocality());
				}
			} catch (IOException e) {
				Toast.makeText(HouseBind0Activity.this, e.getMessage(),
						Toast.LENGTH_LONG).show();
			}
		}

		// provider失效时调用
		@Override
		public void onProviderDisabled(String provider) {
		}

		// provider启用时调用
		@Override
		public void onProviderEnabled(String provider) {
		}

		// 状态改变时调用
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (locationManager != null) {
			// 移除监听器
			locationManager.removeUpdates(locationListener);
		}
	}
}
