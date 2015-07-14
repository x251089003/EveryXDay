package com.xinxin.everyxday.base.loopj.requestinstance;

import android.content.Context;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.handler.AsyncHttpResponseHandler;
import com.xinxin.everyxday.base.loopj.requestclient.RequestClient;

import org.apache.http.Header;

public class UpyunUploadFileWrap {

	public static void postFileToUpyun(Context cxt, String uploadUrl,
			RequestParams params, Header[] headers,
			AsyncHttpResponseHandler responseHandler) {
		RequestClient.post(cxt, uploadUrl, params, headers, responseHandler);
	}
	
}
