package com.zk.mapnavigation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * author: ZK.
 * date:   On 2017/11/17.
 */
public class WebViewActivity extends AppCompatActivity {

    private static final String EXTRA_URL = "extra_url";
    private WebView mWebView;
    private String mUrl;

    public static void launch(Activity activity, String url) {
        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtra(EXTRA_URL, url);
        activity.startActivity(intent);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        mWebView = (WebView) findViewById(R.id.webview);
         mUrl = getIntent().getStringExtra(EXTRA_URL);
        mWebView.loadUrl(mUrl);
    }


}
