package com.geecity.hisenseplus.home.activity;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.CommonEditActivity.IntentKey;
import com.geecity.hisenseplus.home.utils.ViewHolder;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.ibill.lib.tools.T;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 适用于进行编辑和单级选择的二级界面（多级选择不适用）</p> 使用方法：startActivityAsResult，通过intent传递需要的数据</p> 传入参数：</p> 界面类型
 * {@link CommActvType}</p> Intent相关key值 {@link IntentKey}</p> 返回值：onActivityResult方法回调，解析Intent的key
 * {@link IntentKey.COMM_RESULT}</p></p>
 */
public class CommonEditActivity extends ActionBarActivity {

    private static final String TAG = "CommonEditActivity";
	@ViewInject(R.id.btn_home_menu)
	private Button mBtnBack;
	@ViewInject(R.id.tv_home_topbar_title)
	private TextView mTvTitle;

    @ViewInject(R.id.ly_comm_edit)
    private LinearLayout mLyEdit;
    @ViewInject(R.id.et_comm_input)
    private EditText mEtInput;


    @ViewInject(R.id.ly_pwd_edit)
    private LinearLayout mLyPwdEdit;
    @ViewInject(R.id.et_pwd_old_input)
    private EditText mEtOldInput;
    @ViewInject(R.id.et_pwd_new_input)
    private EditText mEtNewInput;
    @ViewInject(R.id.et_pwd_new_confirm_input)
    private EditText mEtNewConfirmInput;


    @ViewInject(R.id.btn_comm_save)
    private Button mBtnSave;

    @ViewInject(R.id.ly_list)
    private RelativeLayout mLyList;
    @ViewInject(R.id.lv_comm_source)
    private ListView mLvSource;
    @ViewInject(R.id.btn_comm_list_save)
    private Button mBtnListSave;

    private CommActvType mType;
    private String mTitle;
    private ArrayList<String> mSourceList = new ArrayList<String>();
    private ArrayList<String> mSourceListStatus;
    private CommSourceAdpter mAdpter;
    private String mDefaultValue;
    private int mSelectedModel = ListView.CHOICE_MODE_NONE;
    private String[] mSelectedResults;

    /**
     * 界面显示类型：显示输入框或者显示列表</p> 输入类型 {@link name INPUT}</p> 选择类型 {@link name SELECT} </p> 密码修改
     * {@link name CHANGE_PWD} </p>
     */
    public enum CommActvType {
        INPUT, SELECT, CHANGE_PWD
    }

