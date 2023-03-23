package com.example.newsapplication;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailsArticle extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_article);

        WebView webView = findViewById(R.id.webview);
        String url = getIntent().getStringExtra("urlData");
        webView.loadUrl(url);
        webView.getSettings().setJavaScriptEnabled(false);
        webView.setClickable(false);
        webView.setFocusable(false);
        webView.setEnabled(false);

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                view.setEnabled(progress == 100);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
}
