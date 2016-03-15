package com.geecity.hisenseplus.home.activity.live;

import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.adapter.AddressListAdapter;
import com.geecity.hisenseplus.home.adapter.AddressListAdapter.OnCustomClickedListener;
import com.geecity.hisenseplus.home.bean.AddressDefaultCommitBean;
import com.geecity.hisenseplus.home.bean.AddressForAddBean;
import com.geecity.hisenseplus.home.bean.AddressListBean;
import com.geecity.hisenseplus.home.bean.AddressListResultBean;
import com.geecity.hisenseplus.home.bean.BaseBean;
import com.geecity.hisenseplus.home.utils.Const;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.geecity.hisenseplus.home.view.ATMDialog;
import com.geecity.hisenseplus.home.view.ATMDialog.DialogType;
import com.geecity.hisenseplus.home.view.ATMDialog.OnDialogListener;
import com.geecity.hisenseplus.home.view.ListViewForScrollView;
import com.ibill.lib.https.HttpUtil;
import com.ibill.lib.https.HttpUtil.RequestHttpCallBack;
import com.ibill.lib.webutils.RequestHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class AddressActivity extends ActionBarActivity {

    public static final String SELECTEED_ADDRESS = "SELECTEED_ADDRESS";
	public static final String KEY_COMMON_MODEL = "key_common_model";
    @ViewInject(R.id.btn_home_menu)
    private Button mTopBackBtn;
    @ViewInject(R.id.tv_home_topbar_title)
    private TextView mTopTitle;
    @ViewInject(R.id.notice)
    private TextView mTvNotice;

    @ViewInject(R.id.btn_address_add)
    private Button mBtnAdd;

    @ViewInject(R.id.lvsr_address)
    private ListViewForScrollView mLvsrAddress;

    private AddressListAdapter mAdapter;
    private LinkedList<AddressListBean> mAdrsList;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_page);
        ViewUtils.inject(this);
        mAdapter = new AddressListAdapter(this);
        final boolean isNotSelectModel = getIntent().getBooleanExtra(KEY_COMMON_MODEL, false);
        mAdapter.setOnCustomClickedListener(new OnCustomClickedListener() {
          
          @Override
          public void onDele(int position) {
            final AddressListBean bean = mAdrsList.get(position);
            final ATMDialog dialog = new ATMDialog(AddressActivity.this, DialogType.TWO_BUTTON);
            dialog.setDesc("你确定要删除此地址吗？");
            dialog.setOnDialogLister(new OnDialogListener() {
                
                @Override
                public void onConfirm() {
                    dialog.dismiss();
                    sendDelRequest(bean.getAddr_id());
                }

                @Override
                public void onCancle() {
                    dialog.dismiss();
                }
            });
            dialog.show();
          }
          
          @Override
          public void onDefaultSet(int position) {
            final AddressListBean bean = mAdrsList.get(position);
            if(bean.isIs_default()){
              return;
            }
            sendDefaultSetRequest(position, bean);
          }

          @Override
          public void onSelected(int position) {
            if(isNotSelectModel) return;
            AddressListBean bean = mAdrsList.get(position);
            Intent intent = new Intent();
            intent.putExtra(AddressActivity.SELECTEED_ADDRESS, bean);
            setResult(Activity.RESULT_OK, intent);
            finish();
          }
        });
        mLvsrAddress.setAdapter(mAdapter);
        mLvsrAddress.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
              LogUtils.d("onItemClick : " + arg2);
                if(isNotSelectModel) return;
                AddressListBean bean = mAdrsList.get(arg2);
                Intent intent = new Intent();
                intent.putExtra(AddressActivity.SELECTEED_ADDRESS, bean);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
        sendRequest(0);
    }
	
	protected void sendDefaultSetRequest(int position, AddressListBean bean) {
	  AddressDefaultCommitBean ab = new AddressDefaultCommitBean();
	  ab.setAccount(GCApplication.sApp.getUserInfo().getAccount());
	  ab.setAddress(bean.getAddress());
	  ab.setAddrId(bean.getAddr_id()+"");
	  ab.setConsignee(bean.getConsignee());
	  ab.setIsDefault(bean.isIs_default()?"0":"1");
	  ab.setPhone(bean.getPhone_tel());
	  ab.setRegionId(bean.getRegion_id()+"");
	  ab.setRegionName(bean.getRegion_name());
      RequestParams params = new RequestParams();
      params.addBodyParameter("address", ""+mGson.toJson(ab));
      LogUtils.d(mGson.toJson(ab));
      HttpUtil httpUtil = new HttpUtil(new RequestHandler(AddressActivity.this, "", getResources().getString(R.string.loading), 0));
      // 回调函数
      RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
          @Override
          public void onSuccess(ResponseInfo<String> result) {
              super.onSuccess(result);
              try {
                  BaseBean resb = mGson.fromJson(result.result, BaseBean.class);
                  if (Const.SUCCESS.equals(resb.getResult())) {
                      showToast("修改成功");
                      sendRequest(0);
                  } else {
                      showToast(resb.getErrorCode());
                  }
              } catch (Exception e) {
                  showToast(R.string.errorMsg);
              }
          }
      };
      httpUtil.put(WebConstants.METHOD_MANEGER_ADDRESS, params, callback);
    }

  private void sendDelRequest(int addr_id) {
        RequestParams params = new RequestParams();
		params.addBodyParameter("addr_id", ""+addr_id);
        HttpUtil httpUtil = new HttpUtil(new RequestHandler(AddressActivity.this, "", getResources().getString(R.string.loading), 0));
        // 回调函数
        RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> result) {
                super.onSuccess(result);
                try {
                	BaseBean resb = mGson.fromJson(result.result, BaseBean.class);
                    if (Const.SUCCESS.equals(resb.getResult())) {
                    	showToast("地址删除成功");
                        sendRequest(0);
                    } else {
                        showToast(resb.getErrorCode());
                    }
                } catch (Exception e) {
                    showToast(R.string.errorMsg);
                }
            }
        };
        httpUtil.put(WebConstants.METHOD_ORDER_DEL_ADDRESS, params, callback);
    
    
	}

    protected void sendRequest(int pageIndex) {
        RequestParams params = new RequestParams();
		params.addBodyParameter("account", GCApplication.sApp.getUserInfo().getAccount());
        HttpUtil httpUtil = new HttpUtil(new RequestHandler(AddressActivity.this, "", getResources().getString(R.string.loading), 0));
        // 回调函数
        RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> result) {
                super.onSuccess(result);
                try {
                	AddressListResultBean resb = mGson.fromJson(result.result, AddressListResultBean.class);
                    if (Const.SUCCESS.equals(resb.getResult())) {
                    	mAdrsList = resb.getData();
                    	mTvNotice.setVisibility(View.GONE);
                    	mAdapter.setArrayList(mAdrsList);
                    } else {
                        showToast(resb.getErrorCode());
                    }
                } catch (Exception e) {
                    showToast(R.string.errorMsg);
                }
            }
        };
        httpUtil.post(WebConstants.METHOD_ORDER_ADDRESSLIST, params, callback);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void configActionBar() {
        mTopBackBtn.setVisibility(View.VISIBLE);
        mTopTitle.setText(R.string.activity_title_address);
    }

    @OnClick({R.id.btn_home_menu, R.id.btn_address_add})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_home_menu: // 点击返回按钮
                onBackPressed();
                break;
            case R.id.btn_address_add:// 新增收货地址
