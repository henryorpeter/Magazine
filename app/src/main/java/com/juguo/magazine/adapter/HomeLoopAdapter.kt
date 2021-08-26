package com.juguo.magazine.adapter

import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.juguo.magazine.R
import com.juguo.magazine.bean.PieceBean
import com.juguo.magazine.databinding.BannerNewsConsumeBinding
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder

/**
 *  author : Administrator
 *  date : 2021/8/25 17:05
 *  description :
 * @Author : yangjinjing
 */
class HomeLoopAdapter() : BaseBannerAdapter<PieceBean.Price>() {
    override fun getLayoutId(viewType: Int) = R.layout.banner_news_consume

    override fun bindData(
        holder: BaseViewHolder<PieceBean.Price>,
        data: PieceBean.Price,
        position: Int,
        pageSize: Int
    ) {
        val bindind = DataBindingUtil.bind<BannerNewsConsumeBinding>(holder.itemView)
        bindind?.run {
            bean = data
            root.setOnClickListener {
                Log.e("HomeLoopAdapter", "bindData: ${Gson().toJson(data)}")
                onItemDataCallback?.invoke(it,data) //回调传参进去
            }
        }
    }

    //监听点击事件
    var onItemDataCallback: ((v: View, data: PieceBean.Price) -> Unit)? = null
}