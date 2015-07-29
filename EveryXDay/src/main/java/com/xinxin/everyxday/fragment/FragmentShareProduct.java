package com.xinxin.everyxday.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xinxin.everyxday.R;

import me.gujun.android.taggroup.TagGroup;

/**
 * Created by xinxin on 15/7/29.
 */
public class FragmentShareProduct extends Fragment {

    private View shareView;
    private TagGroup mTagGroup;

    public static FragmentShareProduct newInstance(Bundle args) {
        FragmentShareProduct myFragment = new FragmentShareProduct();
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        shareView = inflater.inflate(R.layout.share_new, null);
        mTagGroup = (TagGroup)shareView.findViewById(R.id.tag_group);
        mTagGroup.setTags(new String[]{"有创意", "好玩", "逼格足够高", "科技感", "美味", "简洁", "有个性", "有范", "无印良品", "中国制造", "大片组图", "原创", "可穿戴力作", "文艺小清新"});
        return shareView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
