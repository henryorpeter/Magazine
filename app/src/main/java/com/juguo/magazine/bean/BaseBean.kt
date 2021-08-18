package com.juguo.magazine.bean

/**
 *  author : Administrator
 *  date : 2021/8/18 10:03
 *  description :
 * @Author : yangjinjing
 */
class BaseBean {
    private var code = 0
    private var msg: String? = null
    private var result: String? = null

    fun getCode(): Int {
        return code
    }

    fun setCode(code: Int) {
        this.code = code
    }

    fun getMsg(): String? {
        return msg
    }

    fun setMsg(msg: String?) {
        this.msg = msg
    }

    fun getResult(): String? {
        return result
    }

    fun setResult(result: String?) {
        this.result = result
    }
}