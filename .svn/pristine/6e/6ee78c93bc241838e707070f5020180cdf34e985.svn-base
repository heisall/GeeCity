package com.geecity.hisenseplus.home.fragment;

import java.util.LinkedList;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.geecity.hisenseplus.home.BaseFragment;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.HomeActivity;
import com.geecity.hisenseplus.home.activity.disc.DiscAddActivity;
import com.geecity.hisenseplus.home.activity.notice.NoticeActivity;
import com.geecity.hisenseplus.home.adapter.BaseSelectAdapter;
import com.geecity.hisenseplus.home.adapter.DiscListAdapter;
import com.geecity.hisenseplus.home.bean.DiscListBaseBean;
import com.geecity.hisenseplus.home.bean.DiscListBean;
import com.geecity.hisenseplus.home.bean.DiscTypesBean;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.geecity.hisenseplus.home.view.XListView;
import com.geecity.hisenseplus.home.view.XListView.IXListViewListener;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
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

/**
 * 发现
 * 
 * @ClassName: Home3DiscFragment
 * @Description: TODO
 * @author billkong
 */
public class Home3DiscFragment extends BaseFragment implements IXListViewListener{

	private static final int COUNT_PER_PAGE = 20;
	@ViewInject(R.id.btn_disco_huodong)
	private Button mBtnActiv;
	@ViewInject(R.id.btn_disco_add)
	private Button mBtnAdd; 
	
	@ViewInject(R.id.tv_disc_info_catogery)
	private TextView mTvCatogery;
	@ViewInject(R.id.line_pop_top)
	private TextView mTvLine;
	
	@ViewInject(R.id.lv_discovery)
	private XListView mList;
	private DiscListAdapter mAdpter;
	private LinkedList<DiscTypesBean> mDiscTypesBeans = new LinkedList<DiscTypesBean>();
	private LinkedList<DiscListBean> mDiscListBeans = new LinkedList<DiscListBean>();

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frgmt_home_3discovery, container,
				false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mAdpter = new DiscListAdapter(getActivity());
		mList.setXListViewListener(this);
		mList.setAdapter(mAdpter);
		sendRequest();
		initPop();
	}

	@OnClick({R.id.btn_disco_add,R.id.btn_disco_huodong, R.id.tv_disc_info_catogery})
	private void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_disco_add:
			startActivity(new Intent(getActivity(), DiscAddActivity.class));
			break;
		case R.id.btn_disco_huodong://社区活动
			Bundle b = new Bundle();
			b.putInt(NoticeActivity.KEY_TYPE, NoticeActivity.TYPE_LIVE);
			((HomeActivity)getActivity()).startNextActivity(b, NoticeActivity.class);
			break;
		case R.id.tv_disc_info_catogery://信息分类
			mPopType.showAsDropDown(mTvLine);
			break;
		default:
			break;
		}
	}	

	private PopupWindow mPopType;

	private BaseSelectAdapter mSelectAdapter;
	private DiscTypesBean mDiscType = new DiscTypesBean("", "不限");
	
	private void initPop() {

		mSelectAdapter = new BaseSelectAdapter(getActivity(), true);
		mSelectAdapter.setList(mDiscTypesBeans);

		LinearLayout layout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(
				R.layout.spinner_down, null);
		ListView listView = (ListView) layout.findViewById(R.id.list_spinner);
		listView.setSelector(R.color.txt_white);
		listView.setAdapter(mSelectAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mDiscType = mDiscTypesBeans.get(position);
				mTvCatogery.setText(mDiscType.getMemo());
				mPopType.dismiss();
				onRefresh();
			}
		});
		mPopType = new PopupWindow(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT) {
			@Override
			public void showAsDropDown(View anchor) {
				super.showAsDropDown(anchor);
			}

			@Override
			public void dismiss() {
				super.dismiss();
			}
		};
		mPopType.setOutsideTouchable(true);
		mPopType.setFocusable(true);
		mPopType.setContentView(layout);
		mPopType.setBackgroundDrawable(new ColorDrawable());
		mPopType.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
			}
		});
		mSelectAdapter.notifyDataSetChanged();
	}
	
	private int mPageIndex = 0;

	/**
	 * 获取服务器数据
	 * 
	 * @Title: getData void
	 */
	private void sendRequest() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("pageindex", mPageIndex+"");
		params.addBodyParameter("countperpage", COUNT_PER_PAGE+"");
        params.addBodyParameter("a_id", GCApplication.sApp.getDefaultAreaInfo().getA_id());
        params.addBodyParameter("types", mDiscType.getDictValue().replace(".0", "").replace("-1", ""));
		HttpUtil httpUtil = new HttpUtil(new RequestHandler(getActivity(), "",
				getResources().getString(R.string.loading), 0));
		// 回调函数
		RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> result) {
				super.onSuccess(result);
				mList.stopRefresh();
				mList.stopLoadMore();
				try {
					Gson mGson = new Gson();
					DiscListBaseBean bean = mGson.fromJson(result.result,DiscListBaseBean.class);
					//发现分类
					DiscTypesBean defaultBean = new DiscTypesBean("-1", "全部");
					mDiscTypesBeans.clear();
					mDiscTypesBeans.add(defaultBean);
					mDiscTypesBeans.addAll(bean.getData().getTypes());
					for (DiscTypesBean types : mDiscTypesBeans) {
                        LogUtils.d(types.toString());
                    }
                    if (mDiscTypesBeans != null && mDiscTypesBeans.size() > 0) {
                        mSelectAdapter.setList(mDiscTypesBeans);
                        // 更新分类列表，以便于在发布新增页面显示分类
                        GCApplication.sApp.setDiscTypes(mDiscTypesBeans);
                    }
                    //发现列表
                    LinkedList<DiscListBean> list = bean.getData().getMessage();
					for (DiscListBean lists : list) {
					    LogUtils.d(lists.toString());
                    }
                    if (list != null && list.size() > 0) {
                        if (list.size() == COUNT_PER_PAGE) {
                            mList.setPullLoadEnable(true);
                        } else {
                            mList.setPullLoadEnable(false);
                        }
                        if (mPageIndex > 0) {
                            mDiscListBeans.addAll(list);
                        }else{
                            mDiscListBeans = list;
                        }
                        mAdpter.setArrayList(mDiscListBeans);
                        mPageIndex++;
                    }else{
                      showToast("暂时没有发现任何情况");
                    }
				} catch (JsonSyntaxException e) {
				    e.printStackTrace();
				} catch (Exception e) {
				    e.printStackTrace();
                }
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				super.onFailure(arg0, arg1);
                mList.stopRefresh();
                mList.stopLoadMore();
			}

		};
		// 发送数据 post方式（提交保存）
		httpUtil.post(WebConstants.METHOD_DISC_LIST, params, callback);
	}

	public boolean onBackPressed() {
		return false;
	}

    @Override
    public void onRefresh() {
        mPageIndex = 0;
        sendRequest();
    }

    @Override
    public void onLoadMore() {
        sendRequest();
    }
}
