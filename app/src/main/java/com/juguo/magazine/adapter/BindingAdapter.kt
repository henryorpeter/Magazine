package com.juguo.magazine.adapter

import android.text.TextUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

/**
 *  author : Administrator
 *  date : 2021/8/25 18:09
 *  description :
 * @Author : yangjinjing
 */

@BindingAdapter("showImage")
fun showUserImage(view: ImageView, url: String?) {
    if (!TextUtils.isEmpty(url))
        Glide.with(view.context)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(false)
            .into(view)
    else view.setImageBitmap(null)
}