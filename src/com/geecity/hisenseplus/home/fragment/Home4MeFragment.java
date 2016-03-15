package com.geecity.hisenseplus.home.fragment;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ScrollView;
import android.widget.TextView;
import cn.jpush.android.api.JPushInterface;

import com.geecity.hisenseplus.home.BaseFragment;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.HomeActivity;
import com.geecity.hisenseplus.home.activity.account.LoginActivity;
import com.geecity.hisenseplus.home.activity.live.AddressActivity;
import com.geecity.hisenseplus.home.activity.live.MyOrderActivity;
import com.geecity.hisenseplus.home.activity.mine.AboutHisence;
import com.geecity.hisenseplus.home.activity.mine.ChangePwdActivity;
import com.geecity.hisenseplus.home.activity.mine.MsgActivity;
import com.geecity.hisenseplus.home.activity.mine.MyAdviceActivity;
import com.geecity.hisenseplus.home.activity.mine.MyDiscActivity;
import com.geecity.hisenseplus.home.activity.mine.MyEstateActivity;
import com.geecity.hisenseplus.home.activity.mine.MyRentSaleActivity;
import com.geecity.hisenseplus.home.activity.mine.PersonActivity;
import com.geecity.hisenseplus.home.activity.repair.RepairActivity;
import com.geecity.hisenseplus.home.adapter.HouseBindAdapter;
import com.geecity.hisenseplus.home.bean.AreaInfoBean;
import com.geecity.hisenseplus.home.bean.HouseBean;
import com.geecity.hisenseplus.home.bean.LoginBean;
import com.geecity.hisenseplus.home.bean.LoginResultBean;
import com.geecity.hisenseplus.home.bean.UserBean;
import com.geecity.hisenseplus.home.utils.BitmapAsset;
import com.geecity.hisenseplus.home.utils.ViewHolder;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.geecity.hisenseplus.home.view.ATMDialog;
import com.geecity.hisenseplus.home.view.ATMDialog.DialogType;
import com.geecity.hisenseplus.home.view.ATMDialog.OnDialogListener;
import com.geecity.hisenseplus.home.view.ListViewForScrollView;
import com.geecity.hisenseplus.home.view.RoundImageView;
import com.google.gson.Gson;
import com.ibill.lib.https.HttpUtil;
import com.ibill.lib.https.HttpUtil.RequestHttpCallBack;
import com.ibill.lib.webutils.RequestHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 我
 * 
 * @ClassName: HomePageFragment
 * @author billkong
 */
public class Home4MeFragment extends BaseFragment {

	@ViewInject(R.id.btn_exit)
	private Button mButton;
	@ViewInject(R.id.scrollView1)
	private ScrollView mScrollView;

	@ViewInject(R.id.lv_frg_4_fun_bottom)
	private ListViewForScrollView mList;
	@ViewInject(R.id.gv_frg_4_fun_center)
	private GridView mGridView;

