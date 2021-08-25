package com.juguo.magazine.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.juguo.magazine.bean.FavoritesListBean
import com.juguo.magazine.util.ToastUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody

class FindViewModel : BaseViewModel() {
    val favorites: MutableLiveData<List<FavoritesListBean.Favorites>> = MutableLiveData()

    /**
     * 查询收藏列表
     */
    fun favoritesList() {
        val map = mutableMapOf(
            "order" to "desc",
            "sort" to "add_time",
            "pageNum" to 1,
            "pageSize" to 10
        )
        //{"param":{map}}
        val param: MutableMap<String, Any> = mutableMapOf("param" to map)
        val body = RequestBody.create(
            MediaType.get("application/json; charset=utf-8"),
            Gson().toJson(param)
        )
        addDisposable(mApiService.favoritesListBean(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ favoritesListBean -> //刷新的请求，用postValue()
                favorites.value = favoritesListBean.favorites
                //                    favorites.postValue(favoritesListBean.favorites)
            }) { throwable ->
                Log.d(ContentValues.TAG, "loadMore: $throwable")
            })
    }
}