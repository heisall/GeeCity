package com.geecity.hisenseplus.home.activity.property;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.PhotoBrowseActivity;
import com.geecity.hisenseplus.home.adapter.CommentsAdapter;
import com.geecity.hisenseplus.home.bean.BaseBean;
import com.geecity.hisenseplus.home.bean.CommentBean;
import com.geecity.hisenseplus.home.bean.DiscTypesBean;
import com.geecity.hisenseplus.home.bean.WyjdDetailBean;
import com.geecity.hisenseplus.home.bean.WyjdWorkListBean;
import com.geecity.hisenseplus.home.utils.BitmapAsset;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.geecity.hisenseplus.home.view.ListViewForScrollView;
import com.geecity.hisenseplus.home.view.PicViewer;
import com.geecity.hisenseplus.home.view.PicViewer.OnPageViewClick;
import com.ibill.lib.https.HttpUtil;
import com.ibill.lib.https.HttpUtil.RequestHttpCallBack;
import com.ibill.lib.webutils.RequestHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class WYJDDetailActivity extends ActionBarActivity {

	@ViewInject(R.id.btn_home_menu)
	private Button mTopBackBtn;
	@ViewInject(R.id.tv_home_topbar_title)
	private TextView mTopTitle;

	@ViewInject(R.id.sc_wyjd_detail)
	private ScrollView mScrollView;
	
    @ViewInject(R.id.tv_wyjd_detail_title)
    private TextView mTvTitle;
    @ViewInject(R.id.tv_wyjd_detail_date)
    private TextView mTvDate;
    @ViewInject(R.id.tv_wyjd_detail_catogery)
    private TextView mTvCatogery;
    
    @ViewInject(R.id.img_wyjd_detail_pic)
    private PicViewer mImgPic;

    @ViewInject(R.id.btn_wyjd_detail_pinglun)
    private Button mBtnPingLun;
    @ViewInject(R.id.tv_wyjd_detail_view_count)
    private TextView mTvViewCount;
    @ViewInject(R.id.tv_wyjd_detail_pinglun)
    private TextView mTvPingLunTimes;
    @ViewInject(R.id.btn_wyjd_detail_zan)
    private Button mBtnZan;
    @ViewInject(R.id.tv_wyjd_detail_zan)
    private TextView mTvZanTimes;
    @ViewInject(R.id.btn_wyjd_detail_share)
    private Button mBtnShare;
    @ViewInject(R.id.tv_wyjd_detail_share)
    private TextView mTvShareTimes;
	
    @ViewInject(R.id.lv_wyjd_detail_pinglun)
	private ListViewForScrollView mListView;
    
	@ViewInject(R.id.et_wyjd_detail_bottom_pinglun)
	private EditText mEtPinglun;
    @ViewInject(R.id.btn_wyjd_detail_bottom_send)
    private Button mBtnSend;
    
    @ViewInject(R.id.layout_wyjd_detail_bottom_pinglun)
    private RelativeLayout mRlyBottom;
    @ViewInject(R.id.textView2)
    private TextView mTv2;

	private CommentsAdapter mAdapter;
	private WyjdWorkListBean mWorkListbean;
	LinkedList<CommentBean> mCmBeans;

	private void setCommentsVisibilty(int visible){
		mRlyBottom.setVisibility(visible);
		mTv2.setVisibility(visible);
		mTvPingLunTimes.setVisibility(visible);
		mListView.setVisibility(visible);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wyjd_detail);
		ViewUtils.inject(this);
		mWorkListbean = (WyjdWorkListBean) getIntent().getSerializableExtra(WYJDActivity.KEY_WORKLISTBEAN);
		mAdapter = new CommentsAdapter(this);
		mListView.setAdapter(mAdapter);
		mScrollView.smoothScrollTo(0, 0);
        sendRequest();
	}
    @Override
    protected void sendRequest() {
        super.sendRequest();
		RequestParams params = new RequestParams();
		params.addBodyParameter("id", mWorkListbean.getId() + "");
        HttpUtil httpUtil =
                        new HttpUtil(new RequestHandler(WYJDDetailActivity.this, "", getResources()
                                        .getString(R.string.loading), 0));
        // 回调函数
        RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> result) {
                super.onSuccess(result);
                try {
                    WyjdDetailBean resultBean = mGson.fromJson(result.result, WyjdDetailBean.class);
                    if ("1".equals(resultBean.getResult())) {
                    	mWorkListbean = resultBean.getWork();
                    	// 评论列表
                    	mCmBeans = resultBean.getSupervise();
                    	updateUI();
                    } else {
                        showToast(resultBean.getErrorCode());
                    }
                } catch (Exception e) {
                	e.printStackTrace();
                    showToast(R.string.errorMsg);
                }
            }
        };
        httpUtil.post(WebConstants.METHOD_SUP_DETAIL, params, callback);
    }

    List<String> mPhotoList;
    
    protected void updateUI() {
        // 详情信息
        if (mWorkListbean != null) {
        	// 更新图片
        	if (!TextUtils.isEmpty(mWorkListbean.getPhoto())) {
        		mPhotoList = Arrays.asList(mWorkListbean.getPhoto().split(","));
        		ArrayList<String> ls = new ArrayList<String>();
        		for (String string : mPhotoList) {
        			ls.add(string);
        		}
        		mImgPic.setPicUrls(ls);
        		mImgPic.setOnPageViewClick(new OnPageViewClick() {
					
					@Override
					public void onPageViewClick(int postion) {
						ArrayList<String> photos = new ArrayList<>();
						for (String ps : mPhotoList) {
							photos.add(ps);
						}
						Bundle b = new Bundle();
						b.putStringArrayList(PhotoBrowseActivity.KEY_PIC_INFO, photos);
						startNextActivity(b, PhotoBrowseActivity.class);
					}
				});
        	}
        	
        	mTvTitle.setText(mWorkListbean.getContent());        	
        	mTvDate.setText(mWorkListbean.getReleaseTime());
        	mTvCatogery.setText(mWorkListbean.getTypes() == null ? "" : mWorkListbean.getTypes());
        	mTvCatogery.setBackgroundResource(BitmapAsset.convertBg(DiscTypesBean.getCatogeryType(mWorkListbean.getTypes())));
        	mTvViewCount.setText(mWorkListbean.getClicks()+"");
        	mTvZanTimes.setText(""+mWorkListbean.getClickPraiseCount());
        	mTvShareTimes.setText(""+(mWorkListbean.getShareQQCount()));// + mWorkListbean.getShareWXCount()));
        	
        	if(mWorkListbean.getIftalk() == 1){
        		setCommentsVisibilty(View.VISIBLE);
        		mTvPingLunTimes.setText("( "+mWorkListbean.getLeaveWordCount()+" )");
                if(mCmBeans != null && mCmBeans.size() > 0){
                    mTvPingLunTimes.setText("( "+Math.max(mCmBeans.size(), mWorkListbean.getLeaveWordCount())+" )");
                    mAdapter.setArrayList(mCmBeans);
                }
        	}else{
        		setCommentsVisibilty(View.INVISIBLE);
        	}
        }
    }

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void configActionBar() {
		mTopBackBtn.setVisibility(View.VISIBLE);
		mTopTitle.setText(R.string.activity_title_detail);
	}
    //是否已点赞，如果为是，再次点赞不请求，只提示
	protected boolean mHadZan = false;

	@OnClick({ R.id.btn_home_menu, R.id.btn_wyjd_detail_bottom_send,R.id.btn_wyjd_detail_zan, R.id.btn_wyjd_detail_share})
	public void OnClick(View v) {
		switch (v.getId()) {
		case R.id.btn_home_menu: // 点击返回按钮
			onBackPressed();
			break;
		case R.id.btn_wyjd_detail_bottom_send://评论
            String content = mEtPinglun.getText().toString().trim();
            if(TextUtils.isEmpty(content)){
                showToast("请输入评论内容");
                return;
            }
            sendCommentsRequest(content,"留言");
            mEtPinglun.setText("");
            InputMethodManager m=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
            m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS); 
		case R.id.btn_wyjd_detail_zan://点赞
        	if(mHadZan) {
        		showToast("您已经赞过了，到其他的地方继续赞吧");
        		return;
        	}
            sendCommentsRequest("","点赞");
		    break;
		case R.id.btn_wyjd_detail_share:
			share();
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
	// TODO Auto-generated method stub
	super.onActivityResult(arg0, arg1, arg2);
//	showToast("arg0="+arg0 + ", arg1=" + arg1 + ", arg2"+arg2.getAction());
	}
	
	private void share() {
//	    final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
//		mController.getConfig().removePlatform(SHARE_MEDIA.SINA, SHARE_MEDIA.TENCENT ); 
//		mController.setShareContent(mWorkListbean.getContent());
//		mController.setShareImage(new UMImage(WYJDDetailActivity.this, mPhotoList.get(0)));
//    	// 添加微信平台
//    	UMWXHandler wxHandler = new UMWXHandler(WYJDDetailActivity.this,WebConstants.WXAPP_APPID, WebConstants.WXAPP_APPSECRET);
//		wxHandler.setTitle("为海信社区服务大管家点赞");
//		wxHandler.setTargetUrl(WebConstants.SHARE_URL_WYJD + mWorkListbean.getId());
//		wxHandler.addToSocialSDK();
//    	// 添加微信朋友圈
//    	UMWXHandler wxCircleHandler = new UMWXHandler(WYJDDetailActivity.this,WebConstants.WXAPP_APPID, WebConstants.WXAPP_APPSECRET);
//		wxCircleHandler.setTitle(mWorkListbean.getContent());
//		wxCircleHandler.setTargetUrl(WebConstants.SHARE_URL_WYJD + mWorkListbean.getId());
//		wxCircleHandler.setToCircle(true);
//		wxCircleHandler.addToSocialSDK();
//		// 是否只有已登录用户才能打开分享选择页
//        mController.openShare(WYJDDetailActivity.this, false);
	}

	protected void sendCommentsRequest(final String content, final String types) {
        super.sendRequest();
        RequestParams params = new RequestParams();
        //findId 发现或者物业监督的 id 
        params.addBodyParameter("findId", mWorkListbean.getId() + "");
        //types 是类型  "留言"或者"点赞"
        params.addBodyParameter("types", types);
        //personId：登录用户id 
        params.addBodyParameter("personId", GCApplication.sApp.getUserInfo().getId()+"");
        //leaveWord：留言内容 
        params.addBodyParameter("leaveWord", content);
        //findType:发现或者物业监督本身带的类型的值（如：好人好事对应2）
        params.addBodyParameter("findType", DiscTypesBean.getCatogeryType(mWorkListbean.getTypes()));
        //find Or supervise
        params.addBodyParameter("leixing", "supervise");
        HttpUtil httpUtil =
                        new HttpUtil(new RequestHandler(WYJDDetailActivity.this, "", getResources()
                                        .getString(R.string.loading), 0));
        RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> result) {
                super.onSuccess(result);
                try {
                    BaseBean resultBean = mGson.fromJson(result.result, BaseBean.class);
                    if ("1".equals(resultBean.getResult())) {
                        if("留言".equals(types)){
                            CommentBean bean = new CommentBean();
                            bean.setLeaveWord(content);
                            bean.setPhoto(GCApplication.sApp.getUserInfo().getPhoto());
                            bean.setReleaseTime("刚刚");
                            mCmBeans.add(bean);
                            mAdapter.setArrayList(mCmBeans);
                            mTvPingLunTimes.setText(""+mCmBeans.size());
                        }else{
                            mTvZanTimes.setText((mWorkListbean.getClickPraiseCount() + 1)+"");
                            mHadZan = true;
                        }
                        showToast("成功"+types);
                    } else {
                        showToast(resultBean.getErrorCode());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast(R.string.errorMsg);
                }
            }
        };
        httpUtil.post(WebConstants.METHOD_DISC_DETAIL_COMMENTS, params, callback);
    }
    
	@Override
	public void requestCallBack(String dataJson, RequestType type) {

	}
}
