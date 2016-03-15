package com.geecity.hisenseplus.home.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.kobjects.base64.Base64;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.geecity.hisenseplus.home.activity.WebActivity;
import com.geecity.hisenseplus.home.activity.live.BusinessDetailActivity;
import com.geecity.hisenseplus.home.activity.live.GoodsDetailActivity;
import com.geecity.hisenseplus.home.activity.notice.NoticeDetailActivity;
import com.geecity.hisenseplus.home.bean.ADBean;
import com.geecity.hisenseplus.home.bean.BusinessListBean;
import com.geecity.hisenseplus.home.bean.GoodsListBean;
import com.ibill.lib.activity.BaseActivity;

public class CommonTools {
    
    /**
     * 
     * @param text
     * @return
     */
    public static boolean isIllegalPhone(String text){
        String phpneCheck = "^[1][3578][0-9]{9}$";
        Pattern regex = Pattern.compile(phpneCheck);
        Matcher matcher = regex.matcher(text);
        return matcher.matches();
    }
    
    /**
     * 
     * @param text
     * @return
     */
    public static boolean isIllegalNum1(String text){
        String phpneCheck = "^[0-9]{1}$";
        Pattern regex = Pattern.compile(phpneCheck);
        Matcher matcher = regex.matcher(text);
        return matcher.matches();
    }
    
    /**
     * 
     * @param text
     * @return
     */
    public static boolean isIllegalNum4(String text){
        String phpneCheck = "^[0-9]{4}$";
        Pattern regex = Pattern.compile(phpneCheck);
        Matcher matcher = regex.matcher(text);
        return matcher.matches();
    }

    public static boolean isIllegalPWD(String text) {
//        String phpneCheck = "^[0-9a-zA-Z]{6,20}$";
        //TODO iOS未加入密码内容合法性。为了防止iOS注册密码不能输入，次数仅加长度校验
        String phpneCheck = "^.{6,20}$";
        Pattern regex = Pattern.compile(phpneCheck);
        Matcher matcher = regex.matcher(text);
        return matcher.matches();
    }

	/**
	 * 启动电话拨打界面
	 * @param context
	 * @param telNum
	 */
    public static void launchCall(Context context, String telNum) {
        if(context == null || TextUtils.isEmpty(telNum)){
            return;
        }
        Intent intCall = new Intent();
        intCall.setAction(Intent.ACTION_CALL);
        intCall.setData(Uri.parse("tel:" + telNum));
        context.startActivity(intCall);

    }

    /**
     * 让嵌套的gridview正常显示出来(num表示每行显示几个)
     * */
    // // 显示list中嵌套的gridview

    public static void setGridViewHeight(GridView gv, int num, int height) {
        ListAdapter listAdapter = gv.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        // listItem.measure(0, 0);
        int rows = (listAdapter.getCount() - 1) / num + 1;
        totalHeight = height * rows;
        // for (int i = 0, len = listAdapter.getCount(); i < len; i++) { //
        // listAdapter.getCount()返回数据项的数目
        // View listItem = listAdapter.getView(i, null, gv);
        // listItem.measure(0, 0); // 计算子项View 的宽高
        // totalHeight += (listItem.getMeasuredHeight() - 1); //
        // 统计所有子项的总高度(减1是为了去掉分割线)
        // }

        ViewGroup.LayoutParams params = gv.getLayoutParams();
        params.height = totalHeight;
        // + (gv.getHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        gv.setLayoutParams(params);
    }

    /***
     * 解决内存溢出
     * 
     * */
    public static Bitmap fitSizeImg(String path) {

        if (path == null || path.length() < 1)
            return null;
        File file = new File(path);
        Bitmap resizeBmp = null;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 数字越大读出的图片占用的heap越小 不然总是溢出
        // if (file.length() < 20480) { // 0-20k
        // opts.inSampleSize = 1;
        // } else if (file.length() < 51200) { // 20-50k
        // opts.inSampleSize = 2;
        // } else if (file.length() < 307200) { // 50-300k
        // opts.inSampleSize = 4;
        // } else if (file.length() < 819200) { // 300-800k
        // opts.inSampleSize = 6;
        // } else if (file.length() < 1048576) { // 800-1024k
        // opts.inSampleSize = 8;
        // } else {
        // opts.inSampleSize = 10;
        // }

        if (file.length() < 307200) {
            opts.inSampleSize = 1;
        } else if (file.length() < 819200) {
            opts.inSampleSize = 2;
        } else {
            opts.inSampleSize = 4;
        }
        try {
            resizeBmp = BitmapFactory.decodeFile(file.getPath(), opts);
        } catch (OutOfMemoryError w) {
            opts.inSampleSize = 8;
            try {
                resizeBmp = BitmapFactory.decodeFile(file.getPath(), opts);
            } catch (OutOfMemoryError o) {
                return null;
            }
        }
        return resizeBmp;
    }

