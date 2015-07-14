package com.xinxin.everyxday.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Rect;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.xinxin.everyxday.EveryXDayApplication;

/**
 * 设备及OS相关信息功能类
 */
public class AndroidOSInfoUtil {

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
		} else {
			return false;
		}
	}

	/**
	 * 获取当前程序版本名
	 * @return 返回当前程序版本名
	 */
	public static String getAppVersionName() {
		String versionName = "";
		try {
			PackageManager pm = EveryXDayApplication.getInstance()
					.getApplicationContext().getPackageManager();
			PackageInfo pi = pm.getPackageInfo(EveryXDayApplication.getInstance()
					.getApplicationContext().getPackageName(), 0);
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
			// 块大小,单位byte
			long blockSize = sf.getBlockSize();
			// 可用块数量
			long availCount = sf.getAvailableBlocks();
			// 可用SD卡空间，单位MB
			availableSDCardSpace = availCount * blockSize / 1024 / 1024;
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
		// 块大小,单位byte
		long blockSize = sf.getBlockSize();
		// 可用块数量
		long availCount = sf.getAvailableBlocks();
		// 可用SD卡空间，单位MB
		availableInternalSpace = availCount * blockSize / 1024 / 1024;
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
			// 块大小,单位byte
			long blockSize = sf.getBlockSize();
			// 块总数量
			long blockCount = sf.getBlockCount();
			// 总SD卡空间，单位MB
			availableSDCardSpace = blockCount * blockSize / 1024 / 1024;
		}
		return availableSDCardSpace;
	}

	/**
	 * 获取MAC地址
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
	 * 获得设备屏幕宽度
	 * @return
	 */
	public static int getScreenWidth() {
		WindowManager wm = (WindowManager) EveryXDayApplication.getInstance().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		int height = wm.getDefaultDisplay().getHeight();
		return width;
	}

	/**
	 * 获得设备屏幕密度
	 * 
	 * @param context
	 * @return
	 */
	public static float getScreenDensity(Context context) {
		DisplayMetrics metrics = context.getApplicationContext().getResources()
				.getDisplayMetrics();
		return metrics.density;
	}

	/**
	 * 获得SDK版本
	 */
	public static String getSDKVersion() {
		return Build.VERSION.SDK;
	}

	/**
	 * 获得SDK版本数值
	 */
	public static int getSDKVersionInt() {
		return Build.VERSION.SDK_INT;
	}

	/**
	 * 获得系统版本
	 */
	public static String getSystemVersion() {
		return Build.VERSION.RELEASE;
	}

	/**
	 * 获取android id
	 * http://stackoverflow.com/a/2785493
	 * @param ctx
	 * @return
	 */
	public static String getAndroidId(Context ctx){
		return Secure.getString(ctx.getContentResolver(), Secure.ANDROID_ID);
	}
	
	/**
	 * 获取设备位置信息通过GPS或者WIFI
	 * 
	 * @param context
	 * @return
	 */
	public static Location getLocation(Context context) {
		try {
			LocationManager locMan = (LocationManager) context
					.getSystemService(Context.LOCATION_SERVICE);
			Location location = locMan
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (location == null) {
				location = locMan
						.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			}
			return location;
		} catch (Exception e) {
		}
		return null;
	}

	public static boolean hasFroyo() {
		if (Build.VERSION.SDK_INT >= 8)
			return true;

		return false;
	}

	public static boolean hasGingerbread() {
		if (Build.VERSION.SDK_INT >= 9)
			return true;

		return false;
	}

	public static boolean hasHoneycomb() {
		if (Build.VERSION.SDK_INT >= 11)
			return true;

		return false;
	}

	public static boolean hasHoneycombMR1() {
		if (Build.VERSION.SDK_INT >= 12)
			return true;

		return false;
	}

	public static boolean hasIceCreamSandwich() {
		if (Build.VERSION.SDK_INT >= 14)
			return true;

		return false;
	}

	public static boolean hasIceCreamSandwichMR1() {
		if (Build.VERSION.SDK_INT >= 15)
			return true;

		return false;
	}

	public static boolean hasJellyBean() {
		if (Build.VERSION.SDK_INT >= 16)
			return true;

		return false;
	}

	public static boolean hasKITKAT() {
		if (Build.VERSION.SDK_INT >= 19)
			return true;

		return false;
	}
	
	/**
	 * 获取渠道号
	 * @param ctx
	 * @return
	 */
	public static String getApkChannel(Context ctx) {
		// 渠道号
		String qudao = "unkown";
		try {
			ApplicationInfo appInfo = ctx.getPackageManager()
					.getApplicationInfo(ctx.getPackageName(),PackageManager.GET_META_DATA);
			qudao = appInfo.metaData.getString("UMENG_CHANNEL");
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return qudao;
	}
}
