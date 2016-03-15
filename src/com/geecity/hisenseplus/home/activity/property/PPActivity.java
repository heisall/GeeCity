package com.geecity.hisenseplus.home.activity.property;

import java.util.HashMap;
import java.util.LinkedList;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.BaseFragment;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.property.frgmt.P0UnPayFragment;
import com.geecity.hisenseplus.home.activity.property.frgmt.P1AllFragment;
import com.geecity.hisenseplus.home.activity.property.model.BillsInfos;
import com.geecity.hisenseplus.home.bean.ManagerHomeBean;
import com.geecity.hisenseplus.home.bean.ManagerRoomBean;
import com.geecity.hisenseplus.home.utils.BitmapAsset;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.geecity.hisenseplus.home.view.RoundImageView;
import com.google.gson.Gson;
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
 * 物业账单
 * 
 * @author Administrator
 * 
 */
public class PPActivity extends ActionBarActivity {

	@ViewInject(R.id.btn_home_menu)
	private Button mTopBackBtn;
	@ViewInject(R.id.tv_home_topbar_title)
	private TextView mTopTitle;

	@ViewInject(R.id.img_pp_head)
	private RoundImageView mImgHead;
	// 小区
	@ViewInject(R.id.tv_pp_name)
	private TextView mTvName;
	// 单元
	@ViewInject(R.id.tv_pp_no)
	private TextView mTvNO;
	// 未缴费账单
	@ViewInject(R.id.tv_pp_unpay)
	private TextView mTvUnPay;
	// 全部账单
	@ViewInject(R.id.tv_pp_all)
	private TextView mTvAll;
	// TODO 该布局，在确认订单界面需要隐藏掉
	@ViewInject(R.id.ly_pp_center)
	private LinearLayout mLyCenter;
	@ViewInject(R.id.line_top)
	private TextView mTvLine;

	private FragmentTransaction mTrans;
	/* 当前fragment */
	private BaseFragment sCurrentFrgt;
	/* 新的fragment */
	private static BaseFragment newFrgt;
	/* fragment 集合 */
	private static HashMap<Integer, BaseFragment> fragmentMap;

	private int type = 0;
	
	//在未支付订单选择后更新，进入订单确认界面时使用
	public LinkedList<BillsInfos> mWaitPayBills;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wygl_payment);
		ViewUtils.inject(this);
		fragmentMap = new HashMap<Integer, BaseFragment>();
		BitmapAsset.displayImg(this, mImgHead, GCApplication.sApp.getUserInfo()
				.getPhoto(), R.drawable.icon_default_head);
		// 获取房源信息
		getRoomInfo();
	}

	/**
	 * 初始化fragment 0-未缴费，1-全部， 2-支付<br>
	 * 其他Fragment控制跳转时，需要先调用 {@link PPActivity.setType}
	 * 
	 * @Title: initFragment
	 * @param loadType
	 *            0-表示是初始化布局 1-表示是点击按钮时触发的事件 void
	 * @author billkong
	 * @since 2015-11-24 V 1.0
	 */
	public void initFragment(int loadType) {
		switch (type) {
		case 0:
			mTopTitle.setText("物业账单");
			break;
		case 1:
			mTopTitle.setText("物业账单");
			// mTopTitle.setText("用户结算");
			break;
		case 2:
			mTopTitle.setText("确认订单");
			break;
		default:
			mTopTitle.setText("物业账单");
			break;
		}

		mTrans = getSupportFragmentManager().beginTransaction();
		if (loadType == 0) {
			switch (type) {
			case 0: // 未缴
				newFrgt = new P0UnPayFragment();
				fragmentMap.put(type, newFrgt);
				break;
			case 1: // 全部
				newFrgt = new P1AllFragment();
				fragmentMap.put(type, newFrgt);
				break;
			}
		} else {
			newFrgt = fragmentMap.get(type);
			if (newFrgt == null) {
				switch (type) {
				case 0: // 未缴
					newFrgt = new P0UnPayFragment();
					fragmentMap.put(type, newFrgt);
					break;
				case 1: // 全部
					newFrgt = new P1AllFragment();
					fragmentMap.put(type, newFrgt);
					break;
				}
			}
		}
		// 切换时不刷新 fragment
		if (!newFrgt.isAdded()) {
			if (sCurrentFrgt == null) {
				mTrans.add(R.id.ly_wygl_payment, newFrgt).commit();
			} else {
				mTrans.add(R.id.ly_wygl_payment, newFrgt).hide(sCurrentFrgt)
						.show(newFrgt).commit();
			}
		} else {
			mTrans.hide(sCurrentFrgt).show(newFrgt).commit();
		}
		// 把新的fragment 赋值给当前frangment
		sCurrentFrgt = newFrgt;
	}

	@Override
	public void configActionBar() {
		mTopBackBtn.setVisibility(View.VISIBLE);
	}

	@OnClick({ R.id.btn_home_menu, R.id.tv_pp_all, R.id.tv_pp_unpay })
	public void OnClick(View v) {
		switch (v.getId()) {
		case R.id.btn_home_menu: // 点击返回按钮
			onBackPressed();
			break;
		case R.id.tv_pp_unpay:// 未缴费
			type = 0;
			mTvAll.setTextColor(getResources().getColor(R.color.txt_black));
			mTvUnPay.setTextColor(getResources().getColor(R.color.txt_hisense_green));
			initFragment(0);
			break;
		case R.id.tv_pp_all:// 全部账单
			type = 1;
			initFragment(1);
			mTvAll.setTextColor(getResources().getColor(R.color.txt_hisense_green));
			mTvUnPay.setTextColor(getResources().getColor(R.color.txt_black));
			break;
		default:
			break;
		}
	}

	@Override
	public void requestCallBack(String dataJson, RequestType type) {

	}

	/**
	 * 0-未缴费，1-全部， 2-支付
	 * 
	 * @param type
	 */
	public void setType(int type) {
		this.type = type;
	}

	public void setLyCenterVisible(int visible) {
		mLyCenter.setVisibility(visible);
		mTvLine.setVisibility(visible);
	}

	/** 获取房源信息 */
	private void getRoomInfo() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("a_id", GCApplication.sApp.getDefaultAreaInfo()
				.getA_id());
		params.addBodyParameter("userid", GCApplication.sApp.getUserInfo()
				.getId() + "");
		HttpUtil httpUtil = new HttpUtil(new RequestHandler(PPActivity.this,
				"", getResources().getString(R.string.loading), 0));
		// 回调函数
		RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> result) {
				super.onSuccess(result);
				try {
					Gson gson = new Gson();
					ManagerHomeBean resBean = gson.fromJson(result.result,
							ManagerHomeBean.class);
					if ("1".equals(resBean.getResult())) {
						ManagerRoomBean room = resBean.getRoom();
						if (room != null) {
							if (TextUtils.isEmpty(room.getA_name())
									|| TextUtils.isEmpty(room.getB_id())
									|| TextUtils.isEmpty(room.getR_id())) {
								return;
							}
							mTvName.setText(room.getA_name() + "小区");
							mTvNO.setText((room.getB_id() + "号楼"
									+ room.getR_dy() + "单元" + room.getR_id())
									.replace("null", ""));

							b_id = room.getB_id();
							r_dy = room.getR_dy();
							r_id = room.getR_id();

							initFragment(0);
						}
					}
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		httpUtil.post(WebConstants.METHOD_GET_MANAGER_HOMEPAGE, params,
				callback);
	}

	/** 提供fragment使用的相关参数 */
	public static String b_id = null;
	public static int r_dy = 0;
	public static String r_id = null;
}
