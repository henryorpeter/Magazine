package com.juguo.magazine.remote

import android.util.Log
import com.juguo.magazine.event.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * author : Administrator
 * date : 2021/8/18 9:42
 * description :
 *
 * @Author : yangjinjing
 */


//object声明这个类为单例类
object RetrofitManager {
    private val mLoggingInterceptor =
        HttpLoggingInterceptor { s: String -> Log.e("TAG", "Content：$s") }
            .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val mOkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HeaderInterceptor())
        .addNetworkInterceptor(mLoggingInterceptor)
        .build()


    // = 表示return
    fun <T> getApi(service: Class<T>, url: String = BASE_URL): T = Retrofit.Builder()
        .baseUrl(url)
        .client(mOkHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(service)

    // return写法
    fun <T> returnApi(service: Class<T>, baseUrl: String): T {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(mOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(service)
    }

}