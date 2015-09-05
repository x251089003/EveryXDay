package com.xinxin.everyxday.base.imgloader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;

import com.xinxin.everyxday.R;

/**
 * 图片加载工具类，封装好加载模板直接给外部调用(方法配置好DisplayImageOptions)
 */
public class ImgLoadUtil {

	private static final DisplayImageOptions commonDisplayOptions = ImageLoadingConfig.generateDisplayImageOptions(R.drawable.white_bg);
	private static final DisplayImageOptions commonDisplayOptionsWithAnimation = ImageLoadingConfig.generateDisplayImageOptionsWithAnimation(R.drawable.white_bg);
	private static final DisplayImageOptions commonDisplayOptionsWithAnimationAndNoCorner = ImageLoadingConfig.generateDisplayImageOptionsWithAnimationAndNoCorner(R.drawable.white_bg);

	public static void displayImage(String imgUrl, ImageView imgView){
		ImageLoader.getInstance().displayImage(imgUrl, imgView, commonDisplayOptions);
	}

//	public static void displayImageWithAnimation(String imgUrl, ImageView imgView){
//		ImageLoader.getInstance().displayImage(imgUrl, imgView, commonDisplayOptionsWithAnimation);
//	}

	public static void displayImageWithAnimationAndNoCorner(String imgUrl, ImageView imgView){
		ImageLoader.getInstance().displayImage(imgUrl, imgView, commonDisplayOptionsWithAnimationAndNoCorner);
	}

	public static Bitmap loadImageSync(String imgUrl){
		return ImageLoader.getInstance().loadImageSync(imgUrl, commonDisplayOptions);
	}

	public static Bitmap loadImageSync(String imgUrl, int width, int height){
		return ImageLoader.getInstance().loadImageSync(imgUrl, new ImageSize(width, height) ,commonDisplayOptions);
	}

	public static void displayStartUpImage(String imgUrl, ImageView imgView){
		ImageLoader.getInstance().displayImage(imgUrl, imgView, ImageLoadingConfig.generateDisplayStartupImageOptions());
	}
	
}
