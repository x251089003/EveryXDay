package com.xinxin.everyxday.base.loopj.requestinstance;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.handler.TextHttpResponseHandler;
import com.xinxin.everyxday.base.jsonparser.InterfaceResultParser;
import com.xinxin.everyxday.base.loopj.jsoncache.ResponseCachePolicy;
import com.xinxin.everyxday.base.loopj.postdata.CommonRequestHeaderGenerate;
import com.xinxin.everyxday.base.loopj.postdata.RequestPostDataWrap;
import com.xinxin.everyxday.base.loopj.requestclient.RequestClient;
import com.xinxin.everyxday.base.netcode.ResultCodeUtil;
import com.xinxin.everyxday.bean.base.CommonResponseBody;
import com.xinxin.everyxday.bean.base.CommonResponseErrorBean;
import com.xinxin.everyxday.bean.base.CommonResponseHeader;
import com.xinxin.everyxday.util.StringUtil;

import org.apache.http.Header;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.txx.androidpaginglibrary.R;

public class CommonListRequestWrap<T> {
	
	public interface ListDataLoadWrapDelegate<T>{
		
		public String getRequestType();//接口请求地址
		
		public RequestParams getRequestParams();//获取列表第一次请求需要的params
		
		public boolean isRequestNeedSignParams();
		
		public boolean isNeedLoadDataFromNetWork();
		
		public boolean isListViewRefreshing();//listview是否是刷新状态
		
		public void notifyLoadListFailure();//通知当前页加载数据失败
		
		public void notifyLoadListSuccess(CommonResponseBody<T> resultBody);//通知当前页加载数据成功
		
		public void notifyLoadListEnd();//通知当前页数据加载结束
		
	}
	
	private Context context;
	private ListDataLoadWrapDelegate<T> listDataLoadWrapDelegate;
	private Class<T> beanType;
	
	private boolean isListLoading = false;//当前页数据加载是否结束
	
	private PageVo pageVo;
	
	private CommonResponseHeader responseHeader;
	
	//responseHeader == null 第一次加载数据
	public CommonResponseHeader getResponseHeader() {
		
		if(responseHeader != null){
			return responseHeader;
		}
		return null;
	}
	
	public void setResponseHeader(CommonResponseHeader responseHeader) {
		this.responseHeader = responseHeader;
	}

	public CommonListRequestWrap(Context context,
			ListDataLoadWrapDelegate<T> listDataLoadWrapDelegate, Class<T> beanType) {
		this.context = context;
		this.listDataLoadWrapDelegate = listDataLoadWrapDelegate;
		this.beanType = beanType;
		pageVo = new PageVo();
	}
	
