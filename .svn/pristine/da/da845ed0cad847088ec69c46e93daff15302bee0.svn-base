package com.geecity.hisenseplus.home.activity.live;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.bean.BaseBean;
import com.geecity.hisenseplus.home.bean.MyOrderCompListParentBean;
import com.geecity.hisenseplus.home.utils.BitmapAsset;
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
public class OrderAppaActivity extends ActionBarActivity{

	public static final String KEY_DETAIL = "OrderDetail";
    @ViewInject(R.id.btn_home_menu)
	private Button mTopBackBtn;
	@ViewInject(R.id.tv_home_topbar_title)
	private TextView mTopTitle;


    @ViewInject(R.id.img_item_order_comp_logo)
    private ImageView mImgGoods;
    @ViewInject(R.id.tv_item_order_comp_name)
    private TextView mTvName;
    @ViewInject(R.id.tv_item_order_comp_miaos)
    private TextView mTvMiaoS;
    
	@ViewInject(R.id.et_repair_add_content)
	private EditText mEdContent;
    @ViewInject(R.id.rtbr_repair_compl)
	private RatingBar mRatingBar;

	private int mRatingValue = 5;
	private MyOrderCompListParentBean mDetailBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_appa);
		ViewUtils.inject(this);
		mDetailBean = (MyOrderCompListParentBean) getIntent().getSerializableExtra(KEY_DETAIL);
		updateGoodsDetail();
		mRatingBar.setRating(5.0f);
		mRatingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                mRatingValue = (int) rating;
            }
        });
	}
	
    private void updateGoodsDetail() {
        BitmapAsset.displayImg(OrderAppaActivity.this, mImgGoods, mDetailBean.getGoods_image(), R.drawable.icn_community_selected);
        mTvName.setText(mDetailBean.getGoods_name());
        mTvMiaoS.setText("￥" +mDetailBean.getPrice() + "  " + " 收货时间："+mDetailBean.getAdd_time());
    }

    protected void sendRequest() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("orderId", mDetailBean.getOrder_id()+"");
        params.addBodyParameter("storeId", mDetailBean.getSeller_id() + "");
        params.addBodyParameter("goodsId", mDetailBean.getGoods_id()+ "");
        params.addBodyParameter("comment", mEdContent.getText().toString().trim());
        params.addBodyParameter("star", mRatingValue + "");
        LogUtils.d(WebConstants.METHOD_EVALUATE_GOODS + "    orderId: " + mDetailBean.getOrder_id() 
            + "    storeId: " + mDetailBean.getSeller_id() 
             + "    goodsId: " + mDetailBean.getGoods_id() 
             + "  comment : " + mEdContent.getText().toString().trim()
             + " star: " + mRatingValue);
        params.addBodyParameter("a_id", GCApplication.sApp.getDefaultAreaInfo().getA_id());
        HttpUtil httpUtil =
                        new HttpUtil(new RequestHandler(OrderAppaActivity.this, "", getResources()
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
        httpUtil.put(WebConstants.METHOD_EVALUATE_GOODS, params, callback);
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
