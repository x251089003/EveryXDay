package com.xinxin.everyxday.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.github.ksoichiro.android.observablescrollview.ObservableWebView;
import com.xinxin.everyxday.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by xinxin on 15/8/3.
 */
public class ToolbarControlWebViewActivity extends ToolbarControlBaseActivity<ObservableWebView> {

    private String loadUrl;
    private String viewTitle;
    private ProgressBarCircularIndeterminate loadProgress;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_toolbarcontrolwebview;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if(intent == null){
            finish();
            return;
        }

        loadUrl = intent.getStringExtra("today_new_url");
        viewTitle = intent.getStringExtra("today_new_title");
        super.onCreate(savedInstanceState);
        loadProgress = (ProgressBarCircularIndeterminate)findViewById(R.id.progressBarCircularIndetermininate);
    }

    @Override
    protected ObservableWebView createScrollable() {
        ObservableWebView webView = (ObservableWebView) findViewById(R.id.scrollable);
        webView.setWebViewClient(new WebC());
//        webView.setWebChromeClient(new WebCC());
        webView.loadUrl(loadUrl);
        return webView;
    }

//    private class WebCC extends WebChromeClient {
//
//        @Override
//        public void onProgressChanged(WebView view, int progress) {
//            loadProgress.
//        }
//
//    }

    private class WebC extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            loadProgress.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            loadProgress.setVisibility(View.GONE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
