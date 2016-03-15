package com.geecity.hisenseplus.home.fragment;

import java.util.ArrayList;
import java.util.LinkedList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import com.geecity.hisenseplus.home.BaseFragment;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.HomeActivity;
import com.geecity.hisenseplus.home.activity.SignActivity;
import com.geecity.hisenseplus.home.activity.account.HouseBind0Activity;
import com.geecity.hisenseplus.home.activity.estate.EstateActivity;
import com.geecity.hisenseplus.home.activity.estate.EstateActivity.ESTATE;
import com.geecity.hisenseplus.home.activity.live.GroupBuyActivity;
import com.geecity.hisenseplus.home.activity.live.LifeInfoActivity;
import com.geecity.hisenseplus.home.activity.live.LiveMoreActivity;
import com.geecity.hisenseplus.home.activity.live.ScoreExchangeActivity;
import com.geecity.hisenseplus.home.activity.notice.NoticeActivity;
import com.geecity.hisenseplus.home.activity.notice.NoticeDetailActivity;
import com.geecity.hisenseplus.home.activity.property.PPActivity;
import com.geecity.hisenseplus.home.activity.property.WYJDActivity;
import com.geecity.hisenseplus.home.activity.repair.RepairActivity;
import com.geecity.hisenseplus.home.bean.ADBean;
import com.geecity.hisenseplus.home.bean.HomeBean;
import com.geecity.hisenseplus.home.bean.HomeNoticeBean;
import com.geecity.hisenseplus.home.bean.MallMenuBean;
import com.geecity.hisenseplus.home.utils.BitmapAsset;
import com.geecity.hisenseplus.home.utils.CommonTools;
import com.geecity.hisenseplus.home.utils.Const;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.geecity.hisenseplus.home.view.AutoTextView;
import com.geecity.hisenseplus.home.view.PicViewer;
import com.geecity.hisenseplus.home.view.PicViewer.OnPageViewClick;
import com.google.gson.Gson;
import com.ibill.lib.https.HttpUtil;
import com.ibill.lib.https.HttpUtil.RequestHttpCallBack;
import com.ibill.lib.tools.ScreenUtils;
import com.ibill.lib.webutils.RequestHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 首页
 * 
 * @ClassName: HomePageFragment
 * @Description:
 * @author billkong
 */
public class Home0HomePageFragment extends BaseFragment {

	@ViewInject(R.id.frg0_picviewer)
	private PicViewer mPicViewr;

	@ViewInject(R.id.tv_frg_notice)
	private AutoTextView mTvNotice;// 公告
	
	@ViewInject(R.id.imageview01)
	private ImageView mImgLiveAD;
    @ViewInject(R.id.imageview11)
    private ImageView mImgShopAD;
    @ViewInject(R.id.imageView21)
    private ImageView mImgHourseAD;
	
	@ViewInject(R.id.hs_frg_1_wygl)
	private HorizontalScrollView mHScrollView;
	
