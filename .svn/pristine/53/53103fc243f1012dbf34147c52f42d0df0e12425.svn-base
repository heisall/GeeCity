package com.geecity.hisenseplus.home.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import com.geecity.hisenseplus.home.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;

/**
 * 
 * 该类用于封装BitmapUtils
 * 
 * */
public class BitmapAsset {
    private static final boolean DEBUG = false;
    static BitmapUtils bitmapUtils;

    // 矩形默认图片
    public static <T extends View> void displayRec(Context context, T container, String imgPath) {
        if (DEBUG) {
            return;
        }
        if(bitmapUtils == null){
            initBitmapUtils(context);
        }
        bitmapUtils.display(container, imgPath);
    }

    // 正方形默认图片
    public static <T extends View> void displaySqu(Context context, T container, String imgPath) {
        if (DEBUG) {
            return;
        }
        if(bitmapUtils == null){
            initBitmapUtils(context);
        }
        bitmapUtils.display(container, imgPath);
    }

    // 手动设置默认失败图片
    public static <T extends View> void displayImg(Context context, T container, String imgPath,
                    int drawableId) {
        BitmapUtils bUtils = new BitmapUtils(context);

        if (0 < drawableId) {
//        	bUtils.configDefaultLoadingImage(drawableId);
        	bUtils.configDefaultLoadFailedImage(drawableId);
        }
        bUtils.display(container, imgPath);
    }

    private static void initBitmapUtils(Context context) {
        bitmapUtils = new BitmapUtils(context);
    }
    
    public static int convertBg(String type){
        if ("1".equals(type)) {
            return R.drawable.icn_disc_red;
        } else if ("2".equals(type)) {
            return R.drawable.icn_disc_blue;
        } else if("3".equals(type)) {
            return R.drawable.icn_disc_orange;
        } 
        return R.drawable.icn_disc_orange;
    }

	public static Bitmap getBitmap(Context context, String photo) {
		// TODO Auto-generated method stub

        if(bitmapUtils == null){
            initBitmapUtils(context);
        }
        return bitmapUtils.getBitmapFromMemCache(photo, new BitmapDisplayConfig());
	}

}
