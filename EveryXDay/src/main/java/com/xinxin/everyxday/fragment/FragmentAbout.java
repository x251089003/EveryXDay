package com.xinxin.everyxday.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.xinxin.everyxday.R;

/**
 * Created by xinxin on 15/7/27.
 */
public class FragmentAbout extends Fragment {

    private View aboutView;

    public static FragmentAbout newInstance(Bundle args) {
        FragmentAbout myFragment = new FragmentAbout();
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        aboutView = inflater.inflate(R.layout.about, null);
        return aboutView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
