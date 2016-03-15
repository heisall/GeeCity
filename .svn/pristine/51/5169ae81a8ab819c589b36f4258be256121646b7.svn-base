package com.geecity.hisenseplus.home.activity.live;

import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.adapter.MyOrderAdapter;
import com.geecity.hisenseplus.home.adapter.MyOrderAdapter.OrderClickedListener;
import com.geecity.hisenseplus.home.adapter.MyOrderCompleteListAdapter;
import com.geecity.hisenseplus.home.bean.BaseBean;
import com.geecity.hisenseplus.home.bean.MyOrderCompListParentBean;
import com.geecity.hisenseplus.home.bean.MyOrderCompResultBean;
import com.geecity.hisenseplus.home.bean.MyOrderCountBean;
import com.geecity.hisenseplus.home.bean.MyOrderListParentBean;
import com.geecity.hisenseplus.home.bean.MyOrderResultBean;
import com.geecity.hisenseplus.home.utils.Const;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.geecity.hisenseplus.home.view.XListView;
import com.geecity.hisenseplus.home.view.XListView.IXListViewListener;
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
import com.lingnet.paylib.weixin.WXPayTools;
import com.lingnet.paylib.weixin.WXPayTools.OnPayListener;

public class MyOrderActivity extends ActionBarActivity implements IXListViewListener {

  @ViewInject(R.id.btn_home_menu)
  private Button mTopBackBtn;
  @ViewInject(R.id.tv_home_topbar_title)
  private TextView mTopTitle;

  @ViewInject(R.id.textview01)
  private TextView mTvTitel01;
  @ViewInject(R.id.tv_order_count_01)
  private TextView mTvCount01;
  @ViewInject(R.id.tv_order_count_line_01)
  private TextView mTvLine01;
  @ViewInject(R.id.textview02)
  private TextView mTvTitel02;
  @ViewInject(R.id.tv_order_count_02)
  private TextView mTvCount02;
  @ViewInject(R.id.tv_order_count_line_02)
  private TextView mTvLine02;
  @ViewInject(R.id.textview03)
  private TextView mTvTitel03;
  @ViewInject(R.id.tv_order_count_03)
  private TextView mTvCount03;
  @ViewInject(R.id.tv_order_count_line_03)
  private TextView mTvLine03;
  @ViewInject(R.id.textview04)
  private TextView mTvTitel04;
  @ViewInject(R.id.tv_order_count_04)
  private TextView mTvCount04;
  @ViewInject(R.id.tv_order_count_line_04)
  private TextView mTvLine04;

  @ViewInject(R.id.xlv_order)
  private XListView mXlvOrders;

  private MyOrderAdapter mAdapter;
  private LinkedList<MyOrderListParentBean> mList;

  private MyOrderCompleteListAdapter mAdapterComplete;
  private LinkedList<MyOrderCompListParentBean> mListComp;

  /**
   * 当前订单类型 0-全部， 1-待付款， 2-待收货，3-待评价
   */
  private OrderType mCurrentType = OrderType.UNPAY;
  protected int PAGE_MAX = 20;

  public enum OrderType {
    ALL(0, "全部订单", ""), UNPAY(11, "待付款", "立即付款"), UNCONFIRM(30, "待收货", "确认收货"), UNAPPR(40,
        "待评价", "立即评价");
    private int tag;
    private String desc;
    private String btnDesc;

    private OrderType(int tag, String desc, String btnDesc) {
      this.tag = tag;
      this.desc = desc;
      this.btnDesc = btnDesc;
    }

    public int getTag() {
      return this.tag;
    }

    public String getDesc() {
      return this.desc;
    }

    public String getBtnDesc() {
      return this.btnDesc;
    }

    public static OrderType getType(int tag) {
      String tg = String.valueOf(tag);
      if ("11".equals(tg)) {
        return OrderType.UNPAY;
      } else if ("30".equals(tg)) {
        return OrderType.UNCONFIRM;
      } else if ("40".equals(tg)) {
        return OrderType.UNAPPR;
      } else {
        return OrderType.ALL;
      }
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_order);
    ViewUtils.inject(this);
    mAdapterComplete = new MyOrderCompleteListAdapter(this);
    mXlvOrders.setXListViewListener(this);
    mAdapter = new MyOrderAdapter(this);
    mAdapter.setOrderClickedListener(new OrderClickedListener() {

      @Override
      public void OnRight(int status, int orderId) {
        // TODO 调用支付，支付成功后将结果告知服务端，修改订单状态为已付款
        if(mCurrentType == OrderType.UNPAY){
          sendPrePayRequest(orderId);
        }else if(mCurrentType == OrderType.UNCONFIRM){
          //确认收货
          changeOrderStatus(status, orderId);
        }
      }

      @Override
      public void OnLeft(int position, int orderId) {
        sendOrderDelRequest(position - 1, orderId);
      }
    });
    mXlvOrders.setAdapter(mAdapter);
  }

