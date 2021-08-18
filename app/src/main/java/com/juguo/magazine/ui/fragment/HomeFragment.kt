package com.juguo.magazine.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.juguo.magazine.App
import com.juguo.magazine.R
import com.juguo.magazine.base.BaseFragment
import com.juguo.magazine.databinding.HomeFragmentBinding
import com.juguo.magazine.viewmodel.HomeViewModel

class HomeFragment : BaseFragment <HomeFragmentBinding>() {
    override val getLayoutId=R.layout.home_fragment
    private  val viewModel: HomeViewModel by viewModels()

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //↓↓↓↓↓↓ chomeFragmentBinding.setVariable(BR.homeViewmodel, viewModel) //设置布局的viewmodel才生效，pageViewModel -》 布局的定义的
        mBinding.homeViewmodel = viewModel // 绑定布局的viewModel
    }

}