	private LinkedList<ADBean> mADTopBeans;
    private LinkedList<ADBean> mADLiveBeans;
    private LinkedList<ADBean> mADShopBeans;
    private LinkedList<ADBean> mADHouseBeans;
	private int mNoticeNO = 0;
	LinkedList<HomeNoticeBean> mHomNoticeBeans;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frgmt_home_0homepage, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mPicViewr.getLayoutParams().height = Const.HEIGHT * ScreenUtils.getScreenWidth(getActivity()) / Const.WIDTH;
		mPicViewr.setOnPageViewClick(new OnPageViewClick() {
			
			@Override
			public void onPageViewClick(int postion) {
			    CommonTools.adClick((HomeActivity)getActivity(), mADTopBeans, postion);
			}
		});
		sendRequest();
	}

    // 通知滚动控制
	Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0: // 滚动显示通知
				if (mNoticeNO == mHomNoticeBeans.size()){
					mNoticeNO = 0;
				}
				mTvNotice.next();
				mTvNotice.setText(mHomNoticeBeans.get(mNoticeNO++).getTitle());
				sendEmptyMessageDelayed(0, 6000);
				break;
			default:
				break;
			}
		};
	};

	/**
	 * 获取服务器数据
	 * 
	 * @Title: getData void
	 */
	private void sendRequest() {
		RequestParams params = new RequestParams();
		if(GCApplication.sApp == null || GCApplication.sApp.getDefaultAreaInfo() == null){
		  return;
		}
		params.addBodyParameter("a_id", GCApplication.sApp.getDefaultAreaInfo().getA_id());
		HttpUtil httpUtil = new HttpUtil(new RequestHandler(getActivity(), "", getResources().getString(R.string.loading), 0));
		// 回调函数
		RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> result) {
				super.onSuccess(result);
				try {
					Gson gson = new Gson();
					HomeBean resBean = gson.fromJson(result.result,
							HomeBean.class);
					if (Const.SUCCESS.equals(resBean.getResult())) {
						mHomNoticeBeans = resBean.getNotices();
						if(mHomNoticeBeans != null && mHomNoticeBeans.size() > 0){
							mNoticeNO = 0;
							mHandler.sendEmptyMessage(0);
						}
						//顶部广告
						mADTopBeans = resBean.getTopad();
						if(mADTopBeans != null && mADTopBeans.size() > 0){
							ArrayList<String> picUrls = new ArrayList<String>();
							for (ADBean adb : mADTopBeans) {
								picUrls.add(adb.getPhoto());
							}
							mPicViewr.setPicUrls(picUrls);
                        }
                        // 便民信息左侧广告
                        mADLiveBeans = resBean.getLifad();
                        if (mADLiveBeans != null && mADLiveBeans.size() > 0) {
                            BitmapAsset.displayImg(getActivity(), mImgLiveAD, mADLiveBeans.get(0)
                                            .getPhoto(), R.drawable.icn_community_selected);
                        }
                        // 商城部分右侧广告
                        mADShopBeans = resBean.getShopad();
                        if (mADShopBeans != null && mADShopBeans.size() > 0) {
                            BitmapAsset.displayImg(getActivity(), mImgShopAD, mADShopBeans.get(0)
                                            .getPhoto(), R.drawable.icn_community_selected);
                        }
                        // 房屋信息左侧广告
                        mADHouseBeans = resBean.getHoursead();
                        if (mADHouseBeans != null && mADHouseBeans.size() > 0) {
                            BitmapAsset.displayImg(getActivity(), mImgHourseAD, mADHouseBeans
                                            .get(0).getPhoto(), R.drawable.icn_community_selected);
                        }
					} else {

					}
				} catch (Exception e) {

				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				super.onFailure(arg0, arg1);
			}

		};
		httpUtil.post(WebConstants.METHOD_GET_HOMEPAGE, params, callback);
	}

	@OnClick({ R.id.tv_frg_notice, R.id.frg_qiandao, R.id.frg_duihuan, R.id.tv_frg_manager_service, 
			R.id.btn_frgmt_0_left,R.id.btn_frgmt_0_right,
			R.id.btn_frg_1_notice,R.id.btn_frg_1_live, R.id.btn_frg_1_wyjd,
			R.id.btn_frg_1_baoxiu, R.id.btn_frg_1_praise,R.id.btn_frg_1_order, 
			R.id.tv_frg_live_msg, R.id.imageView01, R.id.imageView02, R.id.imageView03, R.id.imageView04, R.id.imageView05, 
			R.id.tv_frg_mall_msg, R.id.imageView11, R.id.imageView12, R.id.imageView13, R.id.imageView14, R.id.imageView15, 
			R.id.tv_frg_house_msg, R.id.imageView21, R.id.imageView22, R.id.imageView23 })
	private void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_frg_notice:// 滚动的通知公告
			if(mHomNoticeBeans != null && mHomNoticeBeans.size() > 0){
				int index = Math.max(0, mNoticeNO - 1);
				Bundle bundle = new Bundle();
				bundle.putString(NoticeDetailActivity.KEY_NOTICE_ID, mHomNoticeBeans.get(index).getId()+"");
				((HomeActivity)getActivity()).startNextActivity(bundle, NoticeDetailActivity.class);
			}
			break;
		case R.id.frg_qiandao:// 签到
			((HomeActivity) getActivity()).startNextActivity(null, SignActivity.class);
			break;
		case R.id.frg_duihuan:// 积分兑换
			((HomeActivity) getActivity()).startNextActivity(null, ScoreExchangeActivity.class);
			break;
		case R.id.tv_frg_manager_service:// 管家服务
			if (!GCApplication.sApp.isBind()) {
				showToast("该功能需要绑定您的房源后使用");
				startActivity(new Intent(getActivity(), HouseBind0Activity.class));
			} else {
				((HomeActivity) getActivity()).changeBottomStatus(1);
			}
			break;
		case R.id.btn_frgmt_0_left:// 左滑
			mHScrollView.smoothScrollTo(0, 0);
			break;
		case R.id.btn_frgmt_0_right:// 右滑
			mHScrollView.smoothScrollTo(1980, 0);
			break;
		case R.id.btn_frg_1_notice:{// 物业通知
			Bundle b = new Bundle();
			b.putInt(NoticeActivity.KEY_TYPE, NoticeActivity.TYPE_NOTICE);
			((HomeActivity)getActivity()).startNextActivity(b, NoticeActivity.class);
		}
			break;
		case R.id.btn_frg_1_live:{// 社区活动
			Bundle b = new Bundle();
			b.putInt(NoticeActivity.KEY_TYPE, NoticeActivity.TYPE_LIVE);
			((HomeActivity)getActivity()).startNextActivity(b, NoticeActivity.class);
			break;
		}
		case R.id.btn_frg_1_wyjd:{// 物业监督
			((HomeActivity)getActivity()).startNextActivity(null, WYJDActivity.class);
		}
			break;
		case R.id.btn_frg_1_baoxiu:{// 物业报修
			if (!GCApplication.sApp.isBind()) {
				showToast("该功能需要绑定您的房源后使用");
				startActivity(new Intent(getActivity(),
						HouseBind0Activity.class));
			} else {
			    Bundle b = new Bundle();
			    b.putString(RepairActivity.KEY_TYPE, RepairActivity.TYPE_REPAIR);
				((HomeActivity) getActivity()).startNextActivity(b, RepairActivity.class);
			}
		}
			break;
		case R.id.btn_frg_1_praise:// 物业投诉
			if (!GCApplication.sApp.isBind()) {
				showToast("该功能需要绑定您的房源后使用");
				startActivity(new Intent(getActivity(),
						HouseBind0Activity.class));
			} else {
                Bundle b = new Bundle();
                b.putString(RepairActivity.KEY_TYPE, RepairActivity.TYPE_COMPLS);
                ((HomeActivity) getActivity()).startNextActivity(b, RepairActivity.class);
			}
			break;
		case R.id.btn_frg_1_order:// 物业账单
			if (!GCApplication.sApp.isBind()) {
				showToast("该功能需要绑定您的房源后使用");
				startActivity(new Intent(getActivity(),
						HouseBind0Activity.class));
			} else {
				startActivity(new Intent(getActivity(), PPActivity.class));
			}
			break;
		case R.id.tv_frg_live_msg:// 生活信息
		    startActivity(new Intent(getActivity(), LifeInfoActivity.class));
		    break;
		case R.id.imageView01:{// 便民信息左侧广告
//		    CommonTools.adClick(((HomeActivity) getActivity()), mADLiveBeans, 0);
		    launchLiveMoreActivity(5,1);
		}
		break;
		case R.id.imageView02:// 家政
            launchLiveMoreActivity(6,1);
        break;
		case R.id.imageView03:// 鲜花
