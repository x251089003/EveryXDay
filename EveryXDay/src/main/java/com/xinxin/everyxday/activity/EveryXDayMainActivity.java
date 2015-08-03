package com.xinxin.everyxday.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


import com.nispok.snackbar.Snackbar;
import com.xinxin.everyxday.fragment.FragmentAbout;
import com.xinxin.everyxday.fragment.FragmentSetting;
import com.xinxin.everyxday.fragment.FragmentShareProduct;
import com.xinxin.everyxday.fragment.FragmentShowOrderFeaturedContent;
import com.xinxin.everyxday.fragment.FragmentSortContent;
import com.xinxin.everyxday.fragment.FragmentSupportUs;
import com.xinxin.everyxday.util.ResultInterface;
import com.xinxin.everyxday.widget.GlobalMenuAdapter;
import com.xinxin.everyxday.widget.GlobalMenuView;
import com.xinxin.ldrawer.ActionBarDrawerToggle;
import com.xinxin.ldrawer.DrawerArrowDrawable;

import java.util.Locale;

import com.xinxin.everyxday.R;

import butterknife.Bind;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EveryXDayMainActivity extends Activity{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerArrowDrawable drawerArrow;
    private boolean drawerArrowColor;
    private ListView menuView;
    GlobalMenuAdapter globalMenuAdapter;

    private LinearLayout menuLayout;

    private FragmentShowOrderFeaturedContent fragment;

    private int completeFlag = 0;
    ActionBar ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        ab = getActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeButtonEnabled(true);


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
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        initShowOrderFragment();


//        String[] values = new String[]{
//            "Stop Animation (Back icon)",
//            "Stop Animation (Home icon)",
//            "Start Animation",
//            "Change Color",
//            "GitHub Page",
//            "Share",
//            "Rate"
//        };
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//            android.R.layout.simple_list_item_1, android.R.id.text1, values);
//        mDrawerList.setAdapter(adapter);
//        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                switch (position) {
//                    case 0:
//                        mDrawerToggle.setAnimateEnabled(false);
//                        drawerArrow.setProgress(1f);
//                        break;
//                    case 1:
//                        mDrawerToggle.setAnimateEnabled(false);
//                        drawerArrow.setProgress(0f);
//                        break;
//                    case 2:
//                        mDrawerToggle.setAnimateEnabled(true);
//                        mDrawerToggle.syncState();
//                        break;
//                    case 3:
//                        if (drawerArrowColor) {
//                            drawerArrowColor = false;
//                            drawerArrow.setColor(getResources().getColor(R.color.ldrawer_color));
//                        } else {
//                            drawerArrowColor = true;
//                            drawerArrow.setColor(getResources().getColor(R.color.drawer_arrow_second_color));
//                        }
//                        mDrawerToggle.syncState();
//                        break;
//                    case 4:
//                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/IkiMuhendis/LDrawer"));
//                        startActivity(browserIntent);
//                        break;
//                    case 5:
//                        Intent share = new Intent(Intent.ACTION_SEND);
//                        share.setType("text/plain");
//                        share.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        share.putExtra(Intent.EXTRA_SUBJECT,
//                            getString(R.string.app_name));
//                        share.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_description) + "\n" +
//                            "GitHub Page :  https://github.com/IkiMuhendis/LDrawer\n" +
//                            "Sample App : https://play.google.com/store/apps/details?id=" +
//                            getPackageName());
//                        startActivity(Intent.createChooser(share,
//                            getString(R.string.app_name)));
//                        break;
////                    case 6:
////                        String appUrl = "https://play.google.com/store/apps/details?id=" + getPackageName();
////                        Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(appUrl));
////                        startActivity(rateIntent);
//                        FragmentShowOrderFeaturedContent fragment = new FragmentShowOrderFeaturedContent();
////                        Bundle args = new Bundle();
////                        args.putInt("planet_number", position);
////                        fragment.setArguments(args);
//
//                        FragmentManager fragmentManager = getFragmentManager();
////                        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
//                        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
//
//                        // update selected item and title, then close the drawer
//                        mDrawerList.setItemChecked(position, true);
//                        mDrawerLayout.closeDrawer(mDrawerList);
//                        break;
//                }
//
//            }
//        });
    }

    private void initShowOrderFragment() {
        fragment = new FragmentShowOrderFeaturedContent();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
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

    private long mExitTime;

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
                FragmentManager fragmentManager = getFragmentManager();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                switch (position){
                    case 0:
                        ab.setTitle("NEW");
                        fragment = new FragmentShowOrderFeaturedContent();
                        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                        menuView.setItemChecked(position, true);
                        mDrawerLayout.closeDrawers();
                        break;
                    case 1:
                        ab.setTitle("分类");
                        FragmentSortContent sortFragment = new FragmentSortContent();
                        fragmentManager.beginTransaction().replace(R.id.content_frame, sortFragment).commit();
                        menuView.setItemChecked(position, true);
                        mDrawerLayout.closeDrawers();
                        break;
                    case 4:
                        ab.setTitle("分享新品");
                        FragmentShareProduct shareFragment = new FragmentShareProduct();
                        fragmentManager.beginTransaction().replace(R.id.content_frame, shareFragment).commit();
                        menuView.setItemChecked(position, true);
                        mDrawerLayout.closeDrawers();
                        break;
                    case 5:
                        ab.setTitle("支持NEW");
                        FragmentSupportUs supportFragment = new FragmentSupportUs();
                        fragmentManager.beginTransaction().replace(R.id.content_frame, supportFragment).commit();
                        menuView.setItemChecked(position, true);
                        mDrawerLayout.closeDrawers();
                        break;
                    case 7:
                        ab.setTitle("设置");
                        FragmentSetting settingFragment = new FragmentSetting();
                        fragmentManager.beginTransaction().replace(R.id.content_frame, settingFragment).commit();
                        menuView.setItemChecked(position, true);
                        mDrawerLayout.closeDrawers();
                        break;
                    case 8:
                        ab.setTitle("关于");
                        FragmentAbout aboutFragment = new FragmentAbout();
                        fragmentManager.beginTransaction().replace(R.id.content_frame, aboutFragment).commit();
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
//    /**
//     * Fragment that appears in the "content_frame", shows a planet
//     */
//    public static class PlanetFragment extends Fragment {
//        public static final String ARG_PLANET_NUMBER = "planet_number";
//
//        public PlanetFragment() {
//            // Empty constructor required for fragment subclasses
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.fragment_planet, container, false);
//            int i = getArguments().getInt(ARG_PLANET_NUMBER);
//            String planet = getResources().getStringArray(R.array.planets_array)[i];
//
//            int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()),
//                    "drawable", getActivity().getPackageName());
//            ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
//            getActivity().setTitle(planet);
//            return rootView;
//        }
//    }
}
