package com.juguo.magazine.ui.activity

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.gyf.barlibrary.ImmersionBar
import com.juguo.magazine.R
import com.juguo.magazine.util.UITools
import kotlinx.android.synthetic.main.activity_privacy_policys.*

class PrivacyPolicyActivity :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policys)
        UITools.makeStatusBarTransparent(this)
        UITools.setMIUI(this, true)
        userAgree()
        tv_qx.setOnClickListener { finish() }
        //解决白色状态栏
        ImmersionBar.with(this)
            .statusBarDarkFont(true)
            .navigationBarDarkIcon(true)
            .init()
    }

    private fun userAgree() {
        webView.getSettings().setJavaScriptEnabled(true)
        val url = intent.getStringExtra("url")
        webView.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return false
            }
        })
        if (url != null) {
            webView.loadUrl(url)
        }
    }

}