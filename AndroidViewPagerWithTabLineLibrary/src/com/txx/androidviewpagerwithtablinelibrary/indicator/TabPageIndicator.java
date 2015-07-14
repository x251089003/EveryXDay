package com.txx.androidviewpagerwithtablinelibrary.indicator;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.txx.androidviewpagerwithtablinelibrary.R;
import com.txx.androidviewpagerwithtablinelibrary.util.TabTitleScreenInfoUtil;

public class TabPageIndicator extends HorizontalScrollView implements PageIndicator {
	
    private static final CharSequence EMPTY_TITLE = "默认";

    private final OnClickListener mTabClickListener = new OnClickListener() {
        public void onClick(View view) {
        	TextView tabView = (TextView)view;
            final int newSelected = (Integer)(tabView.getTag());//getIndex();
            mViewPager.setCurrentItem(newSelected, false);//设置显示页不带滑动
            
            //由于不带滑动,所以必须手动调用滑动监听.
            onPageScrolled(newSelected, 0, 0);
            onPageScrollStateChanged(ViewPager.SCROLL_STATE_IDLE);
        }
    };

    
    private final FrameLayout contentLayout;
    private final LinearLayout mTabLayout;

    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mListener;

    private int tabTitleHorizontalPadding = 0; //tabView左右padding距离
    private int tabTitleVerticalMaxPadding = 0; //tabView上下padding最大距离
    private int leftStandardTabWidth;//向前滑动时，预留标题的1/4宽度可见
    
    //每个页的滑动长度可以用map存上

    private final UnderlinePageIndicator lineIndicator;
    
    private int screenWidth;
    private LayoutInflater inflater;
    
    public TabPageIndicator(Context context) {
        this(context, null);
    }

    public TabPageIndicator(Context context, AttributeSet attrs) {
    	
        super(context, attrs);
        
        inflater = LayoutInflater.from(context);
        
        setHorizontalScrollBarEnabled(false);
        
        initScreenSizeInfo(context);
        
        contentLayout = new FrameLayout(context);
        addView(contentLayout, new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT));

