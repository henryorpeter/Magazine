package com.juguo.magazine.ui.activity

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.jeremyliao.liveeventbus.LiveEventBus
import com.juguo.magazine.R
import com.juguo.magazine.bean.PieceBean
import com.juguo.magazine.remote.ApiService
import com.juguo.magazine.remote.RetrofitManager
import com.juguo.magazine.util.HtmlFormatUtil
import com.juguo.magazine.util.LoadProgressDialog
import com.juguo.magazine.util.RxUtils
import com.juguo.magazine.util.ToastUtil
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_detailed_news.*
import kotlinx.android.synthetic.main.fashion_magazine_activity.*

class DetailedNewsActivity : AppCompatActivity() {

    private val mDisposable = CompositeDisposable()
    @JvmField
    protected var mApiService = RetrofitManager.getApi(ApiService::class.java) //初始化请求接口ApiService，给继承的子类用
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_news)
        back_zhazhi_xq.setOnClickListener { finish() }
        onClick()
        LiveEventBus.get(PieceBean.Price::class.java)
            .observeSticky(this, { price ->
                Log.e("TAG", "onChanged: " + Gson().toJson(price))
                webView_news.getSettings().setJavaScriptEnabled(true)
                webView_news.loadDataWithBaseURL(
                    null,
                    HtmlFormatUtil.getNewContent(price.getContent()),
                    "text/html",
                    "UTF-8",
                    null
                )
                webView_news.setWebViewClient(object : WebViewClient() {
                    val loadProgressDialog =
                        LoadProgressDialog(this@DetailedNewsActivity, "数据加载中……")

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
        back_shoucang.setOnClickListener {
            favoritesImage()
        }
    }

    /**
     * 收藏图片
     */
    private fun favoritesImage() {
        val sp = application.getSharedPreferences("sp", MODE_PRIVATE)
        val resId = sp.getString("resId", "")
        mDisposable.add(mApiService.favoritesImages(resId, 1)
            .compose(RxUtils.schedulersTransformer())
            .subscribe({ favoritesBean ->
                Log.d(
                    TAG,
                    "favoritesBean: $favoritesBean"
                )
                ToastUtil.showToast(application, "收藏成功！")
            }) { throwable ->
                Log.d(
                    TAG,
                    "loadMore: $throwable"
                )
            })
    }

}