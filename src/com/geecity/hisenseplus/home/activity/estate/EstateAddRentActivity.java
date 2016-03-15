package com.geecity.hisenseplus.home.activity.estate;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.TreeMap;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.CommonEditActivity;
import com.geecity.hisenseplus.home.activity.CommonEditActivity.CommActvType;
import com.geecity.hisenseplus.home.activity.CommonEditActivity.IntentKey;
import com.geecity.hisenseplus.home.activity.disc.PhotoWallActivity;
import com.geecity.hisenseplus.home.adapter.EstateRentSubAdapter;
import com.geecity.hisenseplus.home.adapter.PicGridAdapter;
import com.geecity.hisenseplus.home.adapter.PicGridAdapter.OnAddClickedListener;
import com.geecity.hisenseplus.home.adapter.PicGridAdapter.OnDeleteClickedListener;
import com.geecity.hisenseplus.home.bean.EstateFilterBean;
import com.geecity.hisenseplus.home.bean.EstateFilterResultBean;
import com.geecity.hisenseplus.home.bean.SupportingBean;
import com.geecity.hisenseplus.home.bean.wb.RentHouse;
import com.geecity.hisenseplus.home.utils.CYTJHelper;
import com.geecity.hisenseplus.home.utils.CommonTools;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.geecity.hisenseplus.home.view.ATMDialog;
import com.geecity.hisenseplus.home.view.ATMDialog.DialogType;
import com.geecity.hisenseplus.home.view.ATMDialog.OnDialogListener;
import com.geecity.hisenseplus.home.view.GridViewForScrollView;
import com.google.gson.Gson;
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
 * 租房 新增界面
 * 
 * @author Administrator
 * 
 */
public class EstateAddRentActivity extends ActionBarActivity{

    @ViewInject(R.id.btn_home_menu)
    private Button mTopBackBtn;
    @ViewInject(R.id.tv_home_topbar_title)
    private TextView mTopTitle;

    @ViewInject(R.id.cytj_imgbar)
    private GridView gridView;
    @ViewInject(R.id.cytj_btn_img_none_add)
    private Button addImgBtn; // 添加图片

    @ViewInject(R.id.tv_estate_rent_add_area)
    private TextView mTvArea;
    @ViewInject(R.id.tv_estate_rent_add_chaox)
    private TextView mTvChaoX;
    @ViewInject(R.id.tv_estate_rent_add_zhuangxiu)
    private TextView mTvZhuangX;
    @ViewInject(R.id.tv_estate_rent_add_fukfs)
    private TextView mTvFukfs;
    
    @ViewInject(R.id.et_estate_rent_add_shi)
    private EditText mEtTypeShi;
    @ViewInject(R.id.et_estate_rent_add_ting)
    private EditText mEtTypeTing;
    @ViewInject(R.id.et_estate_rent_add_wei)
    private EditText mEtTypeWei;
    @ViewInject(R.id.et_estate_rent_add_mianj)
    private EditText mEtTypeMianJ;
    @ViewInject(R.id.et_estate_rent_add_zuj)
    private EditText mEtTypeZuJ;
    @ViewInject(R.id.et_estate_rent_add_floor)
    private EditText mEtFloor;
    @ViewInject(R.id.et_estate_rent_add_floors)
    private EditText mEtFloors;
    @ViewInject(R.id.et_estate_rent_add_miaos)
    private EditText mEtTypeMiaoS;
    @ViewInject(R.id.et_estate_rent_add_niandai)
    private EditText mEtNianDai;
    @ViewInject(R.id.et_estate_rent_add_phone)
    private EditText mEtTypePhone;
    
    @ViewInject(R.id.gvfs_estate_rent_add)
    private GridViewForScrollView mGvFScroV;
    
    private EstateRentSubAdapter mSubAdpter;
	private LinkedList<SupportingBean> mSupBeans = new LinkedList<SupportingBean>();
	
    private PicGridAdapter gridAdapter;
    private Dialog dialog; // 选择从"相机  相册"选择图片的dialog

