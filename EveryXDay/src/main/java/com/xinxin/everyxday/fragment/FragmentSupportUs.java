package com.xinxin.everyxday.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xinxin.everyxday.R;

/**
 * Created by xinxin on 15/7/28.
 */
public class FragmentSupportUs extends Fragment {

    private View supportView;

    public static FragmentSupportUs newInstance(Bundle args) {
        FragmentSupportUs myFragment = new FragmentSupportUs();
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        supportView = inflater.inflate(R.layout.supportus, null);
        return supportView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
