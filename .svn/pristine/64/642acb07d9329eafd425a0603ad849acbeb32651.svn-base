package com.geecity.hisenseplus.home.fragment;

import java.util.LinkedList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.estate.EstateActivity;
import com.geecity.hisenseplus.home.activity.estate.EstateActivity.ESTATE;
import com.geecity.hisenseplus.home.activity.estate.EstateDetailRentActivity;
import com.geecity.hisenseplus.home.activity.estate.EstateDetailSecondActivity;
import com.geecity.hisenseplus.home.adapter.EstatesAdapter;
import com.geecity.hisenseplus.home.bean.EstateAdaptBean;
import com.geecity.hisenseplus.home.bean.EstateListRentBean;
import com.geecity.hisenseplus.home.bean.EstateListRentResultBean;
import com.geecity.hisenseplus.home.bean.EstateListSecondHandBean;
import com.geecity.hisenseplus.home.bean.EstateListSecondHandResultBean;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.geecity.hisenseplus.home.view.XListView;
import com.geecity.hisenseplus.home.view.XListView.IXListViewListener;
import com.google.gson.Gson;
import com.ibill.lib.https.HttpUtil;
import com.ibill.lib.https.HttpUtil.RequestHttpCallBack;
import com.ibill.lib.tools.T;
import com.ibill.lib.webutils.RequestHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MyRentSaleFragment extends Fragment implements IXListViewListener {
    private EstatesAdapter mAdapte;
	private static final int PAGE_COUNT_MAX = 20;
	private LinkedList<EstateAdaptBean> mList=new LinkedList<EstateAdaptBean>();;
	private int mPageIndex;
	@ViewInject(R.id.xl_my_estate)
	private XListView mXList;
	private ESTATE mEstateType;
	
	public MyRentSaleFragment(ESTATE type){
		super();
		this.mEstateType = type;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_my_rent_sale, container, false);
		ViewUtils.inject(this, view);
		mAdapte = new EstatesAdapter(getActivity());
		mXList.setAdapter(mAdapte);
		mXList.setPullRefreshEnable(true);
		mXList.setPullLoadEnable(false);
		mXList.setXListViewListener(this);
		mXList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String id = mList.get(arg2 - 1).getId() + "";
				Intent intent = new Intent(getActivity(), mEstateType == ESTATE.RENT ?EstateDetailRentActivity.class:EstateDetailSecondActivity.class);
				intent.putExtra(EstateActivity.ESTATE_DETAIL_ID, id);
				getActivity().startActivity(intent);
			}
		});
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		sendRequest(mPageIndex);
	}
	
	protected void sendRequest(final int pageIndex) {
        RequestParams params = new RequestParams();
		params.addBodyParameter("Account", "" + GCApplication.sApp.getUserInfo().getAccount());
        params.addBodyParameter("pageindex", "" + pageIndex);
        params.addBodyParameter("countperpage", "" + PAGE_COUNT_MAX );
        params.addBodyParameter("userid", "" + GCApplication.sApp.getUserInfo().getId());
        HttpUtil httpUtil =
                        new HttpUtil(new RequestHandler(getActivity(), "", getResources()
                                        .getString(R.string.loading), 0));
        // 回调函数
        RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> result) {
                super.onSuccess(result);
                mXList.stopRefresh();
                mXList.stopLoadMore();
                try {
                	String errorInfo = "";
                	Gson mGson = new Gson();
                	switch (mEstateType) {
					case NEW_HOME:{
					}
						break;
					case SECOND_HAND:{
						EstateListSecondHandResultBean resBean = mGson.fromJson(result.result, EstateListSecondHandResultBean.class);
						if("1".equals(resBean.getResult())){
							mPageIndex = pageIndex;
							mAdapte.setType(ESTATE.SECOND_HAND);
						    LinkedList<EstateListSecondHandBean> elBeans = resBean.getData();
							if(elBeans != null && elBeans.size() > 0){
								// 转换为适配器所需数据类型
								LinkedList<EstateAdaptBean> adpteBeans = new LinkedList<EstateAdaptBean>();
								for (EstateListSecondHandBean eBean : elBeans) {
									EstateAdaptBean bean = new EstateAdaptBean();
                                    bean.setId(eBean.getId());
									bean.setLpmc(eBean.getBuildingInfo());
									bean.setTv1(eBean.getHouse_Indoor() + "室" + eBean.getHouse_living() +"厅"+eBean.getHouse_Toilet() + "卫"+ "  " + eBean.getBuildingArea() + "平米");
									bean.setTv2(eBean.getCity() + "  " + eBean.getArea());
									bean.setPrice(String.valueOf(eBean.getRent()));
									bean.setPicUrl(eBean.getPhoto());
									adpteBeans.add(bean);
								}
                                
                                if(mPageIndex == 0){
                                    mList = adpteBeans;
                                    mAdapte.setArrayList(adpteBeans);
                                }else{
                                    mList.addAll(adpteBeans);
                                    mAdapte.addList(adpteBeans);
                                }
                                if(elBeans.size() >= PAGE_COUNT_MAX){
                                    mXList.setPullLoadEnable(true);
                                }else{
                                    mXList.setPullLoadEnable(false);
                                }
							}else{
                                mList.clear();
                                mAdapte.setArrayList(mList);
							}
	                    	return;
						}else{
							errorInfo = resBean.getErrorCode();
						}
					}
						break;
					case RENT:{
						EstateListRentResultBean resBean = mGson.fromJson(result.result, EstateListRentResultBean.class);
						if("1".equals(resBean.getResult())){
							mPageIndex = pageIndex;
							mAdapte.setType(ESTATE.RENT);
							LinkedList<EstateListRentBean> elBeans = resBean.getData();
							if(elBeans != null && elBeans.size() > 0){
								// 转换为适配器所需数据类型
								LinkedList<EstateAdaptBean> adpteBeans = new LinkedList<EstateAdaptBean>();
								for (EstateListRentBean eBean : elBeans) {
									EstateAdaptBean bean = new EstateAdaptBean();
                                    bean.setId(eBean.getId());
									bean.setLpmc(eBean.getBuildingInfo());
									//室、厅、卫+面积+装修情况
									bean.setTv1(eBean.getHouse_Indoor() + "室" + eBean.getHouse_living() +"厅"+eBean.getHouse_Toilet() + "卫"+ "  " + eBean.getBuildingArea() + "㎡");
									//青岛市+区域
									bean.setTv2(eBean.getArea());
									bean.setPricePre("");
									bean.setPrice(String.valueOf(eBean.getRent()));
									bean.setPicUrl(eBean.getPhoto());
									LogUtils.d(bean.toString());
									adpteBeans.add(bean);
								}
                                
                                if(mPageIndex == 0){
                                    mList = adpteBeans;
                                    mAdapte.setArrayList(adpteBeans);
                                }else{
                                    mList.addAll(adpteBeans);
                                    mAdapte.addList(adpteBeans);
                                }
                                if(elBeans.size() >= PAGE_COUNT_MAX){
                                    mXList.setPullLoadEnable(true);
                                }else{
                                    mXList.setPullLoadEnable(false);
                                }
							}else{
                                mList.clear();
                                mAdapte.setArrayList(mList);
							}
	                    	return;
						}else{
							errorInfo = resBean.getErrorCode();
						}
					}
						break;

					default:
						break;
					}
                    T.show(getActivity(), errorInfo, Toast.LENGTH_SHORT);
                } catch (Exception e) {
                	e.printStackTrace();
                	T.show(getActivity(), R.string.errorMsg, Toast.LENGTH_SHORT);
                }
            }
        };
        httpUtil.post(mEstateType == ESTATE.RENT ?WebConstants.METHOD_USER_MY_RENT:WebConstants.METHOD_USER_MY_SALE, params, callback);
    }
	
	@Override
	public void onRefresh() {
		sendRequest(0);
	}
	@Override
	public void onLoadMore() {
		sendRequest(mPageIndex + 1);
	}
	

}
