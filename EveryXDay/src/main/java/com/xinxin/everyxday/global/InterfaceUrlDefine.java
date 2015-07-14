package com.xinxin.everyxday.global;

public class InterfaceUrlDefine {

	public static final String MIAOSHA_MAIN_URL = "/main"; // 秒杀

	public static final String MIAOSHA_MORE_URL = "/kills";// 秒杀商品


	public static final String MIAOSHA_DETAIL_URL = "/kills/";// 秒杀详情

	public static final String GUIDES_MAIN_URL = "/guides";// 值得买
	
	public static final String GUIDES_CATEGORIES_URL = "/categories";//值得买类目列表
	
	/*
	 * 购买成功后上报(用于用户已登录)
	 */
	public static final String PAY_RESULT_REPORT_WITH_LOGIN = "/guide/payment";
	
	/*
	 * 购买成功后上报(用于用户未登录)
	 */
	public static final String PAY_RESULT_REPORT_WITH_NOT_LOGIN = "/guide/orphanpay";
	
	/*
	 * 上报payOrderId
	 */
	public static final String PAY_ORDER_ID_REPORT = "/guide/prepay";
	
	/*
	 * 晒单
	 */
	public static final String SHAIDAN_MAIN_URL = "/shows";// 晒单列表
	
	/*
	 * 晒单精选
	 */
	public static final String SHAIDAN_EXCELLENT_URL = "/excellent/shows";
	
	/*
	 * 晒单精选评论
	 */
	public static final String SHAIDAN_EXCELLENT_COMMENT_URL = "/excellent/comment";

	public static final String SHAIDAN_CREATE_URL = "/shows/create";// 发布晒单
	
	/*
	 * 我的
	 */
	public static final String MY_MINE_URL = "/mine";// 我的
	
	public static final String MY_BALANCE_HISTORY_URL = "/mine/balance/history";// 账户余额
	
	public static final String MY_ORDERS_HISTORY_URL = "/mine/orders";// 我的-晒单返现、秒杀记录

	public static final String USER_LOGIN_URL = "/user/login";// 登陆url
	
	/*
	 * 注册
	 */
	public static final String REGISTER_GET_TOKEN_URL = "/phone/token";//"/user/phone/bind/token";// step 1 获取token
	public static final String REGISTER_REQUEST_SMS_CODE_URL = "/phone/captcha";//"/user/phone/bind/captcha";// step 2 请求发送短信验证码
	public static final String REGISTER_BIND_USER_URL = "/user/phone/bind";// step 3 提交验证码、昵称
	
	/*
	 * 提现
	 */
	public static final String REGISTER_WITHDRAW_URL = "/mine/balance/withdraw";
	
	/*
	 * 我的余额
	 */
	public static final String MY_BALANCE_URL = "/mine/balance/basic";
	
	/*
	 * 充值订单创建 (支付宝&微信)
	 */
	public static final String MY_BALANCE_DEPOSIT_URL = "/mine/balance/deposit";
	
	/*
	 * 支付宝支付结果上报
	 */
	public static final String ALIPAY_SYNC_URL = "/alipay/sync";
	
	/*
	 * 微信支付确认接口
	 */
	public static final String WECHAT_QUERY_URL = "/wechat/query";
	
//	/*
//	 * 消息中心列表
//	 */
//	public static final String MESSAGE_CENTER__URL = "/mine/messages";
	
	/*
	 * 公共消息中心列表
	 */
	public static final String PUBLIC_MESSAGE_CENTER_URL = "/mine/messages/public";
	
	/*
	 * 私人中心列表
	 */
	public static final String PRIVATE_MESSAGE_CENTER_URL = "/mine/messages/private";
	
	/*
	 * 数据上报
	 */
	public static final String REPORT_DEVICE_URL = "/report/device";// 上报
	
	/*
	 * 首页晒单列表赞/取消赞 赞接口需要拼接/shows/:id/zan
	 */
	public static final String SHOW_ORDER_ZAN_URL = "/zan";// 赞
	
	/*
	 * 首页晒单列表评论 评论接口需要拼接/shows/:id/say
	 */
	public static final String SHOW_ORDER_SAY_URL = "/say";// 评论
	
	/*
	 * 首页晒单列表item 评论列表 评论列表接口需要拼接/shows/:id/says
	 */
	public static final String SHOW_ORDER_SAYS_URL = "/says";// 评论列表
	
	/*
	 * 我的优惠券
	 */
	public static final String MY_COUPONS_URL = "/mine/coupons";
	
	/*
	 * webview JS注入配置信息
	 */
	public static final String BROWSER_INTERCEPT_MANIFEST_URL = "http://static.taoxiaoxian.com/static_file/manifest.json";
	
	/*
	 * 获取日志上报资源
	 */
	public static final String CREATE_REPORT_KILL_LOG_URL = "/report/urlhistory";
}
