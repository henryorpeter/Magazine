package com.juguo.magazine.ui.activity

import android.Manifest
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.gson.Gson
import com.gyf.barlibrary.ImmersionBar
import com.jeremyliao.liveeventbus.LiveEventBus
import com.juguo.magazine.App
import com.juguo.magazine.R
import com.juguo.magazine.bean.PieceBean
import com.juguo.magazine.bean.ReadBean
import com.juguo.magazine.event.WX_APP_ID
import com.juguo.magazine.event.creatFiles
import com.juguo.magazine.remote.ApiService
import com.juguo.magazine.remote.RetrofitManager
import com.juguo.magazine.util.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detailed_news.*

import java.io.File
import com.juguo.magazine.util.DownloadUtil
import com.juguo.magazine.util.DownloadUtil.OnDownloadListener
import kotlinx.android.synthetic.main.activity_advertise_detailed_news.*
import kotlinx.android.synthetic.main.activity_detailed_news.back_zhazhi_xq
import kotlinx.android.synthetic.main.activity_detailed_news.favorites_btn
import kotlinx.android.synthetic.main.activity_detailed_news.favorites_btn_delet
import kotlinx.android.synthetic.main.activity_detailed_news.webView_news
import kotlinx.android.synthetic.main.view_empty.*
import retrofit2.http.Url
import java.lang.Exception


class AdvertiseDetailedNewsActivity : AppCompatActivity() {

    private val mDisposable = CompositeDisposable()

    @JvmField
    protected var mApiService =
        RetrofitManager.getApi(ApiService::class.java) //?????????????????????ApiService????????????????????????
    val mWebView = WebView(App.sInstance)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advertise_detailed_news)
        back_zhazhi_xq.setOnClickListener { finish() }
        onClick()
        getReadtise()

        LiveEventBus.get(PieceBean.Price::class.java)
            .observeSticky(this, { price ->
                //??????sp????????????id
                val sp = application.getSharedPreferences("sp", MODE_PRIVATE)
                val editor = sp.edit()
                editor.putString("resId", price.id).apply()
                Log.e("TAG", "onChangedssss: " + Gson().toJson(price))
                webViews_ab_news.getSettings().setJavaScriptEnabled(true)
                webViews_ab_news.loadDataWithBaseURL(
                    null,
                    HtmlFormatUtil.getNewContent(price.getContent()),
                    "text/html",
                    "UTF-8",
                    null
                )
                webViews_ab_news.setWebViewClient(object : WebViewClient() {
                    val loadProgressDialog =
                        LoadProgressDialog(this@AdvertiseDetailedNewsActivity, "?????????????????????")

                    override fun onPageFinished(view: WebView?, url: String) {
                        super.onPageFinished(view, url)
                        // ????????????
                        loadProgressDialog.dismiss()
                    }

                    override fun onPageStarted(view: WebView?, url: String, favicon: Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                        //????????????
                        loadProgressDialog.show()
                    }
                })
            })
    }

    fun onClick() {
        favorites_btn.setOnClickListener {
            favoritesImage()
        }
        favorites_btn_delet.setOnClickListener {
            deleFavoritesImage()
        }
    }

    /**
     * ????????????
     */
    fun getReadtise() {
        val sp = application.getSharedPreferences("sp", MODE_PRIVATE)
        val resId = sp.getString("resId", "")
        mDisposable.add(mApiService.getReadtise(resId)
            .compose(RxUtils.schedulersTransformer())
            .subscribe({ readBean ->
                Log.d(TAG, "acceptgetReadtise>>>>>? $readBean")
            }) { throwable -> Log.d(TAG, "loadMore: $throwable") })
    }

    /**
     * ??????
     */
    fun favoritesImage() {
        val sp = application.getSharedPreferences("sp", MODE_PRIVATE)
        val resId = sp.getString("resId", "")
        mDisposable.add(mApiService.favoritesImages(resId, 1)
            .compose(RxUtils.schedulersTransformer())
            .subscribe({ favoritesBean ->
                Log.d(TAG, "favoritesBean: $favoritesBean")
                ToastUtil.showToast(application, "???????????????")
                favorites_btn.setVisibility(View.GONE)
                favorites_btn_delet.setVisibility(View.VISIBLE)
            }) { throwable ->
                Log.d(TAG, "loadMore: $throwable")
            })
    }

    /**
     * ????????????
     */
    private fun deleFavoritesImage() {
        val sp = application.getSharedPreferences("sp", MODE_PRIVATE)
        val resId = sp.getString("resId", null)

        mDisposable.add(mApiService.deleFavoritesImages(resId, 2)
            .compose(RxUtils.schedulersTransformer())
            .subscribe({ favoritesBean ->
                Log.d(
                    TAG, "favoritesBean: $favoritesBean"
                )
                ToastUtil.showToast(application, "???????????????")
                favorites_btn.setVisibility(View.VISIBLE)
                favorites_btn_delet.setVisibility(View.GONE)
            }
            ) { throwable ->
                Log.d(
                    TAG, "loadMore: $throwable"
                )
            })
    }

    override fun onDestroy() {
        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
        }
        super.onDestroy()
    }

}