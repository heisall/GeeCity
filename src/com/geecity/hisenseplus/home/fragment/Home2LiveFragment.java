package com.geecity.hisenseplus.home.fragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geecity.hisenseplus.home.BaseFragment;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.HomeActivity;
import com.geecity.hisenseplus.home.activity.live.GroupBuyActivity;
import com.geecity.hisenseplus.home.activity.live.ShopMallADActivity;
import com.geecity.hisenseplus.home.adapter.GroupBuyListAdapter;
import com.geecity.hisenseplus.home.adapter.LiveHomeMenuAdapter;
import com.geecity.hisenseplus.home.bean.ADBean;
import com.geecity.hisenseplus.home.bean.GroupBuyBean;
import com.geecity.hisenseplus.home.bean.LiveHomePageBean;
import com.geecity.hisenseplus.home.bean.MallMenuBean;
import com.geecity.hisenseplus.home.utils.CommonTools;
import com.geecity.hisenseplus.home.utils.Const;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.geecity.hisenseplus.home.view.GridViewForScrollView;
import com.geecity.hisenseplus.home.view.ListViewForScrollView;
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
 * 生活
 * 
 * @ClassName: HomePageFragment
 * @author billkong
 */
public class Home2LiveFragment extends BaseFragment {

	@ViewInject(R.id.pv_live_top)
	private PicViewer mPvTopAd;

	@ViewInject(R.id.gvfscv_live_menu)
	private GridViewForScrollView mGvMenu;
	
	@ViewInject(R.id.btn_live_categray_shangm)
	private Button mBtn0ShangM;
    @ViewInject(R.id.tv_live_categray_shangm)
    private TextView mTV0ShangM;
    @ViewInject(R.id.btn_live_categray_business)
    private Button mBtn1Business;
    @ViewInject(R.id.tv_live_categray_business)
    private TextView mTV1Business;
    @ViewInject(R.id.btn_live_categray_shipin)
    private Button mBtn2ShiP;
    @ViewInject(R.id.tv_live_categray_shipin)
    private TextView mTV2ShiP;
    @ViewInject(R.id.btn_live_categray_jiazhuang)
    private Button mBtn3JiaZ;
    @ViewInject(R.id.tv_live_categray_jiazhuang)
    private TextView mTV3JiaZ;

    @ViewInject(R.id.lyt_life_ad_top_left)
    private LinearLayout mLytAdTopLeft;
    @ViewInject(R.id.tv_life_ad_top_left)
    private TextView mTVAdTopLeft;
    @ViewInject(R.id.lyt_life_ad_top_right)
    private LinearLayout mLytAdTopRight;
    @ViewInject(R.id.tv_life_ad_top_right)
    private TextView mTVAdTopRight;
//    @ViewInject(R.id.img_life_ad_bottom_left)
//    private ImageView mImgAdBottomLeft;// 海信广场
//    @ViewInject(R.id.img_life_ad_bottom_right)
//    private ImageView mImgAdBottomRight;// 海信电器

    @ViewInject(R.id.lvfsr_live_bottom)
    private ListViewForScrollView mLvfscBottom;
    private LinkedList<GroupBuyBean> mGroupBuys;
    private GroupBuyListAdapter mAdapterGroupBuy;
    
    private LinkedHashMap<Integer, Button> mHmBtns = new LinkedHashMap<>();
    private LinkedHashMap<Integer, TextView> mHmTVs = new LinkedHashMap<>();
    
