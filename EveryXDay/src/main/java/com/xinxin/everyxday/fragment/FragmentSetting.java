package com.xinxin.everyxday.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;
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
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;
import com.xinxin.everyxday.R;
import com.xinxin.everyxday.activity.ContactUsActivity;
import com.xinxin.everyxday.activity.OpenSourceActivity;
import com.xinxin.everyxday.activity.QuestionActivity;
import com.xinxin.everyxday.activity.ServiceTermActivity;
import com.xinxin.everyxday.global.Globe;
import com.xinxin.everyxday.sina.AccessTokenKeeper;
import com.xinxin.everyxday.tencent.BaseUiListener;
import com.xinxin.everyxday.util.AppInstallUtil;
import com.xinxin.everyxday.util.DataCleanUtil;
import com.xinxin.everyxday.util.LocalStorageUtil;
import com.xinxin.everyxday.util.WXBitmapConvertToByteUtil;
import com.xinxin.everyxday.wxapi.WXBaseEntryActivity;

import java.io.File;


/**
 * Created by xinxin on 15/7/28.
 */
public class FragmentSetting extends Fragment {

    private View settingView;
    private RippleView question;
    private RippleView contact;
    private RippleView serviceTerm;
    private RippleView openSource;
    private RippleView score;
    private RippleView clear;
    private RippleView checkUpdate;

    private RippleView weixin;
    private RippleView pengyouquan;
    private RippleView sina;
    private RippleView qq;

    private TextView cacheSize;

    private LocalStorageUtil mLocalStorageUtil;

    private Tencent mTencent;
    private AuthInfo mAuthInfo;
    private SsoHandler mSsoHandler;
    private IWeiboShareAPI mIWeiboShareAPI;

