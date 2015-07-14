package com.xinxin.everyxday.widget;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

public class AnimatioinAndCornerBitmapDisplayer implements BitmapDisplayer {
	
	protected final int cornerRadius;
	protected final int margin;

	public AnimatioinAndCornerBitmapDisplayer(int cornerRadiusPixels) {
		this(cornerRadiusPixels, 0);
	}

	public AnimatioinAndCornerBitmapDisplayer(int cornerRadiusPixels, int marginPixels) {
		this.cornerRadius = cornerRadiusPixels;
		this.margin = marginPixels;
		
		durationMillis = 500;
		animateFromNetwork = true;
		animateFromDisk = true;
		animateFromMemory = true;
	}

	@Override
	public void display(Bitmap bitmap, ImageAware imageAware,
			LoadedFrom loadedFrom) {

		if (!(imageAware instanceof ImageViewAware)) {
			throw new IllegalArgumentException("ImageAware should wrap ImageView. ImageViewAware is expected.");
		}

		imageAware.setImageDrawable(new RoundedDrawable(bitmap, cornerRadius, margin));
		
		if ((animateFromNetwork && loadedFrom == LoadedFrom.NETWORK) ||
				(animateFromDisk && loadedFrom == LoadedFrom.DISC_CACHE) ||
				(animateFromMemory && loadedFrom == LoadedFrom.MEMORY_CACHE)) {
			animate(imageAware.getWrappedView(), durationMillis);
		}
	}
	
	
	private final int durationMillis;

	private final boolean animateFromNetwork;
	private final boolean animateFromDisk;
	private final boolean animateFromMemory;

	public static class RoundedDrawable extends Drawable {

		protected final float cornerRadius;
		protected final int margin;

		protected final RectF mRect = new RectF(),
				mBitmapRect;
		protected final BitmapShader bitmapShader;
		protected final Paint paint;

		public RoundedDrawable(Bitmap bitmap, int cornerRadius, int margin) {
			this.cornerRadius = cornerRadius;
			this.margin = margin;

			bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
			mBitmapRect = new RectF (margin, margin, bitmap.getWidth() - margin, bitmap.getHeight() - margin);
			
			paint = new Paint();
			paint.setAntiAlias(true);
			paint.setShader(bitmapShader);
		}

		@Override
		protected void onBoundsChange(Rect bounds) {
			super.onBoundsChange(bounds);
			mRect.set(margin, margin, bounds.width() - margin, bounds.height() - margin);
			
			// Resize the original bitmap to fit the new bound
			Matrix shaderMatrix = new Matrix();
			shaderMatrix.setRectToRect(mBitmapRect, mRect, Matrix.ScaleToFit.FILL);
			bitmapShader.setLocalMatrix(shaderMatrix);
			
		}

		@Override
		public void draw(Canvas canvas) {
			canvas.drawRoundRect(mRect, cornerRadius, cornerRadius, paint);
		}

		@Override
		public int getOpacity() {
			return PixelFormat.TRANSLUCENT;
		}

		@Override
		public void setAlpha(int alpha) {
			paint.setAlpha(alpha);
		}

		@Override
		public void setColorFilter(ColorFilter cf) {
			paint.setColorFilter(cf);
		}
	}
	
	//-----------动画
	
	/**
	 * Animates {@link ImageView} with "fade-in" effect
	 *
	 * @param imageView      {@link ImageView} which display image in
	 * @param durationMillis The length of the animation in milliseconds
	 */
	public static void animate(View imageView, int durationMillis) {
		if (imageView != null) {
			AlphaAnimation fadeImage = new AlphaAnimation(0, 1);
			fadeImage.setDuration(durationMillis);
			fadeImage.setInterpolator(new DecelerateInterpolator());
			imageView.startAnimation(fadeImage);
		}
	}

}
