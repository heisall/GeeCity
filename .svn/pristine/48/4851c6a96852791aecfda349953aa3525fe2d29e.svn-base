package com.geecity.hisenseplus.home.activity.live;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.PhotoBrowseActivity;
import com.geecity.hisenseplus.home.activity.notice.EronllActivity;
import com.geecity.hisenseplus.home.adapter.CommentsAdapter;
import com.geecity.hisenseplus.home.bean.BaseBean;
import com.geecity.hisenseplus.home.bean.CommentBean;
import com.geecity.hisenseplus.home.bean.GoodsCommentBean;
import com.geecity.hisenseplus.home.bean.GoodsDetailBean;
import com.geecity.hisenseplus.home.bean.GoodsDetailResultBean;
import com.geecity.hisenseplus.home.bean.GoodsListBean;
import com.geecity.hisenseplus.home.bean.ShopCarForAddBean;
import com.geecity.hisenseplus.home.bean.ShopCarListBean;
import com.geecity.hisenseplus.home.utils.CommonTools;
import com.geecity.hisenseplus.home.utils.Const;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.geecity.hisenseplus.home.view.ATMDialog;
import com.geecity.hisenseplus.home.view.ATMDialog.DialogType;
import com.geecity.hisenseplus.home.view.ATMDialog.OnDialogListener;
import com.geecity.hisenseplus.home.view.ListViewForScrollView;
import com.geecity.hisenseplus.home.view.PicViewer;
import com.geecity.hisenseplus.home.view.PicViewer.OnPageViewClick;
import com.google.gson.Gson;
import com.ibill.lib.https.HttpUtil;
import com.ibill.lib.https.HttpUtil.RequestHttpCallBack;
import com.ibill.lib.tools.ScreenUtils;
import com.ibill.lib.webutils.RequestHandler;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 商品详情页
 * 
 * @author Administrator
 * 
 */
public class GoodsDetailActivity extends ActionBarActivity {

    public static final String KEY_DETAIL_BEAN = "key_detail_bean";
    public static final String KEY_IS_SCORE = "key_is_score";
    @ViewInject(R.id.btn_home_menu)
    private Button mTopBackBtn;
    @ViewInject(R.id.btn_goods_detail_collect)
    private Button mBtnCollect;
    @ViewInject(R.id.tv_home_topbar_title)
    private TextView mTopTitle;

    @ViewInject(R.id.pv_goods_detail_pics)
    private PicViewer mPvSmall;
    @ViewInject(R.id.tv_goods_detail_title)
    private TextView mTvTitle;
    @ViewInject(R.id.tv_goods_detail_prices_youh)
    private TextView mTvPriceYouH;
    @ViewInject(R.id.tv_goods_detail_price)
    private TextView mTvPriceShiC;
    @ViewInject(R.id.tv_goods_detail_count)
    private TextView mTvSalesCount;
    @ViewInject(R.id.tv_goods_detail_discp)
    private TextView mTvGoodsDiscp;

    @ViewInject(R.id.tv_goods_detail_info)
    private TextView mTvGoodsInfo;
    @ViewInject(R.id.tv_goods_detail_spcif)
    private TextView mTvGoodsSpcif;

    @ViewInject(R.id.tv_goods_detail_comments_count)
    private TextView mTvCommentsCount;
    @ViewInject(R.id.textview02)
    private TextView mTvCommentsHead;

    @ViewInject(R.id.btn_goods_detail_01)
    private Button mBtnBottom01;
    @ViewInject(R.id.btn_goods_detail_02)
    private Button mBtnBottom02;
    @ViewInject(R.id.btn_goods_detail_03)
    private Button mBtnBottom03;
    @ViewInject(R.id.btn_goods_detail_04)
    private Button mBtnBottom04;
    @ViewInject(R.id.btn_goods_detail_05)
    private Button mBtnBottom05;

    @ViewInject(R.id.rl_shopcar_count)
    private RelativeLayout mRtlyShopCarCount;
    @ViewInject(R.id.tv_goods_detail_shopcar_count)
    private TextView mTvShopcarCount;

    @ViewInject(R.id.lv_goods_detail_pinglun)
    private ListViewForScrollView mLvComments;

