package com.xinxin.everyxday.fragment;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

import com.loopj.android.http.RequestParams;
import com.txx.androidpaginglibrary.listwrap.listview.PagingListViewWrapBase;
import com.txx.androidpaginglibrary.listwrap.listview.PagingListViewWrapBase.PagingListViewWrapDelegate;
import com.txx.androidpaginglibrary.listwrap.listview.PagingRefreshingListViewWrap;
import com.txx.androidpaginglibrary.listwrap.loaderrorview.LoadListDataErrorViewWrap;
import com.txx.androidpaginglibrary.listwrap.loaderrorview.LoadListDataErrorViewWrap.LoadListDataErrorViewWrapDelegate;
import com.xinxin.everyxday.R;
import com.xinxin.everyxday.base.loopj.requestinstance.CommonListRequestWrap;
import com.xinxin.everyxday.bean.base.CommonResponseBody;
import com.xinxin.everyxday.bean.base.CommonResponseHeader;
import com.xinxin.everyxday.util.StringUtil;
import com.xinxin.everyxday.widget.swipeback.SwipeBackSherlockActivity;


public abstract class ListBaseActivity<T> extends SwipeBackSherlockActivity implements
		CommonListRequestWrap.ListDataLoadWrapDelegate<T>, PagingListViewWrapDelegate,
	LoadListDataErrorViewWrapDelegate{

	protected Context context;
	protected LayoutInflater inflater;
	
	protected LinearLayout containerView;
	
	private CommonListRequestWrap<T> listDataLoadWrap;
	private PagingListViewWrapBase pagingListViewWrap;
	private LoadListDataErrorViewWrap loadListDataErrorViewWrap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_common_listview);
		containerView = (LinearLayout)findViewById(R.id.myLinearLayout);
		context = this;
		inflater = LayoutInflater.from(context);
		
		listDataLoadWrap = new CommonListRequestWrap<T>(context, this, getBeanType());
		pagingListViewWrap = getPagingListViewWrap(this);
		loadListDataErrorViewWrap = new LoadListDataErrorViewWrap(this);
	}
	
	public abstract Class<T> getBeanType();
	
	/**
	 * 获取列表类型 下拉或者其他已实现的列表类型
	 * @param pagingListViewWrapDelegate
	 * @return
	 */
	public abstract PagingListViewWrapBase getPagingListViewWrap(PagingListViewWrapDelegate pagingListViewWrapDelegate);
			
	/**
	 * 获取listview的parentView
	 * @return
	 */
	public ViewGroup getContainerView(){
		return containerView;
	}
	
	//---------------ListDataLoadWrapDelegate
	
	public abstract String getRequestType();
	
	//接口是否需要签名header 默认是不需要带签名 如果接口要带签名则在对应的界面重写次方法 返回true就可以了
	@Override
	public boolean isRequestNeedSignParams(){
		return false;
	}
	
	@Override
	public RequestParams getRequestParams() {
		return null;
	}
	
	@Override
	public boolean isNeedLoadDataFromNetWork(){
		return true;
	}
	
	@Override
	public boolean isListViewRefreshing() {
		return pagingListViewWrap.isListViewRefreshing();
	}
	
	@Override
	public void notifyLoadListFailure(){
		if(isLoadingFirstPage() && isShowEmpty()){//页面显示重新加载 
			loadListDataErrorViewWrap.setLoadFirstPageDataFailureView(context, inflater);
		}else{//listfooter更改为点击加载更多
			if(!pagingListViewWrap.isListViewRefreshing()){
				pagingListViewWrap.setFooterLoadCurPageView(inflater, context.getResources().getString(R.string.common_paging_click_reload_tip));
			}else{
				pagingListViewWrap.removeFooterView();
			}
		}
	}

	@Override
	public void notifyLoadListSuccess(CommonResponseBody<T> responseBody) {
		
		List<T> voList = responseBody.getList();
		
		CommonResponseHeader responseHeader = responseBody.getResponseHeader();
		
		int serverResponseCurPageCount = (voList == null || voList.size() == 0) ? 0 : voList.size();
		
//		System.out.println("\r\n notifyLoadListSuccess : " + serverResponseCurPageCount + " curLoadPage : " + curLoadPage);
		
		if(serverResponseCurPageCount == 0 && isLoadingFirstPage()){ //第一页并且没有数据返回，则页面显示空
			
			if(isShowEmpty()){
				String emptyString = getEmptyString();
				if(StringUtil.isEmpty(emptyString)){
					emptyString = context.getResources().getString(R.string.common_paging_list_load_empty_tip);
				}
				loadListDataErrorViewWrap.setLoadDataResponseEmptyView(inflater, emptyString);
				return;
			}
			
			//当listview带了headerview(不可删除)，并且此列表没有数据时，可能需要加一个footerview，提示当前列表暂无数据。
			if(getListHeaderView() != null){
				pagingListViewWrap.setFooterEmptyView(inflater);
				return;
			}
			
		}
		
		if(voList != null && voList.size() > 0){
			if(pagingListViewWrap.isListViewRefreshing()){
				clearLoadedListData();
			}
			addAll(responseBody);
		}
		
		pagingListViewWrap.updateListView(inflater);
		
		boolean isShowFooterView = false;
		
		if(responseHeader != null){
			if(!StringUtil.isEmpty(responseHeader.getLink())){
				isShowFooterView = true;
			}
		}
		
		pagingListViewWrap.setFooterLoadingView(isShowFooterView);
	}

	@Override
	public void notifyLoadListEnd() {
		if (pagingListViewWrap.isListViewRefreshing()) {
			pagingListViewWrap.refreshComplete();
		}
	}

	//----------PagingListViewWrapDelegate
	
	@Override
	public boolean isListDataLoading() {
		return listDataLoadWrap.isListDataLoading();
	}

	@Override
	public void loadListData() {
		listDataLoadWrap.loadListData();
	}

	public abstract void addListViewToContainer(View listView);

	@Override
	public int getListCount() {
		return getLoadedListCount();
	}

	@Override
	public int getListViewItemType(int position) {
		return 0;
	}

	@Override
	public int getListViewTypeCount() {
		return 1;
	}

	@Override
	public boolean isAdapterItemEnable(int position) {
		return false;
	}

	public abstract View getCovertView(int position);

	public abstract void initCovertView(View convertView, int position);
	
	@Override
	public View getEmptyFooterView(){
		return null;
	}
	
	//-----------LoadListDataErrorViewWrapDelegate
	
	@Override
	public void addLoadFirstPageDataFailureView(View errorView) {
		getContainerView().removeAllViews();
		getContainerView().addView(errorView,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
	}

	@Override
	public void addFirstPageReloadingView(View loadingView) {
		getContainerView().removeAllViews();
		getContainerView().addView(loadingView,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		setListViewRefreshingStatus();
		listDataLoadWrap.loadListData();//加载第一页
	}

	@Override
	public void addEmptyView(View emptyView) {
		getContainerView().removeAllViews();
		getContainerView().addView(emptyView,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
	}
	
	//-------------扩展部分
	
	public boolean isLoadingFirstPage(){//根据集合数量判断是否是第一页
		if(getViewListData() == null || getViewListData().size() == 0){
			return true;
		}
		return false;
	}
	
	public void clearLoadedListData(){
		if(getViewListData() != null){
			getViewListData().clear();
		}
	}
	
	public int getLoadedListCount(){
		if(getViewListData() != null){
			return getViewListData().size();
		}
		return 0;
	}
	
	public abstract List<T> getViewListData();

	//某些列表需要对返回list vo更改
	public void addAll(CommonResponseBody<T> resultBody) {
		getViewListData().addAll(resultBody.getList());
	}
	
	/**
	 *  1.特殊列表 可能不需要显示空界面
	 *  2.当列表数据为空时，并且listview已经有headview时，不显示空界面，界面显示listview的headerview。
	 *  3.直接显示空间面
	 */
	public boolean isShowEmpty() { 
		return getListHeaderView() == null ? true : false ;
	}
	
	/**
	 * 当列表数据为空时，空界面提示文字内容。每个界面提示内容可能不一样，所以放出此接口。
	 * @return
	 */
	public String getEmptyString() {
		return null;
	}
	
	//--------------------------给子类调用
	
	public void removeFooterView(){
		pagingListViewWrap.removeFooterView();
	}
	
	public void updateListView(){
		pagingListViewWrap.updateListView(inflater);
	}
	
	public ListView getListView(){
		return pagingListViewWrap.getListView();
	}
	
	public BaseAdapter getListAdapter(){
		return pagingListViewWrap.getListAdapter();
	}
	
	public String getListHeaderViewTag(){
		return pagingListViewWrap.getListHeaderViewTag();
	}
	
	public View getListHeaderView(){
		return pagingListViewWrap.getListHeaderView();
	}
	
	public void reloadFragmentListData(){
		if(!listDataLoadWrap.isListDataLoading() && !pagingListViewWrap.isListViewRefreshing() && isLoadingFirstPage()){
			setListViewRefreshingStatus();
			listDataLoadWrap.loadListData();
		}
	}
	
	public void reFreshingListData(){
		if (!listDataLoadWrap.isListDataLoading()) {
			setListViewRefreshingStatus();
			loadListData();
		}
	}
	
	private void setListViewRefreshingStatus(){ //将列表置于下拉刷新状态
		((PagingRefreshingListViewWrap)pagingListViewWrap).setListViewRefreshing();
	}
	
}
