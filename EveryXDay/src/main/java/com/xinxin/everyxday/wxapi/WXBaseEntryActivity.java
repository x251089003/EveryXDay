package com.xinxin.everyxday.wxapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.xinxin.everyxday.global.Globe;

/**
 * 微信回调基类
 * 
 */
public abstract class WXBaseEntryActivity extends Activity implements IWXAPIEventHandler{
	
    private IWXAPI wxApi;
    
    public static IWXAPI getIWXAPI(Context context){
    	return WXAPIFactory.createWXAPI(context, Globe.WX_APP_ID, true);
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	wxApi = getIWXAPI(this);
        wxApi.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        wxApi.handleIntent(intent, this);
	}
	
}