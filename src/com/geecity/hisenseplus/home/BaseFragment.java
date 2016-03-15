package com.geecity.hisenseplus.home;

import android.support.v4.app.Fragment;
import android.widget.Toast;

public abstract class BaseFragment extends Fragment {

   // private static final String TAG = "BaseFragment";

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    public abstract boolean onBackPressed();

    // public abstract void slideMenuEvent();


    public void showToast(int resId) {
        Toast.makeText(getActivity(), resId, Toast.LENGTH_SHORT).show();
    }

    public void showToast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    public void showToastLong(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
    }
    
}
