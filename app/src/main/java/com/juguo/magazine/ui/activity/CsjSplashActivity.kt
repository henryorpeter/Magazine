package com.juguo.magazine.ui.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.fenghuajueli.lib_ad.AdConfig
import com.fenghuajueli.lib_ad.AdShowUtils
import com.fenghuajueli.lib_ad.AdShowUtils.AdConfigBuilder
import com.google.gson.Gson
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.jeremyliao.liveeventbus.BuildConfig
import com.juguo.getRequestBody
import com.juguo.magazine.App
import com.juguo.magazine.R
import com.juguo.magazine.event.CSJ_APP_ID
import com.juguo.magazine.event.CSJ_CHAP_ID
import com.juguo.magazine.event.CSJ_CODE_ID
import com.juguo.magazine.event.WX_APP_ID
import com.juguo.magazine.remote.ApiService
import com.juguo.magazine.remote.RetrofitManager
import com.juguo.magazine.util.DeviceIdUtil
import com.juguo.magazine.util.RxUtils
import com.juguo.magazine.util.ToastUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.mine_fragment.*

import java.util.*

/**
 *  author : Administrator
 *  date : 2021/8/26 11:11
 *  description :
 * @Author : yangjinjing
 */
class CsjSplashActivity : Activity() {
    private val TAG = "SplashActivity"
    private val mDisposable = CompositeDisposable()
    private lateinit var mSplashContainer: FrameLayout

    @JvmField
    protected var mApiService =
        RetrofitManager.getApi(ApiService::class.java) //?????????????????????ApiService????????????????????????

    //??????????????????????????????
    private var mForceGoMain = false

    //????????????????????????
    private var isFirstUse = false
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_splash)
        super.onCreate(savedInstanceState)
        mSplashContainer = findViewById<View>(R.id.fl_ad) as FrameLayout
        initViewAndData()
    }

    private fun showPrivacyDialog() {
        //NONE ???  CSJ ?????????  SYS ?????????
//        boolean isAgree = (boolean) mySharedPreferences.getValue("isagree", false);
        //??????????????????app
        val preferences = getSharedPreferences("isFirstUse", MODE_PRIVATE)
        val edit = preferences.edit()
        isFirstUse = preferences.getBoolean("isFirstUse", false)
        if (isFirstUse) {
            showAd()
            return
        }
        val relativeLayout = layoutInflater.inflate(R.layout.dialog_agree, null) as CardView
        val bt_cancel = relativeLayout.findViewById<View>(R.id.bt_cancle) as TextView
        val bt_sure = relativeLayout.findViewById<View>(R.id.bt_sure) as TextView
        val tv_message = relativeLayout.findViewById<View>(R.id.tv_message) as TextView
        val userLicenseAgreement = "??????????????????"
        val privacyAgreement = "??????????????????"
        val message = "?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????"
        val messageAll = ("?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????"
                + userLicenseAgreement + "???" + privacyAgreement
                + "???????????????????????????????????????????????????????????????????????????????????????")
        val start_User = message.length
        val end_User = start_User + userLicenseAgreement.length - 1
        val start_Privacy = end_User + 3
        val end_Privacy = start_Privacy + privacyAgreement.length - 1
        val spannableStringBuilder = SpannableStringBuilder()
        spannableStringBuilder.append(messageAll)
        val userLicenseClickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val userLicenseIntent =
                    Intent(this@CsjSplashActivity, UserAgreementActivity::class.java)
                userLicenseIntent.putExtra(
                    "url",
                    "file:///android_asset/web/UserLicenseAgreement.html"
                )
                startActivity(userLicenseIntent)
                finish()
            }
        }
        val privacyClickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val userLicenseIntent =
                    Intent(this@CsjSplashActivity, PrivacysActivity::class.java)