        mTabLayout = new LinearLayout(context);
        contentLayout.addView(mTabLayout, new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT));
        
        lineIndicator = new UnderlinePageIndicator(context);
        
        FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.view_pager_with_tab_line_title_line_height));
        flp.gravity = Gravity.BOTTOM;
        contentLayout.addView(lineIndicator, flp);
        
    }
    
    private void initScreenSizeInfo(Context context){
    	screenWidth = TabTitleScreenInfoUtil.getScreenWidth(context);
    	tabTitleVerticalMaxPadding = getResources().getDimensionPixelSize(R.dimen.view_pager_with_tab_line_title_vertical_max_padding);
    }
    
    private int notFullScreenWidth;
    
    public void setNotFullScreenWidth(int notFullScreenWidth) {
		this.notFullScreenWidth = notFullScreenWidth;
	}

	private int getScreenWidth(){
		
		if(notFullScreenWidth != 0){
			return notFullScreenWidth;
		}
    	return screenWidth;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    	
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final boolean lockedExpanded = widthMode == MeasureSpec.EXACTLY;
        setFillViewport(lockedExpanded);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    
    private int currentPosition;//当前viewpager所在page index
    
    @Override
    public void onPageSelected(int position) {
        currentPosition = position;//标识前进后退
        setTabSelected(currentPosition);
        if (mListener != null) {
            mListener.onPageSelected(position);
        }
    }
    
    @Override
    public void onPageScrollStateChanged(int state) {
        if (mListener != null) {
            mListener.onPageScrollStateChanged(state);
        }
    }
    
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    	
//    	System.out.println("\r\n onPageScrolled : " + positionOffset + " position : " + position + " currentPosition : " + currentPosition);
    	
    	int leftBaseDistance = 0;
    	
    	if(position <= currentPosition){ //前进
    		
    		if(position > 0){
    			leftBaseDistance = getTabBaseHoldOnDistance(position);
    		}
    		
    		tabSmoothScrollTo(leftBaseDistance, position, positionOffset);
    		lineSmoothScrollTo(position, positionOffset, true);
    		
    	}else{ //后退
    		
    		if(position > 0){
    			
    			if(position > 1){
    				leftBaseDistance = getTabBaseHoldOnDistance(position);
    			}
    			
    			tabSmoothScrollTo(leftBaseDistance, position, positionOffset);
    			lineSmoothScrollTo(position, positionOffset, false);
    		}
    	}
    	
        if (mListener != null) {
            mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }
    
    //title滑动至目标点
    private void tabSmoothScrollTo(int leftBaseDistance, int position, float positionOffset){
    	
    	int currentScrollBaseDistance = getTabNeedScrollDistance(position);
    	int smoothScrollTo = leftBaseDistance + (int)(currentScrollBaseDistance * positionOffset);
		smoothScrollTo(smoothScrollTo, 0);
    }
    
    //line滑动至目标点
    private void lineSmoothScrollTo(int position, float positionOffset, boolean isAdvance){
    	
    	float realLineWidth = getScrollLineWidth(position, positionOffset, isAdvance);
		lineIndicator.updateLeftScrollDistance(getLineBaseHoldOnDistance(position) + (getLineNeedScrollDistance(position) + getTabNeedScrollDistance(position)) * positionOffset, realLineWidth);// + getTabNeedScrollDistance(position) new
    }
    
    //获取滑动前title左边初始距离
    private int getTabBaseHoldOnDistance(int position){
    	
    	int leftBaseHoldOnDistance = 0;
		
    	for (int i = 0; i < position; i++) {
			leftBaseHoldOnDistance += getTabNeedScrollDistance(i);
		}
    	
		return leftBaseHoldOnDistance;
    }
    
    //获取title本次需要滑动距离
    private int getTabNeedScrollDistance(int position){
    	
    	int tabCanScrollDistance = (mTabLayout.getWidth() - getScreenWidth());
    	
    	int tabNeedScrollDistance = 0;
    	int realTabScrollDistance = 0;
    	
    	if(tabCanScrollDistance > 0){
    		
    		for (int i = 0; i <= position; i++) {
    			
    			if(i == 0){
    				
    				tabNeedScrollDistance += getTabFirstScrollDistance();
        			if(tabNeedScrollDistance > tabCanScrollDistance){
        				if(i == position){
        					realTabScrollDistance = tabCanScrollDistance;
        				}else{
        					realTabScrollDistance = 0;
        				}
        				break;
        			}else{
        				realTabScrollDistance = tabNeedScrollDistance;
        			}
        			
    			}else if( i > 0 && i < position){ //没有走到position 直接返回0
    				
    				tabNeedScrollDistance += getScrollStanderdDistance(i);
	    			if(tabNeedScrollDistance > tabCanScrollDistance){
	    				realTabScrollDistance = 0;
	    				break;
	    			}
	    			
    			}else if(i == position){
    				
    				if((tabCanScrollDistance - tabNeedScrollDistance) > getScrollStanderdDistance(i)){
    					realTabScrollDistance = getScrollStanderdDistance(i);
    				}else{
    					realTabScrollDistance = (tabCanScrollDistance - tabNeedScrollDistance);
    				}
    				
    			}
    		}
    		
    	}
    	
    	return realTabScrollDistance;
    }
    
    //获取滑动前line左边初始距离
    private int getLineBaseHoldOnDistance(int position){
    	
    	int leftBaseHoldOnDistance = 0;
    	
    	for (int i = 0; i < position; i++) {
			leftBaseHoldOnDistance += getLineNeedScrollDistance(i);
			leftBaseHoldOnDistance += getTabNeedScrollDistance(i); //new
		}
    	
    	return leftBaseHoldOnDistance;
    }
    
    //获取line本次需要滑动距离 = (tab和line移动总距离 - tab移动距离)
    private int getLineNeedScrollDistance(int position){
		return getScrollStanderdDistance(position) - getTabNeedScrollDistance(position);
    }
    
    //获取line滑动时真实宽度
    private float getScrollLineWidth(int position, float positionOffset, boolean isAdvance){
    	
    	int curTabWidth = getTabLayoutChild(position).getWidth();
		int differDistance = 0;
		
		if(position == currentPosition && positionOffset == 0){
			differDistance = curTabWidth;
		}else{
			
			int diffPosition = position + 1;
			if(!isAdvance){
				diffPosition = position - 1;
			}
			
    		int diffTabWidth = getTabLayoutChild(diffPosition).getWidth();
    		differDistance = diffTabWidth - curTabWidth;
		}
		
		return getScrollStanderdDistance(position) + differDistance * positionOffset;
    }
    
    //tab第一次移动距离比较特殊
    private int getTabFirstScrollDistance(){
    	return getTabLayoutChild(0).getWidth() + tabTitleHorizontalPadding  - leftStandardTabWidth;
    }
    
    //获取tab和line每次总共移动的标准距离 (tab和line的偏移差值)
    private int getScrollStanderdDistance(int position){
    	return getTabLayoutChild(position).getWidth() + 2 * tabTitleHorizontalPadding;
    }
    
    private View getTabLayoutChild(int position){
    	return mTabLayout.getChildAt(position);
    }
    
    
    //--------------------------------初始化viewpager 和 顶部滑动title部分
    
    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        mListener = listener;
    }

    @Override
    public void setViewPager(ViewPager view) { //, UnderlinePageIndicator indicator
    	
        if (mViewPager == view) {
            return;
        }
        
        if (mViewPager != null) {
            mViewPager.setOnPageChangeListener(null);
        }
        
        final PagerAdapter adapter = view.getAdapter();
        if (adapter == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        
        mViewPager = view;
        
        view.setOnPageChangeListener(this);
        
//        lineIndicator = indicator;
        lineIndicator.setLineScrollSegments(mViewPager.getAdapter().getCount());//lineIndicator 设置最大分割段数
        
        initHorizontalScrollTitle();
        
        setTabSelected(currentPosition);
    }

    public void initHorizontalScrollTitle() {
    	
        mTabLayout.removeAllViews();
        
        PagerAdapter adapter = mViewPager.getAdapter();
        
        final int count = adapter.getCount();
        
        for (int i = 0; i < count; i++) {
            CharSequence title = adapter.getPageTitle(i);
            title = (title == null ? EMPTY_TITLE : title);
            addTab(i, title);
        }
        
        requestLayout();
    }

    private void addTab(int index, CharSequence text) {
    	
    	TextView tabView = createTabView(index, text);

        if(index == 0){
        	calculatePadding(tabView);
        }
        
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(WRAP_CONTENT, MATCH_PARENT);
        lp.setMargins(tabTitleHorizontalPadding, tabTitleVerticalMaxPadding, tabTitleHorizontalPadding, tabTitleVerticalMaxPadding);
        mTabLayout.addView(tabView, lp);
    }
    
    public void setTabSelected(int index) {
        final int tabCount = mTabLayout.getChildCount();
        for (int i = 0; i < tabCount; i++) {
            final View child = mTabLayout.getChildAt(i);
            final boolean isSelected = (i == index);
            child.setSelected(isSelected);
        }
    }
    
    private TextView createTabView(int index, CharSequence text){
    	
		 TextView tabView = (TextView) inflater.inflate(R.layout.view_pager_tab_title_view, null);
         tabView.setTag(index);
         tabView.setFocusable(true);
         tabView.setOnClickListener(mTabClickListener);
         tabView.setText(text);
         
         return tabView;
    }
    
    private void calculatePadding(TextView firstTabView){
    	
    	PagerAdapter adapter = mViewPager.getAdapter();
    	int firstTabTitleLength = adapter.getPageTitle(0).length();
    	
    	firstTabView.measure(0, 0);
    	
    	int firstTabWidth  = firstTabView.getMeasuredWidth();
    	
    	int starderdOneWordWidth = firstTabWidth / firstTabTitleLength; //一个字标准宽度
    	leftStandardTabWidth = starderdOneWordWidth / 2;
    	
    	setTabTitleVerticalMaxPadding(starderdOneWordWidth);
    	
    	final int titleCount = adapter.getCount();
    	int allTitleLength = 0;
        
        for (int i = 0; i < titleCount; i++) {
            CharSequence title = adapter.getPageTitle(i);
            title = (title == null ? EMPTY_TITLE : title);
            allTitleLength += title.length();
        }
        
        if(titleCount <= 5){//每屏最多容纳5个title(且所有title文字总长度+左右padding总长度<= screenWidth)
        	
        	int allTitleLen = starderdOneWordWidth * allTitleLength; //所有title的文字总长度
            int titleHorizontalMinPadding = (int)(starderdOneWordWidth * 1.5); //title左右padding最小长度
            
            int paddingLen = titleHorizontalMinPadding * 2 * titleCount;//所有title左右padding总长度
            
            if((titleHorizontalMinPadding + paddingLen) <= getScreenWidth()){ //文字总长度+padding总长度 <= 屏幕宽度时
            	tabTitleHorizontalPadding = ((getScreenWidth() - allTitleLen)/titleCount)/2;
            	return;
            }
        }
        
        int starderdTabWidth = starderdOneWordWidth * 2; //两个字标准宽度
        tabTitleHorizontalPadding = ((getScreenWidth() - lineIndicator.getLineMaxScrollSegments()*starderdTabWidth)/lineIndicator.getLineMaxScrollSegments())/2;
    }
    
    /**
     * 设置title top&bottom padding
     * @param starderdOneWordWidth
     */
    private void setTabTitleVerticalMaxPadding(int starderdOneWordWidth){
//    	System.out.println("\r\n tabTitleVerticalMaxPadding : " + tabTitleVerticalMaxPadding + " starderdOneWordWidth : " + starderdOneWordWidth);
    	if(starderdOneWordWidth <= tabTitleVerticalMaxPadding){
    		tabTitleVerticalMaxPadding = starderdOneWordWidth;
    	}
    }

}