    // 请求码
    private static final int IMAGE_REQUEST_CODE = 0;// 本地图片请求标志
    private static final int CAMERA_REQUEST_CODE = 1;// 相机请求标志
    private static final int RESULT_REQUEST_CODE = 2;// 返回结果标志
    public static final int PIC_MAX = 6;

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    private Uri uri;
    TreeMap<Integer, String> treeMap = new TreeMap<Integer, String>(); // 需要压缩的图片队列
    private int indexCYTJHelper = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estate_rent_add);
        ViewUtils.inject(this);
        mSubAdpter = new EstateRentSubAdapter(this);
        mGvFScroV.setAdapter(mSubAdpter);
        mGvFScroV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				SupportingBean bean = mSupBeans.get(arg2);
				bean.setIsSup(bean.getIsSup() == 0?1:0);
				mSubAdpter.setArrayList(mSupBeans);
			}
		});
        gridAdapter = new PicGridAdapter(this, 6);
        initPicGridAdapter();
        new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				mTvArea.requestFocus();
			}
		}, 500);
        
        sendFilterRequest();
    }

    private EstateFilterResultBean mFilterBean;
    
    private void sendFilterRequest() {
        super.sendRequest();
        HttpUtil httpUtil = new HttpUtil(new RequestHandler(EstateAddRentActivity.this, "", getResources().getString(R.string.loading), 0));
        // 回调函数
        RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> result) {
                super.onSuccess(result);
                try {
                    mFilterBean = mGson.fromJson(result.result, EstateFilterResultBean.class);
                    if(mFilterBean != null){
                        updateGvFscr();
                    }else{
                        showToast("获取条件失败");
                    }

                } catch (Exception e) {
                    showToast(R.string.errorMsg);
                }

            }
        };
        httpUtil.post(WebConstants.METHOD_ESTATE_ADD_HOUSE, null, callback);
    }

    protected void updateGvFscr() {
        mSupBeans = mFilterBean.getMd();
        if(mSupBeans != null && mSupBeans.size() > 0){
            mSubAdpter.setArrayList(mSupBeans);
        }
    }

	@OnClick({ R.id.btn_home_menu, R.id.cytj_btn_img_none_add,
			R.id.btn_estate_add_send, R.id.tv_estate_rent_add_area,
			R.id.tv_estate_rent_add_chaox, R.id.tv_estate_rent_add_zhuangxiu,
			R.id.tv_estate_rent_add_fukfs })
	public void OnClick(View v) {
        switch (v.getId()) {
        case R.id.btn_home_menu: // 点击返回按钮
            onBackPressed();
            break;
        case R.id.btn_estate_add_send:// 发送
            if (treeMap.size() > 0) {
                progressDialog = ProgressDialog.show(EstateAddRentActivity.this, null, "加载图片中...", true);
                handler.sendEmptyMessage(4);
                return;
            }
            handler.sendEmptyMessage(0); // 满足提交条件
            break;
        case R.id.cytj_btn_img_none_add:// 图片选择
            dialogDispather();
            break;
        case R.id.tv_estate_rent_add_area:{//选择小区
            if(mFilterBean == null || mFilterBean.getLp()== null || mFilterBean.getLp().size() == 0){
                showToast("暂无小区可选");
                return;
            }
            ArrayList<String> list = new ArrayList<String>();
        	LinkedList<EstateFilterBean> beans = mFilterBean.getLp();
        	for (EstateFilterBean estateFilterBean : beans) {
                list.add(estateFilterBean.getDicValue());
            }
        	Bundle bundle = new Bundle();
			bundle.clear();
			bundle.putSerializable(IntentKey.KEY_TYPE, CommActvType.SELECT);
			bundle.putString(IntentKey.KEY_TITLE, getResources().getString(R.string.house_bind_title_1));
			bundle.putStringArrayList(IntentKey.KEY_SOURCE, list);
			startNextActivityForResult(bundle, CommonEditActivity.class, REQU_CODE_AREA);
        }
			break;
        case R.id.tv_estate_rent_add_chaox:{//选择朝向
            if(mFilterBean == null || mFilterBean.getLp()== null || mFilterBean.getCx().size() == 0){
                showToast("暂无朝向可选");
                return;
            }
            LinkedList<EstateFilterBean> cxs = mFilterBean.getCx();
            if(cxs != null && cxs.size() > 0){
                ArrayList<String> list = new ArrayList<String>();
                for (EstateFilterBean estateFilterBean : cxs) {
                    list.add(estateFilterBean.getDicValue());
                }
                Bundle bundle = new Bundle();
                bundle.clear();
                bundle.putSerializable(IntentKey.KEY_TYPE, CommActvType.SELECT);
                bundle.putString(IntentKey.KEY_TITLE, getResources().getString(R.string.house_bind_title_1));
                bundle.putStringArrayList(IntentKey.KEY_SOURCE, list);
                startNextActivityForResult(bundle, CommonEditActivity.class, REQU_CODE_CHAO_X);
            }
        }
			break;
        case R.id.tv_estate_rent_add_zhuangxiu:{//选择装修
            if(mFilterBean == null || mFilterBean.getLp()== null || mFilterBean.getZx().size() == 0){
                showToast("暂无装修方式可选");
                return;
            }
            ArrayList<String> list = new ArrayList<String>();
        	LinkedList<EstateFilterBean> beans = mFilterBean.getZx();
        	for (EstateFilterBean estateFilterBean : beans) {
                list.add(estateFilterBean.getDicValue());
            }
        	Bundle bundle = new Bundle();
			bundle.clear();
			bundle.putSerializable(IntentKey.KEY_TYPE, CommActvType.SELECT);
			bundle.putString(IntentKey.KEY_TITLE, getResources().getString(R.string.house_bind_title_1));
			bundle.putStringArrayList(IntentKey.KEY_SOURCE, list);
			startNextActivityForResult(bundle, CommonEditActivity.class, REQU_CODE_ZHUANG_X);
        }
			break;
        case R.id.tv_estate_rent_add_fukfs:{//选择付款方式
            if(mFilterBean == null || mFilterBean.getLp()== null || mFilterBean.getFkfs().size() == 0){
                showToast("暂无付款方式可选");
                return;
            }
            ArrayList<String> list = new ArrayList<String>();
        	LinkedList<EstateFilterBean> beans = mFilterBean.getFkfs();
        	for (EstateFilterBean estateFilterBean : beans) {
                list.add(estateFilterBean.getDicValue());
            }
        	Bundle bundle = new Bundle();
			bundle.clear();
			bundle.putSerializable(IntentKey.KEY_TYPE, CommActvType.SELECT);
			bundle.putString(IntentKey.KEY_TITLE, getResources().getString(R.string.house_bind_title_1));
			bundle.putStringArrayList(IntentKey.KEY_SOURCE, list);
			startNextActivityForResult(bundle, CommonEditActivity.class, REQU_CODE_FKFS);
        }
			break;
        default:
            break;
        }
    }
    
    private static final int REQU_CODE_AREA = 2000;
    private static final int REQU_CODE_CHAO_X = 2001;
    private static final int REQU_CODE_ZHUANG_X = 2002;
    private static final int REQU_CODE_FKFS = 2003;
    
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        public void dispatchMessage(Message msg) {

            switch (msg.what) {
            case 0: // 开始提交
                if (progressDialog != null) {
                    progressDialog.cancel();
                }
                sendRequest();
                break;
            case 1: // 图片压缩失败
                Toast.makeText(EstateAddRentActivity.this, "图片加载失败,请重新选择!", Toast.LENGTH_SHORT).show();
                break;

            case 2: // 异步压缩图片
                if (treeMap.size() > 0) {
                    cytjHelperImgPro(treeMap.firstKey(), treeMap.get(treeMap.firstKey()));
                }
                break;

            case 4: // 处于等待
                if (treeMap.size() > 0) {
                    sendEmptyMessageDelayed(4, 1000l);
                } else {
                    sendEmptyMessageDelayed(0, 100l);
                }
                break;
            }
        }
    };
    
    @Override
    protected void sendRequest() {
        super.sendRequest();
        RequestParams params = new RequestParams();
        RentHouse rentHouse = getRentHouse();
        if(rentHouse == null) return;
        params.addBodyParameter("rentHouse", mGson.toJson(rentHouse));
        HttpUtil httpUtil = new HttpUtil(new RequestHandler(EstateAddRentActivity.this, "", getResources().getString(R.string.loading), 0));
        // 回调函数
        RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> result) {
                super.onSuccess(result);
                try {
                    Gson mGson = new Gson();
                    @SuppressWarnings("unchecked")
                    HashMap<String, Object> map = mGson.fromJson(result.result, HashMap.class);
                    String result1 = map.get("result").toString();
                    if ("1".equals(result1)) {
                        Toast.makeText(EstateAddRentActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        showToast(map.get("errorCode").toString());
                        return;
                    }

                } catch (Exception e) {
                    showToast(R.string.errorMsg);
                }

            }
        };
        httpUtil.put(WebConstants.METHOD_ESTATE_SAVER_ENT_HOUSE, params, callback);
    }

	private RentHouse getRentHouse() {

	    //小区名称
        if(mFilterBean == null || mFilterBean.getLp()== null || mFilterBean.getLp().size() == 0){
            showToast("暂无小区信息");
            return null;
        }
        
        if(TextUtils.isEmpty(mTvArea.getText().toString())){
            showToast("请选择所在小区");
            return null;
        }
        
        String bk = null;
        LinkedList<EstateFilterBean> beans = mFilterBean.getLp();
        for (EstateFilterBean estateFilterBean : beans) {
            if(estateFilterBean.getDicValue().equals(mTvArea.getText().toString())){
                bk = estateFilterBean.getDicKey();
            }
        }
		if(TextUtils.isEmpty(bk)){
            showToast("暂无小区信息");
		    return null;
		}
		RentHouse rh = new RentHouse();
		rh.setBuildingInfo(bk);
		
		//户型-室
		String ss = getString(mEtTypeShi);
		if(TextUtils.isEmpty(ss)){
			showToast("请输入户型");
			return null;
		}
		rh.setHouse_Indoor(ss);
        //户型-厅
		ss = getString(mEtTypeTing);
		if(TextUtils.isEmpty(ss)){
			showToast("请输入户型");
			return null;
		}
		rh.setHouse_living(ss);
        //户型-卫
		ss = getString(mEtTypeWei);
		if(TextUtils.isEmpty(ss)){
			showToast("请输入户型");
			return null;
		}
		rh.setHouse_Toilet(ss);
		
		//面积
		String data = getString(mEtTypeMianJ);
		if(TextUtils.isEmpty(data)){
			showToast("请输入房屋面积");
			return null;
		}
		rh.setBuildingArea(Double.valueOf(data));
		
		//年代
		data = getString(mEtNianDai);
		if(TextUtils.isEmpty(data)){
			showToast("请输入房屋年代");
			return null;
		}
		rh.setNiandai(data);
		
		//租金
		data = getString(mEtTypeZuJ);
		if(TextUtils.isEmpty(data)){
			showToast("请输入租金");
			return null;
		}
		rh.setRent(Double.valueOf(data));

		//楼层
		rh.setFloorCount(getString(mEtFloors));

		//总楼层
		rh.setFloors(getString(mEtFloor));
        
        //朝向
        if(mFilterBean.getCx()== null || mFilterBean.getCx().size() == 0){
            showToast("暂无楼盘朝向信息");
            return null;
        }
        if(TextUtils.isEmpty(mTvChaoX.getText().toString())){
            showToast("请选择楼盘朝向");
            return null;
        }
        bk = null;
        LinkedList<EstateFilterBean> cxs = mFilterBean.getCx();
        for (EstateFilterBean estateFilterBean : cxs) {
            if(estateFilterBean.getDicValue().equals(mTvChaoX.getText().toString())){
                bk = estateFilterBean.getDicKey();
            }
        }
        if(TextUtils.isEmpty(bk)){
            showToast("暂无小区信息");
            return null;
        }
        rh.setOrientation(bk);
        
        //装修
        if(mFilterBean.getZx()== null || mFilterBean.getZx().size() == 0){
            showToast("暂无楼盘装修信息");
            return null;
        }
        if(TextUtils.isEmpty(mTvZhuangX.getText().toString())){
            showToast("请选择楼盘装修");
            return null;
        }
        bk = null;
        LinkedList<EstateFilterBean> zxs = mFilterBean.getZx();
        for (EstateFilterBean estateFilterBean : zxs) {
            if(estateFilterBean.getDicValue().equals(mTvZhuangX.getText().toString())){
                bk = estateFilterBean.getDicKey();
            }
        }
        if(TextUtils.isEmpty(bk)){
            showToast("暂无装修信息");
            return null;
        }
        rh.setRenovationInfo(bk);
        
        //付款方式
        if(mFilterBean.getZx()== null || mFilterBean.getZx().size() == 0){
            showToast("暂无付款方式可选");
            return null;
        }
        if(TextUtils.isEmpty(mTvFukfs.getText().toString())){
            showToast("请选择付款方式");
            return null;
        }
        bk = null;
        LinkedList<EstateFilterBean> fkfs = mFilterBean.getFkfs();
        for (EstateFilterBean estateFilterBean : fkfs) {
            if(estateFilterBean.getDicValue().equals(mTvFukfs.getText().toString())){
                bk = estateFilterBean.getDicKey();
            }
        }
        if(TextUtils.isEmpty(bk)){
            showToast("暂无请选择付款方式");
            return null;
        }
        rh.setPayMethod(bk);
		
		//描述
		rh.setDescription(getString(mEtTypeMiaoS));
		
		//联系人(此处使用用户ID)
//		String name = getString(mEtTypeName);
//		if(TextUtils.isEmpty(name)){
//			showToast("请输入联系人姓名");
//			return null;
//		}
		rh.setAddPerson(GCApplication.sApp.getUserInfo().getId() + "");
		
		//手机号
		String phone = getString(mEtTypePhone);
		if(TextUtils.isEmpty(phone)){
			showToast("请输入手机号，方便与您及时联系");
			return null;
		}
		rh.setMobilePhone(phone);
		
		//配套设施
		StringBuilder sbeans = new StringBuilder();
		if(mSupBeans != null && mSupBeans.size() > 0){
            for (int i = 0; i < mSupBeans.size(); i++) {
                SupportingBean sbean = mSupBeans.get(i);
                if (sbean.getIsSup() == 1) {
                    sbeans.append(sbean.getDicKey());
                    sbeans.append(",");
                }
            }
            if(sbeans.toString().endsWith(",")){
                sbeans.replace(sbeans.length() -1, sbeans.length(), "");
            }
            rh.setSupporting(sbeans.toString());
        }

		//图片处理
        StringBuilder sbd = new StringBuilder();
        for (int k = 0; k < gridAdapter.list.size(); k++) {
            String imgStr = ((CYTJHelper) (gridAdapter.list.get(k))).getImageStream();
            sbd.append(imgStr);
            if(k < gridAdapter.list.size() - 1){
                sbd.append(",");
            }
        }
        rh.setPhoto(sbd.toString().split(","));
        return rh;
    }
	
	private String getString(EditText et){
		if(et == null) return null;
		return et.getText().toString().trim();
	}

    private void initPicGridAdapter() {
        gridAdapter.setOnAddClickedListener(new OnAddClickedListener() {

            @Override
            public void onAddClicked(int position) {
                // 选择图片Dialog
                dialog = new Dialog(EstateAddRentActivity.this, R.style.MyDialog);
                dialog.setContentView(R.layout.dialog_cytj);
                dialog.show();
                Button btn_camera = (Button) dialog.findViewById(R.id.btn_cytj_camera);
                Button btn_gray = (Button) dialog.findViewById(R.id.btn_cytj_gray);
                Button btn_exit = (Button) dialog.findViewById(R.id.btn_cytj_exit);
                btn_exit.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btn_camera.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (gridAdapter.list.size() >= PIC_MAX) {
                            return;
                        }
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 调用android自带的照相机
                        File dir = new File(Environment.getExternalStorageDirectory() + File.separator  + "/imag");
                        if (!dir.exists())
                            dir.mkdirs();
                        File f = new File(dir, sdf.format(new Date()) + ".jpg");// localTempImgDir和localTempImageFileName是自己定义的名字
                        uri = Uri.fromFile(f);

                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(intent, CAMERA_REQUEST_CODE);
                        overridePendingTransition(R.anim.enter_righttoleft, R.anim.exit_righttoleft);
                    }
                });
                btn_gray.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (gridAdapter.list.size() >= PIC_MAX) {
                            return;
                        }
                        // 打开最近照片
                        Intent intentFromGallery = new Intent(EstateAddRentActivity.this, PhotoWallActivity.class);
                        intentFromGallery.putExtra("curMaxSize", PIC_MAX);
                        intentFromGallery.putExtra("curSize", gridAdapter.list.size());
                        intentFromGallery.putExtra("type", "cyty");
                        startActivityForResult(intentFromGallery, IMAGE_REQUEST_CODE);
                        overridePendingTransition(R.anim.enter_righttoleft, R.anim.exit_righttoleft);
                    }
                });
            }
        });
        gridAdapter.setDeleteClickedListener(new OnDeleteClickedListener() {

            @Override
            public void onDelClicked(final int position) {
                final ATMDialog dialog = new ATMDialog(EstateAddRentActivity.this, DialogType.TWO_BUTTON);

                dialog.setText(R.string.dialog_del, R.string.dialog_exit);
                dialog.setDesc(R.string.dialog_del_desc);
                final int id = position;
                dialog.setOnDialogLister(new OnDialogListener() {

                    @Override
                    public void onConfirm() {
                        dialog.dismiss();
                        gridAdapter.list.remove(id);
                        try {
                            if (treeMap.containsKey(id)) {
                                treeMap.remove(id);
                            }
                        } catch (Exception e) {

                        }
                        CommonTools.setGridViewHeight(gridView, 4, ScreenUtils.dp2px(90));
                        gridAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancle() {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        gridView.setAdapter(gridAdapter);
        CommonTools.setGridViewHeight(gridView, 4, ScreenUtils.dp2px(90));
        gridAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(final int requestCode, int resultCode,
            final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        reslutDispatcher(requestCode, data);
    }

    void reslutDispatcher(int requestCode, Intent data) {
        switch (requestCode) {
        case IMAGE_REQUEST_CODE:
            dispather(data);
            break;
        case CAMERA_REQUEST_CODE:
            if (CommonTools.hasSdcard()) {
                getImageToView(data);
            } else {
                showToast("未找到存储卡！");
            }
            break;
        case RESULT_REQUEST_CODE: // 裁剪的返回
            break;
        case REQU_CODE_AREA:{//选择小区返回
    		String result = data.getStringExtra(IntentKey.KEY_RESULT);
    		mTvArea.setText(result);
        }
        	break;
        case REQU_CODE_CHAO_X:{
            String result = data.getStringExtra(IntentKey.KEY_RESULT);
            mTvChaoX.setText(result);
        }
            break;
        case REQU_CODE_ZHUANG_X:{
            String result = data.getStringExtra(IntentKey.KEY_RESULT);
            mTvZhuangX.setText(result);
		}
			break;
        case REQU_CODE_FKFS:{
            String result = data.getStringExtra(IntentKey.KEY_RESULT);
            mTvFukfs.setText(result);
        }
            break;
        }
    }

    // 从相册返回的具体处理
    void dispather(Intent intent) {
        if (intent == null) {
            return;
        }
        dialog.dismiss();

        final ArrayList<String> paths = intent.getStringArrayListExtra("paths");
        addImgBtn.setVisibility(View.GONE);
        gridView.setVisibility(View.VISIBLE);
        for (int i = 0; i < paths.size(); i++) {
            CYTJHelper cytjHelper = cytjHelperPro(paths.get(i));
            gridAdapter.list.add(cytjHelper);

            // 开始异步加载压缩图片
            treeMap.put(cytjHelper.getIndex(), paths.get(i));
        }
        CommonTools.setGridViewHeight(gridView, 4, ScreenUtils.dp2px(90));
        gridAdapter.notifyDataSetChanged();
        handler.sendEmptyMessageDelayed(2, 500l);
    }

    /**
     * 根据url生成CYTJHelper
     * 
     * */
    private CYTJHelper cytjHelperPro(String urlImgStr) {
        CYTJHelper cytjHelper = new CYTJHelper();
        cytjHelper.setType(2);
        cytjHelper.setIndex(indexCYTJHelper++);
        cytjHelper.setImageInfo(urlImgStr);
        return cytjHelper;
    }

    private ProgressDialog progressDialog;

    /**
     * 异步压缩线程
     * @param indexNum标示码
     *            ,目的当前正在压缩的图片,找到其在gridview.list中对对应的位置 urlImgStr为这张图片地址
     * */
    private void cytjHelperImgPro(final int indexNum, final String urlImgStr) {
        new Thread() {
            public void run() {

                Bitmap bitmap = CommonTools.fitSizeImg(urlImgStr);
                if (null == bitmap) {
                    return;
                }
                Bitmap bitmap2 = null;
                try {
                    bitmap2 = compressImage(bitmap);
                    bitmap.recycle();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                CYTJHelper cyHelper = null;
                for (int j = 0; j < gridAdapter.list.size(); j++) {
                    if (gridAdapter.list.get(j).getIndex() == indexNum) {
                        cyHelper = gridAdapter.list.get(j);
                        break;
                    }
                }
                if (cyHelper == null) { // 找不到,可能已经删除
                    bitmap2.recycle();
                    treeMap.remove(indexNum);
                    handler.sendEmptyMessageDelayed(2, 500l);
                    return;
                }
                // 确保是同一张图片
                if (cyHelper.getType() == 2  && ((String) (cyHelper.getImageInfo())).equals(urlImgStr)) {
                    cyHelper.setImageStream(CommonTools.bitMapToString(bitmap2));
                    bitmap2.recycle();
                }
                treeMap.remove(indexNum);
                handler.sendEmptyMessageDelayed(2, 500l);
            }
        }.start();
    }

    /**
     * 保存裁剪之后的图片数据
     */
    private void getImageToView(Intent data) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        if (uri == null) {
            return;
        }
        CYTJHelper cytjHelper = cytjHelperPro(uri.getPath());
        if (null == cytjHelper) {
            return;
        }
        addImgBtn.setVisibility(View.GONE);
        gridView.setVisibility(View.VISIBLE);
        gridAdapter.list.add(cytjHelper);
        CommonTools.setGridViewHeight(gridView, 4, ScreenUtils.dp2px(90));
        gridAdapter.notifyDataSetChanged();
        // 开始异步加载压缩图片
        treeMap.put(cytjHelper.getIndex(), uri.getPath());
        handler.sendEmptyMessageDelayed(2, 500l);
    }

    /**
     * 压缩图片
     * 
     * @Title: compressImage
     * @param image
     * @throws FileNotFoundException
     *             void
     */
    private Bitmap compressImage(Bitmap image) throws FileNotFoundException {
        Random random = new Random(4);
        File dir = new File(Environment.getExternalStorageDirectory() + "/imag2");
        if (!dir.exists())
            dir.mkdirs();
        File f = new File(dir, sdf.format(new Date()) + random.nextInt() + ".jpg");
        if (f.exists()) {
            f.delete();
        }
        FileOutputStream out = new FileOutputStream(f);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;

        while (baos.toByteArray().length / 1024f > 2048.0) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置
            options -= 1;// 每次都减少10
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中

        }
        image.compress(Bitmap.CompressFormat.JPEG, options, out);// 这里压缩options%，把压缩后的数据存放到baos中
        BitmapFactory.Options options2 = new BitmapFactory.Options();
        options2.inJustDecodeBounds = false;
        options2.inSampleSize = 2; // width，hight设为原来的十分一
        image.recycle();
        Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), options2);
        return bitmap;
    }

    // 添加图片dialog处理
    protected void dialogDispather() {

        // 选择图片Dialog
        dialog = new Dialog(EstateAddRentActivity.this, R.style.MyDialog);
        dialog.setContentView(R.layout.dialog_cytj);
        dialog.show();
        Button btn_camera = (Button) dialog.findViewById(R.id.btn_cytj_camera);
        Button btn_gray = (Button) dialog.findViewById(R.id.btn_cytj_gray);
        Button btn_exit = (Button) dialog.findViewById(R.id.btn_cytj_exit);
        btn_exit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_camera.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (gridAdapter.list.size() >= PIC_MAX) {// 最多6张图片
                    return;
                }

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 调用android自带的照相机
                File dir = new File(Environment.getExternalStorageDirectory() + File.separator + "/imag");
                if (!dir.exists())
                    dir.mkdirs();
                File f = new File(dir, sdf.format(new Date()) + ".jpg");// localTempImgDir和localTempImageFileName是自己定义的名字
                uri = Uri.fromFile(f);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
                overridePendingTransition(R.anim.enter_righttoleft, R.anim.exit_righttoleft);
            }
        });
        btn_gray.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (gridAdapter.list.size() >= PIC_MAX) {
                    return;
                }

                // 打开最近照片
                Intent intentFromGallery = new Intent(EstateAddRentActivity.this, PhotoWallActivity.class);
                intentFromGallery.putExtra("curMaxSize", PIC_MAX);
                intentFromGallery.putExtra("curSize", gridAdapter.list.size());
                intentFromGallery.putExtra("type", "order");
                startActivityForResult(intentFromGallery, IMAGE_REQUEST_CODE);
                overridePendingTransition(R.anim.enter_righttoleft, R.anim.exit_righttoleft);
                dialog.dismiss();
            }
        });

    }

    @Override
	public void configActionBar() {
		mTopBackBtn.setVisibility(View.VISIBLE);
		mTopTitle.setText(R.string.actv_title_estate_rent_add);
	}

	@Override
	public void requestCallBack(String dataJson, RequestType type) {
	}
}
