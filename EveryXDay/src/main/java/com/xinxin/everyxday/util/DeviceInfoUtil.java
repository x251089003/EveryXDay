package com.xinxin.everyxday.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import java.lang.reflect.Field;

public class DeviceInfoUtil {

	/**
	 * 获取当前网络状态
	 * 
	 * @return NetworkInfo
	 */
	public static NetworkInfo getCurrentNetStatus(Context ctx) {
		ConnectivityManager manager = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		return manager.getActiveNetworkInfo();
	}

	/**
	 * 获取网络连接状态
	 * 
	 * @param ctx
	 * @return true:有网 false：没网
	 */
	public static boolean isNetworkAvailable(Context ctx) {
		NetworkInfo nki = getCurrentNetStatus(ctx);
		if (nki != null) {
			return nki.isAvailable();
		} else
			return false;
	}

	/**
	 * 获取当前程序版本名
	 * 
	 * @param context
	 * @return 返回当前程序版本名
	 */
	public static String getAppVersionName(Context context) {
		String versionName = "";
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
			if (versionName == null || versionName.length() <= 0) {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionName;
	}

	/**
	 * 获取客户端版本号
	 * 
	 * @param c
	 * @return 版本号
	 */
	public static int getVersionCode(Context c) {
		PackageInfo pi = null;
		try {
			pi = c.getPackageManager().getPackageInfo(c.getPackageName(),
					PackageManager.GET_ACTIVITIES);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return pi.versionCode;
	}

	/**
	 * 获取可用存储空间大小 若存在SD卡则返回SD卡剩余空间大小 否则返回手机内存剩余空间大小
	 * 
	 * @return
	 */
	public static long getAvailableStorageSpace() {
		long externalSpace = getExternalStorageSpace();
		if (externalSpace == -1L) {
			return getInternalStorageSpace();
		}

		return externalSpace;
	}

	/**
	 * 获取SD卡可用空间
	 * 
	 * @return availableSDCardSpace 可用空间(MB)。-1L:没有SD卡
	 */
	public static long getExternalStorageSpace() {
		long availableSDCardSpace = -1L;
		// 存在SD卡
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			StatFs sf = new StatFs(Environment.getExternalStorageDirectory()
					.getPath());
			long blockSize = sf.getBlockSize();// 块大小,单位byte
			long availCount = sf.getAvailableBlocks();// 可用块数量
			availableSDCardSpace = availCount * blockSize / 1024 / 1024;// 可用SD卡空间，单位MB
		}

		return availableSDCardSpace;
	}

	/**
	 * 获取机器内部可用空间
	 * 
	 * @return availableSDCardSpace 可用空间(MB)。-1L:没有SD卡
	 */
	public static long getInternalStorageSpace() {
		long availableInternalSpace = -1L;

		StatFs sf = new StatFs(Environment.getDataDirectory().getPath());
		long blockSize = sf.getBlockSize();// 块大小,单位byte
		long availCount = sf.getAvailableBlocks();// 可用块数量
		availableInternalSpace = availCount * blockSize / 1024 / 1024;// 可用SD卡空间，单位MB

		return availableInternalSpace;
	}

	/**
	 * 获取SD卡总空间
	 * 
	 * @return availableSDCardSpace 总空间(MB)。-1L:没有SD卡
	 */
	public static long getExternalStorageTotalSpace() {
		long availableSDCardSpace = -1L;
		// 存在SD卡
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			StatFs sf = new StatFs(Environment.getExternalStorageDirectory()
					.getPath());
			long blockSize = sf.getBlockSize();// 块大小,单位byte
			long blockCount = sf.getBlockCount();// 块总数量
			availableSDCardSpace = blockCount * blockSize / 1024 / 1024;// 总SD卡空间，单位MB
		}

		return availableSDCardSpace;
	}

	/**
	 * 获取mac 地址
	 * 
	 * @return
	 */
	public static String getLocalMacAddress(Context ctx) {
		WifiManager wifi = (WifiManager) ctx
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		return info.getMacAddress();
	}

	/**
	 * 获得设备型号
	 * 
	 * @return
	 */
	public static String getDeviceModel() {
		return Build.MODEL;
	}

	/**
	 * 获得国际移动设备身份码
	 * 
	 * @param context
	 * @return
	 */
	public static String getIMEI(Context context) {
		return ((TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
	}

	/**
	 * 获得国际移动用户识别码
	 * 
	 * @param context
	 * @return
	 */
	public static String getIMSI(Context context) {
		return ((TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
	}

	/**
	 * 获得设备屏幕矩形区域范围
	 * 
	 * @param activity
	 * @return
	 */
	public static Rect getScreenRect(Activity activity) {
		// 取出平屏幕的宽和高
		DisplayMetrics metric = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
		return new Rect(0, 0, metric.widthPixels, metric.heightPixels);
	}

	/**
	 * 获得设备屏幕密度
	 */
	public static float getScreenDensity(Context context) {
		DisplayMetrics metrics = context.getApplicationContext().getResources()
				.getDisplayMetrics();
		return metrics.density;
	}

	/**
	 * 获得系统版本
	 */
	public static String getSDKVersion() {
		return android.os.Build.VERSION.SDK;
	}

	public static int getSDKVersionInt() {
		return android.os.Build.VERSION.SDK_INT;
	}

	public static String getSystemVersion() {
		return android.os.Build.VERSION.RELEASE;
	}

	public static String getTelNumber(Context ctx) {
		return ((TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
	}
	
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

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

}