  /** 发送支付请求 **/
  protected void sendPrePayRequest(int orderId) {
      // 获取微信支付工具
      WXPayTools tools = new WXPayTools(MyOrderActivity.this);
      tools.sendPrePayRequest(String.valueOf(orderId));
      tools.setOnPayStartListener(new OnPayListener() {
        
        @Override
        public void onStartPay() {
            // TODO Auto-generated method stub
            
        }
        
        @Override
        public void onCompletPay(boolean result) {
            // TODO Auto-generated method stub
            
        }
    });
  }

  protected void changeOrderStatus(int oldStatus, int orderId) {
    RequestParams params = new RequestParams();
    params.addBodyParameter("orderId", orderId + "");
    OrderType type = OrderType.getType(oldStatus);
    int status = 0;
    switch (type) {
      case UNPAY:
        status = OrderType.UNCONFIRM.getTag();
        break;
      case UNCONFIRM:
        status = OrderType.UNAPPR.getTag();
        break;
      default:
        break;
    }
    params.addBodyParameter("status", ""+status);
    LogUtils.d("changeOrderStatus : orderId = " + orderId + ",  oldStatus = " + oldStatus + ",  status = " + status);
    HttpUtil httpUtil =
        new HttpUtil(new RequestHandler(MyOrderActivity.this, "", getResources().getString(
            R.string.loading), 0));
    // 回调函数
    RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
      @Override
      public void onSuccess(ResponseInfo<String> result) {
        super.onSuccess(result);
        mXlvOrders.stopRefresh();
        mXlvOrders.stopLoadMore();
        try {
          BaseBean resb = mGson.fromJson(result.result, BaseBean.class);
          if (Const.SUCCESS.equals(resb.getResult())) {
            onRefresh();
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
        mXlvOrders.stopRefresh();
        mXlvOrders.stopLoadMore();
      }
    };
    httpUtil.put(WebConstants.METHOD_ORDER_CHANGE_STATUS, params, callback);
  }

  protected void sendOrderDelRequest(final int position, int orderId) {
    RequestParams params = new RequestParams();
    params.addBodyParameter("orderId", orderId + "");
    LogUtils.d("delOrder : orderId = " + orderId);
    HttpUtil httpUtil =
        new HttpUtil(new RequestHandler(MyOrderActivity.this, "", getResources().getString(
            R.string.loading), 0));
    // 回调函数
    RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
      @Override
      public void onSuccess(ResponseInfo<String> result) {
        super.onSuccess(result);
        mXlvOrders.stopRefresh();
        mXlvOrders.stopLoadMore();
        try {
          BaseBean resb = mGson.fromJson(result.result, BaseBean.class);
          if (Const.SUCCESS.equals(resb.getResult())) {
            onRefresh();
            // if(position >= 0 && mList != null && position < mList.size()){
            // mAdapter.list.remove(position);
            // mAdapter.notifyDataSetChanged();
            // }
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
        mXlvOrders.stopRefresh();
        mXlvOrders.stopLoadMore();
      }
    };
    httpUtil.put(WebConstants.METHOD_ORDER_DEL_DETAILS, params, callback);
  }

  protected void sendRequest(final int pageIndex) {
    RequestParams params = new RequestParams();
    params.addBodyParameter("account", GCApplication.sApp.getUserInfo().getAccount());
    params.addBodyParameter("pageindex", pageIndex + "");
    params.addBodyParameter("countperpage", PAGE_MAX + "");
    params.addBodyParameter("status", ""+mCurrentType.getTag());
    LogUtils.d("account " + GCApplication.sApp.getUserInfo().getAccount() + ", pageindex = "
        + pageIndex + ", countperpage = " + PAGE_MAX + ", status = " + mCurrentType.getTag());
    HttpUtil httpUtil =
        new HttpUtil(new RequestHandler(MyOrderActivity.this, "", getResources().getString(
            R.string.loading), 0));
    // 回调函数
    RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
      @Override
      public void onSuccess(ResponseInfo<String> result) {
        super.onSuccess(result);
        mXlvOrders.stopRefresh();
        mXlvOrders.stopLoadMore();
        try {
          if (mCurrentType == OrderType.UNAPPR) {
            MyOrderCompResultBean resb = mGson.fromJson(result.result, MyOrderCompResultBean.class);
            if (Const.SUCCESS.equals(resb.getResult())) {
              if (pageIndex > 0) {
                mListComp.addAll(resb.getOrders());
              } else {
                mListComp = resb.getOrders();
              }
              mPageIndex = pageIndex;

              if (resb.getOrders().size() >= PAGE_MAX) {
                mXlvOrders.setPullLoadEnable(true);
              } else {
                mXlvOrders.setPullLoadEnable(false);
              }
              mXlvOrders.setAdapter(mAdapterComplete);
              mAdapterComplete.setList(mListComp);
              updateOrderCountUI(resb.getOrderCount());
            } else {
              showToast(resb.getErrorCode());
            }
          } else {
            MyOrderResultBean resb = mGson.fromJson(result.result, MyOrderResultBean.class);
            if (Const.SUCCESS.equals(resb.getResult())) {
              if (pageIndex > 0) {
                mList.addAll(resb.getOrders());
              } else {
                mList = resb.getOrders();
              }
              mPageIndex = pageIndex;

              if (resb.getOrders().size() >= PAGE_MAX) {
                mXlvOrders.setPullLoadEnable(true);
              } else {
                mXlvOrders.setPullLoadEnable(false);
              }
              mXlvOrders.setAdapter(mAdapter);
              mAdapter.setLinkedList(mList);
              updateOrderCountUI(resb.getOrderCount());
            } else {
              showToast(resb.getErrorCode());
            }
          }
        } catch (Exception e) {
          e.printStackTrace();
          showToast(R.string.errorMsg);
        }
      }

      @Override
      public void onFailure(HttpException arg0, String arg1) {
        super.onFailure(arg0, arg1);
        mXlvOrders.stopRefresh();
        mXlvOrders.stopLoadMore();
      }
    };
    httpUtil.post(WebConstants.METHOD_ORDER_MYORDERS, params, callback);
  }

  protected void updateOrderCountUI(MyOrderCountBean cb) {
    mTvCount01.setVisibility(View.INVISIBLE);
    mTvCount02.setVisibility(View.INVISIBLE);
    mTvCount03.setVisibility(View.INVISIBLE);
    mTvCount04.setVisibility(View.INVISIBLE);
    if (cb == null) return;
    if (cb.getAll_pay_num() > 0) {
      mTvCount01.setText("" + cb.getAll_pay_num());
      mTvCount01.setVisibility(View.VISIBLE);
    }
    if (cb.getNo_pay_num() > 0) {
      mTvCount02.setText("" + cb.getNo_pay_num());
      mTvCount02.setVisibility(View.VISIBLE);
    }
    if (cb.getNo_receive_num() > 0) {
      mTvCount03.setText("" + cb.getNo_receive_num());
      mTvCount03.setVisibility(View.VISIBLE);
    }
    if (cb.getNo_evaluation_num() > 0) {
      mTvCount04.setText("" + cb.getNo_evaluation_num());
      mTvCount04.setVisibility(View.VISIBLE);
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    sendRequest(0);
  }

  @Override
  public void configActionBar() {
    mTopBackBtn.setVisibility(View.VISIBLE);
    mTopTitle.setText(R.string.frg_4_title_order);
  }

  @OnClick({R.id.btn_home_menu, R.id.textview01, R.id.textview02, R.id.textview03, R.id.textview04})
  public void OnClick(View v) {
    switch (v.getId()) {
      case R.id.btn_home_menu: // 点击返回按钮
        onBackPressed();
        break;
      case R.id.textview01:
        if (mCurrentType != OrderType.ALL) {
          mCurrentType = OrderType.ALL;
          updateTitleUi();
          sendRequest(0);
        }
        break;
      case R.id.textview02:
        if (mCurrentType != OrderType.UNPAY) {
          mCurrentType = OrderType.UNPAY;
          updateTitleUi();
          sendRequest(0);
        }
        break;
      case R.id.textview03:
        if (mCurrentType != OrderType.UNCONFIRM) {
          mCurrentType = OrderType.UNCONFIRM;
          updateTitleUi();
          sendRequest(0);
        }
        break;
      case R.id.textview04:
        if (mCurrentType != OrderType.UNAPPR) {
          mCurrentType = OrderType.UNAPPR;
          updateTitleUi();
          sendRequest(0);
        }
        break;
      default:
        break;
    }
  }

  private void updateTitleUi() {
    mTvTitel01.setTextColor(mCurrentType == OrderType.ALL ? getResources().getColor(
        R.color.txt_hisense_green) : getResources().getColor(R.color.txt_black_light2));
    mTvLine01.setVisibility(mCurrentType == OrderType.ALL ? View.VISIBLE : View.INVISIBLE);

    mTvTitel02.setTextColor(mCurrentType == OrderType.UNPAY ? getResources().getColor(
        R.color.txt_hisense_green) : getResources().getColor(R.color.txt_black_light2));
    mTvLine02.setVisibility(mCurrentType == OrderType.UNPAY ? View.VISIBLE : View.INVISIBLE);

    mTvTitel03.setTextColor(mCurrentType == OrderType.UNCONFIRM ? getResources().getColor(
        R.color.txt_hisense_green) : getResources().getColor(R.color.txt_black_light2));
    mTvLine03.setVisibility(mCurrentType == OrderType.UNCONFIRM ? View.VISIBLE : View.INVISIBLE);

    mTvTitel04.setTextColor(mCurrentType == OrderType.UNAPPR ? getResources().getColor(
        R.color.txt_hisense_green) : getResources().getColor(R.color.txt_black_light2));
    mTvLine04.setVisibility(mCurrentType == OrderType.UNAPPR ? View.VISIBLE : View.INVISIBLE);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    // if(requestCode != REQUEST_CODE){
    // return;
    // }
    switch (resultCode) {
      case Activity.RESULT_CANCELED:

        break;
      case Activity.RESULT_OK:
        break;
      default:
        break;
    }
  }

  @Override
  public void requestCallBack(String dataJson, RequestType type) {

  }

  private int mPageIndex = 0;

  @Override
  public void onRefresh() {
    sendRequest(0);
  }

  @Override
  public void onLoadMore() {
    sendRequest(mPageIndex + 1);
  }
}
