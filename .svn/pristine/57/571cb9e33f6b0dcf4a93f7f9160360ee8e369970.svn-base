package com.geecity.hisenseplus.home.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.geecity.hisenseplus.home.R;

/**
 * 圆形ImageView，可设置最多两个宽度不同且颜色不同的圆形边框。 在首页面显示用户的头像
 * 
 * @author wangtao
 * @since 2014.08.26
 * @version 1.0
 */
public class RoundImageView extends ImageView {
	private int mBorderThickness = 0;
	private Context mContext;
	private int mDefaultColor = 0xFFFFFFFF;
	// 如果只有其中一个有值，则只画一个圆形边框
	private int mBorderOutsideColor = 0;
	private int mBorderInsideColor = 0;
	// 控件默认长、宽
	private int mDefaultWidth = 0;
	private int mDefaultHeight = 0;

	private Matrix mMatrix; // 缩放比例
	private boolean mClickFlag = true; // 是否关闭点击事件

	@SuppressLint("ClickableViewAccessibility")
    @Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!mClickFlag) {
			return false;
		}
		return super.onTouchEvent(event);
	}

	public void setClickFlag(boolean clickFlag) {
		this.mClickFlag = clickFlag;
	}

	public RoundImageView(Context context) {
		super(context);
		mContext = context;
	}

	public RoundImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		setCustomAttributes(attrs);
	}

	public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		setCustomAttributes(attrs);
	}

	@SuppressLint("Recycle")
    private void setCustomAttributes(AttributeSet attrs) {
		TypedArray a = mContext.obtainStyledAttributes(attrs,
				R.styleable.roundedimageview);
		mBorderThickness = a.getDimensionPixelSize(
				R.styleable.roundedimageview_border_thickness, 0);
		mBorderOutsideColor = a.getColor(
				R.styleable.roundedimageview_border_outside_color,
				mDefaultColor);
		mBorderInsideColor = a
				.getColor(R.styleable.roundedimageview_border_inside_color,
						mDefaultColor);
	}

	public Matrix getMatrix(int width, int height) {
		mMatrix = new Matrix();
		mMatrix.setScale(1f * getWidth() / width, 1f * getWidth() / height);
		return mMatrix;
	}

	@Override
	protected void onDraw(Canvas canvas) {

		Drawable drawable = getDrawable();
		if (drawable == null) {
			try {
				drawable = new BitmapDrawable(getResources(),BitmapFactory.decodeResource(getResources(), R.drawable.icon_employ_master));
			} catch (Exception w) {
				return;
			}
		}
		if (getWidth() == 0 || getHeight() == 0) {
			return;
		}
		this.measure(0, 0);
		if (drawable.getClass() != BitmapDrawable.class) {
			return;
		}

		Bitmap b = ((BitmapDrawable) drawable).getBitmap();
		if (b == null) {
			return;
		}
		Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);
		if (mDefaultWidth == 0) {
			mDefaultWidth = getWidth();

		}
		if (mDefaultHeight == 0) {
			mDefaultHeight = getWidth();
		}

		int radius = 0;
		if (mBorderInsideColor != mDefaultColor
				&& mBorderOutsideColor != mDefaultColor) {// 定义画两个边框，分别为外圆边框和内圆边框
			radius = (mDefaultWidth < mDefaultHeight ? mDefaultWidth
					: mDefaultHeight) / 2 - 2 * mBorderThickness;
			// 画内圆
			drawCircleBorder(canvas, radius + mBorderThickness / 2,
					mBorderInsideColor);
			// 画外圆
			drawCircleBorder(canvas, radius + mBorderThickness
					+ mBorderThickness / 2, mBorderOutsideColor);
		} else if (mBorderInsideColor != mDefaultColor
				&& mBorderOutsideColor == mDefaultColor) {// 定义画一个边框
			radius = (mDefaultWidth < mDefaultHeight ? mDefaultWidth
					: mDefaultHeight) / 2 - mBorderThickness;
			drawCircleBorder(canvas, radius + mBorderThickness / 2,
					mBorderInsideColor);
		} else if (mBorderInsideColor == mDefaultColor
				&& mBorderOutsideColor != mDefaultColor) {// 定义画一个边框
			radius = (mDefaultWidth < mDefaultHeight ? mDefaultWidth
					: mDefaultHeight) / 2 - mBorderThickness;
			drawCircleBorder(canvas, radius + mBorderThickness / 2,
					mBorderOutsideColor);
		} else {// 没有边框
			radius = (mDefaultWidth < mDefaultHeight ? mDefaultWidth
					: mDefaultHeight) / 2;
		}
		Bitmap roundBitmap = getCroppedRoundBitmap(bitmap, radius);
		canvas.drawBitmap(roundBitmap, mDefaultWidth / 2 - radius,
				mDefaultHeight / 2 - radius, null);
	}

	/**
	 * 获取裁剪后的圆形图片
	 * 
	 * @param radius
	 *            半径
	 */
	public Bitmap getCroppedRoundBitmap(Bitmap bmp, int radius) {

		Bitmap scaledSrcBmp;

		// 为了防止宽高不相等，造成圆形图片变形，因此截取长方形中处于中间位置最大的正方形图片
		int bmpWidth = bmp.getWidth();
		int bmpHeight = bmp.getHeight();
		int squareWidth = 0, squareHeight = 0;
		int x = 0, y = 0;
		Bitmap squareBitmap;
		if (bmpHeight > bmpWidth) {// 高大于宽
			squareWidth = squareHeight = bmpWidth;
			x = 0;
			y = (bmpHeight - bmpWidth) / 2;
			// 截取正方形图片
			squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth,
					squareHeight);
		} else if (bmpHeight < bmpWidth) {// 宽大于高
			squareWidth = squareHeight = bmpHeight;
			x = (bmpWidth - bmpHeight) / 2;
			y = 0;
			squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth,
					squareHeight);
		} else {
			squareBitmap = bmp;
		}

		scaledSrcBmp = Bitmap.createScaledBitmap(squareBitmap, getWidth(),
				getWidth(), true);

		Bitmap output = Bitmap.createBitmap(scaledSrcBmp.getWidth(),
				scaledSrcBmp.getHeight(), Config.ARGB_8888);

		Canvas canvas = new Canvas(output);

		Paint paint = new Paint();
		Rect rect = new Rect(0, 0, scaledSrcBmp.getWidth(),
				scaledSrcBmp.getHeight());

		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		canvas.drawARGB(0, 0, 0, 0);
		canvas.drawCircle(scaledSrcBmp.getWidth() / 2,
				scaledSrcBmp.getHeight() / 2, scaledSrcBmp.getWidth() / 2,
				paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(scaledSrcBmp, rect, rect, paint);
		bmp = null;
		squareBitmap = null;
		scaledSrcBmp = null;
		return output;
	}

	/**
	 * 边缘画圆
	 */
	private void drawCircleBorder(Canvas canvas, int radius, int color) {
		Paint paint = new Paint();
		/* 去锯齿 */
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		paint.setColor(color);
		/* 设置paint的　style　为STROKE：空心 */
		paint.setStyle(Paint.Style.STROKE);
		/* 设置paint的外框宽度 */
		paint.setStrokeWidth(mBorderThickness);
		canvas.drawCircle(getWidth() / 2, getWidth() / 2, radius, paint);
	}

}
