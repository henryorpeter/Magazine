package com.juguo.magazine.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gyf.barlibrary.ImmersionBar
import com.juguo.magazine.R
import com.juguo.magazine.remote.ApiService
import com.juguo.magazine.remote.RetrofitManager
import com.juguo.magazine.util.*
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_privacy.*

/**
 *  author : Administrator
 *  date : 2021/8/20 10:21
 *  description :
 * @Author : yangjinjing
 */
class PrivacyActivity : AppCompatActivity() {
    private val TAG = "cimoGallery"
    //private var urlWebView: WebView? = null   //为空对象，可能造成空指针异常
    //private lateinit var urlWebView: WebView   //可以声明为延迟加载  lateinit=延迟初始化
    private val mDisposable = CompositeDisposable()
    @JvmField
    protected var mApiService = RetrofitManager.getApi(ApiService::class.java) //初始化请求接口ApiService，给继承的子类用

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy)
        UITools.makeStatusBarTransparent(this)
        UITools.setMIUI(this, true)
        initViewAndData()
        onclick()
        //解决白色状态栏
        ImmersionBar.with(this)
            .statusBarDarkFont(true)
            .navigationBarDarkIcon(true)
            .init()
    }

    private fun initViewAndData() {
        mDisposable.add(mApiService.getPrivacy()
            .compose(RxUtils.schedulersTransformer())
            .subscribe({ privacyBean ->
                Log.d(TAG, "accept: $privacyBean")
                //urlWebView = findViewById(R.id.webView) //在这里延迟初始化urlWebView这个变量
                webView.getSettings().javaScriptEnabled = true  //需要加?  表示如果为空的情况，不会执行这条代码
                if (RomUtil.isVivo()) {
                    //如果是vivo设备
                    webView.loadDataWithBaseURL(
                        null,
                        privacyBean.result.vivo,
                        "text/html",
                        "UTF-8",
                        null
                    )
                } else if (RomUtil.isEmui()) {
                    //如果是华为设备
                    webView.loadDataWithBaseURL(
                        null,
                        privacyBean.result.huawei,
                        "text/html",
                        "UTF-8",
                        null
                    )
                }
            }) { throwable -> Log.d(TAG, "loadMore: $throwable") })
    }

    private fun onclick() {
        // alt+ enter
        tv_qx.setOnClickListener { v: View? -> finish() }
    }
}