	public void loadListData() {// 不同应用接口地址 
		
		isListLoading = true;
		
		final int curPage = getLoadPage();
		
		RequestParams requestParams = listDataLoadWrapDelegate.getRequestParams();
		String requestUrl = listDataLoadWrapDelegate.getRequestType();
		
		if(getResponseHeader() != null && !StringUtil.isEmpty(getResponseHeader().getLink())){
			
			requestParams = new RequestParams();
			
			Uri link = Uri.parse(getResponseHeader().getLink());

			Set<String> paramNameSet = getQueryParameterNames(link);
			
			if(paramNameSet != null && paramNameSet.size() > 0){
				
				Iterator<String> ite = paramNameSet.iterator();
				
				while(ite.hasNext()){
					String key = ite.next();
					requestParams.put(key, link.getQueryParameter(key));
				}
			}
			
			requestUrl = link.getScheme() + "://" + link.getHost() + link.getPath();
		}
		
//		System.out.println("\r\n listDataLoadWrapDelegate.isNeedLoadDataFromNetWork() : " + listDataLoadWrapDelegate.isNeedLoadDataFromNetWork());
		
		//如果是第一次load当前列表 并且不需要从网络加载当前列表数据
		if(!listDataLoadWrapDelegate.isNeedLoadDataFromNetWork() && handleCacheData(curPage)){
			return;
		}
		
		Header[] headers = CommonRequestHeaderGenerate.generateRequestHeader(RequestClient.REQUEST_TYPE_GET, null,
				listDataLoadWrapDelegate.isRequestNeedSignParams(),
				RequestPostDataWrap.generateUrlWithParams(listDataLoadWrapDelegate.getRequestType(), requestParams));
		
		RequestClient.get(context, requestUrl, headers, requestParams, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listDataLoadWrapDelegate.notifyLoadListFailure();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				handleServerResponse(responseString, curPage, headers, false);
			}
			
			@Override
			public void onFinish() {
				setLoadFinish();
			}
			
		});
		
	}
	
	private boolean handleCacheData(int curPage){
		
		String cacheJsonStr = getResponseCacheData(curPage);
		
		if (!StringUtil.isEmpty(cacheJsonStr)) {
			
			CommonResponseBody<T> responseBody = getResponseBodyFromJson(cacheJsonStr);
			if(responseBody != null){
				
				CommonResponseHeader responseHeader = getResponseHeaderCache(curPage);
				if(responseHeader != null){
					
					responseBody.setResponseHeader(responseHeader);
					
					handleFinalData(responseBody, null, curPage, true);
					
					setLoadFinish();
					
					return true;
				}
			}
		}
		return false;
	}
	
	private synchronized void handleServerResponse(String responseString, int curPage, Header[] headers, boolean isCacheData){
		
		if(!StringUtil.isEmpty(responseString)){
			
			CommonResponseBody<T> responseBody = getResponseBodyFromJson(headers, responseString);
			
			if(responseBody != null){
				
				if(handleFinalData(responseBody, responseString, curPage, isCacheData)){
					return;
				}
			}
		}
		
		if(!isCacheData){
			listDataLoadWrapDelegate.notifyLoadListFailure();
			Toast.makeText(context, context.getString(R.string.common_paging_network_failure_tip), Toast.LENGTH_SHORT).show();
		}
		
	}
	
	private boolean handleFinalData(CommonResponseBody<T> responseBody, String responseString, int curPage, boolean isCacheData){
		
		setResponseHeader(responseBody.getResponseHeader());
		
		if (getResponseHeader() != null) {
			
			String result = getResponseHeader().getResultCode();

			if(ResultCodeUtil.SUCESS.equals(result)){
				
				List<T> voList = responseBody.getList();
				
				if(isCacheData){
					if(voList == null || voList.size() == 0){
					}else{
						listDataLoadWrapDelegate.notifyLoadListSuccess(responseBody);
						pageVo.plusLoadPage();
					}
					return true;
				}
				
				if(voList != null && voList.size() > 0){
					cacheServerResponseData(responseString, curPage, getResponseHeader());
				}
				
				listDataLoadWrapDelegate.notifyLoadListSuccess(responseBody);
				pageVo.plusLoadPage();
				
				return true;
				
			}else{
				if(!isCacheData){
					listDataLoadWrapDelegate.notifyLoadListFailure();
					Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
					return true;
				}
			}
		}
		
		return false;
	}
	
	private void setLoadFinish(){
		isListLoading = false;
		listDataLoadWrapDelegate.notifyLoadListEnd();
	}
	
	//当前列表数据是否正在加载
	public boolean isListDataLoading(){
		return isListLoading;
	}
	
	//获取当前加载页 页标
	private int getLoadPage(){
		
		if(listDataLoadWrapDelegate.isListViewRefreshing()){
			pageVo.resetLoadPage();
			setResponseHeader(null);
		}
		
		int curPage = pageVo.getLoadPage();
		
		return (curPage + 1);
	}
	
	/**
	 * 当前加载页数，用来做缓存文件的key用。
	 * @author tangj
	 *
	 */
	private class PageVo{
		
		private int loadPage;
		
		public void resetLoadPage(){
			loadPage = 0;
		}

		public int getLoadPage() {
			return loadPage;
		}

		public void plusLoadPage(){
			loadPage++;
		}
		
	}
	
	//----------------缓存部分
	
	private boolean hadLoadedCacheData = false;//是否已经加载过缓存数据(每个页面只有第一次创建时才获取缓存数据)
	private boolean hadLoadedCacheHeader = false;
	
	private String getResponseCacheData(int curPage) {
		
		String listUrl = getListCacheUrl();
		
		if(!StringUtil.isEmpty(listUrl)){
			
			if(!hadLoadedCacheData){
				
				hadLoadedCacheData = true;
				
				ResponseCachePolicy cachePolicy = ResponseCachePolicy.getInstance();
				return cachePolicy.getResponseCacheData(getListCacheUrl(), curPage);
			}
		}
		return null;
	}
	
	private CommonResponseHeader getResponseHeaderCache(int curPage){
		
		String listUrl = getListCacheUrl();
		
		if(!StringUtil.isEmpty(listUrl)){
			
			if(!hadLoadedCacheHeader){
				
				hadLoadedCacheHeader = true;
				
				ResponseCachePolicy cachePolicy = ResponseCachePolicy.getInstance();
				return cachePolicy.getResponseHeaderCache(getListCacheUrl(), curPage);
			}
		}
		return null;
	}

	private void cacheServerResponseData(String responseString, int curPage, CommonResponseHeader responseHeader) {
		
		String listUrl = getListCacheUrl();
		
		if(!StringUtil.isEmpty(listUrl)){
			
			ResponseCachePolicy cachePolicy = ResponseCachePolicy.getInstance();
			try {
				cachePolicy.cacheResponseData(getListCacheUrl(), curPage, responseString.getBytes("UTF-8"), responseHeader);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}
	
	private String getListCacheUrl(){
		
		if(listDataLoadWrapDelegate != null){
			if(!StringUtil.isEmpty(listDataLoadWrapDelegate.getRequestType())){
				if(listDataLoadWrapDelegate.getRequestParams() != null){
					return RequestClient.getListRequestUrl(listDataLoadWrapDelegate.getRequestType(), listDataLoadWrapDelegate.getRequestParams());
				}
			}
		}
		return null;
	}
	
	private Set<String> getQueryParameterNames(Uri uri) {
		
	    if (uri.isOpaque()) {
//	        throw new UnsupportedOperationException("This isn't a hierarchical URI.");
	    	return null;
	    }

	    String query = uri.getEncodedQuery();
	    if (query == null) {
	        return Collections.emptySet();
	    }

	    Set<String> names = new LinkedHashSet<String>();
	    int start = 0;
	    do {
	        int next = query.indexOf('&', start);
	        int end = (next == -1) ? query.length() : next;

	        int separator = query.indexOf('=', start);
	        if (separator > end || separator == -1) {
	            separator = end;
	        }

	        String name = query.substring(start, separator);
	        names.add(Uri.decode(name));

	        // Move start to end of name.
	        start = end + 1;
	    } while (start < query.length());

	    return Collections.unmodifiableSet(names);
	}
	
	private CommonResponseBody<T> getResponseBodyFromJson(String str) {
		
		Gson gson = InterfaceResultParser.generateDateFormatGson();
		
		CommonResponseBody<T> responseBody = new CommonResponseBody<T>();
		
		try {
			responseBody.setList(parseResponseContent(gson, str));
		} catch (Exception e) {
		}
		
		return responseBody;
	}
	
	private CommonResponseBody<T> getResponseBodyFromJson(Header[] headers, String str) {
		
		CommonResponseHeader responseHeader = InterfaceResultParser.parserResponseHeader(headers);
		
		try {
			if(responseHeader != null && !StringUtil.isEmpty(responseHeader.getResultCode())){
				
				if (!StringUtil.isEmpty(str)) {
					
					Gson gson = InterfaceResultParser.generateDateFormatGson();
					
					CommonResponseBody<T> responseBody = new CommonResponseBody<T>();
					
					if(ResultCodeUtil.SUCESS.equals(responseHeader.getResultCode())){
						
						responseBody.setList(parseResponseContent(gson, str));
					}else{
						
						Type type = new TypeToken<CommonResponseErrorBean>() {}.getType();
						
						if(type != null){
							responseBody.setResponseErrorBean((CommonResponseErrorBean)gson.fromJson(str, type));
						}
					}
					
					responseBody.setResponseHeader(InterfaceResultParser.processResult(responseHeader));
					return responseBody;
				}
			}
		} catch (Exception e) {
		}
		
		CommonResponseBody<T> responseBody = new CommonResponseBody<T>();
		responseBody.setResponseErrorBean(InterfaceResultParser.generateErrorBean());
		return responseBody;
	}
	
	private List<T> parseResponseContent(Gson gson, String str){
		
//		System.out.println("\r\n\r\n=====List<T> parseResponseContent======="+str);
		
		JsonParser parser = new JsonParser();
		JsonArray array = parser.parse(str).getAsJsonArray();

		List<T> lst = new ArrayList<T>();
		for (final JsonElement json : array) {
			T entity = gson.fromJson(json, beanType);
			lst.add(entity);
		}
		
		return lst;
	}
}
