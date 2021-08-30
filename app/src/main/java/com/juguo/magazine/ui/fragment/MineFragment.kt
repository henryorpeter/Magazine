package com.juguo.magazine.ui.fragment

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import androidx.fragment.app.viewModels
import com.juguo.magazine.App
import com.juguo.magazine.R
import com.juguo.magazine.adapter.GwhpPopupwindowAdapter
import com.juguo.magazine.base.BaseFragment
import com.juguo.magazine.bean.MarketPkgsBean
import com.juguo.magazine.databinding.MineFragmentBinding
import com.juguo.magazine.ui.activity.*
import com.juguo.magazine.util.CacheDataManager
import com.juguo.magazine.util.CommUtils
import com.juguo.magazine.util.NoScrollGridView
import com.juguo.magazine.util.ToastUtil
import com.juguo.magazine.viewmodel.MineViewModel
import java.util.*

class MineFragment : BaseFragment<MineFragmentBinding>() {
    override val getLayoutId = R.layout.mine_fragment
    private val viewModel: MineViewModel by viewModels()
    private val installedMarketPkgs: ArrayList<MarketPkgsBean>? = null

    companion object {
        fun newInstance() = MineFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.mineViewmodel = viewModel // 绑定布局的viewModel
        onClick()
    }

    override fun initView(savedInstanceState: Bundle?) {
    }

    private fun onClick() {
        mBinding.linearLayout5.setOnClickListener {
            var cacheAllSize = CacheDataManager.getTotalCacheSize(context)
            CacheDataManager.clearAllCache(context);
            Log.i(TAG,"清理缓存?>>?>?>?>?>?>?" + cacheAllSize)
            ToastUtil.showLongToast(App.sInstance, "成功清理缓存" + cacheAllSize)
        }
        mBinding.linearLayoutYdjl.setOnClickListener {
            val intent = Intent()
            intent.setClass(App.sInstance, ReadingRecordActivity::class.java)
            startActivity(intent)
        }
        mBinding.linearLayoutYszc.setOnClickListener {
            val intent = Intent()
            intent.setClass(App.sInstance, PrivacyActivity::class.java)
            startActivity(intent)
        }
        mBinding.linearLayoutSghp.setOnClickListener {
            if (installedMarketPkgs != null && installedMarketPkgs.size > 0) {
                showSelectDialog()
            } else {
                ToastUtil.showLongToast(App.sInstance, "手机暂无应用商店")
            }
        }
        mBinding.linearLayoutLxkf.setOnClickListener {
            val intent = Intent()
            intent.setClass(App.sInstance, HelpActivity::class.java)
            startActivity(intent)
        }
        mBinding.linearLayoutGywm.setOnClickListener {
            val intent = Intent()
            intent.setClass(App.sInstance, AboutActivity::class.java)
            startActivity(intent)
        }
        mBinding.linearLayoutYhxy.setOnClickListener {
            val intent = Intent()
            intent.setClass(App.sInstance, UserAgreementActivity::class.java)
            intent.putExtra("url", "file:///android_asset/web/UserLicenseAgreement.html")
            startActivity(intent)
        }
        mBinding.linearLayoutJcgx.setOnClickListener {
           viewModel.UpdaterApp()
        }
    }

    /**
     * 跳转应用商店弹窗
     */
    private fun showSelectDialog() {
        val dialog: AlertDialog
        val diaView = View.inflate(App.sInstance, R.layout.gwhp_popupwindow, null)
        val grid_view: NoScrollGridView = diaView.findViewById(R.id.grid_view)
        val gwhpPopupwindowAdapter = GwhpPopupwindowAdapter(App.sInstance, installedMarketPkgs)
        grid_view.setAdapter(gwhpPopupwindowAdapter)
        val builder = AlertDialog.Builder(App.sInstance)
        builder.setView(diaView)
        dialog = builder.create()
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
        val display = requireActivity().windowManager.defaultDisplay
        val window = dialog.window
        val lp = window!!.attributes
        window.setBackgroundDrawableResource(android.R.color.transparent)
        window.setWindowAnimations(R.style.popupAnimation)
        lp.gravity = Gravity.BOTTOM
        lp.width = display.width //设置宽度
        dialog.window!!.attributes = lp
        grid_view.setOnItemClickListener(OnItemClickListener { parent, view, position, id -> // 跳转应用商店
            val marketPkgsBean: MarketPkgsBean = installedMarketPkgs!!.get(position)
            CommUtils.launchAppDetail(
                App.sInstance,
                CommUtils.getApkPkgName(App.sInstance),
                marketPkgsBean.getPkgName()
            )
            dialog.dismiss()
        })
    }
}