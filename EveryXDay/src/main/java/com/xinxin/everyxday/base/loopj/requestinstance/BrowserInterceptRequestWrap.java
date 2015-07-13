package com.xinxin.everyxday.base.loopj.requestinstance;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.gson.Gson;


import org.apache.http.Header;

/**
 * 
 *  GET /guides?page=0&cid=1 HTTP/1.1
	Host: api.taoxiaoxian.com
	Accept: application/vnd.taoxiaoxian.v1+json
	User-Agent : taoxiaoxian/1.0 (Android; Ice Cream Sandwich/4.0; java/1.8.0_05)
 *
 */

public class BrowserInterceptRequestWrap<T> {
	
	private Class<T> beanType;
	
	private Context cxt;
	private String requestType;
	private RequestParams requestParams;
	private boolean isNeedSigned;
	private CommonRequestWrap.CommonRequestWrapDelegate<T> commonRequestWrapDelegate;
	private ProgressDialog progressDialog;
	
	public BrowserInterceptRequestWrap(Context cxt, String requestType,
			RequestParams requestParams, boolean isNeedSigned,
			CommonRequestWrap.CommonRequestWrapDelegate<T> commonRequestWrapDelegate,
			Class<T> beanType) {
		this.cxt = cxt;
		this.requestType = requestType;
		this.requestParams = requestParams;
		this.isNeedSigned = isNeedSigned;
		this.commonRequestWrapDelegate = commonRequestWrapDelegate;
		this.beanType = beanType;
	}

	public BrowserInterceptRequestWrap(Context cxt, String requestType,
			RequestParams requestParams, boolean isNeedSigned, int stringId,
			CommonRequestWrap.CommonRequestWrapDelegate<T> commonRequestWrapDelegate,
			Class<T> beanType) {
		this.cxt = cxt;
		this.requestType = requestType;
		this.requestParams = requestParams;
		this.isNeedSigned = isNeedSigned;
		this.commonRequestWrapDelegate = commonRequestWrapDelegate;

		progressDialog = RequestProgressDialogWrap.createProgressDialog(cxt,
				stringId);
		this.beanType = beanType;
	}
	
	public BrowserInterceptRequestWrap(CommonRequestWrap.CommonRequestWrapDelegate<T> commonRequestWrapDelegate){
		this.commonRequestWrapDelegate = commonRequestWrapDelegate;
	}
	
	//note contentType 根据 body是否为空进行判断 如果为空 给一个null 不为空就给对应的请求类型
	public void getRequest(){
		
		String urlWithParams = RequestPostDataWrap.generateUrlWithParams(requestType, requestParams);
		RequestClient.get(cxt, requestType, 
				CommonRequestHeaderGenerate.generateRequestHeader(RequestClient.REQUEST_TYPE_GET, null, isNeedSigned, urlWithParams), 
				requestParams, new ServerRresponseHandler());
	}
	
	private class ServerRresponseHandler extends TextHttpResponseHandler{
		
		@Override
		public void setRequestHeaders(Header[] requestHeaders) {
			super.setRequestHeaders(requestHeaders);
			
		}
		
		@Override
		public void onStart() {
			if(!isDelegateNull()){
				commonRequestWrapDelegate.requestServerStart(progressDialog);
			}
		}
		
		@Override
		public void onFailure(int statusCode, Header[] headers,
				String responseString, Throwable throwable) {
			if(!isDelegateNull()){
				commonRequestWrapDelegate.requestServerNetWorkError();
			}
		}

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				String responseString) {
			
			CommonResponseBody<T> responseBody = getCommonResponseBodyFromJson(responseString);
			
			if(responseBody != null){
				if(!isDelegateNull()){
					commonRequestWrapDelegate.requestServerSuccess(responseBody);
				}else{
					if(!isDelegateNull()){
						commonRequestWrapDelegate.requestServerFailure(cxt, responseBody.getResponseErrorBean());
					}
				}
				return;
			}
			
			if(!isDelegateNull()){
				commonRequestWrapDelegate.requestServerResponseError(cxt);
			}
		}
		
		@Override
		public void onFinish() {
			if(!isDelegateNull()){
				commonRequestWrapDelegate.requestServerEnd(progressDialog);
			}
		}
	}
	
	private boolean isDelegateNull(){
		if(commonRequestWrapDelegate == null){
			return true;
		}
		return false;
	}
	
	private CommonResponseBody<T> getCommonResponseBodyFromJson(String str) {
		Gson gson = new Gson();
		CommonResponseBody<T> responseBody = new CommonResponseBody<T>();
		responseBody.setResponseObject(gson.fromJson(str, beanType));
		return responseBody;
	}
	
}
