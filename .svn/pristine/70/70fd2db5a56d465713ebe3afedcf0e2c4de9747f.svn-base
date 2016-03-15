package com.geecity.hisenseplus.home.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.view.PicViewer;
import com.ibill.lib.activity.BaseActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class PhotoBrowseActivity extends BaseActivity {

	protected static final String TAG = "PhotoBrowseActivity";
    public static final String KEY_PIC_INFO = "KEY_PIC_INFO";
    @ViewInject(R.id.btn_home_menu)
    private Button mTopBackBtn;
    @ViewInject(R.id.tv_home_topbar_title)
    private TextView mTopTitle;
    @ViewInject(R.id.imageView)
	private PicViewer mImgView;
    
    private ArrayList<String> mPhotos;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_photo_browse);
		ViewUtils.inject(this);
		mTopTitle.setText(R.string.activity_title_detail);
		mPhotos = getIntent().getStringArrayListExtra(KEY_PIC_INFO);
		mImgView.setPicUrls(mPhotos);
	}

    @OnClick({R.id.btn_home_menu})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_home_menu: // 点击返回按钮
                onBackPressed();
                break;
            default:
                break;
        }
    }


    @Override
    public void callBackWebJson(String json, int msgFlag) {
        // TODO Auto-generated method stub
        
    }
}