    @ViewInject(R.id.scrollView1)
    private ScrollView mScrollView;

    private GoodsListBean mGoodsListBean;
    private GoodsDetailBean mDetailBean;

    private CommentsAdapter mAdapterComments;
    private boolean mIsCollected;

    // 是否为积分产品，如果是，价格显示为积分，底部只显示积分兑换按钮
    private boolean mIsScore;
    protected LinkedList<CommentBean> mCommentList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        ViewUtils.inject(this);
        mPvSmall.getLayoutParams().height = ScreenUtils.getScreenWidth(this);
        mPvSmall.setOnPageViewClick(new OnPageViewClick() {

            @Override
            public void onPageViewClick(int postion) {
                Bundle b = new Bundle();
                b.putStringArrayList(PhotoBrowseActivity.KEY_PIC_INFO, mTopPics);
                startNextActivity(b, PhotoBrowseActivity.class);
            }
        });
        mGoodsListBean = (GoodsListBean) getIntent().getSerializableExtra(KEY_DETAIL_BEAN);
        mIsScore = getIntent().getBooleanExtra(KEY_IS_SCORE, false);
        mAdapterComments = new CommentsAdapter(this);
        mLvComments.setAdapter(mAdapterComments);
        sendRequest();
    }

    @Override
    protected void sendRequest() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("account", GCApplication.sApp.getUserInfo().getAccount());
        params.addBodyParameter("goods_id", mGoodsListBean.getGoods_id() + "");
        HttpUtil httpUtil =
                        new HttpUtil(new RequestHandler(GoodsDetailActivity.this, "",
                                        getResources().getString(R.string.loading), 0));
        // 回调函数
        RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> result) {
                super.onSuccess(result);
                try {
                    GoodsDetailResultBean resultBean =
                                    mGson.fromJson(result.result, GoodsDetailResultBean.class);
                    if ("1".equals(resultBean.getResult())) {
                        mDetailBean = resultBean.getGoods();
                        updateUI();
                        updateShopCarCount(resultBean.getCounts());
                        // 转换评论列表
                        LinkedList<GoodsCommentBean> beans = resultBean.getComments();
                        if (beans != null) {
                            if (beans.size() > 0) {
                                mTvCommentsCount.setText("(" + beans.size() + "人参与评论)");
                            } else {
                                mTvCommentsCount.setText("(暂时还没有评论)");
                            }
                            LinkedList<CommentBean> cms = new LinkedList<>();
                            for (GoodsCommentBean gcb : beans) {
                                CommentBean cb = new CommentBean();
                                cb.setNickName(gcb.getBuyer_name());
                                // TODO 头像没有
                                cb.setPhoto("");
                                cb.setLeaveWord(gcb.getComment());
                                cb.setReleaseTime(gcb.getEvaluation_time());
                                mCommentList.add(cb);
                                if (cms.size() < 5) {
                                    cms.add(cb);
                                }
                            }
                            mAdapterComments.setArrayList(cms);
                        } else {}
                    } else {
                        showToast(resultBean.getErrorCode());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast(R.string.errorMsg);
                }
            }
        };
        httpUtil.post(WebConstants.METHOD_LIFE_GOODS_ETAIL, params, callback);
    }

    private ArrayList<String> mPics;
    private ArrayList<String> mTopSmallPics;
    private ArrayList<String> mTopPics;

    protected void updateUI() {
        if (mDetailBean == null) return;
        mTvTitle.setText(mDetailBean.getGoods_name());

        if (!TextUtils.isEmpty(mDetailBean.getGoods_img_small())) {
            List<String> pics = Arrays.asList(mDetailBean.getGoods_img_small().split(","));
            if (pics != null) {
                mTopSmallPics = new ArrayList<>();
                for (String string : pics) {
                    mTopSmallPics.add(string);
                }
                mPvSmall.setPicUrls(mTopSmallPics);
            }
        }

        if (!TextUtils.isEmpty(mDetailBean.getGoods_img())) {
            List<String> pics = Arrays.asList(mDetailBean.getGoods_img().split(","));
            if (pics != null) {
                mTopPics = new ArrayList<>();
                for (String string : pics) {
                    mTopPics.add(string);
                }
            }
        }

        if (mIsScore) {
            mTvPriceYouH.setText(mDetailBean.getPrice() + "积分");
        } else {
            mTvPriceYouH.setText("￥" + mDetailBean.getPrice());
        }
        mTvPriceShiC.setText("￥" + mDetailBean.getOld_price());
        mTvSalesCount.setText("" + mDetailBean.getSales());
        mTvGoodsDiscp.setText(mDetailBean.getSimple_desc());

        if (!TextUtils.isEmpty(mDetailBean.getDescription())) {
            List<String> pics = Arrays.asList(mDetailBean.getDescription().split(","));
            if (pics != null) {
                initGallgry(pics);
            }
        }

        String orderType = mDetailBean.getOrder_type();
        if (mIsScore) {
            mBtnBottom04.setText("立即兑换");
            mBtnBottom04.setVisibility(View.VISIBLE);
        } else {
            // 1：拨打电话 2：优惠政策 3: 在线预约 4：在线订购
            if (orderType.contains("拨打电话")) {
                mBtnBottom01.setVisibility(View.VISIBLE);
            }

            if (orderType.contains("在线预约")) {
                mBtnBottom03.setVisibility(View.VISIBLE);
            }

            if (orderType.contains("立即购买")) {
                mBtnBottom04.setVisibility(View.VISIBLE);
                mBtnBottom05.setVisibility(View.VISIBLE);
                mRtlyShopCarCount.setVisibility(View.VISIBLE);
            }

            if (orderType.contains("优惠政策")) {
                mBtnBottom02.setVisibility(View.VISIBLE);
            }
        }

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                mScrollView.scrollTo(0, 0);
            }
        }, 500);
    }

    private void initGallgry(List<String> pics) {
        mPics = new ArrayList<>();
        for (int i = 0; i < pics.size(); i++) {
            final String bean = pics.get(i);
            if (bean == null) {
                continue;
            }
            mPics.add(bean);
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

    private static final int REQUEST_CODE = 0x01;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != REQUEST_CODE) {
            return;
        }
        switch (resultCode) {
            case Activity.RESULT_CANCELED:

                break;
            case Activity.RESULT_OK:
                String phone = data.getStringExtra(EronllActivity.ERONLL_PHONE);
                String name = data.getStringExtra(EronllActivity.ERONLL_NAME);
                sendOrderOnLine(phone, name);
                break;
            default:
                break;
        }
    }

    private void sendOrderOnLine(final String phone, final String name) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("storeId", mDetailBean.getStore_id() + "");
        params.addBodyParameter("goodsId", mDetailBean.getGoods_id() + "");
        params.addBodyParameter("account", GCApplication.sApp.getUserInfo().getAccount());
        params.addBodyParameter("contactPreson", name);
        params.addBodyParameter("phone", phone);
        HttpUtil httpUtil =
                        new HttpUtil(new RequestHandler(GoodsDetailActivity.this, "",
                                        getResources().getString(R.string.loading), 0));
        // 回调函数
        RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> result) {
                super.onSuccess(result);
                try {
                    BaseBean resb = mGson.fromJson(result.result, BaseBean.class);
                    if (Const.SUCCESS.equals(resb.getResult())) {
                        showToast("恭喜您已成功预约");

                        sendMessageRequest(name, phone);
                    } else {
                        showToast(resb.getErrorCode());
                    }
                } catch (Exception e) {
                    showToast(R.string.errorMsg);
                }
            }
        };
        httpUtil.put(WebConstants.METHOD_ORDER_BOOKONLINE, params, callback);
    }

    // 《信我家》预约信息：预约产品：XXXX，张三，18660228266.
    protected void sendMessageRequest(String name, String phone) {
        LogUtils.d(mDetailBean.getTel());
        HttpUtils http = new HttpUtils();
        String url = null;
        try {
            url =
                            "http://dx.qxtsms.cn/sms.aspx?action="
                                            + "send&userid=2735&account=hisenseplus&password=hisenseplus&mobile="
                                            + URLEncoder.encode(mDetailBean.getTel(), "UTF-8")
                                            + "&content=【信我家】预约信息：预约产品:"
                                            + URLEncoder.encode(mDetailBean.getGoods_name(),
                                                            "UTF-8") + "," + name + "," + phone
                                            + "&sendTime=&checkcontent=1";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(url)) {
            return;
        }
        http.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                // showToast("正在发送");
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                showToast("预约短信已发送，商家稍后会联系您");
            }

            @Override
            public void onStart() {}

            @Override
            public void onFailure(HttpException error, String msg) {}
        });
    }


    @SuppressLint("NewApi")
    @OnClick({R.id.rl_shopcar_count, R.id.btn_home_menu, R.id.btn_goods_detail_01,
                    R.id.btn_goods_detail_02, R.id.btn_goods_detail_03, R.id.btn_goods_detail_04,
                    R.id.btn_goods_detail_05, R.id.btn_goods_detail_collect,
                    R.id.tv_goods_detail_info, R.id.tv_goods_detail_spcif,
                    R.id.tv_goods_detail_comments_count, R.id.textview02})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_home_menu: // 点击返回按钮
                onBackPressed();
                break;
            case R.id.btn_goods_detail_01:// 拨打电话
                CommonTools.launchCall(GoodsDetailActivity.this, mDetailBean.getTel());
                break;
            case R.id.btn_goods_detail_02:// 优惠政策
                final ATMDialog dialog =
                                new ATMDialog(GoodsDetailActivity.this, DialogType.ONE_BUTTON);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.setDesc(mDetailBean.getPolicy());
                dialog.setOnDialogLister(new OnDialogListener() {
                    @Override
                    public void onConfirm() {
                        dialog.dismiss();
                    }

                    @Override
                    public void onCancle() {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            case R.id.btn_goods_detail_03: {// 在线预约
                if (mDetailBean.getStock() <= 0) {
                    showToast("该商品暂无可预约数量");
                    return;
                }
                Bundle b = new Bundle();
                b.putInt(EronllActivity.KEY_TITLE, R.string.actv_title_order_online);
                startNextActivityForResult(b, EronllActivity.class, REQUEST_CODE);
            }
                break;
            case R.id.btn_goods_detail_04: {// 立即结算和积分兑换
                if (mDetailBean.getStock() <= 0) {
                    showToast("该商品暂无可售数量");
                    return;
                }
                if (mIsScore) {
                    // 积分不足不能兑换
                    if (GCApplication.sApp.getUserInfo().getJifen() < mDetailBean.getPrice()) {
                        showToast("您的积分不足，可以坚持签到获取更多积分后再来");
                        return;
                    }

                    final ATMDialog dialog1 =
                                    new ATMDialog(GoodsDetailActivity.this, DialogType.TWO_BUTTON);
                    dialog1.setDesc("将使用您相应的积分，是否立即兑换？");
                    dialog1.setOnDialogLister(new OnDialogListener() {

                        @Override
                        public void onConfirm() {
                            dialog1.dismiss();
                            luncherOrderCommitActivity();
                        }

                        @Override
                        public void onCancle() {
                            dialog1.dismiss();
                            return;
                        }
                    });
                    dialog1.show();
                } else {
                    luncherOrderCommitActivity();
                }
            }
                break;
            case R.id.btn_goods_detail_05:// 加入购物车
                if (mDetailBean.getStock() <= 0) {
                    showToast("该商品暂无可售数量");
                    return;
                }
                sendAddShopCarRequest();
                break;
            case R.id.btn_goods_detail_collect:// 收藏
                if (mIsCollected) {
                    mBtnCollect.setBackground(getResources().getDrawable(
                                    R.drawable.icn_un_collected));
                    mIsCollected = false;
                } else {
                    mBtnCollect.setBackground(getResources().getDrawable(R.drawable.icn_collected));
                    mIsCollected = true;
                }
                break;
            case R.id.rl_shopcar_count:
                startNextActivity(null, ShopCarActivity.class);
                break;
            case R.id.tv_goods_detail_info: {
                Bundle b = new Bundle();
                b.putStringArrayList(GoodsDetailPicsActivity.KEY_PICS_LIST, mPics);
                startNextActivity(b, GoodsDetailPicsActivity.class);
            }
                break;
            case R.id.tv_goods_detail_spcif: {
                // TODO 进入规格参数
                Bundle b = new Bundle();
                b.putString(GoodsDetailSpecifActivity.KEY_GG_DESC, mDetailBean.getGg_desc());
                startNextActivity(b, GoodsDetailSpecifActivity.class);
            }
                break;
            case R.id.textview02:
            case R.id.tv_goods_detail_comments_count: {
                // 评论列表
                String coms = mGson.toJson(mCommentList);
                Bundle b = new Bundle();
                b.putString(GoodsCommentsActivity.KEY_TYPE, coms);
                startNextActivity(b, GoodsCommentsActivity.class);
            }
            default:
                break;
        }
    }


    protected void luncherOrderCommitActivity() {
        ArrayList<ShopCarListBean> selectBeans = new ArrayList<>();
        ShopCarListBean bean = new ShopCarListBean();
        // 购物车-100标识来自直接购买，调用直接购买的接口
        bean.setRec_id(-100);
        bean.setGoods_id(mDetailBean.getGoods_id());
        bean.setGoods_image(mDetailBean.getDefault_image());
        bean.setGoods_name(mDetailBean.getGoods_name());
        bean.setPrice(mDetailBean.getPrice());
        bean.setQuantity(1);
        bean.setStore_id(mDetailBean.getStore_id());
        bean.setScore(mIsScore ? 1 : 0);
        LogUtils.d(mIsScore + " -- " + bean.toString());
        selectBeans.add(bean);
        Bundle b = new Bundle();
        b.putParcelableArrayList(OrderCommitActivity.SHOPCAR_LIST, selectBeans);
        startNextActivity(b, OrderCommitActivity.class);
    }

    private void sendAddShopCarRequest() {
        RequestParams params = new RequestParams();
        ShopCarForAddBean scBean = getShopCarForAdd();
        LogUtils.d(new Gson().toJson(scBean));
        params.addBodyParameter("cart", new Gson().toJson(scBean));
        HttpUtil httpUtil =
                        new HttpUtil(new RequestHandler(GoodsDetailActivity.this, "",
                                        getResources().getString(R.string.loading), 0));
        // 回调函数
        RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> result) {
                super.onSuccess(result);
                try {
                    BaseBean resultBean = mGson.fromJson(result.result, BaseBean.class);
                    if (Const.SUCCESS.equals(resultBean.getResult())) {
                        showToast("已加入购物车");
                        String countStr = mTvShopcarCount.getText().toString();
                        int count = 0;
                        if (TextUtils.isEmpty(countStr)) {
                            count = 1;
                        } else {
                            count = Integer.parseInt(countStr) + 1;
                        }
                        updateShopCarCount(count);
                    } else {
                        showToast(resultBean.getErrorCode());
                    }
                } catch (Exception e) {
                    showToast(R.string.errorMsg);
                }
            }
        };
        httpUtil.put(WebConstants.METHOD_ORDER_ADDCART, params, callback);
    }

    protected void updateShopCarCount(int count) {
        if (count > 99) {
            mTvShopcarCount.setVisibility(View.VISIBLE);
            mTvShopcarCount.setText("99+");
        } else if (count == 0) {
            mTvShopcarCount.setVisibility(View.INVISIBLE);
            mTvShopcarCount.setText("");
        } else {
            mTvShopcarCount.setVisibility(View.VISIBLE);
            mTvShopcarCount.setText("" + count);
        }
    }

    private ShopCarForAddBean getShopCarForAdd() {
        ShopCarForAddBean scb = new ShopCarForAddBean();
        scb.setAccount(GCApplication.sApp.getUserInfo().getAccount());
        scb.setCounts("1");
        scb.setFlg("1");
        scb.setGoodsId(mDetailBean.getGoods_id() + "");
        scb.setStoreId(mDetailBean.getStore_id() + "");
        scb.setUnitPrice(String.valueOf(mDetailBean.getPrice()));
        return scb;
    }

    @Override
    public void requestCallBack(String dataJson, RequestType type) {}
}
