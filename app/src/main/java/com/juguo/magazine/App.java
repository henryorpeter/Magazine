package com.juguo.magazine;

import android.app.Application;
import android.content.Context;

/**
 * author : Administrator
 * date : 2021/8/18 9:46
 * description :
 *
 * @Author : yangjinjing
 */
public class App extends Application {
    private static Context sInstance;
//    public GtpClient gtpClient = null;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        //穿山甲SDK初始化
//        //强烈建议在应用对应的Application#onCreate()方法中调用，避免出现content为null的异常
//        TTAdManagerHolder.init(this);
//        LiveEventBus
//                .config();
    }

    public static Context getContext() {
        return sInstance;
    }
}
