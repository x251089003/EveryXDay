package com.xinxin.everyxday.activity;

import android.os.Bundle;

import com.txx.androidpaginglibrary.listwrap.listview.PagingListViewWrapBase;
import com.txx.androidpaginglibrary.listwrap.listview.PagingListViewWrapBase.PagingListViewWrapDelegate;
import com.txx.androidpaginglibrary.listwrap.listview.PagingRefreshingListViewWrap;
import com.xinxin.everyxday.fragment.ListBaseActivity;

public abstract class RefreshingListBaseActivity<T> extends ListBaseActivity<T> {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	public PagingListViewWrapBase getPagingListViewWrap(PagingListViewWrapDelegate pagingListViewWrapDelegate){
		return new PagingRefreshingListViewWrap(pagingListViewWrapDelegate);
	}
}
