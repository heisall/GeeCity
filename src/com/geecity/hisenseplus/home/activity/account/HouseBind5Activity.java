package com.geecity.hisenseplus.home.activity.account;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.HomeActivity;
import com.geecity.hisenseplus.home.activity.mine.MyEstateActivity;
import com.geecity.hisenseplus.home.bean.AreaInfoBean;
import com.geecity.hisenseplus.home.bean.BaseBean;
import com.geecity.hisenseplus.home.bean.RoomOwnerChildBean;
import com.geecity.hisenseplus.home.bean.RoomOwnerInfoBean;
import com.geecity.hisenseplus.home.bean.RoomOwnerResultBean;
import com.geecity.hisenseplus.home.bean.RoomRolesBean;
import com.geecity.hisenseplus.home.utils.CommonTools;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.ibill.lib.https.HttpUtil;
import com.ibill.lib.https.HttpUtil.RequestHttpCallBack;
import com.ibill.lib.webutils.RequestHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class HouseBind5Activity extends ActionBarActivity{

	@ViewInject(R.id.btn_home_menu)
	private Button mTopBackBtn;
	@ViewInject(R.id.tv_home_topbar_title)
	private TextView mTopTitle;

	@ViewInject(R.id.btn_house_bind_5_change)
	private Button mBtnChange;
	@ViewInject(R.id.btn_house_bind_5_confirm)
	private Button mBtnConfirm;

	@ViewInject(R.id.tv_house_num_1)
	private TextView mTvNum01;
	@ViewInject(R.id.tv_house_num_2)
	private TextView mTvNum02;
	@ViewInject(R.id.tv_house_num_3)
	private TextView mTvNum03;
	@ViewInject(R.id.et_house_num_4)
	private EditText mEtNum04;
	@ViewInject(R.id.et_house_num_5)
	private EditText mEtNum05;
	@ViewInject(R.id.et_house_num_6)
	private EditText mEtNum06;
	@ViewInject(R.id.et_house_num_7)
	private EditText mEtNum07;
	@ViewInject(R.id.et_house_num_8)
	private EditText mEtNum08;
	@ViewInject(R.id.et_house_num_9)
	private EditText mEtNum09;
	@ViewInject(R.id.tv_house_num_10)
	private TextView mTvNum10;
	@ViewInject(R.id.tv_house_num_11)
	private TextView mTvNum11;

	@ViewInject(R.id.radio0)
	private RadioButton mRb01;
	@ViewInject(R.id.radio1)
	private RadioButton mRb02;
	@ViewInject(R.id.radio2)
	private RadioButton mRb03;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_house_bind_5_verfiy);
		ViewUtils.inject(this);
		setOnEditTextChangedListener();
		sendOwnerRequest();
        mIsAddModel = getIntent().getBooleanExtra(MyEstateActivity.KEY_ADD_BIND, false);
    }
    private boolean mIsAddModel;

	// 所选房源的房主登记信息
	RoomOwnerInfoBean mOwnerInfo;
	// 角色信息
	LinkedList<RoomRolesBean> mRoles;
	// 手机号码列表
	List<String> mPhones;
	// 当前显示号码索引
	private int mCurrentPhoneIndex;
	// 选定的角色
	private String mSelectedRole;
	/**
	 * 获取所选房间的业主信息
	 */
	protected void sendOwnerRequest() {
		RequestParams params = new RequestParams();
		//小区ID
		params.addBodyParameter("a_id", GCApplication.sApp.getBeans().get(1).getA_id());
		//楼座ID
		params.addBodyParameter("b_id", GCApplication.sApp.getBeans().get(2).getA_id());
		//单元ID
		params.addBodyParameter("r_dy", GCApplication.sApp.getBeans().get(4).getA_id());
		//房间ID
		params.addBodyParameter("r_id", GCApplication.sApp.getBeans().get(5).getA_id());
		HttpUtil httpUtil = new HttpUtil(new RequestHandler(HouseBind5Activity.this, "", getResources().getString(R.string.loading), 0));
		// 回调函数
		RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> result) {
				super.onSuccess(result);
				try {
					RoomOwnerResultBean beans = mGson.fromJson(result.result, RoomOwnerResultBean.class);
					if("1".equals(beans.getResult())){
						RoomOwnerChildBean beanChild = beans.getData();
						mOwnerInfo = beanChild.getInfo();
						if(mOwnerInfo == null){
							showToast("所选房源的业主登记信息异常");
						}else{
							String phones = mOwnerInfo.getMobilePhone();
							if(TextUtils.isEmpty(phones)){
								showToast("未绑定手机号码，请联系物业");
							}else if(phones.length() < 11){
								showToast("手机号码格式错误，请联系物业");
							}else{
								mPhones = Arrays.asList(phones.split(","));
								updateUIPhone();
							}
						}
						mRoles = beanChild.getRoles();
						if(mRoles == null || mRoles.size() == 0){
						}else{
							updateUIRadioGroup();
						}
					} else {
						showToast(beans.getErrorCode());
						return;
					}

				} catch (Exception e) {
					e.printStackTrace();
					showToast(R.string.errorMsg);
				}

			}
		};
		// 发送数据 post方式（提交保存）
		httpUtil.post(WebConstants.METHOD_GET_OWNERS, params, callback);
	}
	
	protected void updateUIPhone() {
		changPhoneNum(mPhones.get(0));
	}
	
	protected void updateUIRadioGroup() {
		mRb01.setText(mRoles.get(0).getMemo());
		mRb01.setChecked(true);
		mSelectedRole = mRoles.get(0).getDictValue();
		
		mRb02.setText(mRoles.get(1).getMemo());
		mRb03.setText(mRoles.get(2).getMemo());
	}

	protected void sendLockRequest() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("userid", GCApplication.sApp.getUserInfo().getId()+"");
		//小区ID
		params.addBodyParameter("A_id", GCApplication.sApp.getBeans().get(1).getA_id());
		//楼座ID
		params.addBodyParameter("B_id", GCApplication.sApp.getBeans().get(2).getA_id());
		//单元ID
		params.addBodyParameter("R_dy", GCApplication.sApp.getBeans().get(4).getA_id());
		//业主UID(通过身份信息核验中获取)
		params.addBodyParameter("U_id", mOwnerInfo.getU_id()+"");
		//房间中的JU_RID
		params.addBodyParameter("JU_RID", GCApplication.sApp.getBeans().get(3).getA_id());
		//家庭中的角色类型(通过身份信息核验中获取DictValue)
		params.addBodyParameter("Types", mSelectedRole);
		HttpUtil httpUtil = new HttpUtil(new RequestHandler(
				HouseBind5Activity.this, "", getResources().getString(R.string.loading), 0));
		// 回调函数
		RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> result) {
				super.onSuccess(result);
				try {
					BaseBean bean = mGson.fromJson(result.result, BaseBean.class);
					if ("1".equals(bean.getResult())) {
						GCApplication.sApp.setBind(true);
						AreaInfoBean infoBean = new AreaInfoBean();
						infoBean.setA_id(GCApplication.sApp.getBeans().get(1).getA_id());
						infoBean.setA_name(GCApplication.sApp.getBeans().get(1).getA_name());
						infoBean.setIsDefault(1);
						GCApplication.sApp.setDefaultAreaInfo(infoBean);
						if(mIsAddModel){
						    Bundle b = new Bundle();
			                b.putBoolean(MyEstateActivity.KEY_ADD_BIND, mIsAddModel);
						    startNextActivity(b, MyEstateActivity.class, true);
						}else{
						    startNextActivity(null, HomeActivity.class, true);
						}
					} else {
						showToast(bean.getErrorCode());
						return;
					}

				} catch (Exception e) {
					e.printStackTrace();
					showToast(R.string.errorMsg);
				}
			}
		};
		// 发送数据 post方式（提交保存）
		httpUtil.put(WebConstants.METHOD_LOCK_HOME, params, callback);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void configActionBar() {
		mTopBackBtn.setVisibility(View.VISIBLE);
		mTopTitle.setText(R.string.house_bind_title_5);
	}

	@OnClick({ R.id.btn_home_menu, R.id.radio0, R.id.radio2,
			R.id.radio1, R.id.btn_house_bind_5_confirm,
			R.id.btn_house_bind_5_change })
	public void OnClick(View v) {
		switch (v.getId()) {
		case R.id.btn_home_menu: // 点击返回按钮
			onBackPressed();
			break;

		case R.id.radio0://
			mSelectedRole = mRoles.get(0).getDictValue();
			break;

		case R.id.radio1://
			mSelectedRole = mRoles.get(1).getDictValue();
			break;

		case R.id.radio2://	
			mSelectedRole = mRoles.get(2).getDictValue();
			break;
		case R.id.btn_house_bind_5_change:// 更换手机号
			if(mPhones != null && mPhones.size() > 0){
				if(mCurrentPhoneIndex == mPhones.size() - 1){
					mCurrentPhoneIndex = 0;
				}else{
					mCurrentPhoneIndex ++;
				}
				changPhoneNum(mPhones.get(mCurrentPhoneIndex));
			}
			break;
		case R.id.btn_house_bind_5_confirm:// 确定
			if(verfiyInputPhone()){
				sendLockRequest();
			}else{
				showToastLong("手机号码与预留号码不符，请确认后重新输入");
				cleanInput();
			}
			break;
		default:
			break;
		}
	}

	private void cleanInput() {
		mEtNum04.setText("");
		mEtNum05.setText("");
		mEtNum06.setText("");
		mEtNum07.setText("");
		mEtNum08.setText("");
		mEtNum09.setText("");
		mEtNum04.requestFocus();
	}

	private boolean verfiyInputPhone() {
		String phone = mPhones.get(mCurrentPhoneIndex).substring(3, 9);
		LogUtils.d(phone);
		if(phone.equals(getInputPhone())){
			return true;
		}
		return false;
	}

	private String getInputPhone() {
		return mEtNum04.getText().toString().trim()
				+ mEtNum05.getText().toString().trim()
				+ mEtNum06.getText().toString().trim()
				+ mEtNum07.getText().toString().trim()
				+ mEtNum08.getText().toString().trim()
				+ mEtNum09.getText().toString().trim();
	}

	private void changPhoneNum(String phone) {
		mTvNum01.setText(phone.subSequence(0, 1));
		mTvNum02.setText(phone.subSequence(1, 2));
		mTvNum03.setText(phone.subSequence(2, 3));
		mTvNum10.setText(phone.subSequence(9, 10));
		mTvNum11.setText(phone.subSequence(10, 11));
	}

	private void setOnEditTextChangedListener() {
        mEtNum04.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String it = mEtNum04.getText().toString().trim();
                if(!TextUtils.isEmpty(it)){
                    if(!CommonTools.isIllegalNum1(it)){
                        mEtNum04.setText("");
                        showToast("请输入合法的手机号码");
                    }else{
                        mEtNum05.requestFocus();
                        if(!TextUtils.isEmpty(mEtNum05.getText().toString().trim())){
                            mEtNum05.setSelection(1);
                        }
                    }
                    
                }
            }
        });

        mEtNum05.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String it = mEtNum05.getText().toString().trim();
                if(!TextUtils.isEmpty(it)){
                    if(!CommonTools.isIllegalNum1(it)){
                        mEtNum05.setText("");
                        showToast("请输入合法的手机号码");
                    }else{
                        mEtNum06.requestFocus();
                        if(!TextUtils.isEmpty(mEtNum05.getText().toString().trim())){
                            mEtNum05.setSelection(1);
                        }
                    }
                    
                }
            }
        });

        mEtNum06.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String it = mEtNum06.getText().toString().trim();
                if(!TextUtils.isEmpty(it)){
                    if(!CommonTools.isIllegalNum1(it)){
                        mEtNum06.setText("");
                        showToast("请输入合法的手机号码");
                    }else{
                        mEtNum07.requestFocus();
                        if(!TextUtils.isEmpty(mEtNum06.getText().toString().trim())){
                            mEtNum06.setSelection(1);
                        }
                    }
                    
                }
            }
        });

        mEtNum07.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String it = mEtNum07.getText().toString().trim();
                if(!TextUtils.isEmpty(it)){
                    if(!CommonTools.isIllegalNum1(it)){
                        mEtNum07.setText("");
                        showToast("请输入合法的手机号码");
                    }else{
                        mEtNum08.requestFocus();
                        if(!TextUtils.isEmpty(mEtNum07.getText().toString().trim())){
                            mEtNum07.setSelection(1);
                        }
                    }
                    
                }
            }
        });
    
        mEtNum08.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String it = mEtNum08.getText().toString().trim();
                if(!TextUtils.isEmpty(it)){
                    if(!CommonTools.isIllegalNum1(it)){
                        mEtNum08.setText("");
                        showToast("请输入合法的手机号码");
                    }else{
                        mEtNum09.requestFocus();
                        if(!TextUtils.isEmpty(mEtNum08.getText().toString().trim())){
                            mEtNum08.setSelection(1);
                        }
                    }
                    
                }
            }
        });

        mEtNum09.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String it = mEtNum09.getText().toString().trim();
                if(!TextUtils.isEmpty(it)){
                    if(!CommonTools.isIllegalNum1(it)){
                        mEtNum09.setText("");
                        showToast("请输入合法的手机号码");
                    }
                }
            }
        });
    
	}

	@Override
	public void requestCallBack(String dataJson, RequestType type) {

	}
}
