package com.xinxin.everyxday.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.xinxin.everyxday.EveryXDayApplication;

public class ProjectSettingInfoPreUtil extends PrefUtilBase {

	private static final String DEFAULT_PREF_NAME = "everyxday_sp";

	private static ProjectSettingInfoPreUtil instance;
	private static SharedPreferences sp;
	
	public ProjectSettingInfoPreUtil(Context context, String prefName, int mode){
		sp = context.getSharedPreferences(prefName, mode);
	}

	public static ProjectSettingInfoPreUtil getInstance(String prefName, int mode) {
		if(instance == null){
			EveryXDayApplication application = EveryXDayApplication.getInstance();
			if(application != null){
				Context context = application.getApplicationContext();
				if(context != null){
					instance = new ProjectSettingInfoPreUtil(context, prefName, mode);
				}
			}
		}
		return instance; 
	}
	
	public static ProjectSettingInfoPreUtil getInstance(String prefName) {
		return getInstance(prefName, Context.MODE_PRIVATE);
	}
	
	public static ProjectSettingInfoPreUtil getInstance() {
		return getInstance(DEFAULT_PREF_NAME);
	}
	
	@Override
	public SharedPreferences getSp() {
		return sp;
	}
	
	public void clearLoginInfo(){
		resetStringToQuote(PreferencesKey.SP_ACCESSKEY_KEY);
		resetStringToQuote(PreferencesKey.SP_SECRETKEY_KEY);
	}
	
	public String getAccessKey(){
		return getStringWithDefaultValueNull(PreferencesKey.SP_ACCESSKEY_KEY);
	}
	
	public void addAccessKey(String accessKey){
		addString(PreferencesKey.SP_ACCESSKEY_KEY, accessKey);
	}
	
	public String getSecretKey(){
		return getStringWithDefaultValueNull(PreferencesKey.SP_SECRETKEY_KEY);
	}
	
	public void addSecretKey(String secretKey){
		addString(PreferencesKey.SP_SECRETKEY_KEY, secretKey);
	}
	
	public String getOldRegId(){
		return getStringWithDefaultValue0(PreferencesKey.SP_OLD_PUSH_REGID_KEY);
	}
	
	public String getNewdRegId(){
		return getStringWithDefaultValue0(PreferencesKey.SP_NEW_PUSH_REGID_KEY);
	}
	
	public void addNewRegId(String value){
		addString(PreferencesKey.SP_NEW_PUSH_REGID_KEY, value);
	}
	
	public void addOldRegId(String value){
		addString(PreferencesKey.SP_OLD_PUSH_REGID_KEY, value);
	}
	
	public boolean getMessageSwitch(){
		return getBoolean(PreferencesKey.SP_PUSH_SWITCH_KEY, true);
	}
	
	public void addMessageSwitch(boolean value){
		addBoolean(PreferencesKey.SP_PUSH_SWITCH_KEY, value);
	}
	
	public boolean getShowNotificationWithKillId(String killId){
		return getBoolean(PreferencesKey.SP_SHOW_NOTIFICATION + killId, false);
	}
	
	public void addShowNotificationWithKillId(String killId, boolean value){
		addBoolean(PreferencesKey.SP_SHOW_NOTIFICATION + killId, value);
	}
	
	public boolean getIsFirstIn(){
		return getBoolean(PreferencesKey.SP_IS_FIRST_IN, true);
	}
	
	public void addIsFirstIn(boolean value){
		addBoolean(PreferencesKey.SP_IS_FIRST_IN, value);
	}
	
	public boolean getNeedStart(){
		return getBoolean(PreferencesKey.SP_NEED_START, false);
	}
	
	public void addNeedStart(boolean value){
		addBoolean(PreferencesKey.SP_NEED_START, value);
	}
	
	public boolean getIsFirstStartKillDtail(){
		return getBoolean(PreferencesKey.SP_IS_FIRST_START_KILL_DETAIL, true);
	}
	
	public void addIsFirstStartKillDtail(boolean value){
		addBoolean(PreferencesKey.SP_IS_FIRST_START_KILL_DETAIL, value);
	}
	
	public boolean getSpApplicationIsFirstStart(){
		return getBoolean(PreferencesKey.SP_APPLICATION_IS_FIRST_START, false);
	}
	
	public void addSpApplicationIsFirstStart(boolean value){
		addBoolean(PreferencesKey.SP_APPLICATION_IS_FIRST_START, value);
	}
	
}
