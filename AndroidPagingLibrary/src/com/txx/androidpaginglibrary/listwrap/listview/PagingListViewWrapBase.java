package com.txx.androidpaginglibrary.listwrap.listview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.txx.androidpaginglibrary.R;

public abstract class PagingListViewWrapBase {
	
	public interface PagingListViewWrapDelegate{
		
		public boolean isListDataLoading();//当前是否正在加载数据
		
		public void loadListData();//启动当前页数据加载
		
		public void addListViewToContainer(View listView);//添加listview至页面主容器
		
		public int getListCount();
		
		public int getListViewItemType(int position);
		
		public int getListViewTypeCount();
		
		public boolean isAdapterItemEnable(int position);
		
		public View getCovertView(int position);
		
		public void initCovertView(View convertView,int position);
		
		public View getEmptyFooterView();//列表本身带有headerview，并且无列表数据时。(个人主页)
		
	}
	
	protected PagingListViewWrapDelegate pagingListViewWrapDelegate;
	
	protected ListView listView;
	private BaseAdapter listAdapter;
	
	private View listFooterLoadingView;//list footer view loading item
	private View listFooterLoadCurPageView;//list footer view load cur page item
	private View listEmptyFooterView;//list empty footer item
	
	private void initListView(LayoutInflater inflater) {
		
		listFooterLoadingView = inflater.inflate(R.layout.common_paging_listview_loading_item,null);
		listFooterLoadingView.setTag(footerLoadingViewTag);
		
		listView = getConfigedListView(inflater);
		
		addListFooterViewWithTag(footerLoadingViewTag);

		pagingListViewWrapDelegate.addListViewToContainer(getAddToContainerView());
		
		listAdapter = new ListBaseAdapter();
		listView.setAdapter(listAdapter);
		
	}
	
	protected abstract View getAddToContainerView();
	
	protected abstract ListView getConfigedListView(LayoutInflater inflater);
	