    /***
     * 图片压缩递归算法 解决内存溢出
     * 
     * */
    public static Bitmap fitSizeImg(Resources res, int drawableId,
            int inSampleSize) {

        if (drawableId == 0)
            return null;
        Bitmap resizeBmp = null;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = inSampleSize;
        try {
            resizeBmp = BitmapFactory.decodeResource(res, drawableId, opts);
        } catch (OutOfMemoryError w) {
            if (inSampleSize >= 8) {
                return null;
            }
            fitSizeImg(res, drawableId, 2 * inSampleSize);
        }
        return resizeBmp;
    }

    /**
     * 将图片转换成String字符串
     */
    public static String bitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 设置位图的压缩格式，质量为100%，并放入字节数组输出流中
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        // 将字节数组输出流转化为字节数组byte[]
        byte[] imagedata = baos.toByteArray();
        String iconString = new String(Base64.encode(imagedata));

        return iconString;
    }

    /**
     * 将Base64字符串转换成图片
     * */

    public static Bitmap stringtoBitmap(String string) {

        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
        // 　　// 将字符串转换成Bitmap类型

    }

    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证email格式
     * */
    public static boolean isEmailFormat(String line) {
        Pattern p = Pattern
                .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher m = p.matcher(line);
        return m.find();

    }
    public static int getVersionCode(Context context)//获取版本号(内部识别号)   
    {   
        try {   
           PackageInfo pi=context.getPackageManager().getPackageInfo(context.getPackageName(), 0);   
          return pi.versionCode;   
   } catch (NameNotFoundException e) {   
           e.printStackTrace();   
        return 0;   
       }   
    } 
    
    
    /**
     * 压缩图片
     * 
     * @Title: compressImage
     * @param image
     * @throws FileNotFoundException
     *             void
     */
    @SuppressLint("SimpleDateFormat")
    public static Bitmap compressImage(Bitmap image) throws FileNotFoundException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
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
    
    public static boolean isConnected(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }
    
    /**
     * 物业通知：1 社区活动：2 商户:3 商品：4
     */
    public static void adClick(BaseActivity context,LinkedList<ADBean> ads, int index) {
        if(ads != null && index < ads.size()){
            ADBean bean = ads.get(index);
            Bundle b = new Bundle();
            if(Const.LINK_TYPE.WEB.equals(bean.getTypes())){
                b.putString(WebActivity.KEY_YURL, bean.getUrl());
                context.startNextActivity(b, WebActivity.class);
            }else{
                if("4".equals(bean.getUrl())){
                    GoodsListBean gb = new GoodsListBean();
                    gb.setGoods_id(Integer.parseInt(bean.getContent()));
                    b.putSerializable(GoodsDetailActivity.KEY_DETAIL_BEAN, gb);
                    context.startNextActivity(b, GoodsDetailActivity.class);
                }else if("3".equals(bean.getUrl())){
                    BusinessListBean bbean = new BusinessListBean();
                    bbean.setId(Integer.parseInt(bean.getContent()));
                    b.putSerializable(BusinessDetailActivity.BUSINESS_BEAN, bbean);
                    context.startNextActivity(b, BusinessDetailActivity.class);
                }else if("2".equals(bean.getUrl()) || "1".equals(bean.getUrl())){
                    b.putString(NoticeDetailActivity.KEY_NOTICE_ID, bean.getContent());
                    context.startNextActivity(b, NoticeDetailActivity.class);
                }else{
                    
                }
            }
        }
    }
}
