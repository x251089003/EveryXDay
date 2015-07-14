package com.xinxin.everyxday.fragment;

import android.os.Bundle;

import com.txx.androidpaginglibrary.listwrap.listview.PagingListViewWrapBase;
import com.txx.androidpaginglibrary.listwrap.listview.PagingListViewWrapBase.PagingListViewWrapDelegate;
import com.txx.androidpaginglibrary.listwrap.listview.PagingRefreshingListViewWrap;


public abstract class RefreshingListBaseFragment<T> extends ListBaseFragment<T>{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public PagingListViewWrapBase getPagingListViewWrap(PagingListViewWrapDelegate pagingListViewWrapDelegate) {
		return new PagingRefreshingListViewWrap(pagingListViewWrapDelegate);
	}
	
}
