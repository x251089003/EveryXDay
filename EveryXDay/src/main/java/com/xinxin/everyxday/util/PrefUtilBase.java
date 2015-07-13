package com.xinxin.everyxday.util;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public abstract class PrefUtilBase {
	
	public abstract SharedPreferences getSp();

	protected void remove(String key) {
		Editor editor = getSp().edit();
		editor.remove(key);
		editor.commit();
	}
	
	/* ----- Boolean ----- */
	
	protected void addBoolean(String key, Boolean value) {
		Editor editor = getSp().edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	protected boolean getBoolean(String key, boolean defValue) {
		return getSp().getBoolean(key, defValue);
	}
	
	protected boolean getBooleanWithDefaultValueFalse(String key) {
		return getSp().getBoolean(key, false);
	}
	
	protected boolean getBooleanWithDefaultValueTrue(String key) {
		return getSp().getBoolean(key, true);
	}
	
	/* ----- String ----- */
	
	protected void addString(String key, String value) {
		Editor editor = getSp().edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	protected void resetStringTo1(String key){
		addString(key, "1");
	}
	
	protected void resetStringTo0(String key){
		addString(key, "0");
	}
	
	protected void resetStringToQuote(String key){
		addString(key, "");
	}
	
	protected String getString(String key, String defValue) {
		return getSp().getString(key, defValue);
	}
	
	protected String getStringWithDefaultValueNull(String key) {
		return getSp().getString(key, null);
	}
	
	protected String getStringWithDefaultValueQuote(String key) {
		return getSp().getString(key, "");
	}
	
	protected String getStringWithDefaultValue0(String key) {
		return getSp().getString(key, "0");
	}
	
	protected String getStringWithDefaultValue1(String key) {
		return getSp().getString(key, "1");
	}
	
	protected String getStringWithDefaultValueTrue(String key) {
		return getSp().getString(key, "true");
	}
	
	/* ----- Integer ----- */
	
	protected void addInt(String key, int value) {
		Editor editor = getSp().edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	protected int getIntWithDefaultValue0(String key) {
		return getSp().getInt(key, 0);
	}
	
	protected int getInt(String key, int defValue) {
		return getSp().getInt(key, defValue);
	}
	
	/* ----- Float ----- */
	
	protected void addFloat(String key, float value) {
		Editor editor = getSp().edit();
		editor.putFloat(key, value);
		editor.commit();
	}
	
	protected void resetFloatTo0(String key){
		addFloat(key, 0);
	}
	
	protected float getFloatWithDefaultValue0(String key) {
		return getSp().getFloat(key, 0);
	}
	
	protected float getFloat(String key, float defValue) {
		return getSp().getFloat(key, defValue);
	}
}
