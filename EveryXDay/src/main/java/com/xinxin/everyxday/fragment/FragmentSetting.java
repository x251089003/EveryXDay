package com.xinxin.everyxday.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andexert.library.RippleView;
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
            }
        });

        checkUpdate = (RippleView)settingView.findViewById(R.id.checkupdate);
        checkUpdate.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent intent = new Intent(getActivity(), ContactUsActivity.class);
                startActivity(intent);
            }
        });

        return settingView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
