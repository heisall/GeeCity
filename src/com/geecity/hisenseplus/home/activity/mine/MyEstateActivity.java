package com.geecity.hisenseplus.home.activity.mine;

import java.util.LinkedList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.HomeActivity;
import com.geecity.hisenseplus.home.activity.account.CommunitySelectActivity;
import com.geecity.hisenseplus.home.activity.account.HouseBind0Activity;
import com.geecity.hisenseplus.home.adapter.MyEstateListAdpter;
import com.geecity.hisenseplus.home.bean.BaseBean;
import com.geecity.hisenseplus.home.bean.MyEstateBean;
import com.geecity.hisenseplus.home.bean.MyEstateResultBean;
import com.geecity.hisenseplus.home.utils.Const;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.geecity.hisenseplus.home.view.ListViewForScrollView;
import com.google.gson.JsonSyntaxException;
import com.ibill.lib.https.HttpUtil;
import com.ibill.lib.https.HttpUtil.RequestHttpCallBack;
import com.ibill.lib.webutils.RequestHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class MyEstateActivity extends ActionBarActivity {

    public static final String KEY_ADD_BIND = "KEY_ADD_BIND";
    @ViewInject(R.id.btn_home_menu)
    private Button mTopBackBtn;
    @ViewInject(R.id.tv_home_topbar_title)
    private TextView mTopTitle;

    @ViewInject(R.id.textView11)
    private TextView mTvAdd;
    @ViewInject(R.id.lv_my_estate)
    private ListViewForScrollView mLvEstate;
    private MyEstateListAdpter mAdpter;
    private LinkedList<MyEstateBean> mBeans;
    private boolean mIsAddModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_estate);
        ViewUtils.inject(this);
        mIsAddModel = getIntent().getBooleanExtra(KEY_ADD_BIND, false);
        mAdpter = new MyEstateListAdpter(this);
        mLvEstate.setAdapter(mAdpter);
        mLvEstate.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                sendSetDefalut(arg2);
            }
        });
        sendRequest();
    }

    @Override
    protected void sendRequest() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("userid", "" + GCApplication.sApp.getUserInfo().getId());
        HttpUtil httpUtil =
                        new HttpUtil(new RequestHandler(MyEstateActivity.this, "", getResources()
                                        .getString(R.string.loading), 0));
        // 回调函数
        RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> result) {
                super.onSuccess(result);
                try {
                    MyEstateResultBean resultBean =
                                    mGson.fromJson(result.result, MyEstateResultBean.class);
                    if (Const.SUCCESS.equals(resultBean.getResult())) {
                        mBeans = resultBean.getData();
                        updateUI();
                    } else {
                        showToast(resultBean.getErrorCode());
                    }
                } catch (JsonSyntaxException e) {}
            }
        };
        httpUtil.post(WebConstants.METHOD_BUILD_MYROOMS, params, callback);
    }

    protected void sendSetDefalut(int index) {
        final MyEstateBean bean = mBeans.get(index);
        if (bean == null || bean.getIsDefault() == 1) {
            return;
        }
        RequestParams params = new RequestParams();
        params.addBodyParameter("userid", "" + GCApplication.sApp.getUserInfo().getId());
        params.addBodyParameter("JU_RID", bean.getJU_RID());
        LogUtils.d("build/changeDefault : userid=" + GCApplication.sApp.getUserInfo().getId()
                        + "  JU_RID=" + bean.getJU_RID());
        HttpUtil httpUtil =
                        new HttpUtil(new RequestHandler(MyEstateActivity.this, "", getResources()
                                        .getString(R.string.loading), 0));
        RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> result) {
                super.onSuccess(result);
                try {
                    BaseBean resultBean = mGson.fromJson(result.result, BaseBean.class);
                    if (Const.SUCCESS.equals(resultBean.getResult())) {
                        for (MyEstateBean b : mBeans) {
                            if (b.getU_id() == bean.getU_id()) {
                                b.setIsDefault(1);
                            } else {
                                b.setIsDefault(0);
                            }
                        }
                        updateUI();
                        showToast("设置成功，应用将重启后更新数据");
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                Intent i =
                                                getBaseContext().getPackageManager()
                                                                .getLaunchIntentForPackage(
                                                                                getBaseContext().getPackageName());
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                GCApplication.sApp.finishActivities();
                                startActivity(i);
                            }
                        }, 2000);
                    } else {
                        showToast(resultBean.getErrorCode());
                    }
                } catch (JsonSyntaxException e) {}
            }
        };
        httpUtil.post(WebConstants.METHOD_BUILD_CHANGEDEFAULT, params, callback);
    }

    private void updateUI() {
        mAdpter.setArrayList(mBeans);
    }

    @OnClick({R.id.btn_home_menu, R.id.textView11})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_home_menu: // 点击返回按钮
                if (mIsAddModel) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(HomeActivity.KEY_FRAGMENT_INDEX, 4);
                    startNextActivity(bundle, HomeActivity.class);
                } else {
                    onBackPressed();
                }
                break;
            case R.id.textView11:
                nextBindActiv(0);
                break;
        }
    }

    private void nextBindActiv(int index) {
        Bundle b = new Bundle();
        b.putBoolean(CommunitySelectActivity.KEY_TYPE, index == 1);
        b.putBoolean(KEY_ADD_BIND, true);
        startNextActivity(b, HouseBind0Activity.class);
    }

    @Override
    public void configActionBar() {
        mTopTitle.setText(R.string.activity_title_my_estate);
    }

    @Override
    public void requestCallBack(String dataJson, RequestType type) {

    }
}
