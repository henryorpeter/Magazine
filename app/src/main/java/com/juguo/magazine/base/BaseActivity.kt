package com.juguo.magazine.base

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.gyf.barlibrary.ImmersionBar

abstract class BaseActivity<DB : ViewDataBinding, T> : AppCompatActivity() {
    private val TAG = "Baseactivty"
    lateinit var mBinding: DB //延迟初始化 --- 在onCreate里面初始化这个变量
    private lateinit var mImmersionBar: ImmersionBar //状态栏沉浸，在statusBarConfig初始化这个变量  lateinit==延迟初始化
    abstract val getLayoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, getLayoutId)

        //*** kotlin的setXX()方法都是赋值方式 -> mBinding.setLifecycleOwner(this)
        mBinding.lifecycleOwner = this // 绑定生命周期，只有在onStart才会通知更新UI
        statusBarConfig()!!.init()
        //initLoadingEvent();
        onViewCreate(savedInstanceState)
    }

    /**
     * 初始化沉浸式状态栏
     */
    protected fun statusBarConfig(): ImmersionBar? {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this)
            .statusBarDarkFont(true) //默认状态栏字体颜色为黑色
            .keyboardEnable(
                false, WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                        or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
            ) //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
        //必须设置View树布局变化监听，否则软键盘无法顶上去，还有模式必须是SOFT_INPUT_ADJUST_PAN
        window.decorView.viewTreeObserver.addOnGlobalLayoutListener {}
        return mImmersionBar
    }

    protected open fun onViewCreate(savedInstanceState: Bundle?) {}

}