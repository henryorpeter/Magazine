package com.juguo.magazine.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.juguo.magazine.R
import com.juguo.magazine.adapter.ViewBindingSampleAdapter
import com.juguo.magazine.base.BaseFragment
import com.juguo.magazine.databinding.HomeFragmentBinding
import com.juguo.magazine.util.ToastUtil
import com.juguo.magazine.viewmodel.HomeViewModel
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.annotation.APageStyle
import com.zhpan.bannerview.constants.PageStyle

class HomeFragment : BaseFragment<HomeFragmentBinding>() {
    private lateinit var mViewPager: BannerViewPager<Int>
    override val getLayoutId = R.layout.home_fragment
    private val viewModel: HomeViewModel by viewModels()

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.homeViewmodel = viewModel // 绑定布局的viewModel
    }

    override fun initView(savedInstanceState: Bundle?) {
        mViewPager = mBinding.bannerView as BannerViewPager<Int>
        initBVP()
        setupBanner(
            PageStyle.MULTI_PAGE_SCALE,
            resources.getDimensionPixelOffset(R.dimen.dp_50)
        )
    }

    private fun initBVP() {
        mViewPager.apply {
            setLifecycleRegistry(lifecycle)
            adapter = ViewBindingSampleAdapter(resources.getDimensionPixelOffset(R.dimen.dp_12))
            setOnPageClickListener { _: View, position: Int -> itemClick(position) }
            setInterval(4000)
        }
    }

    /**
     * Different page styles can be implement by use [BannerViewPager.setPageStyle] and
     * [BannerViewPager.setRevealWidth]
     * @param pageStyle Optional params [PageStyle.MULTI_PAGE_SCALE] and [PageStyle.MULTI_PAGE_OVERLAP]
     * @param revealWidth In the multi-page mode, The exposed width of the items on the left and right sides
     */
    private fun setupBanner(@APageStyle pageStyle: Int, revealWidth: Int) {
        setupBanner(pageStyle, revealWidth, revealWidth)
    }

    private fun setupBanner(
        @APageStyle pageStyle: Int,
        leftRevealWidth: Int,
        rightRevealWidth: Int) {
        mViewPager
            .setPageMargin(resources.getDimensionPixelOffset(R.dimen.dp_30))
            .setScrollDuration(800)
            .setRevealWidth(leftRevealWidth, rightRevealWidth)
            .setPageStyle(pageStyle)
            .create(getPicList(4))
    }

    private fun itemClick(position: Int) {
        if (position != mViewPager.currentItem) {
            mViewPager.setCurrentItem(position, true)
        }
        ToastUtil.showLongToast(context,"点击了:$position")
    }

}