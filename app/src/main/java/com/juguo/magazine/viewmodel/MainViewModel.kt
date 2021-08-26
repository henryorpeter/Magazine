package com.juguo.magazine.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.juguo.getRequestBody
import com.juguo.magazine.App
import com.juguo.magazine.event.WX_APP_ID
import com.juguo.magazine.util.DeviceIdUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MainViewModel : BaseViewModel() {
    /**
     * 登录请求
     */
    fun Requestlogin() {
        val map: MutableMap<String, Any> = mutableMapOf(
            "appId" to WX_APP_ID,
            "type" to 2,
            "unionInfo" to DeviceIdUtil.getDeviceUUID()
        )
        //{"param":{map}}
        val param: MutableMap<String, Any> =  mutableMapOf("param" to map)

        val body = Gson().toJson(param).getRequestBody()
        addDisposable(
            mApiService.login(body) // 添加rxjava请求到绑定容器
                //                .doOnSubscribe(disposable -> MainViewModel.this.getLoadingLiveData().getShowDialog())
                .subscribeOn(Schedulers.io()) // io线程执行请求
                .observeOn(AndroidSchedulers.mainThread()) // 主线程返回结果
                .subscribe({
                    Log.i(TAG, "Login_>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + Gson().toJson(it))
                    //保存result_token
                    val sp: SharedPreferences =
                        App.sInstance.getSharedPreferences("sp", Context.MODE_PRIVATE)
                    val editor: SharedPreferences.Editor = sp.edit()
                    editor.putString("result_token", it.getResult()).apply()
                    //                        getLoadingLiveData().getDismissDialog().setValue(false);
                }, { e: Throwable -> Log.e(TAG, "onFailure" + e.message) })
        )
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}