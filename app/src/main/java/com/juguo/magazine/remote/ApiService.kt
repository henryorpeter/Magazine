package com.juguo.magazine.remote

import com.juguo.magazine.bean.*
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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

    //瀑布流
    @POST("res-ext/list")
    fun getList(@Body bean: RequestBody?): Observable<PieceBean>

    //分类相关
    @POST("category/list")
    fun getCategory(@Body bean: RequestBody?): Observable<CategoryBean>

    //收藏
    @POST("res/{resId}/{starType}/")
    fun favoritesImages(
        @Path("resId") resId: String?,
        @Path("starType") starType: Int): Observable<FavoritesBean>

    //取消收藏
    @POST("res/{resId}/{starType}/")
    fun deleFavoritesImages(
        @Path("resId") resId: String?, @Path("starType") starType: Int): Observable<FavoritesBean>

    //获取收藏列表
    @POST("res-ext/star-list")
    fun favoritesListBean(@Body bean: RequestBody?): Observable<FavoritesListBean>

}