//                userLicenseIntent.putExtra("url", "file:///android_asset/web/PrivacyPolicys.html")
                startActivity(userLicenseIntent)
                finish()

            }
        }
        spannableStringBuilder.setSpan(
            userLicenseClickableSpan,
            start_User,
            end_User,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableStringBuilder.setSpan(
            privacyClickableSpan,
            start_Privacy,
            end_Privacy,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        tv_message.movementMethod = LinkMovementMethod.getInstance()
        tv_message.text = spannableStringBuilder
        val dialog = AlertDialog.Builder(this)
            .setView(relativeLayout)
            .setCancelable(false)
            .create()
        dialog.setView(relativeLayout, 0, 0, 0, 0)
        dialog.show()

        //??????show()??????????????????????????????????????????????????????height???width
        val dialogWindow = dialog.window
        val m = windowManager
        val d = m.defaultDisplay // ?????????????????????
        val p = dialogWindow!!.attributes // ?????????????????????????????????
        // ????????????
        p.width = (d.width * 0.95).toInt() // ????????????????????????0.95
        p.gravity = Gravity.CENTER //????????????
        //p.alpha = 0.5f;//???????????????
        dialogWindow.attributes = p
        bt_cancel.setOnClickListener { v: View? ->
            dialog.dismiss()
            finish()
        }
        bt_sure.setOnClickListener { v: View? ->
            edit.putBoolean("isFirstUse", true).apply()
            //            checkPermission();
            Requestlogin()
            dialog.dismiss()
            ThirdPartySdk()
            showTwoAd()
        }
    }

    fun ThirdPartySdk() {
        //??????????????????????????????Application#onCreate()??????????????????????????????content???null?????????
        val config = AdConfigBuilder()
            .setAdAppId(CSJ_APP_ID)
            .setSplashId(CSJ_CODE_ID)
            .setInteractionExpressId(CSJ_CHAP_ID)
            .builder()
        AdShowUtils.getInstance().initAdConfig(App.sInstance, BuildConfig.DEBUG, config)
    }

    private fun showTwoAd() {
        if (App.sInstance.isShowAd) {
            hideBottomUIMenu() //?????????????????????????????????
            AdShowUtils.getInstance().loadSplashAd(this, mSplashContainer) { initViewAndData() }
        } else {
            initViewAndData()
        }
    }

    private fun showAd() {
        if (App.sInstance.isShowAd) {
            hideBottomUIMenu() //?????????????????????????????????
            AdShowUtils.getInstance().loadSplashAd(this, mSplashContainer) { goToMainActivity() }
        } else {
            goToMainActivity()
        }
    }

    /**
     * ??????????????????
     */
    private fun initViewAndData() {
        mDisposable.add(mApiService.getAppIdAdvertise(WX_APP_ID)
            .compose(RxUtils.schedulersTransformer())
            .subscribe({ privacyBean ->
                Log.d(TAG, "accept: $privacyBean")
                val startAdFlag: String = privacyBean.getResult().getStartAdFlag()
                if ("NONE" == startAdFlag) {

                } else if ("CSJ" == startAdFlag) {
                    App.sInstance.isShowAd = true
                } else if ("SYS" == startAdFlag) {

                }
                showPrivacyDialog()
            }) { throwable -> Log.d(TAG, "loadMore: $throwable") })
    }

    /**
     * ??????????????????
     */
    private fun goToMainActivity() {
        val intent = Intent(this@CsjSplashActivity, MainActivity::class.java)
        startActivity(intent)
        //mSplashContainer.removeAllViews();  //??????????????????
        finish()
    }

    /**
     * ?????????????????????????????????
     */
    protected fun hideBottomUIMenu() {
        //?????????????????????????????????
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            val v = this.window.decorView
            v.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            val decorView = window.decorView
            val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN)
            decorView.systemUiVisibility = uiOptions
        }
    }

    fun Requestlogin() {
        val map: MutableMap<String, Any> = mutableMapOf(
            "appId" to WX_APP_ID,
            "type" to 2,
            "unionInfo" to DeviceIdUtil.getDeviceUUID()
        )
        //{"param":{map}}
        val param: MutableMap<String, Any> = mutableMapOf("param" to map)

        val body = Gson().toJson(param).getRequestBody()
        mDisposable.add(
            mApiService.login(body) // ??????rxjava?????????????????????
                //                .doOnSubscribe(disposable -> MainViewModel.this.getLoadingLiveData().getShowDialog())
                .subscribeOn(Schedulers.io()) // io??????????????????
                .observeOn(AndroidSchedulers.mainThread()) // ?????????????????????
                .subscribe({
                    Log.i(TAG, "Login_csj??????????????????????+++++++" + Gson().toJson(it))
                    //??????result_token
                    val sp: SharedPreferences =
                        App.sInstance.getSharedPreferences("sp", Context.MODE_PRIVATE)
                    val editor: SharedPreferences.Editor = sp.edit()
                    editor.putString("result_token", it.getResult()).apply()
                    //                        getLoadingLiveData().getDismissDialog().setValue(false);
                }, { e: Throwable -> Log.e(TAG, "onFailure" + e.message) })
        )
    }

}
