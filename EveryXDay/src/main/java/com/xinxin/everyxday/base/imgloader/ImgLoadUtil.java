/******************************************************************************
 * PROPRIETARY/CONFIDENTIAL 
 * Copyright (c) 2015 XianTao Technology Co.,Ltd
 * 
 * All rights reserved. This medium contains confidential and proprietary 
 * source code and other information which is the exclusive property of 
 * XianTao Technology Co.,Ltd. None of these materials may be used, 
 * disclosed, transcribed, stored in a retrieval system, translated into any 
 * other language or other computer language, or transmitted in any 
 * form or by any means (electronic, mechanical, photocopied, recorded 
 * or otherwise) without the prior written permission of XianTao Technology 
 * Co.,Ltd. 
 *******************************************************************************/
package com.xinxin.everyxday.base.imgloader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.txx.miaosha.R;

/**
 * 图片加载工具类，封装好加载模板直接给外部调用(方法配置好DisplayImageOptions)
 */
public class ImgLoadUtil {

	private static final DisplayImageOptions commonDisplayOptions = ImageLoadingConfig.generateDisplayImageOptions(R.drawable.common_default_loading_bg);
	private static final DisplayImageOptions commonDisplayOptionsWithAnimation = ImageLoadingConfig.generateDisplayImageOptionsWithAnimation(R.drawable.common_default_loading_bg);
	private static final DisplayImageOptions commonDisplayOptionsWithAnimationAndNoCorner = ImageLoadingConfig.generateDisplayImageOptionsWithAnimationAndNoCorner(R.drawable.common_default_loading_bg);
	
	public static void displayImage(String imgUrl, ImageView imgView){
		ImageLoader.getInstance().displayImage(imgUrl, imgView, commonDisplayOptions);
	}
	
	public static void displayImageWithAnimation(String imgUrl, ImageView imgView){
		ImageLoader.getInstance().displayImage(imgUrl, imgView, commonDisplayOptionsWithAnimation);
	}
	
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
