/*
 * Copyright 2014 Soichiro Kashima
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xinxin.everyxday.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.xinxin.everyxday.R;
import com.xinxin.everyxday.base.imgloader.ImgLoadUtil;
import com.xinxin.everyxday.base.loopj.requestinstance.CommonRequestWrapDelegateAbstractImpl;
import com.xinxin.everyxday.base.loopj.requestinstance.CommonRequestWrapWithBean;
import com.xinxin.everyxday.bean.ShowOrderDetialBean;
import com.xinxin.everyxday.bean.base.CommonResponseBody;
import com.xinxin.everyxday.widget.AlignTextView;
import com.xinxin.everyxday.widget.CBAlignTextView;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class ToolbarControlDetailListViewActivity extends ToolbarControlBaseActivity<ObservableListView> implements SwipeRefreshLayout.OnRefreshListener{

    private static final String TAG = ToolbarControlDetailListViewActivity.class.getSimpleName();

    private LayoutInflater inflater;

    private DetailNewAdapter mDetailNewAdapter;

    private ArrayList<ShowOrderDetialBean.ContentsEntity> showOrderDetialList;

    private String detailNeww;

    private boolean isRefreshing = false;

    private ObservableListView refreshView;

    private ProgressBarCircularIndeterminate loadProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if(intent == null){
            finish();
            return;
        }
        detailNeww = intent.getStringExtra("today_detail_new_buyurl");
        super.onCreate(savedInstanceState);
        inflater = LayoutInflater.from(this);
    }

    private void loadListData() {
        CommonRequestWrapWithBean<ShowOrderDetialBean> worthBuyRequest = new CommonRequestWrapWithBean<ShowOrderDetialBean>(
                this, detailNeww, null, false, new ShowOrderDetailRequestWrapDelegateImpl(),
                ShowOrderDetialBean.class);

        worthBuyRequest.getRequest();
    }

    @Override
    public void onRefresh() {
        if(!isRefreshing){
            loadListData();
        }
    }

    private class ShowOrderDetailRequestWrapDelegateImpl extends CommonRequestWrapDelegateAbstractImpl<ShowOrderDetialBean> {

        @Override
        public void requestServerStart(ProgressDialog progressDialog) {
            isRefreshing = true;
        }

        @Override
        public void requestServerSuccess(CommonResponseBody<ShowOrderDetialBean> responseBody) {

            if(responseBody != null){

                ShowOrderDetialBean mShowOrderDetialBean = responseBody.getResponseObject();

                if(mShowOrderDetialBean != null){

                    showOrderDetialList = mShowOrderDetialBean.getContents();

                    for(int i = 0 ; i < showOrderDetialList.size() ; i++){
                        System.out.println("img = " + showOrderDetialList.get(i).getImg());
                        System.out.println("description = " + showOrderDetialList.get(i).getDescription());
                    }

                    if(mDetailNewAdapter != null){

                        mDetailNewAdapter.notifyDataSetChanged();

                    }else{
                        initListView();
                    }
                }
            }
        }

        @Override
        public void requestServerEnd(ProgressDialog progressDialog) {
            if(loadProgress != null){
                loadProgress.setVisibility(View.GONE);
            }
            isRefreshing = false;
        }
    }

    private void initListView(){

        int dividerHeight = getResources().getDimensionPixelSize(R.dimen.detail_view_divider_height);
        refreshView.setCacheColorHint(0);
        refreshView.setFastScrollEnabled(false);
        refreshView.setDivider(null);
//        refreshView.setDividerHeight(dividerHeight);
//        refreshView.setPadding(paddingSpace, paddingSpace, paddingSpace, paddingSpace);
//        refreshView.setClipToPadding(false);
//        refreshView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);

        mDetailNewAdapter = new DetailNewAdapter();
        refreshView.setAdapter(mDetailNewAdapter);

        if(loadProgress != null){
            loadProgress.setVisibility(View.GONE);
        }

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_toolbarcontrollistview;
    }

    @Override
    protected ObservableListView createScrollable() {
        loadProgress = (ProgressBarCircularIndeterminate)findViewById(R.id.progressBarCircularIndetermininate);
        refreshView = (ObservableListView)findViewById(R.id.scrollable);

        loadListData();

        // ObservableListView uses setOnScrollListener, but it still works.
        refreshView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                Log.v(TAG, "onScrollStateChanged: " + scrollState);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                Log.v(TAG, "onScroll: firstVisibleItem: " + firstVisibleItem + " visibleItemCount: " + visibleItemCount + " totalItemCount: " + totalItemCount);
            }
        });
        return refreshView;
    }

    class DetailNewAdapter extends BaseAdapter{

        public static final int ITEM_CONTENT_TYPE = 0;
        public static final int ITEM_SHARE_TYPE = 1;

        @Override
        public int getCount() {
            return showOrderDetialList.size() + 1;
        }

        @Override
        public ShowOrderDetialBean.ContentsEntity getItem(int position) {
            if(position != showOrderDetialList.size()){
                return showOrderDetialList.get(position);
            }else{
                return null;
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            if(position == showOrderDetialList.size()){
                return ITEM_SHARE_TYPE;
            }else{
                return ITEM_CONTENT_TYPE;
            }
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int type = getItemViewType(position);
            if(convertView == null){
                switch (type) {
                    case ITEM_CONTENT_TYPE:
                        convertView = inflater.inflate(R.layout.detail_new_item, null);
                        break;
                    case ITEM_SHARE_TYPE:
                        convertView = inflater.inflate(R.layout.detail_share_item, null);
                        break;
                }
            }

            switch (type) {
                case ITEM_CONTENT_TYPE:
                    CBAlignTextView detailDescription = (CBAlignTextView) convertView.findViewById(R.id.description);
                    ImageView detailImageView = (ImageView) convertView.findViewById(R.id.img);
                    System.out.println("des === " +showOrderDetialList.get(position).getDescription() );
                    detailDescription.setText(showOrderDetialList.get(position).getDescription());
                    ImgLoadUtil.displayImageWithAnimationAndNoCorner(showOrderDetialList.get(position).getImg(), detailImageView);
                    break;
                case ITEM_SHARE_TYPE:

                    break;
            }

            return convertView;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem menu) {
        int id = menu.getItemId();
        if (id == R.id.menu_about) {

            return true;
        }
        return false;
    }

}
