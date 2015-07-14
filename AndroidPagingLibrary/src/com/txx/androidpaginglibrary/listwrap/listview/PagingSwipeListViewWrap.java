package com.txx.androidpaginglibrary.listwrap.listview;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.txx.androidpaginglibrary.R;

public class PagingSwipeListViewWrap extends PagingListViewWrapBase{
	
	private int leftSwipeOffSet;
	
	public PagingSwipeListViewWrap(PagingListViewWrapDelegate pagingListViewWrapDelegate,int leftSwipeOffSet){
		this.pagingListViewWrapDelegate = pagingListViewWrapDelegate;
		this.leftSwipeOffSet = leftSwipeOffSet;
	}
	
	protected ListView getConfigedListView(LayoutInflater inflater){
		
		final SwipeListView swipeListView = (SwipeListView)inflater.inflate(R.layout.common_paging_swipe_list_view, null);
		
		swipeListView.setSwipeMode(SwipeListView.SWIPE_MODE_LEFT);
        swipeListView.setSwipeActionLeft(SwipeListView.SWIPE_ACTION_REVEAL);
//        swipeListView.setSwipeActionRight();
        
        swipeListView.setOffsetLeft(leftSwipeOffSet); //left swipe offset
//        swipeListView.setOffsetRight(convertDpToPixel(settings.getSwipeOffsetRight()));
        swipeListView.setAnimationTime(0);
//        swipeListView.setSwipeOpenOnLongPress(settings.isSwipeOpenOnLongPress());
        
        swipeListView.setSwipeListViewListener(new BaseSwipeListViewListener() {
			
			private int lastOpenPosition = -1;
			
//			@Override
//			public void onClickBackView(int position) {
//				//System.out.println("\r\n onClickBackView:" + position);
//			}
//
//			@Override
//			public void onClickFrontView(int position) {
//				//System.out.println("\r\n onClickFrontView:" + position);
//			}

			@Override
			public void onOpened(int position, boolean toRight) {
				if(lastOpenPosition != -1 && lastOpenPosition != position){
					swipeListView.closeAnimate(lastOpenPosition);
				}
				lastOpenPosition = position;
			}
			
		});
        
//		swipeListView.setSwipeListViewListener(new BaseSwipeListViewListener() {
//			
//			private int lastOpenPosition = -1;
//			
//			@Override
//			public void onChoiceChanged(int position, boolean selected) {
//				Log.d(TAG, "onChoiceChanged:" + position + ", " + selected);
//			}
//
//			@Override
//			public void onChoiceEnded() {
//				Log.d(TAG, "onChoiceEnded");
//			}
//
//			@Override
//			public void onChoiceStarted() {
//				Log.d(TAG, "onChoiceStarted");
//			}
//
//			@Override
//			public void onFirstListItem() {
//				Log.d(TAG, "onFirstListItem");
//			}
//
//			@Override
//			public void onLastListItem() {
//				Log.d(TAG, "onLastListItem");
//			}
//
//			@Override
//			public void onListChanged() {
//				Log.d(TAG, "onListChanged");
//			}
//			
//			@Override
//			public void onDismiss(int[] reverseSortedPositions) {
//				Log.d(TAG, "onDismiss");
//			}
//			
//			@Override
//			public void onClickBackView(int position) {
//				//System.out.println("\r\n onClickBackView:" + position);
//			}
//
//			@Override
//			public void onClickFrontView(int position) {
//				//System.out.println("\r\n onClickFrontView:" + position);
//			}
//
//			@Override
//			public void onMove(int position, float x) {
////				Log.d(TAG, "onMove:" + position + "," + x);
//			}
//
//			@Override
//			public void onStartOpen(int position, int action, boolean right) {
////				//System.out.println("\r\nonStartOpen : " + position + " action : " + action + " right : " + right);
//			}
//			
//			@Override
//			public void onOpened(int position, boolean toRight) {
////				//System.out.println("\r\nonOpened : " + position + " toRight : " + toRight);
//				if(lastOpenPosition != -1 && lastOpenPosition != position){
////					//System.out.println("\r\n swipeListView.closeAnimate : " + lastOpenPosition);
//					swipeListView.closeAnimate(lastOpenPosition);
//				}
//				lastOpenPosition = position;
//			}
//			
//			@Override
//			public void onStartClose(int position, boolean right) {
////				//System.out.println("\r\nonStartClose : " + position + " right : " + right);
//			}
//			
//			@Override
//			public void onClosed(int position, boolean fromRight) {
////				//System.out.println("\r\nonClosed : " + position + " fromRight : " + fromRight + "\r\n");
//			}
//			
//		});
        
		return swipeListView;
	}

	@Override
	protected View getAddToContainerView() {
		return listView;
	}
	
}
