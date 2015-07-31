package com.xinxin.everyxday.fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xinxin.everyxday.R;

import me.kaede.tagview.OnTagClickListener;
import me.kaede.tagview.Tag;
import me.kaede.tagview.TagView;


/**
 * Created by xinxin on 15/7/29.
 */
public class FragmentShareProduct extends Fragment {

    private View shareView;
    private TagView tagView;

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
        tagView = (TagView) shareView.findViewById(R.id.tagview);
        //SET LISTENER
        tagView.setOnTagClickListener(new OnTagClickListener() {

            @Override
            public void onTagClick(Tag tag, int position) {
                switch (position){
                    case 0:
                        tag.tagTextColor = getActivity().getResources().getColor(R.color.ac_title_color);
                        break;
                }
                Toast.makeText(getActivity(), "click tag id=" + tag.id + " position=" + position, Toast.LENGTH_SHORT).show();
            }
        });
        Tag tag = new Tag("有创意");
        tag.tagTextColor = this.getResources().getColor(R.color.app_main_theme_color);
        tag.background = this.getResources().getDrawable(R.drawable.bg_tag);
        tagView.addTag(tag);
//        String[] tags = getResources().getStringArray(R.array.continents);
//        tagView.addTags(tags);
//        <string-array name="continents">
//        <item>有创意</item>
//        <item>好玩</item>
//        <item>逼格足够高</item>
//        <item>科技感</item>
//        <item>美味</item>
//        <item>简洁</item>
//        <item>有个性</item>
//        <item>有范</item>
//        <item>无印良品</item>
//        <item>中国制造</item>
//        <item>大片组图</item>
//        <item>原创</item>
//        <item>可穿戴力作</item>
//        <item>文艺小清新</item>
//        </string-array>

        return shareView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
