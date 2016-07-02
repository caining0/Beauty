package com.onlylady.beautyapp.activitys;


import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.onlylady.beautyapp.R;
import com.onlylady.beautyapp.utils.NetWorkState;

public class AdActivity extends Activity {
    private RelativeLayout layouth5;
    private String h5Url;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        initView();
        initData();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void initView(){
        layouth5 = (RelativeLayout) findViewById(R.id.ad_layout);
        h5Url = getIntent().getStringExtra(SplashActivity.VAL);
        webView = new WebView(this.getApplicationContext());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        webView.setLayoutParams(params);
        layouth5.addView(webView);
        webView.setInitialScale(25);// 为25%，最小缩放等级
        if(NetWorkState.getInstance().isConnect(this)){
            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);//缓存
        }else {
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        webView.setWebViewClient(new WebViewClient());
//        webSettings.setPluginState(WebSettings.PluginState.ON);
//        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        WebChromeClient wvcc = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                view.setLayerType(View.LAYER_TYPE_HARDWARE, null);

            }
        };
        webView.setWebChromeClient(wvcc);
    }
    public void initData(){
        webView.loadUrl(h5Url);
    }
}
