package com.xinxin.everyxday.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.nispok.snackbar.Snackbar;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.update.UmengUpdateAgent;
import com.xinxin.everyxday.fragment.FragmentAbout;
import com.xinxin.everyxday.fragment.FragmentLike;
import com.xinxin.everyxday.fragment.FragmentSetting;
import com.xinxin.everyxday.fragment.FragmentShareProduct;
import com.xinxin.everyxday.fragment.FragmentShowOrderFeaturedContent;
import com.xinxin.everyxday.fragment.FragmentSortContent;
import com.xinxin.everyxday.fragment.FragmentSupportUs;
import com.xinxin.everyxday.global.Globe;
import com.xinxin.everyxday.util.ResultInterface;
import com.xinxin.everyxday.widget.GlobalMenuAdapter;
import com.xinxin.ldrawer.ActionBarDrawerToggle;
import com.xinxin.ldrawer.DrawerArrowDrawable;

import com.xinxin.everyxday.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EveryXDayMainActivity extends Activity{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerArrowDrawable drawerArrow;
    private ListView menuView;
    private GlobalMenuAdapter globalMenuAdapter;

    private LinearLayout menuLayout;

    private FragmentShowOrderFeaturedContent showFragment;
    private FragmentSortContent sortFragment;
    private FragmentLike likeFragment;
    private FragmentShareProduct shareFragment;
    private FragmentSupportUs supportFragment;
    private FragmentSetting settingFragment;
    private FragmentAbout aboutFragment;

    private int meuP = 0;
    private ActionBar ab;
    private FragmentManager fragmentManager;

    private long mExitTime;

    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        fragmentManager = getFragmentManager();
        regToWx();
        initActionBar();
        initUpdate();
        initViews();
        initViewEvents();

        showFragment(1);

    }

    private void regToWx(){
        api = WXAPIFactory.createWXAPI(this, Globe.WX_APP_ID, true);
        api.registerApp(Globe.WX_APP_ID);
    }

    private void initActionBar(){
        ab = getActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeButtonEnabled(true);
    }

    private void initUpdate(){
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.update(this);
    }

    private void initViews(){
        menuLayout = (LinearLayout)findViewById(R.id.navdrawer);
        menuView = (ListView)findViewById(R.id.menuview);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerArrow = new DrawerArrowDrawable(this) {
            @Override
            public boolean isLayoutRtl() {
                return false;
            }
        };
    }

    private void initViewEvents(){
        if(null == globalMenuAdapter) {
            globalMenuAdapter = new GlobalMenuAdapter(this, listener);
        }
        menuView.setAdapter(globalMenuAdapter);

        mDrawerLayout.setScrimColor(getResources().getColor(R.color.whitetransparent));

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                drawerArrow, R.string.drawer_open,
                R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
                switch (meuP){
                    case 0:
                        showFragment(1);
                        break;
                    case 1:
                        showFragment(2);
                        break;
                    case 2:
                        showFragment(3);
                        break;
                    case 4:
                        showFragment(4);
                        break;
                    case 5:
                        showFragment(5);
                        break;
                    case 7:
                        showFragment(6);
                        break;
                    case 8:
                        showFragment(7);
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

            if (mDrawerLayout.isDrawerOpen(menuLayout)) {
                mDrawerLayout.closeDrawer(menuLayout);
            }else {

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

    /**
     * 展现Fragment
     * @param index
     */
    public void showFragment(int index) {
        FragmentTransaction ft = fragmentManager.beginTransaction();

        // 想要显示一个fragment,先隐藏所有fragment，防止重叠
        hideFragments(ft);

        switch (index) {
            case 1:
                // 如果fragment1已经存在则将其显示出来
                if (showFragment != null)
                    ft.show(showFragment);
                    // 否则是第一次切换则添加fragment1，注意添加后是会显示出来的，replace方法也是先remove后add
                else {
                    showFragment = new FragmentShowOrderFeaturedContent();
                    ft.add(R.id.content_frame, showFragment);
                }
                break;
            case 2:
                if (sortFragment != null)
                    ft.show(sortFragment);
                else {
                    sortFragment = new FragmentSortContent();
                    ft.add(R.id.content_frame, sortFragment);
                }
                break;
            case 3:
                if (likeFragment != null)
                    ft.show(likeFragment);
                else {
                    likeFragment = new FragmentLike();
                    ft.add(R.id.content_frame, likeFragment);
                }
                break;
            case 4:
                if (shareFragment != null)
                    ft.show(shareFragment);
                else {
                    shareFragment = new FragmentShareProduct();
                    ft.add(R.id.content_frame, shareFragment);
                }
                break;
            case 5:
                if (supportFragment != null)
                    ft.show(supportFragment);
                else {
                    supportFragment = new FragmentSupportUs();
                    ft.add(R.id.content_frame, supportFragment);
                }
                break;
            case 6:
                if (settingFragment != null)
                    ft.show(settingFragment);
                else {
                    settingFragment = new FragmentSetting();
                    ft.add(R.id.content_frame, settingFragment);
                }
                break;
            case 7:
                if (aboutFragment != null)
                    ft.show(aboutFragment);
                else {
                    aboutFragment = new FragmentAbout();
                    ft.add(R.id.content_frame, aboutFragment);
                }
                break;
        }
        ft.commit();
    }

    // 当fragment已被实例化，就隐藏起来
    public void hideFragments(FragmentTransaction ft) {
        if (showFragment != null)
            ft.hide(showFragment);
        if (sortFragment != null)
            ft.hide(sortFragment);
        if (likeFragment != null)
            ft.hide(likeFragment);
        if (shareFragment != null)
            ft.hide(shareFragment);
        if (supportFragment != null)
            ft.hide(supportFragment);
        if (settingFragment != null)
            ft.hide(settingFragment);
        if (aboutFragment != null)
            ft.hide(aboutFragment);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //触发fm的
        if(settingFragment != null) {
            settingFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

}
