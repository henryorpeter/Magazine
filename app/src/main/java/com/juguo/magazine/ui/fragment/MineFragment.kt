package com.juguo.magazine.ui.fragment

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.juguo.magazine.R
import com.juguo.magazine.base.BaseFragment
import com.juguo.magazine.databinding.MineFragmentBinding
import com.juguo.magazine.viewmodel.FindViewModel
import com.juguo.magazine.viewmodel.MineViewModel

class MineFragment : BaseFragment<MineFragmentBinding>() {
    override val getLayoutId=R.layout.mine_fragment
    private  val viewModel: MineViewModel by viewModels()

    companion object {
        fun newInstance() = MineFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.mineViewmodel = viewModel // 绑定布局的viewModel
    }
}