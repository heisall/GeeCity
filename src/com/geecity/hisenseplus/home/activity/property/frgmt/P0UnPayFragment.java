package com.geecity.hisenseplus.home.activity.property.frgmt;

import java.text.DecimalFormat;
import java.util.LinkedList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.geecity.hisenseplus.home.BaseFragment;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.property.PPActivity;
import com.geecity.hisenseplus.home.activity.property.ProPayOrderActivity;
import com.geecity.hisenseplus.home.activity.property.model.BillsInfos;
import com.geecity.hisenseplus.home.activity.property.model.PropertyP01Adapter;
import com.geecity.hisenseplus.home.bean.BillUnPayListBean;
import com.geecity.hisenseplus.home.utils.Const;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.geecity.hisenseplus.home.view.ListViewForScrollView;
import com.google.gson.Gson;
import com.ibill.lib.https.HttpUtil;
import com.ibill.lib.https.HttpUtil.RequestHttpCallBack;
import com.ibill.lib.webutils.RequestHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 未缴费
 * 
 * @ClassName: P0UnPayFragment
 * @Description:
 * @author billkong
 */
public class P0UnPayFragment extends BaseFragment {

	@ViewInject(R.id.cb_payment_left)
	private CheckBox mCb;
	@ViewInject(R.id.xl_payment_left)
	private ListViewForScrollView mLvFs;// 账单列表
	@ViewInject(R.id.tv_payment_price)
	public static TextView mTvTotalPrice;// 总计价格
	@ViewInject(R.id.btn_payment_pay)
	private Button mBtnPay;// 缴纳费用

	// 总价格
	private double bill_totalPrice = 0;

	private PropertyP01Adapter mAdapter;
	private LinkedList<BillsInfos> mBeans = new LinkedList<BillsInfos>();

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.frgmt_pp0_unpay, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mLvFs.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (!mBeans.get(position).isCheck()) {
					mBeans.get(position).setCheck(true);
					bill_totalPrice += mBeans.get(position).getTotalPrice();
				} else {
					mBeans.get(position).setCheck(false);
					bill_totalPrice -= mBeans.get(position).getTotalPrice();
				}

				// 保留一位小数
				// 统计最后总价
				mTvTotalPrice.setText(reserved2DecimalPlaces() + "");

				mAdapter.notifyDataSetChanged();
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		((PPActivity) getActivity()).setLyCenterVisible(View.VISIBLE);
		findBills();
	}

	@OnClick({ R.id.btn_home_menu, R.id.cb_payment_left, R.id.btn_payment_pay })
	private void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_home_menu:// 退出
			onBackPressed();
			break;
		case R.id.cb_payment_left:// 全选
			bill_totalPrice = 0.0;
			for (BillsInfos bean : mBeans) {
				bean.setCheck(mCb.isChecked());
				if (mCb.isChecked()) {
					bill_totalPrice += bean.getTotalPrice();
				} else {
					bill_totalPrice = 0.0;
				}
			}

			// 保留一位小数
			mTvTotalPrice.setText(reserved2DecimalPlaces() + "");
			mAdapter.notifyDataSetChanged();
			break;
		case R.id.btn_payment_pay:// 支付

			GCApplication.sApp.mWaitPayBills = new LinkedList<>();
			for (BillsInfos bean : mBeans) {
				if(bean.isCheck()){
					GCApplication.sApp.mWaitPayBills.add(bean);
				}
			}
			if(GCApplication.sApp.mWaitPayBills.size() > 0){
				((PPActivity) getActivity()).startNextActivity(null, ProPayOrderActivity.class);
			}else{
				showToast("请选择一个或多个未缴账单");
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 获取服务器数据
	 * 
	 * @Title: getData void
	 */
	// 获取账单列表
	private void findBills() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("a_id", GCApplication.sApp.getDefaultAreaInfo().getA_id());
		params.addBodyParameter("b_id", PPActivity.b_id);
		params.addBodyParameter("r_dy", PPActivity.r_dy + "");
		params.addBodyParameter("r_id", PPActivity.r_id);
		params.addBodyParameter("sign", "0");
		HttpUtil httpUtil = new HttpUtil(new RequestHandler(getActivity(), "", getResources().getString(R.string.loading), 0));
		// 回调函数
		RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> result) {
				super.onSuccess(result);
				BillUnPayListBean bean = new Gson().fromJson(result.result, BillUnPayListBean.class);
				if(!Const.SUCCESS.equals(bean.getResult())){
				    return;
				}
				// 获取账单信息
				mBeans = singleChild(bean.getData());
				if (mAdapter == null) {
					mAdapter = new PropertyP01Adapter(getActivity());
				}
				mAdapter.setList(mBeans);
				mLvFs.setAdapter(mAdapter);
				mTvTotalPrice.setText("0.00");
			}
		};
		httpUtil.post(WebConstants.METHOD_BILL_FINDBILLS, params, callback);
	}

	public boolean onBackPressed() {
		return false;
	}

	/**
	 * 数据整合
	 */
	private LinkedList<BillsInfos> singleChild(LinkedList<BillsInfos> mList) {

		int len = mList.size();
		for (int i = 0; i < len; i++) {
		    BillsInfos parentBills = mList.get(i);
		    parentBills.setPriceForShow(String.valueOf(parentBills.getT_money()));
		    parentBills.setTotalPrice(parentBills.getT_money());
		    parentBills.setIds(String.valueOf(parentBills.getId()));
			int j = i + 1;
			for (; j < len; j++) {
	            BillsInfos childBills = mList.get(j);
				if ((parentBills.getT_date()).equals((childBills.getT_date()))) {
					parentBills.setT_item(parentBills.getT_item() + "\n" + childBills.getT_item());// 字符串拼接
					parentBills.setPriceForShow(parentBills.getPriceForShow() + "\n" + childBills.getT_money());// 字符串拼接
					parentBills.setTotalPrice(parentBills.getTotalPrice() + childBills.getT_money());
				    parentBills.setIds(parentBills.getIds() + ","+String.valueOf(childBills.getId()));//拼接支付使用的物业账单ID
					parentBills.setCheck(false);
					mList.remove(j);// 删掉第 j 位置上的数据
					len--;// 集合长度减去1
					j = i;// 保证指针移动到相应位置
				}
			}
		}

		return mList;
	}

	/** 保留一位小数 */
	private double reserved2DecimalPlaces() {
		DecimalFormat df = new DecimalFormat("#.00");
		String v = df.format(bill_totalPrice);
		double newValue = 0;
		if (isNumber(v)) {
			newValue = Double.parseDouble(v);
		}

		return newValue;
	}
	
	/** 判断是否可以转化为数字 */
	public boolean isNumber(String string) {
		try {
			Double.parseDouble(string);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	
}
