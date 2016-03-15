package com.geecity.hisenseplus.home.adapter;

import java.util.LinkedList;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.bean.CommentBean;
import com.geecity.hisenseplus.home.utils.BitmapAsset;
import com.geecity.hisenseplus.home.utils.ViewHolder;
import com.geecity.hisenseplus.home.view.RoundImageView;

/**
 * 物业监督--物业员工列表
 * */
public class CommentsAdapter extends BaseAdapter {

    Context context;
    public LinkedList<CommentBean> list;

    public CommentsAdapter(Context context) {
        this.context = context;
        list = new LinkedList<CommentBean>();
    }

    public void setArrayList(LinkedList<CommentBean> cmList) {
        this.list = cmList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        int count = 0;
        if (list != null) {
            count = list.size();
        }
        return count;
    }

    @Override
    public CommentBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_pinglun_list, null);
        }
        RoundImageView head = ViewHolder.get(convertView, R.id.item_img_head);
        TextView name = ViewHolder.get(convertView, R.id.item_tv_name);
        TextView post = ViewHolder.get(convertView, R.id.item_tv_date);
        TextView content = ViewHolder.get(convertView, R.id.item_tv_content);
        CommentBean bean = list.get(position);
        BitmapAsset.displayImg(context, head, bean.getPhoto(), R.drawable.icon_default_head);
        name.setText(TextUtils.isEmpty(bean.getNickName())?"":bean.getNickName());
        post.setText(bean.getReleaseTime());
        content.setText(bean.getLeaveWord());
        return convertView;
    }

}
