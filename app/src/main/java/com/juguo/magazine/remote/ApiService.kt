package com.juguo.magazine.remote

import com.juguo.magazine.bean.BaseBean
import com.juguo.magazine.bean.HelpResponseBean
import com.juguo.magazine.bean.PrivacyBean
import com.juguo.magazine.bean.VersionUpdateBean
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 *  author : Administrator
 *  date : 2021/8/18 9:34
 *  description :
 * @Author : yangjinjing
 */
interface ApiService {
    //静谧登录
    @POST("user/login")
    fun login(@Body body: RequestBody): Observable<BaseBean>

    //隐私政策
    @GET("app/last-v")
    fun getPrivacy(): Observable<PrivacyBean>

    // 帮助反馈
    @POST("feedback/")
    fun getfeedBack(@Body body: RequestBody?): Observable<HelpResponseBean>

    //版本更新
    @POST("app-v/check")
    fun getUpdate(@Body body: RequestBody?): Observable<VersionUpdateBean>

}