package com.geecity.hisenseplus.home.activity.property.frgmt;

import java.util.LinkedList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.geecity.hisenseplus.home.BaseFragment;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.property.PPActivity;
import com.geecity.hisenseplus.home.activity.property.ProPayOrderActivity;
import com.geecity.hisenseplus.home.activity.property.model.BillsInfos;
import com.geecity.hisenseplus.home.adapter.PropertyP1Adapter;
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
 * 全部账单
 * 
 * @ClassName: P1AllFragment
 * @Description:
 * @author billkong
 */
public class P1AllFragment extends BaseFragment {

	@ViewInject(R.id.xl_payment_left)
	private ListViewForScrollView mLvFs;// 账单列表
	@ViewInject(R.id.btn_payment_pay)
	private Button mBtnPay;// 缴纳费用

	private PropertyP1Adapter mAdapter;
	private LinkedList<BillsInfos> mBeans = new LinkedList<BillsInfos>();

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frgmt_pp1_all, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (mAdapter == null) {
			mAdapter = new PropertyP1Adapter(getActivity());
		}
		mAdapter.setArrayList(mBeans);
		mLvFs.setAdapter(mAdapter);
	}

	/**
	 * 获取服务器数据
	 * 
	 * @Title: getData void
	 */
	// 获取账单列表
	private void findBills() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("a_id", GCApplication.sApp.getDefaultAreaInfo()
				.getA_id());
		params.addBodyParameter("b_id", PPActivity.b_id);
		params.addBodyParameter("r_dy", PPActivity.r_dy + "");
		params.addBodyParameter("r_id", PPActivity.r_id);
		// 0是未支付 1是已支付 空是所有
		params.addBodyParameter("sign", "");
		HttpUtil httpUtil = new HttpUtil(new RequestHandler(getActivity(), "",
				getResources().getString(R.string.loading), 0));
		// 回调函数
		RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> result) {
				super.onSuccess(result);
				BillUnPayListBean bean = new Gson().fromJson(result.result,
						BillUnPayListBean.class);
				if (!Const.SUCCESS.equals(bean.getResult())) {
					return;
				}
				// 获取账单信息
				mBeans = singleChild(bean.getData());
				mAdapter.setArrayList(mBeans);
			}
		};
		httpUtil.post(WebConstants.METHOD_BILL_FINDBILLS, params, callback);
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
		    parentBills.setDatas(parentBills.getT_lastdate()+"-"+parentBills.getT_thisdate());
			int j = i + 1;
			for (; j < len; j++) {
	            BillsInfos childBills = mList.get(j);
				if ((parentBills.getT_date()).equals((childBills.getT_date()))) {
					parentBills.setT_item(parentBills.getT_item() + "\n" + childBills.getT_item());// 字符串拼接
					parentBills.setPriceForShow(parentBills.getPriceForShow() + "\n" + childBills.getT_money());// 字符串拼接
					parentBills.setTotalPrice(parentBills.getTotalPrice() + childBills.getT_money());
				    parentBills.setIds(parentBills.getIds() + ","+String.valueOf(childBills.getId()));//拼接支付使用的物业账单ID
				    parentBills.setDatas(parentBills.getDatas() + "\n" + childBills.getT_lastdate()+"-"+childBills.getT_thisdate());
					mList.remove(j);// 删掉第 j 位置上的数据
					len--;// 集合长度减去1
					j = i;// 保证指针移动到相应位置
				}
			}
		}

		return mList;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		((PPActivity) getActivity()).setLyCenterVisible(View.VISIBLE);
		findBills();
	}

	@OnClick({ R.id.btn_payment_pay })
	private void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_payment_pay:// 支付
			GCApplication.sApp.mWaitPayBills = new LinkedList<>();
			for (BillsInfos bean : mBeans) {
				if(bean.getT_sign() == 0){
					GCApplication.sApp.mWaitPayBills.add(bean);
				}
			}
			if(GCApplication.sApp.mWaitPayBills.size() > 0){
				((PPActivity) getActivity()).startNextActivity(null, ProPayOrderActivity.class);
			}else{
				showToast("当前没有欠缴账单，无需缴费");
			}
			break;
		default:
			break;
		}
	}

	public boolean onBackPressed() {
		return false;
	}
}
