package com.juguo.magazine.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.gyf.barlibrary.ImmersionBar
import com.juguo.magazine.App
import com.juguo.magazine.R
import java.util.ArrayList

/**
 *  author : Administrator
 *  date : 2021/8/18 15:14
 *  description :
 * @Author : yangjinjing
 */
abstract class BaseFragment<DB : ViewDataBinding> : Fragment() {
    lateinit var mBinding: DB //延迟初始化 --- 在onCreate里面初始化这个变量
    abstract val getLayoutId: Int
    private var mPictureList: MutableList<Int> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?, ): View? {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.lifecycleOwner = this //databinding绑定声明周期
        initView(savedInstanceState)
    }

    protected fun getPicList(count: Int): MutableList<Int> {
        mPictureList.clear()
        for (i in 0..count) {
            val mipmap = resources.getIdentifier("advertise$i", "mipmap", App.getContext().packageName)
            mPictureList.add(mipmap)
        }
        return mPictureList;
    }

    /**
     * 初始化数据
     */
    protected abstract fun initView(
        savedInstanceState: Bundle?,
    )
}