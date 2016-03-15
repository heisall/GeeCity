package com.geecity.hisenseplus.home.activity.mine;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.CommonEditActivity;
import com.geecity.hisenseplus.home.activity.CommonEditActivity.CommActvType;
import com.geecity.hisenseplus.home.activity.CommonEditActivity.IntentKey;
import com.geecity.hisenseplus.home.bean.HobbyBean;
import com.geecity.hisenseplus.home.bean.HobbyData;
import com.geecity.hisenseplus.home.bean.UserBean;
import com.geecity.hisenseplus.home.utils.BitmapAsset;
import com.geecity.hisenseplus.home.utils.CommonTools;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.geecity.hisenseplus.home.view.RoundImageView;
import com.ibill.lib.https.HttpUtil;
import com.ibill.lib.https.HttpUtil.RequestHttpCallBack;
import com.ibill.lib.webutils.RequestHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class PersonActivity extends ActionBarActivity {
	// 请求码
	private static final int IMAGE_REQUEST_CODE = 0;// 本地图片请求标志
	private static final int CAMERA_REQUEST_CODE = 1;// 相机请求标志
	private static final int RESULT_REQUEST_CODE = 2;// 返回结果标志
	private Dialog dialog; // 选择从"相机  相册"选择图片的dialog
	@ViewInject(R.id.img_pers_head)
	private RoundImageView mImageViewIcon;
	@ViewInject(R.id.tv_pers_nickname)
	private TextView mTvNickName;// 昵称
	@ViewInject(R.id.tv_pers_edit_gender)
	private TextView mTvSex;// 性别
	@ViewInject(R.id.tv_pers_status)
	private TextView mTvMotion;// 情感
	@ViewInject(R.id.tv_pers_like)
	private TextView mTvLike;// 兴趣爱好
	@ViewInject(R.id.tv_pers_sign)
	private TextView mTvSign;// 兴趣爱好
	@ViewInject(R.id.btn_home_menu)
	private Button mTopBackBtn;
	@ViewInject(R.id.tv_home_topbar_title)
	private TextView mTopTitle;

	@ViewInject(R.id.img_pers_head)
	private ImageView mImgHead;
	@ViewInject(R.id.tv_pers_nickname)
	private TextView mTv1NickName;
	@ViewInject(R.id.tv_pers_edit_gender)
	private TextView mTv2Gender;
	@ViewInject(R.id.tv_pers_status)
	private TextView mTv3Status;
	@ViewInject(R.id.tv_pers_like)
	private TextView mTv4Like;
	@ViewInject(R.id.tv_pers_sign)
	private TextView mTv5Sign;
	private Bundle mBundle = new Bundle();
	//兴趣爱好
    protected List<HobbyData> mHobbys;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_info);

		ViewUtils.inject(this);
		updateUI();
	}

    private void updateUI() {
        UserBean bean = GCApplication.sApp.getUserInfo();
        if (bean == null) return;
        BitmapAsset.displayImg(PersonActivity.this, mImageViewIcon, bean.getPhoto(), R.drawable.icn_community_selected);

        if (bean.getNickName() != null) {
            mTv1NickName.setText(bean.getNickName());
        }
        if (bean.getSex() != null) {
            mTvSex.setText(bean.getSex());
        }
        if(bean.getQgzk() != null){
            mTv3Status.setText(bean.getQgzk());
        }
        if(bean.getXqah() != null){
            mTv4Like.setText(bean.getXqah());
        }
        if(bean.getGxqm() != null){
            mTv5Sign.setText(bean.getGxqm());
        }

    }

    @Override
	public void configActionBar() {
		mTopBackBtn.setVisibility(View.VISIBLE);
		mTopTitle.setText(getActivityTitle());
	}

	private int getActivityTitle() {
		return R.string.pers_actv_edit_title;
	}

	private static final int REQU_CODE_NICKNAME = 10;
	private static final int REQU_CODE_GENDER = 11;
	private static final int REQU_CODE_STATUS = 12;
	private static final int REQU_CODE_LIKE = 13;
	private static final int REQU_CODE_SIGN = 14;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK) {
	        if(data == null && requestCode==CAMERA_REQUEST_CODE){//调用相机返回的data是空，此处不能返回
	        }else{
	            return;
	        }
		}
        String result = "";
        if(data != null){
            result = data.getStringExtra(IntentKey.KEY_RESULT);
        }
        
		switch (requestCode) {
		// 相机
		case CAMERA_REQUEST_CODE:
		    LogUtils.d("CAMERA_REQUEST_CODE");
		    
			if (CommonTools.hasSdcard()) {
				reSizePhoto(imageUri);
//				cytjHelperImgPro(imageUri.getPath());
			} else {
				showToast("未找到存储卡！");
			}
			break;
		// 相册
		case IMAGE_REQUEST_CODE:
            LogUtils.d("IMAGE_REQUEST_CODE");
			reSizePhoto(data.getData());
			break;
		//裁剪完成
		case RESULT_REQUEST_CODE:
            LogUtils.d("RESULT_REQUEST_CODE");
			if (data != null) {
				getImageToView(data);
			}
			break;
		case REQU_CODE_NICKNAME:
			mTvNickName.setText(result);
            sendRequest("nickname", result);
			break;
		case REQU_CODE_GENDER:
			mTvSex.setText(result);
            sendRequest("sex", result);
			break;
		//情感状况
		case REQU_CODE_STATUS:
		    mTvMotion.setText(result);
            sendRequest("qgzk", getDictValue(result));
			break;
		//兴趣爱好
		case REQU_CODE_LIKE:
		    mTv4Like.setText(result);
            sendRequest("xqah", getDictValue(result));
			break;
		case REQU_CODE_SIGN:
			mTv5Sign.setText(result);
            sendRequest("gxqm", result);
			break;
		default:
			return;
		}
	}

    protected void sendRequest(String paramsName, String paramsValue) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("account", "" + GCApplication.sApp.getUserInfo().getAccount());
        params.addBodyParameter("infoName", paramsName);
        params.addBodyParameter("infoValue", paramsValue);
