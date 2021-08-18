package com.juguo.magazine.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.juguo.magazine.R
import com.juguo.magazine.adapter.AdapterFragmentPager
import com.juguo.magazine.base.BaseActivity
import com.juguo.magazine.databinding.ActivityMainBinding
import com.juguo.magazine.viewmodel.MainViewModel


class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val TAG = "MainActivity"
    override val getLayoutId = R.layout.activity_main
    private val mViewPager: ViewPager? = null //lateinit：如果不延迟初始化变量，必须要设为null，或者初始化这个变量
    private val mViewModel: MainViewModel by viewModels() //通过kotlin拓展方法创建viewmodel

    override fun onViewCreate(savedInstanceState: Bundle?) {
        super.onViewCreate(savedInstanceState)
        mBinding.viewModel = mViewModel //绑定布局的viewmodel
        mViewModel.Requestlogin()
        redaGiao()
        initData()
        setListener()
    }

    private fun initData() {
        with(mBinding.vpFragment) {
            adapter = AdapterFragmentPager(this@MainActivity)
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
        page_selector.setBounds(0, 0, 70, 70)
        mBinding.rbHome.setCompoundDrawables(null, page_selector, null, null) //只放上面

        val magazine_selector = resources.getDrawable(R.drawable.magazine_selector)
        magazine_selector.setBounds(0, 0, 70, 70)
        mBinding.rbAdd.setCompoundDrawables(null, magazine_selector, null, null)

        val find_selector = resources.getDrawable(R.drawable.find_selector)
        find_selector.setBounds(0, 0, 70, 70)
        mBinding.rbFind.setCompoundDrawables(null, find_selector, null, null)

        val mine_selector = resources.getDrawable(R.drawable.mine_selector)
        mine_selector.setBounds(0, 0, 70, 70)
        mBinding.rbOthers.setCompoundDrawables(null, mine_selector, null, null)

    }

}