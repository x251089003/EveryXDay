package com.txx.androidpaginglibrary.listwrap.listview;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.txx.androidpaginglibrary.R;

public class PagingRefreshingListViewWrap extends PagingListViewWrapBase implements SwipeRefreshLayout.OnRefreshListener{
	
	private boolean isRefreshing = false;//是否是下拉刷新
	private SwipeRefreshLayout refreshingListView;
	
	public PagingRefreshingListViewWrap(PagingListViewWrapDelegate pagingListViewWrapDelegate){
		this.pagingListViewWrapDelegate = pagingListViewWrapDelegate;
	}
	
	private Handler handler;
	
	protected ListView getConfigedListView(LayoutInflater inflater){
		
		handler = new Handler();
		
		refreshingListView = (SwipeRefreshLayout)inflater.inflate(R.layout.common_paging_refresh_list_view, null);
		refreshingListView.setColorSchemeResources(R.color.app_main_theme_color,
				R.color.app_main_theme_color_two, R.color.app_main_theme_color_three,
				R.color.app_main_theme_color_four);
		
		ListView refreshView = (ListView)refreshingListView.findViewById(R.id.list_view);
		refreshView.setCacheColorHint(0);
		refreshView.setDividerHeight(0);
		refreshView.setFastScrollEnabled(false);
		
		refreshView.setOverScrollMode(View.OVER_SCROLL_NEVER);
		
//		refreshingListView.setShowIndicator(false);
//		refreshingListView.setVerticalScrollBarEnabled(true);
		refreshingListView.setOnRefreshListener(this);
//		
//		refreshingListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
//			@Override
//			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//				if(!isRefreshing && !pagingListViewWrapDelegate.isListDataLoading()){
//					isRefreshing = true;
//					pagingListViewWrapDelegate.loadListData();//下拉刷新
//				}else{
//					refreshComplete();
//				}
//			}
//		});
		
		return refreshView;
	}
	
	public boolean isListViewRefreshing(){
		return isRefreshing;
	}
	
	public void setListViewRefreshing(){
		isRefreshing = true;
	}
	
//	public void refreshComplete(){
//		isRefreshing = false;
//		if(refreshingListView != null){
//			refreshingListView.onRefreshComplete();
//		}
//	}
	
	public void refreshComplete() {
		isRefreshing = false;
		if(handler != null){
			handler.postDelayed(runnable, 500);
		}
	}
	

	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			refreshingListView.setRefreshing(false);
		    if (refreshingListView.isRefreshing()) {
		        System.out.println("\r\ntrying to hide refresh.....");
		        handler.postDelayed(this, 500);
		    }
		}
	};

	@Override
	protected View getAddToContainerView() {
		return refreshingListView;
	}

	@Override
	public void onRefresh() {
		if(!isRefreshing && !pagingListViewWrapDelegate.isListDataLoading()){
			isRefreshing = true;
			pagingListViewWrapDelegate.loadListData();//下拉刷新
		}else{
			refreshComplete();
		}
	}
	
}
