package com.xinxin.everyxday.base.jsonparser;

import com.xinxin.everyxday.base.netcode.ResultCodeUtil;
import com.xinxin.everyxday.bean.base.CommonResponseHeader;

public class InterfaceResultParserBase {
	
	protected static CommonResponseHeader processResult(CommonResponseHeader responseHeader){
		if(responseHeader != null){
			responseHeader.setResultCode(ResultCodeUtil.getInstance().getCommonResult(responseHeader));
		}
		return responseHeader;
	}

}
