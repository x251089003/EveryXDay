package com.xinxin.everyxday.base.loopj.requestinstance;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.xinxin.everyxday.activity.R;
import com.xinxin.everyxday.bean.base.CommonResponseBody;
import com.xinxin.everyxday.bean.base.CommonResponseErrorBean;

public abstract class CommonRequestWrapDelegateAbstractImpl<T> implements CommonRequestWrap.CommonRequestWrapDelegate<T> {
	
	@Override
	public void requestServerStart(ProgressDialog progressDialog) {
		RequestProgressDialogWrap.showProgressDialog(progressDialog);
	}

	@Override
	public void requestServerNetWorkError() {
	}
	
	public abstract void requestServerSuccess(CommonResponseBody<T> responseBody);

	@Override
	public void requestServerFailure(Context cxt, CommonResponseErrorBean errorBean){
		Toast.makeText(cxt, errorBean.getCode(), Toast.LENGTH_SHORT).show();
	}

	@Override
	public void requestServerResponseError(Context cxt) {
		Toast.makeText(cxt, R.string.request_failure, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void requestServerEnd(ProgressDialog progressDialog) {
		RequestProgressDialogWrap.dismissProgressDialog(progressDialog);
	}

}
