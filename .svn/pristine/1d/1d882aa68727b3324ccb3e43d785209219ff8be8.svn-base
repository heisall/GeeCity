package com.geecity.hisenseplus.home.adapter;



import java.io.File;
import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.utils.SDCardImageLoader;
import com.ibill.lib.tools.ScreenUtils;




/**
 * PhotoWall中GridView的适配器
 * @author bill kong
 * @create 20150505
 *
 */

public class PhotoWallAdapter extends BaseAdapter {
    private Context context;
    public ArrayList<ImgHelper> imagePathList = null;
    private SDCardImageLoader loader;


    public PhotoWallAdapter(Context context) {
        this.context = context;
        this.imagePathList = new ArrayList<ImgHelper>();
        loader = new SDCardImageLoader(ScreenUtils.getScreenHeight(context), ScreenUtils.getScreenHeight(context));
    }

    @Override
    public int getCount() {
        return imagePathList.size();
    }

    @Override
    public Object getItem(int position) {
        return imagePathList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	final ImgHelper imgHelper = imagePathList.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.adpter_photo_wall_item, null);
		}
		final ImageView imageView = (ImageView) convertView
				.findViewById(R.id.photo_wall_item_photo);
		final CheckBox checkBox = (CheckBox) convertView
				.findViewById(R.id.photo_wall_item_cb);

		if(imgHelper.isSelected()){
				imageView.setSelected(true);
				imageView.setColorFilter(context.getResources().getColor(
						R.color.transparent));
				imgHelper.setSelected(true);
			} else {
				imageView.setColorFilter(null);
				imageView.setSelected(false);
				imgHelper.setSelected(false);
		}
			checkBox.setChecked(imageView.isSelected());
		checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					imageView.setSelected(true);
					imageView.setColorFilter(context.getResources().getColor(
							R.color.transparent));
					imgHelper.setSelected(true);
				} else {
					imageView.setColorFilter(null);
					imageView.setSelected(false);
					imgHelper.setSelected(false);
				}
			}
		});
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				imageView.setSelected(!imageView.isSelected());
				checkBox.setChecked(imageView.isSelected());
				imgHelper.setSelected(imageView.isSelected());
			}
		});

        loader.loadImage(4, imgHelper.getImgPath(), imageView);
        return convertView;
    }
    
    @Override
    public void notifyDataSetChanged() {
    	super.notifyDataSetChanged();
    }
    
	/**
	 * 使用ContentProvider读取SD卡最近图片。
	 */
	public void getLatestImagePaths(int maxCount) {
		imagePathList.clear();
		Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		String key_MIME_TYPE = MediaStore.Images.Media.MIME_TYPE;
		String key_DATA = MediaStore.Images.Media.DATA;

		ContentResolver mContentResolver = context
				.getContentResolver();

		// 只查询jpg和png的图片,按最新修改排序
		Cursor cursor = mContentResolver.query(mImageUri,
				new String[] { key_DATA }, key_MIME_TYPE + "=? or "
						+ key_MIME_TYPE + "=? or " + key_MIME_TYPE + "=?",
				new String[] { "image/jpg", "image/jpeg", "image/png" },
				MediaStore.Images.Media.DATE_MODIFIED);

		if (cursor != null) {
			// 从最新的图片开始读取.
			// 当cursor中没有数据时，cursor.moveToLast()将返回false
			if (cursor.moveToLast()) {

				while (true) {
					// 获取图片的路径
					String path = cursor.getString(0);
					imagePathList.add(new ImgHelper(path,false));

					if (imagePathList.size() >= maxCount
							|| !cursor.moveToPrevious()) {
						break;
					}
				}
			}
			cursor.close();
		}
	}
    
	
	/**
	 * 获取指定路径下的所有图片文件。
	 */
	public void getAllImagePathsByFolder(String folderPath) {
		imagePathList.clear();
		File folder = new File(folderPath);
		String[] allFileNames = folder.list();
		if (allFileNames == null || allFileNames.length == 0) {
			return;
		}

		for (int i = allFileNames.length - 1; i >= 0; i--) {
			// if (isImage(allFileNames[i])) {
			imagePathList.add(new ImgHelper(folderPath + File.separator + allFileNames[i], false));
			// }
		}
	}
	
    public ArrayList<String> getSelectPathList(){
    	ArrayList<String> strList = new ArrayList<String>();
    	for(int k=0;k<imagePathList.size();k++){
    		if(imagePathList.get(k).isSelected()){
    			strList.add(imagePathList.get(k).getImgPath());
    		}
    	}
    	return strList;
    }
    
    class ImgHelper{
    	private String imgPath;
    	private boolean isSelected;
    	public ImgHelper(String imgPath,boolean isSelected){
    		this.imgPath = imgPath;
    		this.isSelected = isSelected;
    	}
		public String getImgPath() {
			return imgPath;
		}
		public void setImgPath(String imgPath) {
			this.imgPath = imgPath;
		}
		public boolean isSelected() {
			return isSelected;
		}
		public void setSelected(boolean isSelected) {
			this.isSelected = isSelected;
		}
    }
    



}
