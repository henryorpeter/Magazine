package com.juguo.magazine.ui.activity

import android.content.ClipboardManager
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.gyf.barlibrary.ImmersionBar
import com.juguo.getRequestBody
import com.juguo.magazine.R
import com.juguo.magazine.event.WX_APP_ID
import com.juguo.magazine.remote.ApiService
import com.juguo.magazine.remote.RetrofitManager
import com.juguo.magazine.util.DeviceIdUtil
import com.juguo.magazine.util.RxUtils
import com.juguo.magazine.util.ToastUtil
import com.juguo.magazine.util.UITools
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_help.*
import okhttp3.MediaType
import okhttp3.RequestBody
import java.util.*

/**
 *  author : Administrator
 *  date : 2021/8/20 11:21
 *  description :
 * @Author : yangjinjing
 */
class HelpActivity : AppCompatActivity() {
    private val TAG = "HelpModel"
    private val mDisposable = CompositeDisposable()
    @JvmField
    protected var mApiService = RetrofitManager.getApi(ApiService::class.java) //初始化请求接口ApiService，给继承的子类用

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
        UITools.makeStatusBarTransparent(this)
        UITools.setMIUI(this, true)
        tv_title.setText("联系客服")
        initData()
        onClick()
        //解决白色状态栏
        ImmersionBar.with(this)
            .statusBarDarkFont(true)
            .navigationBarDarkIcon(true)
            .init()
    }

    private fun initData() {
        et_context!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                tv_input_sum!!.text =
                    et_context!!.text.toString().trim { it <= ' ' }.length.toString()
            }
        })
    }

    private fun onClick() {
        image_back_guanyu.setOnClickListener { v: View? -> finish() }
        tv_qq_fz.setOnClickListener {
            val content = tv_qq!!.text.toString().split("：").toTypedArray()[1]
            // qq复制
            val cm1 = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            // 将文本内容放到系统剪贴板里。
            cm1.text = content
            ToastUtil.showToast(this, "复制成功！")
        }
        tv_fs.setOnClickListener {
            // 发送
            val context = et_context!!.text.toString().trim { it <= ' ' }
            if (TextUtils.isEmpty(context)) {
                ToastUtil.showLongToast(this, "请输入您的问题和意见？")
            } else {
                    httpGetHelp()
            }
        }
    }

    private fun httpGetHelp() {
        val context = et_context!!.text.toString().trim { it <= ' ' }
        val contact = et_lxfs!!.text.toString()

        val map: MutableMap<String, Any> = mutableMapOf(
            "contact" to contact,
            "content" to context,
            "appId" to WX_APP_ID
        )
        //{"param":{map}}
        val param: MutableMap<String, Any> =  mutableMapOf("param" to map)

        val body = RequestBody.create(
            MediaType.get("application/json; charset=utf-8"),
            Gson().toJson(param)
        )
        mDisposable.add(mApiService.getfeedBack(body)
            .compose(RxUtils.schedulersTransformer())
            .subscribe({ helpResponseBean ->
                Log.d(TAG, "loadMore: $helpResponseBean")
                ToastUtil.showToast(application, "提交成功！")
                finish()
            }) { throwable ->
                Log.d(TAG, "loadMore: $throwable")
                tv_fs_erro!!.visibility = View.VISIBLE
                ToastUtil.showToast(application, resources.getString(R.string.erro))
            })
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}