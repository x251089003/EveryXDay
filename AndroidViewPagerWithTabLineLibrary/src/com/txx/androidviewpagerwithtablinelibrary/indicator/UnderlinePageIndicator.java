package com.txx.androidviewpagerwithtablinelibrary.indicator;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.txx.androidviewpagerwithtablinelibrary.R;
import com.txx.androidviewpagerwithtablinelibrary.util.TabTitleScreenInfoUtil;

public class UnderlinePageIndicator extends View{

    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int maxScrollSegments = 5; //将整个屏幕切成5段
    private int screenWidth;
    
    public UnderlinePageIndicator(Context context) {
        this(context, null);
    }

    public UnderlinePageIndicator(Context context, AttributeSet attrs) {
    	
        super(context, attrs);
        
        if (isInEditMode()) return;

        final Resources res = getResources();

        //Load defaults from resources
        final int defaultSelectedColor = res.getColor(R.color.view_pager_with_tab_line_under_line_color);
        
        setSelectedColor(defaultSelectedColor);
        
        initScreenSizeInfo(context);
    }

    private void initScreenSizeInfo(Context context){
    	screenWidth = TabTitleScreenInfoUtil.getScreenWidth(context);
    }

    public int getSelectedColor() {
        return mPaint.getColor();
    }

    public void setSelectedColor(int selectedColor) {
        mPaint.setColor(selectedColor);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final int paddingLeft = getPaddingLeft();
        final int paddingRight = getPaddingRight();
        
//        final float pageWidth = (getWidth() - paddingLeft - paddingRight) / (1f * maxScrollSegments);
        
        final float left = paddingLeft + leftScrollDistance; //paddingLeft + pageWidth * (mCurrentPage + mPositionOffset);
        final float right = left + pageWidth + paddingRight;
        final float top = getPaddingTop();
        final float bottom = getHeight() - getPaddingBottom();
        
        canvas.drawRect(left, top, right, bottom, mPaint);
    }
    
    public int getLineMaxScrollSegments(){
    	return maxScrollSegments;
    }
    
    public int getStandardLineSegmentWidth(){
    	return screenWidth / maxScrollSegments;
    }
    
    public void setLineScrollSegments(int pageCount){
    	if(pageCount < maxScrollSegments){
    		maxScrollSegments = pageCount;
    	}
    	pageWidth = screenWidth / maxScrollSegments;
    }
    
    private float leftScrollDistance;//移动距离
    private float pageWidth ;//line宽度根据title宽度自动变化
    
    public void updateLeftScrollDistance(float leftScrollDistance, float pageWidth){
    	this.leftScrollDistance = leftScrollDistance;
    	this.pageWidth = pageWidth;
    	
//    	System.out.println("\r\n leftScrollDistance : " + leftScrollDistance + " pageWidth : " + pageWidth);
    	
    	invalidate();
    }

}