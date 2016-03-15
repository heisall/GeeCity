package com.geecity.hisenseplus.home.view;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.utils.BitmapAsset;

/**
 * @author Administrator
 * 
 */
public class PicSmallViewer extends FrameLayout implements OnPageChangeListener {

    private Context mCtx;
    private ViewPager mPager;
    private List<String> imgUrlList = new ArrayList<String>(); // 存放图片的地址
    private static final int[] POINT_IDS = {R.id.vp_point_0, R.id.vp_point_1, R.id.vp_point_2,
                    R.id.vp_point_3, R.id.vp_point_4, R.id.vp_point_5, R.id.vp_point_6,
                    R.id.vp_point_7, R.id.vp_point_8, R.id.vp_point_9};

    private OnPageViewClick onPageViewClick;


    public void setOnPageViewClick(OnPageViewClick onPageViewClick) {
        this.onPageViewClick = onPageViewClick;
    }

    private ArrayList<String> mOldUrls;

    public void setPicUrls(ArrayList<String> urls) {
        if (urls == null || urls.size() == 0 || urls.equals(mOldUrls)) {
            return;
        }
        mOldUrls = urls;
        imgUrlList.clear();
        imgUrlList.addAll(urls);
        try {
            ((ImageView) mPointViewList.get(mCurrentPoint)).setSelected(false);
        } catch (Exception e) {

        }
        mPicViewList.clear();
        mPointViewList.clear();
        initNovicePic();
    }

    private List<View> mPicViewList = new ArrayList<View>();
    private List<View> mPointViewList = new ArrayList<View>();
    private int mCurrentPoint = 0;
    private int mCurrentPosition = 0;
    private static final int AUTO_RATE = 1000 * 5;

    public PicSmallViewer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mCtx = context;
        LayoutInflater.from(context).inflate(R.layout.widget_layout_picviewer, this);
        mPager = (ViewPager) findViewById(R.id.vp_pic_viewer);
        mPager.setOnPageChangeListener(this);
        initNovicePic();
    }

    private void initNovicePic() {
        for (int i = 0; i < imgUrlList.size(); i++) {
            View view = LayoutInflater.from(mCtx).inflate(R.layout.widget_picviewer_img, null);
            ImageView viewImg = (ImageView) view.findViewById(R.id.widget_img);
            BitmapAsset.displayRec(mCtx, viewImg, imgUrlList.get(i));
            viewImg.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (null != onPageViewClick) {
                        onPageViewClick.onPageViewClick();
                    }
                }
            });
            mPicViewList.add(view);
            ImageView pointView = (ImageView) findViewById(POINT_IDS[i]);
            pointView.setVisibility(View.VISIBLE);
            if (i == 0) {
                pointView.setSelected(true);
            }
            mPointViewList.add(pointView);

        }
        // 只有一个图片时，不显示位置标识
        if (imgUrlList.size() == 1) {
            mPointViewList.get(0).setVisibility(View.INVISIBLE);
        }
        mPager.setAdapter(new NoviceAdapter());
        mCurrentPoint = 0;
        mCurrentPosition = 0;
        mPager.setCurrentItem(mCurrentPosition);
        mAutoScrollHandler.sendEmptyMessageDelayed(0, AUTO_RATE);
    }

    private Handler mAutoScrollHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            ++mCurrentPosition;
            if (mCurrentPosition == mPicViewList.size()) {
                mCurrentPosition = 0;
                mPager.setCurrentItem(mCurrentPosition, false);
            } else {
                mPager.setCurrentItem(mCurrentPosition);
            }
            removeMessages(0);
            sendEmptyMessageDelayed(0, AUTO_RATE);
        }

    };

    @Override
    public void onPageScrollStateChanged(int arg0) {
        switch (arg0) {
            case 1:// 正在滑动
                break;
            case 2:// 滑动完毕
                break;
            case 0:// 空闲
                break;
        }
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {}

    @Override
    public void onPageSelected(int arg0) {
        mCurrentPosition = arg0;
        ((ImageView) mPointViewList.get(arg0)).setSelected(true);
        ((ImageView) mPointViewList.get(mCurrentPoint)).setSelected(false);
        mCurrentPoint = arg0;
        mAutoScrollHandler.removeMessages(0);
        mAutoScrollHandler.sendEmptyMessageDelayed(0, AUTO_RATE);
    }

    class NoviceAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mPicViewList == null ? 0 : mPicViewList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mPicViewList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mPicViewList.get(position));
            return mPicViewList.get(position);
        }

    }
    public interface OnPageViewClick {
        public void onPageViewClick();
    }

    public void recyle() {
        // mAutoScrollHandler.removeMessages(0);
        // mPager.removeAllViews();
    }

}
