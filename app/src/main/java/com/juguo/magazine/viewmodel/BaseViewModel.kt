package com.juguo.magazine.viewmodel

import androidx.lifecycle.ViewModel
import com.juguo.magazine.event.SingleLiveEvent
import com.juguo.magazine.remote.ApiService
import com.juguo.magazine.remote.RetrofitManager.getApi
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {
    private var mDisposable = CompositeDisposable()

    private var mLoadingLiveData // 显示加载框用的
            =LoadingLiveData()
    @JvmField
    protected var mApiService = getApi(ApiService::class.java) //初始化请求接口ApiService，给继承的子类用

    // 将rxjava请求添加到CompositeDisposable，等viewmodel生命周期结束就自动解除绑定
    protected fun addDisposable(disposable: Disposable) {
        mDisposable.add(disposable)
    }



    override fun onCleared() {
        super.onCleared()

        // rxjava解除绑定，然后就不会内存泄露啦啦啦
/*        if (mDisposable != null) {
            mDisposable!!.dispose()
        }*/

        mDisposable.dispose()
    }

    /**
     * 网络请求弹出加载和关闭
     */
    inner class LoadingLiveData : SingleLiveEvent<Any?>() {
        var showDialog: SingleLiveEvent<String>? = null
            get() {
                if (field == null) {
                    field = SingleLiveEvent()
                }
                return field
            }
            private set
        var dismissDialog: SingleLiveEvent<Boolean>? = null
            get() {
                if (field == null) {
                    field = SingleLiveEvent()
                }
                return SingleLiveEvent<Boolean>().also { field = it }
            }
            private set
    }
}