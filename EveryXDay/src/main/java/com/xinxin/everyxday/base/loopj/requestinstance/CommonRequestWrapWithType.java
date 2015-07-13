package com.xinxin.everyxday.base.loopj.requestinstance;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.loopj.android.http.RequestParams;
import com.txx.miaosha.bean.base.CommonResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于非列表型接口返回列表型数据的请求和解析
 * 
 * @author tangjie
 * 
 * @param <T>
 */

public class CommonRequestWrapWithType<T> extends CommonRequestWrap<T> {
	
	public CommonRequestWrapWithType(Context cxt, String requestType,
			RequestParams requestParams, boolean isNeedSigned,
			CommonRequestWrapDelegate<T> commonRequestWrapDelegate, Class<T> beanType) {
		super(cxt, requestType, requestParams, isNeedSigned,
				commonRequestWrapDelegate, beanType);
	}

	public CommonRequestWrapWithType(Context cxt, String requestType,
			RequestParams requestParams, boolean isNeedSigned, int stringId,
			CommonRequestWrapDelegate<T> commonRequestWrapDelegate, Class<T> beanType) {
		super(cxt, requestType, requestParams, isNeedSigned, stringId,
				commonRequestWrapDelegate, beanType);
	}

	@Override
	public void setResponseObject(CommonResponseBody<T> responseBody,
			String str, Gson gson) {
		
		try {

			JsonParser parser = new JsonParser();
			JsonArray array = parser.parse(str).getAsJsonArray();

			List<T> lst = new ArrayList<T>();
			for (final JsonElement json : array) {
				T entity = gson.fromJson(json, getBeanType());
				lst.add(entity);
			}
			
			responseBody.setList(lst);
			
		} catch (Exception e) {
			
		}
	}

}
