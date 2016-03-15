package com.geecity.hisenseplus.home.activity.account;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.HomeActivity;
import com.geecity.hisenseplus.home.bean.LoginResultBean;
import com.geecity.hisenseplus.home.bean.UserBean;
import com.geecity.hisenseplus.home.utils.CommonTools;
import com.geecity.hisenseplus.home.utils.Const;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.google.gson.Gson;
import com.ibill.lib.activity.BaseActivity;
import com.ibill.lib.https.HttpUtil;
import com.ibill.lib.https.HttpUtil.RequestHttpCallBack;
import com.ibill.lib.webutils.RequestHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class LoginActivity extends BaseActivity {

	@ViewInject(R.id.et_login_account)
	private EditText mEtAccount;
	@ViewInject(R.id.et_login_pwd)
	private EditText mEtPwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		ViewUtils.inject(this);
		((Button) findViewById(R.id.btn_login))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						 //登录
						if(verifyLoginInfo()){
							sendRequest();
						}
					}
				});

		((Button) findViewById(R.id.btn_login_reg))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// 注册
						startNextActivity(null, VerfiyPhoneActivity.class);
					}
				});

		((Button) findViewById(R.id.btn_login_fgtpwd))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// 忘记密码
						startNextActivity(null,
								ResetPwdVerfiyPhoneActivity.class);
					}
				});
	}

	protected void sendRequest() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("account", mEtAccount.getText().toString());
		params.addBodyParameter("password", mEtPwd.getText().toString());
		HttpUtil httpUtil = new HttpUtil(new RequestHandler(LoginActivity.this,
				"", getResources().getString(R.string.loading), 0));
		// 回调函数
		RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> result) {
				super.onSuccess(result);
				try {
					Gson mGson = new Gson();
					LoginResultBean resBean = mGson.fromJson(result.result, LoginResultBean.class);
					if (Const.SUCCESS.equals(resBean.getResult())) {
						UserBean login = resBean.getData().getUser();
						login.setPwd(mEtPwd.getText().toString());
						LogUtils.d(login.toString());
						GCApplication.sApp.setUserInfo(login);
						GCApplication.sApp.setLoginBean(resBean.getData());
						//此处判断是否绑定了房源，如果未绑定，显示小区选择界面
						//如果已绑定，进入首页
						if(GCApplication.sApp.isBind()){
							startNextActivity(null, HomeActivity.class, true);
						}else{
							startNextActivity(null, CommunitySelectActivity.class, true);
						}
					} else {
						showToast(resBean.getErrorCode());
						return;
					}

				} catch (Exception e) {
					showToast(R.string.errorMsg);
				}

			}
		};
		// 发送数据 post方式（提交保存）
		httpUtil.post(WebConstants.METHOD_LOGIN, params, callback);
	}

	private boolean verifyLoginInfo() {
		// 获取手机号输入框内容
		String str = mEtAccount.getText().toString();
		if (null == str || str.equals("")) {
			Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (!CommonTools.isIllegalPhone(str)) {
			Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
			return false;
		}
		
		if (!CommonTools.isIllegalPWD(mEtPwd.getText().toString())) {
			Toast.makeText(this, "请输入6-20位密码", Toast.LENGTH_SHORT).show();
			return false;
		}
//		if (mEtPwd.getText().toString().length()<6 || mEtPwd.getText().toString().length()>20) {
//			Toast.makeText(this, "密码位数必须是6~20位", Toast.LENGTH_SHORT).show();
//			return false;
//		}
		return true;
	
	}

	@Override
	public void callBackWebJson(String json, int msgFlag) {
		// TODO Auto-generated method stub

	}
}
