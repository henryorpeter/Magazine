package com.juguo.magazine.ui.fragment

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.*
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import cn.lemon.view.adapter.Action
import com.google.gson.Gson
import com.hjq.permissions.Permission
import com.jeremyliao.liveeventbus.LiveEventBus
import com.juguo.magazine.App
import com.juguo.magazine.R
import com.juguo.magazine.adapter.ClassifitionRecordAdapter
import com.juguo.magazine.base.BaseFragment
import com.juguo.magazine.bean.CategoryBean
import com.juguo.magazine.databinding.MagazineFragmentBinding
import com.juguo.magazine.remote.ApiService
import com.juguo.magazine.remote.RetrofitManager
import com.juguo.magazine.ui.activity.ClassifitionDetailsActivity
import com.juguo.magazine.util.LoadProgressDialog
import com.juguo.magazine.viewmodel.MagazineViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.magazine_fragment.*
import okhttp3.MediaType
import okhttp3.RequestBody
import com.hjq.permissions.XXPermissions
import com.juguo.magazine.ui.activity.MainActivity

import com.hjq.permissions.OnPermissionCallback
import com.juguo.magazine.util.ToastUtil


class MagazineFragment : BaseFragment<MagazineFragmentBinding>() {
    override val getLayoutId=R.layout.magazine_fragment
    private  val viewModel: MagazineViewModel by viewModels()
    private lateinit var mClassifitoinAdapter: ClassifitionRecordAdapter
    private lateinit var mHandler: Handler
    private var page = 1
    private val mDisposable = CompositeDisposable()
    var price: MutableLiveData<List<CategoryBean.Category>> = MutableLiveData<List<CategoryBean.Category>>()
    @JvmField
    protected var mApiService = RetrofitManager.getApi(ApiService::class.java) //?????????????????????ApiService????????????????????????
    companion object {
        fun newInstance() = MagazineFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.magazineViewmodel = viewModel // ???????????????viewModel
    }

    override fun initView(savedInstanceState: Bundle?) {
        magazineFormation()
        promPtf()
        Qermissions()
    }

    fun Qermissions(){
        XXPermissions.with(this) // ????????? Android 11 ???????????????
            //.permission(Permission.Group.STORAGE)
            // ?????? Android 11 ???????????????????????????????????? Permission.Group.STORAGE
            .permission(Permission.MANAGE_EXTERNAL_STORAGE)
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: List<String>, all: Boolean) {
                    if (all) {
//                        ToastUtil.showToast(App.sInstance,"????????????????????????")
                    }
                }

                override fun onDenied(permissions: List<String>, never: Boolean) {
                    if (never) {
                        //ToastUtil.showToast(App.sInstance,"???????????????????????????????????????????????????")
                        // ??????????????????????????????????????????????????????????????????
                        XXPermissions.startPermissionActivity(this@MagazineFragment, permissions)
                    } else {
                        //ToastUtil.showToast(App.sInstance,"????????????????????????")
                    }
                }
            })
    }


    fun getData(isRefresh: Boolean) {
        val loadProgressDialog = LoadProgressDialog(mActivity, "?????????????????????").apply { show() }
        mHandler.postDelayed({
            if (isRefresh) {
                page = 1
                mClassifitoinAdapter.clear()
                mClassifitoinAdapter.addAll(price.value)
                recyclerView_fenlei?.apply {
                    dismissSwipeRefresh()
                    recyclerView.scrollToPosition(0)
                }
                loadProgressDialog.dismiss()
            } else if (page == 5) {
                mClassifitoinAdapter.showLoadMoreError()
            } else {
                mClassifitoinAdapter.addAll(price.value)
                if (page >= 11) {
                    recyclerView_fenlei.showNoMore()
                }
            }
        }, 500)
    }

    private fun promPtf() {
        mHandler = Handler(Looper.myLooper()!!)
        mClassifitoinAdapter = ClassifitionRecordAdapter(context)
        recyclerView_fenlei.apply {
            setSwipeRefreshColors(-0xbc87bb, -0x1bb068, -0xd053df)
            setLayoutManager(GridLayoutManager(context, 2))
            setAdapter(mClassifitoinAdapter)
            addRefreshAction{
                Log.e(TAG, "promPtf: addRefreshAction")
                if (mClassifitoinAdapter == null) {
                    getData(true)
                } else {
                    dismissSwipeRefresh() //????????????
                }
            }
            addLoadMoreErrorAction {
                getData(false)
                page++
            }
            addLoadMoreAction{
                if (mClassifitoinAdapter == null) {
                    getData(false)
                } else {
                    showNoMore()
                }
            }
            post {
                Log.e(TAG, "promPtf: addRefreshAction")
                if (!this@MagazineFragment.isHidden) {
                    showSwipeRefresh()
                    getData(true)
                }
            }
        }
        //??????????????????
        mClassifitoinAdapter.setOnItemClickListener { data ->
            val intent = Intent()
            intent.setClass(App.sInstance, ClassifitionDetailsActivity::class.java)
            startActivity(intent)
            LiveEventBus
                .get("CategoryKey",CategoryBean.Category::class.java)
                .post(data)
            Log.i(TAG,"CategoryKey...............")
        }
    }

    /**
     * ??????
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
                    TAG,
                    "loadMore?>>>>>>>>>>>>>>>>>>>>: $pieceBean"
                )
                price.postValue(pieceBean.category)
            }) { throwable ->
                Log.d(
                    TAG,
                    "loadMore: $throwable"
                )
            })
    }

}