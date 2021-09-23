package com.juguo.magazine.ui.activity

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.jeremyliao.liveeventbus.LiveEventBus
import com.juguo.magazine.R
import com.juguo.magazine.bean.FavoritesListBean
import com.juguo.magazine.bean.PieceBean
import com.juguo.magazine.remote.ApiService
import com.juguo.magazine.remote.RetrofitManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detailed_news.*
import kotlinx.android.synthetic.main.fashion_magazine_activity.*
import okhttp3.MediaType
import okhttp3.RequestBody
import android.webkit.WebSettings
import android.widget.RelativeLayout
import com.juguo.magazine.App
import com.juguo.magazine.event.creatFiles
import com.juguo.magazine.util.*
import kotlinx.android.synthetic.main.activity_advertise_detailed_news.*
import kotlinx.android.synthetic.main.activity_advertise_detailed_news.webViews_ab_news
import kotlinx.android.synthetic.main.activity_advertison_f_detailed_find.*
import kotlinx.android.synthetic.main.activity_detailed_news.back_zhazhi_xq
import java.io.File
import java.lang.Exception


class AdvertisonFindDetailedActivity : AppCompatActivity() {

    private val mDisposable = CompositeDisposable()

    @JvmField
    protected var mApiService =
        RetrofitManager.getApi(ApiService::class.java) //初始化请求接口ApiService，给继承的子类用
    var favorites: MutableLiveData<List<FavoritesListBean.Favorites>> = MutableLiveData<List<FavoritesListBean.Favorites>>()
    val mWebView = WebView(App.sInstance)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advertison_f_detailed_find)
        back_zhazhi_xq.setOnClickListener { finish() }
        onClick()
        LiveEventBus.get("favoritesKey", FavoritesListBean.Favorites::class.java)
            .observeSticky(this, { price ->
                //写入sp保存图片id
                val sp = application.getSharedPreferences("sp", MODE_PRIVATE)
                val editor = sp.edit()
                editor.putString("resId", price.id).apply()
                Log.e("TAG", "onChanged: " + Gson().toJson(price))
                webViews_abs_news.getSettings().setJavaScriptEnabled(true)
                webViews_abs_news.loadDataWithBaseURL(
                    null,
                    HtmlFormatUtil.getNewContent(price.getContent()),
                    "text/html",
                    "UTF-8",
                    null
                )
                webViews_abs_news.setWebViewClient(object : WebViewClient() {
                    val loadProgressDialog =
                        LoadProgressDialog(this@AdvertisonFindDetailedActivity, "数据加载中……")

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
        favorites_btns_delet.setOnClickListener {
            deleFavoritesImage()
        }
    }

    /**
     * 收藏
     */
//    fun favoritesImage() {
//        val sp = application.getSharedPreferences("sp", MODE_PRIVATE)
//        val resId = sp.getString("resId", "")
//        mDisposable.add(mApiService.favoritesImages(resId, 1)
//            .compose(RxUtils.schedulersTransformer())
//            .subscribe({ favoritesBean ->
//                Log.d(TAG, "favoritesBean: $favoritesBean")
//                ToastUtil.showToast(application, "收藏成功！")
//                favorites_btn.setVisibility(View.GONE)
//                favorites_btn_delet.setVisibility(View.VISIBLE)
//            }) { throwable ->
//                Log.d(TAG, "loadMore: $throwable")
//            })
//    }

    /**
     * 删除收藏
     */
    private fun deleFavoritesImage() {
        val sp = application.getSharedPreferences("sp", MODE_PRIVATE)
        val resId = sp.getString("resId", null)
        mDisposable.add(mApiService.deleFavoritesImages(resId, 2)
            .compose(RxUtils.schedulersTransformer())
            .subscribe({ favoritesBean ->
                Log.d(TAG, "favoritesBean: $favoritesBean")
                ToastUtil.showToast(application, "取消收藏！")
                favorites_btns_delet.visibility = View.GONE
                finish()
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