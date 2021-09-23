package com.juguo.magazine.ui.activity

import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.jeremyliao.liveeventbus.LiveEventBus
import com.juguo.magazine.App
import com.juguo.magazine.R
import com.juguo.magazine.bean.PieceBean
import com.juguo.magazine.bean.ReadHistoryBean
import com.juguo.magazine.event.creatFiles
import com.juguo.magazine.remote.ApiService
import com.juguo.magazine.remote.RetrofitManager
import com.juguo.magazine.util.*
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_advertise_detailed_news.*
import kotlinx.android.synthetic.main.activity_advertison_f_detailed_find.*
import kotlinx.android.synthetic.main.activity_advertison_f_detailed_find.webViews_abs_news
import kotlinx.android.synthetic.main.activity_detailed_news.*
import kotlinx.android.synthetic.main.activity_detailed_news.back_zhazhi_xq
import kotlinx.android.synthetic.main.activity_detailed_news.favorites_btn
import kotlinx.android.synthetic.main.activity_detailed_news.favorites_btn_delet
import java.io.File
import java.lang.Exception

class AdvertionReadHistoryNewsActivity : AppCompatActivity() {

    private val mDisposable = CompositeDisposable()

    @JvmField
    protected var mApiService =
        RetrofitManager.getApi(ApiService::class.java) //初始化请求接口ApiService，给继承的子类用
    val mWebView = WebView(App.sInstance)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advertise_detailed_news)
        back_zhazhi_xq.setOnClickListener { finish() }
        onClick()
        getReadtise()
        LiveEventBus.get("ReadHistoryBean",ReadHistoryBean.ReadHistory::class.java)
            .observeSticky(this, { price ->
                //写入sp保存图片id
                val sp = application.getSharedPreferences("sp", MODE_PRIVATE)
                val editor = sp.edit()
                editor.putString("resId", price.id).apply()
                Log.e("TAG", "onChangedssss: " + Gson().toJson(price))
                webViews_ab_news.getSettings().setJavaScriptEnabled(true)
                webViews_ab_news.loadDataWithBaseURL(
                    null,
                    HtmlFormatUtil.getNewContent(price.content),
                    "text/html",
                    "UTF-8",
                    null
                )
                webViews_ab_news.setWebViewClient(object : WebViewClient() {
                    val loadProgressDialog =
                        LoadProgressDialog(this@AdvertionReadHistoryNewsActivity, "数据加载中……")

                    override fun onPageFinished(view: WebView?, url: String) {
                        super.onPageFinished(view, url)
                        // 加载完成
                        loadProgressDialog.dismiss()
                    }

                    override fun onPageStarted(view: WebView?, url: String, favicon: Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                        //开始加载
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
     * 阅读记录
     */
    fun getReadtise() {
        val sp = application.getSharedPreferences("sp", MODE_PRIVATE)
        val resId = sp.getString("resId", "")
        mDisposable.add(mApiService.getReadtise(resId)
            .compose(RxUtils.schedulersTransformer())
            .subscribe({ readBean ->
                Log.d(ContentValues.TAG, "acceptgetReadtise>>>>>? $readBean")
            }) { throwable -> Log.d(ContentValues.TAG, "loadMore: $throwable") })
    }

    /**
     * 收藏
     */
    fun favoritesImage() {
        val sp = application.getSharedPreferences("sp", MODE_PRIVATE)
        val resId = sp.getString("resId", "")
        mDisposable.add(mApiService.favoritesImages(resId, 1)
            .compose(RxUtils.schedulersTransformer())
            .subscribe({ favoritesBean ->
                Log.d(ContentValues.TAG, "favoritesBean: $favoritesBean")
                ToastUtil.showToast(application, "收藏成功！")
                favorites_btn.setVisibility(View.GONE)
                favorites_btn_delet.setVisibility(View.VISIBLE)
            }) { throwable ->
                Log.d(ContentValues.TAG, "loadMore: $throwable")
            })
    }

    /**
     * 删除收藏
     */
    private fun deleFavoritesImage() {
        val sp = application.getSharedPreferences("sp", MODE_PRIVATE)
        val resId = sp.getString("resId", null)

        mDisposable.add(mApiService.deleFavoritesImages(resId, 2)
            .compose(RxUtils.schedulersTransformer())
            .subscribe({ favoritesBean ->
                Log.d(
                    ContentValues.TAG, "favoritesBean: $favoritesBean"
                )
                ToastUtil.showToast(application, "取消收藏！")
                favorites_btn.setVisibility(View.VISIBLE)
                favorites_btn_delet.setVisibility(View.GONE)
            }
            ) { throwable ->
                Log.d(
                    ContentValues.TAG, "loadMore: $throwable"
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