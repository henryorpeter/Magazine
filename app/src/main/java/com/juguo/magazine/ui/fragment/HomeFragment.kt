package com.juguo.magazine.ui.fragment

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.View.GONE
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import cn.lemon.view.adapter.Action
import com.google.gson.Gson
import com.jeremyliao.liveeventbus.LiveEventBus
import com.juguo.magazine.App
import com.juguo.magazine.R
import com.juguo.magazine.adapter.*
import com.juguo.magazine.base.BaseFragment
import com.juguo.magazine.bean.PieceBean
import com.juguo.magazine.databinding.HomeFragmentBinding
import com.juguo.magazine.remote.ApiService
import com.juguo.magazine.remote.RetrofitManager
import com.juguo.magazine.ui.activity.DetailedNewsActivity
import com.juguo.magazine.ui.activity.FashionMagazineActivity
import com.juguo.magazine.util.LoadProgressDialog
import com.juguo.magazine.viewmodel.HomeViewModel
import com.kingja.loadsir.core.LoadService
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.annotation.APageStyle
import com.zhpan.bannerview.constants.PageStyle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.home_fragment.*
import okhttp3.MediaType
import okhttp3.RequestBody

class HomeFragment : BaseFragment<HomeFragmentBinding>() {
    //界面状态管理者
    private lateinit var loadsir: LoadService<Any>
    private lateinit var mViewPager: BannerViewPager<PieceBean.Price>
    override val getLayoutId = R.layout.home_fragment
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var mAdapter: NewRecordAdapter
    private lateinit var mHotAdapter: HotRecordAdapter
    private lateinit var mHandler: Handler
    private var page = 1
    private val mDisposable = CompositeDisposable()
    var price: MutableLiveData<List<PieceBean.Price>> = MutableLiveData<List<PieceBean.Price>>()
    var priceNew: MutableLiveData<List<PieceBean.Price>> = MutableLiveData<List<PieceBean.Price>>()
    @JvmField
    protected var mApiService = RetrofitManager.getApi(ApiService::class.java) //初始化请求接口ApiService，给继承的子类用

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.homeViewmodel = viewModel // 绑定布局的viewModel
    }

    override fun initView(savedInstanceState: Bundle?) {
        mViewPager = mBinding.bannerView as BannerViewPager<PieceBean.Price>
        initBVP()
        onClick()

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            bannerNews()
            promPtf()
            moreNews()
            hotInformation()
            hotPromPtf()
        }, 200)

        //初始化 SwipeRefreshLayout
        swipeRefresh.init {
            //触发刷新监听时请求数据
            bannerNews()
            promPtf()
            moreNews()
            hotInformation()
            hotPromPtf()
            swipeRefresh.setRefreshing(false)
        }
    }

    //初始化 SwipeRefreshLayout
    fun SwipeRefreshLayout.init(onRefreshListener: () -> Unit) {
        this.run {
            setOnRefreshListener {
                onRefreshListener.invoke()
            }
        }
    }

    private fun initBVP() {
        mViewPager.apply {
            setLifecycleRegistry(lifecycle)
            setIndicatorVisibility(GONE)   //banner小圆点不可见
            adapter = HomeLoopAdapter().apply {
                onItemDataCallback={ _, data ->
                    itemClick(data)
                }
            }
            //setOnPageClickListener { _ , pieceBean -> itemClick(pieceBean)}不用设置参数类型

            setInterval(4000)
        }

    }

    /**
     * Different page styles can be implement by use [BannerViewPager.setPageStyle] and
     * [BannerViewPager.setRevealWidth]
     * @param pageStyle Optional params [PageStyle.MULTI_PAGE_SCALE] and [PageStyle.MULTI_PAGE_OVERLAP]
     * @param revealWidth In the multi-page mode, The exposed width of the items on the left and right sides
     */
    private fun setupBanner(@APageStyle pageStyle: Int, revealWidth: Int) {
        setupBanner(pageStyle, revealWidth, revealWidth)
    }

    private fun setupBanner(
        @APageStyle pageStyle: Int,
        leftRevealWidth: Int,
        rightRevealWidth: Int) {
        mViewPager
            .setPageMargin(resources.getDimensionPixelOffset(R.dimen.dp_50))
            .setScrollDuration(800)
            .setRevealWidth(leftRevealWidth, rightRevealWidth)
            .setPageStyle(pageStyle)
            .create(priceNew.value) //List<T> data == List<PieceBean.Price>

    }

    private fun itemClick(bean: PieceBean.Price) {
        val intent = Intent()
        intent.setClass(App.sInstance, DetailedNewsActivity::class.java)
        startActivity(intent)
        LiveEventBus
            .get(PieceBean.Price::class.java)
            .post(bean)
    }

    private fun onClick(){
        more_new.setOnClickListener {
            val intent = Intent()
            intent.setClass(App.sInstance, FashionMagazineActivity::class.java)
            startActivity(intent)
        }
    }

    fun getNewsData(isRefresh: Boolean) {
        val loadProgressDialog = LoadProgressDialog(context, "数据加载中……")
        loadProgressDialog.show()
        mHandler.postDelayed(Runnable {
            if (isRefresh) {
                page = 1
                mAdapter.clear()
                mAdapter.addAll(priceNew.getValue())
                recyclerView.dismissSwipeRefresh()
                recyclerView.getRecyclerView().scrollToPosition(0)
                loadProgressDialog.dismiss()
            } else if (page == 5) {
                //加载错误
                mAdapter.showLoadMoreError()
            } else {
                mAdapter.addAll(priceNew.getValue())
                if (page >= 11) {
                    recyclerView.showNoMore()
                }
            }
        }, 500)
    }

    fun getData(isRefresh: Boolean) {
        val loadProgressDialog = LoadProgressDialog(context, "数据加载中……")
        loadProgressDialog.show()
        mHandler.postDelayed(Runnable {
            if (isRefresh) {
                page = 1
                //热门
                mHotAdapter.clear()
                mHotAdapter.addAll(price.getValue())
                //热门
                recyclerView_tuijian.dismissSwipeRefresh()
                recyclerView_tuijian.getRecyclerView().scrollToPosition(0)
                loadProgressDialog.dismiss()
            } else if (page == 5) {
                mHotAdapter.showLoadMoreError()
            } else {
                mHotAdapter.addAll(price.getValue())
                if (page >= 11) {
                    recyclerView_tuijian.showNoMore()
                }
            }
        }, 500)
    }


    /**
     * 最新资讯
     */
    private fun promPtf() {
        mHandler = Handler(Looper.myLooper()!!)
        mAdapter = NewRecordAdapter(context)
        recyclerView.setSwipeRefreshColors(-0xbc87bb, -0x1bb068, -0xd053df)
        recyclerView.setLayoutManager(GridLayoutManager(context, 3))
        recyclerView.setAdapter(mAdapter)
        recyclerView.addRefreshAction(Action {
            if (mAdapter == null) {
                getNewsData(true)
            } else {
                recyclerView.dismissSwipeRefresh() //圈圈消失
            }
        })
        recyclerView.addLoadMoreErrorAction(Action {
            getNewsData(false)
            page++
        })
        //上拉加载更多
        recyclerView.addLoadMoreAction(Action {
            if (mAdapter == null) {
                getNewsData(false)
            } else {
                recyclerView.showNoMore()
            }
        })
        recyclerView.post(Runnable {
            recyclerView.showSwipeRefresh()
            getNewsData(true)
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
    fun bannerNews() {
        val map: MutableMap<String, Any> = mutableMapOf(
            "order" to "desc",
            "sort" to "add_time",
            "type" to 315
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
                priceNew.value =pieceBean.price//主线程用setValue

                setupBanner(
                    PageStyle.MULTI_PAGE_SCALE,
                    resources.getDimensionPixelOffset(R.dimen.dp_53)
                )
            }) { throwable ->
                Log.d(
                    ContentValues.TAG,
                    "loadMore: $throwable"
                )
            })
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
                    priceNew.value =pieceBean.price//主线程用setValue
                }) { throwable ->
                    Log.d(
                        ContentValues.TAG,
                        "loadMore: $throwable"
                    )
                })
    }

    /**
     * 热门资讯
     */
    private fun hotPromPtf() {
        mHandler = Handler(Looper.myLooper()!!)
        mHotAdapter = HotRecordAdapter(context)
        recyclerView_tuijian.setSwipeRefreshColors(-0xbc87bb, -0x1bb068, -0xd053df)
        recyclerView_tuijian.setLayoutManager(GridLayoutManager(context, 1))
        recyclerView_tuijian.setAdapter(mHotAdapter)
        recyclerView_tuijian.addRefreshAction(Action {
            if (mHotAdapter == null) {
                getData(true)
            } else {
                recyclerView_tuijian.dismissSwipeRefresh() //圈圈消失
            }
        })
        recyclerView_tuijian.addLoadMoreErrorAction(Action {
            getData(false)
            page++
        })
        //上拉加载更多
        recyclerView_tuijian.addLoadMoreAction(Action {
            if (mHotAdapter == null) {
                getData(false)
            } else {
                recyclerView_tuijian.showNoMore()
            }
        })
        recyclerView_tuijian.post(Runnable {
            recyclerView_tuijian.showSwipeRefresh()
            getData(true)
        })
        mHotAdapter.setOnItemClickListener { data ->
            val intent = Intent()
            intent.setClass(App.sInstance, DetailedNewsActivity::class.java)
            startActivity(intent)
            LiveEventBus
                .get(PieceBean.Price::class.java)
                .post(data)
        }
    }

    /**
     * 热门资讯2
     */
    fun hotInformation() {
        val map= mutableMapOf(
            "order" to "desc",
            "sort" to "add_time",
            "type" to 312
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
                price.value = pieceBean.price
            }) { throwable ->
                Log.d(ContentValues.TAG, "loadMore: $throwable")
            })
    }

}