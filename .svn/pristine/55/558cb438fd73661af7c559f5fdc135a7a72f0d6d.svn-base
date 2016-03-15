package com.geecity.hisenseplus.home.activity.account;

import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.bean.BaseBean;
import com.geecity.hisenseplus.home.utils.CommonTools;
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

public class ResetPwdActivity extends ActionBarActivity {


    @ViewInject(R.id.btn_home_menu)
    private Button mTopBackBtn;
    @ViewInject(R.id.tv_home_topbar_title)
    private TextView mTopTitle;

    @ViewInject(R.id.btn_reset_showpwd)
    private Button mBtnShowPwd;
    @ViewInject(R.id.et_reset_pwd)
    private EditText mEtPwd;
    
    @ViewInject(R.id.btn_reset_pwd_commit)
    private Button mBtnCommit;

    private boolean isShowPwd;

	private String mPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);
        ViewUtils.inject(this);
		mPhone = getIntent().getStringExtra(SetPwdActivity.KEY_PHONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isShowPwd = false;
        mEtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        mEtPwd.postInvalidate();
        mBtnShowPwd.setBackgroundResource(R.drawable.off_btn);
    }

    @Override
    public void configActionBar() {
        mTopBackBtn.setVisibility(View.VISIBLE);
        mTopTitle.setText(R.string.actv_title_pwd_find);
    }

    @OnClick({R.id.btn_home_menu, R.id.btn_reset_pwd_commit, R.id.btn_reset_showpwd})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_home_menu: // 点击返回按钮
                onBackPressed();
                break;
            case R.id.btn_reset_pwd_commit: // 完成
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
		final String pwd = mEtPwd.getText().toString().trim();
        if(!CommonTools.isIllegalPWD(pwd)){
            showToast("请输入6~20位的密码");
			return;
		}
		
		RequestParams params = new RequestParams();
		params.addBodyParameter("account", mPhone);
		params.addBodyParameter("password", pwd);
		HttpUtil httpUtil = new HttpUtil(new RequestHandler(ResetPwdActivity.this, "", getResources().getString(R.string.loading), 0));
		RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> result) {
				super.onSuccess(result);
				try {
					Gson gson = new Gson();
					BaseBean basebean = gson.fromJson(result.result, BaseBean.class);
					if(Const.SUCCESS.equals(basebean.getResult())){
						showToast("您的密码已重置成功，请使用新的密码登录");
						new Handler().postDelayed(new Runnable() {
							
							@Override
							public void run() {
								startNextActivity(null, LoginActivity.class);
							}
						}, 1000);
					} else {
						showToast(basebean.getErrorCode());
						return;
					}

				} catch (Exception e) {
					showToast(R.string.errorMsg);
				}

			}
		};
		httpUtil.post(WebConstants.METHOD_RESET_PWD, params, callback);
	}
    
    @Override
    public void requestCallBack(String dataJson, RequestType type) {
        // TODO Auto-generated method stub

    }
}
