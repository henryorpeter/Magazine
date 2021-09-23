package com.juguo.magazine.ui.activity

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import cn.lemon.view.adapter.Action
import com.google.gson.Gson
import com.jeremyliao.liveeventbus.LiveEventBus
import com.juguo.magazine.App
import com.juguo.magazine.R
import com.juguo.magazine.adapter.HotRecordAdapter
import com.juguo.magazine.adapter.MoreNewRecordAdapter
import com.juguo.magazine.adapter.NewRecordAdapter
import com.juguo.magazine.base.BaseActivity
import com.juguo.magazine.bean.PieceBean
import com.juguo.magazine.databinding.FashionMagazineActivityBinding
import com.juguo.magazine.event.WX_APP_ID
import com.juguo.magazine.remote.ApiService
import com.juguo.magazine.remote.RetrofitManager
import com.juguo.magazine.util.RxUtils
import com.juguo.magazine.viewmodel.FashionMagazineViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fashion_magazine_activity.*
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_fragment.recyclerView
import okhttp3.MediaType
import okhttp3.RequestBody

class FashionMagazineActivity : BaseActivity<FashionMagazineActivityBinding, Any?>() {
    override val getLayoutId = R.layout.fashion_magazine_activity
    private val mViewModel: FashionMagazineViewModel by viewModels()
    private lateinit var mAdapter: MoreNewRecordAdapter
    private lateinit var mHandler: Handler
    private var page = 1
    private val mDisposable = CompositeDisposable()
    var price: MutableLiveData<List<PieceBean.Price>> = MutableLiveData<List<PieceBean.Price>>()
    @JvmField
    protected var mApiService = RetrofitManager.getApi(ApiService::class.java) //初始化请求接口ApiService，给继承的子类用
    override fun onViewCreate(savedInstanceState: Bundle?) {
        super.onViewCreate(savedInstanceState)
        mBinding.fashionMagazineViewmodel = mViewModel //绑定布局的viewmodel
        moreNews()
        AdvertisingSwitch()
        back_zhazhi.setOnClickListener { finish() }
    }

    fun getData(isRefresh: Boolean) {
        mHandler.postDelayed(Runnable {
            if (isRefresh) {
                page = 1
                mAdapter.clear()
                mAdapter.addAll(price.getValue())
                recyclerView.dismissSwipeRefresh()
                recyclerView.getRecyclerView().scrollToPosition(0)
            } else if (page == 5) {
                mAdapter.showLoadMoreError()
            } else {
                mAdapter.addAll(price.getValue())
                if (page >= 11) {
                    recyclerView.showNoMore()
                }
            }
        }, 500)
    }

    /**
     * 有哥广告时最新资讯
     */
    private fun promPtf() {
        mHandler = Handler(Looper.myLooper()!!)
        mAdapter = MoreNewRecordAdapter(this)
        recyclerView.setSwipeRefreshColors(-0xbc87bb, -0x1bb068, -0xd053df)
        recyclerView.setLayoutManager(GridLayoutManager(this, 2))
        recyclerView.setAdapter(mAdapter)
        recyclerView.addRefreshAction(Action {
            if (mAdapter == null) {
                getData(true)
            } else {
                recyclerView.dismissSwipeRefresh() //圈圈消失
            }
        })
        recyclerView.addLoadMoreErrorAction(Action {
            getData(false)
            page++
        })
        //上拉加载更多
        recyclerView.addLoadMoreAction(Action {
            if (mAdapter == null) {
                getData(false)
            } else {
                recyclerView.showNoMore()
            }
        })
        recyclerView.post(Runnable {
            recyclerView.showSwipeRefresh()
            getData(true)
        })
        mAdapter.setOnItemClickListener { data ->
            val intent = Intent()
            intent.setClass(App.sInstance, DetailedNewsActivity::class.java)
            startActivity(intent)
            LiveEventBus
                .get(PieceBean.Price::class.java)
                .post(data)
        }
    }

    /**
     * 最新资讯
     */
    private fun AdvertisingpromPtf() {
        mHandler = Handler(Looper.myLooper()!!)
        mAdapter = MoreNewRecordAdapter(this)
        recyclerView.setSwipeRefreshColors(-0xbc87bb, -0x1bb068, -0xd053df)
        recyclerView.setLayoutManager(GridLayoutManager(this, 2))
        recyclerView.setAdapter(mAdapter)
        recyclerView.addRefreshAction(Action {
            if (mAdapter == null) {
                getData(true)
            } else {
                recyclerView.dismissSwipeRefresh() //圈圈消失
            }
        })
        recyclerView.addLoadMoreErrorAction(Action {
            getData(false)
            page++
        })
        //上拉加载更多
        recyclerView.addLoadMoreAction(Action {
            if (mAdapter == null) {
                getData(false)
            } else {
                recyclerView.showNoMore()
            }
        })
        recyclerView.post(Runnable {
            recyclerView.showSwipeRefresh()
            getData(true)
        })
        mAdapter.setOnItemClickListener { data ->
            val intent = Intent()
            intent.setClass(App.sInstance, NewsVideoPlayActivity::class.java)
            startActivity(intent)
            LiveEventBus
                .get(PieceBean.Price::class.java)
                .post(data)
        }
    }

    /**
     * 广告开关
     */
    fun AdvertisingSwitch(){
        mDisposable.add(mApiService.getAppIdAdvertise(WX_APP_ID)
            .compose(RxUtils.schedulersTransformer())
            .subscribe({ privacyBean ->
                Log.d(ContentValues.TAG, "<<<<<<<<<<AdvertisingSwitch>>>>>>>>>>>>: $privacyBean")
                val startAdFlag: String = privacyBean.getResult().getStartAdFlag()
                if ("NONE" == startAdFlag) {
                    AdvertisingpromPtf()
                } else if ("CSJ" == startAdFlag) {
                    promPtf()
                } else if ("SYS" == startAdFlag) {

                }
            }) { throwable -> Log.d(ContentValues.TAG, "loadMore: $throwable") })
    }

    /**
     * 最新资讯
     */
    fun moreNews() {
        val map: MutableMap<String, Any> = mutableMapOf(
            "order" to "desc",
            "sort" to "add_time",
            "type" to 314
        )
        //{"param":{map}}
        val param: MutableMap<String, Any> =  mutableMapOf("param" to map)
        val body = RequestBody.create(
            MediaType.get("application/json; charset=utf-8"),
            Gson().toJson(param)
        )
        mDisposable.add(mApiService.getList(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ pieceBean ->
                Log.d(
                    ContentValues.TAG,
                    "loadMore: $pieceBean"
                )
                price.postValue(pieceBean.getPrice())
            }) { throwable ->
                Log.d(
                    ContentValues.TAG,
                    "loadMore: $throwable"
                )
            })
    }

}