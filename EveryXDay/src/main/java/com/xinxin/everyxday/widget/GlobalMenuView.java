package com.xinxin.everyxday.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.xinxin.everyxday.R;

public class GlobalMenuView extends ListView implements View.OnClickListener {

    private OnHeaderClickListener onHeaderClickListener;
    private GlobalMenuAdapter globalMenuAdapter;
    private ImageView ivUserProfilePhoto;
    private int avatarSize;

    public GlobalMenuView(Context context) {
        super(context);
        init();
    }

    private void init() {
        setChoiceMode(CHOICE_MODE_SINGLE);
        setDivider(getResources().getDrawable(android.R.color.transparent));
        setDividerHeight(0);
        setBackgroundColor(Color.WHITE);

//        setupHeader();
        setupAdapter();
    }

    private void setupAdapter() {
//        globalMenuAdapter = new GlobalMenuAdapter(getContext(),this);
//        setAdapter(globalMenuAdapter);
    }

    private void setupHeader() {
        this.avatarSize = getResources().getDimensionPixelSize(R.dimen.global_menu_avatar_size);

        setHeaderDividersEnabled(true);
        View vHeader = LayoutInflater.from(getContext()).inflate(R.layout.view_global_menu_header, null);
        ivUserProfilePhoto = (ImageView) vHeader.findViewById(R.id.ivUserProfilePhoto);

        addHeaderView(vHeader);
        vHeader.setOnClickListener(this);
    }

    @Override
    public void setOnItemClickListener(OnItemClickListener listener) {
        super.setOnItemClickListener(listener);
    }

    @Override
    public void onClick(View v) {
        if (onHeaderClickListener != null) {
            onHeaderClickListener.onGlobalMenuHeaderClick(v);
        }
    }

    public interface OnHeaderClickListener {
        public void onGlobalMenuHeaderClick(View v);
    }

    public void setOnHeaderClickListener(OnHeaderClickListener onHeaderClickListener) {
        this.onHeaderClickListener = onHeaderClickListener;
    }
}