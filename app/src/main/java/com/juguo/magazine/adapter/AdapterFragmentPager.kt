package com.juguo.magazine.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.juguo.magazine.ui.fragment.FindFragment
import com.juguo.magazine.ui.fragment.HomeFragment
import com.juguo.magazine.ui.fragment.MagazineFragment
import com.juguo.magazine.ui.fragment.MineFragment

/**
 *  author : Administrator
 *  date : 2021/8/18 14:44
 *  description :
 * @Author : yangjinjing
 */
class AdapterFragmentPager(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            PAGE_HOME -> HomeFragment.newInstance()
            PAGE_FIND -> MagazineFragment.newInstance()
            PAGE_INDICATOR -> FindFragment.newInstance()
            PAGE_OTHERS -> MineFragment.newInstance()
            else -> HomeFragment.newInstance()
        }
    }

    override fun getItemCount(): Int {
        return 4
    }

    companion object {
        const val PAGE_HOME = 0
        const val PAGE_FIND = 1
        const val PAGE_INDICATOR = 2
        const val PAGE_OTHERS = 3
    }
}