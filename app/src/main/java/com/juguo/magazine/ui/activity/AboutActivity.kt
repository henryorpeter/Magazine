package com.juguo.magazine.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.gyf.barlibrary.ImmersionBar
import com.juguo.magazine.R
import com.juguo.magazine.util.UITools
import kotlinx.android.synthetic.main.activity_about.*

/**
 *  author : Administrator
 *  date : 2021/8/20 11:06
 *  description :
 * @Author : yangjinjing
 */
class AboutActivity :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        UITools.makeStatusBarTransparent(this)
        UITools.setMIUI(this, true)
        //解决白色状态栏
        ImmersionBar.with(this)
            .statusBarDarkFont(true)
            .navigationBarDarkIcon(true)
            .init()
        image_back_guanyu.setOnClickListener({ finish() })
    }
}