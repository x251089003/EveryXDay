package com.txx.androidviewpagerwithtablinelibrary.indicator;

import android.support.v4.view.ViewPager;

public interface PageIndicator extends ViewPager.OnPageChangeListener {

	void setViewPager(ViewPager view);//, UnderlinePageIndicator lineIndicator

	void setOnPageChangeListener(ViewPager.OnPageChangeListener listener);
}
