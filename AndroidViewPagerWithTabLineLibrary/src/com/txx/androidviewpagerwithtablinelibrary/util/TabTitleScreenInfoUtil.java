package com.txx.androidviewpagerwithtablinelibrary.util;

import java.lang.reflect.Field;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class TabTitleScreenInfoUtil {

	private static DisplayMetrics getDisplayMetrics(Context ctx){
		Resources re = ctx.getResources();
		return re.getDisplayMetrics();
	}
	
	public static int getScreenWidth(Context ctx){
        return getDisplayMetrics(ctx).widthPixels;
	}
	
	public static int getScreenHeight(Context ctx){
        return getDisplayMetrics(ctx).heightPixels;
	}
	
	public static int getStatusBarHeight(Context ctx){
		int statusBarHeight = 38;
		try {  
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");  
            Object object = clazz.newInstance();  
            Field field = clazz.getField("status_bar_height");  
            int height = Integer.parseInt(field.get(object).toString());  
            statusBarHeight = ctx.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {  
        	e.printStackTrace();
        }
		return statusBarHeight;
	}
	
}
