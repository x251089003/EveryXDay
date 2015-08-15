package com.xinxin.everyxday.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.loopj.android.http.RequestParams;
import com.txx.androidpaginglibrary.listwrap.listview.PagingListViewWrapBase;
import com.xinxin.everyxday.R;
import com.xinxin.everyxday.base.imgloader.ImgLoadUtil;
import com.xinxin.everyxday.bean.ShowOrderFeaturedBean;
import com.xinxin.everyxday.fragment.ListBaseActivity;
import com.xinxin.everyxday.global.InterfaceUrlDefine;
import com.xinxin.everyxday.util.TimeUtil;
import com.xinxin.everyxday.widget.swipeback.SwipeBackSherlockActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by xinxin on 15/8/4.
 */
public class SortActivity extends RefreshingListBaseActivity<ShowOrderFeaturedBean> implements View.OnClickListener {

    private Toolbar mToolbar;

    private ArrayList<ShowOrderFeaturedBean> voList = new ArrayList<ShowOrderFeaturedBean>();
    private final ArrayList<Integer> likedPositions = new ArrayList<>();
    private final Map<ImageView, AnimatorSet> likeAnimations = new HashMap<>();

    private static final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);

    private String viewTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if(intent == null){
            finish();
            return;
        }

        viewTitle = intent.getStringExtra("title");
        super.onCreate(savedInstanceState);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha));
        mToolbar.setTitle(viewTitle);// 标题的文字需在setSupportActionBar之前，不然会无效
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SortActivity.this.finish();
            }
        });
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {

                }
                return true;
            }
        });
        loadListData();
    }

    @Override
    public List<ShowOrderFeaturedBean> getViewListData() {
        return voList;
    }

    @Override
    public Class<ShowOrderFeaturedBean> getBeanType() {
        return ShowOrderFeaturedBean.class;
    }

    @Override
    public String getRequestType() {
        return InterfaceUrlDefine.SHAIDAN_EXCELLENT_URL;
    };

    @Override
    public RequestParams getRequestParams() {
        RequestParams params = new RequestParams();
        params.put("offset", 0);
        return params;
    }

    @Override
    public void addListViewToContainer(View listView) {
        SwipeRefreshLayout refreshListView = (SwipeRefreshLayout)listView;
        ListView contentView = (ListView)refreshListView.findViewById(R.id.list_view);
        contentView.setDivider(new ColorDrawable(R.color.transparent));
        contentView.setDividerHeight(0);
        contentView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);

        containerView.removeAllViews();
        containerView.addView(listView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    public View getCovertView(int position) {
        View convertView = inflater.inflate(R.layout.showorder_list_view_item_new, null);
        return convertView;
    }

    @Override
    public void initCovertView(View convertView, final int position) {
        final ShowOrderFeaturedBean vo = voList.get(position);

        ImageView userPhoto = (ImageView)convertView.findViewById(R.id.showorder_list_user_avater);
        ImgLoadUtil.displayImageWithAnimation(vo.getAvatar(), userPhoto);

        TextView userName = (TextView)convertView.findViewById(R.id.showorder_list_user_name);
        userName.setText(vo.getTitle().replace("今日最佳：", ""));

        final ImageView like = (ImageView)convertView.findViewById(R.id.btnLike);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!likedPositions.contains(position)) {
                    likedPositions.add(position);
                    updateHeartButton(like, true, position);
                    System.out.println("daxiao === " + likeAnimations.size());
                }
            }
        });
        if (likedPositions.contains(position)) {
            like.setBackgroundResource(R.mipmap.ic_heart_red);
        }else{
            like.setBackgroundResource(R.mipmap.ic_heart_outline_grey);
        }

        TextView publishTime = (TextView)convertView.findViewById(R.id.showorder_list_user_publish_time);
//		publishTime.setText(TimeUtil.getStandardDate(""+vo.getCreateTime().getTime()/1000));
        publishTime.setText(TimeUtil.dateToStr(vo.getCreateTime()));

        ImageView orderImg = (ImageView)convertView.findViewById(R.id.showorder_list_img);
        ImgLoadUtil.displayImageWithAnimationAndNoCorner(vo.getCover(), orderImg);

        RippleView mRippleView = (RippleView)convertView.findViewById(R.id.item_rippleview);
        mRippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent intent = new Intent();
                intent.setClass(SortActivity.this, ToolbarControlDetailListViewActivity.class);
                intent.putExtra("today_new_title", vo.getTitle().replace("今日最佳：", ""));
                intent.putExtra("today_new_url", vo.getDetail());
                intent.putExtra("today_new_id", vo.getId());
                intent.putExtra("today_new_buyurl", vo.getBuyurl());
                intent.putExtra("today_detail_new_url", vo.getDetailNew());

                startActivity(intent);
            }
        });
    }

    private void updateHeartButton(final ImageView view, boolean animated, int position) {
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

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
