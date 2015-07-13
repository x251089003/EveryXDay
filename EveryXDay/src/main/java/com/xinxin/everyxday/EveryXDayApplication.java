package com.xinxin.everyxday;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.*;
import android.util.Log;

import com.xinxin.everyxday.util.LocalStorageUtil;
import com.xinxin.everyxday.util.ProjectSettingInfoPreUtil;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

public class EveryXDayApplication extends Application{

    private static EveryXDayApplication instance;

    private LocalStorageUtil mLocalStorageUtil;

    public LocalStorageUtil getLocalStorageUtil() {
        return mLocalStorageUtil;
    }

    public static EveryXDayApplication getInstance() {
        if (instance == null) {
            instance = new EveryXDayApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {

        super.onCreate();

        instance = this;

        //存储application刚启动状态
        ProjectSettingInfoPreUtil psip = ProjectSettingInfoPreUtil.getInstance();
        psip.addSpApplicationIsFirstStart(true);

        mLocalStorageUtil = new LocalStorageUtil();
        mLocalStorageUtil.initLocalDir(this);

        initImageLoader(getApplicationContext());

        startXiaoMiPush();
    }

    public void initImageLoader(Context context) {

        File cacheDir = new File(mLocalStorageUtil.getImageCacheAbsoluteDir());

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCache(new UnlimitedDiscCache(cacheDir))
//				.writeDebugLogs() // Remove for release app
                .build();
        ImageLoader.getInstance().init(config);
    }

    // 小米推送APPID
    private static final String APP_ID = "2882303761517314666";
    // 小米推送APPKEY
    private static final String APP_KEY = "5561731444666";
    // 此TAG在adb logcat中检索自己所需要的信息， 只需在命令行终端输入 adb logcat | grep
    private static final String TAG = "com.txx.miaosha";

    private void startXiaoMiPush(){

        LoggerInterface newLogger = new LoggerInterface() {

            @Override
            public void setTag(String tag) {
                // ignore
            }

            @Override
            public void log(String content, Throwable t) {
                Log.d(TAG, content, t);
            }

            @Override
            public void log(String content) {
                Log.d(TAG, content);
            }
        };

        Logger.setLogger(this, newLogger);

        if (shouldInit()) {
            MiPushClient.registerPush(this, APP_ID, APP_KEY);
        }
    }

    private boolean shouldInit() {

        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
