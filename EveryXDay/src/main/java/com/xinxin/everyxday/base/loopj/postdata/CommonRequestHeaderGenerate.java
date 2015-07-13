/******************************************************************************
 * PROPRIETARY/CONFIDENTIAL 
 * Copyright (c) 2015 XianTao Technology Co.,Ltd
 * 
 * All rights reserved. This medium contains confidential and proprietary 
 * source code and other information which is the exclusive property of 
 * XianTao Technology Co.,Ltd. None of these materials may be used, 
 * disclosed, transcribed, stored in a retrieval system, translated into any 
 * other language or other computer language, or transmitted in any 
 * form or by any means (electronic, mechanical, photocopied, recorded 
 * or otherwise) without the prior written permission of XianTao Technology 
 * Co.,Ltd. 
 *******************************************************************************/
package com.xinxin.everyxday.base.loopj.postdata;

import com.txx.miaosha.base.loopj.requestclient.RequestClient;
import com.txx.miaosha.util.AndroidOSInfoUtil;
import com.txx.miaosha.util.SignUtil;
import com.txx.miaosha.util.StringUtil;
import com.txx.miaosha.util.TimeUtil;
import com.txx.miaosha.util.sp.ProjectSettingInfoPreUtl;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CommonRequestHeaderGenerate {
	
	/**
	 * 请求header封装 (带签名/不带签名)
	 * @param httpVerb 动作类型(get/post/put)
	 * @return
	 */
	public static Header[] generateRequestHeader(String httpVerb,
			String jsonStr, boolean isNeedSigned, String urlWithParams) {
		
		String contentType = StringUtil.isEmpty(jsonStr) ? null : RequestClient.CONTENT_TYPE;
		
		if(isNeedSigned){
			return CommonRequestHeaderGenerate.generateSignedRequestHeader(contentType, httpVerb, urlWithParams);
		}else{
			return CommonRequestHeaderGenerate.generateCommonRequestHeader();
		}
	}
	
	/**
	 * 生成普通不带签名的请求header
	 * @return
	 */
	public static Header[] generateCommonRequestHeader(){
		
		ArrayList<Header> headers = new ArrayList<Header>();
		
		headers.add(new BasicHeader(RequestClient.ACCEPT_KEY, RequestClient.ACCEPT_VALUE));
		headers.add(new BasicHeader(RequestClient.USER_AGENT_KEY, generateUserAgent()));
		
		//所有请求带上Access-Key 暂时只有首页晒单列表必须带上
		ProjectSettingInfoPreUtl psip = ProjectSettingInfoPreUtl.getInstance();
		String accessKey = psip.getAccessKey();
		if(!StringUtil.isEmpty(accessKey)){
			headers.add(new BasicHeader(RequestClient.ACCESS_KEY, accessKey));
		}
		
		Header[] hs = new Header[headers.size()];
		return headers.toArray(hs);
	}
	
	/**
	 * 生成带签名的请求header
	 * @param contentType
	 * @param httpVerb
	 * @param urlWithParams
	 * @return
	 */
	public static Header[] generateSignedRequestHeader(String contentType, String httpVerb, String urlWithParams){
		
		ArrayList<Header> headers = new ArrayList<Header>();
		
		headers.add(new BasicHeader(RequestClient.HOST_KEY, RequestClient.HOST_VALUE));
		headers.add(new BasicHeader(RequestClient.ACCEPT_KEY, RequestClient.ACCEPT_VALUE));
		headers.add(new BasicHeader(RequestClient.USER_AGENT_KEY, generateUserAgent()));
		
		if (!StringUtil.isEmpty(contentType))
			headers.add(new BasicHeader(RequestClient.CONTENT_TYPE_KEY, contentType));
		
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
		Date now = TimeUtil.getNow();
		@SuppressWarnings("deprecation")
		Date d = new Date(now.getTime() + now.getTimezoneOffset()*60*1000 + 5*60*1000);////获取当前时间+5分钟
		String expires = sdf.format(d);
		headers.add(new BasicHeader(RequestClient.EXPIRES_KEY, expires));
		
		ProjectSettingInfoPreUtl psip = ProjectSettingInfoPreUtl.getInstance();
		String accessKey = psip.getAccessKey();
		String secretKey = psip.getSecretKey();

		if (!StringUtil.isEmpty(accessKey) && !StringUtil.isEmpty(secretKey)){
			SignUtil su = new SignUtil();
			
			/*
			 * StringToSign = HTTP-Verb + "\n" +
		               Content-Type + "\n" +
		               Expire + "\n" +
		               ResourceURI;             //资源URI,域名后面所有的部分.
			 */
			StringBuffer stringToSign = new StringBuffer();
			stringToSign.append(httpVerb+"\n");
			stringToSign.append((StringUtil.isEmpty(contentType)?"":contentType)+"\n");
			stringToSign.append(expires+"\n");
			stringToSign.append(urlWithParams);
		
			try {
				String ssig = su.genAuthorizationValue(stringToSign.toString(), secretKey);
				headers.add(new BasicHeader("Authorization", accessKey+":"+ssig));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			//accessKey, secretKey不存在，不签名，服务器会直接返回相应错误
		}
		
		Header[] hs = new Header[headers.size()];
		headers.toArray(hs);
		
		return hs;
	}
	
	private static String generateUserAgent(){
		
		StringBuilder userAgentBuilder = new StringBuilder();
		
		userAgentBuilder.append(RequestClient.USER_AGENT_KEY);
		userAgentBuilder.append(RequestClient.APP_PROJECT_NAME);
		userAgentBuilder.append("/");
		userAgentBuilder.append(AndroidOSInfoUtil.getAppVersionName());
		userAgentBuilder.append("(");
		userAgentBuilder.append(RequestClient.OS);
		userAgentBuilder.append(";");
		userAgentBuilder.append(AndroidOSInfoUtil.getDeviceModel());
		userAgentBuilder.append("/");
		userAgentBuilder.append(AndroidOSInfoUtil.getSystemVersion());
		userAgentBuilder.append(";");
		userAgentBuilder.append(RequestClient.JAVA_VERSION);
		userAgentBuilder.append(")");
		
		String accessKey = ProjectSettingInfoPreUtl.getInstance().getAccessKey();
		if(StringUtil.isEmpty(accessKey)){
			userAgentBuilder.append(" -");
		}else{
			userAgentBuilder.append(" " + accessKey);
		}
		
		return userAgentBuilder.toString();
	}

}
