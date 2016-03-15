package com.geecity.hisenseplus.home.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geecity.hisenseplus.home.R;

public class ATMDialog extends Dialog implements
		android.view.View.OnClickListener {

	private Button mBtnLeft;
	private Button mBtnRight;
	private Button mBtnCofirm;
//	private RelativeLayout mLayoutShare;
	private LinearLayout mLayoutTwoBtn;
	private Context context;
     
	private TextView dialogTvDesc;
	private DialogType mType;
	

	private OnDialogListener mListener;

	public void setOnDialogLister(OnDialogListener l) {
		this.mListener = l;
	}

	private OnShareListener mSListener;

	public void setOnShareListener(OnShareListener l) {
		this.mSListener = l;
	}

	private ATMDialog(Context context) {
		super(context);
	}

	/**
	 * 初始化类型，type为{@link DialogType}
	 * <p>
	 * 按钮监听分别为{@link OnDialogListener}，{@link OnShareListener}
	 * 
	 * @param context
	 * @param type
	 *            : {@link DialogType}
	 */
	public ATMDialog(Context context, DialogType type) {
		super(context, R.style.MyDialog);
		this.mType = type;
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.laout_custom_dialog);
		setCanceledOnTouchOutside(false);
		initView();
		switch (mType) {
		case ONE_BUTTON:
			mBtnCofirm.setVisibility(View.VISIBLE);
			break;
		case TWO_BUTTON:
			mLayoutTwoBtn.setVisibility(View.VISIBLE);
			break;
//		case SHARE:
//			mLayoutShare.setVisibility(View.VISIBLE);
//			mLayoutTwoBtn.setVisibility(View.VISIBLE);
//			break;
		default:
			break;
		}
		
    }

	private void initView() {
		mBtnLeft = (Button) findViewById(R.id.dialog_left);
		mBtnRight = (Button) findViewById(R.id.dialog_right);
		mBtnCofirm = (Button) findViewById(R.id.dialog_cofirm);
		dialogTvDesc = (TextView) findViewById(R.id.dialog_tv_desc);
        mLayoutTwoBtn = (LinearLayout) findViewById(R.id.dialog_layout_two_button);
		
		
		mBtnLeft.setOnClickListener(this);
		mBtnRight.setOnClickListener(this);
		mBtnCofirm.setOnClickListener(this);
		
		if(mLeftTxtId != null)	mBtnCofirm.setText(mLeftTxtId);
		if(mLeftTxtId != null)	mBtnLeft.setText(mLeftTxtId);
		if(mRightTxtId != null) mBtnRight.setText(mRightTxtId);
		if(tvDesc != null) dialogTvDesc.setText(tvDesc);
		else if(strDesc != null) dialogTvDesc.setText(strDesc);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.dialog_left: {
			if (mListener != null) {
				mListener.onConfirm();
			}
		}
			break;
		case R.id.dialog_right: {
			if (mListener != null) {
				mListener.onCancle();
			}
		}
			break;
		case R.id.dialog_cofirm: {
			if (mListener != null) {
				mListener.onConfirm();
			}
		}
			break;
		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
//		super.onBackPressed();
//		dismiss();
	}

	/**
	 * {@link QQ} : 分享到QQ
	 * <p>
	 * {@link WCHAT} ：分享到微信
	 */
	public enum ShareType {
		QQ, WCHAT
	}

	/**
	 * {@link ONE_BUTTON} : 只显示一个Button
	 * <p>
	 * {@link TWO_BUTTON} ：显示两个Button
	 * <p>
	 * {@link SHARE} ：显示分享按钮
	 */
	public enum DialogType {
		ONE_BUTTON, TWO_BUTTON, SHARE
	}

	/**
	 * 监听方法{@link setOnDialogLister}
	 * <p>
	 * 回掉方法
	 * <p>
	 * 确认按钮点击：{@link onConfirm()} 取消按钮点击：{@link onCancle()}
	 */
	public interface OnDialogListener {
		void onConfirm();

		void onCancle();
	}

	/**
	 * 监听方法{@link setOnDialogLister}
	 * <p>
	 * 回掉方法
	 * <p>
	 * 确认按钮点击：{@link onShare({@link ShareType})}
	 * 
	 */
	public interface OnShareListener {
		void onShare(ShareType type);
	}
	
	private String mLeftTxtId = null;
	private String mRightTxtId = null;
	
	//描述
	private String tvDesc = null;
	private String strDesc = null;

	public void setText(String leftSoruceId, String rightSoruceId) {
		this.mLeftTxtId = leftSoruceId;
		this.mRightTxtId = rightSoruceId;
	}
	
	public void setText(int leftSoruceId, int rightSoruceId) {
		this.mLeftTxtId = context.getString(leftSoruceId);
		this.mRightTxtId =  context.getString(rightSoruceId);
	}
	
	
	public void setText(int sourceId){
		this.mLeftTxtId =  context.getString(sourceId);
	}
	public void setText(String sourceId){
		this.mLeftTxtId =  sourceId;
	}
	
	public void setDesc(int tvDesc){
		this.tvDesc =  context.getString(tvDesc);
	}
	
	public void setDesc(String strDesc){
		this.strDesc = strDesc;
	}

}
