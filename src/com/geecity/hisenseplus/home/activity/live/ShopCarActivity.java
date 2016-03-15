package com.geecity.hisenseplus.home.activity.live;

import java.util.ArrayList;
import java.util.LinkedList;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.adapter.ShopCarListAdapter;
import com.geecity.hisenseplus.home.adapter.ShopCarListAdapter.ShopCarChangedListener;
import com.geecity.hisenseplus.home.bean.BaseBean;
import com.geecity.hisenseplus.home.bean.ShopCarForAddBean;
import com.geecity.hisenseplus.home.bean.ShopCarListBean;
import com.geecity.hisenseplus.home.bean.ShopCarListResultBean;
import com.geecity.hisenseplus.home.utils.Const;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.geecity.hisenseplus.home.view.XListView;
import com.geecity.hisenseplus.home.view.XListView.IXListViewListener;
import com.google.gson.Gson;
import com.ibill.lib.https.HttpUtil;
import com.ibill.lib.https.HttpUtil.RequestHttpCallBack;
import com.ibill.lib.webutils.RequestHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class ShopCarActivity extends ActionBarActivity implements IXListViewListener{

    @ViewInject(R.id.btn_home_menu)
    private Button mTopBackBtn;
    @ViewInject(R.id.btn_home_message)
    private Button mTopEdit;
    @ViewInject(R.id.tv_home_topbar_title)
    private TextView mTopTitle;

    @ViewInject(R.id.rtly_shopcar_edit)
    private RelativeLayout mRtlyTopEdit;
    @ViewInject(R.id.cb_shopcar_select)
    private CheckBox mCbSelectAll;
    @ViewInject(R.id.btn_shopcar_detele)
    private Button mBtnDelet;
    @ViewInject(R.id.rtly_shopcar_bottom)
    private RelativeLayout mRtlyBottom;

    @ViewInject(R.id.lv_shopcar_list)
    private XListView mLvShopCar;
    @ViewInject(R.id.tv_shopcar_total)
    private TextView mTvSelectCount;
    @ViewInject(R.id.tv_shopcar_total_price)
    private TextView mTvSelectCountPrice;

    @ViewInject(R.id.btn_shopcar_pay)
    private Button mBtnPay;

    private ShopCarListAdapter mAdapter;
    private LinkedList<ShopCarListBean> mShopCars;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_car);
        ViewUtils.inject(this);
        mAdapter = new ShopCarListAdapter(this);
        mAdapter.setOnShopCarSelectedListener(new ShopCarChangedListener() {

			@Override
			public void onSelected(int position, boolean isSelected) {
			    //有选择操作时，表示购物车不为空，激活删除按钮
                mBtnDelet.setClickable(true);
				mShopCars.get(position).setSelected(isSelected?1:0);
				updateOrderInfo();
			}

			@Override
			public void onCountChanged(int position, int newValue, int addFlag) {
				mShopCars.get(position).setQuantity(newValue);
				sendAddShopCarRequest(mShopCars.get(position), addFlag);
				updateOrderInfo();
			}
		});
        mLvShopCar.setXListViewListener(this);
        mLvShopCar.setPullLoadEnable(false);
        mLvShopCar.setAdapter(mAdapter);
        mLvShopCar.setItemsCanFocus(true);
        mAdapter.notifyDataSetChanged();
        sendRequest(0);
    }

    protected void updateOrderInfo() {
        //编辑模式下，不刷新结算数量
        if(isEditModel()) return;
    	int count = 0;
    	double priceCount = 0;
		if(mShopCars != null){
			for (ShopCarListBean spcs : mShopCars) {
				if(spcs.isSelected() == 1){
					count += spcs.getQuantity();
					priceCount += spcs.getQuantity() * spcs.getPrice();
				}
			}
		}
		mTvSelectCount.setText(String.valueOf(count));
		mTvSelectCountPrice.setText("￥"+String.valueOf(priceCount));
	}

    protected void sendRequest(int pageIndex) {
    	super.sendRequest();
        RequestParams params = new RequestParams();
		params.addBodyParameter("account", GCApplication.sApp.getUserInfo().getAccount());
        HttpUtil httpUtil = new HttpUtil(new RequestHandler(ShopCarActivity.this, "", getResources().getString(R.string.loading), 0));
        // 回调函数
        RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> result) {
                super.onSuccess(result);
            	mLvShopCar.stopRefresh();
            	mLvShopCar.stopLoadMore();
                try {
                    ShopCarListResultBean resb = mGson.fromJson(result.result, ShopCarListResultBean.class);
                    if (Const.SUCCESS.equals(resb.getResult())) {
                    	mShopCars = resb.getData();
                    	mAdapter.setList(mShopCars);
                        updateOrderInfo();
                    } else {
                        showToast(resb.getErrorCode());
                    }
                } catch (Exception e) {
                    showToast(R.string.errorMsg);
                }
            }
            
            @Override
            public void onFailure(HttpException arg0, String arg1) {
            	super.onFailure(arg0, arg1);
            	mLvShopCar.stopRefresh();
            	mLvShopCar.stopLoadMore();
            }
        };
        httpUtil.post(WebConstants.METHOD_ORDER_CAETLIST, params, callback);
    
    }

    @Override
    protected void onResume() {
        super.onResume();
        sendRequest(0);
    }

    @Override
    public void configActionBar() {
        mTopBackBtn.setVisibility(View.VISIBLE);
        mTopTitle.setText(R.string.activity_title_shopcar);
        mTopEdit.setText(R.string.btn_text_edit);
        LayoutParams params = mTopEdit.getLayoutParams();
        params.width = 120;
        params.height = LayoutParams.MATCH_PARENT;
        mTopEdit.setLayoutParams(params);
        mTopEdit.setVisibility(View.VISIBLE);
        mTopEdit.setTextSize(14);
        mTopEdit.setTextColor(getResources().getColor(R.drawable.text_white_gray));
        mTopEdit.setBackgroundResource(android.R.color.transparent);
    }

    @OnClick({R.id.btn_home_menu, R.id.btn_home_message, R.id.cb_shopcar_select,
                    R.id.btn_shopcar_detele, R.id.btn_shopcar_pay})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_home_menu: // 点击返回按钮
                onBackPressed();
                break;
            case R.id.btn_home_message:// 编辑
                if(mShopCars != null){
                    for (ShopCarListBean spcs : mShopCars) {
                        spcs.setSelected(0);
                    }
                }
                mAdapter.setList(mShopCars);
                if(!isEditModel()){
                    mCbSelectAll.setChecked(false);
                    mRtlyTopEdit.setVisibility(View.VISIBLE);
                    mTopEdit.setText(R.string.btn_text_complete);
                    mRtlyBottom.setVisibility(View.GONE);
                }else{
                    mRtlyTopEdit.setVisibility(View.GONE);
                    mTopEdit.setText(R.string.btn_text_edit);
                    mRtlyBottom.setVisibility(View.VISIBLE);
                    updateOrderInfo();
                }
                break;
            case R.id.cb_shopcar_select:// 全选
                if(mShopCars != null && mShopCars.size() > 0){
                    for (ShopCarListBean spcs : mShopCars) {
                        spcs.setSelected(mCbSelectAll.isChecked()?1:0);
                    }
                    mAdapter.setList(mShopCars);
                }else{
                    //没有数据时，全选无效
                    mCbSelectAll.setChecked(false);
                }
                break;
            case R.id.btn_shopcar_detele:// 删除
                if (mShopCars != null) {
                    ArrayList<Integer> idIndex = new ArrayList<>();
                    for (int i = 0; i < mShopCars.size(); i++) {
                        ShopCarListBean spcs = mShopCars.get(i);
                        if (spcs.isSelected() == 1) {
                            idIndex.add(spcs.getRec_id());
                        }
                    }
                    LogUtils.d(idIndex.toString() + "  " + mShopCars.size());
                    if (idIndex.size() == 0) {
                        showToast("请选择要删除的商品");
                        return;
                    }
                    
                    sendDeleteRequest(idIndex);
                }
                break;
            case R.id.btn_shopcar_pay:// 支付
            	if(mShopCars == null || mShopCars.size() == 0){
            		showToast("客官又跟小二说笑，购物车可空着呢");
            		return;
            	}
            	ArrayList<ShopCarListBean> selectBeans = new ArrayList<>();
            	for (ShopCarListBean spc : mShopCars) {
					if(spc.isSelected() == 1){
						selectBeans.add(spc);
					}
				}
            	
            	for (int i = 0; i < selectBeans.size() - 1; i++) {
					for (int j = i+1; j < selectBeans.size(); j++) {
						if(selectBeans.get(i).getStore_id() != selectBeans.get(j).getStore_id()){
							showToast("小二一次暂且处理一个商户，您得分批结算");
							return;
						}
					}
				}
            	
            	if(selectBeans.size() == 0){
            		showToast("客官，没有商品就去结账，小二做不到啊");
            		return;
            	}
            	Bundle b = new Bundle();
            	b.putParcelableArrayList(OrderCommitActivity.SHOPCAR_LIST, selectBeans);
            	startNextActivity(b, OrderCommitActivity.class);
                break;
            default:
                break;
        }
    }

    private void sendDeleteRequest(final ArrayList<Integer> idIndexs) {
    	String ids = "";
    	for (int i = 0; i < idIndexs.size(); i++) {
			ids += (idIndexs.get(i) + "");
			if(i < idIndexs.size() - 1){
				ids += ",";
			}
		}
    	LogUtils.d(ids);
        RequestParams params = new RequestParams();
		params.addBodyParameter("account", GCApplication.sApp.getUserInfo().getAccount());
		params.addBodyParameter("recIds", ids);
        HttpUtil httpUtil =
                        new HttpUtil(new RequestHandler(ShopCarActivity.this, "", getResources().getString(R.string.loading), 0));
        // 回调函数
        RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> result) {
                super.onSuccess(result);
                try {
                    BaseBean resultBean = mGson.fromJson(result.result, BaseBean.class);
                    if (Const.SUCCESS.equals(resultBean.getResult())) {

                        for (int id : idIndexs) {
                            for (ShopCarListBean spcs : mShopCars) {
                                if (spcs.getRec_id() == id) {
                                    LogUtils.d("remove :" + id);
                                    mShopCars.remove(spcs);
                                    break;
                                }
                            }
                        }
                        if(mShopCars.size() == 0) {
                            mCbSelectAll.setChecked(false);
                            mBtnDelet.setClickable(false);
                        }
                        mAdapter.setList(mShopCars);
                    } else {
                        showToast(resultBean.getErrorCode());
                    }
                } catch (Exception e) {
                    showToast(R.string.errorMsg);
                }
            }
        };
        httpUtil.put(WebConstants.METHOD_ORDER_BEACHDELETE, params, callback);
    }

	/**
     * true为编辑模式，false为正常模式
     **/
    private boolean isEditModel() {
        return getResources().getString(R.string.btn_text_complete).equals(mTopEdit.getText().toString());
    }

    @Override
    public void requestCallBack(String dataJson, RequestType type) {

    }

    private int mPageIndex;
    
    @Override
    public void onRefresh() {
        sendRequest(0);
    }

    @Override
    public void onLoadMore() {
        sendRequest(mPageIndex + 1);
    }

    private void sendAddShopCarRequest(ShopCarListBean shopCarListBean, int addFlag) {
      RequestParams params = new RequestParams();
      ShopCarForAddBean scBean = getShopCarForAdd(shopCarListBean, addFlag);
      LogUtils.d(new Gson().toJson(scBean));
      params.addBodyParameter("cart", new Gson().toJson(scBean));
      HttpUtil httpUtil =
          new HttpUtil(new RequestHandler(ShopCarActivity.this, "", getResources().getString(
              R.string.loading), 0));
      // 回调函数
      RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
        @Override
        public void onSuccess(ResponseInfo<String> result) {
          super.onSuccess(result);
          try {
            BaseBean resultBean = mGson.fromJson(result.result, BaseBean.class);
            if (Const.SUCCESS.equals(resultBean.getResult())) {
            } else {
              showToast(resultBean.getErrorCode());
            }
          } catch (Exception e) {
            showToast(R.string.errorMsg);
          }
        }
      };
      httpUtil.put(WebConstants.METHOD_ORDER_ADDCART, params, callback);
    }

    private ShopCarForAddBean getShopCarForAdd(ShopCarListBean shopCarListBean, int addFlag) {
      ShopCarForAddBean scb = new ShopCarForAddBean();
      scb.setAccount(GCApplication.sApp.getUserInfo().getAccount());
      scb.setCounts("1");
      scb.setFlg(String.valueOf(addFlag));
      scb.setGoodsId(shopCarListBean.getGoods_id() + "");
      scb.setStoreId(shopCarListBean.getStore_id() + "");
      scb.setUnitPrice(String.valueOf(shopCarListBean.getPrice()));
      return scb;
    }
}
