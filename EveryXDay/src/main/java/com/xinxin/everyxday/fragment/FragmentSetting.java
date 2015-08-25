package com.xinxin.everyxday.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.gc.materialdesign.widgets.Dialog;
import com.nispok.snackbar.Snackbar;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;
import com.xinxin.everyxday.R;
import com.xinxin.everyxday.activity.ContactUsActivity;
import com.xinxin.everyxday.activity.OpenSourceActivity;
import com.xinxin.everyxday.activity.QuestionActivity;
import com.xinxin.everyxday.activity.ServiceTermActivity;
import com.xinxin.everyxday.util.DataCleanUtil;
import com.xinxin.everyxday.util.LocalStorageUtil;

import java.io.File;

/**
 * Created by xinxin on 15/7/28.
 */
public class FragmentSetting extends Fragment{

    private View settingView;
    private RippleView question;
    private RippleView contact;
    private RippleView serviceTerm;
    private RippleView openSource;
    private RippleView score;
    private RippleView clear;
    private RippleView checkUpdate;

    private TextView cacheSize;

    private LocalStorageUtil mLocalStorageUtil;

    public static FragmentSetting newInstance(Bundle args) {
        FragmentSetting myFragment = new FragmentSetting();
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocalStorageUtil = new LocalStorageUtil();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        settingView = inflater.inflate(R.layout.setting, null);

        cacheSize = (TextView)settingView.findViewById(R.id.clear_mb);
        try {
            cacheSize.setText(DataCleanUtil.getCacheSize(new File(mLocalStorageUtil.getAppDir())));
        } catch (Exception e) {
            e.printStackTrace();
        }

        question = (RippleView)settingView.findViewById(R.id.question);
        question.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent intent = new Intent(getActivity(), QuestionActivity.class);
                startActivity(intent);
            }
        });

        contact = (RippleView)settingView.findViewById(R.id.contact);
        contact.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent intent = new Intent(getActivity(), ContactUsActivity.class);
                startActivity(intent);
            }
        });

        serviceTerm = (RippleView)settingView.findViewById(R.id.service);
        serviceTerm.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent intent = new Intent(getActivity(), ServiceTermActivity.class);
                startActivity(intent);
            }
        });

        openSource = (RippleView)settingView.findViewById(R.id.opensource);
        openSource.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent intent = new Intent(getActivity(), OpenSourceActivity.class);
                startActivity(intent);
            }
        });

        score = (RippleView)settingView.findViewById(R.id.mark);
        score.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        clear = (RippleView)settingView.findViewById(R.id.clear);
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

        checkUpdate = (RippleView)settingView.findViewById(R.id.checkupdate);
        checkUpdate.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                UmengUpdateAgent.setDefault();
                UmengUpdateAgent.setUpdateAutoPopup(false);
                UmengUpdateAgent.setUpdateOnlyWifi(false);
                UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
                    @Override
                    public void onUpdateReturned(int updateStatus,UpdateResponse updateInfo) {
                        switch (updateStatus) {
                            case UpdateStatus.Yes: // has update
                                UmengUpdateAgent.showUpdateDialog(getActivity(), updateInfo);
                                Dialog dialog = new Dialog(getActivity(),"NEW新版本", updateInfo.updateLog);
                                dialog.getButtonAccept().setText("确定");
                                dialog.getButtonCancel().setText("取消");
                                dialog.show();
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

        return settingView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
