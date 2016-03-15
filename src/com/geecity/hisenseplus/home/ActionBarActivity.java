package com.geecity.hisenseplus.home;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.geecity.hisenseplus.home.bean.BaseBean;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.google.gson.Gson;
import com.ibill.lib.activity.BaseActivity;
import com.lidroid.xutils.DbUtils;

public abstract class ActionBarActivity extends BaseActivity {

    protected static final String TAG = "ActionBarActivity";
    public DbUtils mDBUtils;
    public Gson mGson = new Gson();
    private boolean isPaued;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // setTheme(android.R.style.Theme_NoTitleBar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
//        if (mWebHelper == null) {
//            mWebHelper = new WebHelper();
//            mWebHelper.setNameSpace(WebConstants.REQUEST_NAMESPACE);
//        }
        if (mDBUtils == null) {
            mDBUtils = DbUtils.create(this);
            mDBUtils.configAllowTransaction(true);
        }
        GCApplication.sApp.addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        configActionBar();
        isPaued = false;
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        isPaued = true;
    }

    protected boolean isPaused() {
        return this.isPaued;
    }

    @Override
    public void callBackWebJson(String json, int msgFlag) {}

//    @SuppressWarnings("unchecked")
    protected BaseBean convertBaseBean(String json) {
//        LinkedHashMap<String, Object> map =
//                        mGson.fromJson(json,
//                                        new TypeToken<LinkedHashMap<String, Object>>() {}.getType());
//        if (map == null || map.size() == 0) {
//            return null;
//        }
//        BaseBean bean = new BaseBean();
//        if (map.containsKey("result")) {
//            bean.setResult(map.get("result").toString());
//        }
//        if (map.containsKey("data")) {
//            Object dataObj = map.get("data");
//            if (dataObj instanceof String) {
//                bean.setData("");
//            } else {
//                if (dataObj instanceof LinkedTreeMap<?, ?>) {
//                    LinkedTreeMap<String, Object> dataMap = (LinkedTreeMap<String, Object>) dataObj;
//                    bean.setData(mGson.toJson(dataMap));
//                } else if (dataObj instanceof List<?>) {
//                    List<LinkedTreeMap<String, Object>> dataMap =
//                                    (List<LinkedTreeMap<String, Object>>) dataObj;
//                    bean.setData(mGson.toJson(dataMap));
//                } else {
//                    bean.setData(map.get("data") == null ? "" : map.get("data").toString());
//                }
//            }
//        }
//        if (map.containsKey("errorCode")) {
//            bean.setErrorCode(map.get("errorCode").toString());
//        }
        return null;
    }

    public abstract void configActionBar();

    public abstract void requestCallBack(String dataJson, RequestType type);


    protected void sendRequest() {
    }

    @Override
    public void onBackPressed() {
        try {
            super.onBackPressed();
        } catch (Exception e) {}
    }
//    
// // 获取点击事件
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        // TODO Auto-generated method stub
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            View view = getCurrentFocus();
//            if (isHideInput(view, ev)) {
//                HideSoftInput(view.getWindowToken());
//            }
//        }
//        return super.dispatchTouchEvent(ev);
//    }
//    // 判定是否需要隐藏
//    private boolean isHideInput(View v, MotionEvent ev) {
//        if (v != null && (v instanceof EditText)) {
//            int[] l = { 0, 0 };
//            v.getLocationInWindow(l);
//            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
//                    + v.getWidth();
//            if (ev.getX() > left && ev.getX() < right && ev.getY() > top
//                    && ev.getY() < bottom) {
//                return false;
//            } else {
//                return true;
//            }
//        }
//        return false;
//    }
//    // 隐藏软键盘
//    private void HideSoftInput(IBinder token) {
//        if (token != null) {
//            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            manager.hideSoftInputFromWindow(token,
//                    InputMethodManager.HIDE_NOT_ALWAYS);
//        }
//    }
}
