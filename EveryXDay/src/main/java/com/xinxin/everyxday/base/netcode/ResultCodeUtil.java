package com.xinxin.everyxday.base.netcode;


import com.xinxin.everyxday.bean.base.CommonResponseHeader;
import com.xinxin.everyxday.util.StringUtil;

public class ResultCodeUtil {

	private static ResultCodeUtil instance;

	public static ResultCodeUtil getInstance() {
		if (instance == null) {
			instance = new ResultCodeUtil();
		}
		return instance;
	}

	//---------------通用异常
	
	public static final String BAD_REQUEST_UNKNOWN = "400"; //code
	public static final String BAD_REQUEST_TYPE_UNKNOWN = "type_unknown"; //type
	public static final String BAD_REQUEST_RESOURCE_UNKNOWN = "resource_unknown"; //resource
	
	public static final String SUCESS = "200";// 处理正常
	
	public static final String BAD_REQUEST_40001 = "40001"; //参数无效或缺少必要参数
	public static final String BAD_REQUEST_40002 = "40002"; //请求头缺少user-agent
	public static final String BAD_REQUEST_40003 = "40003"; //请求头缺少accept
	public static final String BAD_REQUEST_40004 = "40004"; //请求头expires过期(expires对应的过期时间小于服务器当前时间)
	public static final String BAD_REQUEST_40005 = "40005"; //错误的Method
	public static final String BAD_REQUEST_40006 = "40006"; //无效的Body，可能是JSON异常，或Query数据异常
	public static final String BAD_REQUEST_40007 = "40007"; //待定
	public static final String BAD_REQUEST_40101 = "40101"; //签名信息无效或缺少签名信息
	public static final String BAD_REQUEST_40401 = "40401"; //请求资源不存在
	public static final String BAD_REQUEST_40402 = "40402"; //优惠券冻结失败
	
	public static final String BAD_REQUEST_50001 = "50001"; //服务器端开小差了，暂时不能提供服务
	public static final String BAD_REQUEST_50002 = "50002"; //当前用户的帐户异常，Client 或将提示用户“再试/帐户异常/联系工作人员”
	public static final String BAD_REQUEST_50003 = "50003"; //当前用户信息异常
	public static final String BAD_REQUEST_50101 = "50101"; //用户余额不足以支付/秒杀/取现
	public static final String BAD_REQUEST_50102 = "50102"; //秒杀商品，售罄/暂停出售
	public static final String BAD_REQUEST_50103 = "50103"; //用户已经参与过秒杀
	public static final String BAD_REQUEST_50104 = "50104"; //秒杀未开始
	public static final String BAD_REQUEST_50105 = "50105"; //秒杀已结束

	/**
	 * 统一处理返回Result-Code
	 * 
	 * @param responseHeader
	 * @return
	 */
	public String getCommonResult(CommonResponseHeader responseHeader) {

		if (responseHeader == null || StringUtil.isEmpty(responseHeader.getResultCode())) {
			return "请求失败";
		}

		String result = responseHeader.getResultCode();

		if (SUCESS.equals(result)) {
			return SUCESS;
		}else if (BAD_REQUEST_40001.equals(result)) {
			return BAD_REQUEST_40001;
		}else if (BAD_REQUEST_40002.equals(result)) {
			return BAD_REQUEST_40002;
		}else if (BAD_REQUEST_40003.equals(result)) {
			return BAD_REQUEST_40003;
		}else if (BAD_REQUEST_40004.equals(result)) {
			return BAD_REQUEST_40004;
		}else if (BAD_REQUEST_40005.equals(result)) {
			return BAD_REQUEST_40005;
		}else if (BAD_REQUEST_40006.equals(result)) {
			return BAD_REQUEST_40006;
		}else if (BAD_REQUEST_40007.equals(result)) {
			return BAD_REQUEST_40007;
		}else if (BAD_REQUEST_40101.equals(result)) {
			return BAD_REQUEST_40101;
		}else if (BAD_REQUEST_40401.equals(result)) {
			return BAD_REQUEST_40401;
		}else if (BAD_REQUEST_40402.equals(result)) {
			return BAD_REQUEST_40402;
		}else if (BAD_REQUEST_50001.equals(result)) {
			return BAD_REQUEST_50001;
		}else if (BAD_REQUEST_50002.equals(result)) {
			return BAD_REQUEST_50002;
		}else if (BAD_REQUEST_50003.equals(result)) {
			return BAD_REQUEST_50003;
		}else if (BAD_REQUEST_50101.equals(result)) {
			return BAD_REQUEST_50101;
		}else if (BAD_REQUEST_50102.equals(result)) {
			return BAD_REQUEST_50102;
		}else if (BAD_REQUEST_50103.equals(result)) {
			return BAD_REQUEST_50103;
		}else if (BAD_REQUEST_50104.equals(result)) {
			return BAD_REQUEST_50104;
		}else if (BAD_REQUEST_50105.equals(result)) {
			return BAD_REQUEST_50105;
		}

		return "请求失败";
	}

}
