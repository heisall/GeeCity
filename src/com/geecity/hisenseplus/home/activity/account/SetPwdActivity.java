package com.geecity.hisenseplus.home.activity.account;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.HomeActivity;
import com.geecity.hisenseplus.home.bean.BaseBean;
import com.geecity.hisenseplus.home.bean.LoginResultBean;
import com.geecity.hisenseplus.home.bean.UserBean;
import com.geecity.hisenseplus.home.utils.CommonTools;
import com.geecity.hisenseplus.home.utils.Const;
import com.geecity.hisenseplus.home.utils.NetWorkUtils;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.google.gson.Gson;
import com.ibill.lib.https.HttpUtil;
import com.ibill.lib.https.HttpUtil.RequestHttpCallBack;
import com.ibill.lib.webutils.RequestHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class SetPwdActivity extends ActionBarActivity {

	public static final String KEY_PHONE = "key_phone";
	@ViewInject(R.id.btn_home_menu)
	private Button mTopBackBtn;
	@ViewInject(R.id.btn_home_message)
	private Button mTopRightBtn;
	@ViewInject(R.id.tv_home_topbar_title)
	private TextView mTopTitle;

	@ViewInject(R.id.editText1)
	private EditText mEtPwd;

	private String mPhone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pwd);
		ViewUtils.inject(this);
		mPhone = getIntent().getStringExtra(KEY_PHONE);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void configActionBar() {
		mTopBackBtn.setVisibility(View.VISIBLE);
		mTopTitle.setText(R.string.actv_title_pwd_set);
	}

	@OnClick({ R.id.btn_home_menu, R.id.btn_pwd_save })
	public void OnClick(View v) {
		switch (v.getId()) {
		case R.id.btn_home_menu: // 点击返回按钮
			onBackPressed();
			break;
		case R.id.btn_pwd_save: // 完成
			sendRequest();
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
		String ip = NetWorkUtils.getLocalIpAddress(SetPwdActivity.this);
		String model = android.os.Build.MODEL;
		RequestParams params = new RequestParams();
		params.addBodyParameter("account", mPhone);
		params.addBodyParameter("password", pwd);
		params.addBodyParameter("type", model);
		params.addBodyParameter("ip", ip);
		HttpUtil httpUtil = new HttpUtil(new RequestHandler(
				SetPwdActivity.this, "", getResources().getString(
						R.string.loading), 0));
		// 回调函数
		RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> result) {
				super.onSuccess(result);
				try {
					Gson gson = new Gson();
					BaseBean basebean = gson.fromJson(result.result, BaseBean.class);
					if(Const.FAILD.equals(basebean.getResult())){
						showToast(basebean.getErrorCode());
						return;
					}
					LoginResultBean bean = gson.fromJson(result.result, LoginResultBean.class);
					if (Const.SUCCESS.equals(bean.getResult())) {
						UserBean login = bean.getData().getUser();
						showToast("注册成功");
						login.setPwd(pwd);
						GCApplication.sApp.setUserInfo(login);
                        GCApplication.sApp.setLoginBean(bean.getData());
						LogUtils.d(login.toString());
						//此处判断是否绑定了房源，如果未绑定，显示小区选择界面
						//如果已绑定，进入首页
						if(GCApplication.sApp.isBind()){
							startNextActivity(null, HomeActivity.class, true);
						}else{
							startNextActivity(null, CommunitySelectActivity.class, true);
						}
						//
						// if (GCApplication.sAllowPush) {
						// // JPushInterface.onResume(this);
						// // ///添加
						// //
						// JPushInterface.resumePush(getApplicationContext());
						// // JPushInterface.setAlias(LoginActivity.this,
						// // GCApplication.sApp.getUserInfo()
						// // .getUserId(),
						// // new TagAliasCallback() {
						// // public void gotResult(int arg0,
						// // String arg1, Set<String> arg2) {
						// // LogUtils.d(arg0 + "  " + arg1);
						// // }
						// // });
						// // 这句就是将当成该设备的别名,达到往指定客户端发送消息的目的.
						// }
						//
						// finish();
					} else {
						showToast(bean.getErrorCode());
						return;
					}

				} catch (Exception e) {
					showToast(R.string.errorMsg);
				}

			}
			/*
			 * @Override public void onFailure(HttpException arg0, String arg1)
			 * { super.onFailure(arg0, arg1); }
			 */
		};
		httpUtil.put(WebConstants.METHOD_REGISTER, params, callback);
	}

	@Override
	public void requestCallBack(String dataJson, RequestType type) {

	}
}
