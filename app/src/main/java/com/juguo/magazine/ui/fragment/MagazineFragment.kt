package com.juguo.magazine.ui.fragment

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import cn.lemon.view.adapter.Action
import com.google.gson.Gson
import com.jeremyliao.liveeventbus.LiveEventBus
import com.juguo.magazine.App
import com.juguo.magazine.R
import com.juguo.magazine.adapter.ClassifitionRecordAdapter
import com.juguo.magazine.base.BaseFragment
import com.juguo.magazine.bean.CategoryBean
import com.juguo.magazine.bean.PieceBean
import com.juguo.magazine.databinding.MagazineFragmentBinding
import com.juguo.magazine.remote.ApiService
import com.juguo.magazine.remote.RetrofitManager
import com.juguo.magazine.ui.activity.ClassifitionDetailsActivity
import com.juguo.magazine.ui.activity.DetailedNewsActivity
import com.juguo.magazine.ui.activity.FashionMagazineActivity
import com.juguo.magazine.viewmodel.MagazineViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.magazine_fragment.*
import okhttp3.MediaType
import okhttp3.RequestBody

class MagazineFragment : BaseFragment<MagazineFragmentBinding>() {
    override val getLayoutId=R.layout.magazine_fragment
    private  val viewModel: MagazineViewModel by viewModels()
    private lateinit var mClassifitoinAdapter: ClassifitionRecordAdapter
    private lateinit var mHandler: Handler
    private var page = 1
    private val mDisposable = CompositeDisposable()
    var price: MutableLiveData<List<CategoryBean.Category>> = MutableLiveData<List<CategoryBean.Category>>()
    @JvmField
    protected var mApiService = RetrofitManager.getApi(ApiService::class.java) //初始化请求接口ApiService，给继承的子类用
    companion object {
        fun newInstance() = MagazineFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.magazineViewmodel = viewModel // 绑定布局的viewModel
    }

    override fun initView(savedInstanceState: Bundle?) {
        promPtf()
        magazineFormation()
    }

    fun getData(isRefresh: Boolean) {
        mHandler.postDelayed(Runnable {
            if (isRefresh) {
                page = 1
                mClassifitoinAdapter.clear()
                mClassifitoinAdapter.addAll(price.getValue())
                recyclerView_fenlei.dismissSwipeRefresh()
                recyclerView_fenlei.getRecyclerView().scrollToPosition(0)
            } else if (page == 5) {
                mClassifitoinAdapter.showLoadMoreError()
            } else {
                mClassifitoinAdapter.addAll(price.getValue())
                if (page >= 11) {
                    recyclerView_fenlei.showNoMore()
                }
            }
        }, 500)
    }

    private fun promPtf() {
        mHandler = Handler(Looper.myLooper()!!)
        mClassifitoinAdapter = ClassifitionRecordAdapter(context)
        recyclerView_fenlei.setSwipeRefreshColors(-0xbc87bb, -0x1bb068, -0xd053df)
        recyclerView_fenlei.setLayoutManager(GridLayoutManager(context, 2))
        recyclerView_fenlei.setAdapter(mClassifitoinAdapter)
        recyclerView_fenlei.addRefreshAction(Action {
            if (mClassifitoinAdapter == null) {
                getData(true)
            } else {
                recyclerView_fenlei.dismissSwipeRefresh() //圈圈消失
            }
        })
        recyclerView_fenlei.addLoadMoreErrorAction(Action {
            getData(false)
            page++
        })
        //上拉加载更多
        recyclerView_fenlei.addLoadMoreAction(Action {
            if (mClassifitoinAdapter == null) {
                getData(false)
            } else {
                recyclerView_fenlei.showNoMore()
            }
        })
        recyclerView_fenlei.post(Runnable {
            recyclerView_fenlei.showSwipeRefresh()
            getData(true)
        })
        mClassifitoinAdapter.setOnItemClickListener { data ->
            val intent = Intent()
            intent.setClass(App.getContext(), ClassifitionDetailsActivity::class.java)
            startActivity(intent)
            LiveEventBus
                .get("CategoryKey",CategoryBean.Category::class.java)
                .post(data)
            Log.i(TAG,"")
        }
    }

    /**
     * 分类
     */
    fun magazineFormation() {
        val map: MutableMap<String, Any> = mutableMapOf(
            "pageNum" to 0,
            "pageSize" to 0,
            "code" to "res.magazine"
        )
        //{"param":{map}}
        val param: MutableMap<String, Any> =  mutableMapOf("param" to map)
        val body = RequestBody.create(
            MediaType.get("application/json; charset=utf-8"),
            Gson().toJson(param)
        )
        mDisposable.add(mApiService.getCategory(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ pieceBean ->
                Log.d(
                    ContentValues.TAG,
                    "loadMore?>>>>>>>>>>>>>>>>>>>>: $pieceBean"
                )
                price.postValue(pieceBean.category)
            }) { throwable ->
                Log.d(
                    ContentValues.TAG,
                    "loadMore: $throwable"
                )
            })
    }

}