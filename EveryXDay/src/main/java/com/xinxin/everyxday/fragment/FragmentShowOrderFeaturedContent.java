package com.xinxin.everyxday.fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.xinxin.everyxday.R;
import com.xinxin.everyxday.base.imgloader.ImgLoadUtil;
import com.xinxin.everyxday.bean.ShowOrderFeaturedBean;
import com.xinxin.everyxday.global.InterfaceUrlDefine;
import com.xinxin.everyxday.util.TimeUtil;


import java.util.ArrayList;
import java.util.List;

public class FragmentShowOrderFeaturedContent extends RefreshingListBaseFragment<ShowOrderFeaturedBean> {
	
	private ArrayList<ShowOrderFeaturedBean> voList = new ArrayList<ShowOrderFeaturedBean>();
	
	public static FragmentShowOrderFeaturedContent newInstance(Bundle args) {
		FragmentShowOrderFeaturedContent myFragment = new FragmentShowOrderFeaturedContent();
        myFragment.setArguments(args);
        return myFragment;
    }
	
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
			contentView.setDivider(new ColorDrawable(R.color.transparent));
			contentView.setDividerHeight(1);
			contentView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
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
		
		ImageView userPhoto = (ImageView)convertView.findViewById(R.id.showorder_list_user_avater);
		ImgLoadUtil.displayImageWithAnimation(vo.getAvatar(), userPhoto);
		
		TextView userName = (TextView)convertView.findViewById(R.id.showorder_list_user_name);
		userName.setText(vo.getTitle());
		
		TextView publishTime = (TextView)convertView.findViewById(R.id.showorder_list_user_publish_time);
//		publishTime.setText(TimeUtil.getStandardDate(""+vo.getCreateTime().getTime()/1000));
		publishTime.setText(TimeUtil.dateToStr(vo.getCreateTime()));
		
		ImageView orderImg = (ImageView)convertView.findViewById(R.id.showorder_list_img);
		ImgLoadUtil.displayImageWithAnimationAndNoCorner(vo.getCover(), orderImg);
		
		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(!isActivityAttached()){
					return;
				}
				
//				Intent intent = new Intent();
//				intent.setClass(getAttachActivity(), ShowOrderFeaturedDetailContentActivity.class);
//				intent.putExtra(CommonWebViewActivity.KILL_HELP_ACTIVITY_VIEW_TITLE, "晒单精选");
//				intent.putExtra(CommonWebViewActivity.KILL_HELP_ACTIVITY_LOAD_URL, vo.getDetail());
//
//				intent.putExtra(ShowOrderFeaturedDetailContentActivity.SHOR_ORDER_FEATURED_CONTENT_ACTIVITY_ID, vo.getId());
//				intent.putExtra(ShowOrderFeaturedDetailContentActivity.SHOR_ORDER_FEATURED_CONTENT_ACTIVITY_URL, vo.getBuyurl());
//
//				startActivity(intent);
			}
		});
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

}