    /**
     * @value KEY_TITLE : 导航栏显示的标题（所修改的项目标题）<br>
     *        KEY_TYPE : 界面类型，具体值为{@link CommActvType}<br>
     *        KEY_SOURCE : 选择类型的数据列表 <br>
     *        KEY_RESULT : 返回结果 <br>
     *        KEY_CHOICEMODE : 列表单选或多选模式 <br>
     *        KEY_DEFAULT : 显示的默认值（String 类型.编辑模式，显示在编辑框内；选择模式，显示默认选中的行索引，从0开始）</p>
     */
    public static final class IntentKey {
        public static final String KEY_TITLE = "key_title";
        public static final String KEY_TYPE = "key_type";
        public static final String KEY_SOURCE = "key_source";
        public static final String KEY_RESULT = "key_result";
        public static final String KEY_DEFAULT = "key_default";
        public static final String KEY_CHOICEMODE = "key_choicemode";
        public static final String KEY_SOURCE_SELECTED = "key_source_selected";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_common);
        ViewUtils.inject(this);
        mLvSource.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mLvSource.getChoiceMode() != ListView.CHOICE_MODE_MULTIPLE) {
                    finishActivity(RESULT_OK, mSourceList.get(position));
                } else {
                    updateSelected(position);
                    mAdpter.notifyDataSetChanged();
                }
            }
        });
        initData(getIntent());
    }

    @SuppressLint("NewApi")
    private void updateSelected(int position) {
        if (mLvSource.isItemChecked(position)) {
            mSelectedResults[position] = mSourceList.get(position);
        } else {
            mSelectedResults[position] = "";
        }
        if(mSourceListStatus != null && position < mSourceListStatus.size()){
            mSourceListStatus.set(position, "0".equals(mSourceListStatus.get(position))?"1":"0");
        }
    }

    private void initData(Intent intent) {
        mTitle = intent.getStringExtra(IntentKey.KEY_TITLE);
        mTvTitle.setText(mTitle);
        mType = (CommActvType) intent.getSerializableExtra(IntentKey.KEY_TYPE);
        mDefaultValue = intent.getStringExtra(IntentKey.KEY_DEFAULT);
        switch (mType) {
            case INPUT: {
                mEtInput.setText(mDefaultValue);
                mLyEdit.setVisibility(View.VISIBLE);
                mLyList.setVisibility(View.GONE);
                mLyPwdEdit.setVisibility(View.GONE);
            }
                break;
            case SELECT: {
                mLyList.setVisibility(View.VISIBLE);
                mSelectedModel =
                                intent.getIntExtra(IntentKey.KEY_CHOICEMODE, ListView.CHOICE_MODE_NONE);
                mLvSource.setChoiceMode(mSelectedModel);
                if (mSelectedModel == ListView.CHOICE_MODE_MULTIPLE) {
                    mBtnListSave.setVisibility(View.VISIBLE);
                } else {
                    mBtnListSave.setVisibility(View.GONE);
                }
                mLyPwdEdit.setVisibility(View.GONE);
                mLyEdit.setVisibility(View.GONE);
                mSourceList.clear();
                mSourceList.addAll(intent.getStringArrayListExtra(IntentKey.KEY_SOURCE));
                mSelectedResults = new String[mSourceList.size()];
                mAdpter = new CommSourceAdpter();

                mSourceListStatus = intent.getStringArrayListExtra(IntentKey.KEY_SOURCE_SELECTED);
                mLvSource.setAdapter(mAdpter);
            }
                break;
            case CHANGE_PWD: {
                mLyPwdEdit.setVisibility(View.VISIBLE);
                mLyList.setVisibility(View.GONE);
                mLyEdit.setVisibility(View.GONE);
            }
                break;

            default:
                break;
        }
    }

    @OnClick({R.id.btn_home_menu, R.id.btn_comm_save, R.id.btn_pwd_save,
                    R.id.btn_comm_list_save})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_home_menu:// 返回
                finishActivity(Activity.RESULT_CANCELED, null);
                onBackPressed();
                break;
            case R.id.btn_comm_save:// 保存
                String inputText = mEtInput.getText().toString().trim();
                finishActivity(Activity.RESULT_OK, inputText);
                break;
            case R.id.btn_comm_list_save:// 列表选择保存
                String result = "";
                if(mSourceListStatus != null){//用于关联网站类型选择的数据处理
                    for (int i = 0; i < mSourceListStatus.size(); i++) {
                        if (TextUtils.isEmpty(mSourceListStatus.get(i))) {
                            continue;
                        }
                        if (i > 0 && result.length() > 0) {
                            result += ",";
                        }
                        result += mSourceListStatus.get(i);
                    }
                }else{//用于保险类型选择的数据处理
                    for (int i = 0; i < mSelectedResults.length; i++) {
                        if (TextUtils.isEmpty(mSelectedResults[i])) {
                            continue;
                        }
                        if (i > 0 && result.length() > 0) {
                            result += ",";
                        }
                        result += mSelectedResults[i];
                    }
                    if (result.length() == 0) {
                        showToast("请选择险种，或者按返回键返回");
                        return;
                    }
                }
                finishActivity(Activity.RESULT_OK, result);
                break;
            case R.id.btn_pwd_save:
                String oldPwd = mEtOldInput.getText().toString().trim();
                if (TextUtils.isEmpty(oldPwd)) {
                    T.showShort(CommonEditActivity.this, "请输入原始密码");
                    return;
                }

                if (!oldPwd.equals(mDefaultValue)) {
                    T.showShort(CommonEditActivity.this, "原密码验证失败，请重新输入");
                    mEtOldInput.setText("");
                    mEtOldInput.requestFocus(20);
                    return;
                }

                String newPwd = mEtNewInput.getText().toString().trim();
                if (TextUtils.isEmpty(newPwd)) {
                    T.showShort(CommonEditActivity.this, "请输入新的密码");
                    return;
                }
                String newConfirmPwd = mEtNewConfirmInput.getText().toString().trim();
                if (TextUtils.isEmpty(newConfirmPwd)) {
                    T.showShort(CommonEditActivity.this, "请再次输入新密码");
                    return;
                }
                if (!newPwd.equals(newConfirmPwd)) {
                    T.showShort(CommonEditActivity.this, "新密码输入不一致，请重新输入");
                    return;
                }
                finishActivity(RESULT_OK, newConfirmPwd);
            default:
                break;
        }

    }

    private void finishActivity(int resultCode, String inputText) {
        if (TextUtils.isEmpty(inputText)) {
            resultCode = RESULT_CANCELED;
        }
        Intent intent = new Intent();
        intent.putExtra(IntentKey.KEY_RESULT, inputText);
        setResult(resultCode, intent);
        finish();
        closeKeyBorad();
    }

    @Override
    public void configActionBar() {
        mBtnBack.setVisibility(View.VISIBLE);
    }

    private class CommSourceAdpter extends BaseAdapter {

        @Override
        public int getCount() {
            // int count = 0;
            // if (mSourceList != null) {
            // count = mSourceList.size();
            // }
            return mSourceList.size();
        }

        @Override
        public String getItem(int position) {
            if (mSourceList == null || position > mSourceList.size() - 1) return null;
            return mSourceList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView =
                                LayoutInflater.from(CommonEditActivity.this).inflate(
                                                R.layout.item_select_comm, null);
            }

            RelativeLayout layout = ViewHolder.get(convertView, R.id.ly_comm_item);
            TextView selectTv = ViewHolder.get(convertView, R.id.tv_comm_item);
            if (mLvSource.getChoiceMode() == ListView.CHOICE_MODE_MULTIPLE) {
                if (mLvSource.isItemChecked(position)) {
                    layout.setBackgroundColor(getResources().getColor(R.color.txt_hint_gray));
                    selectTv.setTextColor(getResources().getColor(R.color.txt_hisense_green));
                } else {
                    layout.setBackgroundColor(getResources().getColor(R.color.txt_white));
                    selectTv.setTextColor(getResources().getColor(R.color.textcolor));
                }
            }
            String sub = "";
            if (mSourceListStatus != null && position < mSourceListStatus.size()) {
                selectTv.setTextColor("1".equals(mSourceListStatus.get(position)) ? getResources()
                                .getColor(R.color.txt_hisense_green) : getResources().getColor(
                                R.color.textcolor));
                sub = "1".equals(mSourceListStatus.get(position)) ? "    ( √ )" : "";
            } else {
//                selectTv.setTextColor(getResources()
//                                .getColor(R.drawable.text_comedit_item_selector));
            }
            selectTv.setText(mSourceList.get(position) + sub);
            return convertView;
        }

    }

    @Override
    public void requestCallBack(String dataJson, RequestType type) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        closeKeyBorad();
    }

    void closeKeyBorad() {
        try {
            InputMethodManager im =
                            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (im.isActive()) {
                im.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {

        }
    }
}
