package com.geecity.hisenseplus.home.activity.live;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.utils.CommonTools;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 添加地址：手机号、姓名、区域、地址、是否设置为默认
 * 
 * @author Administrator
 * 
 */
public class AddressAddActivity extends ActionBarActivity{

    public static final String ADDRESS_NAME = "ADDRESS_NAME";
    public static final String ADDRESS_PHONE = "ADDRESS_PHONE";
//    public static final String ADDRESS_CITY = "ADDRESS_CITY";
    public static final String ADDRESS_DETAIL = "ADDRESS_DETAIL";
    public static final String ADDRESS_DEFAULT = "ADDRESS_DEFAULT";
	@ViewInject(R.id.btn_home_menu)
	private Button mTopBackBtn;
	@ViewInject(R.id.tv_home_topbar_title)
	private TextView mTopTitle;

	@ViewInject(R.id.et_address_add_phone)
	private EditText mEtPhone;
	@ViewInject(R.id.et_address_add_name)
	private EditText mEtName;
    @ViewInject(R.id.et_address_add_city)
    private EditText mEtCity;
    @ViewInject(R.id.et_address_add_detail)
    private EditText mEtDetail;
    
    @ViewInject(R.id.cb_address_add_isdefault)
    private CheckBox mCbDefault;
    @ViewInject(R.id.btn_address_add_save)
    private Button mBtnSave;
	
	private int mTitleId;
	public static final String KEY_TITLE = "key_title";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address_add);
		ViewUtils.inject(this);
		mTitleId = getIntent().getIntExtra(KEY_TITLE, R.string.actv_title_address_add);
		mEtPhone.setText(GCApplication.sApp.getUserInfo().getAccount());
		mCbDefault.setChecked(true);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void configActionBar() {
		mTopBackBtn.setVisibility(View.VISIBLE);
		mTopTitle.setText(mTitleId);
	}

	@OnClick({ R.id.btn_home_menu, R.id.btn_address_add_save})
	public void OnClick(View v) {
		switch (v.getId()) {
		case R.id.btn_home_menu: // 点击返回按钮
			setResult(Activity.RESULT_CANCELED);
			finish();
			break;
		case R.id.btn_address_add_save:// 完成
			String phone = mEtPhone.getText().toString().trim();
			if(!CommonTools.isIllegalPhone(phone)){
				showToast("手机号码格式错误");
				return;
			}
			String name = mEtName.getText().toString().trim();
			if(TextUtils.isEmpty(name)){
				showToast("请输入姓名");
				return;
			}
//            String city = mEtCity.getText().toString().trim();
//            if(TextUtils.isEmpty(city)){
//                showToast("请输入城市");
//                return;
//            }
            String detail = mEtDetail.getText().toString().trim();
            if(TextUtils.isEmpty(detail)){
                showToast("请输入详细的地址");
                return;
            }
			Intent intent = new Intent();
			intent.putExtra(ADDRESS_NAME, name);
            intent.putExtra(ADDRESS_PHONE, phone);
//            intent.putExtra(ADDRESS_CITY, city);
            intent.putExtra(ADDRESS_DETAIL, detail);
            intent.putExtra(ADDRESS_DEFAULT, mCbDefault.isChecked());
			setResult(Activity.RESULT_OK, intent);
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	public void requestCallBack(String dataJson, RequestType type) {
	}
}
