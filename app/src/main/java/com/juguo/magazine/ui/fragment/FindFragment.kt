package com.juguo.magazine.ui.fragment

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import cn.lemon.view.adapter.Action
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.jeremyliao.liveeventbus.LiveEventBus
import com.juguo.magazine.App
import com.juguo.magazine.R
import com.juguo.magazine.adapter.FindRecordAdapter
import com.juguo.magazine.base.BaseFragment
import com.juguo.magazine.bean.FavoritesListBean
import com.juguo.magazine.databinding.FindFragmentBinding
import com.juguo.magazine.remote.ApiService
import com.juguo.magazine.remote.RetrofitManager
import com.juguo.magazine.ui.activity.FindDetailedActivity
import com.juguo.magazine.util.LoadProgressDialog
import com.juguo.magazine.viewmodel.FindViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.find_fragment.*
import okhttp3.MediaType
import okhttp3.RequestBody

class FindFragment : BaseFragment<FindFragmentBinding>() {
    override val getLayoutId = R.layout.find_fragment
    private val viewModel: FindViewModel by viewModels()
    private lateinit var mHotAdapter: FindRecordAdapter
    private lateinit var mHandler: Handler
    private var page = 1
    private val mDisposable = CompositeDisposable()
    private val favorites: MutableLiveData<List<FavoritesListBean.Favorites>> =
        MutableLiveData<List<FavoritesListBean.Favorites>>()

    @JvmField
    protected var mApiService =
        RetrofitManager.getApi(ApiService::class.java) //初始化请求接口ApiService，给继承的子类用

    companion object {
        fun newInstance() = FindFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.findViewmodel = viewModel // 绑定布局的viewModel
    }

    override fun initView(savedInstanceState: Bundle?) {
        viewModel.favoritesList()
        favoritesList()
        hotPromPtf()
        viewModel.favorites.observe(this) {
            Log.e(TAG, "favorites: 数据回调")
            mHotAdapter.clear()
            mHotAdapter.addAll(it)
            mHotAdapter.notifyDataSetChanged()
        }
    }

    fun getData(isRefresh: Boolean) {
        val loadProgressDialog = LoadProgressDialog(context, "数据加载中……")
        loadProgressDialog.show()
        mHandler.postDelayed(Runnable {
            if (isRefresh) {
                page = 1
                //收藏
                mHotAdapter.clear()
                mHotAdapter.addAll(favorites.value)
                //收藏
                recyclerView_find.dismissSwipeRefresh()
                recyclerView_find.recyclerView.scrollToPosition(0)
                loadProgressDialog.dismiss()
            } else if (page == 5) {
                mHotAdapter.showLoadMoreError()
            } else {
                mHotAdapter.addAll(favorites.value)
                if (page >= 11) {
                    recyclerView_find.showNoMore()
                }
            }
        }, 500)
    }

    private val TAG = "FindFragment"
    private fun hotPromPtf() {
        mHandler = Handler(Looper.myLooper()!!)
        mHotAdapter = FindRecordAdapter(context)
        recyclerView_find.setSwipeRefreshColors(-0xbc87bb, -0x1bb068, -0xd053df)
        recyclerView_find.setLayoutManager(GridLayoutManager(context, 1))
        recyclerView_find.setAdapter(mHotAdapter)
        recyclerView_find.addRefreshAction(Action {
            Log.e(TAG, "addRefreshAction: ")
            if (mHotAdapter == null) {
                getData(true)
            } else {
                recyclerView_find.dismissSwipeRefresh() //圈圈消失
            }
            viewModel.favoritesList()
        })
        recyclerView_find.addLoadMoreErrorAction(Action {
            Log.e(TAG, "addLoadMoreErrorAction: ")
            getData(false)
            page++
        })
        //上拉加载更多
        recyclerView_find.addLoadMoreAction(Action {
            Log.e(TAG, "addLoadMoreAction: ")
            if (mHotAdapter == null) {
                getData(false)
            } else {
                recyclerView_find.showNoMore()
            }
        })
        recyclerView_find.post(Runnable {
            Log.e(TAG, "post: ")
            recyclerView_find.showSwipeRefresh()
            getData(true)
        })

        mHotAdapter.setOnItemClickListener { data ->
            val intent = Intent()
            intent.setClass(App.sInstance, FindDetailedActivity::class.java)
            startActivity(intent)
            LiveEventBus
                .get("favoritesKey", FavoritesListBean.Favorites::class.java)
                .post(data)
        }
    }

    fun favoritesList() {
        val map = mutableMapOf(
            "order" to "desc",
            "sort" to "add_time",
            "pageNum" to 1,
            "pageSize" to 10
        )
        //{"param":{map}}
        val param: MutableMap<String, Any> = mutableMapOf("param" to map)
        val body = RequestBody.create(
            MediaType.get("application/json; charset=utf-8"),
            Gson().toJson(param)
        )
        mDisposable.add(mApiService.favoritesListBean(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ favoritesListBean -> //刷新的请求，用postValue()
                favorites.value = favoritesListBean.favorites
                //                    favorites.postValue(favoritesListBean.favorites)
            }) { throwable ->
                Log.d(ContentValues.TAG, "loadMore: $throwable")
            })
    }


}