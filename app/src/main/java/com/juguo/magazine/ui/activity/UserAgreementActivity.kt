package com.juguo.magazine.ui.activity

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.gyf.barlibrary.ImmersionBar
import com.juguo.magazine.R
import com.juguo.magazine.util.LoadProgressDialog
import com.juguo.magazine.util.UITools
import kotlinx.android.synthetic.main.activity_privacy.*

/**
 *  author : Administrator
 *  date : 2021/8/20 11:51
 *  description :
 * @Author : yangjinjing
 */
class UserAgreementActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_agreement)
        UITools.makeStatusBarTransparent(this)
        tv_title.setText("用户协议")
        userAgree()
        onclick()
        //解决白色状态栏
        ImmersionBar.with(this)
            .statusBarDarkFont(true)
            .navigationBarDarkIcon(true)
            .init()
    }

    private fun userAgree() {
        webView.settings.javaScriptEnabled = true
        val url = intent.getStringExtra("url")
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return false
            }
        }
        webView!!.loadUrl(url!!)
        webView!!.webViewClient = object : WebViewClient() {
            val loadProgressDialog = LoadProgressDialog(this@UserAgreementActivity, "数据加载中……")
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                // 加载完成
                loadProgressDialog.dismiss()
            }

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                //开始加载
                loadProgressDialog.show()
            }
        }
    }

    private fun onclick() {
        tv_qx.setOnClickListener({ v: View? -> finish() })
    }
}