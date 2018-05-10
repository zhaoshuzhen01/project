package com.lubandj.master.my;

import android.databinding.DataBindingUtil;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.baselibrary.BaseActivity;
import com.lubandj.master.R;
import com.lubandj.master.databinding.ActivityAppaggrementBinding;


/**
 * 协议界面
 */
public class AdActivity extends BaseActivity {
    private ActivityAppaggrementBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_appaggrement);
        mBinding.tvTitle.setText("广告");
        initWebView();
    }

    /**
     * 返回
     *
     * @param view
     */
    public void onBack(View view) {
        finish();
    }

    @Override
    public int getLayout() {
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    private void initWebView() {
        WebSettings settings = mBinding.webView.getSettings();
        //支持javascript
        settings.setJavaScriptEnabled(true);
        // 设置可以支持缩放 
        settings.setSupportZoom(true);
        // 设置出现缩放工具 
//        settings.setBuiltInZoomControls(true);
        //扩大比例的缩放
        settings.setUseWideViewPort(true);
        settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
        mBinding.webView.loadUrl(getIntent().getStringExtra("url"));
        mBinding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                } else
                    view.loadUrl(request.toString());
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }


        });
        mBinding.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mBinding.pbAgreement.setProgress(newProgress * 100);
                if (newProgress >= 100) {
                    mBinding.pbAgreement.setVisibility(View.GONE);
                }
            }
        });
    }

//    new WebViewClient() {
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
//            view.loadUrl(url);
//            return true;
//        }
//
//        @Override
//        public void onReceivedError(WebView view, int errorCode,
//        String description, String failingUrl) {
//            super.onReceivedError(view, errorCode, description, failingUrl);
//            //在此处显示加载失败页面
//        }
//
//    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mBinding.webView.canGoBack()) {
            mBinding.webView.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        } else {
            finish();
        }
        return false;
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {

    }
}
