package com.geecity.hisenseplus.home.activity.repair;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.bean.BaseBean;
import com.geecity.hisenseplus.home.bean.RepairListBean;
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

/**
 * 报修/投诉 评价界面
 * 
 * @author Administrator
 * 
 */
public class RepairComplActivity extends ActionBarActivity{

	@ViewInject(R.id.btn_home_menu)
	private Button mTopBackBtn;
	@ViewInject(R.id.tv_home_topbar_title)
	private TextView mTopTitle;

	@ViewInject(R.id.tv_repair_compl_title)
	private TextView mRepairTitle;
	@ViewInject(R.id.et_repair_add_content)
	private EditText mEdContent;
    @ViewInject(R.id.rtbr_repair_compl)
	private RatingBar mRatingBar;
    @ViewInject(R.id.tvScore_repair_compl)
    private TextView mTvscore;// 分数

	private String mType;
	private int mRatingValue = 5;
	private RepairListBean mDetailBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_repair_compl);
		ViewUtils.inject(this);
		mType = getIntent().getStringExtra(RepairActivity.KEY_TYPE);
		mDetailBean = (RepairListBean) getIntent().getSerializableExtra(RepairActivity.KEY_DETAIL);
		mRepairTitle.setText(mDetailBean.getMiaoshu());
		mRatingBar.setRating(5.0f);
		mTvscore.setText("5分");
		mRatingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                // TODO Auto-generated method stub
                mRatingValue = (int) rating;
                mTvscore.setText(mRatingValue + "分");
            }
        });
	}
	
    protected void sendRequest() {
        RequestParams params = new RequestParams();
		params.addBodyParameter("id", mDetailBean.getId() + "");
        params.addBodyParameter("evaluate", mEdContent.getText().toString().trim());
        params.addBodyParameter("star", mRatingValue + "");
        LogUtils.d(WebConstants.METHOD_BILL_SAVE_EVALUATE + "    id: " + mDetailBean.getId() + "  evaluate : " + mEdContent.getText().toString().trim() + " star: " + mRatingValue);
        params.addBodyParameter("a_id", GCApplication.sApp.getDefaultAreaInfo().getA_id());
        HttpUtil httpUtil =
                        new HttpUtil(new RequestHandler(RepairComplActivity.this, "", getResources()
                                        .getString(R.string.loading), 0));
        // 回调函数
        RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> result) {
                super.onSuccess(result);
                try {
                    BaseBean resultBean = mGson.fromJson(result.result, BaseBean.class);
                    if ("1".equals(resultBean.getResult())) {
                    	showToast("评价成功，感谢您的支持");
                        onBackPressed();
                    } else {
                        showToast(resultBean.getErrorCode());
                    }
                } catch (Exception e) {
                    showToast(R.string.errorMsg);
                }
            }
        };
        httpUtil.put(WebConstants.METHOD_BILL_SAVE_EVALUATE, params, callback);
    }

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void configActionBar() {
		mTopBackBtn.setVisibility(View.VISIBLE);
        mTopTitle.setText(R.string.actv_title_repair_compl);
	}

	@OnClick({ R.id.btn_home_menu, R.id.btn_repair_compl_send})
	public void OnClick(View v) {
		switch (v.getId()) {
		case R.id.btn_home_menu: // 点击返回按钮
			onBackPressed();
			break;
		case R.id.btn_repair_compl_send://提交
			sendRequest();
			break;
		default:
			break;
		}
	}

	@Override
	public void requestCallBack(String dataJson, RequestType type) {
		// TODO Auto-generated method stub

	}
}