//        LogUtils.d("account=" + GCApplication.sApp.getUserInfo().getAccount()
//            +"  infoName="+paramsName
//            + " infoValue="+paramsValue);
        HttpUtil httpUtil = new HttpUtil(new RequestHandler(PersonActivity.this, "", getResources().getString(R.string.loading), 0));
        // 回调函数
        RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> result) {
                super.onSuccess(result);
            }
        };
        httpUtil.post(WebConstants.METHOD_USER_INFOR, params, callback);
    }

    @Override
    public void requestCallBack(String dataJson, RequestType type) {

    }

    private String getDictValue(String result) {
        String res = "";
        if(mHobbys == null){
            return res;
        }
        for (int i = 0; i < mHobbys.size(); i++) {
            HobbyData d = mHobbys.get(i);
            if (result.contains(d.getMemo())) {
                res+=d.getDictValue()+",";
            }
        }
        if(res.endsWith(",")){
            res = res.substring(0, res.length() - 1);
        }
        return res;
    }

	
	/**
	 * 保存裁剪之后的图片数据
	 */
	private void getImageToView(final Intent data) {
		new Thread(new Runnable() {
            
            @Override
            public void run() {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    try {
                        final Bitmap newPhoto = CommonTools.compressImage(photo);
                        final String imageString = CommonTools.bitMapToString(newPhoto);
                        LogUtils.d("图片长度"+imageString.length());
                        runOnUiThread(new Runnable() {
                            
                            @Override
                            public void run() {
                                mImgHead.setImageBitmap(newPhoto);
                                sendRequest("photo", imageString);
                            }
                        });
                        photo.recycle();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                
            }
        }).start();
	}
	

    // 异步压缩线程
    /**
     * @param indexNum标示码
     *            ,目的当前正在压缩的图片,找到其在gridview.list中对对应的位置 urlImgStr为这张图片地址
     * */
    private void cytjHelperImgPro(final String urlImgStr) {
        new Thread() {
            public void run() {

                Bitmap bitmap = CommonTools.fitSizeImg(urlImgStr);
                if (null == bitmap) {
                    return;
                }
                try {
                    final Bitmap bitmap2 = CommonTools.compressImage(bitmap);
                    bitmap.recycle();
                    if(bitmap2 == null){
                        LogUtils.e("compress image failed");
                        return;
                    }
                    final String imageString = CommonTools.bitMapToString(bitmap2);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mImgHead.setImageBitmap(bitmap2);
                            LogUtils.d(imageString.length()+"");
                            sendRequest("photo", imageString);
                        }
                    });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
               
            }
        }.start();
    }

	public void reSizePhoto(Uri uri) {
	    LogUtils.d("reSizePhoto = " + uri.getPath());
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, RESULT_REQUEST_CODE);
		overridePendingTransition(R.anim.enter_righttoleft, R.anim.exit_righttoleft);

	}
	
	private Uri imageUri; //图片路径
	private String filename; //图片名称
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	
	@OnClick({ R.id.btn_home_menu, R.id.img_pers_head, R.id.ly_pers_head,
			R.id.ly_pers_nickname, R.id.ly_pers_gender, R.id.ly_pers_status,
			R.id.ly_pers_like, R.id.ly_pers_sign })
	public void OnClick(View v) {
		switch (v.getId()) {

		case R.id.btn_home_menu: // 点击返回按钮
			onBackPressed();
			break;
		case R.id.ly_pers_head:
		case R.id.img_pers_head:// 头像

			// 选择图片Dialog
			dialog = new Dialog(PersonActivity.this, R.style.MyDialog);
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
			// 从相机
			btn_camera.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
	                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	                File dir = new File(Environment.getExternalStorageDirectory() + File.separator + "/imag");
	                if (!dir.exists())
	                    dir.mkdirs();
	                File f = new File(dir, sdf.format(new Date()) + ".jpg");
	                imageUri = Uri.fromFile(f);

	                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
	                startActivityForResult(intent, CAMERA_REQUEST_CODE);
	                overridePendingTransition(R.anim.enter_righttoleft, R.anim.exit_righttoleft);
					dialog.dismiss();
				}
			});
			btn_gray.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 打开图库
					Intent intentFromGallery = new Intent();
					intentFromGallery.setType("image/*"); // 设置文件类型
					intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
					startActivityForResult(intentFromGallery, IMAGE_REQUEST_CODE);
					overridePendingTransition(R.anim.enter_righttoleft, R.anim.exit_righttoleft);
					dialog.dismiss();
				}
			});
			break;
		case R.id.ly_pers_nickname: {// 昵称
			mBundle.clear();
			mBundle.putCharSequence(IntentKey.KEY_TITLE, getResources()
					.getString(R.string.pers_actv_edit_item_tel));
			mBundle.putSerializable(IntentKey.KEY_TYPE, CommActvType.INPUT);
			mBundle.putCharSequence(IntentKey.KEY_DEFAULT, mTvNickName
					.getText().toString());
			startNextActivityForResult(mBundle, CommonEditActivity.class,
					REQU_CODE_NICKNAME);
		}
			break;
		case R.id.ly_pers_gender: {// 性别
			mBundle.clear();
			mBundle.putSerializable(IntentKey.KEY_TYPE, CommActvType.SELECT);
			mBundle.putString(IntentKey.KEY_TITLE, getResources().getString(R.string.pers_actv_edit_item_gender));
			ArrayList<String> list = new ArrayList<String>();
			String[] source = getResources().getStringArray(R.array.gender);
			list.addAll(Arrays.asList(source));
			mBundle.putStringArrayList(IntentKey.KEY_SOURCE, list);
			mBundle.putString(IntentKey.KEY_DEFAULT, "男".equals(mTv2Gender.getText().toString()) ? "0" : "1");
			startNextActivityForResult(mBundle, CommonEditActivity.class, REQU_CODE_GENDER);
		}
			break;
		case R.id.ly_pers_status: {// 情感状况
		    requestConfig(WebConstants.METHOD_USER_INFOR_MOTION);
		}
			break;
		case R.id.ly_pers_like: {// 兴趣爱好
		    requestConfig(WebConstants.METHOD_USER_INFOR_HOBBY);
		}
			break;
		case R.id.ly_pers_sign: {// 个性签名
			mBundle.clear();
			mBundle.putCharSequence(IntentKey.KEY_TITLE, getResources()
					.getString(R.string.pers_actv_edit_item_ins));
			mBundle.putSerializable(IntentKey.KEY_TYPE, CommActvType.INPUT);
			mBundle.putCharSequence(IntentKey.KEY_DEFAULT, mTv1NickName
					.getText().toString());
			startNextActivityForResult(mBundle, CommonEditActivity.class, REQU_CODE_SIGN);
		}
			break;

		default:
			break;
		}
	}

    private void requestConfig(final String path) {
        RequestParams params = new RequestParams();
        HttpUtil httpUtil = new HttpUtil(new RequestHandler(PersonActivity.this, "", getResources().getString(R.string.loading), 0));
        // 回调函数
        RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> result) {
                super.onSuccess(result);
                HobbyBean bean = mGson.fromJson(result.result, HobbyBean.class);
                if(bean.getData() == null || bean.getData().size() == 0){
                    return;
                }
                mHobbys = bean.getData();
                mBundle.clear();
                mBundle.putSerializable(IntentKey.KEY_TYPE, CommActvType.SELECT);
                ArrayList<String> list = new ArrayList<String>();
                for (HobbyData b : mHobbys) {
                    LogUtils.d(b.getMemo() + "  " + b.getDictValue());
                    list.add(b.getMemo());
                }
                mBundle.putStringArrayList(IntentKey.KEY_SOURCE, list);
                if(WebConstants.METHOD_USER_INFOR_MOTION.equals(path)){
                    mBundle.putInt(IntentKey.KEY_CHOICEMODE, ListView.CHOICE_MODE_SINGLE);
                    mBundle.putString(IntentKey.KEY_TITLE, getResources().getString(R.string.pers_actv_edit_item_city));
                    startNextActivityForResult(mBundle, CommonEditActivity.class, REQU_CODE_STATUS);
                }else if(WebConstants.METHOD_USER_INFOR_HOBBY.equals(path)){
                    mBundle.putInt(IntentKey.KEY_CHOICEMODE, ListView.CHOICE_MODE_MULTIPLE);
                    mBundle.putString(IntentKey.KEY_TITLE, getResources().getString(R.string.pers_actv_edit_item_pwd));
                    startNextActivityForResult(mBundle, CommonEditActivity.class, REQU_CODE_LIKE);
                }
//                mBundle.putString(IntentKey.KEY_DEFAULT, "男".equals(mTv2Gender.getText().toString()) ? "0" : "1");
            }
        };
        httpUtil.post(path, params, callback);
    }
}
