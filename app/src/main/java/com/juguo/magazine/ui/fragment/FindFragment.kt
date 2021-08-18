package com.juguo.magazine.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.juguo.magazine.App
import com.juguo.magazine.R
import com.juguo.magazine.base.BaseFragment
import com.juguo.magazine.databinding.FindFragmentBinding
import com.juguo.magazine.viewmodel.FindViewModel
import com.juguo.magazine.viewmodel.MagazineViewModel

class FindFragment : BaseFragment<FindFragmentBinding>() {
    override val getLayoutId=R.layout.find_fragment
    private  val viewModel: FindViewModel by viewModels()

    companion object {
        fun newInstance() = FindFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.findViewmodel = viewModel // 绑定布局的viewModel
    }

    private fun initView() {
        mBinding.toolbar.apply {
            title = getString(R.string.find_name)

        }
    }
}