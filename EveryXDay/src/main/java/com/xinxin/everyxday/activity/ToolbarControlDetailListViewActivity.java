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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.nispok.snackbar.Snackbar;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.utils.Utility;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.tauth.Tencent;
import com.xinxin.everyxday.R;
import com.xinxin.everyxday.base.imgloader.ImgLoadUtil;
import com.xinxin.everyxday.base.loopj.requestinstance.CommonRequestWrapDelegateAbstractImpl;
import com.xinxin.everyxday.base.loopj.requestinstance.CommonRequestWrapWithBean;
import com.xinxin.everyxday.bean.ShowOrderDetialBean;
import com.xinxin.everyxday.bean.base.CommonResponseBody;
import com.xinxin.everyxday.dao.util.DbService;
import com.xinxin.everyxday.global.Globe;
import com.xinxin.everyxday.sina.AccessTokenKeeper;
import com.xinxin.everyxday.tencent.BaseUiListener;
import com.xinxin.everyxday.util.AppInstallUtil;
import com.xinxin.everyxday.util.WXBitmapConvertToByteUtil;
import com.xinxin.everyxday.widget.AlignTextView;
import com.xinxin.everyxday.widget.CBAlignTextView;
import com.xinxin.everyxday.wxapi.WXBaseEntryActivity;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class ToolbarControlDetailListViewActivity extends ToolbarControlBaseActivity<ObservableListView> implements SwipeRefreshLayout.OnRefreshListener{

    private static final String TAG = ToolbarControlDetailListViewActivity.class.getSimpleName();

    private LayoutInflater inflater;

    private DetailNewAdapter mDetailNewAdapter;

    private ArrayList<ShowOrderDetialBean.ContentsEntity> showOrderDetialList;



    private boolean isRefreshing = false;

    private ObservableListView refreshView;

    private ProgressBarCircularIndeterminate loadProgress;

    private boolean isLiked = false;

    private Tencent mTencent;
    private AuthInfo mAuthInfo;
    private SsoHandler mSsoHandler;
    private IWeiboShareAPI mIWeiboShareAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if(intent == null){
            finish();
            return;
        }
        super.onCreate(savedInstanceState);

        inflater = LayoutInflater.from(this);

        mTencent = Tencent.createInstance(Globe.QQ_APP_ID, this);
        mAuthInfo = new AuthInfo(this, Globe.SINA_APP_KEY, Globe.SINA_REDIRECT_URL, null);
        mSsoHandler = new SsoHandler(this, mAuthInfo);
        mIWeiboShareAPI =  WeiboShareSDK.createWeiboAPI(this, Globe.SINA_APP_KEY);
        mIWeiboShareAPI.registerApp();
    }

    private void loadListData() {
        CommonRequestWrapWithBean<ShowOrderDetialBean> worthBuyRequest = new CommonRequestWrapWithBean<ShowOrderDetialBean>(
                this, detailNew, null, false, new ShowOrderDetailRequestWrapDelegateImpl(),
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
                    RippleView weixin = (RippleView) convertView.findViewById(R.id.weixin);
                    weixin.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                        @Override
                        public void onComplete(RippleView rippleView) {
                            shareToWX(0);
                        }
                    });

                    RippleView pengyouquan = (RippleView) convertView.findViewById(R.id.pengyouquan);
                    pengyouquan.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                        @Override
                        public void onComplete(RippleView rippleView) {
                            shareToWX(1);
                        }
                    });

                    RippleView qq = (RippleView) convertView.findViewById(R.id.qq);
                    qq.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                        @Override
                        public void onComplete(RippleView rippleView) {
                            onClickQQShare();
                        }
                    });

                    RippleView sina = (RippleView) convertView.findViewById(R.id.sina);
                    sina.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                        @Override
                        public void onComplete(RippleView rippleView) {
                            onClickSinaShare();
                        }
                    });
                    break;
            }

            return convertView;
        }
    }

    private void shareToWX(int type){
        if(!AppInstallUtil.isWeiXinInstalled(this)){
            Snackbar.with(this) // context
                    .colorResource(R.color.app_main_theme_color_transparent)
                    .duration(Snackbar.SnackbarDuration.LENGTH_SHORT)
                    .text("请先安装微信客户端") // text to display
                    .show(this);
            return;
        }else{
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            WXWebpageObject webpage = new WXWebpageObject();
            webpage.webpageUrl = Globe.APP_TARGET_URL;

            WXMediaMessage msg = new WXMediaMessage(webpage);
            msg.title = "一个神秘的礼物";
            msg.description = "这里没有浮躁与喧嚣，这里会让你静下心来感受生活的美好，一切精彩尽在NEW！";
            msg.thumbData = WXBitmapConvertToByteUtil.bmpToByteArray(bmp, false);

            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("webpage");
            req.message = msg;

            if(type == 0){
                req.scene = SendMessageToWX.Req.WXSceneSession;
            }else{
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
            }

            WXBaseEntryActivity.getIWXAPI(this).sendReq(req);
        }
    }

    private void onClickQQShare() {
        if(!AppInstallUtil.isQQInstalled(this)){
            Snackbar.with(this) // context
                    .colorResource(R.color.app_main_theme_color_transparent)
                    .duration(Snackbar.SnackbarDuration.LENGTH_SHORT)
                    .text("请先安装QQ客户端") // text to display
                    .show(this);
            return;
        }else {
            final Bundle params = new Bundle();
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putString(QQShare.SHARE_TO_QQ_TITLE, "一个神秘的礼物");
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "这里没有浮躁与喧嚣，这里会让你静下心来感受生活的美好，一切精彩尽在NEW！");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://www.taoxiaoxian.com");
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "NEW");
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
            mTencent.shareToQQ(this, params, new BaseUiListener(this));
        }
    }

    private void onClickSinaShare(){
        if(!AppInstallUtil.isWeiBoInstalled(this)){
            Snackbar.with(this) // context
                    .colorResource(R.color.app_main_theme_color_transparent)
                    .duration(Snackbar.SnackbarDuration.LENGTH_SHORT)
                    .text("请先安装新浪微博客户端") // text to display
                    .show(this);
            return;
        }else {
            Oauth2AccessToken mAccessToken = AccessTokenKeeper.readAccessToken(this);
            if (!mAccessToken.isSessionValid()) {
                mSsoHandler.authorizeClientSso(new AuthListener());
                Toast.makeText(this, "调用授权", Toast.LENGTH_SHORT).show();
            }else{
                Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                ImageObject imageObject = new ImageObject();
                imageObject.identify = Utility.generateGUID();
                imageObject.imageData = WXBitmapConvertToByteUtil.bmpToByteArray(bmp, false);

                TextObject textObject = new TextObject();
                textObject.text = "这里没有浮躁与喧嚣，这里会让你静下心来感受生活的美好，一切精彩尽在NEW！";

                WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
                weiboMessage.imageObject = imageObject;
                weiboMessage.textObject = textObject;

                // 2. 初始化从第三方到微博的消息请求
                SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
                // 用transaction唯一标识一个请求
                request.transaction = String.valueOf(System.currentTimeMillis());
                request.multiMessage = weiboMessage;

                mIWeiboShareAPI.sendRequest(this, request);
                Toast.makeText(this,"可以分享啦",Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 微博认证授权回调类。
     * 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用 {@link SsoHandler#authorizeCallBack} 后，
     *    该回调才会被执行。
     * 2. 非 SSO 授权时，当授权结束后，该回调就会被执行。
     * 当授权成功后，请保存该 access_token、expires_in、uid 等信息到 SharedPreferences 中。
     */
    class AuthListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            Oauth2AccessToken mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            System.out.println("呵呵呵呵呵呵呵呵呵呵呵呵呵");
            //从这里获取用户输入的 电话号码信息
            if (mAccessToken.isSessionValid()) {
                // 保存 Token 到 SharedPreferences
                AccessTokenKeeper.writeAccessToken(getApplicationContext(), mAccessToken);
                Toast.makeText(getApplicationContext(),
                        "授权成功啦", Toast.LENGTH_SHORT).show();
            } else {
                // 以下几种情况，您会收到 Code：
                // 1. 当您未在平台上注册的应用程序的包名与签名时；
                // 2. 当您注册的应用程序包名与签名不正确时；
                // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                String code = values.getString("code");
                Toast.makeText(getApplicationContext(),"授权失败啦" + code,Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancel() {
            Toast.makeText(getApplicationContext(),"取消授权啦",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(getApplicationContext(),
                    "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != mTencent){
            mTencent.onActivityResult(requestCode, resultCode, data);
        }
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

}