    public static FragmentSetting newInstance(Bundle args) {
        FragmentSetting myFragment = new FragmentSetting();
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocalStorageUtil = new LocalStorageUtil();
        // Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
        // 其中APP_ID是分配给第三方应用的appid，类型为String。
        mTencent = Tencent.createInstance(Globe.QQ_APP_ID, getActivity());
        mAuthInfo = new AuthInfo(getActivity(), Globe.SINA_APP_KEY, Globe.SINA_REDIRECT_URL, null);
        mSsoHandler = new SsoHandler(getActivity(), mAuthInfo);
        mIWeiboShareAPI =  WeiboShareSDK.createWeiboAPI(getActivity(), Globe.SINA_APP_KEY);
        mIWeiboShareAPI.registerApp();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        settingView = inflater.inflate(R.layout.setting, null);

        cacheSize = (TextView) settingView.findViewById(R.id.clear_mb);
        try {
            cacheSize.setText(DataCleanUtil.getCacheSize(new File(mLocalStorageUtil.getAppDir())));
        } catch (Exception e) {
            e.printStackTrace();
        }

        question = (RippleView) settingView.findViewById(R.id.question);
        question.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent intent = new Intent(getActivity(), QuestionActivity.class);
                startActivity(intent);
            }
        });

        contact = (RippleView) settingView.findViewById(R.id.contact);
        contact.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent intent = new Intent(getActivity(), ContactUsActivity.class);
                startActivity(intent);
            }
        });

        serviceTerm = (RippleView) settingView.findViewById(R.id.service);
        serviceTerm.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent intent = new Intent(getActivity(), ServiceTermActivity.class);
                startActivity(intent);
            }
        });

        openSource = (RippleView) settingView.findViewById(R.id.opensource);
        openSource.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent intent = new Intent(getActivity(), OpenSourceActivity.class);
                startActivity(intent);
            }
        });

        score = (RippleView) settingView.findViewById(R.id.mark);
        score.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        clear = (RippleView) settingView.findViewById(R.id.clear);
        clear.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                DataCleanUtil.cleanCustomCache(mLocalStorageUtil.getImageCacheDir());
                cacheSize.setText("0.0KB");
                Snackbar.with(getActivity()) // context
                        .colorResource(R.color.app_main_theme_color_transparent)
                        .duration(Snackbar.SnackbarDuration.LENGTH_SHORT)
                        .text("缓存清除成功") // text to display
                        .show(getActivity());
            }
        });

        checkUpdate = (RippleView) settingView.findViewById(R.id.checkupdate);
        checkUpdate.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                UmengUpdateAgent.setDefault();
                UmengUpdateAgent.setUpdateAutoPopup(false);
                UmengUpdateAgent.setUpdateOnlyWifi(false);
                UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
                    @Override
                    public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
                        switch (updateStatus) {
                            case UpdateStatus.Yes: // has update
                                UmengUpdateAgent.showUpdateDialog(getActivity(), updateInfo);
                                break;
                            case UpdateStatus.No: // has no update
                                Snackbar.with(getActivity()) // context
                                        .colorResource(R.color.app_main_theme_color_transparent)
                                        .duration(Snackbar.SnackbarDuration.LENGTH_SHORT)
                                        .text("当前已是最新版本") // text to display
                                        .show(getActivity());
                                break;
                            case UpdateStatus.NoneWifi: // none wifi
                                Snackbar.with(getActivity()) // context
                                        .colorResource(R.color.app_main_theme_color_transparent)
                                        .duration(Snackbar.SnackbarDuration.LENGTH_SHORT)
                                        .text("仅在WIFI环境下更新") // text to display
                                        .show(getActivity());
                                break;
                            case UpdateStatus.Timeout: // time out
                                Snackbar.with(getActivity()) // context
                                        .colorResource(R.color.app_main_theme_color_transparent)
                                        .duration(Snackbar.SnackbarDuration.LENGTH_SHORT)
                                        .text("网络连接失败") // text to display
                                        .show(getActivity());
                                break;
                        }
                    }
                });
                UmengUpdateAgent.update(getActivity());
            }
        });

        weixin = (RippleView) settingView.findViewById(R.id.weixin);
        weixin.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                shareToWX(0);
            }
        });

        pengyouquan = (RippleView) settingView.findViewById(R.id.pengyouquan);
        pengyouquan.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                shareToWX(1);
            }
        });

        qq = (RippleView) settingView.findViewById(R.id.qq);
        qq.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                onClickQQShare();
            }
        });

        sina = (RippleView) settingView.findViewById(R.id.sina);
        sina.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                onClickSinaShare();
            }
        });

        return settingView;
    }

    private void shareToWX(int type){
        if(!AppInstallUtil.isWeiXinInstalled(getActivity())){
            Snackbar.with(getActivity()) // context
                    .colorResource(R.color.app_main_theme_color_transparent)
                    .duration(Snackbar.SnackbarDuration.LENGTH_SHORT)
                    .text("请先安装微信客户端") // text to display
                    .show(getActivity());
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

            WXBaseEntryActivity.getIWXAPI(getActivity()).sendReq(req);
        }
    }

    private void onClickQQShare() {
        if(!AppInstallUtil.isQQInstalled(getActivity())){
            Snackbar.with(getActivity()) // context
                    .colorResource(R.color.app_main_theme_color_transparent)
                    .duration(Snackbar.SnackbarDuration.LENGTH_SHORT)
                    .text("请先安装QQ客户端") // text to display
                    .show(getActivity());
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
            mTencent.shareToQQ(getActivity(), params, new BaseUiListener(getActivity()));
        }
    }

    private void onClickSinaShare(){
        if(!AppInstallUtil.isWeiBoInstalled(getActivity())){
            Snackbar.with(getActivity()) // context
                    .colorResource(R.color.app_main_theme_color_transparent)
                    .duration(Snackbar.SnackbarDuration.LENGTH_SHORT)
                    .text("请先安装新浪微博客户端") // text to display
                    .show(getActivity());
            return;
        }else {
            Oauth2AccessToken mAccessToken = AccessTokenKeeper.readAccessToken(getActivity());
            if (!mAccessToken.isSessionValid()) {
                mSsoHandler.authorizeClientSso(new AuthListener());
                Toast.makeText(getActivity(),"调用授权",Toast.LENGTH_SHORT).show();
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

                mIWeiboShareAPI.sendRequest(getActivity(), request);
                Toast.makeText(getActivity(),"可以分享啦",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != mTencent){
            mTencent.onActivityResult(requestCode, resultCode, data);
        }
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
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
                AccessTokenKeeper.writeAccessToken(getActivity(), mAccessToken);
                Toast.makeText(getActivity(),
                        "授权成功啦", Toast.LENGTH_SHORT).show();
            } else {
                // 以下几种情况，您会收到 Code：
                // 1. 当您未在平台上注册的应用程序的包名与签名时；
                // 2. 当您注册的应用程序包名与签名不正确时；
                // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                String code = values.getString("code");
                Toast.makeText(getActivity(),"授权失败啦" + code,Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancel() {
            Toast.makeText(getActivity(),"取消授权啦",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(getActivity(),
                    "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
