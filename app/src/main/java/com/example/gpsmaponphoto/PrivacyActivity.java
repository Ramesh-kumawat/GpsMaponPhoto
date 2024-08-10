package com.example.gpsmaponphoto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class PrivacyActivity extends AppCompatActivity {

    ProgressBar privacy_bar;
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        privacy_bar = findViewById(R.id.privacy_bar);
        webview = findViewById(R.id.webview);

        WebSettings settings = webview.getSettings();
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(false);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setDomStorageEnabled(true);
        webview.setScrollbarFadingEnabled(true);

        init();
    }
    public void init() {
        this.webview.loadUrl("file:///android_asset/privacy.html");
        this.webview.requestFocus();
        this.privacy_bar.setVisibility(View.VISIBLE);
        this.webview.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView webView, String str) {
                try {
                    privacy_bar.setVisibility(View.INVISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}