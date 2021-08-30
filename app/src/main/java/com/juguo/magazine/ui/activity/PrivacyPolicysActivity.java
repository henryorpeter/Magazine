package com.juguo.magazine.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.gyf.barlibrary.ImmersionBar;
import com.juguo.magazine.App;
import com.juguo.magazine.R;
import com.juguo.magazine.util.UITools;


public class PrivacyPolicysActivity extends AppCompatActivity {
    private TextView tilite;
    private TextView tvqx;
    private WebView urlWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policys);
        UITools.makeStatusBarTransparent(this);
        UITools.setMIUI(this, true);
        tilite = findViewById(R.id.tv_title);
        tilite.setText("隐私政策");
        userAgree();
        onclick();
        //解决白色状态栏
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .navigationBarDarkIcon(true)
                .init();
    }

    private void userAgree() {
        urlWebView = (WebView) findViewById(R.id.webView);
        urlWebView.getSettings().setJavaScriptEnabled(true);
        String url = getIntent().getStringExtra("url");
        urlWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        urlWebView.loadUrl(url);
    }

    private void onclick() {
        tvqx = findViewById(R.id.tv_qx);
        tvqx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(App.sInstance, CsjSplashActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}