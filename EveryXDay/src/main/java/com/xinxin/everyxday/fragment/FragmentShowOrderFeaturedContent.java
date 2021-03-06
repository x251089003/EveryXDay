package com.xinxin.everyxday.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.loopj.android.http.RequestParams;
import com.xinxin.everyxday.R;
import com.xinxin.everyxday.activity.ToolbarControlDetailListViewActivity;
import com.xinxin.everyxday.base.imgloader.ImgLoadUtil;
import com.xinxin.everyxday.bean.ShowOrderFeaturedBean;
import com.xinxin.everyxday.dao.model.Like;
import com.xinxin.everyxday.dao.util.DbService;
import com.xinxin.everyxday.global.InterfaceUrlDefine;
import com.xinxin.everyxday.util.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentShowOrderFeaturedContent extends RefreshingListBaseFragment<ShowOrderFeaturedBean> {
	
	private ArrayList<ShowOrderFeaturedBean> voList = new ArrayList<ShowOrderFeaturedBean>();

	private final Map<ImageView, AnimatorSet> likeAnimations = new HashMap<>();

	private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
	private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);

	private DbService mDbService;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mDbService = DbService.getInstance(getAttachActivity());
		loadListData();
	}
	
	@Override
	public View getFragmentView() {
		containerView = (RelativeLayout) inflater.inflate(R.layout.fragment_common_list, null);
		return containerView;
	}

	@Override
	public void addListViewToContainer(View listView) {
		
		if(isActivityAttached()){

			SwipeRefreshLayout refreshListView = (SwipeRefreshLayout)listView;
			ListView contentView = (ListView)refreshListView.findViewById(R.id.list_view);
			contentView.setDivider(null);
			contentView.setDividerHeight(0);
		}
		
		containerView.removeAllViews();
		containerView.addView(listView, new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}

	@Override
	public View getCovertView(int position) {
		View convertView = inflater.inflate(R.layout.showorder_list_view_item_new, null);
		return convertView;
	}

	@Override
	public void initCovertView(View convertView, final int position) {
		
		final ShowOrderFeaturedBean vo = voList.get(position);
		
//		ImageView userPhoto = (ImageView)convertView.findViewById(R.id.showorder_list_user_avater);
//		ImgLoadUtil.displayImageWithAnimation(vo.getAvatar(), userPhoto);
		
		TextView userName = (TextView)convertView.findViewById(R.id.showorder_list_user_name);
		userName.setText(vo.getTitle().replace("今日最佳：", ""));

		final ImageView like = (ImageView)convertView.findViewById(R.id.btnLike);
		like.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(mDbService.queryLike("WHERE NEWID = "+ vo.getId()).size() == 0){
					Like likeBean = new Like();
					likeBean.setAvatar(vo.getAvatar());
					likeBean.setCover(vo.getCover());
					likeBean.setCreateTime(vo.getCreateTime());
					likeBean.setDetailNew(vo.getDetailNew());
					likeBean.setNewid(vo.getId() + "");
					likeBean.setTitle(vo.getTitle());
					likeBean.setCategory(vo.getCategory());
					mDbService.saveLike(likeBean);
					updateHeartButton(like, true, position);
					System.out.println("daxiao === " + likeAnimations.size());
				}
			}
		});

		System.out.println("LikeList === " + mDbService.queryLike("WHERE NEWID = " + vo.getId()).size());
		if(mDbService.queryLike("WHERE NEWID = "+ vo.getId()).size() != 0){
			like.setBackgroundResource(R.mipmap.ic_heart_red);
		}else{
			like.setBackgroundResource(R.mipmap.ic_heart_outline_grey);
		}
		
		TextView publishTime = (TextView)convertView.findViewById(R.id.new_time);
		publishTime.setText(TimeUtil.getMonthAndDay(vo.getCreateTime()));

		TextView category = (TextView)convertView.findViewById(R.id.new_sort);
		category.setText("#"+vo.getCategory());
		
		ImageView orderImg = (ImageView)convertView.findViewById(R.id.showorder_list_img);
		ImgLoadUtil.displayImageWithAnimationAndNoCorner(vo.getCover(), orderImg);

		RippleView mRippleView = (RippleView)convertView.findViewById(R.id.item_rippleview);
		mRippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
			@Override
			public void onComplete(RippleView rippleView) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), ToolbarControlDetailListViewActivity.class);
				intent.putExtra("today_new_title", vo.getTitle().replace("今日最佳：", ""));
				intent.putExtra("today_new_url", vo.getDetail());
				intent.putExtra("today_new_id", vo.getId());
				intent.putExtra("today_new_cover",vo.getCover());
				intent.putExtra("today_new_time",vo.getCreateTime());
				intent.putExtra("today_new_avatar",vo.getAvatar());
				intent.putExtra("today_new_buyurl", vo.getBuyurl());
				intent.putExtra("today_detail_new_url", vo.getDetailNew());
				intent.putExtra("today_new_category", vo.getCategory());
				System.out.println("=================  " + vo.getDetailNew());

				startActivity(intent);
			}
		});
//		ImageView aboveImg = (ImageView)convertView.findViewById(R.id.showorder_list_img_above);
//		convertView.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//
//				System.out.println("这里这里这里这里这里这里这里这里这里这里这里这里这里");
//
//			}
//		});
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
				System.out.println("pos =========== " + position);
			}
		}
//		else {
//			if (likedPositions.contains(position)) {
//				view.setBackgroundResource(R.mipmap.ic_heart_red);
////				view.setImageResource(R.mipmap.ic_heart_red);
//			} else {
//				view.setBackgroundResource(R.mipmap.ic_heart_outline_grey);
//			}
//		}
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
	public List<ShowOrderFeaturedBean> getViewListData() {
		return voList;
	}
	
	@Override
	public Class<ShowOrderFeaturedBean> getBeanType() {
		return ShowOrderFeaturedBean.class;
	}

	@Override
	public void onResume() {
		super.onResume();
//		notifyMyListView();
	}
}