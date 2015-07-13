package com.xinxin.everyxday.base.loopj.jsoncache;

import java.io.File;

//TODO
/**
 * 缓存策略类 指定缓存生成规则
 * 列表数据缓存需要做 CommonResponseHeader(记录加载状态) 和 responseString(列表数据) 两个文件缓存
 */

public class ResponseCachePolicy extends ResponseCachePolicyBase {

	private static ResponseCachePolicy responseCachePolicy;

	public static ResponseCachePolicy getInstance() {
		if (responseCachePolicy == null) {
			responseCachePolicy = new ResponseCachePolicy();
		}
		return responseCachePolicy;
	}
	
	@Override
	protected String getResponseHeaderCacheFilePath(String requestUrl, int page){
		return MiaoShaApplication.getInstance().getLocalStorageUtil().getFileCacheAbsoluteDir() + 
				File.separator+ Md5Encode.getMD5(requestUrl+page+"header");
	}
	
	@Override
	protected boolean isNeedResponseHeaderCache(String requestUrl, int page){
		
		if (!StringUtil.isEmpty(requestUrl)) {
			if (requestUrl.contains(InterfaceUrlDefine.GUIDES_MAIN_URL)) {
				if (page == 1) {
					return true;
				}
			}
		}
		return false;
	}
	
	//本地缓存文件规则
	@Override
	protected String getDataCacheFilePath(String requestUrl, int page){
		return MiaoShaApplication.getInstance().getLocalStorageUtil().getFileCacheAbsoluteDir() + 
				File.separator+ Md5Encode.getMD5(requestUrl+page);
	}

	@Override
	// 接口缓存规则 具体指定哪些数据需要缓存
	protected boolean isNeedDataCache(String requestUrl, int page) {
		
		if (!StringUtil.isEmpty(requestUrl)) {
			if(page == 1){
				if (requestUrl.contains(InterfaceUrlDefine.GUIDES_MAIN_URL)) {
					return true;
				}
			}
		}
		return false;
	}

}
