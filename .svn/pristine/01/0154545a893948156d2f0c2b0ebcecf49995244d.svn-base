package com.geecity.hisenseplus.home.activity;

import java.util.Properties;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import cn.jpush.android.api.JPushInterface;

import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.account.CommunitySelectActivity;
import com.geecity.hisenseplus.home.activity.account.LoginActivity;
import com.geecity.hisenseplus.home.bean.LoginBean;
import com.geecity.hisenseplus.home.bean.LoginResultBean;
import com.geecity.hisenseplus.home.bean.UserBean;
import com.geecity.hisenseplus.home.utils.Const;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.google.gson.Gson;
import com.ibill.lib.activity.BaseActivity;
import com.ibill.lib.https.HttpUtil;
import com.ibill.lib.https.HttpUtil.RequestHttpCallBack;
import com.ibill.lib.tools.SPUtils;
import com.ibill.lib.tools.ScreenUtils;
import com.ibill.lib.utils.LibConstant;
import com.ibill.lib.webutils.RequestHandler;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.util.LogUtils;

public class WelcomeActivty extends BaseActivity {

    private UserBean mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(android.R.style.Theme_Light_NoTitleBar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        GCApplication.sApp.addActivity(this);
        View view = View.inflate(this, R.layout.activity_welcome, null);
        mLogin = GCApplication.sApp.getUserInfo();
        welcomeAnim(view);
        setContentView(view);
        ScreenUtils.initScreen(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                initServiceUrl();
            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }
    
    

    private void welcomeAnim(View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_app_launch);
        view.startAnimation(animation);
        final boolean needGuide = (boolean) SPUtils.get(WelcomeActivty.this, Const.IS_FIRST_START, true);
        animation.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
                //读取是否首次安装启动
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {}

            @Override
            public void onAnimationEnd(Animation arg0) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(needGuide){
                            startNextActivity(null, GuidePageActivity.class);
                        }else if (mLogin == null) {
                            startNextActivity(null, LoginActivity.class, true);
                            overridePendingTransition(R.anim.enter_righttoleft,
                                            R.anim.exit_righttoleft);
                        } else {
                            sendRequest();
                        }
                    }
                }, 1500);
            }
        });
    }

    /**
     * 验证登录信息
     */

    protected void sendRequest() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("account", mLogin.getAccount());
        params.addBodyParameter("password", mLogin.getPwd());
        HttpUtil httpUtil = new HttpUtil(new RequestHandler(WelcomeActivty.this, "", "", 0));
        // 回调函数
        RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> result) {
                super.onSuccess(result);
                try {
                    Gson gson = new Gson();
                    LoginResultBean bean = gson.fromJson(result.result, LoginResultBean.class);
                    if ("1".equals(bean.getResult())) {
                        LoginBean login = bean.getData();
                        UserBean userBean = login.getUser();
                        userBean.setPwd(mLogin.getPwd());
                        LogUtils.d(userBean.toString());
                        GCApplication.sApp.setUserInfo(userBean);
                        GCApplication.sApp.setLoginBean(login);
                        // 此处判断是否绑定了房源，如果未绑定，显示小区选择界面
                        // 如果已绑定，进入首页
                        if (GCApplication.sApp.isBind()) {
                            startNextActivity(null, HomeActivity.class, true);
                        } else {
                            startNextActivity(null, CommunitySelectActivity.class, true);
                        }
                    } else {
                        // 起始页不显示登录失败提示，直接进入登录页
                        // showToast(map.get("errorCode").toString());
                        startNextActivity(null, LoginActivity.class);
                    }

                } catch (Exception e) {
                    startNextActivity(null, LoginActivity.class, true);
                }
                overridePendingTransition(R.anim.enter_righttoleft, R.anim.exit_righttoleft);
            }
        };
        // 发送数据 post方式（提交保存）
        httpUtil.post(WebConstants.METHOD_LOGIN, params, callback);
    }

    private void initServiceUrl() {
        Properties prop = new Properties();
        try {
            prop.load(getAssets().open("config.properties"));
            WebConstants.sServiceUrl = prop.getProperty("webserver");
            LibConstant.sServiceUrl = WebConstants.sServiceUrl;
            WebConstants.DEBUG = "true".endsWith(prop.getProperty("debug"));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void callBackWebJson(String json, int msgFlag) {
        // TODO Auto-generated method stub

    }

}
