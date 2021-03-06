package com.juguo.magazine.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
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
     lateinit var mActivity: AppCompatActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.lifecycleOwner = this //databinding绑定声明周期
        initView(savedInstanceState)
    }

    /**
     * 初始化数据
     */
    protected abstract fun initView(
        savedInstanceState: Bundle?,
    )

}