//            	新增收货地址
                startNextActivityForResult(null, AddressAddActivity.class, REQUEST_CODE);
                break;
        }
    }
    
    private static final int REQUEST_CODE = 1;
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode != REQUEST_CODE){
            return;
        }
        switch (resultCode) {
        case Activity.RESULT_CANCELED:
            
            break;
        case Activity.RESULT_OK:
            String phone = data.getStringExtra(AddressAddActivity.ADDRESS_PHONE);
            String name = data.getStringExtra(AddressAddActivity.ADDRESS_NAME);
//            String city = data.getStringExtra(AddressAddActivity.ADDRESS_CITY);
            String detail = data.getStringExtra(AddressAddActivity.ADDRESS_DETAIL);
            boolean isdefalut = data.getBooleanExtra(AddressAddActivity.ADDRESS_DEFAULT, false);
            
            AddressForAddBean bean = new AddressForAddBean();
//            bean.setAddress(city + detail);
            bean.setAddress(detail);
            bean.setPhone(phone);
            bean.setConsignee(name);
            bean.setIsDefault(isdefalut?"1":"0");
            bean.setAccount(GCApplication.sApp.getUserInfo().getAccount());
            sendAddressAdd(bean);
            break;
        default:
            break;
        }
    }
    
    private void sendAddressAdd(AddressForAddBean bean) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("address", mGson.toJson(bean));
        HttpUtil httpUtil = new HttpUtil(new RequestHandler(AddressActivity.this, "", getResources().getString(R.string.loading), 0));
        // 回调函数
        RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> result) {
                super.onSuccess(result);
                try {
                    BaseBean resultBean = mGson.fromJson(result.result, BaseBean.class);
                    if (Const.SUCCESS.equals(resultBean.getResult())) {
                        showToast("添加成功");
                        sendRequest(0);
                    } else {
                        showToast(resultBean.getErrorCode());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast(R.string.errorMsg);
                }
            }
        };

        httpUtil.put(WebConstants.METHOD_ORDER_ADD_ADDRESS, params, callback);
    }


    @Override
    public void requestCallBack(String dataJson, RequestType type) {

    }
}
