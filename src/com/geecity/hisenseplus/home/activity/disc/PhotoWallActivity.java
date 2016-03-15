package com.geecity.hisenseplus.home.activity.disc;



import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.adapter.PhotoWallAdapter;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.geecity.hisenseplus.home.view.ATMDialog;
import com.geecity.hisenseplus.home.view.ATMDialog.DialogType;
import com.geecity.hisenseplus.home.view.ATMDialog.OnDialogListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 选择照片页面
 * 
 * @author bill kong
 * @create 20150505
 */
public class PhotoWallActivity extends ActionBarActivity {

	@ViewInject(R.id.btn_home_menu)
	private Button topBackBtn;

	@ViewInject(R.id.tv_home_topbar_title)
	TextView topTextviewTitle;

	private GridView mPhotoWall;
	private PhotoWallAdapter adapter;

	Button confirmBtn;
	private int maxSize = 0;
	private int curSize = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_wall);
		ViewUtils.inject(this);
		Intent intent = getIntent();
		maxSize = intent.getExtras().getInt("curMaxSize", DiscAddActivity.PIC_MAX);
		curSize = intent.getExtras().getInt("curSize", 0);
		mPhotoWall = (GridView) findViewById(R.id.photo_wall_grid);
		adapter = new PhotoWallAdapter(this);
		mPhotoWall.setAdapter(adapter);

		new Handler().post(new Runnable() {

			@Override
			public void run() {
				adapter.getLatestImagePaths(200);
				adapter.notifyDataSetChanged();
			}
		});
	}

	// 重写返回键
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// backAction();
			this.overridePendingTransition(R.anim.enter_lefttoright,
					R.anim.exit_lefttoright);

			this.finish();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	@Override
	public void configActionBar() {
		topBackBtn.setVisibility(View.VISIBLE);
		topTextviewTitle.setText("选择上传图片");
	}

	@OnClick({ R.id.btn_home_menu, R.id.select_img_btn_cy_submat })
	public void OnClick(View v) {
		switch (v.getId()) {
		case R.id.btn_home_menu: // 点击返回按钮
			onBackPressed();
			break;
		case R.id.select_img_btn_cy_submat: // 上传照片
			
			ArrayList<String> curSelectList = adapter.getSelectPathList();
			if (curSelectList.size() <= maxSize - curSize) {
				submit(curSelectList);
				return;
			}
			if(curSelectList.size() > maxSize -curSize){
				
				 final ATMDialog dialog=new ATMDialog(this,DialogType.ONE_BUTTON);
					dialog.setDesc("最多支持上传"+maxSize+"张图片");
					dialog.setText("确 定");
					dialog.setOnDialogLister(new OnDialogListener() {
						@Override
						public void onConfirm() {
							dialog.dismiss();
						}

						@Override
						public void onCancle() {
							dialog.dismiss();
						}
					});
					dialog.show();
					return;
			}
			
			
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.overridePendingTransition(R.anim.enter_lefttoright,
				R.anim.exit_lefttoright);
		this.finish();
	}

	void submit(ArrayList<String> pathsList) {
		if (pathsList == null) {
			return;
		}
		// 数据是使用Intent返回
		Intent intent = new Intent();
		// 把返回数据存入Intent
		intent.putStringArrayListExtra("paths", pathsList);
		setResult(2, intent);
		onBackPressed();
	}

    @Override
    public void requestCallBack(String dataJson, RequestType type) {
    }
}
