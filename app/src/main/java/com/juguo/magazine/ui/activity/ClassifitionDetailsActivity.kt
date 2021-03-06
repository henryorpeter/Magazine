package com.juguo.magazine.ui.activity

import android.Manifest
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
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.jeremyliao.liveeventbus.LiveEventBus
import com.juguo.magazine.App
import com.juguo.magazine.R
import com.juguo.magazine.adapter.MoreNewRecordAdapter
import com.juguo.magazine.base.BaseActivity
import com.juguo.magazine.bean.CategoryBean
import com.juguo.magazine.bean.PieceBean
import com.juguo.magazine.databinding.FashionMagazineActivityBinding
import com.juguo.magazine.event.WX_APP_ID
import com.juguo.magazine.remote.ApiService
import com.juguo.magazine.remote.RetrofitManager
import com.juguo.magazine.util.RxUtils
import com.juguo.magazine.util.ToastUtil
import com.juguo.magazine.viewmodel.FashionMagazineViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fashion_magazine_activity.*
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_fragment.recyclerView
import okhttp3.MediaType
import okhttp3.RequestBody

class ClassifitionDetailsActivity : BaseActivity<FashionMagazineActivityBinding, Any?>() {
    override val getLayoutId = R.layout.fashion_magazine_activity
    private val mViewModel: FashionMagazineViewModel by viewModels()
    private lateinit var mAdapter: MoreNewRecordAdapter
    private lateinit var mHandler: Handler
    private var page = 1
    private val mDisposable = CompositeDisposable()
    var price: MutableLiveData<List<PieceBean.Price>> = MutableLiveData<List<PieceBean.Price>>()
    @JvmField
    protected var mApiService = RetrofitManager.getApi(ApiService::class.java) //?????????????????????ApiService????????????????????????
    override fun onViewCreate(savedInstanceState: Bundle?) {
        super.onViewCreate(savedInstanceState)
        mBinding.fashionMagazineViewmodel = mViewModel //???????????????viewmodel
        AdvertisingSwitch()
        LiveEventBus.get("CategoryKey",CategoryBean.Category::class.java)
            .observeSticky(this){
                moreNews(it)
                tv_title_op.setText(it.name)
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
     * ????????????
     */
    fun AdvertisingSwitch(){
        mDisposable.add(mApiService.getAppIdAdvertise(WX_APP_ID)
            .compose(RxUtils.schedulersTransformer())
            .subscribe({ privacyBean ->
                Log.d(ContentValues.TAG, "<<<<<<<<<<AdvertisingSwitch>>>>>>>>>>>>: $privacyBean")
                val startAdFlag: String = privacyBean.getResult().getStartAdFlag()
                if ("NONE" == startAdFlag) {
                    advertisePromPtf()
                } else if ("CSJ" == startAdFlag) {
                    promPtf()
                } else if ("SYS" == startAdFlag) {

                }
            }) { throwable -> Log.d(ContentValues.TAG, "loadMore: $throwable") })
    }


    /**
     * ????????????
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
                recyclerView.dismissSwipeRefresh() //????????????
            }
        })
        recyclerView.addLoadMoreErrorAction(Action {
            getData(false)
            page++
        })
        //??????????????????
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
     * g??????????????????
     */
    private fun advertisePromPtf() {
        mHandler = Handler(Looper.myLooper()!!)
        mAdapter = MoreNewRecordAdapter(this)
        recyclerView.setSwipeRefreshColors(-0xbc87bb, -0x1bb068, -0xd053df)
        recyclerView.setLayoutManager(GridLayoutManager(this, 2))
        recyclerView.setAdapter(mAdapter)
        recyclerView.addRefreshAction(Action {
            if (mAdapter == null) {
                getData(true)
            } else {
                recyclerView.dismissSwipeRefresh() //????????????
            }
        })
        recyclerView.addLoadMoreErrorAction(Action {
            getData(false)
            page++
        })
        //??????????????????
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
            intent.setClass(App.sInstance, AdvertiseDetailedNewsActivity::class.java)
            startActivity(intent)
            LiveEventBus
                .get(PieceBean.Price::class.java)
                .post(data)
        }
    }

    /**
     * ??????????????????????????????
     */
    fun moreNews(bean: CategoryBean.Category) {
        //mutableMapOf ?????????map
        //mapOf ????????????map
        //????????????????????????mapOf?????????????????????mutableMapOf
        val map = mapOf( //?????????????????????????????? -> :Map<String,Any>
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