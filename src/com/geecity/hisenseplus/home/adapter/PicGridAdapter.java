package com.geecity.hisenseplus.home.adapter;

import java.util.LinkedList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.activity.disc.DiscAddActivity;
import com.geecity.hisenseplus.home.utils.BitmapAsset;
import com.geecity.hisenseplus.home.utils.CYTJHelper;
import com.geecity.hisenseplus.home.utils.SDCardImageLoader;
import com.geecity.hisenseplus.home.utils.ViewHolder;
import com.ibill.lib.tools.ScreenUtils;

/**
 * 图片适配器
 * 
 * @author billkong
 * 
 */
public class PicGridAdapter extends BaseAdapter {

    protected static final String TAG = "PicGridAdapter";
    private Context context;
    public LinkedList<CYTJHelper> list; // 最大长度应该为6,最小长度为0,其中最后一个数字为代表为空
    private SDCardImageLoader loader;
    private OnAddClickedListener mAddClickedListener;
    private OnDeleteClickedListener deleteClickedListener;
    private ImageView leftIMG;
    CYTJHelper cytjHelper;
    boolean deltype = false;// 判断类型 长按后再单击
    private int maxSize;

    public void setOnAddClickedListener(OnAddClickedListener l) {
        this.mAddClickedListener = l;
    }

    public void setDeleteClickedListener(OnDeleteClickedListener deleteClickedListener) {
        this.deleteClickedListener = deleteClickedListener;
    }

    public PicGridAdapter(Context context, int maxSize) {
        super();
        this.context = context;
        this.maxSize = maxSize;
        list = new LinkedList<CYTJHelper>();

        // lsit最大长度应该为6,最小长度为0,其中最后一个数字为代表为空,此处生成最后一个
        cytjHelper = new CYTJHelper();
        cytjHelper.setType(-1);
        cytjHelper.setIndex(-1);


        loader = new SDCardImageLoader(ScreenUtils.getScreenWidth(context),
                                        ScreenUtils.getScreenHeight(context));
    }

    public void clear() {
        list.clear();
    }

    @Override
    public void notifyDataSetChanged() {

        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        switch (maxSize) {
            case 3:
                if (list.size() == 3) {
                    return list.size();
                }
                break;
            case 5: 
                if (list.size() == 5) {
                    return list.size();
                }
                break;
        }
        return list.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        if (list == null || list.size() == 0 || position > list.size()) {
            return null;
        }
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolders holders = new ViewHolders();

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cytj_grid, null);
        }
        CYTJHelper helper = null;
        if (position == list.size()) {
            helper = cytjHelper;
        } else {
            helper = list.get(position);
        }

        leftIMG = (ImageView) ViewHolder.get(convertView, R.id.cytj_img_view);
        final TextView del_text = ViewHolder.get(convertView, R.id.cytj_del_text);
        holders.imageView = (ImageView) ViewHolder.get(convertView, R.id.cytj_img_view);
        Button button = ViewHolder.get(convertView, R.id.cytj_btn_img_add);
        if (position == list.size()) {
            if (maxSize == DiscAddActivity.PIC_MAX) {
                if (list.size() >= DiscAddActivity.PIC_MAX) {
                    button.setVisibility(View.GONE);
                } else {
                    button.setVisibility(View.VISIBLE);
                }

                leftIMG.setVisibility(View.GONE);
                del_text.setVisibility(View.GONE);

                button.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (mAddClickedListener != null) {
                            mAddClickedListener.onAddClicked(position);

                        }
                    }
                });
                return convertView;
            } else {
                if (list.size() >= 3) {
                    button.setVisibility(View.GONE);
                } else {
                    button.setVisibility(View.VISIBLE);
                }

                leftIMG.setVisibility(View.GONE);
                del_text.setVisibility(View.GONE);
                button.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (mAddClickedListener != null) {
                            mAddClickedListener.onAddClicked(position);

                        }
                    }
                });
                return convertView;
            }

        }
        if (position < list.size()) {

            switch (helper.getType()) {
                case -1: // 天生最后一个数
                    break;
                case 0: // id
                    int img_id = Integer.valueOf((String) (helper.getImageInfo() + ""));
                    leftIMG.setImageResource(img_id);
                    break;
                case 1: // bit
                    Bitmap bitmap = (Bitmap) (helper.getImageInfo());
                    leftIMG.setImageBitmap(bitmap);
                    break;
                case 2: // path(本地地址)
                    String path = (String) (helper.getImageInfo());
                    leftIMG.setTag(path);
                    loader.loadImage(4, path, leftIMG);
                    break;
                case 4:// 网络图片地址
                    BitmapAsset.displaySqu(context, leftIMG, (String) (helper.getImageInfo()));
                    break;
            }
        }
        leftIMG.setVisibility(View.VISIBLE);
        button.setVisibility(View.GONE);

        holders.imageView.setColorFilter(context.getResources().getColor(R.color.image_checked_bg));
        del_text.setVisibility(View.VISIBLE);
        holders.imageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (deleteClickedListener != null) {
                    deleteClickedListener.onDelClicked(position);
                }

            }
        });

        return convertView;

    }

    private class ViewHolders {
        ImageView imageView;
        CheckBox checkBox;
    }

    public interface OnDeleteClickedListener {
        void onDelClicked(int position);
    }

    public interface OnAddClickedListener {
        void onAddClicked(int position);
    }

    public interface OnCommfitClickedListener {
        void onComClicked(int position);
    }
}
