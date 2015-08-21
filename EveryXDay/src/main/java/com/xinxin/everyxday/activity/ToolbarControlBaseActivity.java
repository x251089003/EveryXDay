/*
 * Copyright 2014 Soichiro Kashima
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xinxin.everyxday.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.Scrollable;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.xinxin.everyxday.R;
import com.xinxin.everyxday.dao.model.Like;
import com.xinxin.everyxday.dao.util.DbService;
import com.xinxin.everyxday.widget.swipeback.SwipeBackSherlockActivity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class ToolbarControlBaseActivity<S extends Scrollable> extends SwipeBackSherlockActivity implements ObservableScrollViewCallbacks {

    private Toolbar mToolbar;
    private S mScrollable;
    private String viewTitle;
    private TextView titleTextView;
    private ImageView likeImageView;
    private String avatar;
    private String cover;
    private Date createTime;
    private int newId;
    public String detailNew;
    private String category;

    private DbService mDbService;

    private final Map<ImageView, AnimatorSet> likeAnimations = new HashMap<>();

    private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if(intent == null){
            finish();
            return;
        }
        avatar = intent.getStringExtra("today_new_avatar");
        cover = intent.getStringExtra("today_new_cover");
        createTime = (Date)intent.getSerializableExtra("today_new_time");
        System.out.println("time ========================= " + createTime.toString());
        detailNew = intent.getStringExtra("today_detail_new_url");
        newId = intent.getIntExtra("today_new_id", -1);
        viewTitle = intent.getStringExtra("today_new_title");
        category = intent.getStringExtra("today_new_category");
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        mDbService = DbService.getInstance(this);

        titleTextView = (TextView)findViewById(R.id.toolbar_title);
        titleTextView.setText(viewTitle);

        likeImageView = (ImageView)findViewById(R.id.imageView_like);

        if(mDbService.queryLike("WHERE NEWID = "+ newId).size() != 0){
            likeImageView.setBackgroundResource(R.mipmap.ic_heart_red);
        }else{
            likeImageView.setBackgroundResource(R.mipmap.ic_heart_outline_grey);
        }
        likeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mDbService.queryLike("WHERE NEWID = "+ newId).size() == 0){
                    Like likeBean = new Like();
                    likeBean.setAvatar(avatar);
                    likeBean.setCover(cover);
                    likeBean.setCreateTime(createTime);
                    likeBean.setDetailNew(detailNew);
                    likeBean.setNewid(newId + "");
                    likeBean.setTitle(viewTitle);
                    likeBean.setCategory(category);
                    mDbService.saveLike(likeBean);
                    updateHeartButton(likeImageView, true);
                }
            }
        });

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha));
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mScrollable = createScrollable();
        mScrollable.setScrollViewCallbacks(this);
    }

    public void initToolBar(){

    }

    protected abstract int getLayoutResId();

    protected abstract S createScrollable();

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        Log.e("DEBUG", "onUpOrCancelMotionEvent: " + scrollState);
        if (scrollState == ScrollState.UP) {
            if (toolbarIsShown()) {
                hideToolbar();
            }
        } else if (scrollState == ScrollState.DOWN) {
            if (toolbarIsHidden()) {
                showToolbar();
            }
        }
    }

    private boolean toolbarIsShown() {
        return ViewHelper.getTranslationY(mToolbar) == 0;
    }

    private boolean toolbarIsHidden() {
        return ViewHelper.getTranslationY(mToolbar) == -mToolbar.getHeight();
    }

    private void showToolbar() {
        moveToolbar(0);
    }

    private void hideToolbar() {
        moveToolbar(-mToolbar.getHeight());
    }

    private void moveToolbar(float toTranslationY) {
        if (ViewHelper.getTranslationY(mToolbar) == toTranslationY) {
            return;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(ViewHelper.getTranslationY(mToolbar), toTranslationY).setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float translationY = (float) animation.getAnimatedValue();
                ViewHelper.setTranslationY(mToolbar, translationY);
                ViewHelper.setTranslationY((View) mScrollable, translationY);
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) ((View) mScrollable).getLayoutParams();
                lp.height = (int) -translationY + getScreenHeight() - lp.topMargin;
                ((View) mScrollable).requestLayout();
            }
        });
        animator.start();
    }

    private void updateHeartButton(final ImageView view, boolean animated) {
        if (animated) {
            if (!likeAnimations.containsKey(view)) {
                AnimatorSet animatorSet = new AnimatorSet();
                likeAnimations.put(view, animatorSet);

                ObjectAnimator rotationAnim = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
                rotationAnim.setDuration(300);
                rotationAnim.setInterpolator(ACCELERATE_INTERPOLATOR);

                ObjectAnimator bounceAnimX = ObjectAnimator.ofFloat(view, "scaleX", 0.2f, 1f);
                bounceAnimX.setDuration(300);
                bounceAnimX.setInterpolator(OVERSHOOT_INTERPOLATOR);

                ObjectAnimator bounceAnimY = ObjectAnimator.ofFloat(view, "scaleY", 0.2f, 1f);
                bounceAnimY.setDuration(300);
                bounceAnimY.setInterpolator(OVERSHOOT_INTERPOLATOR);
                bounceAnimY.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        view.setBackgroundResource(R.mipmap.ic_heart_red);
                    }
                });

                animatorSet.play(rotationAnim);
                animatorSet.play(bounceAnimX).with(bounceAnimY).after(rotationAnim);

                animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        likeAnimations.remove(view);
                    }
                });

                animatorSet.start();
            }
        }
    }
}