	private class ListBaseAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return pagingListViewWrapDelegate.getListCount();
		}
		
		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}
		
		@Override
		public int getItemViewType(int position) {
			return pagingListViewWrapDelegate.getListViewItemType(position);
		}
		
		@Override
		public int getViewTypeCount() {
			return pagingListViewWrapDelegate.getListViewTypeCount();
		}
		
		@Override
		public boolean isEnabled(int position) {
		    return pagingListViewWrapDelegate.isAdapterItemEnable(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if(convertView == null){
				convertView = pagingListViewWrapDelegate.getCovertView(position);
			}
			
			pagingListViewWrapDelegate.initCovertView(convertView,position);
			
			if(position == getCount() - 1){
				if(findListFooterViewWithTag(footerLoadingViewTag) != null){
					if(!pagingListViewWrapDelegate.isListDataLoading()){
						pagingListViewWrapDelegate.loadListData();
					}
				}
			}
			
			return convertView;
		}
		
	}

	private static final String LIST_HEADER_VIEW = "list_headview";//listview heacer view tag
	private static final String footerLoadingViewTag = "list_footer_loading_view";//listview footer loading view tag
	private static final String footerLoadCurPageViewTag = "list_footer_load_cur_page_view";//listview footer load cur page view tag
	private static final String footerEmptyViewTag = "list_empty_footer_view";//listview empty footer view tag
	
	private View findListFooterViewWithTag(String tag) {
		if(listView != null){
			return listView.findViewWithTag(tag);
		}
		return null;
	}

	private void addListFooterViewWithTag(String tag) {
		if(findListFooterViewWithTag(tag) == null){
			if(footerLoadingViewTag.equals(tag)){
				listView.addFooterView(listFooterLoadingView, null, false);
			}else if(footerLoadCurPageViewTag.equals(tag)){
				listView.addFooterView(listFooterLoadCurPageView, null, false);
			}else if(footerEmptyViewTag.equals(tag)){
				if(listEmptyFooterView != null){
					listView.addFooterView(listEmptyFooterView, null, false);
				}
			}
		}
	}

	private void removeListFooterViewWithTag(String tag) {
		if(findListFooterViewWithTag(tag) != null){
			if(footerLoadingViewTag.equals(tag)){
				listView.removeFooterView(listFooterLoadingView);
			}else if(footerLoadCurPageViewTag.equals(tag)){
				listView.removeFooterView(listFooterLoadCurPageView);
			}else if(footerEmptyViewTag.equals(tag)){
				listView.removeFooterView(listEmptyFooterView);
			}
		}
	}
	
	public void setFooterLoadingView(boolean isShowFooterView){
		
		removeListFooterViewWithTag(footerEmptyViewTag);
		
		if(!isShowFooterView){
			if(listView != null){
				removeListFooterViewWithTag(footerLoadingViewTag);
			}
		}
		
		if(listAdapter != null){
			listAdapter.notifyDataSetChanged();
			if(isShowFooterView){// && isListViewRefreshing()
				if(listView != null){
					addListFooterViewWithTag(footerLoadingViewTag);
				}
			}
		}
		
	}
	
	public void setFooterLoadCurPageView(LayoutInflater inflater, String tip) {
		
		if(listView == null){
			return;
		}
		
		if(listFooterLoadCurPageView == null){
			listFooterLoadCurPageView = inflater.inflate(R.layout.common_paging_listview_reload_next_page_item, null);
			listFooterLoadCurPageView.setTag(footerLoadCurPageViewTag);
		}
		
		try{
			View reloadBtn = listFooterLoadCurPageView.findViewById(R.id.reload_btn);
			
			reloadBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					
					//显示listfooterloadingview 并且开始加载下一页数据
					removeListFooterViewWithTag(footerLoadCurPageViewTag);
					addListFooterViewWithTag(footerLoadingViewTag);
					
					pagingListViewWrapDelegate.loadListData();
				}
			});
		}catch(Exception e){
			e.printStackTrace();
		}
		
		removeListFooterViewWithTag(footerLoadingViewTag);
		addListFooterViewWithTag(footerLoadCurPageViewTag);

	}
	
	public void setFooterEmptyView(LayoutInflater inflater) {
		
		if(listEmptyFooterView == null){
			listEmptyFooterView = pagingListViewWrapDelegate.getEmptyFooterView();
			if(listEmptyFooterView != null){
				listEmptyFooterView.setTag(footerEmptyViewTag);
			}
		}
		
		removeListFooterViewWithTag(footerLoadingViewTag);
		removeListFooterViewWithTag(footerLoadCurPageViewTag);
		addListFooterViewWithTag(footerEmptyViewTag);

	}
	
	public void removeFooterView(){
		if(listView != null){
			if(listView.getFooterViewsCount() > 0){
				removeListFooterViewWithTag(footerLoadingViewTag);
				removeListFooterViewWithTag(footerLoadCurPageViewTag);
				removeListFooterViewWithTag(footerEmptyViewTag);
			}
		}
	}
	
	public boolean isListViewRefreshing(){//如果带下拉刷新，则重写该方法
		return false;
	}
	
	public void refreshComplete(){}
	
	public void updateListView(LayoutInflater inflater){
		if(listView != null){
			listAdapter.notifyDataSetChanged();
		}else{
			initListView(inflater);
		}
	}

	public void notifyMyListView(){
		if(listView != null){
			listAdapter.notifyDataSetChanged();
		}
	}

	public String getListHeaderViewTag(){
		return LIST_HEADER_VIEW;
	}
	
	public View getListHeaderView(){
		if(listView != null){
			return listView.findViewWithTag(LIST_HEADER_VIEW);
		}
		return null;
	}
	
	public BaseAdapter getListAdapter(){
		return listAdapter;
	}
	
	public ListView getListView(){
		return listView;
	}
}
