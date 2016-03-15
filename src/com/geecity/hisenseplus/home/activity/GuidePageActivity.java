package com.geecity.hisenseplus.home.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.account.LoginActivity;
import com.geecity.hisenseplus.home.utils.Const;
import com.ibill.lib.tools.SPUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class GuidePageActivity extends Activity {

    private static final int[] PAGE_ID = {R.drawable.luncher_01, R.drawable.luncher_02,
                    R.drawable.luncher_03};

    @ViewInject(R.id.pv_guide)
    private ViewPager mViewPager;
    @ViewInject(R.id.btn_next)
    private Button mBtnNext;
    @ViewInject(R.id.btn_start)
    private Button mBtnStart;
    private List<View> mList = new ArrayList<View>();
    private int mCurrentPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 取消标题栏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidepage);
        ViewUtils.inject(this);
        initNovicePic();
        setListener();
    }

    @OnClick({R.id.btn_next, R.id.btn_start})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next: 
                if(mCurrentPoint < PAGE_ID.length - 1){
                    mViewPager.setCurrentItem(mCurrentPoint + 1);
                }
                break;
            case R.id.btn_start:
                luancherHomeActivity();
            default:
                break;
        }
    }

    boolean isFirstScorllEnd;
    
    private void setListener() {
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                mCurrentPoint = arg0;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                if(arg0 == PAGE_ID.length - 1 && arg0 == mCurrentPoint){
                    if(isFirstScorllEnd){
                        luancherHomeActivity();
                    }else{
                        isFirstScorllEnd = true;
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {}
        });
    }

    private void initNovicePic() {
        for (int i = 0; i < PAGE_ID.length; i++) {
            View view = LayoutInflater.from(GuidePageActivity.this).inflate(R.layout.item_guide, null);
            ImageView imgv = (ImageView) view.findViewById(R.id.novi_img);
            imgv.setImageResource(PAGE_ID[i]);
            mList.add(view);
        }

        mViewPager.setAdapter(new NoviceAdapter());
        mViewPager.setCurrentItem(0);
    }

    class NoviceAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mList == null ? 0 : mList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mList.get(position));
            return mList.get(position);
        }

    }

    protected void luancherHomeActivity() {
        SPUtils.put(GuidePageActivity.this, Const.IS_FIRST_START, false);
        startActivity(new Intent(GuidePageActivity.this, LoginActivity.class));
        overridePendingTransition(R.anim.enter_righttoleft, R.anim.exit_righttoleft);
        finish();
    }

    @Override
    public void onBackPressed() {}
}
