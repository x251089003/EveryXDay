package com.xinxin.everyxday.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class LocalStorageUtil {

    // SD卡文件根目录
    private static String BASE_DIR = "EveryXDay";

    // 缓存目录
    private static final String FILE_LOG_REPORT_DIR = "killLogReport";
    private static final String FILE_PAY_NEED_REPORT_DIR = "payNeedReport";
    private static final String FILE_CACHE_DIR = "caches";
    private static final String IMAGE_CACHE_DIR = "temp";

    // sd卡根目录
    private String sdcardCacheBaseAbsolutePath;

    private String imageCacheAbsoluteDir;// 图片缓存目录
    private String fileCacheAbsoluteDir;// 其他文件缓存目录
    private String payReportNeedCacheAbsoluteDir;// 购买结果等待上传目录
    private String killLogCacheAbsoluteDir;// 秒杀日志缓存目录

    // 照片上传临时路径
    private String uploadUserPhotoTempFilePath;// 未处理的jpg
    private String uploadUserPhotoHandledFilePath;// 已处理待上传的jpg

    public String getSdcardCacheBaseAbsolutePath() {
        return sdcardCacheBaseAbsolutePath;
    }

    public void setSdcardCacheBaseAbsolutePath(String sdcardCacheBaseAbsolutePath) {
        this.sdcardCacheBaseAbsolutePath = sdcardCacheBaseAbsolutePath;
    }

    public String getImageCacheAbsoluteDir() {
        return imageCacheAbsoluteDir;
    }

    public void setImageCacheAbsoluteDir(String imageCacheAbsoluteDir) {
        this.imageCacheAbsoluteDir = imageCacheAbsoluteDir;
    }

    public String getFileCacheAbsoluteDir() {
        return fileCacheAbsoluteDir;
    }

    public void setFileCacheAbsoluteDir(String fileCacheAbsoluteDir) {
        this.fileCacheAbsoluteDir = fileCacheAbsoluteDir;
    }

    public String getPayReportNeedCacheAbsoluteDir() {
        return payReportNeedCacheAbsoluteDir;
    }

    public void setPayReportNeedCacheAbsoluteDir(
            String payReportNeedCacheAbsoluteDir) {
        this.payReportNeedCacheAbsoluteDir = payReportNeedCacheAbsoluteDir;
    }

    public String getKillLogCacheAbsoluteDir() {
        return killLogCacheAbsoluteDir;
    }

    public void setKillLogCacheAbsoluteDir(String killLogCacheAbsoluteDir) {
        this.killLogCacheAbsoluteDir = killLogCacheAbsoluteDir;
    }

    public String getUploadUserPhotoTempFilePath() {
        return uploadUserPhotoTempFilePath;
    }

    public void setUploadUserPhotoTempFilePath(
            String uploadUserPhotoTempFilePath) {
        this.uploadUserPhotoTempFilePath = uploadUserPhotoTempFilePath;
    }

    public String getUploadUserPhotoHandledFilePath() {
        return uploadUserPhotoHandledFilePath;
    }

    public void setUploadUserPhotoHandledFilePath(
            String uploadUserPhotoHandledFilePath) {
        this.uploadUserPhotoHandledFilePath = uploadUserPhotoHandledFilePath;
    }

    public String getAppDir(){
        return Environment.getExternalStorageDirectory() + File.separator + BASE_DIR;
    }

    public String getImageCacheDir(){
        return Environment.getExternalStorageDirectory() + File.separator + BASE_DIR + File.separator + IMAGE_CACHE_DIR;
    }

    public void initLocalDir(Context context) {

        long availableSDCardSpace = DeviceInfoUtil.getExternalStorageSpace();// 获取SD卡可用空间

        String sdcardBasePath;

        if (availableSDCardSpace != -1L) {// 如果存在SD卡
            sdcardBasePath = Environment.getExternalStorageDirectory() + File.separator + BASE_DIR;
        } else if (DeviceInfoUtil.getInternalStorageSpace() != -1L) {
            sdcardBasePath = context.getFilesDir().getPath() + File.separator + BASE_DIR;
        } else {// sd卡不存在
            // 没有可写入位置
            return;
        }

        setSdcardCacheBaseAbsolutePath(sdcardBasePath);

        // 图片缓存目录
        setImageCacheAbsoluteDir(getSdcardCacheBaseAbsolutePath() + File.separator + IMAGE_CACHE_DIR);

        // 其他文件缓存目录
        String photoUploadCacheDire = getSdcardCacheBaseAbsolutePath() + File.separator + FILE_CACHE_DIR;

        setFileCacheAbsoluteDir(photoUploadCacheDire);

        // 购买结果等待上传目录
        String payNeedReportCacheDire = getSdcardCacheBaseAbsolutePath() + File.separator + FILE_PAY_NEED_REPORT_DIR;

        setPayReportNeedCacheAbsoluteDir(payNeedReportCacheDire);

        // kill log上传目录
        String killLogCacheDire = getSdcardCacheBaseAbsolutePath() + File.separator + FILE_LOG_REPORT_DIR;

        setKillLogCacheAbsoluteDir(killLogCacheDire);

        // 初始化根目录
        File basePath = new File(getSdcardCacheBaseAbsolutePath());
        if (!basePath.exists()) {
            basePath.mkdir();
        }

        // 初始化照片上传缓存目录
        File cachePath = new File(photoUploadCacheDire);
        if (!cachePath.exists()) {
            cachePath.mkdir();
        }

        //购买结果等待上传目录
        File payNeedReportCachePath = new File(payNeedReportCacheDire);
        if (!payNeedReportCachePath.exists()) {
            payNeedReportCachePath.mkdir();
        }

        File killLogCachePath = new File(killLogCacheDire);
        if (!killLogCachePath.exists()) {
            killLogCachePath.mkdir();
        }

        setUploadUserPhotoHandledFilePath(photoUploadCacheDire + File.separator + "handled.jpg");
        setUploadUserPhotoTempFilePath(photoUploadCacheDire + File.separator + "unhandled.jpg");

    }
}
