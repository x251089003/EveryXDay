package com.xinxin.everyxday.util;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * 启动第三方分享或者充值前,先判断手机是否已经安装了客户端.
 */
public class AppInstallUtil {

	public static final String CLIENT_PACKAGE_WEIBO = "com.sina.weibo";
	public static final String CLIENT_PACKAGE_WEIXIN = "com.tencent.mm";
	public static final String CLIENT_PACKAGE_QQ = "com.tencent.mobileqq";

	public static boolean isWeiXinInstalled(Context context) {
		return appInstalledOrNot(CLIENT_PACKAGE_WEIXIN, context);
	}

	public static boolean isQQInstalled(Context context) {
		return appInstalledOrNot(CLIENT_PACKAGE_QQ, context);
	}

	public static boolean isWeiBoInstalled(Context context) {
		return appInstalledOrNot(CLIENT_PACKAGE_WEIBO, context);
	}

	private static boolean appInstalledOrNot(String uri, Context context) {

		PackageManager pm = context.getPackageManager();
		boolean app_installed;
		try {
			pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
			app_installed = true;
		} catch (PackageManager.NameNotFoundException e) {
			app_installed = false;
		}
		return app_installed;
	}

}
