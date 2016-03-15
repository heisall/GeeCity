package com.geecity.hisenseplus.home.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.geecity.hisenseplus.home.R;

public class PicAddLayout extends RelativeLayout {

    private Context mCtx;
    private Button mBtn;
    private GridView mGv;

    public PicAddLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mCtx = context;
        LayoutInflater.from(context).inflate(R.layout.widgt_pics_add, this);
        mBtn = (Button) findViewById(R.id.btn_pics_add);
        mGv = (GridView) findViewById(R.id.gv_pics_add);
        
        mBtn.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                
            }
        });
        
        mGv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                
            }});
    }

}
