package com.geecity.hisenseplus.home.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.geecity.hisenseplus.home.R;

public class XListViewHeader extends LinearLayout {
    private LinearLayout mContainer;
    private ImageView mArrowImageView;
    private ProgressBar mProgressBar;
    private TextView mHintTextView;
    private int mState = STATE_NORMAL;

    private Animation mRotateUpAnim;
    private Animation mRotateDownAnim;

    private final int ROTATE_ANIM_DURATION = 180;

    public final static int STATE_NORMAL = 0;
    public final static int STATE_READY = 1;
    public final static int STATE_REFRESHING = 2;

    public XListViewHeader(Context context) {
        super(context);
        initView(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public XListViewHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    /**
     * 初始化布局
     * 
     * @param context
     */
    private void initView(Context context) {
        // 初始情况，设置下拉刷新view高度
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, 0);
        mContainer = (LinearLayout) LayoutInflater.from(context).inflate(
                R.layout.xlistview_header, null);
        if (isInEditMode()) {
            return;
        }
        addView(mContainer, lp);
        setGravity(Gravity.BOTTOM);
        // 向下箭头
        mArrowImageView = (ImageView) findViewById(R.id.xlistview_header_arrow);
        // 下拉刷新文字
        mHintTextView = (TextView) findViewById(R.id.xlistview_header_hint_textview);
        // 刷新进度条
        mProgressBar = (ProgressBar) findViewById(R.id.xlistview_header_progressbar);
        // 箭头旋转动画
        mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateUpAnim.setFillAfter(true);
        mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateDownAnim.setFillAfter(true);
    }

    /**
     * 设置显示状态
     * 
     * @param state
     */
    public void setState(int state) {
        if (state == mState)
            return;

        if (state == STATE_REFRESHING) { // 显示进度
            mArrowImageView.clearAnimation();
            mArrowImageView.setVisibility(View.INVISIBLE);
            mProgressBar.setVisibility(View.VISIBLE);
        } else { // 显示箭头图片
            mArrowImageView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
        }

        switch (state) {
        case STATE_NORMAL:
            if (mState == STATE_READY) {
                mArrowImageView.startAnimation(mRotateDownAnim);
            }
            if (mState == STATE_REFRESHING) {
                mArrowImageView.clearAnimation();
            }
            mHintTextView.setText(R.string.xlistview_header_hint_normal);
            break;
        case STATE_READY:
            if (mState != STATE_READY) {
                mArrowImageView.clearAnimation();
                // 箭头由下向上旋转
                mArrowImageView.startAnimation(mRotateUpAnim);
                // 松开刷新数据
                mHintTextView.setText(R.string.xlistview_header_hint_ready);
            }
            break;
        case STATE_REFRESHING:
            // 正在加载
            mHintTextView.setText(R.string.xlistview_header_hint_loading);
            break;
        default:
        }

        mState = state;
    }

    /**
     * 设置刷新界面的高度
     * 
     * @param height
     */
    public void setVisiableHeight(int height) {
        if (height < 0)
            height = 0;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContainer
                .getLayoutParams();
        lp.height = height;
        mContainer.setLayoutParams(lp);
    }

    /**
     * 获得可见高度
     * 
     * @return
     */
    public int getVisiableHeight() {
        return mContainer.getHeight();
    }

}
