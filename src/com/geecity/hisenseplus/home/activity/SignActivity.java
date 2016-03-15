package com.geecity.hisenseplus.home.activity;

import java.util.ArrayList;
import java.util.LinkedList;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.bean.ADBean;
import com.geecity.hisenseplus.home.bean.ADListResultBean;
import com.geecity.hisenseplus.home.bean.BaseBean;
import com.geecity.hisenseplus.home.bean.LoginBean;
import com.geecity.hisenseplus.home.bean.LoginResultBean;
import com.geecity.hisenseplus.home.bean.UserBean;
import com.geecity.hisenseplus.home.utils.CommonTools;
import com.geecity.hisenseplus.home.utils.Const;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.geecity.hisenseplus.home.view.PicViewer;
import com.geecity.hisenseplus.home.view.PicViewer.OnPageViewClick;
import com.google.gson.Gson;
import com.ibill.lib.activity.BaseActivity;
import com.ibill.lib.https.HttpUtil;
import com.ibill.lib.https.HttpUtil.RequestHttpCallBack;
import com.ibill.lib.tools.ScreenUtils;
import com.ibill.lib.webutils.RequestHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 签到页面
 */
public class SignActivity extends BaseActivity {

	@ViewInject(R.id.btn_home_menu)
	private Button mTopBackBtn;
	@ViewInject(R.id.tv_home_topbar_title)
	private TextView mTopTitle;
	@ViewInject(R.id.pv_sign)
	private PicViewer mImgView;
	@ViewInject(R.id.btn_sign)
	private Button mBtnSign;
	@ViewInject(R.id.tv_sign_jifen)
    private TextView mTvScore;// 积分

	private ArrayList<String> mPhotos;
	protected LinkedList<ADBean> mAdBeans;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign);
		ViewUtils.inject(this);
		mTopTitle.setText(R.string.activity_title_sign);
		mImgView.getLayoutParams().height = ScreenUtils.getScreenWidth(this) * 5 / 4;
		mImgView.setOnPageViewClick(new OnPageViewClick() {
			
			@Override
			public void onPageViewClick(int postion) {
			    CommonTools.adClick(SignActivity.this, mAdBeans, postion);
			}
		});
		sendRequestAD();// 获取海报信息
		updateUserinfo();// 更新用户信息，积分信息
	}

	/** 获取海报信息 **/
	private void sendRequestAD() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("a_id", GCApplication.sApp.getDefaultAreaInfo().getA_id()+"");
		HttpUtil httpUtil = new HttpUtil(new RequestHandler(SignActivity.this,
				"", getResources().getString(R.string.loading), 0));
		RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> result) {
				super.onSuccess(result);
				try {
					ADListResultBean resultBean = new Gson().fromJson(result.result, ADListResultBean.class);
					if (Const.SUCCESS.equals(resultBean.getResult())) {
						mAdBeans = resultBean.getAd();
						if(mAdBeans != null && mAdBeans.size() > 0){
							mPhotos = new ArrayList<>();
							for (ADBean ab : mAdBeans) {
								mPhotos.add(ab.getPhoto());
							}
							mImgView.setPicUrls(mPhotos);
						}
					} else {
						showToast(resultBean.getErrorCode());
					}
				} catch (Exception e) {
					showToast(R.string.errorMsg);
				}
			}
		};
		httpUtil.post(WebConstants.METHOD_SIGN_INDEX, params, callback);
	}

	@OnClick({ R.id.btn_home_menu, R.id.btn_sign })
	public void OnClick(View v) {
		switch (v.getId()) {
		case R.id.btn_home_menu: // 点击返回按钮
			onBackPressed();
			break;
		case R.id.btn_sign:// 签到
			sendRequest();
			break;
		default:
			break;
		}
	}
	
	/** 签到 **/
	protected void sendRequest() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("account", GCApplication.sApp.getUserInfo().getAccount());
		HttpUtil httpUtil = new HttpUtil(new RequestHandler(SignActivity.this,
				"", getResources().getString(R.string.loading), 0));
		// 回调函数
		RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> result) {
				super.onSuccess(result);
				try {
					BaseBean resultBean = new Gson().fromJson(result.result, BaseBean.class);
					if (Const.SUCCESS.equals(resultBean.getResult())) {
						showToast("今日签到成功，积分奉上！明天继续吧");
						updateUserinfo();// 更新用户信息(积分)
					} else {
						showToast(resultBean.getErrorCode());
					}
				} catch (Exception e) {
					showToast(R.string.errorMsg);
				}
			}
		};
		httpUtil.put(WebConstants.METHOD_USER_SIGNIN, params, callback);
	}

	@Override
	public void callBackWebJson(String json, int msgFlag) {}
	
	/** 获取用户信息 */
    private void updateUserinfo() {
        final UserBean userinfo = GCApplication.sApp.getUserInfo();
        RequestParams params = new RequestParams();
        params.addBodyParameter("account", userinfo.getAccount());
        params.addBodyParameter("password", userinfo.getPwd());
        HttpUtil httpUtil = new HttpUtil(new RequestHandler(SignActivity.this, "",
                "", 0));
        // 回调函数
        RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> result) {
                super.onSuccess(result);
                try {
                    Gson gson = new Gson();
                    LoginResultBean bean = gson.fromJson(result.result,
                            LoginResultBean.class);
                    if ("1".equals(bean.getResult())) {
                        LoginBean login = bean.getData();
                        UserBean userBean = login.getUser();
                        mTvScore.setText("积分: " + userBean.getJifen());
                    } else {
                        showToast("获取用户信息失败");
                    }
                } catch (Exception e) {
                    showToast("获取用户信息失败");
                }
            }
        };
        // 发送数据 post方式（提交保存）
        httpUtil.post(WebConstants.METHOD_LOGIN, params, callback);
    }
}
