package com.juguo.magazine.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.juguo.magazine.R
import com.juguo.magazine.base.BaseFragment
import com.juguo.magazine.databinding.MagazineFragmentBinding
import com.juguo.magazine.viewmodel.HomeViewModel
import com.juguo.magazine.viewmodel.MagazineViewModel

class MagazineFragment : BaseFragment<MagazineFragmentBinding>() {
    override val getLayoutId=R.layout.magazine_fragment
    private  val viewModel: MagazineViewModel by viewModels()

    companion object {
        fun newInstance() = MagazineFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.magazineViewmodel = viewModel // 绑定布局的viewModel
    }

    override fun initView(savedInstanceState: Bundle?) {

    }
}