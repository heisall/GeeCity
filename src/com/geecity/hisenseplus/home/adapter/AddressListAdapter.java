package com.geecity.hisenseplus.home.adapter;

import java.util.LinkedList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geecity.hisenseplus.home.R;
import com.geecity.hisenseplus.home.bean.AddressListBean;
import com.geecity.hisenseplus.home.utils.ViewHolder;

/**
 * 收货地址列表适配器
 * */
public class AddressListAdapter extends BaseAdapter {

  Context context;
  public LinkedList<AddressListBean> list;

  public AddressListAdapter(Context context) {
    this.context = context;
    list = new LinkedList<AddressListBean>();
  }

  public void setArrayList(LinkedList<AddressListBean> mList) {
    this.list = mList;
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
  public AddressListBean getItem(int position) {
    return list.get(position);
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  @Override
  public View getView(final int position, View convertView, ViewGroup parent) {
    if (null == convertView) {
      convertView = LayoutInflater.from(context).inflate(R.layout.item_address_list, null);
    }
    RelativeLayout layout = ViewHolder.get(convertView, R.id.rly_item_address);
    TextView name = ViewHolder.get(convertView, R.id.tv_item_adrs_name);
    setOnClicked(name, position);
    TextView phone = ViewHolder.get(convertView, R.id.tv_item_adrs_phone);
    setOnClicked(phone, position);
    TextView adrs = ViewHolder.get(convertView, R.id.tv_item_adrs_address);
    setOnClicked(adrs, position);
    ImageView img = ViewHolder.get(convertView, R.id.img_item_adrs_selected);
    setOnClicked(layout, position);
    Button btnDefault = ViewHolder.get(convertView, R.id.btn_address_default);
    btnDefault.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View arg0) {
        if (listener != null) {
          listener.onDefaultSet(position);
        }
      }
    });

    TextView tvDefault = ViewHolder.get(convertView, R.id.tv_item_adrs_flag_default);
    tvDefault.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View arg0) {
        if (listener != null) {
          listener.onDefaultSet(position);
        }
      }
    });

    Button btnDele = ViewHolder.get(convertView, R.id.btn_address_del);
    btnDele.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View arg0) {
        if (listener != null) {
          listener.onDele(position);
        }
      }
    });
    TextView tvDele = ViewHolder.get(convertView, R.id.tv_item_adrs_delet);
    tvDele.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View arg0) {
        if (listener != null) {
          listener.onDele(position);
        }
      }
    });

    AddressListBean bean = getItem(position);
    if (bean != null) {
      name.setText(bean.getConsignee());
      phone.setText(bean.getPhone_tel());
      btnDefault.setBackgroundResource(bean.isIs_default()
          ? R.drawable.btn_address_defalut
          : R.drawable.btn_address_undefalut);
      tvDefault.setText(bean.isIs_default() ? "默认" : "设为默认");
      tvDefault.setTextColor(bean.isIs_default() ? context.getResources().getColor(
          R.color.txt_hisense_green) : context.getResources().getColor(R.color.txt_hint_gray));
      adrs.setText(bean.getRegion_name() + " " + bean.getAddress());
      img.setVisibility(bean.isSelected() ? View.VISIBLE : View.INVISIBLE);
    }
    return convertView;
  }

  private void setOnClicked(View view, final int position) {
    view.setOnClickListener(new OnClickListener() {
      
      @Override
      public void onClick(View arg0) {
        if (listener != null) {
          listener.onSelected(position);
        }
      }
    });
  }

  public interface OnCustomClickedListener {
    public void onDefaultSet(int postion);
    public void onDele(int position);
    public void onSelected(int position);
  }

  private OnCustomClickedListener listener;

  public void setOnCustomClickedListener(OnCustomClickedListener listener) {
    this.listener = listener;
  }
}
