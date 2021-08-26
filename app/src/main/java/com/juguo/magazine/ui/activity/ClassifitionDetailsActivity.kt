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
import com.juguo.magazine.adapter.MoreNewRecordAdapter
import com.juguo.magazine.base.BaseActivity
import com.juguo.magazine.bean.CategoryBean
import com.juguo.magazine.bean.PieceBean
import com.juguo.magazine.databinding.FashionMagazineActivityBinding
import com.juguo.magazine.remote.ApiService
import com.juguo.magazine.remote.RetrofitManager
import com.juguo.magazine.viewmodel.FashionMagazineViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fashion_magazine_activity.*
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_fragment.recyclerView
import okhttp3.MediaType
import okhttp3.RequestBody

class ClassifitionDetailsActivity : BaseActivity<FashionMagazineActivityBinding>() {
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
        promPtf()
        LiveEventBus.get("CategoryKey",CategoryBean.Category::class.java)
            .observeSticky(this){
                moreNews(it)
            }
        mBinding.backZhazhi.setOnClickListener { finish() }
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
     * 最新资讯
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
    fun moreNews(bean: CategoryBean.Category) {
        //mutableMapOf 可变的map
        //mapOf 不可变的map
        //不需要重新赋值用mapOf，要重新复制用mutableMapOf
        val map = mapOf( //自动识别类型，不用加 -> :Map<String,Any>
            "order" to "desc",
            "sort" to "add_time",
            "type" to bean.id
        )

        //{"param":{map}}
        val param =  mapOf("param" to map)
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
                price.postValue(pieceBean.price)
            }) { throwable ->
                Log.d(
                    ContentValues.TAG,
                    "loadMore: $throwable"
                )
            })
    }
}