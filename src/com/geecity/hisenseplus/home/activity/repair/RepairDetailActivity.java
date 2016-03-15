package com.geecity.hisenseplus.home.activity.repair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.PhotoBrowseActivity;
import com.geecity.hisenseplus.home.adapter.RepairDetailProgressAdapter;
import com.geecity.hisenseplus.home.bean.RepairDetailBean;
import com.geecity.hisenseplus.home.bean.RepairDetailResultBean;
import com.geecity.hisenseplus.home.bean.RepairListBean;
import com.geecity.hisenseplus.home.bean.RepairProgressBean;
import com.geecity.hisenseplus.home.utils.Const;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.geecity.hisenseplus.home.view.ListViewForScrollView;
import com.geecity.hisenseplus.home.view.PicViewer;
import com.geecity.hisenseplus.home.view.PicViewer.OnPageViewClick;
import com.ibill.lib.https.HttpUtil;
import com.ibill.lib.https.HttpUtil.RequestHttpCallBack;
import com.ibill.lib.tools.ScreenUtils;
import com.ibill.lib.webutils.RequestHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 报修/投诉详情页
 * 
 * @author Administrator
 * 
 */
public class RepairDetailActivity extends ActionBarActivity {

	@ViewInject(R.id.btn_home_menu)
	private Button mTopBackBtn;
	@ViewInject(R.id.tv_home_topbar_title)
	private TextView mTopTitle;
	@ViewInject(R.id.btn_repair_detail_appra)
	private Button mBtnAppra;

	@ViewInject(R.id.tv_repair_detail_orderno)
	private TextView mTvOrderNo;
	@ViewInject(R.id.tv_repair_detail_content)
	private TextView mTvContent;
	@ViewInject(R.id.tv_repair_detail_type_title)
	private TextView mTvTypeTitle;
	@ViewInject(R.id.lvfscr_repair_detail)
	private ListViewForScrollView mLvRepair;
	@ViewInject(R.id.picviewer)
	private PicViewer mPicViewer;

	private LinkedList<RepairProgressBean> mList = new LinkedList<RepairProgressBean>();
	private RepairDetailProgressAdapter mAdapte;

	private String mDetailType;
	private RepairListBean mRepairBean;

	// 存放图片的集合
	private ArrayList<String> mPhotoList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_repair_detail);
		ViewUtils.inject(this);
		mDetailType = getIntent().getStringExtra(RepairActivity.KEY_TYPE);
		mRepairBean = (RepairListBean) getIntent().getSerializableExtra(
				RepairActivity.KEY_DETAIL);
		mAdapte = new RepairDetailProgressAdapter(this);
		mLvRepair.setAdapter(mAdapte);
		if (mRepairBean != null) {
			mTvOrderNo.setText(mRepairBean.getCode());
			mTvContent.setText(mRepairBean.getMiaoshu());
		}
		mBtnAppra.setVisibility(mRepairBean.getXing() == -1 ? View.VISIBLE
				: View.INVISIBLE);

