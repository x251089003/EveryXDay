package com.xinxin.everyxday;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.*;
import android.util.Log;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xinxin.everyxday.dao.newdao.DaoMaster;
import com.xinxin.everyxday.dao.newdao.DaoSession;
import com.xinxin.everyxday.global.Globe;
import com.xinxin.everyxday.util.LocalStorageUtil;
import com.xinxin.everyxday.util.ProjectSettingInfoPreUtil;

import java.io.File;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class EveryXDayApplication extends Application{

    private static EveryXDayApplication instance;

    private static DaoMaster daoMaster;

    private static DaoSession daoSession;

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
        if(instance == null) {
            instance = this;
        }

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/FZLanTingHeiS-L-GB-Regular.TTF")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

        //存储application刚启动状态
        ProjectSettingInfoPreUtil psip = ProjectSettingInfoPreUtil.getInstance();
        psip.addSpApplicationIsFirstStart(true);

        mLocalStorageUtil = new LocalStorageUtil();
        mLocalStorageUtil.initLocalDir(this);

        initImageLoader(getApplicationContext());

        startXiaoMiPush();
    }

    /**
     * 取得DaoMaster
     *
     * @param context
     * @return
     */
    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, Globe.DB_NAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 取得DaoSession
     *
     * @param context
     * @return
     */
    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
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
    private static final String TAG = "com.xinxin.everyxday";

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
