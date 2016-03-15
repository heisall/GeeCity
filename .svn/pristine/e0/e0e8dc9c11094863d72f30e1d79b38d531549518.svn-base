package com.geecity.hisenseplus.home.activity.disc;

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
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.geecity.hisenseplus.home.ActionBarActivity;
import com.geecity.hisenseplus.home.GCApplication;
import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.adapter.BaseSelectAdapter;
import com.geecity.hisenseplus.home.adapter.PicGridAdapter;
import com.geecity.hisenseplus.home.adapter.PicGridAdapter.OnAddClickedListener;
import com.geecity.hisenseplus.home.adapter.PicGridAdapter.OnDeleteClickedListener;
import com.geecity.hisenseplus.home.bean.DiscTypesBean;
import com.geecity.hisenseplus.home.utils.CYTJHelper;
import com.geecity.hisenseplus.home.utils.CommonTools;
import com.geecity.hisenseplus.home.utils.WebConstants;
import com.geecity.hisenseplus.home.utils.WebConstants.RequestType;
import com.geecity.hisenseplus.home.view.ATMDialog;
import com.geecity.hisenseplus.home.view.ATMDialog.DialogType;
import com.geecity.hisenseplus.home.view.ATMDialog.OnDialogListener;
import com.google.gson.Gson;
import com.ibill.lib.https.HttpUtil;
import com.ibill.lib.https.HttpUtil.RequestHttpCallBack;
import com.ibill.lib.tools.ScreenUtils;
import com.ibill.lib.webutils.RequestHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class DiscAddActivity extends ActionBarActivity {

	@ViewInject(R.id.btn_home_menu)
	private Button mTopBackBtn;
	@ViewInject(R.id.btn_home_message)
	private Button mTopSend;
	@ViewInject(R.id.tv_home_topbar_title)
	private TextView mTopTitle;
	@ViewInject(R.id.line_disc_add)
	private TextView mTvLine;
	@ViewInject(R.id.cytj_imgbar)
	private GridView gridView;
	@ViewInject(R.id.cytj_btn_img_none_add)
	private Button addImgBtn; // 添加图片

	@ViewInject(R.id.tv_disc_add_catogery)
	private TextView mTvCatogery;
	@ViewInject(R.id.et_disc_add_content)
	private EditText mEtContent;

	private PopupWindow mPopType;

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
		setContentView(R.layout.activity_disc_add);
		ViewUtils.inject(this);
		gridAdapter = new PicGridAdapter(this, 6);
		//获取发现首页获取的分类
        mSelectSourceList = GCApplication.sApp.getDiscTypes();
        int i = 0;
        boolean needRemove = false;
        for (; i < mSelectSourceList.size(); i++) {
        	DiscTypesBean bean = mSelectSourceList.get(i);
        	if("全部".equals(bean.getMemo())){
        		needRemove = true;
        		break;
        	}
		}
        if(needRemove) mSelectSourceList.remove(i);
		initPicGridAdapter();
		initPop();
	}

	private void initPicGridAdapter() {
		gridAdapter.setOnAddClickedListener(new OnAddClickedListener() {

			@Override
			public void onAddClicked(int position) {
				// 选择图片Dialog
				dialog = new Dialog(DiscAddActivity.this, R.style.MyDialog);
				dialog.setContentView(R.layout.dialog_cytj);
				dialog.show();
				Button btn_camera = (Button) dialog
						.findViewById(R.id.btn_cytj_camera);
				Button btn_gray = (Button) dialog
						.findViewById(R.id.btn_cytj_gray);
				Button btn_exit = (Button) dialog
						.findViewById(R.id.btn_cytj_exit);
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
						Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						File dir = new File(Environment.getExternalStorageDirectory() + File.separator + "/imag");
						if (!dir.exists())
							dir.mkdirs();
						File f = new File(dir, sdf.format(new Date()) + ".jpg");
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
						Intent intentFromGallery = new Intent(
								DiscAddActivity.this, PhotoWallActivity.class);
						intentFromGallery.putExtra("curMaxSize", PIC_MAX);
						intentFromGallery.putExtra("curSize",
								gridAdapter.list.size());
						intentFromGallery.putExtra("type", "cyty");
						startActivityForResult(intentFromGallery,
								IMAGE_REQUEST_CODE);
						overridePendingTransition(R.anim.enter_righttoleft,
								R.anim.exit_righttoleft);

					}
				});
			}
		});
		gridAdapter.setDeleteClickedListener(new OnDeleteClickedListener() {

			@Override
			public void onDelClicked(final int position) {
				final ATMDialog dialog = new ATMDialog(DiscAddActivity.this,
						DialogType.TWO_BUTTON);

				dialog.setText(R.string.dialog_del, R.string.dialog_exit);
				dialog.setDesc(R.string.dialog_del_desc);
				final int id = position;
				dialog.setOnDialogLister(new OnDialogListener() {

					@Override
					public void onConfirm() {
						dialog.dismiss();
						// 删除相关？？
						// if (flag && gridAdapter.list.get(position).getType()
						// == 4) {// 删除网络图片
						// delImg((String)
						// (gridAdapter.list.get(position).getImageInfo()), id);
						// return;
						// }

						gridAdapter.list.remove(id);
						try {
							if (treeMap.containsKey(id)) {
								treeMap.remove(id);
							}
						} catch (Exception e) {

						}
						CommonTools.setGridViewHeight(gridView, 4,
								ScreenUtils.dp2px(90));
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
			// reSizePhoto(data.getData());
			// getImageToView(IMAGE_REQUEST_CODE,data);
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
			// if (data != null) {
			// getImageToView(data);
			// }
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

	private void submitCY() {
	}

	private ProgressDialog progressDialog;

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {

			switch (msg.what) {
			case 0: // 开始提交
				if (progressDialog != null) {
					progressDialog.cancel();
				}
				submitCY();
				break;
			case 1: // 图片压缩失败
				Toast.makeText(DiscAddActivity.this, "图片加载失败,请重新选择!",
						Toast.LENGTH_SHORT).show();
				break;

			case 2: // 异步压缩图片
				if (treeMap.size() > 0) {
					int num = treeMap.firstKey();
					cytjHelperImgPro(num, treeMap.get(num));

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

	// 异步压缩线程
	/**
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
				if (cyHelper.getType() == 2
						&& ((String) (cyHelper.getImageInfo()))
								.equals(urlImgStr)) {
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

	private BaseSelectAdapter mSelectAdapter;
	private LinkedList<DiscTypesBean> mSelectSourceList = new LinkedList<DiscTypesBean>();
	private DiscTypesBean mDiscType = new DiscTypesBean("", "不限");

	private void initPop() {

		mSelectAdapter = new BaseSelectAdapter(this, true);
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
				mTvCatogery.setText(mDiscType.getMemo());
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

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void configActionBar() {
		mTopBackBtn.setVisibility(View.VISIBLE);
		mTopTitle.setText(R.string.activity_title_disc_add);
		mTopSend.setText(R.string.btn_text_commit);
		LayoutParams params = mTopSend.getLayoutParams();
		params.width = 120;
		params.height = LayoutParams.MATCH_PARENT;
		mTopSend.setLayoutParams(params);
		mTopSend.setVisibility(View.VISIBLE);
		mTopSend.setTextSize(14);
		mTopSend.setTextColor(getResources().getColor(
				R.drawable.text_white_gray));
		mTopSend.setBackgroundResource(android.R.color.transparent);
	}

	@OnClick({ R.id.btn_home_menu, R.id.btn_home_message,
			R.id.tv_disc_add_catogery, R.id.cytj_btn_img_none_add })
	public void OnClick(View v) {
		switch (v.getId()) {
		case R.id.btn_home_menu: // 点击返回按钮
			onBackPressed();
			break;
		case R.id.btn_home_message:// 发送
			
			if(TextUtils.isEmpty(mDiscType.getDictValue())){
				showToast("请选择类型");
				return;
			}
			
			if(TextUtils.isEmpty(mEtContent.getText().toString().trim())){
				showToast("请输入内容");
				return;
			}
			
			if(gridAdapter.list.size() == 0){
				showToast("请上传1-6张图片");
				return;
			}
			
			if (treeMap.size() > 0) {
				progressDialog = ProgressDialog.show(DiscAddActivity.this,
						null, "加载图片中...", true);
				handler.sendEmptyMessage(4);
				return;
			}
			handler.sendEmptyMessage(0); // 满足提交条件
			sendRequest();
			break;
		case R.id.tv_disc_add_catogery:// 选择分类
			mPopType.showAsDropDown(mTvLine);
			break;
		case R.id.cytj_btn_img_none_add:// 图片选择
			dialogDispather();
		default:
			break;
		}
	}

	// 添加图片dialog处理
	protected void dialogDispather() {

		// 选择图片Dialog
		dialog = new Dialog(DiscAddActivity.this, R.style.MyDialog);
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
				File dir = new File(Environment.getExternalStorageDirectory()
						+ File.separator + "/imag");
				if (!dir.exists())
					dir.mkdirs();
				File f = new File(dir, sdf.format(new Date()) + ".jpg");// localTempImgDir和localTempImageFileName是自己定义的名字
				uri = Uri.fromFile(f);

				intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
				startActivityForResult(intent, CAMERA_REQUEST_CODE);
				overridePendingTransition(R.anim.enter_righttoleft,
						R.anim.exit_righttoleft);

			}
		});
		btn_gray.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (gridAdapter.list.size() >= PIC_MAX) {
					return;
				}

				// 打开最近照片
				Intent intentFromGallery = new Intent(DiscAddActivity.this,
						PhotoWallActivity.class);
				intentFromGallery.putExtra("curMaxSize", PIC_MAX);
				intentFromGallery.putExtra("curSize", gridAdapter.list.size());
				intentFromGallery.putExtra("type", "order");
				startActivityForResult(intentFromGallery, IMAGE_REQUEST_CODE);
				overridePendingTransition(R.anim.enter_righttoleft,
						R.anim.exit_righttoleft);
				dialog.dismiss();
			}
		});

	}

	@Override
	protected void sendRequest() {
		super.sendRequest();
		RequestParams params = new RequestParams();
        params.addBodyParameter("a_id", GCApplication.sApp.getDefaultAreaInfo().getA_id());
		params.addBodyParameter("appId", GCApplication.sApp.getUserInfo().getId()+"");
		params.addBodyParameter("types", mDiscType.getDictValue());
		params.addBodyParameter("content", mEtContent.getText().toString()
				.trim());
		for (int k = 0; k < gridAdapter.list.size(); k++) {
			String imgStr = ((CYTJHelper) (gridAdapter.list.get(k)))
					.getImageStream();
			switch (k) {
			case 0:
				params.addBodyParameter("pic1", imgStr);
				break;
			case 1:
				params.addBodyParameter("pic2", imgStr);
				break;
			case 2:
				params.addBodyParameter("pic3", imgStr);
				break;
			case 3:
				params.addBodyParameter("pic4", imgStr);
				break;
			case 4:
				params.addBodyParameter("pic5", imgStr);
				break;
			case 5:
				params.addBodyParameter("pic6", imgStr);
				break;
			default:
				break;
			}
		}
		HttpUtil httpUtil = new HttpUtil(new RequestHandler(
				DiscAddActivity.this, "", getResources().getString(
						R.string.loading), 0));
		// 回调函数
		RequestHttpCallBack<String> callback = httpUtil.new RequestHttpCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> result) {
				super.onSuccess(result);
				LogUtils.d(result.toString());
				try {
					Gson mGson = new Gson();
					@SuppressWarnings("unchecked")
					HashMap<String, Object> map = mGson.fromJson(result.result,
							HashMap.class);
					String result1 = map.get("result").toString();
					LogUtils.d("Add Disc : " + result1);
					if ("1".equals(result1)) {
						Toast.makeText(DiscAddActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
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
		// 发送数据 post方式（提交保存）
		httpUtil.post(WebConstants.METHOD_DISC_ADD, params, callback);
	}

	@Override
	public void requestCallBack(String dataJson, RequestType type) {

	}
}