	private LiveHomeMenuAdapter mMenuAdapter;
	private LinkedList<MallMenuBean> mMenus;
	protected LinkedList<ADBean> mADBeans;
	protected LinkedList<ADBean> mADLefts;
	protected LinkedList<ADBean> mADRights;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frgmt_home_2live, container, false);
		ViewUtils.inject(this, view);
		mPvTopAd.getLayoutParams().height = Const.HEIGHT * ScreenUtils.getScreenWidth(getActivity()) / Const.WIDTH;
		mMenuAdapter = new LiveHomeMenuAdapter(getActivity());
		mGvMenu.setAdapter(mMenuAdapter);
		
		mAdapterGroupBuy = new GroupBuyListAdapter(getActivity());
		mLvfscBottom.setAdapter(mAdapterGroupBuy);
		
		mPvTopAd.setOnPageViewClick(new OnPageViewClick() {
			
			@Override
			public void onPageViewClick(int postion) {
                CommonTools.adClick((HomeActivity)getActivity(), mADBeans, postion);
			}
		});
		sendRequest();
		return view;
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    mHmBtns.clear();
	    mHmBtns.put(0, mBtn0ShangM);
        mHmBtns.put(1, mBtn1Business);
        mHmBtns.put(2, mBtn2ShiP);
        mHmBtns.put(3, mBtn3JiaZ);
        
        mHmTVs.clear();
        mHmTVs.put(0, mTV0ShangM);
        mHmTVs.put(1, mTV1Business);
        mHmTVs.put(2, mTV2ShiP);
        mHmTVs.put(3, mTV3JiaZ);
	}
	
	private int mCurrentId;
	private LinkedHashMap<Integer, LinkedList<MallMenuBean>> mHmMenus = new LinkedHashMap<>();
	
	protected void sendRequest() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("a_id", GCApplication.sApp.getDefaultAreaInfo().getA_id());
		HttpUtil httpUtil = new HttpUtil(new RequestHandler(getActivity(), "",
				getResources().getString(R.string.loading), 0));
		// 回调函数
		RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> result) {
				super.onSuccess(result);
				try {
					LiveHomePageBean resBean = new Gson().fromJson(result.result, LiveHomePageBean.class);
					if (Const.SUCCESS.equals(resBean.getResult())) {
						//顶部广告
						mADBeans = resBean.getAd();
						if(mADBeans != null && mADBeans.size() > 0){
							ArrayList<String> pics = new ArrayList<String>();
							for (ADBean adBean : mADBeans) {
								pics.add(adBean.getPhoto());
							}
							mPvTopAd.setPicUrls(pics);
						}
						//左边广告
						mADLefts = resBean.getAd_left();
						/*if (mADLefts != null && mADLefts.size() > 0) {
							BitmapAsset.displayImg(getActivity(), mImgAdBottomLeft,
									mADLefts.getFirst().getPhoto(),
									R.drawable.icn_community_selected);
						}*/
						
						//右边广告
						mADRights = resBean.getAd_right();
						/*if (mADRights != null && mADRights.size() > 0) {
							BitmapAsset.displayImg(getActivity(), mImgAdBottomRight,
									mADRights.getFirst().getPhoto(),
									R.drawable.icn_community_selected);
						}*/
						
						//菜单
						mHmMenus.put(0, resBean.getSm());
						mHmMenus.put(1, resBean.getSh());
						mHmMenus.put(2, resBean.getSp());
						mHmMenus.put(3, resBean.getJz());
						mMenus = resBean.getSm();
						if(mMenus != null && mMenus.size() > 0){
							mMenuAdapter.setMenus(mMenus);
						}
						
						//底部团购列表
						mGroupBuys = resBean.getGroupbuy();
						mAdapterGroupBuy.setList(mGroupBuys);
					}else{
						showToast(resBean.getErrorCode());
					}
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		httpUtil.post(WebConstants.METHOD_LIFE_HOMEPAGE, params, callback);
    }

    @OnClick({R.id.lyt_life_ad_top_left, R.id.lyt_life_ad_top_right, R.id.tv_life_ad_top_left,
                    R.id.tv_life_ad_top_right,R.id.lyt_life_ad_bottom_left,
                    R.id.tv_life_ad_bottom_left,R.id.lyt_life_ad_bottom_right,
                    R.id.tv_life_ad_bottom_right, R.id.btn_live_categray_shangm,
                    R.id.btn_live_categray_business, R.id.btn_live_categray_shipin,
                    R.id.btn_live_categray_jiazhuang})
	private void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_live_categray_shangm:
			changeBtnBg(0);
			break;
		case R.id.btn_live_categray_business:
			changeBtnBg(1);
			break;
		case R.id.btn_live_categray_shipin:
			changeBtnBg(2);
			break;
		case R.id.btn_live_categray_jiazhuang:
			changeBtnBg(3);
			break;
		case R.id.lyt_life_ad_top_left://社区团购
		case R.id.tv_life_ad_top_left:
			((HomeActivity)getActivity()).startNextActivity(null, GroupBuyActivity.class);
			break;
		case R.id.lyt_life_ad_top_right://商城优惠
		case R.id.tv_life_ad_top_right:
			((HomeActivity)getActivity()).startNextActivity(null, ShopMallADActivity.class);
			break;
		/*case R.id.img_life_ad_bottom_left://底部左侧广告
          if (mADLefts != null && 0 < mADLefts.size()) {
            CommonTools.adClick((HomeActivity)getActivity(), mADLefts, 0);
          }
			break;
		case R.id.img_life_ad_bottom_right://底部右侧广告
			if(mADRights != null && 0 < mADRights.size()){
	            CommonTools.adClick((HomeActivity)getActivity(), mADRights, 0);
			}
			break;*/
		case R.id.lyt_life_ad_bottom_left://海信广场
        case R.id.tv_life_ad_bottom_left:
            if (mADLefts != null && 0 < mADLefts.size()) {
                CommonTools.adClick((HomeActivity)getActivity(), mADLefts, 0);
              }
            break;
        case R.id.lyt_life_ad_bottom_right://海信电器
        case R.id.tv_life_ad_bottom_right:
            if(mADRights != null && 0 < mADRights.size()){
                CommonTools.adClick((HomeActivity)getActivity(), mADRights, 0);
            }
            break;
		default:
			break;
		}
		mMenuAdapter.setMenus(mHmMenus.get(mCurrentId));
	}

	private void changeBtnBg(final int newId) {
	    mHmBtns.get(mCurrentId).setTextColor(getActivity().getResources().getColor(R.color.txt_hint_gray));
	    mHmBtns.get(mCurrentId).setBackgroundColor(getActivity().getResources().getColor(R.color.bg_live_top_menu));
	    mHmBtns.get(newId).setTextColor(getActivity().getResources().getColor(R.color.txt_hisense_green));
	    mHmBtns.get(newId).setBackgroundColor(getActivity().getResources().getColor(R.color.txt_white));
	    mHmTVs.get(mCurrentId).setVisibility(View.INVISIBLE);
	    mHmTVs.get(newId).setVisibility(View.VISIBLE);
        mCurrentId = newId;
    }

    public boolean onBackPressed() {
		return false;
	}
}
