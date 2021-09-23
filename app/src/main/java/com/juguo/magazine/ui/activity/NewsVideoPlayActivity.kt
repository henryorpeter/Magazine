package com.juguo.magazine.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.danikula.videocache.HttpProxyCacheServer
import com.google.gson.Gson
import com.jeremyliao.liveeventbus.LiveEventBus
import com.juguo.magazine.R
import com.juguo.magazine.bean.PieceBean
import com.juguo.magazine.util.ProxyVideoCacheManager
import kotlinx.android.synthetic.main.activity_detailed_news.*
import kotlinx.android.synthetic.main.activity_news_video_play.*
import xyz.doikki.videocontroller.StandardVideoController
import xyz.doikki.videoplayer.player.AbstractPlayer
import xyz.doikki.videoplayer.player.VideoView

class NewsVideoPlayActivity : AppCompatActivity() {
    private lateinit var mVideoView : VideoView<AbstractPlayer>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_video_play)
        image_back_guanyu.setOnClickListener { finish() }
        LiveEventBus.get(PieceBean.Price::class.java)
            .observeSticky(this, { price ->
                Log.e("TAG", "onChangedssss: " + Gson().toJson(price))
                val cacheServer: HttpProxyCacheServer = ProxyVideoCacheManager.getProxy(this)
                val proxyUrl = cacheServer.getProxyUrl(price.content)
                Log.d("TAG", "视频链接: " + price.content)
                mVideoView = findViewById(R.id.video_view)
                mVideoView.setUrl(proxyUrl)
                val controller = StandardVideoController(this)
                controller.addDefaultControlComponent(getString(R.string.str_cache), false)
                mVideoView.setVideoController(controller)
                mVideoView.start()
            })
    }
}