//		mPhotoList = Arrays.asList(mRepairBean.getPhotos().split(","));

		// 控制图片轮播器的显示与否
		if (mPhotoList.size() < 1) {
			mPicViewer.setVisibility(View.GONE);
		} else {
			mPicViewer.setVisibility(View.VISIBLE);
		}

		mPicViewer.getLayoutParams().height = Const.HEIGHT
				* ScreenUtils.getScreenWidth(this) / Const.WIDTH;
		mPicViewer.setOnPageViewClick(new OnPageViewClick() {

			@Override
			public void onPageViewClick(int postion) {
				ArrayList<String> photos = new ArrayList<>();
				for (String string : mPhotoList) {
					photos.add(string);
				}
				Bundle b = new Bundle();
				b.putStringArrayList(PhotoBrowseActivity.KEY_PIC_INFO, photos);
				startNextActivity(b, PhotoBrowseActivity.class);
			}
		});
		sendRequest();
	}

	@Override
	protected void sendRequest() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("id", mRepairBean.getId() + "");
		HttpUtil httpUtil = new HttpUtil(new RequestHandler(
				RepairDetailActivity.this, "", getResources().getString(
						R.string.loading), 0));
		// 回调函数
		RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> result) {
				super.onSuccess(result);
				try {
					RepairDetailResultBean resultBean = mGson.fromJson(
							result.result, RepairDetailResultBean.class);
					if ("1".equals(resultBean.getResult())) {
						RepairDetailBean bean = resultBean.getData();
						if (bean != null) {
							updateUI(bean);
						} else {
						}
						mAdapte.setArrayList(mList);
						
						String ps = bean.getFj();
						if(TextUtils.isEmpty(ps)){
						    return;
						}
						List<String> pss = Arrays.asList(ps.split(","));
						if(pss != null && pss.size() > 0){
		                    mPhotoList = new ArrayList<String>();
		                    for (String string : pss) {
		                        mPhotoList.add(string);
		                    }
		                    mPicViewer.setVisibility(View.VISIBLE);
		                    mPicViewer.setPicUrls(mPhotoList);
		                }
					} else {
						showToast(resultBean.getErrorCode());
					}
				} catch (Exception e) {
					showToast(R.string.errorMsg);
				}
			}
		};
		httpUtil.post(WebConstants.METHOD_BILL_DETAIL, params, callback);
	}

	/** 更新界面 */
	protected void updateUI(RepairDetailBean dbean) {
		mTvOrderNo.setText(dbean.getCode());
		mTvContent.setText(dbean.getMiaoshu());
		{// 已受理，默认都显示
			RepairProgressBean bean = new RepairProgressBean();
			if (("未受理").equals(dbean.getZt())) {
			    bean.setStatus(10);
			    bean.setDate("");
            } else {
                bean.setStatus(0);
                bean.setDate(dbean.getCreatetime());
            }
			bean.setTitle("已受理");
			
			mList.add(bean);
		}
		// 1.处理中 lwpgsj 不是空
		if (!TextUtils.isEmpty(dbean.getLwpgsj())) {// 处理中
			RepairProgressBean bean = new RepairProgressBean();
			bean.setStatus(1);
			bean.setTitle("处理中");
			bean.setDate(getCurrentDateTime(Long.parseLong(dbean.getLwpgsj())));
			mList.add(bean);
		}
		/* TODO 巍修改1 - head */
		else {
			RepairProgressBean bean = new RepairProgressBean();
			bean.setStatus(11);
			bean.setTitle("处理中");
			bean.setDate("");
			mList.add(bean);
		}/* TODO 巍修改1 - end */

		// 2.已完成 gbtime 不是空
		if (!TextUtils.isEmpty(dbean.getGbtime())) {// 已完成
			RepairProgressBean bean = new RepairProgressBean();
			bean.setStatus(2);
			bean.setTitle("已完成");
			// 联系人
			if (("").equals(dbean.getLwclr()) || dbean.getLwclr() == null) {
				bean.setContact("");
			} else {
				bean.setContact(dbean.getLwclr());
			}

			// 手机号码
			if (("").equals(dbean.getSj()) || dbean.getSj() == null) {
				bean.setContactPhone("");
			} else {
				bean.setContactPhone(dbean.getSj());
			}

			bean.setDate(dbean.getGbtime());
			mList.add(bean);
		}
		/* TODO 巍修改2 - head */
		else {
			RepairProgressBean bean = new RepairProgressBean();
			bean.setStatus(21);
			bean.setTitle("已完成");
			bean.setContact("");
			bean.setContactPhone("");
			bean.setDate("");
			mList.add(bean);
		}/* TODO 巍修改2 - head */

		mAdapte.setArrayList(mList);
		// 3.评价 根据hfzt不是已评价 和gbtim不是空
		mBtnAppra.setVisibility(View.GONE);
		if (!"已评价".equals(dbean.getHfzt())
				&& !TextUtils.isEmpty(dbean.getGbtime())) {
			mBtnAppra.setVisibility(View.VISIBLE);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void configActionBar() {
		mTopBackBtn.setVisibility(View.VISIBLE);
		mTopTitle
				.setText(RepairActivity.TYPE_REPAIR.equals(mDetailType) ? R.string.actv_title_repair_detail
						: R.string.actv_title_repair_appra_detail);
		mTvTypeTitle
				.setText(RepairActivity.TYPE_REPAIR.equals(mDetailType) ? R.string.actv_title_repair_detail_type
						: R.string.actv_title_repair_appra_detail_type);
	}

	@OnClick({ R.id.btn_home_menu, R.id.btn_repair_detail_appra })
	public void OnClick(View v) {
		switch (v.getId()) {
		case R.id.btn_home_menu: // 点击返回按钮
			onBackPressed();
			break;
		case R.id.btn_repair_detail_appra:// 评价
			// TODO 巍 修改start
			Intent intent = new Intent(RepairDetailActivity.this,
					RepairComplActivity.class);
			intent.putExtra(RepairActivity.KEY_DETAIL, mRepairBean);
			startActivity(intent);
			// TODO 巍 修改end
			break;
		default:
			break;
		}
	}

	@Override
	public void requestCallBack(String dataJson, RequestType type) {

	}

	// 获取格式化时间
	public String getCurrentDateTime(long Date) {
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
		String dateString = formatter.format(Date);

		return dateString;
	}
}
