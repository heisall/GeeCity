package com.geecity.hisenseplus.home.activity.account;

import java.util.Arrays;
import java.util.Random;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.utils.CommonTools;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class VerfiyPhoneActivity extends ActionBarActivity {

	@ViewInject(R.id.btn_home_menu)
	private Button mTopBackBtn;
	@ViewInject(R.id.btn_home_message)
	private Button mTopRightBtn;
	@ViewInject(R.id.tv_home_topbar_title)
	private TextView mTopTitle;
	@ViewInject(R.id.btn_verfiy_get)
	private Button mBtnGetCode;

	@ViewInject(R.id.et_verfiy_phone)
	private EditText mEtPhone;
	@ViewInject(R.id.et_verfiy_code)
	private EditText mEtCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_verfiy_code);
		ViewUtils.inject(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void configActionBar() {
		mTopBackBtn.setVisibility(View.VISIBLE);
		mTopTitle.setText(R.string.actv_title_phone_verfiy);
	}

	@OnClick({ R.id.btn_home_menu, R.id.btn_home_message, R.id.btn_verfiy_get,
			R.id.btn_verfiy_commit })
	public void OnClick(View v) {
		switch (v.getId()) {
		case R.id.btn_home_menu: // 点击返回按钮
			onBackPressed();
			break;
		case R.id.btn_home_message: //
			break;
		case R.id.btn_verfiy_get: // 获取验证码
			mVerfyCode = getVerfyCode();
			mPhone = mEtPhone.getText().toString().trim();
			if(!CommonTools.isIllegalPhone(mPhone)){
			  showToast("请输入合法手机号码");
			  return;
			}
			mEtCode.requestFocus();
			sendRequest();
			handler.sendEmptyMessage(-1);
			break;
            case R.id.btn_verfiy_commit: // 提交
                mPhone = mEtPhone.getText().toString().trim();
                if (!CommonTools.isIllegalPhone(mPhone)) {
                    showToast("请输入合法手机号码");
                    return;
                }
                String inputCode = mEtCode.getText().toString().trim();
                if (!CommonTools.isIllegalNum4(inputCode)) {
                    showToast("请输入您收到的验证码");
                    return;
                }
			if(mVerfyCode.equals(mEtCode.getText().toString().trim())){
				handler.removeMessages(0);
				handler.removeMessages(-1);
				handler.removeMessages(1);
				Bundle b = new Bundle();
				b.putString(SetPwdActivity.KEY_PHONE, mPhone);
				startNextActivity(b, SetPwdActivity.class);
			}else{
				showToast("验证码有误，请重新输入或再次获取后验证");
			}
			break;
		}
	}

	private String mVerfyCode;
	private String mPhone;

	private String getVerfyCode() {
		Random ran = new Random();
		int r = 0;
		m1: while (true) {
			int n = ran.nextInt(10000);
			r = n;
			int[] bs = new int[4];
			for (int i = 0; i < bs.length; i++) {
				bs[i] = n % 10;
				n /= 10;
			}
			Arrays.sort(bs);
			for (int i = 1; i < bs.length; i++) {
				if (bs[i - 1] == bs[i]) {
					continue m1;
				}
			}
			break;
		}
		if(r<1000){
			int a = new Random().nextInt(9);
			return  a + "" + r;
		}else{
			return String.valueOf(r);
		}
	}

	//【信我家】您的信我家验证码为：0000，感谢您的使用！
	@Override
	protected void sendRequest() {
		HttpUtils http = new HttpUtils();
		http.send(
				HttpRequest.HttpMethod.GET,
				"http://dx.qxtsms.cn/sms.aspx?"
						+ "action=send&userid=2735&account=hisenseplus&password=hisenseplus&mobile="
						+ mPhone
						+ "&content=【信我家】您的信我家验证码为：" + mVerfyCode + "，感谢您的使用！"
						+ "&sendTime=&checkcontent=1",
				new RequestCallBack<String>() {
					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
//						showToast("正在发送");
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						showToast("发送成功，请查收短信。如果60s未收到请重试");
					}

					@Override
					public void onStart() {
					}

					@Override
					public void onFailure(HttpException error, String msg) {
					}
				});

	}

	// 验证码倒计时
	Handler handler = new Handler() {
		public int time = 0;

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case -1: // 重置时间
				time = 60;
				sendEmptyMessage(0);
				break;
			case 0: // 刷新界面
				mBtnGetCode.setText(time + "秒后重新发送");
				if (time > 0) {
					time--;
					mBtnGetCode.setEnabled(false);
					sendEmptyMessageDelayed(0, 1000l);

				} else {
					sendEmptyMessage(1); // 倒计时结束
				}
				break;
			case 1:
				mBtnGetCode.setText("再次获取");
				mBtnGetCode.setEnabled(true);
				break;
			}
		};
	};

	@Override
	public void requestCallBack(String dataJson, RequestType type) {
		// TODO Auto-generated method stub

	}
}
