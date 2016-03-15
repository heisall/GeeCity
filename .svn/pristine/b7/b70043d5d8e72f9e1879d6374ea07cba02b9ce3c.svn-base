package com.geecity.hisenseplus.home.fragment;

import java.util.ArrayList;
import java.util.LinkedList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.geecity.hisenseplus.home.BaseFragment;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.HomeActivity;
import com.geecity.hisenseplus.home.activity.account.HouseBind0Activity;
import com.geecity.hisenseplus.home.activity.estate.EstateActivity;
import com.geecity.hisenseplus.home.activity.estate.EstateActivity.ESTATE;
import com.geecity.hisenseplus.home.activity.notice.NoticeActivity;
import com.geecity.hisenseplus.home.activity.property.PPActivity;
import com.geecity.hisenseplus.home.activity.property.WYJDActivity;
import com.geecity.hisenseplus.home.activity.repair.RepairActivity;
import com.geecity.hisenseplus.home.bean.ADBean;
import com.geecity.hisenseplus.home.bean.ManagerHomeBean;
import com.geecity.hisenseplus.home.bean.ManagerRoomBean;
import com.geecity.hisenseplus.home.utils.CommonTools;
import com.geecity.hisenseplus.home.utils.Const;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.geecity.hisenseplus.home.view.PicViewer;
import com.geecity.hisenseplus.home.view.PicViewer.OnPageViewClick;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
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
 * 管家
 * 
 * @ClassName: HomePageFragment
 * @author billkong
 */
public class Home1ManagerFragment extends BaseFragment {
	
	@ViewInject(R.id.frg1_picviewer)
	private PicViewer mPicViewer;
    @ViewInject(R.id.btn_frgmt_1_notice)
    private Button mBtnNotice;
    
    @ViewInject(R.id.tv_frg1_address)
    private TextView mTvAddress;
	
	private LinkedList<ADBean> mADBeans;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frgmt_home_1manager, container,
				false);
		ViewUtils.inject(this, view);
		mTvAddress.setText("");
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mPicViewer.getLayoutParams().height = Const.HEIGHT * ScreenUtils.getScreenWidth(getActivity()) / Const.WIDTH;
		mPicViewer.setOnPageViewClick(new OnPageViewClick() {
			
			@Override
			public void onPageViewClick(int postion) {
                CommonTools.adClick((HomeActivity)getActivity(), mADBeans, postion);
			}
		});
		sendRequest();
	}

	/**
	 * 获取服务器数据
	 * 
	 * @Title: getData void
	 */
	private void sendRequest() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("a_id", GCApplication.sApp.getDefaultAreaInfo().getA_id());
        params.addBodyParameter("userid", GCApplication.sApp.getUserInfo().getId()+"");
		HttpUtil httpUtil = new HttpUtil(new RequestHandler(getActivity(), "",
				getResources().getString(R.string.loading), 0));
		// 回调函数
		RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> result) {
				super.onSuccess(result);
				try {
					Gson gson = new Gson();
					ManagerHomeBean resBean = gson.fromJson(result.result, ManagerHomeBean.class);
					if ("1".equals(resBean.getResult())) {
						mADBeans = resBean.getAds();
						if(mADBeans != null && mADBeans.size() > 0){
							ArrayList<String> pics = new ArrayList<String>();
							for (ADBean adBean : mADBeans) {
								pics.add(adBean.getPhoto());
							}
							mPicViewer.setPicUrls(pics);
						}
						ManagerRoomBean room = resBean.getRoom();
						if (room != null) {
							if (TextUtils.isEmpty(room.getA_name())
									|| TextUtils.isEmpty(room.getB_id())
									|| TextUtils.isEmpty(room.getR_id())) {
								return;
							}
							mTvAddress.setText((room.getA_name() + room.getB_id() + "号楼" + room.getR_dy() + "单元" + room.getR_id()).replace("null", ""));
						}
					}
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		httpUtil.post(WebConstants.METHOD_GET_MANAGER_HOMEPAGE, params, callback);
    }

    @OnClick({R.id.btn_frgmt_1_notice, R.id.btn_frgmt_1_live, R.id.btn_frgmt_1_repair,
                    R.id.btn_frgmt_1_praise, R.id.btn_frgmt_1_order, R.id.btn_frgmt_1_wyjd,
                    R.id.btn_frgmt_1_estate,R.id.btn_frgmt_1_estate_new})
    private void onClick(View v) {
        HomeActivity activity = (HomeActivity) getActivity();
        Bundle b = new Bundle();
        switch (v.getId()) {
		case R.id.btn_frgmt_1_notice:
		    b.putInt(NoticeActivity.KEY_TYPE, NoticeActivity.TYPE_NOTICE);
		    activity.startNextActivity(b, NoticeActivity.class);
			break;
        case R.id.btn_frgmt_1_live:
            b.putInt(NoticeActivity.KEY_TYPE, NoticeActivity.TYPE_LIVE);
            activity.startNextActivity(b, NoticeActivity.class);
            break;
        case R.id.btn_frgmt_1_repair:
			if (!GCApplication.sApp.isBind()) {
				showToast("该功能需要绑定您的房源后使用");
				startActivity(new Intent(getActivity(),
						HouseBind0Activity.class));
			} else {
                b.putString(RepairActivity.KEY_TYPE, RepairActivity.TYPE_REPAIR);
                ((HomeActivity) getActivity()).startNextActivity(b, RepairActivity.class);
			}
            break;
        case R.id.btn_frgmt_1_praise:
			if (!GCApplication.sApp.isBind()) {
				showToast("该功能需要绑定您的房源后使用");
				startActivity(new Intent(getActivity(),
						HouseBind0Activity.class));
			} else {
	            b.putString(RepairActivity.KEY_TYPE, RepairActivity.TYPE_COMPLS);
	            ((HomeActivity) getActivity()).startNextActivity(b, RepairActivity.class);
			}
            break;
        case R.id.btn_frgmt_1_order:
			if (!GCApplication.sApp.isBind()) {
				showToast("该功能需要绑定您的房源后使用");
				startActivity(new Intent(getActivity(),
						HouseBind0Activity.class));
			} else {
	            activity.startNextActivity(b, PPActivity.class);
			}
            break;
        case R.id.btn_frgmt_1_wyjd:
            activity.startNextActivity(b, WYJDActivity.class);
            break;
        case R.id.btn_frgmt_1_estate:
            b.putSerializable(EstateActivity.KEY_ESTATE, ESTATE.SECOND_HAND);
            activity.startNextActivity(b, EstateActivity.class);
            break;
        case R.id.btn_frgmt_1_estate_new:
            b.putSerializable(EstateActivity.KEY_ESTATE, ESTATE.NEW_HOME);
            activity.startNextActivity(b, EstateActivity.class);
        	break;
		default:
			break;
		}
	}

	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		return false;
	}
}