//            launchLiveMoreActivity(31,1);
            launchLiveMoreActivity(44,2);
        break;
		case R.id.imageView04:// 洗衣
            launchLiveMoreActivity(7,1);
        break;
		case R.id.imageView05:// 蛋糕
//            launchLiveMoreActivity(16,1);
		    launchLiveMoreActivity(16,3);
        break;
		case R.id.tv_frg_mall_msg:// 商家信息
			((HomeActivity) getActivity()).changeBottomStatus(2);
			break;
		case R.id.imageView11:{// 经济实用
            CommonTools.adClick(((HomeActivity) getActivity()), mADShopBeans, 0);
		}
			break;
		case R.id.imageView12:// 团购
			((HomeActivity) getActivity()).startNextActivity(null, GroupBuyActivity.class);
			break;
		case R.id.imageView13:// 食品
            launchLiveMoreActivity(14,3);
			break;
		case R.id.imageView14:// 商户
            launchLiveMoreActivity(39,2);
			break;
		case R.id.imageView15:// 家装
            launchLiveMoreActivity(21,4);
			break;
		case R.id.tv_frg_house_msg:// 房屋信息
			
		case R.id.imageView21:// 新房
		{
			Bundle b = new Bundle();
			b.putSerializable(EstateActivity.KEY_ESTATE, ESTATE.NEW_HOME);
			((HomeActivity) getActivity()).startNextActivity(b,
					EstateActivity.class);
		}
			break;
		case R.id.imageView22:// 二手房
		{
			Bundle b = new Bundle();
			b.putSerializable(EstateActivity.KEY_ESTATE, ESTATE.SECOND_HAND);
			((HomeActivity) getActivity()).startNextActivity(b,
					EstateActivity.class);
		}
			break;
		case R.id.imageView23:// 租房
		{
			Bundle b = new Bundle();
			b.putSerializable(EstateActivity.KEY_ESTATE, ESTATE.RENT);
			((HomeActivity) getActivity()).startNextActivity(b,
					EstateActivity.class);
		}
			break;
		default:
			break;
		}
	}

	private void launchLiveMoreActivity(int id, int parentId) {
	    MallMenuBean bean = new MallMenuBean();
	    bean.setId(id);
	    bean.setParent_id(parentId);
        Intent intent = new Intent(getActivity(),LiveMoreActivity.class);
        intent.putExtra(LiveMoreActivity.CATE_BEAN, bean);
        getActivity().startActivity(intent);
    }

    public boolean onBackPressed() {
		// TODO Auto-generated method stub
		return false;
	}
}