	@ViewInject(R.id.img_frg_4_head)
	private RoundImageView mImgHead;
	@ViewInject(R.id.tv_frg_4_nickname)
	private TextView mTvName;
	@ViewInject(R.id.tv_frg_4_score)
	private TextView mTvScore;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frgmt_home_4me, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initGridView();
		initListView();
	}

	@Override
	public void onResume() {
		super.onResume();
		getUserinfo();
	}

	private void initGridView() {
		FuncAdapter adapter = new FuncAdapter(getActivity());
		mGridView.setAdapter(adapter);
	}

	private void initListView() {
		ArrayList<HouseBean> beans = new ArrayList<HouseBean>();
		for (int i = 0; i < LIST_TEXT_SID.length; i++) {
			HouseBean bean = new HouseBean(getResources().getString(
					LIST_TEXT_SID[i]), "" + i);
			beans.add(bean);
		}
		HouseBindAdapter adapter = new HouseBindAdapter(getActivity());
		adapter.setArrayList(beans);
		mList.setAdapter(adapter);
		mList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				switch (position) {
				case 0: // 关于信我家
					Intent intentAbout = new Intent(getActivity(),
							AboutHisence.class);
					startActivity(intentAbout);
					break;
				case 1:// 修改密码
					((HomeActivity) getActivity()).startNextActivity(null,
							ChangePwdActivity.class);
					break;
				case 2:// 版本检测
					((HomeActivity) getActivity()).checkVersionRequest(true);
					break;
				case 3:// 修改建议
					Intent intent = new Intent(getActivity(),
							MyAdviceActivity.class);
					startActivity(intent);
					break;
				default:
					break;
				}
			}
		});
	}

	@OnClick({ R.id.tv_frg_4_person, R.id.tv_frg_4_my_estate,
			R.id.img_frg_4_head, R.id.btn_exit })
	private void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_frg_4_head:
		case R.id.tv_frg_4_person:// 个人信息
			((HomeActivity) getActivity()).startNextActivity(null,
					PersonActivity.class);
			break;
		case R.id.tv_frg_4_my_estate:// 我的房产
			((HomeActivity) getActivity()).startNextActivity(null,
					MyEstateActivity.class);
			break;
		case R.id.btn_exit: {
			final ATMDialog dialog = new ATMDialog(getActivity(),
					DialogType.TWO_BUTTON);
			dialog.setDesc("是否要退出登录?");
			dialog.setOnDialogLister(new OnDialogListener() {
				@Override
				public void onConfirm() {
					JPushInterface.stopPush(getActivity());
					Intent intent = new Intent(getActivity(),
							LoginActivity.class);
					startActivity(intent);
					getActivity().finish();
					UserBean login = new UserBean();
					GCApplication.sApp.setUserInfo(login);
					GCApplication.sApp.setDefaultAreaInfo(new AreaInfoBean());
					dialog.dismiss();
				}

				@Override
				public void onCancle() {
					dialog.dismiss();
				}
			});
			dialog.show();
		}
			break;
		default:
			break;
		}
	}

	public boolean onBackPressed() {
		return false;
	}

	private static int[] FUNC_IC_SID = { R.drawable.btn_frg_4me_msg,
			R.drawable.btn_frg_4me_search, R.drawable.btn_frg_4me_estate,
			R.drawable.btn_frg_4me_order, R.drawable.btn_frg_4me_repair,
			R.drawable.btn_frg_4me_praise, R.drawable.btn_frg_4me_address,
			R.drawable.btn_frg_4me_goods_collect
			};

	private static int[] FUNC_TEXT_SID = { R.string.frg_4_title_msg,
			R.string.frg_4_title_disc, R.string.frg_4_title_estate,
			R.string.frg_4_title_order, R.string.frg_4_title_repair,
			R.string.frg_4_title_balance, R.string.frg_4_title_address,
			R.string.frg_4_title_collect 
			};

	private static int[] LIST_TEXT_SID = { R.string.frg_4_title_2_about,
			R.string.frg_4_title_2_resetped, R.string.frg_4_title_2_vercheck,
			R.string.frg_4_title_2_advance };

	private class FuncAdapter extends BaseAdapter {
		Context context;

		public FuncAdapter(Context context) {
			this.context = context;
		}

		@Override
		public int getCount() {
			return FUNC_IC_SID.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (null == convertView) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_me_function_gridview, null);
			}
			Button button = ViewHolder.get(convertView, R.id.item_btn_me_func);
			button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					switch (position) {
					case 0:// 我的消息
						((HomeActivity) getActivity()).startNextActivity(null,
								MsgActivity.class);
						break;
					case 1:// 我的发现
						Intent intent = new Intent(getActivity(),
								MyDiscActivity.class);
						startActivity(intent);
						break;
					case 2:// 我的租售
						Intent intentEstate = new Intent(getActivity(),
								MyRentSaleActivity.class);
						startActivity(intentEstate);
						break;
					case 3:// 我的订单
						((HomeActivity) getActivity()).startNextActivity(null,
								MyOrderActivity.class);
						break;
					case 4:// 我的报修
						Bundle b = new Bundle();
						b.putString(RepairActivity.KEY_TYPE,
								RepairActivity.TYPE_REPAIR);
						((HomeActivity) getActivity()).startNextActivity(b,
								RepairActivity.class);
						break;
					case 5:// 投诉表扬
						Bundle c = new Bundle();
						c.putString(RepairActivity.KEY_TYPE,
								RepairActivity.TYPE_COMPLS);
						((HomeActivity) getActivity()).startNextActivity(c,
								RepairActivity.class);
						break;
					case 6:// 我的收件地址
						Bundle d = new Bundle();
						d.putBoolean(AddressActivity.KEY_COMMON_MODEL, true);
						((HomeActivity) getActivity()).startNextActivity(d,
								AddressActivity.class);
						break;
					case 7:// 我的收藏
						break;

					default:
						showToast("猜来猜去布吉岛皇上您点的谁的牌子，您再点一次");
						break;
					}
				}
			});
			button.setBackgroundResource(FUNC_IC_SID[position]);
			TextView selectTv = ViewHolder.get(convertView,
					R.id.item_tv_me_func);
			selectTv.setText(context.getResources().getString(
					FUNC_TEXT_SID[position]));
			return convertView;
		}
	}

	private UserBean userinfo;

	/** 获取用户信息 */
	private void getUserinfo() {
		userinfo = GCApplication.sApp.getUserInfo();
		RequestParams params = new RequestParams();
		params.addBodyParameter("account", userinfo.getAccount());
		params.addBodyParameter("password", userinfo.getPwd());
		HttpUtil httpUtil = new HttpUtil(new RequestHandler(getActivity(), "",
				"", 0));
		// 回调函数
		RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> result) {
				super.onSuccess(result);
				try {
					Gson gson = new Gson();
					LoginResultBean bean = gson.fromJson(result.result,
							LoginResultBean.class);
					if ("1".equals(bean.getResult())) {
						LoginBean login = bean.getData();
						UserBean userBean = login.getUser();
						userBean.setPwd(userinfo.getPwd());
						LogUtils.d(userBean.toString());
						GCApplication.sApp.setUserInfo(userBean);
						GCApplication.sApp.setLoginBean(login);
						BitmapAsset.displayImg(getActivity(), mImgHead,
								GCApplication.sApp.getUserInfo().getPhoto(),
								R.drawable.icn_community_selected);
						mTvName.setText(GCApplication.sApp.getUserInfo()
								.getNickName());
						mTvScore.setText("积分: "
								+ GCApplication.sApp.getUserInfo().getJifen());
					} else {
						showToast("获取用户信息失败");
					}
				} catch (Exception e) {
					showToast("获取用户信息失败");
				}
			}
		};
		// 发送数据 post方式（提交保存）
		httpUtil.post(WebConstants.METHOD_LOGIN, params, callback);
	}
}
