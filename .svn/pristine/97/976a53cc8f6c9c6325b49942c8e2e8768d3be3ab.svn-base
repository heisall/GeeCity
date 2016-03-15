package com.geecity.hisenseplus.home.activity.mine;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.account.LoginActivity;
import com.geecity.hisenseplus.home.bean.BaseBean;
import com.geecity.hisenseplus.home.utils.Const;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.google.gson.Gson;
import com.ibill.lib.https.HttpUtil;
import com.ibill.lib.https.HttpUtil.RequestHttpCallBack;
import com.ibill.lib.webutils.RequestHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class ChangePwdActivity extends ActionBarActivity {


    @ViewInject(R.id.btn_home_menu)
    private Button mTopBackBtn;
    @ViewInject(R.id.tv_home_topbar_title)
    private TextView mTopTitle;

    @ViewInject(R.id.btn_reset_showpwd)
    private Button mBtnShowPwd;
    @ViewInject(R.id.btn_reset_pwd_commit)
    private Button mBtnCommit;
    
    @ViewInject(R.id.et_reset_pwd_old)
    private EditText mEtPwdOld;
    @ViewInject(R.id.et_reset_pwd)
    private EditText mEtPwd;

    private boolean isShowPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chang_pwd);
        ViewUtils.inject(this);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        isShowPwd = false;
        mEtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        mEtPwd.postInvalidate();
        mBtnShowPwd.setBackgroundResource(R.drawable.off_btn);
    }

    @Override
    public void configActionBar() {
        mTopBackBtn.setVisibility(View.VISIBLE);
        mTopTitle.setText(R.string.frg_4_title_2_resetped);
    }

    @OnClick({R.id.btn_home_menu, R.id.btn_reset_pwd_commit, R.id.btn_reset_showpwd})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_home_menu: // 点击返回按钮
                onBackPressed();
                break;
            case R.id.btn_reset_pwd_commit: // 提交
            	if(TextUtils.isEmpty(mEtPwdOld.getText().toString().trim())){
            		showToast("请输入原始密码");
            		return;
            	}
            	if(TextUtils.isEmpty(mEtPwd.getText().toString().trim())){
            		showToast("请输入新密码");
            		return;
            	}
                sendRequest();
                break;
            case R.id.btn_reset_showpwd:// 显示密码
                if (!isShowPwd) {// 隐藏密码
                    mEtPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mBtnShowPwd.setBackgroundResource(R.drawable.off_btn);
                    isShowPwd = true;
                } else {// 显示密码
                    mEtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mBtnShowPwd.setBackgroundResource(R.drawable.on_btn);
                    isShowPwd = false;
                }
                mEtPwd.postInvalidate();
                break;
        }
    }

    @Override
	protected void sendRequest() {
		super.sendRequest();
		RequestParams params = new RequestParams();
		params.addBodyParameter("account", GCApplication.sApp.getUserInfo().getAccount());
		params.addBodyParameter("password", mEtPwdOld.getText().toString().trim());
		params.addBodyParameter("newPass", mEtPwd.getText().toString().trim());
		HttpUtil httpUtil = new HttpUtil(new RequestHandler(
				ChangePwdActivity.this, "", getResources().getString(
						R.string.loading), 0));
		// 回调函数
		RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> result) {
				super.onSuccess(result);
				try {
					Gson gson = new Gson();
					BaseBean basebean = gson.fromJson(result.result, BaseBean.class);
					if(Const.SUCCESS.equals(basebean.getResult())){
						showToast("您的密码修改成功，请使用新的密码登录");
						new Handler().postDelayed(new Runnable() {
							
							@Override
							public void run() {
								startNextActivity(null, LoginActivity.class);
							}
						}, 1000);
					} else {
						showToast(basebean.getErrorCode());
					}
				} catch (Exception e) {
					showToast(R.string.errorMsg);
				}

			}
		};
		httpUtil.post(WebConstants.METHOD_CHANGEPASS, params, callback);
	}

	@Override
	public void requestCallBack(String dataJson, RequestType type) {

	}
}
