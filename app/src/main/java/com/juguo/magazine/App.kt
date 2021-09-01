package com.juguo.magazine

import android.app.Application
import android.content.SharedPreferences
import com.fenghuajueli.lib_ad.AdShowUtils
import com.fenghuajueli.lib_ad.AdShowUtils.AdConfigBuilder
import com.fenghuajueli.lib_ad.BuildConfig
import com.hjq.permissions.XXPermissions
import com.juguo.magazine.event.CSJ_APP_ID
import com.juguo.magazine.event.CSJ_CHAP_ID
import com.juguo.magazine.event.CSJ_CODE_ID

/**
 *  author : Administrator
 *  date : 2021/8/25 16:45
 *  description :
 * @Author : yangjinjing
 */
class App : Application() {

    companion object {
        lateinit var sInstance: App
    }
    //是否是第一次使用
    private var isFirstUse = false
    var isShowAd = false

    override fun onCreate() {
        super.onCreate()
        sInstance = this
        // 当前项目是否已经适配了分区存储的特性
        XXPermissions.setScopedStorage(true)
        val preferences = getSharedPreferences("isFirstUse", MODE_PRIVATE)
        isFirstUse = preferences.getBoolean("isFirstUse", false)
        if (isFirstUse) {
            ThirdPartySdk()
        }
    }

    fun ThirdPartySdk() {
        //强烈建议在应用对应的Application#onCreate()方法中调用，避免出现content为null的异常
        val config = AdConfigBuilder()
            .setAdAppId(CSJ_APP_ID)
            .setSplashId(CSJ_CODE_ID)
            .setInteractionExpressId(CSJ_CHAP_ID)
            .builder()
        AdShowUtils.getInstance().initAdConfig(this, BuildConfig.DEBUG, config)
    }


}