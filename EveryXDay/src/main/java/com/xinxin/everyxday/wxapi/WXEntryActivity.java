package com.xinxin.everyxday.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.sdk.modelmsg.WXAppExtendObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.xinxin.everyxday.R;
import com.xinxin.everyxday.global.Globe;

/**
 * Created by xinxin on 15/8/28.
 */
public class WXEntryActivity extends WXBaseEntryActivity{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {

        if(resp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX){

//			String result = "";
//
//			switch (resp.errCode) {
//			case BaseResp.ErrCode.ERR_OK:
//				result = "分享成功";
//				break;
//			case BaseResp.ErrCode.ERR_SENT_FAILED:
//				result = "分享失败";
//				break;
//			case BaseResp.ErrCode.ERR_USER_CANCEL:
//				result = "分享已取消";
//				break;
//			case BaseResp.ErrCode.ERR_AUTH_DENIED:
//				result = "认证失败";
//				break;
//			default:
//				result = "未知异常";
//				break;
//			}
            finish();
//			Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        }
    }

}
