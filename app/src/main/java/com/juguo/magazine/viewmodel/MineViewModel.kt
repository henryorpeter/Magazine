package com.juguo.magazine.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.juguo.getRequestBody
import com.juguo.magazine.App
import com.juguo.magazine.R
import com.juguo.magazine.event.WX_APP_ID
import com.juguo.magazine.util.CommUtils
import com.juguo.magazine.util.DeviceIdUtil
import com.juguo.magazine.util.ToastUtil
import constant.UiType
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import model.UiConfig
import model.UpdateConfig
import okhttp3.MediaType
import okhttp3.RequestBody
import update.UpdateAppUtils
import java.util.*

class MineViewModel : BaseViewModel() {
    private val TAG = "MineViewModel"

    /**
     * 更新版本
     */
    fun UpdaterApp() {
        val sp: SharedPreferences =
            App.getContext().getSharedPreferences("sp", Context.MODE_PRIVATE)
        val uuid_token = sp.getString("uuid_token", "") // 拼接的url参数

        val map: MutableMap<String, Any> = mutableMapOf(
            "appId" to WX_APP_ID,
            "channel" to "",
            "currentV" to CommUtils.getVersionNumber(App.getContext())
        )
        //{"param":{map}}
        val param: MutableMap<String, Any> = mutableMapOf("param" to map)
        param["param"] = map
        val body = RequestBody.create(
            MediaType.get("application/json; charset=utf-8"),
            Gson().toJson(param)
        )
        addDisposable(
            mApiService.getUpdate(body) // 添加rxjava请求到绑定容器
                //                .doOnSubscribe(disposable -> MainViewModel.this.getLoadingLiveData().getShowDialog())
                .subscribeOn(Schedulers.io()) // io线程执行请求
                .observeOn(AndroidSchedulers.mainThread()) // 主线程返回结果
                .subscribe({ versionUpdateBean ->
                    Log.e(TAG, "onFailure" + versionUpdateBean.getResult().getUrl())
                    if (versionUpdateBean.getResult().getUpdateV() != null) {
                        if (!TextUtils.isEmpty(versionUpdateBean.getResult().getUrl())) {
                            //保存result_token
                            val sp: SharedPreferences = App.getContext()
                                .getSharedPreferences("apkUrl", Context.MODE_PRIVATE)
                            val editor = sp.edit()
                            editor.putString("apkUrl", versionUpdateBean.getResult().getUrl())
                                .apply()
                            val desc: String = versionUpdateBean.getResult().getVRemark()
                            val vIfForceUpd: String = versionUpdateBean.getResult().getVType()
                            val uiConfig = UiConfig()
                            uiConfig.uiType
                            uiConfig.customLayoutId.apply { R.layout.view_update_version }
                            val updateConfig = UpdateConfig()
                            // 是否强制更新
                            if ("1" == vIfForceUpd) {
                                updateConfig.force
                            } else {
                                updateConfig.force
                            }
                            UpdateAppUtils
                                .getInstance()
                                .apkUrl(versionUpdateBean.getResult().getUrl())
                                .uiConfig(uiConfig)
                                .updateConfig(updateConfig)
                                .update()
                        } else {
                            ToastUtil.showLongToast(App.getContext(), "已经是最新版本")
                        }
                    } else {
                        ToastUtil.showLongToast(App.getContext(), "已经是最新版本")
                    }
                },
                    Consumer<Throwable> { throwable -> Log.d(TAG, "loadMore: $throwable") })
        )
    }
}