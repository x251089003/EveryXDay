package com.xinxin.everyxday.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;


import com.xinxin.everyxday.fragment.FragmentShowOrderFeaturedContent;
import com.xinxin.everyxday.widget.GlobalMenuView;
import com.xinxin.ldrawer.ActionBarDrawerToggle;
import com.xinxin.ldrawer.DrawerArrowDrawable;

import java.util.Locale;

import com.xinxin.everyxday.R;

import butterknife.Bind;

public class EveryXDayMainActivity extends Activity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerArrowDrawable drawerArrow;
    private boolean drawerArrowColor;
    private GlobalMenuView menuView;

    private LinearLayout menuLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        ActionBar ab = getActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeButtonEnabled(true);

        menuLayout = (LinearLayout)findViewById(R.id.navdrawer);
        menuView = new GlobalMenuView(this);
        menuLayout.addView(menuView);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


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
//                    case 6:
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
