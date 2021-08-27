package com.juguo.magazine.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.fenghuajueli.lib_ad.AdShowUtils
import com.juguo.magazine.App
import com.juguo.magazine.R
import com.juguo.magazine.adapter.AdapterFragmentPager
import com.juguo.magazine.base.BaseActivity
import com.juguo.magazine.databinding.ActivityMainBinding
import com.juguo.magazine.remote.ApiService
import com.juguo.magazine.remote.RetrofitManager
import com.juguo.magazine.util.RxUtils
import com.juguo.magazine.viewmodel.MainViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.view.*


class MainActivity : BaseActivity<ActivityMainBinding, Any?>() {
    private val TAG = "MainActivity"
    override val getLayoutId = R.layout.activity_main
    private val mViewPager: ViewPager? = null //lateinit：如果不延迟初始化变量，必须要设为null，或者初始化这个变量
    private val mViewModel: MainViewModel by viewModels() //通过kotlin拓展方法创建viewmodel
    private val mDisposable = CompositeDisposable()
    @JvmField
    protected var mApiService = RetrofitManager.getApi(ApiService::class.java) //初始化请求接口ApiService，给继承的子类用
    override fun onViewCreate(savedInstanceState: Bundle?) {
        super.onViewCreate(savedInstanceState)
        mBinding.viewModel = mViewModel //绑定布局的viewmodel
        mViewModel.Requestlogin()
        redaGiao()
        initData()
        setListener()
        initViewAndData()
        if (App.sInstance.isShowAd) {
            AdShowUtils.getInstance().loadInteractionAd(this, "main_chaping_ad")
        }
    }

    private fun initData() {
        with(mBinding.vpFragment) {
            adapter = AdapterFragmentPager(this@MainActivity)
            vp_fragment.isUserInputEnabled = false //禁止滑动
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    mBinding.rgTab.check(getCheckedId(position))
                }
            })
        }
    }

    private fun getCheckedId(position: Int): Int {
        return when (position) {
            0 -> R.id.rb_home
            1 -> R.id.rb_add
            2 -> R.id.rb_find
            3 -> R.id.rb_others
            else -> R.id.rb_home
        }
    }

    private fun setListener() {
        mBinding.rgTab.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_home -> mBinding.vpFragment.setCurrentItem(AdapterFragmentPager.PAGE_HOME, true)
                R.id.rb_add -> mBinding.vpFragment.setCurrentItem(AdapterFragmentPager.PAGE_FIND, true)
                R.id.rb_find -> mBinding.vpFragment.setCurrentItem(AdapterFragmentPager.PAGE_INDICATOR, true)
                R.id.rb_others -> mBinding.vpFragment.setCurrentItem(AdapterFragmentPager.PAGE_OTHERS, true)
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun redaGiao(){
        //定义底部标签图片大小 第一0是距左右边距离，第二0是距上下边距离，第三长度,第四宽度
        val page_selector = resources.getDrawable(R.drawable.page_selector)
        page_selector.setBounds(0, 0, 65, 65)
        mBinding.rbHome.setCompoundDrawables(null, page_selector, null, null) //只放上面

        val magazine_selector = resources.getDrawable(R.drawable.magazine_selector)
        magazine_selector.setBounds(0, 0, 65, 65)
        mBinding.rbAdd.setCompoundDrawables(null, magazine_selector, null, null)

        val find_selector = resources.getDrawable(R.drawable.find_selector)
        find_selector.setBounds(0, 0, 65, 65)
        mBinding.rbFind.setCompoundDrawables(null, find_selector, null, null)

        val mine_selector = resources.getDrawable(R.drawable.mine_selector)
        mine_selector.setBounds(0, 0, 65, 65)
        mBinding.rbOthers.setCompoundDrawables(null, mine_selector, null, null)

    }

    private fun initViewAndData() {
        mDisposable.add(mApiService.getAppIdAdvertise("wx9ooOL2SleR78Slil")
            .compose(RxUtils.schedulersTransformer())
            .subscribe({ privacyBean ->
                Log.d(TAG, "accept: $privacyBean")
                val startAdFlag: String = privacyBean.getResult().getStartAdFlag()
                //NONE 无  CSJ 穿山甲  SYS 自系统
                if ("NONE".contains(startAdFlag)) {
                } else if ("CSJ" == startAdFlag) {
                } else if ("SYS" == startAdFlag) {
                }
            }) { throwable -> Log.d(TAG, "loadMore: $throwable") })
    }

    override fun onDestroy() {
        super.onDestroy()
        AdShowUtils.getInstance().destroyInteractionAd("main_chaping_ad")
    }

}