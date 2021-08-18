package com.juguo.magazine.remote

import com.juguo.magazine.bean.BaseBean
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.Body
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
}