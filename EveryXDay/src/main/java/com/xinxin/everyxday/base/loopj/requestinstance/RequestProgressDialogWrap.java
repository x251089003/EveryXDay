package com.xinxin.everyxday.base.loopj.requestinstance;

import android.app.ProgressDialog;
import android.content.Context;

public class RequestProgressDialogWrap {

	public static ProgressDialog createProgressDialog(Context cxt, int stringId){
		ProgressDialog progressDialog = new ProgressDialog(cxt);
		progressDialog.setMessage(cxt.getResources().getString(stringId));
		progressDialog.setCancelable(false);
		
		return progressDialog;
	}
	
	public static void dismissProgressDialog(ProgressDialog progressDialog){
		if(progressDialog != null && progressDialog.isShowing()){
			progressDialog.dismiss();
		}
	}
	
	public static void showProgressDialog(ProgressDialog progressDialog){
		if(progressDialog != null && !progressDialog.isShowing()){
			progressDialog.show();
		}
	}
	
}
