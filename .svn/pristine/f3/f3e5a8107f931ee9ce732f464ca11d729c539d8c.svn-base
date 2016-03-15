package com.geecity.hisenseplus.home;

import java.util.LinkedList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.WindowManager;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.geecity.hisenseplus.home.activity.property.model.BillsInfos;
import com.geecity.hisenseplus.home.bean.AreaInfoBean;
import com.geecity.hisenseplus.home.bean.DiscTypesBean;
import com.geecity.hisenseplus.home.bean.HouseBean;
import com.geecity.hisenseplus.home.bean.LoginBean;
import com.geecity.hisenseplus.home.bean.UserBean;
import com.ibill.lib.crash.CrashApplication;
import com.ibill.lib.tools.T;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.LogUtils;

public class GCApplication extends CrashApplication {

    private boolean isBind;
    public static GCApplication sApp;
    private LoginBean loginBean;
    private UserBean usInfo;
    private LinkedList<AreaInfoBean> areaInfos;
    private static AreaInfoBean defaultAreaInfo;
    private DbUtils dBUtils;
    private LinkedList<HouseBean> beans = new LinkedList<HouseBean>();
    private LinkedList<DiscTypesBean> discTypes;
    private LinkedList<Activity> activities = new LinkedList<>();
    // 当前处理订单ID
    public String sOrderId;
    public ProgressDialog dialog;
    
    private boolean isDebug = false;

    @Override
    public void onCreate() {
        super.onCreate();
        configLogger(isDebug);
        sApp = this;
        isBind = false;
        dialog = new ProgressDialog(getApplicationContext());
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        JPushInterface.setDebugMode(isDebug); // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this); // 初始化 JPush
    }

    private void configLogger(boolean isAllow) {
        LogUtils.allowD = isAllow;
        LogUtils.allowE = isAllow;
        LogUtils.allowI = isAllow;
    }

    @Override
    public void onCrashHandler() {
        JPushInterface.stopPush(this);
        T.showLong(this, "重启一下，马上回来");
        Intent i =
                        getBaseContext().getPackageManager().getLaunchIntentForPackage(
                                        getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finishActivities();
        startActivity(i);
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public LinkedList<AreaInfoBean> getAreaInfos() {
        return areaInfos;
    }

    // 应将数据存入数据库，即使更新和为空时重新读取
    public void setAreaInfos(LinkedList<AreaInfoBean> areaInfos) {
        this.areaInfos = areaInfos;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
        setAreaInfos(loginBean.getArea());
        for (AreaInfoBean aInfo : this.areaInfos) {
            if (aInfo.getIsDefault() == 1) {
                setDefaultAreaInfo(aInfo);
            }
        }
    }

    public AreaInfoBean getDefaultAreaInfo() {
        return defaultAreaInfo;
    }

    public void setDefaultAreaInfo(AreaInfoBean defaultAreaInfo) {
        GCApplication.defaultAreaInfo = defaultAreaInfo;
        if (areaInfos == null || areaInfos.size() == 0) {
            areaInfos = new LinkedList<AreaInfoBean>();
            areaInfos.add(defaultAreaInfo);
        }
        if (loginBean == null) {
            loginBean = new LoginBean();
        }
        loginBean.setArea(areaInfos);
    }

    /**
     * 是否绑定了房源
     * 
     * @return
     */
    public boolean isBind() {
        isBind = false;
        AreaInfoBean houseBean = getBindHouse();
        // TODO 获取绑定的房间编号，如果没有，表示未绑定
        isBind = (houseBean != null);
        return isBind;
    }

    private AreaInfoBean getBindHouse() {
        if (loginBean != null) {
            LinkedList<AreaInfoBean> aBeans = loginBean.getArea();
            for (AreaInfoBean aBean : aBeans) {
                if (aBean.getIsDefault() == 1) {
                    return aBean;
                }
            }
        }
        return null;
    }

    public void setBind(boolean isBind) {
        this.isBind = isBind;
    }

    /**
     * 列表按照指定位置存放指定数据，定义如下：</br> 0-城市(cityNo)</br> 1-小区(a_id)</br> 2-楼座(b_id)</br>
     * 3-房间匹配ID(JU_RID)</br> 4-单元(r_dy)</br> 5-房号(r_id)</br>
     * 
     * @return
     */
    public LinkedList<HouseBean> getBeans() {
        return beans;
    }

    public void setBeans(LinkedList<HouseBean> beans) {
        this.beans = beans;
    }

    public void setUserInfo(UserBean userInfo) {
        this.usInfo = userInfo;
        if (dBUtils == null) {
            dBUtils = DbUtils.create(this);
        }
        try {
            dBUtils.deleteAll(UserBean.class);
            if (userInfo == null) {
                return;
            }
            dBUtils.save(userInfo);
        } catch (DbException e) {
            try {
                dBUtils.dropTable(UserBean.class);
            } catch (DbException e1) {
                e1.printStackTrace();
            }
        }
    }

    public UserBean getUserInfo() {
        if (usInfo == null) {
            if (dBUtils == null) {
                dBUtils = DbUtils.create(this);
            }
            try {
                usInfo = dBUtils.findFirst(UserBean.class);
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
        return usInfo;
    }

    public LinkedList<DiscTypesBean> getDiscTypes() {
        return discTypes;
    }

    public void setDiscTypes(LinkedList<DiscTypesBean> discTypes) {
        this.discTypes = discTypes;
    }

    private Activity curActivity;
    /**
     * 当前订单支付的类型 0-普通商品，1-物业缴费
     */
	public int sOrderType;
	public LinkedList<BillsInfos> mWaitPayBills;

    public void addActivity(Activity activity) {
        this.curActivity = activity;
        this.activities.add(activity);
    }

    public void finishActivities() {
        for (Activity activity : activities) {
            activity.finish();
        }
    }

    public void showToast(String string) {
        Toast.makeText(sApp, string, Toast.LENGTH_LONG).show();
    }

    public void showDialog(Context context, final String msg) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = new ProgressDialog(context);
        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        dialog.setMessage(msg);
        dialog.show();
    }

    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
