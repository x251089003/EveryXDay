package com.xinxin.everyxday.base.loopj.requestinstance;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.handler.TextHttpResponseHandler;
import com.xinxin.everyxday.base.jsonparser.InterfaceResultParser;
import com.xinxin.everyxday.base.loopj.postdata.CommonRequestHeaderGenerate;
import com.xinxin.everyxday.base.loopj.postdata.RequestPostDataWrap;
import com.xinxin.everyxday.base.loopj.requestclient.RequestClient;
import com.xinxin.everyxday.base.netcode.ResultCodeUtil;
import com.xinxin.everyxday.bean.base.CommonResponseBody;
import com.xinxin.everyxday.bean.base.CommonResponseErrorBean;
import com.xinxin.everyxday.bean.base.CommonResponseHeader;
import com.xinxin.everyxday.util.StringUtil;

import org.apache.http.Header;

import java.lang.reflect.Type;

/**
 * 
 *  GET /guides?page=0&cid=1 HTTP/1.1
	Host: api.taoxiaoxian.com
	Accept: application/vnd.taoxiaoxian.v1+json
	User-Agent : taoxiaoxian/1.0 (Android; Ice Cream Sandwich/4.0; java/1.8.0_05)
 *
 */

public abstract class CommonRequestWrap<T> {
	
	public interface CommonRequestWrapDelegate<T>{
		
		/**
		 * 请求服务器开始
		 * @param progressDialog
		 */
		public void requestServerStart(ProgressDialog progressDialog);
		
		/**
		 * 请求服务器返回成功数据
		 * @param responseBody
		 */
		public void requestServerSuccess(CommonResponseBody<T> responseBody);
		
		/**
		 * 请求服务器返回失败数据
		 * @param cxt
		 * @param errorBean
		 */
		public void requestServerFailure(Context cxt, CommonResponseErrorBean errorBean);
		
		/**
		 * 请求服务器返回网络连接异常
		 */
		public void requestServerNetWorkError();
		
		/**
		 * 请求服务器响应异常(无具体异常信息)
		 * @param cxt
		 */
		public void requestServerResponseError(Context cxt);
		
		/**
		 * 请求服务器结束
		 * @param progressDialog
		 */
		public void requestServerEnd(ProgressDialog progressDialog);
		
	}
	
	private Context cxt;
	private String requestType;
	private RequestParams requestParams;
	private boolean isNeedSigned;
	private CommonRequestWrapDelegate<T> commonRequestWrapDelegate;
	private ProgressDialog progressDialog;
	private Class<T> beanType;

	protected Class<T> getBeanType() {
		return beanType;
	}

	public CommonRequestWrap(Context cxt, String requestType,
			RequestParams requestParams, boolean isNeedSigned,
			CommonRequestWrapDelegate<T> commonRequestWrapDelegate,
			Class<T> beanType) {
		this.cxt = cxt;
		this.requestType = requestType;
		this.requestParams = requestParams;
		this.isNeedSigned = isNeedSigned;
		this.commonRequestWrapDelegate = commonRequestWrapDelegate;
		this.beanType = beanType;
	}

	public CommonRequestWrap(Context cxt, String requestType,
			RequestParams requestParams, boolean isNeedSigned, int stringId,
			CommonRequestWrapDelegate<T> commonRequestWrapDelegate,
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
	
	public CommonRequestWrap(CommonRequestWrapDelegate<T> commonRequestWrapDelegate){
		this.commonRequestWrapDelegate = commonRequestWrapDelegate;
	}
	
	//note contentType 根据 body是否为空进行判断 如果为空 给一个null 不为空就给对应的请求类型
	public void getRequest(){
		
		Header[] headers = generateRequestHeader(RequestClient.REQUEST_TYPE_GET, null, getUrlWithParams());
		
		RequestClient.get(cxt, requestType, headers, requestParams,
				new ServerRresponseHandler());
	}
	
	public void postRequest(String jsonStr){
		
		String urlWithParams = getUrlWithParams();
		Header[] headers = generateRequestHeader(RequestClient.REQUEST_TYPE_POST, jsonStr, urlWithParams);
		
		RequestClient.post(cxt, urlWithParams, headers,
				RequestPostDataWrap.generateRequestEntity(jsonStr),
				getContentType(jsonStr), new ServerRresponseHandler());
	}
	
	public void putRequest(String jsonStr){
		
		String urlWithParams = getUrlWithParams();
		Header[] headers = generateRequestHeader(RequestClient.REQUEST_TYPE_PUT, jsonStr, urlWithParams);
		
		RequestClient.put(cxt, urlWithParams, headers,
				RequestPostDataWrap.generateRequestEntity(jsonStr),
				getContentType(jsonStr), new ServerRresponseHandler());
	}
	
	private String getUrlWithParams(){
		return RequestPostDataWrap.generateUrlWithParams(requestType, requestParams);
	}
	
	private Header[] generateRequestHeader(String httpVerb, String jsonStr, String urlWithParams){
		return CommonRequestHeaderGenerate.generateRequestHeader(httpVerb, jsonStr, isNeedSigned, urlWithParams);
	}
	
	private String getContentType(String jsonStr){
		
		if(StringUtil.isEmpty(jsonStr)){
			return null;
		}
		return RequestClient.CONTENT_TYPE;
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
			
			System.out.println("\r\n\r\n CommonRequestWrap : " + responseString);
			
			CommonResponseBody<T> responseBody = getCommonResponseBodyFromJson(headers, responseString);
			
			if(responseBody != null){
				CommonResponseHeader responseHeader = responseBody.getResponseHeader();
				
				if(responseHeader != null){
					
					String result = responseHeader.getResultCode();
					
					if(ResultCodeUtil.SUCESS.equals(result)){ //先判断返回结果
						if(!isDelegateNull()){
							commonRequestWrapDelegate.requestServerSuccess(responseBody);
						}
					}else{
						if(!isDelegateNull()){
							commonRequestWrapDelegate.requestServerFailure(cxt, responseBody.getResponseErrorBean());
						}
					}
					return;
				}
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
	
	public CommonResponseBody<T> getCommonResponseBodyFromJson(Header[] headers, String str) {
		
		CommonResponseHeader responseHeader = InterfaceResultParser.parserResponseHeader(headers);
		
		try {
			if(responseHeader != null && !StringUtil.isEmpty(responseHeader.getResultCode())){
				
				if (!StringUtil.isEmpty(str)) {
					
					Gson gson = InterfaceResultParser.generateDateFormatGson();
					
					CommonResponseBody<T> responseBody = new CommonResponseBody<T>();
					
					if(ResultCodeUtil.SUCESS.equals(responseHeader.getResultCode())){
						
						setResponseObject(responseBody, str, gson);
						
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
	
	public abstract void setResponseObject(CommonResponseBody<T> responseBody, String str, Gson gson);
}
