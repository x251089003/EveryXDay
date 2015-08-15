package com.xinxin.everyxday.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.nispok.snackbar.Snackbar;
import com.xinxin.everyxday.fragment.FragmentAbout;
import com.xinxin.everyxday.fragment.FragmentLike;
import com.xinxin.everyxday.fragment.FragmentSetting;
import com.xinxin.everyxday.fragment.FragmentShareProduct;
import com.xinxin.everyxday.fragment.FragmentShowOrderFeaturedContent;
import com.xinxin.everyxday.fragment.FragmentSortContent;
import com.xinxin.everyxday.fragment.FragmentSupportUs;
import com.xinxin.everyxday.util.ResultInterface;
import com.xinxin.everyxday.widget.GlobalMenuAdapter;
import com.xinxin.ldrawer.ActionBarDrawerToggle;
import com.xinxin.ldrawer.DrawerArrowDrawable;

import com.xinxin.everyxday.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

//TODO 需要封装个BaseFragment
//TODO Fragment的replace和add

public class EveryXDayMainActivity extends Activity{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerArrowDrawable drawerArrow;
    private ListView menuView;
    GlobalMenuAdapter globalMenuAdapter;

    private LinearLayout menuLayout;

    private FragmentShowOrderFeaturedContent showFragment;

    private int meuP = 0;
    ActionBar ab;
    private FragmentManager fragmentManager;

    private Fragment mContent;

    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        ab = getActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeButtonEnabled(true);

        fragmentManager = getFragmentManager();
        menuLayout = (LinearLayout)findViewById(R.id.navdrawer);
        menuView = (ListView)findViewById(R.id.menuview);
        globalMenuAdapter = new GlobalMenuAdapter(this,listener);
        menuView.setAdapter(globalMenuAdapter);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setScrimColor(getResources().getColor(R.color.whitetransparent));

        drawerArrow = new DrawerArrowDrawable(this) {
            @Override
            public boolean isLayoutRtl() {
                return false;
            }
        };
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
            drawerArrow, R.string.drawer_open,
            R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
                switch (meuP){
                    case 0:
                        showFragment = new FragmentShowOrderFeaturedContent();
                        fragmentManager.beginTransaction().replace(R.id.content_frame, showFragment).commit();
                        break;
                    case 1:
                        FragmentSortContent sortFragment = new FragmentSortContent();
                        fragmentManager.beginTransaction().replace(R.id.content_frame, sortFragment).commit();
                        break;
                    case 2:
                        FragmentLike likeFragment = new FragmentLike();
                        fragmentManager.beginTransaction().replace(R.id.content_frame, likeFragment).commit();
                        break;
                    case 4:
                        FragmentShareProduct shareFragment = new FragmentShareProduct();
                        fragmentManager.beginTransaction().replace(R.id.content_frame, shareFragment).commit();
                        break;
                    case 5:
                        FragmentSupportUs supportFragment = new FragmentSupportUs();
                        fragmentManager.beginTransaction().replace(R.id.content_frame, supportFragment).commit();
                        break;
                    case 7:
                        FragmentSetting settingFragment = new FragmentSetting();
                        fragmentManager.beginTransaction().replace(R.id.content_frame, settingFragment).commit();
                        break;
                    case 8:
                        FragmentAbout aboutFragment = new FragmentAbout();
                        fragmentManager.beginTransaction().replace(R.id.content_frame, aboutFragment).commit();
                        break;
                    default:
                        break;
                }
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        initShowOrderFragment();

    }

    private void initShowOrderFragment() {
        if(showFragment == null) {
            showFragment = new FragmentShowOrderFeaturedContent();
        }
        fragmentManager.beginTransaction().replace(R.id.content_frame, showFragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(menuLayout)) {
                mDrawerLayout.closeDrawer(menuLayout);
            } else {
                mDrawerLayout.openDrawer(menuLayout);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Snackbar.with(getApplicationContext()) // context
                        .colorResource(R.color.app_main_theme_color_transparent)
                        .duration(Snackbar.SnackbarDuration.LENGTH_SHORT)
                        .text("再按一次退出程序") // text to display
                        .show(this);
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    ResultInterface listener = new ResultInterface() {
        @Override
        public void OnComplete(int state, int position) {
            if(state == 1){
                switch (position){
                    case 0:
                        ab.setTitle("NEW");
                        meuP = 0;
                        mDrawerLayout.closeDrawers();
                        menuView.setItemChecked(position, true);
                        break;
                    case 1:
                        ab.setTitle("分类");
                        meuP = 1;
                        mDrawerLayout.closeDrawers();
                        menuView.setItemChecked(position, true);
                        break;
                    case 2:
                        ab.setTitle("喜欢过的");
                        meuP = 2;
                        mDrawerLayout.closeDrawers();
                        menuView.setItemChecked(position, true);
                        break;
                    case 4:
                        ab.setTitle("分享新品");
                        meuP = 4;
                        menuView.setItemChecked(position, true);
                        mDrawerLayout.closeDrawers();
                        break;
                    case 5:
                        ab.setTitle("支持NEW");
                        meuP = 5;
                        menuView.setItemChecked(position, true);
                        mDrawerLayout.closeDrawers();
                        break;
                    case 7:
                        ab.setTitle("设置");
                        meuP = 7;
                        menuView.setItemChecked(position, true);
                        mDrawerLayout.closeDrawers();
                        break;
                    case 8:
                        ab.setTitle("关于");
                        meuP = 8;
                        menuView.setItemChecked(position, true);
                        mDrawerLayout.closeDrawers();
                        break;
                    default:
                        break;
                }
            }
        }
    };

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
