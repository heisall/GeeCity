package com.geecity.hisenseplus.home.activity;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RemoteViews;
import android.widget.TextView;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.geecity.hisenseplus.home.BaseFragment;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.live.ShopCarActivity;
import com.geecity.hisenseplus.home.activity.mine.MsgActivity;
import com.geecity.hisenseplus.home.adapter.BaseSelectAdapter;
import com.geecity.hisenseplus.home.bean.AreaInfoBean;
import com.geecity.hisenseplus.home.bean.DiscTypesBean;
import com.geecity.hisenseplus.home.bean.VersionInfo;
import com.geecity.hisenseplus.home.bean.VersonUpdateResultBean;
import com.geecity.hisenseplus.home.fragment.Home0HomePageFragment;
import com.geecity.hisenseplus.home.fragment.Home1ManagerFragment;
import com.geecity.hisenseplus.home.fragment.Home2LiveFragment;
import com.geecity.hisenseplus.home.fragment.Home3DiscFragment;
import com.geecity.hisenseplus.home.fragment.Home4MeFragment;
import com.geecity.hisenseplus.home.receiver.MessageReceiver;
import com.geecity.hisenseplus.home.utils.CommonTools;
import com.geecity.hisenseplus.home.utils.Const;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.geecity.hisenseplus.home.view.ATMDialog;
import com.geecity.hisenseplus.home.view.ATMDialog.DialogType;
import com.geecity.hisenseplus.home.view.ATMDialog.OnDialogListener;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.ibill.lib.activity.BaseActivity;
import com.ibill.lib.https.HttpUtil;
import com.ibill.lib.https.HttpUtil.RequestHttpCallBack;
import com.ibill.lib.tools.AppUtils;
import com.ibill.lib.tools.SDCardUtils;
import com.ibill.lib.webutils.RequestHandler;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class HomeActivity extends BaseActivity {
	public static final String KEY_FRAGMENT_INDEX = "key_fragment_index";
    @ViewInject(R.id.btn_home_menu)
	private Button mBtnShopCar;
	@ViewInject(R.id.btn_home_select_area)
	private Button mTopSelectArea;
	@ViewInject(R.id.btn_home_message)
	private Button mTopRightBtn;
	@ViewInject(R.id.tv_home_topbar_title)
	private TextView mTopTitle;

	@ViewInject(R.id.btn_home_bottom_home)
	private Button mBtnHome;
	@ViewInject(R.id.btn_home_bottom_manager)
	private Button mBtnManager;
	@ViewInject(R.id.btn_home_bottom_live)
	private Button mBtnLive;
	@ViewInject(R.id.btn_home_bottom_discovery)
	private Button mBtnDis;
	@ViewInject(R.id.btn_home_bottom_me)
	private Button mBtnMe;

	@ViewInject(R.id.tv_home_bottom_home)
	private TextView mTvBottmHome;
	@ViewInject(R.id.tv_home_bottom_manager)
	private TextView mTvBottmManager;
	@ViewInject(R.id.tv_home_bottom_live)
	private TextView mTvBottmLive;
	@ViewInject(R.id.tv_home_bottom_discovery)
	private TextView mTvBottmDisc;
	@ViewInject(R.id.tv_home_bottom_me)
	private TextView mTvBottmMe;
	
	FragmentTransaction mTrans;
	/* 当前fragment */
	private BaseFragment sCurrentFrgt;
	/* 新的fragment */
	private static BaseFragment newFrgt;
	/* fragment 集合 */
	private static HashMap<Integer, BaseFragment> fragmentMap;

	// 默认为首页
	private int type = 0;

	@SuppressLint("UseSparseArrays")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);
		ViewUtils.inject(this);
		fragmentMap = new HashMap<Integer, BaseFragment>();
		initAreaData();
		initTitle();
		initPop();
		int index = getIntent().getIntExtra(KEY_FRAGMENT_INDEX, 0);
		changeBottomStatus(index);
        checkVersionRequest(false);
        initJPush();
	}
	
	private void initJPush() {
        registerMessageReceiver();  // used for receive msg
	    JPushInterface.init(getApplicationContext());
	    bindPushID();
    }
	
    private void bindPushID() {
        JPushInterface.setAlias(GCApplication.sApp, GCApplication.sApp.getUserInfo().getId() + "",
                        new TagAliasCallback() {
                            @Override
                            public void gotResult(int code, String alias, Set<String> tags) {
                                String logs;
                                switch (code) {
                                    case 0:
                                        logs = "Set tag and alias success";
                                        break;

                                    case 6002:
                                        logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                                        if (CommonTools.isConnected(getApplicationContext())) {
                                           handler.sendMessageDelayed(handler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                                        } else {
                                            logs = "No network";
                                        }
                                        break;
                                    default:
                                        logs = "Failed with errorCode = " + code;
                                }
                                LogUtils.i(logs);
                            }
                        });
        JPushInterface.resumePush(GCApplication.sApp);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    
    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MessageReceiver.MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }


    @Override
	protected void onNewIntent(Intent intent) {
	    super.onNewIntent(intent);
        int index = getIntent().getIntExtra(KEY_FRAGMENT_INDEX, 0);
        changeBottomStatus(index);
	}
	
	private AreaInfoBean mDefaultHouseBean;
	
	private void initAreaData() {
		//将HouseBean转为DiscTypesBean，以使用分类列表的adpter
		LinkedList<AreaInfoBean> bean = GCApplication.sApp.getAreaInfos();
		LogUtils.d(bean == null ? "null" : "size = " + bean.size());
		if(bean == null){
			
		}else{
			for (AreaInfoBean aBean : bean) {
				mSelectSourceList.add(new DiscTypesBean(""+aBean.getA_id(), aBean.getA_name()));
				if(aBean.getIsDefault() == 1){
					mDefaultHouseBean = aBean;
				}
			}
		}
	}

	private void initTitle() {
        
	    mBtnShopCar.setVisibility(View.GONE);
	    //控制购物车的显示和隐藏，仅生活主页显示 2016-01-21隐藏不再显示购物车按钮
//        if(type == 2 || type == 4){
//            mBtnShopCar.setVisibility(View.VISIBLE);
//        }else{
//            mBtnShopCar.setVisibility(View.GONE);
//        }
        
		switch (type) {
		case 0:
		case 1:
		case 2:
		case 3:
			mTopRightBtn.setVisibility(View.VISIBLE);
			if(mDefaultHouseBean == null){
				if(mSelectSourceList == null || mSelectSourceList.size() == 0){
					mTopTitle.setText("未绑定房源");
					mTopSelectArea.setVisibility(View.INVISIBLE);
				}else{
					mTopTitle.setText(mSelectSourceList.get(0).getMemo());
					mTopSelectArea.setVisibility(View.VISIBLE);
				}
			}else{
				mTopTitle.setText(mDefaultHouseBean.getA_name());
			}
			break;
		case 4:
			mTopTitle.setText("我");
			mTopSelectArea.setVisibility(View.INVISIBLE);
			mTopRightBtn.setVisibility(View.INVISIBLE);
			break;

		default:
			break;
		}
	}

	/**
	 * 初始化fragment 0-首页，1-管家， 2-生活，3-发现，4-我
	 * 
	 * @Title: initFragment
	 * @param loadType
	 *            0-表示是初始化布局 1-表示是点击按钮时触发的事件 void
	 * @author billkong
	 * @since 2015-11-24 V 1.0
	 */
	private void initFragment(int loadType) {
		initTitle();
		mTrans = getSupportFragmentManager().beginTransaction();
		if (loadType == 0) {
			switch (type) {
			case 0: // 首页
				newFrgt = new Home0HomePageFragment();
				fragmentMap.put(type, newFrgt);
				break;
			case 1: // 管家
				newFrgt = new Home1ManagerFragment();
				fragmentMap.put(type, newFrgt);
				break;
			case 2: // 生活
				newFrgt = new Home2LiveFragment();
				fragmentMap.put(type, newFrgt);
				break;
			case 3: // 发现
				newFrgt = new Home3DiscFragment();
				fragmentMap.put(type, newFrgt);
				break;
			case 4: // 我
				newFrgt = new Home4MeFragment();
				fragmentMap.put(type, newFrgt);
				break;
			}
		} else {
			newFrgt = fragmentMap.get(type);
			if (newFrgt == null) {
				switch (type) {
				case 0: // 首页
					newFrgt = new Home0HomePageFragment();
					fragmentMap.put(type, newFrgt);
					break;
				case 1: // 管家
					newFrgt = new Home1ManagerFragment();
					fragmentMap.put(type, newFrgt);
					break;
				case 2: // 生活
					newFrgt = new Home2LiveFragment();
					fragmentMap.put(type, newFrgt);
					break;
				case 3: // 发现
						// 黑暗过去是光明，都是扯淡。黑暗之后还是黑暗，我们要做的，就是在黑暗里不要害怕，天亮之后，走我们的寻常路
					newFrgt = new Home3DiscFragment();
					fragmentMap.put(type, newFrgt);
					break;
				case 4: // 我
					newFrgt = new Home4MeFragment();
					fragmentMap.put(type, newFrgt);
					break;
				}
			}
		}
		// 切换时不刷新 fragment
		if (!newFrgt.isAdded()) {
			if (sCurrentFrgt == null) {
				mTrans.add(R.id.fragment_main_layout, newFrgt).commit();
			} else {
				mTrans.add(R.id.fragment_main_layout, newFrgt)
						.hide(sCurrentFrgt).show(newFrgt).commit();
			}
		} else {
			mTrans.hide(sCurrentFrgt).show(newFrgt).commit();
		}
		// 把新的fragment 赋值给当前frangment
		sCurrentFrgt = newFrgt;

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@SuppressLint("NewApi")
	@OnClick({ R.id.btn_home_menu, R.id.btn_home_message, R.id.btn_pwd_save,
			R.id.btn_home_bottom_home, R.id.btn_home_bottom_manager,
			R.id.btn_home_bottom_live, R.id.btn_home_bottom_discovery,
			R.id.btn_home_bottom_me, R.id.tv_home_topbar_title
			,R.id.layout_home_bottom_home,R.id.layout_home_bottom_manager,R.id.layout_home_bottom_live
			,R.id.layout_home_bottom_discovery,R.id.layout_home_bottom_me})
	public void OnClick(View v) {
		switch (v.getId()) {
		case R.id.btn_home_menu: // 点击购物车按钮
//			mMenu.toggle();
            startNextActivity(null, ShopCarActivity.class);
			break;
		case R.id.btn_home_message: // 进入消息
			startNextActivity(null, MsgActivity.class);
			break;
		case R.id.layout_home_bottom_home:
		case R.id.btn_home_bottom_home: // 首页
			changeBottomStatus(0);
			break;
		case R.id.layout_home_bottom_manager:
		case R.id.btn_home_bottom_manager: // 管家
			changeBottomStatus(1);
			break;
		case R.id.layout_home_bottom_live:
		case R.id.btn_home_bottom_live: // 生活
			changeBottomStatus(2);
			break;
		case R.id.layout_home_bottom_discovery:
		case R.id.btn_home_bottom_discovery: // 发现
			changeBottomStatus(3);
			break;
		case R.id.layout_home_bottom_me:
		case R.id.btn_home_bottom_me: // 我
			changeBottomStatus(4);
			break;
		case R.id.tv_home_topbar_title://标题点击选择小区
			mPopType.showAsDropDown(mTopTitle);
			break;
		}
	}	

	private PopupWindow mPopType;
	private BaseSelectAdapter mSelectAdapter;
	private static LinkedList<DiscTypesBean> mSelectSourceList = new LinkedList<DiscTypesBean>();
	private DiscTypesBean mDiscType = new DiscTypesBean("", "不限");
	
	private void initPop() {

		mSelectAdapter = new BaseSelectAdapter(this);
		mSelectAdapter.setList(mSelectSourceList);

		LinearLayout layout = (LinearLayout) LayoutInflater.from(this).inflate(
				R.layout.spinner_down, null);
		ListView listView = (ListView) layout.findViewById(R.id.list_spinner);
		listView.setSelector(R.color.txt_white);
		listView.setAdapter(mSelectAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mDiscType = mSelectSourceList.get(position);
				mTopTitle.setText(mDiscType.getMemo());
				mPopType.dismiss();
			}
		});
		mPopType = new PopupWindow(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT) {
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

	private int mBackTime = 0;

	@Override
	public void onBackPressed() {
		if (mBackTime == 1) {
			System.exit(0);
			android.os.Process.killProcess(android.os.Process.myPid());// 杀死进程
		} else {
			showToast("再按一次退出程序");
			mBackTime = 1;
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					mBackTime = 0;
				}
			}, 1000);
		}
	}

	public void changeBottomStatus(int index) {
		type = index;
		initFragment(1);
		mBtnHome.setBackgroundResource(index == 0 ? R.drawable.btn_home_home_pressed
				: R.drawable.btn_home_home);
		mTvBottmHome.setTextColor(index == 0 ? getResources().getColor(
				R.color.txt_hisense_green) : getResources().getColor(
				R.color.txt_black_light2));

		mBtnManager
				.setBackgroundResource(index == 1 ? R.drawable.btn_home_manager_pressed
						: R.drawable.btn_home_manager);
		mTvBottmManager.setTextColor(index == 1 ? getResources().getColor(
				R.color.txt_hisense_green) : getResources().getColor(
				R.color.txt_black_light2));

		mBtnLive.setBackgroundResource(index == 2 ? R.drawable.btn_home_live_pressed
				: R.drawable.btn_home_live);
		mTvBottmLive.setTextColor(index == 2 ? getResources().getColor(
				R.color.txt_hisense_green) : getResources().getColor(
				R.color.txt_black_light2));

		mBtnDis.setBackgroundResource(index == 3 ? R.drawable.btn_home_disovery_pressed
				: R.drawable.btn_home_disovery);
		mTvBottmDisc.setTextColor(index == 3 ? getResources().getColor(
				R.color.txt_hisense_green) : getResources().getColor(
				R.color.txt_black_light2));

		mBtnMe.setBackgroundResource(index == 4 ? R.drawable.btn_home_me_pressed
				: R.drawable.btn_home_me);
		mTvBottmMe.setTextColor(index == 4 ? getResources().getColor(
				R.color.txt_hisense_green) : getResources().getColor(
				R.color.txt_black_light2));

	}

	@Override
	public void callBackWebJson(String json, int msgFlag) {
	}

	public void enterFragment(int index) {
		type = index;
		initFragment(1);
	}

    VersionInfo versionInfo; // 版本检查
    int mDownloadFlag = 0; // 1.正在下载最新版 ,2.成功下载版本 ,3下载失败,-1表示 开始安装

    // 通知栏进度条
    private NotificationManager mNotificationManager = null;
    private Notification mNotification;	
    public void checkVersionRequest(final boolean showToast) {
        HttpUtil httpUtil = new HttpUtil(new RequestHandler(HomeActivity.this, "", getResources().getString(
                        R.string.loading), 0));
        // 回调函数
        RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> result) {
                super.onSuccess(result);
                try {
                    VersonUpdateResultBean bean = new Gson().fromJson(result.result, VersonUpdateResultBean.class);
                    if(Const.SUCCESS.equals(bean.getResult())){
                        versionInfo = bean.getData();
                        final String parentPath = SDCardUtils.getSDCardPath() + "lpsd";
                        File parentFile = new File(parentPath);
                        if (!parentFile.exists()) {
                            parentFile.mkdir();
                        }
                        String url = versionInfo.getUrl();
                        final String upgradeFilepath = (parentFile + url.substring(url.lastIndexOf("/"))).toLowerCase(Locale.CHINA);
                        LogUtils.d("path = " + upgradeFilepath);
                        File upgradeFile = new File(upgradeFilepath);

                        if (upgradeFile.exists()) {
                            upgradeFile.delete();
                        }
                        if (updateVersion(upgradeFilepath)) {
                            if(showToast)showToast("当前版本号"+AppUtils.getVersionName(getApplicationContext())+"已是最新版本");
                            return;
                        } else {
                            // mTvVersion.setTextColor(getResources().getColor(R.color.txt_red));
                        }
                    } else {
                        if(showToast)showToast(bean.getErrorCode());
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    if(showToast)showToast(R.string.errorMsg);
                }
            }
        };
        httpUtil.post(WebConstants.METHOD_UPDATE_METHED, null, callback);
    }
    
    // 返回true表示不需要更新
    private boolean updateVersion(final String upgradeFilepath) {
        if (null == versionInfo) {
            return true;
        }
        int updateFlag = 0; // 1表示强制更新
        try {
            updateFlag = Integer.parseInt(versionInfo.getUpdateFlag());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (CommonTools.getVersionCode(this) < versionInfo.getVerCode()) {
            if (updateFlag == 0) { // 不强制更新
                dailogVersionAdvice(upgradeFilepath);
            } else {
                dailogVersionForce(upgradeFilepath);
            }
            return false; // 需要更新,弹出更新对话框
        }
        // 不需要更新时，将更新信息清空，便于正常流程执行
        versionInfo = null;
        return true;
    }

    private void notificationInit() {
        // 通知栏内显示下载进度条
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("installFlag", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        mNotificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        mNotification = new Notification();
        mNotification.icon = R.drawable.ic_launcher;
        mNotification.tickerText = "正在下载";
        mNotification.contentView = new RemoteViews(getPackageName(),
                R.layout.notify_view);// 通知栏中进度布局
        mNotification.contentIntent = pIntent;
    }

    void downLoadApk(final String upgradeFilepath) {

        notificationInit();
        final String url = versionInfo.getUrl();
        LogUtils.d(versionInfo.getUrl());
        HttpUtils http = new HttpUtils();
        http.download(url, upgradeFilepath, true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
                false, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
                new RequestCallBack<File>() {
                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        mDownloadFlag = 3;
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        mDownloadFlag = 1;
                    }

                    @Override
                    public void onSuccess(ResponseInfo<File> arg0) {
                        Message message = handler.obtainMessage();
                        message.what = MSG_LOADING;
                        message.arg1 = 100;
                        mDownloadFlag = -1;
                        handler.sendMessageDelayed(message, 10l);
                        AppUtils.InstallPackage(HomeActivity.this,
                                upgradeFilepath);
                    }

                    @Override
                    public void onLoading(long total, long current,
                            boolean isUploading) {
                        super.onLoading(total, current, isUploading);

                        Message message = handler.obtainMessage();
                        message.what = MSG_LOADING;
                        message.arg1 = (int) (1f * current / total * 100);
                        handler.sendMessageDelayed(message, 10l);
                    }

                });
    }
    // 版本检测(建议更新)
    private void dailogVersionAdvice(final String upgradeFilepath) {
        final ATMDialog dialog = new ATMDialog(this, DialogType.TWO_BUTTON);
        dialog.setText("立即更新", "稍后再说");
        if (versionInfo.getVerInfo() != null
                && !versionInfo.getVerInfo().equals("")) {
            dialog.setDesc("有新的版本，请下载更新\n版本号："+versionInfo.getVerName() + "\n" + (versionInfo.getVerInfo()));
        } else {
            dialog.setText("检测到新版本,请立即更新!");
        }
        dialog.setOnDialogLister(new OnDialogListener() {

            @Override
            public void onConfirm() {
                dialog.dismiss();
                downLoadApk(upgradeFilepath);
            }

            @Override
            public void onCancle() {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    // 版本检测(强制更新)
    private void dailogVersionForce(final String upgradeFilepath) {
        final ATMDialog dialog = new ATMDialog(this, DialogType.ONE_BUTTON);
        dialog.setText("立即更新", "稍后再说");
        if (versionInfo.getVerInfo() != null
                && !versionInfo.getVerInfo().equals("")) {
            dialog.setDesc("有新的版本，请下载更新\n版本号："+versionInfo.getVerName() + "\n" + versionInfo.getVerInfo());
        } else {
            dialog.setText("检测到新版本,旧版本已经不能使用,请立即更新");
        }
        dialog.setOnDialogLister(new OnDialogListener() {

            @Override
            public void onConfirm() {
                dialog.dismiss();
                downLoadApk(upgradeFilepath);
            }

            @Override
            public void onCancle() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    
    private final static int MSG_SET_ALIAS = 0x1000;
    private final static int MSG_LOADING = 0x1001;
    private final static int MSG_LOAD_COMPLETE = 0x1002;
    private final static int MSG_NEED_UPGRAD = 0x1003;
    
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MSG_LOADING: // 更新通知栏(下载中)
                mNotification.contentView.setTextViewText( R.id.content_view_text1, msg.arg1 + "%");
                mNotification.contentView.setProgressBar( R.id.content_view_progress, 100, msg.arg1, false);
                mNotificationManager.notify(0, mNotification);
                break;
            case MSG_LOAD_COMPLETE: // 下载完毕
                break;
            case MSG_NEED_UPGRAD: // 版本检查更新
                checkVersionRequest(false);
                break;
            case MSG_SET_ALIAS:
                bindPushID();
                break;
            }
        }
    };

}
