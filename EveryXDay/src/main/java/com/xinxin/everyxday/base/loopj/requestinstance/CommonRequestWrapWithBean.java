package com.xinxin.everyxday.base.loopj.requestinstance;

import android.content.Context;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.xinxin.everyxday.bean.base.CommonResponseBody;

/**
 * 用于非列表型接口返回非列表型数据的请求和解析
 * @param <T>
 */

public class CommonRequestWrapWithBean<T> extends CommonRequestWrap<T> {

	public CommonRequestWrapWithBean(Context cxt, String requestType,
			RequestParams requestParams, boolean isNeedSigned,
			CommonRequestWrapDelegate<T> commonRequestWrapDelegate,
			Class<T> beanType) {
		super(cxt, requestType, requestParams, isNeedSigned,
				commonRequestWrapDelegate, beanType);
	}

	public CommonRequestWrapWithBean(Context cxt, String requestType,
			RequestParams requestParams, boolean isNeedSigned, int stringId,
			CommonRequestWrapDelegate<T> commonRequestWrapDelegate,
			Class<T> beanType) {
		super(cxt, requestType, requestParams, isNeedSigned, stringId,
				commonRequestWrapDelegate, beanType);
	}

	@Override
	public void setResponseObject(CommonResponseBody<T> responseBody, String str,
			Gson gson) {
		responseBody.setResponseObject(gson.fromJson(str, getBeanType()));
	}

}
