package com.geecity.hisenseplus.home.activity.disc;

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
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.PhotoBrowseActivity;
import com.geecity.hisenseplus.home.adapter.CommentsAdapter;
import com.geecity.hisenseplus.home.bean.BaseBean;
import com.geecity.hisenseplus.home.bean.CommentBean;
import com.geecity.hisenseplus.home.bean.DiscDetailBaseBean;
import com.geecity.hisenseplus.home.bean.DiscDetailBean;
import com.geecity.hisenseplus.home.bean.DiscDetailResultBean;
import com.geecity.hisenseplus.home.bean.DiscListBean;
import com.geecity.hisenseplus.home.bean.DiscTypesBean;
import com.geecity.hisenseplus.home.utils.BitmapAsset;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.geecity.hisenseplus.home.view.ListViewForScrollView;
import com.geecity.hisenseplus.home.view.PicViewer;
import com.geecity.hisenseplus.home.view.PicViewer.OnPageViewClick;
import com.geecity.hisenseplus.home.view.RoundImageView;
import com.geecity.hisenseplus.home.view.ShareDialog;
import com.geecity.hisenseplus.home.view.pull.PullToRefreshLayout;
import com.geecity.hisenseplus.home.view.pull.PullToRefreshLayout.OnRefreshListener;
import com.geecity.hisenseplus.home.view.pull.PullableScrollView;
import com.ibill.lib.https.HttpUtil;
import com.ibill.lib.https.HttpUtil.RequestHttpCallBack;
import com.ibill.lib.webutils.RequestHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class DiscDetailActivity extends ActionBarActivity implements OnRefreshListener{

  public static final String KEY_DISCLIST = "discBean";
  @ViewInject(R.id.btn_home_menu)
  private Button mTopBackBtn;
  @ViewInject(R.id.tv_home_topbar_title)
  private TextView mTopTitle;

  @ViewInject(R.id.img_disc_detail_head)
  private RoundImageView mImgHead;
  @ViewInject(R.id.tv_disc_detail_nickname)
  private TextView mTvNickname;

  @ViewInject(R.id.sc_disc_detail)
  private PullableScrollView mScrollView;

  @ViewInject(R.id.tv_disc_detail_title)
  private TextView mTvTitle;
  @ViewInject(R.id.tv_disc_detail_date)
  private TextView mTvDate;
  @ViewInject(R.id.tv_disc_detail_catogery)
  private TextView mTvCatogery;

  @ViewInject(R.id.img_disc_detail_pic)
  private PicViewer mImgPic;

  @ViewInject(R.id.btn_disc_detail_pinglun)
  private Button mBtnPingLun;
  @ViewInject(R.id.tv_disc_detail_view_count)
  private TextView mTvViewCount;
  @ViewInject(R.id.tv_disc_detail_pinglun)
  private TextView mTvPingLunTimes;
  @ViewInject(R.id.btn_disc_detail_zan)
  private Button mBtnZan;
  @ViewInject(R.id.tv_disc_detail_zan)
  private TextView mTvZanTimes;
  @ViewInject(R.id.btn_disc_detail_share)
  private Button mBtnShare;
  @ViewInject(R.id.tv_disc_detail_share)
  private TextView mTvShareTimes;

  @ViewInject(R.id.lv_disc_detail_pinglun)
  private ListViewForScrollView mListView;

  @ViewInject(R.id.et_disc_detail_bottom_pinglun)
  private EditText mEtPinglun;
  @ViewInject(R.id.btn_disc_detail_bottom_send)
  private Button mBtnSend;

  private CommentsAdapter mAdapter;
  private DiscListBean mSourceBean;
  
  @ViewInject(R.id.refresh_view)
  private PullToRefreshLayout mPullToRefreshLayout;
  private int mCurrentPage = 0;
  private int mRequestPage = 0;
  private static final int PAGE_MAX_COUNT = 5;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_disc_detail);
    ViewUtils.inject(this);
    mSourceBean = (DiscListBean) getIntent().getSerializableExtra(KEY_DISCLIST);
    mAdapter = new CommentsAdapter(this);
    mListView.setAdapter(mAdapter);
    mScrollView.smoothScrollTo(0, 0);
    mPullToRefreshLayout.setOnRefreshListener(this);
    onRefresh(mPullToRefreshLayout);
  }

  private DiscDetailBaseBean mDetailBean;

  protected void sendRequest(int index) {
    // TODO Auto-generated method stub
    super.sendRequest();
    RequestParams params = new RequestParams();
    params.addBodyParameter("id", mSourceBean.getId() + "");
    params.addBodyParameter("pageindex",  index + "");//第几页 String,从0开始
    params.addBodyParameter("countperpage", PAGE_MAX_COUNT + "");
    HttpUtil httpUtil =
        new HttpUtil(new RequestHandler(DiscDetailActivity.this, "", getResources().getString(
            R.string.loading), 0));
    // 回调函数
    RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
      @Override
      public void onSuccess(ResponseInfo<String> result) {
        super.onSuccess(result);
        mPullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
        try {
          DiscDetailResultBean resultBean =
              mGson.fromJson(result.result, DiscDetailResultBean.class);
          if ("1".equals(resultBean.getResult())) {
            mDetailBean = resultBean.getData();
            updateUI();
          } else {
            showToast(resultBean.getErrorCode());
          }
        } catch (Exception e) {
          e.printStackTrace();
          showToast(R.string.errorMsg);
        }
      }
      
      @Override
        public void onFailure(HttpException arg0, String arg1) {
            // TODO Auto-generated method stub
            super.onFailure(arg0, arg1);
            mPullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
        }
    };
    // 发送数据 post方式（提交保存）
    httpUtil.post(WebConstants.METHOD_DISC_DETAIL, params, callback);
  }

  List<String> mPhotoList;

  protected void updateUI() {
    // 详情信息
    DiscDetailBean dBean = mDetailBean.getFind();
    if (dBean != null) {
      // 更新图片
      if (!TextUtils.isEmpty(dBean.getPhoto())) {
        mPhotoList = Arrays.asList(dBean.getPhoto().split(","));
        ArrayList<String> ls = new ArrayList<String>();
        for (String string : mPhotoList) {
          ls.add(string);
        }
        mImgPic.setPicUrls(ls);
        mImgPic.setOnPageViewClick(new OnPageViewClick() {

          @Override
          public void onPageViewClick(int postion) {
            ArrayList<String> photos = new ArrayList<String>();
            for (String string : mPhotoList) {
              photos.add(string);
            }
            Bundle b = new Bundle();
            b.putStringArrayList(PhotoBrowseActivity.KEY_PIC_INFO, photos);
            startNextActivity(b, PhotoBrowseActivity.class);
          }
        });
      }
      BitmapAsset.displayImg(DiscDetailActivity.this, mImgHead, dBean.getUserP(),
          R.drawable.icon_default_head);
      mTvNickname.setText(TextUtils.isEmpty(dBean.getNickName()) ? "" : dBean.getNickName());
      mTvTitle.setText(dBean.getContent());
      mTvDate.setText(dBean.getReleaseTime());
      mTvCatogery.setText(dBean.getMemo() == null ? "" : dBean.getMemo());
      mTvCatogery.setBackgroundResource(BitmapAsset.convertBg(dBean.getTypes()));
      mTvPingLunTimes.setText("( " + dBean.getLeaveWordCount() + " )");
      mTvViewCount.setText(dBean.getLeaveWordCount() + "");
      mTvZanTimes.setText("" + dBean.getClickPraiseCount());
      mTvShareTimes.setText("" + (dBean.getShareQQCount() + dBean.getShareWXCount()));
    }
    // 评论列表
    LinkedList<CommentBean> list = mDetailBean.getComments();
    if (list != null && list.size() > 0) {
        mCurrentPage = mRequestPage;
        mTvPingLunTimes.setText("( " + Math.max(mCmBeans.size(), dBean.getLeaveWordCount()) + " )");
      if(mCurrentPage == 0){
          mCmBeans.clear();
          mCmBeans = list;
      }else{
          mCmBeans.addAll(list);
      }
      mAdapter.setArrayList(mCmBeans);
    }
  }

  LinkedList<CommentBean> mCmBeans = new LinkedList<CommentBean>();
  // 是否已点赞，如果为是，再次点赞不请求，只提示
  protected boolean mHadZan = false;

  @Override
  protected void onResume() {
    super.onResume();
  }

  @Override
  public void configActionBar() {
    mTopBackBtn.setVisibility(View.VISIBLE);
    mTopTitle.setText(R.string.activity_title_detail);
  }

  @OnClick({R.id.btn_home_menu, R.id.btn_disc_detail_bottom_send, R.id.btn_disc_detail_zan,
      R.id.btn_disc_detail_share})
  public void OnClick(View v) {
    switch (v.getId()) {
      case R.id.btn_home_menu: // 点击返回按钮
        onBackPressed();
        break;
      case R.id.btn_disc_detail_bottom_send:// 评论
        String content = mEtPinglun.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
          showToast("请输入评论内容");
          return;
        }
        sendCommentsRequest(content, "留言");
        mEtPinglun.setText("");
        InputMethodManager m = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        break;
      case R.id.btn_disc_detail_zan:
        if (mHadZan) {
          showToast("您已经赞过了，到其他的地方继续赞吧");
          return;
        }
        sendCommentsRequest("", "点赞");
        break;
      case R.id.btn_disc_detail_share:
        share();
        break;
      default:
        break;
    }
  }

  private void share() {
      ShareDialog dialog = new ShareDialog(DiscDetailActivity.this, WebConstants.SHARE_URL_DISC + mDetailBean.getFind().getId(),
          "我在社区有个非常精彩的发现", mDetailBean.getFind().getContent());
      dialog.show();
//    final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
//    mController.getConfig().removePlatform(SHARE_MEDIA.SINA, SHARE_MEDIA.TENCENT);
//    // 在微信好友分享时显示在标题下边，图片的右边，但在朋友圈中不显示
//    mController.setShareContent(mDetailBean.getFind().getContent());
//    mController.setShareImage(new UMImage(DiscDetailActivity.this, mPhotoList.get(0)));
//    // 添加微信平台
//    UMWXHandler wxHandler = new UMWXHandler(DiscDetailActivity.this, WebConstants.WXAPP_APPID, WebConstants.WXAPP_APPSECRET);
//    wxHandler.setTitle("我在社区有个非常精彩的发现");
//    wxHandler.setTargetUrl(WebConstants.SHARE_URL_DISC + mDetailBean.getFind().getId());
//    wxHandler.addToSocialSDK();
//    // 添加微信朋友圈
//    UMWXHandler wxCircleHandler = new UMWXHandler(DiscDetailActivity.this, WebConstants.WXAPP_APPID, WebConstants.WXAPP_APPSECRET);
//    // 显示在朋友圈标题中
//    wxCircleHandler.setTitle(mDetailBean.getFind().getContent());
//    wxCircleHandler.setTargetUrl(WebConstants.SHARE_URL_DISC + mDetailBean.getFind().getId());
//    wxCircleHandler.setToCircle(true);
//    wxCircleHandler.addToSocialSDK();
//    // 是否只有已登录用户才能打开分享选择页
//    mController.openShare(DiscDetailActivity.this, false);
  }

  @Override
  protected void onActivityResult(int arg0, int arg1, Intent arg2) {
    // TODO Auto-generated method stub
    super.onActivityResult(arg0, arg1, arg2);
//    showToast("arg0=" + arg0 + ", arg1=" + arg1 + ", arg2" + arg2.getAction());
  }

  protected void sendCommentsRequest(final String content, final String types) {
    super.sendRequest();
    RequestParams params = new RequestParams();
    LogUtils.d("find   " + content + "  id=" + mDetailBean.getFind().getId() + "  " + types
        + "  personId=" + GCApplication.sApp.getUserInfo().getId() + "  findType="
        + DiscTypesBean.getCatogeryType(mDetailBean.getFind().getTypes()) + "  ");
    // find 圣诞快乐 22 留言 36 1

    // findId 发现或者物业监督的 id
    params.addBodyParameter("findId", mDetailBean.getFind().getId() + "");
    // types 是类型 "留言"或者"点赞"
    params.addBodyParameter("types", types);
    // personId：登录用户id
    params.addBodyParameter("personId", GCApplication.sApp.getUserInfo().getId() + "");
    // leaveWord：留言内容
    params.addBodyParameter("leaveWord", content);
    // findType:发现或者物业监督本身带的类型的值（如：好人好事对应2）
    params.addBodyParameter("findType",
        DiscTypesBean.getCatogeryType(mDetailBean.getFind().getTypes()));
    // find Or supervise
    params.addBodyParameter("leixing", "find");
    HttpUtil httpUtil =
        new HttpUtil(new RequestHandler(DiscDetailActivity.this, "", getResources().getString(
            R.string.loading), 0));
    // 回调函数
    RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
      @Override
      public void onSuccess(ResponseInfo<String> result) {
        super.onSuccess(result);
        try {
          BaseBean resultBean = mGson.fromJson(result.result, BaseBean.class);
          if ("1".equals(resultBean.getResult())) {
            showToast("成功" + types);
            if ("留言".equals(types)) {
              CommentBean bean = new CommentBean();
              bean.setLeaveWord(content);
              bean.setPhoto(GCApplication.sApp.getUserInfo().getPhoto());
              bean.setReleaseTime("刚刚");
              mCmBeans.add(bean);
              mAdapter.setArrayList(mCmBeans);
              mTvPingLunTimes.setText("" + mCmBeans.size());
            } else {
              mTvZanTimes.setText((mDetailBean.getFind().getClickPraiseCount() + 1) + "");
              mHadZan = true;
            }
          } else {
            showToast(resultBean.getErrorCode());
          }
        } catch (Exception e) {
          e.printStackTrace();
          showToast(R.string.errorMsg);
        }
      }
    };
    // 发送数据 post方式（提交保存）
    httpUtil.post(WebConstants.METHOD_DISC_DETAIL_COMMENTS, params, callback);
  }

  @Override
  public void requestCallBack(String dataJson, RequestType type) {

  }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        mRequestPage = 0;
        sendRequest(mRequestPage);
    }
    
    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        mRequestPage = mCurrentPage + 1;
        sendRequest(mRequestPage);
    }
}
