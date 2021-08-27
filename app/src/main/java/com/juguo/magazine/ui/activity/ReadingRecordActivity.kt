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
import com.juguo.magazine.adapter.NewRecordAdapter
import com.juguo.magazine.adapter.ReadRecordAdapter
import com.juguo.magazine.base.BaseActivity
import com.juguo.magazine.bean.PieceBean
import com.juguo.magazine.bean.ReadHistoryBean
import com.juguo.magazine.databinding.ReadingRecordActivityBinding
import com.juguo.magazine.remote.ApiService
import com.juguo.magazine.remote.RetrofitManager
import com.juguo.magazine.util.LoadProgressDialog
import com.juguo.magazine.viewmodel.FashionMagazineViewModel
import com.juguo.magazine.viewmodel.ReadingRecordModel
import com.zhpan.bannerview.constants.PageStyle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detailed_news.*
import kotlinx.android.synthetic.main.home_fragment.*
import okhttp3.MediaType
import okhttp3.RequestBody

class ReadingRecordActivity : BaseActivity<ReadingRecordActivityBinding,ReadingRecordModel>() {
    override val getLayoutId = R.layout.reading_record_activity
    private val mViewModel: ReadingRecordModel by viewModels()
    private lateinit var mAdapter: ReadRecordAdapter
    private lateinit var mHandler: Handler
    private var page = 1
    private val mDisposable = CompositeDisposable()
    var price: MutableLiveData<List<ReadHistoryBean.ReadHistory>> = MutableLiveData<List<ReadHistoryBean.ReadHistory>>()
    @JvmField
    protected var mApiService = RetrofitManager.getApi(ApiService::class.java) //初始化请求接口ApiService，给继承的子类用

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        back_zhazhi_xq.setOnClickListener { finish() }
        readingRecord()
        reading()
    }

    /**
     * 阅读记录
     */
    private fun reading() {
        mHandler = Handler(Looper.myLooper()!!)
        mAdapter = ReadRecordAdapter(this)
        recyclerView.setSwipeRefreshColors(-0xbc87bb, -0x1bb068, -0xd053df)
        recyclerView.setLayoutManager(GridLayoutManager(this, 1))
        recyclerView.setAdapter(mAdapter)
        recyclerView.addRefreshAction(Action {
            if (mAdapter == null) {
                getReadingData(true)
            } else {
                recyclerView.dismissSwipeRefresh() //圈圈消失
            }
        })
        recyclerView.addLoadMoreErrorAction(Action {
            getReadingData(false)
            page++
        })
        //上拉加载更多
        recyclerView.addLoadMoreAction(Action {
            if (mAdapter == null) {
                getReadingData(false)
            } else {
                recyclerView.showNoMore()
            }
        })
        recyclerView.post(Runnable {
            recyclerView.showSwipeRefresh()
            getReadingData(true)
        })
        mAdapter.setOnItemClickListener { data ->
            val intent = Intent()
            intent.setClass(App.sInstance, ReadHistoryNewsActivity::class.java)
            startActivity(intent)
            LiveEventBus
                .get("ReadHistoryBean",ReadHistoryBean.ReadHistory::class.java)
                .post(data)
        }
    }


    fun getReadingData(isRefresh: Boolean) {
        val loadProgressDialog = LoadProgressDialog(this, "数据加载中……")
        loadProgressDialog.show()
        mHandler.postDelayed(Runnable {
            if (isRefresh) {
                page = 1
                mAdapter.clear()
                mAdapter.addAll(price.value)
                recyclerView.dismissSwipeRefresh()
                recyclerView.getRecyclerView().scrollToPosition(0)
                loadProgressDialog.dismiss()
            } else if (page == 5) {
                //加载错误
                mAdapter.showLoadMoreError()
            } else {
                mAdapter.addAll(price.value)
                if (page >= 11) {
                    recyclerView.showNoMore()
                }
            }
        }, 500)
    }


    fun readingRecord() {
        val map: MutableMap<String, Any> = mutableMapOf(
            "order" to "desc",
            "sort" to "add_time",
        )
        //{"param":{map}}
        val param: MutableMap<String, Any> =  mutableMapOf("param" to map)
        val body = RequestBody.create(
            MediaType.get("application/json; charset=utf-8"),
            Gson().toJson(param)
        )
        mDisposable.add(mApiService.getReadHistory(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ ReadHistoryBean ->
                Log.d(
                    ContentValues.TAG,
                    "getReadHistory***************: ${ReadHistoryBean}"
                )
                price.value = ReadHistoryBean.list
            }) { throwable ->
                Log.d(
                    ContentValues.TAG,
                    "loadMore: $throwable"
                )
            })
    }


}