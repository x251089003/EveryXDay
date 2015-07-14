package com.xinxin.everyxday.base.loopj.requestclient;

import android.content.Context;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.handler.AsyncHttpResponseHandler;
import com.xinxin.everyxday.util.StringUtil;

import org.apache.http.Header;
import org.apache.http.HttpEntity;

public class RequestClient {
	
	/**----------------------------------------------- 请求header内键值对 ----------------------------------------------- */
	public static final String HOST_KEY = "Host";
	public static final String HOST_VALUE = "api.taoxiaoxian.com";
	public static final String ACCEPT_KEY = "Accept";
	public static final String ACCEPT_VALUE = "application/vnd.taoxiaoxian.v2+json";
	public static final String ACCESS_KEY = "Access-Key";
	public static final String USER_AGENT_KEY = "User-Agent";
	public static final String EXPIRES_KEY = "Expires";
	public static final String CONTENT_TYPE_KEY = "Content-Type";
	public static final String OS = "Android";
	public static final String APP_PROJECT_NAME = "taoxiaoxian";
	public static final String JAVA_VERSION = "java/mobile";
	
	public static final String REQUEST_TYPE_GET = "GET";
	public static final String REQUEST_TYPE_POST = "POST";
	public static final String REQUEST_TYPE_PUT = "PUT";
	public static final String CONTENT_TYPE = "application/json";
	/**----------------------------------------------- end -----------------------------------------------*/
	
	// 秒杀线上
	private static final String BASE_DATA_INTERFACE_URL = "http://api.taoxiaoxian.com";
	
	//get request
	public static void get(Context context, String url, Header[] headers,
			RequestParams params, AsyncHttpResponseHandler responseHandler) {
		
		if(!StringUtil.isEmpty(url) && !url.contains("http")){
			url = getBaseDataInterfaceUrl(url);
		}
		RequestClientFactory.getInstance().get(context, url, headers, params, responseHandler);
	}
	
	//post request contentType = "application/json"
	public static void post(Context context, String urlWithParams, Header[] headers,
			HttpEntity entity, String contentType,
			AsyncHttpResponseHandler responseHandler) {

		RequestClientFactory.getInstance().post(context,
				getBaseDataInterfaceUrl(urlWithParams), headers, entity, contentType,
				responseHandler);
	}
	
	//post file to upyun
	public static void post(Context context, String url, RequestParams params,
			Header[] headers, AsyncHttpResponseHandler responseHandler) {
		RequestClientFactory.getInstance().post(context, url, headers, params, null, responseHandler);
	}
	
	public static void put(Context context, String urlWithParams, Header[] headers,
			HttpEntity entity, String contentType,
			AsyncHttpResponseHandler responseHandler) {

		RequestClientFactory.getInstance().put(context,
				getBaseDataInterfaceUrl(urlWithParams), headers, entity, contentType,
				responseHandler);
	}
	
	public static String getBaseDataInterfaceUrl(String url) {
		return BASE_DATA_INTERFACE_URL + url;
	}
	
	public static String getListRequestUrl(String requestType, RequestParams params){
		return getBaseDataInterfaceUrl(requestType) + "?" + params.getParamString(); 
	}

}
