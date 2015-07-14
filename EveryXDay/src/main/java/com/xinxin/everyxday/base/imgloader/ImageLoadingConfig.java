package com.xinxin.everyxday.base.imgloader;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.xinxin.everyxday.widget.AnimatioinAndCornerBitmapDisplayer;

public class ImageLoadingConfig {
	
	// 列表图片加载option没有动画
	public static DisplayImageOptions generateDisplayImageOptions(int drawableId) {
		DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
				.showImageOnLoading(drawableId).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565).build();

		return displayImageOptions;
	}

	// 列表图片加载option有动画并且带圆角
	public static DisplayImageOptions generateDisplayImageOptionsWithAnimation(int drawableId) {
		DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
				.showImageOnLoading(drawableId).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new AnimatioinAndCornerBitmapDisplayer(10)).build();

		return displayImageOptions;
	}
	
	// 列表图片加载option有动画不带圆角
	public static DisplayImageOptions generateDisplayImageOptionsWithAnimationAndNoCorner(int drawableId) {
		DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
				.showImageOnLoading(drawableId).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new FadeInBitmapDisplayer(500)).build();

		return displayImageOptions;
	}

	// 大图加载option
	public static DisplayImageOptions generateDisplayImageOptions() {
		DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
				.resetViewBeforeLoading(true).cacheOnDisk(true)
				.cacheInMemory(true).imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
				.build();

		return displayImageOptions;

	}

	// 图片上传部分 图片加载option
	public static DisplayImageOptions generateDisplayImageOptionsNoCatchDisc() {
		DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
				.cacheInMemory(false).cacheOnDisk(false)
				.considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565).build();

		return displayImageOptions;
	}
	
	public static DisplayImageOptions generateDisplayImageOptionsWithDefaultImg(
			int drawableId) {
		DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
				.showImageOnLoading(drawableId).resetViewBeforeLoading(true)
				.cacheOnDisk(true).cacheInMemory(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
				.build();

		return displayImageOptions;

	}
	
	public static DisplayImageOptions generateDisplayImageOptionsWithNotClear() {
		DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
				.resetViewBeforeLoading(false).cacheOnDisk(true)
				.cacheInMemory(true).imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
				.build();

		return displayImageOptions;

	}
	
	//启动图加载option
	public static DisplayImageOptions generateDisplayStartupImageOptions() {
		DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
				.resetViewBeforeLoading(true).cacheOnDisk(false)
				.cacheInMemory(true).imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
				.build();

		return displayImageOptions;

	}
}
