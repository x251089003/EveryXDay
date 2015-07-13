package com.xinxin.everyxday.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.xinxin.everyxday.EveryXDayApplication;
import com.xinxin.everyxday.bean.MineBean;

public class UserInfoPreUtil extends PrefUtilBase {
	
	private static final String DEFAULT_PREF_NAME = "miaosha_sp";

	private static UserInfoPreUtil instance;
	private static SharedPreferences sp;
	
	public UserInfoPreUtil(Context context, String prefName, int mode){
		sp = context.getSharedPreferences(prefName, mode);
	}

	public static UserInfoPreUtil getInstance(String prefName, int mode) {
		if(instance == null){
			EveryXDayApplication application = EveryXDayApplication.getInstance();
			if(application != null){
				Context context = application.getApplicationContext();
				if(context != null){
					instance = new UserInfoPreUtil(context, prefName, mode);
				}
			}
		}
		return instance; 
	}
	
	public static UserInfoPreUtil getInstance(String prefName) {
		return getInstance(prefName, Context.MODE_PRIVATE);
	}
	
	public static UserInfoPreUtil getInstance() {
		return getInstance(DEFAULT_PREF_NAME);
	}
	
	@Override
	public SharedPreferences getSp() {
		return sp;
	}
	
	public MineBean getUserInfo(){
		
		MineBean bean = new MineBean();
		
		bean.setAvatar(getUserAvater());
		bean.setNickname(getUserNickName());
		bean.setPhone(getUserPhone());
		bean.setBalance(getUserBalance());
		bean.setTotalCashBack(getUserTotalCashBack());
		
		return bean;
	}
	
	public void addUserInfo(MineBean bean){
		
		addUserNickName(bean.getNickname());
		addUserAvater(bean.getAvatar());
		addUserBalance(bean.getBalance());
		addUserTotalCashBack(bean.getTotalCashBack());
		addUserPhone(bean.getPhone());
		addUserInviteFriendUrl(bean.getInviteUrl());
	}
	
	public void clearUserInfo(){
		
		resetStringToQuote(PreferencesKey.SP_USER_NICKNAME_KEY);
		resetStringToQuote(PreferencesKey.SP_USER_AVATER_KEY);
		resetFloatTo0(PreferencesKey.SP_USER_BALANCE_KEY);
		resetFloatTo0(PreferencesKey.SP_USER_TOTAL_CASH_BACK_KEY);
		resetStringToQuote(PreferencesKey.SP_USER_PHONE);
		resetStringToQuote(PreferencesKey.SP_USER_INVITE_FRIEND_URL);
	}
	
	public String getUserAvater(){
		return getStringWithDefaultValueQuote(PreferencesKey.SP_USER_AVATER_KEY);
	}
	
	public void addUserAvater(String avater){
		addString(PreferencesKey.SP_USER_AVATER_KEY, avater);
	}
	
	public String getUserNickName(){
		return getStringWithDefaultValueQuote(PreferencesKey.SP_USER_NICKNAME_KEY);
	}
	
	public void addUserNickName(String nickName){
		addString(PreferencesKey.SP_USER_NICKNAME_KEY, nickName);
	}
	
	public float getUserBalance(){
		return getFloatWithDefaultValue0(PreferencesKey.SP_USER_BALANCE_KEY);
	}
	
	public void addUserBalance(float balance){
		addFloat(PreferencesKey.SP_USER_BALANCE_KEY, balance);
	}
	
	public float getUserTotalCashBack(){
		return getFloatWithDefaultValue0(PreferencesKey.SP_USER_TOTAL_CASH_BACK_KEY);
	}
	
	public void addUserTotalCashBack(float cashBack){
		addFloat(PreferencesKey.SP_USER_TOTAL_CASH_BACK_KEY, cashBack);
	}
	
	public String getUserPhone(){
		return getStringWithDefaultValueQuote(PreferencesKey.SP_USER_PHONE);
	}
	
	public void addUserPhone(String phone){
		addString(PreferencesKey.SP_USER_PHONE, phone);
	}
	
	public String getUserAlipayCount(){
		return getStringWithDefaultValueQuote(PreferencesKey.SP_USER_ALIPAY_COUNT);
	}
	
	public void addUserAlipayCount(String alipayCount){
		addString(PreferencesKey.SP_USER_ALIPAY_COUNT, alipayCount);
	}
	
	public String getUserInviteFriendUrl(){
		return getStringWithDefaultValueQuote(PreferencesKey.SP_USER_INVITE_FRIEND_URL);
	}
	
	public void addUserInviteFriendUrl(String inviteFriendUrl){
		addString(PreferencesKey.SP_USER_INVITE_FRIEND_URL, inviteFriendUrl);
	}

}
