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
public class FragmentSetting extends Fragment{

    private View settingView;

    public static FragmentSetting newInstance(Bundle args) {
        FragmentSetting myFragment = new FragmentSetting();
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        settingView = inflater.inflate(R.layout.setting, null);
        return settingView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
