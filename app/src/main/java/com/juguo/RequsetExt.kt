package com.juguo

import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.RequestBody

/**
 *  author : Administrator
 *  date : 2021/8/18 11:04
 *  description :
 * @Author : yangjinjing
 * 写个扩展函数
 */

//表示只有string才能使用
fun String.getRequestBody(): RequestBody = RequestBody.create(
    MediaType.get("application/json; charset=utf-8"),
    this // this等于闯进来的string -> Gson().toJson(param)
)
