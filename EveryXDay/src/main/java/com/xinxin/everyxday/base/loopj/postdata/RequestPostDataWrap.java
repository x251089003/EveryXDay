package com.xinxin.everyxday.base.loopj.postdata;

import com.loopj.android.http.RequestParams;
import com.xinxin.everyxday.base.loopj.requestclient.RequestClient;
import com.xinxin.everyxday.util.StringUtil;

import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.io.UnsupportedEncodingException;

public class RequestPostDataWrap {

	/**
	 * post / put 请求自动将requestParams 接在url后面，不传入RequestClient
	 * 
	 * @return
	 */
	public static String generateUrlWithParams(String requestType,
			RequestParams requestParams) {

		String requestParamsStr = convertRequestParamsToString(requestParams);

		if (!StringUtil.isEmpty(requestParamsStr)) {
//			System.out.println("\r\n requestParams.getParamString() : " + requestParamsStr);
			return requestType + "?" + requestParamsStr;
		} else {
//			System.out.println("\r\n requestType : " + requestType);
			return requestType;
		}
	}

	private static String convertRequestParamsToString(RequestParams requestParams) {
		if (requestParams != null) {
			return requestParams.getParamString();
		}
		return null;
	}

	/**
	 * post / put body封装
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static StringEntity generateRequestEntity(String jsonStr) {

		StringEntity stringEntity = null;
		if (jsonStr != null){
			try {
				stringEntity = new StringEntity(jsonStr, HTTP.UTF_8);
				stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
						RequestClient.CONTENT_TYPE));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return stringEntity;
	}
}
